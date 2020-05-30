package com.paulmichard.minesweeper.service;

import java.util.List;

import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;

public interface CellService {

	List<GameCellBean> createBoardCells(GameRequest gameRequest);
}
