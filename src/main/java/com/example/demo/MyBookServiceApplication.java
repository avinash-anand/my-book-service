package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
@RequestMapping(value = "/books")
@Import(value = { MyBeanConfiguration.class })
@Slf4j
public class MyBookServiceApplication {

    @Autowired
    private BookService bookService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AsyncRestTemplate asyncRestTemplate;

    private static final String BookReviewServiceUrl = "http://my-zuul-gateway/api/book-review-service/book-reviews/available";

    public static void main(String[] args) {
        SpringApplication.run(MyBookServiceApplication.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("Inside get all books.");
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        log.info("inside get book by id");
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/available")
    public ResponseEntity<String> getAvailablity() {
        ResponseEntity<String> response = restTemplate.getForEntity(BookReviewServiceUrl, String.class);
        return ResponseEntity.ok(response != null ? response.getBody() + "\n" + "Book service is available as well"
                : "Book service is available");
    }

//    @GetMapping("/available-future")
//    public ResponseEntity<String> getAvailablityWithFuture() {
//        log.debug("[MyBookServiceApplication][getAvailablityWithFuture] called.");
//        ListenableFuture<ResponseEntity<String>> futureResponse = asyncRestTemplate.getForEntity(BookReviewServiceUrl,
//                String.class, (Object[]) null);
//        String response = null;
//        if(futureResponse == null) {
//            response = "aa";
//        }
//        futureResponse.addCallback(new SuccessCallback<ResponseEntity<String>>() {
//
//            @Override
//            public void onSuccess(ResponseEntity<String> result) {
//                response = futureResponse != null ? result.getBody() + "\n" + "Book service is available as well"
//                        : "Book service is available";
//            }
//        }, new FailureCallback() {
//            
//            @Override
//            public void onFailure(Throwable ex) {
//                throw new BookServiceException("Future failed while calling book review service", ex);
//            }
//        });
//        return ResponseEntity.ok(response);
//    }

}
