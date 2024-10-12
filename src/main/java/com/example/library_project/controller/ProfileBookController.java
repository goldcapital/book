package com.example.library_project.controller;

import com.example.library_project.dto.ProfileBookDTO;
import com.example.library_project.dto.ProfileMappangDto;
import com.example.library_project.dto.request.ProfileBookRequest;
import com.example.library_project.service.ProfileBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile/book")
public class ProfileBookController {
    @Autowired
    private ProfileBookService studentBookService;

    @PostMapping("/take_book")
    public ResponseEntity<ProfileBookDTO> takeBook(@RequestBody ProfileBookRequest request_book) {
        return ResponseEntity.ok(studentBookService.takeBook(request_book));
    }

    @PutMapping("/return_book/{id}")
    public ResponseEntity<Boolean> returnBook(@PathVariable Long id) {
   return ResponseEntity.ok(studentBookService.returnBook(id));

    }
    @GetMapping("/get_all")
    public  ResponseEntity<List<ProfileBookDTO>>getAllBooks(){
        return ResponseEntity.ok(studentBookService.getAllBooks());
    }
    @GetMapping("/get_book_profile")
    public ResponseEntity<List<ProfileMappangDto>>getBookProfile(){
        return ResponseEntity.ok(studentBookService.getBookByProfileById());
    }

}
