package com.study.springboot.user.repository;

import com.study.springboot.user.model.UserVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // DB 접근자 (DAO)
public interface UserRepository extends JpaRepository<UserVO, Long> {

    UserVO findByUserEmailAndUserPw(String userId, String userPw);

    Optional<UserVO> findByUserEmail(String userEmail);
}
