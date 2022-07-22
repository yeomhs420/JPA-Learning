package com.example.book.service;

import com.example.book.domain.Author;
import com.example.book.domain.Book;
import com.example.book.repository.AuthorRepository;
import com.example.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;    // @RequiredArgsConstructor 을 통한 생성자 주입

    private final AuthorRepository authorRepository;

    @Transactional  // 이 매서드가 끝나면 commit , 중간에 save 는 매서드가 commit 되고 난 후에 DB에 저장
    public void putBookAndAuthor() throws Exception{    // 자신을 호출한 상위메서드로 예외를 던지게 됨
        Book book = new Book();
        book.setName("JPA 시작하기");

        bookRepository.save(book); // DB 에는 아직 적용이 안된 시점

        //System.out.println(bookRepository.findAll());   // 영속성 컨텍스트로부터 가져옴

        Author author = new Author();
        author.setName("martin");

        authorRepository.save(author);

        throw new Exception("오류가 나서 DB commit이 발생하지 않습니다.");

        //throw new RuntimeException("오류가 나서 DB commit이 발생하지 않습니다.");
    }

    @Transactional
    public void get(Long id){
        System.out.println(">>>" + bookRepository.findById(id));
        System.out.println(">>>" + bookRepository.findAll());

        System.out.println(">>>" + bookRepository.findById(id));
        System.out.println(">>>" + bookRepository.findAll());

    }

}
