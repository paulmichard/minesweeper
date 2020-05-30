package com.paulmichard.minesweeper.exception;

public class CellNotFoundException extends RuntimeException {
	public CellNotFoundException(String message) {
		super(message);
	}
}
