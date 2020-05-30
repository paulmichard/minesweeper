package com.paulmichard.minesweeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paulmichard.minesweeper.model.GameBoard;

@Repository
public interface GameBoardRepository extends JpaRepository<GameBoard, Long> {
}
