package com.paulmichard.minesweeper.dao;

import com.paulmichard.minesweeper.bean.GameBoardBean;

public interface GameBoardDAO {
	GameBoardBean saveBoard(GameBoardBean gameBoardBean);

	GameBoardBean fetchBoardById(Long id);
}
