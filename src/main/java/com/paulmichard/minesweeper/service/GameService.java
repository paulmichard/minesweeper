package com.paulmichard.minesweeper.service;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameRequest;

public interface GameService {

	/**
	 * Creates a new game based on the request received
	 *
	 * @param gameRequest
	 * @return
	 */
	GameBoardBean createGame(GameRequest gameRequest);

	/**
	 * Searches for a game with the given id
	 *
	 * @param id
	 * @return
	 */
	GameBoardBean loadGame(Long id);

	/**
	 * Makes a cell visible
	 *
	 * @param id
	 * @param cellId
	 * @return
	 */
	GameBoardBean showCell(Long id, Long cellId);

	/**
	 * Adds a flag to the given cell and board
	 *
	 * @param id
	 * @param cellId
	 * @return
	 */
	GameBoardBean flagCellInGame(Long id, Long cellId);

	/**
	 * Adds a question mark to the given cell and board
	 *
	 * @param id
	 * @param cellId
	 * @return
	 */
	GameBoardBean markCellInGame(Long id, Long cellId);
}
