package com.example.book.domain;

import com.example.book.listener.Auditable;
import com.example.book.listener.MyEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@EntityListeners(value = AuditingEntityListener.class)
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private Long authorId;


    @OneToOne(mappedBy = "book")    // mappedby 를 설정하게 되면 해당 컬럼을 테이블에서 갖게 되지 않음
    @ToString.Exclude   // relation 양방향 관계의 경우 순환 참조가 걸리게 되어 ToString 에서 제외시켜야함
    private BookReviewInfo bookReviewInfo;


    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude   // 양방향 관계이므로 순환 참조 제거
    private List<Review> reviews;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})  // book이 persist나 merge 될 때, publisher도 같이 되게 cascade(종속) 시킴
    @ToString.Exclude
    private Publisher publisher;

}
