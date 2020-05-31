package com.paulmichard.minesweeper.mother;

import static com.paulmichard.minesweeper.model.GameBoardStatus.NEW;

import org.assertj.core.util.Lists;

import com.paulmichard.minesweeper.bean.GameBoardBean;

public class GameBoardBeanMother {
	public static GameBoardBean basic() {
		return GameBoardBean.builder()
				.id(1L)
				.status(NEW)
				.rows(2L)
				.columns(2L)
				.mines(2L)
				.cells(GameCellBeanMother.cellsAsList())
				.build();
	}

	public static GameBoardBean basicWithoutCells() {
		return GameBoardBean.builder()
				.id(1L)
				.status(NEW)
				.rows(2L)
				.columns(2L)
				.mines(2L)
				.cells(Lists.emptyList())
				.build();
	}
}
