package com.example.book.domain;

import com.example.book.listener.Auditable;
import com.example.book.listener.UserEntityListener;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true) // callsuper의 default값은 false이기 때문에 객체를 출력 해도 super의 속성값들이 반영이 안되기 때문에 상속받는 super에 대해 true로 설정
@EqualsAndHashCode(callSuper = true)    // callSuper 속성을 통해 eqauls 와 hashCode 메소드 자동 생성 시 부모 클래스의 필드까지 감안할지의 여부를 설정
@EntityListeners(value = UserEntityListener.class)
public class Man extends BaseEntity {  // BaseEntity 에서 MyEntityListener 처리 (JPA 에서 기본적으로 제공)

    @Id // 대표값을 지정! like a 주민등록번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1,2,3, ... DB가 id를 자동 생성하는 어노테이션
    private Long id;

    @NonNull
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)    // EnumType.STRING : enum 의 값을 index 가 아닌 텍스트 값 그대로 DB에 저장
    private Gender gender;

    @OneToMany(fetch = FetchType.EAGER) //  user를 통한 userHistory를 보는 경우가 많으므로 1:N관계
    @JoinColumn(name = "man_id" , insertable = false, updatable = false)
    // user entity 에서 userHistory 를 저장하거나 수정할 수 없음, 해당 JoinColumn은 userHistories라는 테이블이 또 생기지 않도록 외래키를 지정하는 어노테이션
    @ToString.Exclude   // 양방향 관계에서 순환 참조로 인한 ToString 제외
    private List<UserHistory> userHistories = new ArrayList<>();


    @OneToMany
    @JoinColumn(name = "man_id")
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

}
