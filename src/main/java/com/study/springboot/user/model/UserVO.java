package com.study.springboot.user.model;

import com.study.springboot.common.CommonVO;
import com.study.springboot.config.role.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor // 기본 생성자 자동생성
@Entity // Entity 클래스임을 명시
@Table(name = "USER") // USER 테이블과 매핑
@Getter
public class UserVO extends CommonVO implements Serializable {

    @Setter
    @Column(nullable = false, length = 50)
    private String userEmail;

    @Setter
    @Column(nullable = false)
    private String userPw;

    @Setter
    @Column(nullable = false, length = 50)
    @Enumerated(EnumType.STRING) // userRole은 String으로 고정
    private UserRole role;

    @Builder
    public UserVO(String userEmail, String userPw) {
        this.userEmail = userEmail;
        this.userPw = userPw;
    }
}
