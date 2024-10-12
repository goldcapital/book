package com.example.library_project.entity;

import com.example.library_project.enums.ProfileRole;
import com.example.library_project.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "profile")
public class ProfileEntity extends BastEntity{

    @Column(name = "email")
    private  String email;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
     private  String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status;
    @Column(name = "is_logged_in")
    private boolean isLoggedIn=false;

   /* @Column(name = "jwt_token")
    private String jwtToken;*/
}
