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




    @Test
    void bookCascadeTest(){
        Book book = new Book();
        book.setName("JPA 초격차 패키지");


        Publisher publisher = new Publisher();
        publisher.setName("패스트캠퍼스");


        book.setPublisher(publisher);   // 연관관계 맺어줌 -> 비영속화 상태에서 엔티티간 연관관계 맺기가 안됨 (cascade로 연관관계 맺음)
        bookRepository.save(book);  // persist된 book과 연관된 publisher가 cascade를 통해 같이 persist됨

        publisher.addBook(book);
        publisherRepository.save(publisher);

        System.out.println("books: " + bookRepository.findAll());
        System.out.println("publishers: " + publisherRepository.findAll());

        Book book1 = bookRepository.findById(1L).get();
        book1.getPublisher().setName("슬로우캠퍼스"); // merge된 book과 연관된 publisher가 cascade를 통해 merge됨

        bookRepository.save(book1);

        System.out.println(publisherRepository.findAll());




        book1.setPublisher(null); // 연관관계 제거

        bookRepository.save(book1);
        bookRepository.deleteById(1L);  // remove된 book과 연관된 publisher가 cascade를 통해 remove되지만, 연관관계를 제거했기 때문에 영속성 전이x


        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers : " + publisherRepository.findAll());


    }

}