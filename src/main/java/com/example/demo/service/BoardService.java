package com.example.demo.service;

import java.util.List;

import com.example.demo.AOPexception.Exception.DeleteExistedExcepton;
import com.example.demo.AOPexception.Exception.GetNotFoundException;
import com.example.demo.AOPexception.Exception.PostIllegalArgumemtException;
import com.example.demo.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.request.BoardCreateRequest;
import com.example.demo.controller.dto.request.BoardUpdateRequest;
import com.example.demo.controller.dto.response.BoardResponse;
import com.example.demo.domain.Board;
import com.example.demo.repository.BoardRepository;

@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;

    public BoardService(BoardRepository boardRepository,ArticleRepository articleRepository) {
        this.boardRepository = boardRepository;
        this.articleRepository=articleRepository;
    }

    public List<BoardResponse> getBoards() {
        return boardRepository.findAll().stream()
            .map(BoardResponse::from)
            .toList();
    }

    public BoardResponse getBoardById(Long id) {
       try {
           Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);;
           return BoardResponse.from(board);
       }catch (RuntimeException e) {
           throw new GetNotFoundException(e.getMessage());
       }
    }
    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request) {
        if (request.name() == null ) {
            throw new PostIllegalArgumemtException("NULL field existed");
        }
        Board board = new Board(request.name());
        Board saved = boardRepository.save(board);
        return BoardResponse.from(saved);
    }


    @Transactional
    public void deleteBoard(Long id) {
        //if(!articleRepository.findAllByBoard_Id(id).isEmpty())
            //throw new DeleteExistedExcepton("Articles exist in board!");
        boardRepository.deleteById(id);
    }

    @Transactional
    public BoardResponse update(Long id, BoardUpdateRequest request) {
        Board board = boardRepository.findById(id).orElseThrow(IllegalArgumentException::new);;
        board.update(request.name());
        Board updated = boardRepository.save(board);
        return BoardResponse.from(updated);
    }
}
