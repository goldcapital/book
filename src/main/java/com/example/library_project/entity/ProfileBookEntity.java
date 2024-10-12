package com.example.library_project.entity;

import com.example.library_project.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "profile_book")
public class ProfileBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity book;

    private LocalDateTime createdDate=LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @Column(name = "returned_date")
    private LocalDateTime returnedDate;

    @Column(name = "duration")
    private int duration;

}
