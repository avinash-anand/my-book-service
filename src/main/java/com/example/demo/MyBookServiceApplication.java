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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@RestController
@RequestMapping(value = "/books")
@Import(value = { MyBeanConfiguration.class })
public class MyBookServiceApplication {

    @Autowired
    private BookService bookService;

    @Autowired
    private RestTemplate restTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MyBookServiceApplication.class, args);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping("/available")
    public ResponseEntity<String> getAvailablity() {
        ResponseEntity<String> response = restTemplate
                .getForEntity("http://my-zuul-gateway/api/book-review-service/book-reviews/available", String.class);
        return ResponseEntity.ok(response != null ? response.getBody() + "\n" + "Book service is available as well"
                : "Book service is available");
    }

}
