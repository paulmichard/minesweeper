package com.paulmichard.minesweeper.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.exception.GameNotFoundException;
import com.paulmichard.minesweeper.model.GameBoard;
import com.paulmichard.minesweeper.mother.GameBoardBeanMother;
import com.paulmichard.minesweeper.mother.GameBoardMother;
import com.paulmichard.minesweeper.repository.GameBoardRepository;
import ma.glasnost.orika.MapperFacade;

public class GameBoardDAOImplTest {

	@Mock
	private GameBoardRepository gameBoardRepository;
	@Mock
	private MapperFacade facade;

	@InjectMocks
	private GameBoardDAOImpl gameBoardDAO;

	@BeforeMethod
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@AfterMethod
	public void tearDown() {
		gameBoardDAO = null;
	}

	@Test
	public void testSaveBoard_success() {
		GameBoardBean expectedBean = GameBoardBeanMother.basicWithoutCells();
		GameBoard game = GameBoardMother.basicWithoutCells();

		when(gameBoardRepository.save(game)).thenReturn(game);
		when(facade.map(expectedBean, GameBoard.class)).thenReturn(game);
		when(facade.map(game, GameBoardBean.class)).thenReturn(expectedBean);

		GameBoardBean savedBean = gameBoardDAO.saveBoard(expectedBean);

		assertThat(savedBean).isEqualTo(expectedBean);
	}

	@Test
	public void testFetchBoardById_success() {
		GameBoardBean expectedBean = GameBoardBeanMother.basicWithoutCells();
		GameBoard game = GameBoardMother.basicWithoutCells();

		when(gameBoardRepository.findById(1L)).thenReturn(Optional.of(game));
		when(facade.map(game, GameBoardBean.class)).thenReturn(expectedBean);

		GameBoardBean fetchedBean = gameBoardDAO.fetchBoardById(1L);

		assertThat(fetchedBean).isEqualTo(expectedBean);
	}

	@Test
	public void testFetchBoardById_notFound() {
		when(gameBoardRepository.findById(1L)).thenReturn(Optional.empty());

		assertThatExceptionOfType(GameNotFoundException.class).isThrownBy(() -> gameBoardDAO.fetchBoardById(1L));
	}
}
