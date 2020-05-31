package com.paulmichard.minesweeper.service;

import static com.paulmichard.minesweeper.model.GameCellStatus.FLAGGED;
import static com.paulmichard.minesweeper.model.GameCellStatus.HIDDEN;
import static com.paulmichard.minesweeper.model.GameCellStatus.MARKED;
import static com.paulmichard.minesweeper.model.GameCellStatus.VISIBLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import java.util.stream.Collectors;

import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;
import com.paulmichard.minesweeper.exception.CellNotFoundException;
import com.paulmichard.minesweeper.exception.MineExplodedException;
import com.paulmichard.minesweeper.mother.GameBoardBeanMother;
import com.paulmichard.minesweeper.mother.GameRequestMother;

public class CellServiceImplTest {

	@InjectMocks
	private CellServiceImpl cellService;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreateBoardCells_success() {
		GameRequest game = GameRequestMother.basic();

		List<GameCellBean> createdCells = cellService.createBoardCells(game);

		assertThat(createdCells)
				.hasSize((int) (game.getColumns() * game.getRows()));
		assertThat(createdCells.stream()
				.filter(GameCellBean::isHasMine)
				.collect(Collectors.toList()))
				.hasSize(game.getMines().intValue());
		assertThat(createdCells.stream()
				.allMatch(gameCellBean -> HIDDEN.equals(gameCellBean.getStatus()))).isTrue();
	}

	@Test
	public void testCreateBoardCells_invalidParameters() {
		final GameRequest game = GameRequestMother.basic();

		game.setRows(0L);

		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> cellService.createBoardCells(game)).withMessage("Rows amount must be at least 1");

		game.setRows(3L);
		game.setColumns(0L);

		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> cellService.createBoardCells(game)).withMessage("Columns amount must be at least 1");

		game.setMines(0L);
		game.setColumns(3L);

		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> cellService.createBoardCells(game)).withMessage("Mines amount must be at least 1");

		game.setMines(8L);
		game.setColumns(1L);

		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> cellService.createBoardCells(game)).withMessage("Mines amount cannot be greater than the board size");

		game.setRows(null);

		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> cellService.createBoardCells(game)).withMessage("Rows amount is required to generate Board Cells");

		game.setRows(3L);
		game.setColumns(null);

		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> cellService.createBoardCells(game)).withMessage("Columns amount is required to generate Board Cells");

		game.setColumns(3L);
		game.setMines(null);

		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> cellService.createBoardCells(game)).withMessage("Mines amount is required to generate Board Cells");

		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> cellService.createBoardCells(null)).withMessage("Game data is required to create the cells");
	}

	@Test
	public void testFlagCell_success() {
		GameBoardBean game = GameBoardBeanMother.basic();

		cellService.flagCell(game, 2L);

		assertThat(game.getCells().stream()
				.filter(gameCellBean -> gameCellBean.getId().equals(2L))
				.findFirst()
				.get()
				.getStatus())
				.isEqualTo(FLAGGED);
	}

	@Test
	public void testMarkCell_success() {
		GameBoardBean game = GameBoardBeanMother.basic();

		cellService.markCell(game, 2L);

		assertThat(game.getCells().stream()
				.filter(gameCellBean -> gameCellBean.getId().equals(2L))
				.findFirst()
				.get()
				.getStatus())
				.isEqualTo(MARKED);
	}

	@Test
	public void testRevealCell_success() {
		GameBoardBean game = GameBoardBeanMother.basic();

		cellService.revealCell(game, 2L);

		assertThat(game.getCells().stream()
				.filter(gameCellBean -> gameCellBean.getId().equals(2L))
				.findFirst()
				.get()
				.getStatus())
				.isEqualTo(VISIBLE);
	}

	@Test
	public void testRevealCell_noCellFound() {
		GameBoardBean game = GameBoardBeanMother.basicWithoutCells();

		assertThatExceptionOfType(CellNotFoundException.class).isThrownBy(() -> cellService.revealCell(game, 8L));
	}

	@Test
	public void testRevealCell_mineExploded() {
		GameBoardBean game = GameBoardBeanMother.basic();

		assertThatExceptionOfType(MineExplodedException.class).isThrownBy(() -> cellService.revealCell(game, 1L));
	}
}
