package com.example.library_project.service;

import com.example.library_project.entity.ProfileEntity;
import com.example.library_project.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;

   /* private ProfileEntity findByProfileById(UUID id) {
        return profileRepository.findById(id)
                .orElseThrow(() -> InvalidRequestException("Profile not found by id: ", id));
    }*/
}
