package com.paulmichard.minesweeper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paulmichard.minesweeper.model.GameCell;

@Repository
public interface GameCellRepository extends JpaRepository<GameCell, Long> {
}
