package com.paulmichard.minesweeper.service;

import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;

public interface CellService {

	GameCellBean createBoardCells(GameRequest gameRequest);
}
