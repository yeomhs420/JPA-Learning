package com.example.book.domain;

import com.example.book.listener.Auditable;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass   // 해당 entity의 컬럼을 상속받는 entity의 컬럼으로 포함 시켜 주겠다!
@EntityListeners(value = AuditingEntityListener.class)  // JPA 에서 기본적으로 제공하는 class
public class BaseEntity implements Auditable {
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
