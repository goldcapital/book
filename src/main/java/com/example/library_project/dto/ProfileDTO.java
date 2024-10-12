package com.example.library_project.dto;

import com.example.library_project.enums.ProfileRole;
import com.example.library_project.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private UUID uuid;
    private  String email;
    private String name;
    private  String password;
    private ProfileRole role;
    private ProfileStatus status;
    private  String jwtToken;
}
