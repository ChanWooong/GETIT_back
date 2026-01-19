package com.getit.domain.admin.controller;

import com.getit.domain.admin.service.AdminService;
import com.getit.domain.admin.dto.AdminMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize; // 보안 설정 필요 시 주석 해제
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/dashboard")
    // @PreAuthorize("hasRole('ADMIN')") // 이 줄은 Security 설정이 되어 있어야 작동함
    public ResponseEntity<List<AdminMember>> getDashboard() {
        return ResponseEntity.ok(adminService.getAllMembersStatus());
    }
}