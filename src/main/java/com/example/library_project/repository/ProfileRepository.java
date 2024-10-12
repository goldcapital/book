package com.example.library_project.repository;

import com.example.library_project.entity.ProfileEntity;
import com.example.library_project.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends CrudRepository<ProfileEntity, String> {

    Optional<ProfileEntity> findByEmail(String username);


    @Transactional
    @Modifying
    @Query("update  ProfileEntity  set status = :profileStatus, isLoggedIn =true  where  email= :email")
    void updateByEmail(@Param("profileStatus") ProfileStatus profileStatus,@Param("email") String email);

    @Transactional
    @Modifying
    @Query("delete from ProfileEntity where email=?1")
    void deleteByEmail(String email);

    @Transactional
    @Modifying
    @Query("update  ProfileEntity  set  isLoggedIn = :isLoggedIn where  email= :email")
    void updateByIsLoggendIn(@Param("isLoggedIn") boolean b,@Param("email") String email);
}
