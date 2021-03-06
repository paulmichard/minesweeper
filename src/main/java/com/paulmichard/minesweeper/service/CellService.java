package com.paulmichard.minesweeper.service;

import java.util.List;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;

public interface CellService {

	/**
	 * This method is responsible for creating all the cells that will be involved in a game,
	 * based on the parameters received in the GameRequest
	 *
	 * @param gameRequest
	 * @return
	 */
	List<GameCellBean> createBoardCells(GameRequest gameRequest);

	/**
	 * Flags the cell
	 *
	 * @param game
	 * @param cellId
	 */
	void flagCell(GameBoardBean game, Long cellId);

	/**
	 * Marks the cell
	 *
	 * @param game
	 * @param cellId
	 */
	void markCell(GameBoardBean game, Long cellId);

	/**
	 * Reveals the selected cell
	 *
	 * @param game
	 * @param cellId
	 */
	void revealCell(GameBoardBean game, Long cellId);
}
