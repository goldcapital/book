package com.example.library_project.dto.request;

import lombok.Data;

@Data
public class BookRequest {
    private String title;
    private String author;
    private int publishYear;
}
