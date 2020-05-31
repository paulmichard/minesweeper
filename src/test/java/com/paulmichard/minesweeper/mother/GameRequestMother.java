package com.paulmichard.minesweeper.mother;

import com.paulmichard.minesweeper.bean.GameRequest;

public class GameRequestMother {
	public static GameRequest basic() {
		return GameRequest.builder()
				.rows(2L)
				.columns(2L)
				.mines(2L)
				.build();
	}
}
