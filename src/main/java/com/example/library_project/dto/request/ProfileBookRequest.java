package com.example.library_project.dto.request;

import com.example.library_project.entity.BookEntity;
import com.example.library_project.enums.ProfileStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ProfileBookRequest {
    private Long book_id;
    private int duration;
}
