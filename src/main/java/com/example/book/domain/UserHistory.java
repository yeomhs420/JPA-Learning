package com.example.book.domain;

import com.example.book.listener.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "man_id", insertable = false, updatable = false)
//    private Long manId;

    @NonNull
    private String name;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    private Man man;
}
