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
}
