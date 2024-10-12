package com.example.library_project.service;

import com.example.library_project.confg.CustomUserDetails;
import com.example.library_project.dto.BookDTO;
import com.example.library_project.dto.ProfileBookDTO;
import com.example.library_project.dto.ProfileMappangDto;
import com.example.library_project.dto.request.ProfileBookRequest;
import com.example.library_project.entity.BookEntity;
import com.example.library_project.entity.ProfileBookEntity;
import com.example.library_project.entity.ProfileEntity;
import com.example.library_project.enums.ProfileStatus;
import com.example.library_project.exp.AppBadException;
import com.example.library_project.repository.ProfileBookRepository;
import com.example.library_project.repository.ProfileRepository;
import com.example.library_project.utl.SpringSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileBookService {
    private final BookService bookService;
    private final ProfileBookRepository profileBookRepository;
    private final ProfileRepository profileRepository;

    public ProfileBookDTO takeBook(ProfileBookRequest bookId) {

        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();
        BookDTO bookDTO = bookService.getBookById(bookId.getBook_id());
        if (bookDTO != null&&bookDTO.getSize()!=null) {

            BookEntity bookEntity = new BookEntity();
            bookEntity.setId(bookId.getBook_id());
            char hint='-';
            bookService.bookAdditionAndSubtraction(bookDTO.getId(), hint);

            ProfileEntity profile = new ProfileEntity();
            profile.setId(customUserDetails.getId());

            ProfileBookEntity profileBookEntity = new ProfileBookEntity();
            profileBookEntity.setBook(bookEntity);
            profileBookEntity.setProfile(profile);
            profileBookEntity.setStatus(ProfileStatus.ACTIVE);
            profileBookEntity.setDuration(bookId.getDuration());

            profileBookRepository.save(profileBookEntity);
            return toDto(profileBookEntity);
        }
        throw new ArithmeticException("book not found");
    }

    private ProfileBookDTO toDto(ProfileBookEntity profileBookEntity) {

        ProfileBookDTO profileBookDTO = new ProfileBookDTO();
        profileBookDTO.setId(profileBookEntity.getId());
        profileBookDTO.setBook_id(profileBookEntity.getBook().getId());
        profileBookDTO.setProfile_id(profileBookEntity.getProfile().getId());
        profileBookDTO.setCreatedDate(profileBookEntity.getCreatedDate());
        profileBookDTO.setStatus(ProfileStatus.ACTIVE);
        profileBookDTO.setDuration(profileBookEntity.getDuration());
        return profileBookDTO;
    }

    public Boolean returnBook(Long id) {
        CustomUserDetails customUserDetails = SpringSecurityUtil.getCurrentUser();
        Optional<ProfileBookEntity> optional = profileBookRepository.getProfile_Id(id, customUserDetails.getEmail());

        if (optional.isPresent()&& optional.get().getStatus().equals(ProfileStatus.ACTIVE)) {
            ProfileBookEntity profileBookEntity = optional.get();
            profileBookEntity.setReturnedDate(LocalDateTime.now());
            profileBookEntity.setStatus(ProfileStatus.NOT_ACTIVE);
            char hint='+';
            bookService.bookAdditionAndSubtraction(id,hint);
            profileBookRepository.save(profileBookEntity);
            return true;
        }

        throw new AppBadException("book not found or profile not found");
    }

    public List<ProfileBookDTO> getAllBooks() {

        List<ProfileBookEntity> bookEntities = (List<ProfileBookEntity>) profileBookRepository.findAllActiveBooks();
        return bookEntities.stream().map(this::toDto).collect(Collectors.toList());

    }

    public List<ProfileMappangDto> getBookByProfileById() {
        return profileBookRepository.getByProfiler(SpringSecurityUtil.getCurrentUser().getEmail())
                .stream()
                .filter(Objects::nonNull)
                .map(profileMappar -> new ProfileMappangDto(
                        profileMappar.getName(),
                        profileMappar.getTitle(),
                        profileMappar.getAuthor(),
                        profileMappar.getStatus()
                ))
                .collect(Collectors.toList());
    }

}


