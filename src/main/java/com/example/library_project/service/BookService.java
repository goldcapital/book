package com.example.library_project.service;

import com.example.library_project.dto.BookDTO;
import com.example.library_project.dto.request.BookRequest;
import com.example.library_project.entity.BookEntity;
import com.example.library_project.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    public BookDTO createBook(BookDTO book) {

        bookRepository.save(toBookEntity(book));
        return toDto(toBookEntity(book));
    }
    private BookDTO toDto(BookEntity entity){
        BookDTO bookDTO=new BookDTO();
        bookDTO.setId(entity.getId());
        bookDTO.setSize(entity.getSize());
        bookDTO.setAuthor(entity.getAuthor());
        bookDTO.setTitle(entity.getTitle());
        bookDTO.setPublishYear(entity.getPublishYear());
        return bookDTO;

    }
    public BookDTO getBookById(Long id) {
    BookEntity bookEntity=bookRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return toDto(bookEntity);
    }

    public BookDTO updateBook(Long id, BookRequest bookDetails) {
        Optional<BookEntity> optional = bookRepository.findById(id);
        if (optional.isPresent()) {
            BookEntity bookEntity = optional.get();
            bookEntity.setTitle(bookDetails.getTitle());
            bookEntity.setPublishYear(bookEntity.getPublishYear());
            bookEntity.setAuthor(bookEntity.getAuthor());
            return toDto(bookEntity);
        }
        return null;
    }
    public PageImpl<BookDTO> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<BookEntity> studentPage = bookRepository.findAll(paging);

        List<BookEntity> entityList = studentPage.getContent();
        Long totalElements = studentPage.getTotalElements();

        List<BookDTO> dtoList = new LinkedList<>();
        for (BookEntity entity : entityList) {
            dtoList.add(toDto(entity));
        }
        return new PageImpl<>(dtoList, paging, totalElements);

    }

    public List<BookDTO> getAllBooks() {
        List<BookEntity>entityList=bookRepository.findAll();
        return  entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    private BookEntity toBookEntity(BookDTO dto){
        BookEntity bookEntity=new BookEntity();
        bookEntity.setAuthor(dto.getAuthor());
        bookEntity.setTitle(dto.getTitle());
        bookEntity.setSize(dto.getSize());
        bookEntity.setPublishYear(dto.getPublishYear());
        return bookEntity;
    }

    public void bookAdditionAndSubtraction(Long id, char count) {
        Optional<BookEntity>optional=bookRepository.findById(id);

        if (optional.isPresent()){
            BookEntity bookEntity=optional.get();
            if (count =='-') {
                bookEntity.setSize(bookEntity.getSize() - 1);
            }else {
                bookEntity.setSize(bookEntity.getSize()+1);
            }
            bookRepository.save(bookEntity);
        }
    }
}
