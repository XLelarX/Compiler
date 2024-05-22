package com.lelar.controller.mvc;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {
    private String isbn;
    private String name;
    private String author;
}

