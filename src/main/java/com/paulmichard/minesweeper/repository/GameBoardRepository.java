package com.paulmichard.minesweeper.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.model.GameBoard;
import com.paulmichard.minesweeper.model.GameBoardStatus;

@Repository
public interface GameBoardRepository extends JpaRepository<GameBoard, Long> {

	Optional<GameBoardBean> findByIdAndStatusIn(Long id, GameBoardStatus... status);
}
