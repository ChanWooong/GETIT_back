package com.getit.domain.admin.service;

import com.getit.domain.admin.dto.AdminMember;
import com.getit.domain.lecture.entity.LectureRecord;
import com.getit.domain.lecture.repository.LectureRecordRepository;
import com.getit.domain.lecture.repository.LectureRepository;
import com.getit.domain.member.entity.Member;
import com.getit.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;
    private final LectureRecordRepository lectureRecordRepository;

    public List<AdminMember> getAllMembersStatus() {
        List<Member> members = memberRepository.findAll();

        long totalLectures = lectureRepository.count();
        if (totalLectures == 0) totalLectures = 1;

        List<AdminMember> response = new ArrayList<>();

        for (Member member : members) {
            List<LectureRecord> records = lectureRecordRepository.findByMember(member);
            long doneCount = records.stream()
                    .filter(LectureRecord::isVideoDone)
                    .count();
            int rate = (int) ((double) doneCount / totalLectures * 100);

            response.add(AdminMember.of(member, rate));
        }
        return response;
    }
}