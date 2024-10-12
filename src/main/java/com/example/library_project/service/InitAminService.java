package com.example.library_project.service;

import com.example.library_project.entity.ProfileEntity;
import com.example.library_project.enums.ProfileRole;
import com.example.library_project.enums.ProfileStatus;
import com.example.library_project.repository.ProfileRepository;
import com.example.library_project.utl.MDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InitAminService {
    private  final ProfileRepository profileRepository;
    public void initAdmin() {
        ProfileEntity profile=new ProfileEntity();
        profile.setEmail("admin@gmail.com");
        profile.setPassword(MDUtil.encode("1234"));
        profile.setRole(ProfileRole.ROLE_ADMIN);
        profile.setName("aliy");
        profile.setStatus(ProfileStatus.ACTIVE);
        Optional<ProfileEntity>optional=profileRepository.findByEmail(profile.getEmail());
        if (optional.isEmpty()) {
            profileRepository.save(profile);
        }
    }
}
