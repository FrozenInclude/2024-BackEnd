package com.example.demo.repository;

import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepositoryJpa implements MemberRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Member> findAll() {
        return entityManager.createQuery("SELECT e FROM Member e", Member.class).getResultList();
    }

    @Override
    public Member findById(Long id) {
        try {
            return entityManager.find(Member.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Member not found");
        }
    }

    @Override
    public Member findByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT e FROM Member e WHERE e.email=:email", Member.class).
                    setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Member not found");
        }
    }

    @Override
    public Member insert(Member member) {
        entityManager.persist(member);
        return entityManager.find(Member.class, member.getId());
    }

    @Override
    public Member update(Member member) {
        Member mem = findById(member.getId());
        mem.update(member.getName(), member.getEmail());
        return mem;
    }

    @Override
    public void deleteById(Long id) {
        Member member = findById(id);
        entityManager.remove(member);
    }
}
