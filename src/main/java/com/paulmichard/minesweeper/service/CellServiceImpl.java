package com.paulmichard.minesweeper.service;

import static com.paulmichard.minesweeper.model.GameCellStatus.HIDDEN;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class CellServiceImpl implements CellService {
	@Override
	public GameCellBean createBoardCells(GameRequest gameRequest) {
		return null;
	}

	private GameCellBean buildCell(Long row, Long column) {
		return GameCellBean.builder()
				.rowPosition(row)
				.columnPosition(row)
				.hasMine(false)
				.status(HIDDEN)
				.build();
	}
}
