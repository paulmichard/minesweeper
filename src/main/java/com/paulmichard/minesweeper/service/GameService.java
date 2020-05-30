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
}
