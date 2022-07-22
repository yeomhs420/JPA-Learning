package com.example.book.service;

import com.example.book.domain.Man;
import com.example.book.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.TransactionManager;

@SpringBootTest
//@Transactional
public class EntityManagerTest {

    @Autowired
    private EntityManager entityManager;


    @Autowired
    private UserRepository userRepository;



    @Test
    void entityManagerTest(){
        System.out.println(entityManager.createQuery("select m from Man m").getResultList()); // = userRepository.findAll()

    }

    @Test
    void cacheFindTest(){


        Man man = userRepository.findById(1L).get();

        man.setEmail("yeom@naver.com");

        userRepository.save(man);   // persist(영속화)

        System.out.println("------------------------");

        man.setEmail("yeom2@naver.com");
        userRepository.save(man);

        // entityManager.flush();

        System.out.println(userRepository.findAll());   // id=1 인 값은 영속성 context의 cahche에서 가져오게됨 (최신값)

        //userRepository.delete(man);  // Rollback, 위의 @Transactional 제거하면 해결

    }
}
