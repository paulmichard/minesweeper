package com.paulmichard.minesweeper.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.paulmichard.minesweeper.model.GameBoardStatus;
import com.paulmichard.minesweeper.model.GameCell;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GameBoardBean {
	private Long id;
	private Long rows;
	private Long columns;
	private Long mines;
	private GameBoardStatus status;
	private List<GameCell> cells;
}
