package com.paulmichard.minesweeper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.paulmichard.minesweeper.exception.CellNotFoundException;
import com.paulmichard.minesweeper.exception.GameAlreadyCompletedException;
import com.paulmichard.minesweeper.exception.GameNotFoundException;

@ControllerAdvice
public class ExceptionsControllerAdvice {

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException iae) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
	}

	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleNullPointerException(NullPointerException npe) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(npe.getMessage());
	}

	@ExceptionHandler(GameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleGameNotFoundException(GameNotFoundException gnfe) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gnfe.getMessage());
	}

	@ExceptionHandler(CellNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleCellNotFoundException(CellNotFoundException cnfe) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(cnfe.getMessage());
	}

	@ExceptionHandler(GameAlreadyCompletedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity<Object> handleGameAlreadyCompletedException(GameAlreadyCompletedException gace) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(gace.getMessage());
	}
}
