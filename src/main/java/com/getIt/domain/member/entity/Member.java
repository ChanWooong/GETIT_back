package com.getit.domain.member.entity;

import com.getit.domain.member.Role;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private Long studentId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isApproved = false; // 기본값 false , 관리자 승인 후  true로 변경

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // GUEST, MEMBER, ADMIN만 존재

    @Builder
    public Member(Long studentId, String password, String name, Role role) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    public void approve(){
        this.isApproved = true;
        this.role = Role.ROLE_MEMBER;
    }
}