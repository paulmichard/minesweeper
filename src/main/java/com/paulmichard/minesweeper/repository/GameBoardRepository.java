package com.paulmichard.minesweeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paulmichard.minesweeper.model.GameBoard;

public interface GameBoardRepository extends JpaRepository<GameBoard, Long> {
}
