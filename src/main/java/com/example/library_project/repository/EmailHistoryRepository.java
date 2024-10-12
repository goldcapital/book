package com.example.library_project.repository;

import com.example.library_project.entity.EmailHistoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface EmailHistoryRepository extends CrudRepository<EmailHistoryEntity , String> {
    @Query("select count (s) from  EmailHistoryEntity  s where s.email=?1  and s.createdDate between ?2 and ?3")
    Long countSendEmail(String email, LocalDateTime from, LocalDateTime to);
}
