package com.paulmichard.minesweeper.exception;

public class GameAlreadyCompletedException extends RuntimeException {
	public GameAlreadyCompletedException(String message) {
		super(message);
	}
}
