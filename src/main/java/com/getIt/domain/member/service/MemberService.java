package com.getit.domain.member.service;

import com.getit.domain.member.dto.MemberResponse;
import com.getit.domain.member.entity.Member;
import com.getit.domain.member.Role;
import com.getit.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원가입 부분 -> 중복성 검사, 멤버 빌드해서 저장하는 과정까지.
    @Transactional
    public Long signUp(Long studentId, String rawPassword, String name, String track) {
        if (memberRepository.existsByStudentId(studentId)) {
            throw new IllegalArgumentException("이미 가입된 학번입니다.");
        }

        Member member = Member.builder()
                .studentId(studentId)
                .password(rawPassword)
                .name(name)
                .role(Role.ROLE_MEMBER)
                .build();

        memberRepository.save(member);
        return member.getId();
    }

    //로그인 부분 -> 유효한 학번인지, 그리고 비밀번호가 맞는지 체크하는 역할을 하는 부분.
    public Member login(Long studentId, String password) {
        Member member = (Member) memberRepository.findByStudentId(studentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 학번입니다."));
        if (!member.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return member;
    }
    @Transactional
    public void approveMember(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (member.getIsApproved()) {
            throw new IllegalStateException("이미 승인된 사용자입니다.");
        }
        member.approve(); // member entity 안에 존재.
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findPendingMembers() {
        return memberRepository.findAllByIsApprovedFalse().stream()
                .map(member -> MemberResponse.builder()
                        .id(member.getId())
                        .studentId(member.getStudentId())
                        .name(member.getName())
                        .role(member.getRole())
                        .build())
                .collect(Collectors.toList());
    }
}