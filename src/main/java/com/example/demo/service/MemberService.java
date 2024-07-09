package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.AOPexception.Exception.DeleteExistedExcepton;
import com.example.demo.AOPexception.Exception.GetNotFoundException;
import com.example.demo.AOPexception.Exception.PutDuplicatedException;
import com.example.demo.controller.dto.request.MemberLoginRequest;
import com.example.demo.repository.ArticleRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;


    public MemberService(MemberRepository memberRepository, ArticleRepository articleRepository,
                         PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberResponse getById(Long id) {
        try {
            Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            ;
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
    public MemberResponse create(MemberCreateRequest request) throws RuntimeException {
        if (memberRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        Member member = memberRepository.save(
                new Member(request.name(), request.email(), request.password())
        );
        member.encodePassword(passwordEncoder);
        return MemberResponse.from(member);
    }

    @Transactional
    public Member login(MemberLoginRequest request) throws RuntimeException {
        Optional<Member> optionalUser = memberRepository.findByEmail(request.email());
        if (optionalUser.isEmpty()) {
            return null;
        }
        Member user = optionalUser.get();
        if (!passwordEncoder.matches(request.password(),user.getPassword())) {
            return null;
        }
        return user;
    }

    @Transactional
    public Member getLoginUserByLoginId(String loginId) {
        if (loginId == null) return null;

        Optional<Member> optionalUser = memberRepository.findByEmail(loginId);
        return optionalUser.orElse(null);

    }

    @Transactional
    public void delete(Long id) {
        if (!articleRepository.findAllByAuthorId(id).isEmpty())
            throw new DeleteExistedExcepton("one above articles existed written by this member");
        memberRepository.deleteById(id);
    }

    @Transactional
    public MemberResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        ;
        try {
            memberRepository.findByEmail(request.email());
        } catch (RuntimeException e) {
            member.update(request.name(), request.email());
            memberRepository.save(member);
            return MemberResponse.from(member);
        }
        throw new PutDuplicatedException("already used email");
    }
}
