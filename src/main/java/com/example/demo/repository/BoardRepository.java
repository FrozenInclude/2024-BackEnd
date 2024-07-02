package com.example.demo.repository;

import java.util.List;

import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {

}
