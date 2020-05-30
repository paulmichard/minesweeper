package com.paulmichard.minesweeper.service;

import static com.paulmichard.minesweeper.model.GameCellStatus.HIDDEN;
import static com.paulmichard.minesweeper.model.GameCellStatus.VISIBLE;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;
import com.paulmichard.minesweeper.exception.CellNotFoundException;
import com.paulmichard.minesweeper.exception.MineExplodedException;
import com.paulmichard.minesweeper.model.GameCellStatus;

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

	@Override
	public void flagCell(GameBoardBean game, Long cellId) {
		updateCellStatus(game, cellId, GameCellStatus.FLAGGED);
	}

	@Override
	public void markCell(GameBoardBean game, Long cellId) {
		updateCellStatus(game, cellId, GameCellStatus.MARKED);
	}

	@Override
	public void revealCell(GameBoardBean game, Long cellId) {
		GameCellBean cellToReveal = game.getCells().stream()
				.filter(gameCellBean -> gameCellBean.getId().equals(cellId))
				.findFirst()
				.orElseThrow(() -> new CellNotFoundException(String.format("No cell with id=%s found in game=%s", cellId, game.getId())));

		cellToReveal.setStatus(GameCellStatus.VISIBLE);

		checkForMine(game, cellToReveal);
	}

	/**
	 * Checks if the selected cell as a mine and, if not, tries to reveal the surrounding cells
	 *
	 * @param game
	 * @param cell
	 */
	private void checkForMine(GameBoardBean game, GameCellBean cell) {
		if (cell.isHasMine()) {
			throw new MineExplodedException(String.format("Mine has exploded in cell located at row=%s and column=%s!!", cell.getRowPosition(), cell.getColumnPosition()));
		}
		checkAdjacentCells(game, cell);
	}

	/**
	 * Checks the surroundings for mines or to reveal all the cells
	 *
	 * @param game
	 * @param cell
	 */
	private void checkAdjacentCells(GameBoardBean game, GameCellBean cell) {
		//When a cell with no adjacent mines is revealed, all adjacent squares will be revealed (and repeat)
		cell.setStatus(VISIBLE);
		// First, search all the cells that are still playable
		List<GameCellBean> adjacentCells = getAdjacentPlayableCells(game, cell);

		// Next, check how mine mines are next to the first cell
		long adjacentMines = adjacentCells.stream()
				.filter(GameCellBean::isHasMine)
				.count();

		if (adjacentMines == 0) {
			// If there aren't any mines, then we should reveal all the cells
			adjacentCells.forEach(gameCellBean -> checkAdjacentCells(game, gameCellBean));
		} else {
			cell.setAdjacentMines(adjacentMines);
		}
	}

	/**
	 * Returns a list of all the cells that are still possible to play
	 *
	 * @param game
	 * @param cell
	 * @return
	 */
	private List<GameCellBean> getAdjacentPlayableCells(GameBoardBean game, GameCellBean cell) {
		return game.getCells().stream()
				.filter(gameCellBean -> gameCellBean.getRowPosition().equals(cell.getRowPosition())
						|| gameCellBean.getRowPosition().equals(cell.getRowPosition() + 1)
						|| gameCellBean.getRowPosition().equals(cell.getRowPosition() - 1))
				.filter(gameCellBean -> gameCellBean.getColumnPosition().equals(cell.getColumnPosition())
						|| gameCellBean.getColumnPosition().equals(cell.getColumnPosition() + 1)
						|| gameCellBean.getColumnPosition().equals(cell.getColumnPosition() - 1))
				.filter(gameCellBean -> !VISIBLE.equals(gameCellBean.getStatus()))
				.collect(Collectors.toList());
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

	/**
	 * Updates the status of a given cell in a board
	 *
	 * @param game
	 * @param cellId
	 * @param status
	 */
	private void updateCellStatus(GameBoardBean game, Long cellId, GameCellStatus status) {
		game.getCells().stream()
				.filter(gameCellBean -> gameCellBean.getId().equals(cellId))
				.findFirst()
				.orElseThrow(() -> new CellNotFoundException(String.format("No cell with id=%s found in game=%s", cellId, game.getId())))
				.setStatus(status);
	}
}
