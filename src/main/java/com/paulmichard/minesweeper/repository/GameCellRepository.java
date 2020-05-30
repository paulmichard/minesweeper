package com.paulmichard.minesweeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paulmichard.minesweeper.model.GameCell;

public interface GameCellRepository extends JpaRepository<GameCell, Long> {
}
