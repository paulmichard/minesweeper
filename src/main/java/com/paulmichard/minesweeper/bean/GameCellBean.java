package com.paulmichard.minesweeper.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.paulmichard.minesweeper.model.GameCellStatus;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GameCellBean {
	private Long id;
	private Long rowPosition;
	private Long columnPosition;
	private boolean hasMine;
	private GameCellStatus status;
}
