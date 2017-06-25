package com.example.demo;

import lombok.Data;

@Data
public class Book {
    private int id;
    private String name;

    public Book(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
