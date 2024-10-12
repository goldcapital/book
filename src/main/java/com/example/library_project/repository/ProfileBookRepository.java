package com.example.library_project.repository;

import com.example.library_project.entity.ProfileBookEntity;
import com.example.library_project.mappar.ProfileMappar;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProfileBookRepository extends CrudRepository<ProfileBookEntity,Long> {
    @Query("from ProfileBookEntity where book.id=?1 and profile.email=?2")
    Optional<ProfileBookEntity> getProfile_Id(Long id,String email);
@Query("from ProfileBookEntity where profile.id=?1 ")
    List<ProfileBookEntity> getByProfileAndProfile(String id);

@Query("SELECT p.name AS name, b.title AS title ,b.author AS author ,pb.status AS status\n" +
        "FROM ProfileBookEntity pb\n" +
        "JOIN ProfileEntity p ON pb.profile.id = p.id\n" +
        "JOIN BookEntity b ON pb.book.id = b.id\n" +
        "where p.email=?1\n" +
        "ORDER BY p.name")
   List<ProfileMappar> getByProfiler(String email);

@Query("select  p from  ProfileBookEntity p where p.status='ACTIVE'")
    List<ProfileBookEntity> findAllActiveBooks();

}
