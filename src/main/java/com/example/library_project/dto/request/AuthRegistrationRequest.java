package com.example.library_project.dto.request;

import lombok.Data;

@Data
public class AuthRegistrationRequest {
    private String username;
    private String email;
    private String password;

}
