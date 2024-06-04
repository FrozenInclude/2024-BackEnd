package com.example.demo.service;

import java.util.List;

import com.example.demo.AOPexception.Exception.DeleteExistedExcepton;
import com.example.demo.AOPexception.Exception.GetNotFoundException;
import com.example.demo.AOPexception.Exception.PostIllegalArgumemtException;
import com.example.demo.AOPexception.Exception.PutDuplicatedException;
import com.example.demo.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    public final ArticleRepository articleRepository;

    public MemberService(MemberRepository memberRepository, ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
    }

    public MemberResponse getById(Long id) {
        try {
            Member member = memberRepository.findById(id);
            return MemberResponse.from(member);
        } catch (RuntimeException e) {
            throw new GetNotFoundException(e.getMessage());
        }
    }

    public List<MemberResponse> getAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponse::from)
                .toList();
    }

    @Transactional
    public MemberResponse create(MemberCreateRequest request) {
        if (request.name() == null || request.email() == null || request.password() == null) {
            throw new PostIllegalArgumemtException("NULL field existed");
        }
        Member member = memberRepository.insert(
                new Member(request.name(), request.email(), request.password())
        );
        return MemberResponse.from(member);
    }

    @Transactional
    public void delete(Long id) {
        if (!articleRepository.findAllByMemberId(id).isEmpty())
            throw new DeleteExistedExcepton("one above articles existed written by this member");
        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id);
        try {
            memberRepository.findByEmail(request.email());
        } catch (RuntimeException e) {
            member.update(request.name(), request.email());
            memberRepository.update(member);
            return MemberResponse.from(member);
        }
        throw new PutDuplicatedException("already used email");
    }
}
