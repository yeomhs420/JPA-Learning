package com.example.book.service;

import com.example.book.domain.Book;
import com.example.book.domain.Publisher;
import com.example.book.repository.AuthorRepository;
import com.example.book.repository.BookRepository;
import com.example.book.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;


    @Test
    void transactionTest(){
        try{
            bookService.putBookAndAuthor();
        } catch (Exception e){  // Checked Exception 은 roll back 처리 x
            System.out.println(">>>" + e.getMessage());
        }

        System.out.println("books : " + bookRepository.findAll());

        System.out.println("authors : " + authorRepository.findAll());
    }

    @Test
    void isolationTest(){
        Book book = new Book();
        book.setName("JPA 강의");
        bookRepository.save(book);

        bookService.get(1L);

        System.out.println(">>> " + bookRepository.findAll());
    }

}