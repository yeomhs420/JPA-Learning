package com.example.book.listener;

import com.example.book.domain.Man;
import com.example.book.domain.UserHistory;
import com.example.book.repository.UserHistoryRepository;
import com.example.book.support.BeanUtils;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
public class UserEntityListener {
    @PostPersist    // DB에 저장한 후에 userHistory 에 저장 (Pre로 할 시에, user의 id값이 생성되지 않기 때문)
    @PostUpdate
    public void preUpdate(Object o){    // update 를 할 때마다 userHistory 를 생성하여 유저 정보를 기록
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);   // Listener 에서는 Repository 을 자동 주입 불가

        Man user = (Man) o;

        UserHistory userHistory = new UserHistory();

        userHistory.setMan(user);   // Man_id를 외래키로 가짐
        userHistory.setEmail(user.getEmail());
        userHistory.setName(user.getName());

        userHistoryRepository.save(userHistory);
    }
}
