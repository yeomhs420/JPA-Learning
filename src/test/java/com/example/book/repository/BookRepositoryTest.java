package com.example.book.repository;

import com.example.book.domain.Book;
import com.example.book.domain.Man;
import com.example.book.domain.Publisher;
import com.example.book.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private  UserRepository userRepository;

    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("JPA 초격차 패키지");
        book.setAuthorId(1L);

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }

    @Test
    @Transactional
    void bookRelationTest(){


        givenBookAndReview();

        Man man = userRepository.findByEmail("martin@fastcampus.com");

        System.out.println("Review : " + man.getReviews());
        System.out.println("Book :" + man.getReviews().get(0).getBook());   // 사용자가 등록한 첫번째 리뷰의 책을 가져옴
        System.out.println("Publisher :" + man.getReviews().get(0).getBook().getPublisher());
    }

    private void givenBookAndReview(){
        givenReview(givenUser(), givenBook(givenPublisher()));
    }

    private Man givenUser(){
        return userRepository.findByEmail("martin@fastcampus.com");
    }

    private Book givenBook(Publisher publisher){
        Book book = new Book();
        book.setName("JPA 초격차 패키지");
        book.setPublisher(publisher);

        return bookRepository.save(book);
    }

    private void givenReview(Man man, Book book){
        Review review = new Review();
        review.setTitle("내 인생을 바꾼 책");

        review.setContent("너무너무 재미있고 즐거운 책이었어요.");

        review.setScore(5.0f);

        review.setMan(man);
        review.setBook(book);

        reviewRepository.save(review);
    }


    private Publisher givenPublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("패스트캠퍼스");

        return publisherRepository.save(publisher);
    }
}