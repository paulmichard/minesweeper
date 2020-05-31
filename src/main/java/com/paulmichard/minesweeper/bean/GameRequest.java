package com.paulmichard.minesweeper.bean;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameRequest {
	@NotNull(message = "Rows amount cannot be null")
	@Min(value = 1, message = "Rows amount must be greater than 0")
	private Long rows;
	@NotNull(message = "Columns amount cannot be null")
	@Min(value = 1, message = "Columns amount must be greater than 0")
	private Long columns;
	@NotNull(message = "Mines amount cannot be null")
	private Long mines;
}
