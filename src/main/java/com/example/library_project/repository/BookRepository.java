package com.example.library_project.repository;

import com.example.library_project.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends JpaRepository<BookEntity,Long> {
}
