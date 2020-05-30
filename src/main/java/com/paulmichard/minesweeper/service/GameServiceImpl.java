package com.paulmichard.minesweeper.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

	private final CellService cellService;

	@Override
	public GameBoardBean createGame(GameRequest gameRequest) {
		return null;
	}
}
