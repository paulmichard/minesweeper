package com.paulmichard.minesweeper.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameRequest;

@RequestMapping(value = "/minesweeper/api/v1/games")
public interface GameController {

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GameBoardBean> createNewGame(@Valid @RequestBody GameRequest gameRequest);

	@GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GameBoardBean> loadGame(@PathVariable Long id);

	@PostMapping(path = "/{id}/cells/{cellId}/flag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GameBoardBean> flagCell(@PathVariable Long id, @PathVariable Long cellId);

	@PostMapping(path = "/{id}/cells/{cellId}/questionMark", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GameBoardBean> questionMarkCell(@PathVariable Long id, @PathVariable Long cellId);

	@PostMapping(path = "/{id}/cells/{cellId}/show", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<GameBoardBean> showCell(@PathVariable Long id, @PathVariable Long cellId);
}
