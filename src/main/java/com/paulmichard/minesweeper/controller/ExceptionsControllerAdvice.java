package com.paulmichard.minesweeper.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsControllerAdvice {

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException iae) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(iae.getMessage());
	}
}
