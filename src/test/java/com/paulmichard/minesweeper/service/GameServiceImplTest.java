package com.paulmichard.minesweeper.service;

import static com.paulmichard.minesweeper.model.GameBoardStatus.IN_PROGRESS;
import static com.paulmichard.minesweeper.model.GameBoardStatus.LOST;
import static com.paulmichard.minesweeper.model.GameBoardStatus.PAUSED;
import static com.paulmichard.minesweeper.model.GameBoardStatus.WON;
import static com.paulmichard.minesweeper.model.GameCellStatus.FLAGGED;
import static com.paulmichard.minesweeper.model.GameCellStatus.HIDDEN;
import static com.paulmichard.minesweeper.model.GameCellStatus.MARKED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;
import com.paulmichard.minesweeper.dao.GameBoardDAO;
import com.paulmichard.minesweeper.exception.GameAlreadyCompletedException;
import com.paulmichard.minesweeper.exception.MineExplodedException;
import com.paulmichard.minesweeper.mother.GameBoardBeanMother;
import com.paulmichard.minesweeper.mother.GameCellBeanMother;
import com.paulmichard.minesweeper.mother.GameRequestMother;

public class GameServiceImplTest {

	@Mock
	private CellService cellService;
	@Mock
	private GameBoardDAO gameBoardDAO;

	@InjectMocks
	private GameServiceImpl gameService;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void tearDown() {
		gameService = null;
	}

	@Test
	public void testCreateGame_success() {
		GameRequest gameRequest = GameRequestMother.basic();
		GameBoardBean expectedGame = GameBoardBeanMother.basic();

		when(cellService.createBoardCells(gameRequest)).thenReturn(GameCellBeanMother.cellsAsList());
		when(gameBoardDAO.saveBoard(any())).thenReturn(expectedGame);

		GameBoardBean createdGame = gameService.createGame(gameRequest);

		assertThat(createdGame).isEqualTo(expectedGame);
	}

