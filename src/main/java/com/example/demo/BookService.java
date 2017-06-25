package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    private static List<Book> bookList = new ArrayList<>();;
    static {
        for (int i = 1; i <= 5; i++) {
            Book book = new Book(i, "Book - " + i);
            bookList.add(book);
        }
    }

    public List<Book> getAllBooks() {
        return bookList;
    }

    public Book getBookById(int id) {
        return bookList.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

}
