package com.paulmichard.minesweeper.service;

import static com.paulmichard.minesweeper.model.GameCellStatus.HIDDEN;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;

@Service
@RequiredArgsConstructor
@Slf4j
public class CellServiceImpl implements CellService {
	@Override
	public List<GameCellBean> createBoardCells(GameRequest gameRequest) {
		// Validate cell related data
		validateGameSetup(gameRequest);

		// Build all the cells involved in this game
		List<GameCellBean> gameCellBeanList = buildBoardInitialCells(gameRequest);

		placeRandomMines(gameRequest.getMines(), gameCellBeanList);
		return gameCellBeanList;
	}

	/**
	 * Validates the data received to create the cells
	 *
	 * @param gameRequest
	 */
	private void validateGameSetup(GameRequest gameRequest) {
		// Check for blank values
		Preconditions.checkNotNull(gameRequest, "Game data is required to create the cells");
		Preconditions.checkNotNull(gameRequest.getRows(), "Rows amount is required to generate Board Cells");
		Preconditions.checkNotNull(gameRequest.getColumns(), "Columns amount is required to generate Board Cells");
		Preconditions.checkNotNull(gameRequest.getMines(), "Mines amount is required to generate Board Cells");

		// Check for invalid values
		Preconditions.checkArgument(gameRequest.getRows() >= 1, "Rows amount must be at least 1");
		Preconditions.checkArgument(gameRequest.getColumns() >= 1, "Columns amount must be at least 1");
		Preconditions.checkArgument(gameRequest.getMines() >= 1, "Mines amount must be at least 1");
		Preconditions.checkArgument(gameRequest.getRows() * gameRequest.getColumns() >= gameRequest.getMines(), "Mines amount cannot be greater than the board size");
	}

	/**
	 * Creates a list of cells for a given board size
	 *
	 * @param gameRequest
	 * @return
	 */
	private List<GameCellBean> buildBoardInitialCells(GameRequest gameRequest) {
		List<GameCellBean> cellsList = new ArrayList<>();

		Long totalColumns = gameRequest.getColumns();
		Long totalRows = gameRequest.getRows();

		for (long row = 0; row < totalRows; row++) {
			for (long column = 0; column < totalColumns; column++) {
				cellsList.add(buildCell(row, column));
			}
		}

		return cellsList;
	}

	/**
	 * Places random mines based on an amount and a list of cells
	 *
	 * @param totalMines
	 * @param cells
	 */
	private void placeRandomMines(Long totalMines, List<GameCellBean> cells) {
		long placedMines = 0;

		while (placedMines < totalMines) {
			int randomMine = ThreadLocalRandom.current().nextInt(cells.size() - 1);
			GameCellBean bean = cells.get(randomMine);
			if (!bean.isHasMine()) {
				bean.setHasMine(true);
				placedMines++;
				log.info("Placed mine in row={}, column={}", bean.getRowPosition(), bean.getColumnPosition());
			}
		}

	}

	/**
	 * Builds a new Cell given a row and a column position
	 *
	 * @param row
	 * @param column
	 * @return
	 */
	private GameCellBean buildCell(Long row, Long column) {
		return GameCellBean.builder()
				.rowPosition(row)
				.columnPosition(column)
				.hasMine(false)
				.status(HIDDEN)
				.build();
	}
}
