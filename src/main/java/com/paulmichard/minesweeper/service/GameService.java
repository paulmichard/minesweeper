package com.paulmichard.minesweeper.service;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameRequest;

public interface GameService {

	GameBoardBean createGame(GameRequest gameRequest);
}
