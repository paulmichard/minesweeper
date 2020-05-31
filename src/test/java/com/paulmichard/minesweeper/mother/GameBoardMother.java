package com.paulmichard.minesweeper.mother;

import static com.paulmichard.minesweeper.model.GameBoardStatus.NEW;

import org.assertj.core.util.Lists;

import com.paulmichard.minesweeper.model.GameBoard;

public class GameBoardMother {
	public static GameBoard basicWithoutCells() {
		return GameBoard.builder()
				.id(1L)
				.status(NEW)
				.rows(2L)
				.columns(2L)
				.mines(2L)
				.cells(Lists.emptyList())
				.build();
	}
}
