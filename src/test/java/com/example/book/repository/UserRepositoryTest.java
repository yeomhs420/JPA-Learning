package com.example.book.repository;

import com.example.book.domain.Book;
import com.example.book.domain.Gender;
import com.example.book.domain.Man;

import com.example.book.domain.UserHistory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;

@SpringBootTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;


    @Test
    void crud() { // create, read, update, delete



        Man user1 = new Man();
        user1.setName("ha");
        user1.setEmail("haha@naver.com");

        System.out.println(user1.getId());

        userRepository.save(user1);


        userRepository.findAll().forEach(System.out::println);


//        Page<Man> mans = userRepository.findAll(PageRequest.of(0,3));   // 몇 번째 페이지인지 의미하는 page와 한 페이지의 사이즈를 의미하는 size
//
//        System.out.println("page :" + mans);
//        System.out.println("totalElements : " + mans.getTotalElements()); // element 개수
//        System.out.println("totalpages : " + mans.getTotalPages()); // 총 페이지 수
//        System.out.println("numberofElements : " + mans.getNumberOfElements()); // 해당 페이지의 element 개수
//        System.out.println("sort :" + mans.getSort());
//        System.out.println("size :" + mans.getSize());  // 페이징 할 떄 나누는 크기
//
//        mans.getContent().forEach(System.out::println); // 해당 페이지의 element들 출력



        // example 매칭 : 조건에 맞는 데이터를 조회
//        man man = new man();
//        man.setEmail("na");
//
//        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("email", contains());
//
//        Example<man> example = Example.of(man, matcher);
//
//        userRepository.findAll(example).forEach(System.out::println);


    }

    @Test
    void select(){
        System.out.println(("findByarrId : " + userRepository.findByarrId(1L)));

        System.out.println("findByName : " + userRepository.findByName("martin"));

        System.out.println("findByEmail : " + userRepository.findByEmail("martin@naver.com3"));

        System.out.println("findFirst2ByName : " + userRepository.findFirst2ByName("martin"));  // limit=2 : 처음부터 2개까지 가져옴

        System.out.println("findLast2Byname : " + userRepository.findLast2ByName("martin"));    // Query 에서 정의

        System.out.println("findByEmailAndName : " + userRepository.findByEmailAndName("martin@naver.com", "martin"));

        // 밑의 코드는 값을 비교하여 데이터를 가져옴

        System.out.println("findByIdAfter : " + userRepository.findByIdAfter(3L));  // 해당 id 값보다 큰 id 값을 가진 record 가져옴 (주로 시간, 날짜에서 사용)

        System.out.println("findByIdGreaterThanEqual" + userRepository.findByIdGreaterThanEqual(2L));   // id가 2이상인 record 가져옴

        System.out.println("findByIdBetween : " + userRepository.findByIdBetween(1L, 3L));  // id가 1~3인 record 가져옴

        System.out.println("findByNameStartingWith : " + userRepository.findByNameStartingWith("mar")); // LIKE mar% = mar로 시작하는 문자 찾기

        System.out.println("findByNameEndingWith : " + userRepository.findByNameEndingWith("tin")); // LIKE %tin = tin으로 끝나는 문자 찾기

        System.out.println("findByNameLike : " + userRepository.findByNameLike("%arti%")); // LIKE %arti% = arti를 포함하는 문자 찾기

    }

    @Test
    void pagingSortingTest(){
        System.out.println("findTopByName : " + userRepository.findTop3ByNameOrderByIdDesc("martin"));

        System.out.println("findByNameWithSortParams : " + userRepository.findFirst3ByName("martin", Sort.by(Sort.Order.desc("id"),
                Sort.Order.asc("email"))));

        System.out.println("findByNameWithPaging : " + userRepository.findByName("martin", PageRequest.of(0, 2,
                Sort.by(Sort.Order.desc("id")))).getContent()); // id 역순으로 출력된 결과물 중 0번째 페이지를 가져옴

      
    }

    @Test
    void enumTest(){
        Man man = userRepository.findById(1L).orElseThrow(RuntimeException::new);

        man.setGender(Gender.MALE);

        userRepository.save(man);

        userRepository.findAll().forEach(System.out::println);


        System.out.println("findgender :" + userRepository.findRowRecord().get("gender"));
    }


    @Test
    void preTest(){
        Man man = new Man();

        man.setEmail("martin2@naver.com");
        man.setName("martin");

        userRepository.save(man); // insert


        System.out.println(userRepository.findByEmail("martin2@naver.com"));

    }

    @Test
    void userTest(){

        userRepository.findAll().forEach(System.out::println);

        Man man = new Man();

        man.setName("yeomhs");

        man.setEmail("yeom@naver.com");

        Man man2 = new Man();

        man2.setName("yeomhs2");

        man2.setEmail("yeom2@naver.com");

        System.out.println(userRepository.save(man));

        System.out.println(userRepository.save(man2));

        userHistoryRepository.findAll().forEach(System.out::println);


    }

    @Test
    void userRelationTest(){

        Man man = new Man();
        man.setName("yeom");
        man.setEmail("yeom@naver.com");


        userRepository.save(man);

        man.setName("yeom2");

        userRepository.save(man);

        List<UserHistory> result = userRepository.findByEmail("yeom@naver.com").getUserHistories(); // 해당 메일에 해당하는 user 의 history 반환

        result.forEach(System.out::println);

    }


}