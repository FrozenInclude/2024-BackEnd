package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);
}
