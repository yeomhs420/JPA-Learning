package com.example.book.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.example.book.domain.Man;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QPageRequest;


public interface UserRepository extends JpaRepository<Man, Long> {

    @Query(value = "select * FROM Man where id > :id" , nativeQuery = true)
    List<Man> findByarrId(Long id);

    List<Man> findByName(String name);


    Man findByEmail(String email);


    @Query(value = "select * FROM Man where name = :name order by id desc limit 2" , nativeQuery = true)    // id 역순으로 2개까지 가져옴
    List<Man> findLast2ByName(String name);

    List<Man> findFirst2ByName(String name);    // 처음부터 2개까지 가져옴


    List<Man> findByEmailAndName(String Email, String name);


    List<Man> findByIdAfter(Long id);


    List<Man> findByIdGreaterThanEqual(Long id);

    List<Man> findByIdBetween(Long id1, Long id2);


    List<Man> findByNameStartingWith(String name);

    List<Man> findByNameEndingWith(String name);

    List<Man> findByNameLike(String name);


    List<Man> findTop3ByNameOrderByIdDesc(String name); // name 값에 해당하는 레코드들을 id 역순으로 출력

    List<Man> findFirst3ByName(String name, Sort sort); // 위 코드와 동일하나 가독성 측면이나 매서드의 재사용성 면에서 좋음


    Page<Man> findByName(String name, Pageable pageable);


    @Query(value = "select * from man limit 1;", nativeQuery = true)
    Map<String, Object> findRowRecord();
}
