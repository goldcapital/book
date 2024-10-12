package com.example.library_project.dto;

import com.example.library_project.entity.BookEntity;
import com.example.library_project.entity.ProfileEntity;
import com.example.library_project.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


import java.time.LocalDateTime;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileBookDTO {
    private Long id;


    private String profile_id;

    private Long book_id;

    private LocalDateTime createdDate;
    private ProfileStatus status;

    private LocalDateTime returnedDate;

    private int duration;
}
