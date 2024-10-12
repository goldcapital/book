package com.example.library_project.dto;

import com.example.library_project.enums.AppLanguage;
import com.example.library_project.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class JwtDTO {
    private String email;
    private ProfileRole role;
    private AppLanguage appLanguage;
}
