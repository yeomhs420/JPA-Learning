package com.example.book.listener;

import com.example.book.listener.Auditable;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class MyEntityListener {
    @PrePersist
    public void prePersist(Object o){   // 해당 매서드는 Object 파라미터를 반드시 받아야함 (Entity 처리 Listener 이기 때문에 어떤 Entity 인지 구별해야함)
        if(o instanceof Auditable){
            ((Auditable) o).setCreatedAt(LocalDateTime.now());
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }

    }

    @PreUpdate
    public void preUpdate(Object o){
        if (o instanceof Auditable){
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }
}