	@Test
	public void testCreateGame_failedToCreateCells() {
		GameRequest gameRequest = GameRequestMother.basic();

		when(cellService.createBoardCells(gameRequest)).thenThrow(IllegalArgumentException.class);

		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> gameService.createGame(gameRequest));
		verify(gameBoardDAO, times(0)).saveBoard(any());
	}

	@Test
	public void testLoadGame_success() {
		GameBoardBean expectedGame = GameBoardBeanMother.basic();

		when(gameBoardDAO.fetchBoardById(1L)).thenReturn(expectedGame);

		GameBoardBean game = gameService.loadGame(1L);

		assertThat(game).isEqualTo(expectedGame);
	}

	@Test
	public void testShowCell_success() {
		GameBoardBean expectedGame = GameBoardBeanMother.basic();

		when(gameBoardDAO.fetchBoardById(1L)).thenReturn(expectedGame);
		when(gameBoardDAO.saveBoard(any())).thenReturn(expectedGame);
		doNothing().when(cellService).revealCell(any(), any());

		GameBoardBean game = gameService.showCell(1L, 2L);

		assertThat(game.getStatus()).isEqualTo(IN_PROGRESS);

	}

	@Test
	public void testShowCell_mineExploded() {
		GameBoardBean expectedGame = GameBoardBeanMother.basic();

		when(gameBoardDAO.fetchBoardById(1L)).thenReturn(expectedGame);
		when(gameBoardDAO.saveBoard(any())).thenReturn(expectedGame);
		doThrow(MineExplodedException.class).when(cellService).revealCell(any(), any());

		GameBoardBean game = gameService.showCell(1L, 1L);

		assertThat(game.getStatus()).isEqualTo(LOST);

	}

	@Test
	public void testFlagCellInGame_success_gameWon() {
		GameBoardBean expectedGame = GameBoardBeanMother.basicWithoutCells();
		GameCellBean cell = GameCellBeanMother.basicHiddenWithMine();
		cell.setStatus(FLAGGED);

		expectedGame.setCells(Arrays.asList(cell));

		when(gameBoardDAO.fetchBoardById(1L)).thenReturn(expectedGame);
		when(gameBoardDAO.saveBoard(any())).thenReturn(expectedGame);
		doNothing().when(cellService).flagCell(any(), any());

		GameBoardBean game = gameService.flagCellInGame(1L, 1L);

		assertThat(game.getCells().get(0).getStatus()).isEqualTo(FLAGGED);
		assertThat(game.getStatus()).isEqualTo(WON);
	}

	@Test
	public void testFlagCellInGame_success_gameInProgress() {
		GameBoardBean expectedGame = GameBoardBeanMother.basicWithoutCells();
		GameCellBean cell = GameCellBeanMother.basicHiddenWithMine();
		cell.setStatus(FLAGGED);

		expectedGame.setCells(Arrays.asList(cell, GameCellBeanMother.anotherBasicHiddenWithoutMine()));

		when(gameBoardDAO.fetchBoardById(1L)).thenReturn(expectedGame);
		when(gameBoardDAO.saveBoard(any())).thenReturn(expectedGame);
		doNothing().when(cellService).flagCell(any(), any());

		GameBoardBean game = gameService.flagCellInGame(1L, 1L);

		assertThat(game.getCells().get(0).getStatus()).isEqualTo(FLAGGED);
		assertThat(game.getCells().get(1).getStatus()).isEqualTo(HIDDEN);
		assertThat(game.getStatus()).isEqualTo(IN_PROGRESS);
	}

	@Test
	public void testMarkCellInGame_success_gameInProgress() {
		GameBoardBean expectedGame = GameBoardBeanMother.basicWithoutCells();
		GameCellBean cell = GameCellBeanMother.basicHiddenWithMine();
		cell.setStatus(MARKED);

		expectedGame.setCells(Arrays.asList(cell));

		when(gameBoardDAO.fetchBoardById(1L)).thenReturn(expectedGame);
		when(gameBoardDAO.saveBoard(any())).thenReturn(expectedGame);
		doNothing().when(cellService).markCell(any(), any());

		GameBoardBean game = gameService.markCellInGame(1L, 1L);

		assertThat(game.getCells().get(0).getStatus()).isEqualTo(MARKED);
		assertThat(game.getStatus()).isEqualTo(IN_PROGRESS);
	}

	@Test
	public void testPauseGame_success() {
		GameBoardBean expectedGame = GameBoardBeanMother.basicWithoutCells();

		when(gameBoardDAO.fetchBoardById(1L)).thenReturn(expectedGame);
		when(gameBoardDAO.saveBoard(any())).thenReturn(expectedGame);

		GameBoardBean game = gameService.pauseGame(1L);

		assertThat(game.getStatus()).isEqualTo(PAUSED);
	}

	@Test
	public void testPauseGame_gameFinished_won() {
		GameBoardBean expectedGame = GameBoardBeanMother.basicWithoutCells();
		expectedGame.setStatus(WON);

		when(gameBoardDAO.fetchBoardById(1L)).thenReturn(expectedGame);

		assertThatExceptionOfType(GameAlreadyCompletedException.class).isThrownBy(() -> gameService.pauseGame(1L));
	}

	@Test
	public void testPauseGame_gameFinished_lost() {
		GameBoardBean expectedGame = GameBoardBeanMother.basicWithoutCells();
		expectedGame.setStatus(LOST);

		when(gameBoardDAO.fetchBoardById(1L)).thenReturn(expectedGame);

		assertThatExceptionOfType(GameAlreadyCompletedException.class).isThrownBy(() -> gameService.pauseGame(1L));
	}

	@Test
	public void testResumeGame_success() {
		GameBoardBean expectedGame = GameBoardBeanMother.basicWithoutCells();

		when(gameBoardDAO.fetchBoardById(1L)).thenReturn(expectedGame);
		when(gameBoardDAO.saveBoard(any())).thenReturn(expectedGame);

		GameBoardBean game = gameService.resumeGame(1L);

		assertThat(game.getStatus()).isEqualTo(IN_PROGRESS);
	}
}
