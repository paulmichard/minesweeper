package com.paulmichard.minesweeper.controller;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameRequest;
import com.paulmichard.minesweeper.service.GameService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GameControllerImpl implements GameController {

	private final GameService gameService;

	@Override
	public ResponseEntity<GameBoardBean> createNewGame(@Valid GameRequest gameRequest) {
		return ResponseEntity.ok(gameService.createGame(gameRequest));
	}

	@Override
	public ResponseEntity<GameBoardBean> loadGame(Long id) {
		return ResponseEntity.ok(gameService.loadGame(id));
	}

	@Override
	public ResponseEntity<GameBoardBean> pauseGame(Long id) {
		return ResponseEntity.ok(gameService.pauseGame(id));
	}

	@Override
	public ResponseEntity<GameBoardBean> resumeGame(Long id) {
		return ResponseEntity.ok(gameService.resumeGame(id));
	}

	@Override
	public ResponseEntity<GameBoardBean> flagCell(Long id, Long cellId) {
		return ResponseEntity.ok(gameService.flagCellInGame(id, cellId));
	}

	@Override
	public ResponseEntity<GameBoardBean> questionMarkCell(Long id, Long cellId) {
		return ResponseEntity.ok(gameService.markCellInGame(id, cellId));
	}

	@Override
	public ResponseEntity<GameBoardBean> showCell(Long id, Long cellId) {
		return ResponseEntity.ok(gameService.showCell(id, cellId));
	}
}
