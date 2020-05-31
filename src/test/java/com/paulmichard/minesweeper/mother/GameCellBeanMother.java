package com.paulmichard.minesweeper.mother;

import static com.paulmichard.minesweeper.model.GameCellStatus.HIDDEN;

import java.util.Arrays;
import java.util.List;

import com.paulmichard.minesweeper.bean.GameCellBean;

public class GameCellBeanMother {
	public static GameCellBean basicHiddenWithMine() {
		return GameCellBean.builder()
				.id(1L)
				.status(HIDDEN)
				.hasMine(true)
				.rowPosition(0L)
				.columnPosition(0L)
				.adjacentMines(1L)
				.build();
	}

	public static GameCellBean basicHiddenWithoutMine() {
		return GameCellBean.builder()
				.id(2L)
				.status(HIDDEN)
				.hasMine(false)
				.rowPosition(0L)
				.columnPosition(1L)
				.adjacentMines(2L)
				.build();
	}

	public static GameCellBean anotherBasicHiddenWithMine() {
		return GameCellBean.builder()
				.id(3L)
				.status(HIDDEN)
				.hasMine(true)
				.rowPosition(1L)
				.columnPosition(0L)
				.adjacentMines(1L)
				.build();
	}

	public static GameCellBean anotherBasicHiddenWithoutMine() {
		return GameCellBean.builder()
				.id(4L)
				.status(HIDDEN)
				.hasMine(false)
				.rowPosition(1L)
				.columnPosition(1L)
				.adjacentMines(2L)
				.build();
	}

	public static List<GameCellBean> cellsAsList() {
		return Arrays.asList(basicHiddenWithMine(), basicHiddenWithoutMine(), anotherBasicHiddenWithMine(), anotherBasicHiddenWithoutMine());
	}
}
