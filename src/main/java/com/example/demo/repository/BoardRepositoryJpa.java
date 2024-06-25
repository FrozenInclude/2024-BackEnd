package com.example.demo.repository;

import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardRepositoryJpa implements BoardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Board> findAll() {
        return entityManager.createQuery("SELECT e FROM Board e", Board.class).getResultList();
    }

    @Override
    public Board findById(Long id) {
        try {
            return entityManager.find(Board.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Board not found");
        }
    }

    @Override
    public Board insert(Board board) {
        entityManager.persist(board);
        return entityManager.find(Board.class, board.getId());
    }

    @Override
    public void deleteById(Long id) {
        Board board = findById(id);
        entityManager.remove(board);
    }

    @Override
    public Board update(Board board) {
        Board brd = findById(board.getId());
        brd.update(board.getName());
        return brd;
    }
}
