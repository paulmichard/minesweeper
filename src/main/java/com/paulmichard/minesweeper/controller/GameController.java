package com.paulmichard.minesweeper.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RequestMapping(value = "/minesweeper/api/v1/games")
@Api(value = "Game Controller")
public interface GameController {

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(
			httpMethod = "POST",
			value = "Creates a new game")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Game created", response = GameBoardBean.class),
			@ApiResponse(code = 404, message = "Game not found"),
			@ApiResponse(code = 500, message = "Unknown error")})
	ResponseEntity<GameBoardBean> createNewGame(@Valid @RequestBody GameRequest gameRequest);

	@GetMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(
			httpMethod = "GET",
			value = "Loads an existing game")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Game loaded", response = GameBoardBean.class),
			@ApiResponse(code = 404, message = "Game not found"),
			@ApiResponse(code = 500, message = "Unknown error")})
	ResponseEntity<GameBoardBean> loadGame(@PathVariable Long id);

	@PatchMapping(path = "/{id}/pause", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(
			httpMethod = "PATCH",
			value = "Pauses a game that is still active")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Game paused", response = GameBoardBean.class),
			@ApiResponse(code = 404, message = "Game not found"),
			@ApiResponse(code = 409, message = "Game is not active"),
			@ApiResponse(code = 500, message = "Unknown error")})
	ResponseEntity<GameBoardBean> pauseGame(@PathVariable Long id);

	@PatchMapping(path = "/{id}/resume", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(
			httpMethod = "PATCH",
			value = "Resumes a game that is still active")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Game resumed", response = GameBoardBean.class),
			@ApiResponse(code = 404, message = "Game not found"),
			@ApiResponse(code = 409, message = "Game is not active"),
			@ApiResponse(code = 500, message = "Unknown error")})
	ResponseEntity<GameBoardBean> resumeGame(@PathVariable Long id);

	@PostMapping(path = "/{id}/cells/{cellId}/flag", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(
			httpMethod = "POST",
			value = "Flags a cell")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cell flagged", response = GameBoardBean.class),
			@ApiResponse(code = 404, message = "Cell not found"),
			@ApiResponse(code = 500, message = "Unknown error")})
	ResponseEntity<GameBoardBean> flagCell(@PathVariable Long id, @PathVariable Long cellId);

	@PostMapping(path = "/{id}/cells/{cellId}/questionMark", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(
			httpMethod = "POST",
			value = "Adds a question mark to a cell")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cell marked", response = GameBoardBean.class),
			@ApiResponse(code = 404, message = "Cell not found"),
			@ApiResponse(code = 500, message = "Unknown error")})
	ResponseEntity<GameBoardBean> questionMarkCell(@PathVariable Long id, @PathVariable Long cellId);

	@PostMapping(path = "/{id}/cells/{cellId}/show", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(
			httpMethod = "POST",
			value = "Reveals the cell")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cell flagged", response = GameBoardBean.class),
			@ApiResponse(code = 404, message = "Cell not found"),
			@ApiResponse(code = 500, message = "Unknown error")})
	ResponseEntity<GameBoardBean> showCell(@PathVariable Long id, @PathVariable Long cellId);
}
