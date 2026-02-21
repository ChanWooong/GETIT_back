package com.getit.domain.member.controller;

import com.getit.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getPendingMembers() {
        return ResponseEntity.ok(memberService.findPendingMembers());
    }

    //권한 허용 API
    @PatchMapping("/{memberId}/approve")
    @PreAuthorize("hasRole('ADMIN')") // 운영진만 사용 가능한 API
    public ResponseEntity<String> approveMember(@PathVariable Long memberId) {
        System.out.println("로그인한 유저: " + SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println("보유 권한: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        memberService.approveMember(memberId);
        return ResponseEntity.ok("멤버 승인이 완료되었습니다.");

    }
}