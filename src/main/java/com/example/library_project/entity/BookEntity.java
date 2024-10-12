package com.example.library_project.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "book")
public class BookEntity {
    @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "author")
    private String author;
    @Column(name = "publish_year")
    private String publishYear;
    @Column(name = "visible")
    private Boolean visible=true;
    @Column(name = "book_size")
    private  Integer size;
    @Column(name = "created_date")
    private LocalDateTime createdDate=LocalDateTime.now();
}
