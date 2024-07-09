package com.example.demo.controller;

import java.util.List;

import com.example.demo.controller.dto.request.MemberLoginRequest;
import com.example.demo.domain.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.request.MemberCreateRequest;
import com.example.demo.controller.dto.request.MemberUpdateRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.service.MemberService;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> getMembers() {
        List<MemberResponse> response = memberService.getAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/members/login")
    public void login(
            @RequestBody MemberLoginRequest request,
            HttpSession session,
            HttpServletResponse response
    ) throws IOException {
        Member existingUser = (Member) session.getAttribute("loginUser");
        if (existingUser != null) {
            response.sendRedirect("/members/info");
            return;
        }
        Member user = memberService.login(request);
        if (user == null) {
            response.getWriter().write("로그인 아이디 또는 비밀번호가 틀렸습니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        session.setAttribute("loginUser", user);
        response.getWriter().write(String.format("loginId : %s login success!", user.getEmail()));
    }

    @GetMapping("/members/info")
    public String userInfo(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "로그인 상태가 아닙니다.";
        }
        return String.format("loginId : %s\nnickname : %s", loginUser.getEmail(), loginUser.getName());
    }

    @PostMapping("/members/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "로그아웃 되었습니다.";
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponse> getMember(
            @PathVariable Long id
    ) {
        MemberResponse response = memberService.getById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/members")
    public ResponseEntity<MemberResponse> create(
            @Validated @RequestBody MemberCreateRequest request
    ) {
        MemberResponse response = memberService.create(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/members/{id}")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable Long id,
            @RequestBody MemberUpdateRequest request
    ) {
        MemberResponse response = memberService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long id
    ) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
