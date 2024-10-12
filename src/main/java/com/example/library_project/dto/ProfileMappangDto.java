package com.example.library_project.dto;

import lombok.Data;

@Data
public class ProfileMappangDto{
    private  String name;
    private String title;
    private String author;
    private String status;

    public ProfileMappangDto(String name, String title, String author, String status) {
        this.name=name;
        this.title=title;
        this.author=author;
        this.status=status;
    }
}
