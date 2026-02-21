package com.getit.domain.member.dto;

import com.getit.domain.member.Role;
import com.getit.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {
    private Long id;
    private Long studentId;
    private String name;
    private Role role;

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .studentId(member.getStudentId())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}