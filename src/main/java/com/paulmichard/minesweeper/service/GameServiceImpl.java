package com.paulmichard.minesweeper.service;

import static com.paulmichard.minesweeper.model.GameBoardStatus.IN_PROGRESS;
import static com.paulmichard.minesweeper.model.GameBoardStatus.LOST;
import static com.paulmichard.minesweeper.model.GameBoardStatus.NEW;
import static com.paulmichard.minesweeper.model.GameBoardStatus.PAUSED;
import static com.paulmichard.minesweeper.model.GameBoardStatus.WON;
import static com.paulmichard.minesweeper.model.GameCellStatus.FLAGGED;
import static com.paulmichard.minesweeper.model.GameCellStatus.HIDDEN;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;
import com.paulmichard.minesweeper.dao.GameBoardDAO;
import com.paulmichard.minesweeper.exception.GameAlreadyCompletedException;
import com.paulmichard.minesweeper.exception.GameNotFoundException;
import com.paulmichard.minesweeper.exception.MineExplodedException;
import com.paulmichard.minesweeper.model.GameBoardStatus;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

	private final CellService cellService;
	private final GameBoardDAO gameBoardDAO;

	@Override
	public GameBoardBean createGame(GameRequest gameRequest) {
		log.info("Starting to create a new Board");
		List<GameCellBean> cells = cellService.createBoardCells(gameRequest);

		GameBoardBean newGameBoard = gameBoardDAO.saveBoard(buildGameBoard(gameRequest, cells));
		log.info("Board created successfully with id={}", newGameBoard.getId());
		return newGameBoard;
	}

	@Override
	public GameBoardBean loadGame(Long id) {
		return gameBoardDAO.fetchBoardById(id);
	}

	@Override
	public GameBoardBean showCell(Long id, Long cellId) {
		GameBoardBean game = findActiveGameBoard(id);
		game.setStatus(IN_PROGRESS);

		try {
			cellService.revealCell(game, cellId);

			checkGame(game);
		} catch (MineExplodedException mee) {
			log.info("Game with id={} as finished as a mine has exploded", game.getId());
			game.setStatus(LOST);
		}

		return gameBoardDAO.saveBoard(game);
	}

	@Override
	public GameBoardBean flagCellInGame(Long id, Long cellId) {
		GameBoardBean game = findActiveGameBoard(id);
		game.setStatus(IN_PROGRESS);

		cellService.flagCell(game, cellId);
		checkGame(game);

		return gameBoardDAO.saveBoard(game);
	}

	@Override
	public GameBoardBean markCellInGame(Long id, Long cellId) {
		GameBoardBean game = findActiveGameBoard(id);
		game.setStatus(IN_PROGRESS);

		cellService.markCell(game, cellId);

		return gameBoardDAO.saveBoard(game);
	}

	@Override
	public GameBoardBean pauseGame(Long id) {
		return updateGameStatus(id, PAUSED);
	}

	@Override
	public GameBoardBean resumeGame(Long id) {
		return updateGameStatus(id, IN_PROGRESS);
	}

	private void checkGame(GameBoardBean game) {
		// The game is won when all remaining cells are mines, and are either hidden or marked
		if (game.getCells().stream()
				.filter(gameCellBean -> HIDDEN.equals(gameCellBean.getStatus())
						|| FLAGGED.equals(gameCellBean.getStatus()))
				.allMatch(GameCellBean::isHasMine)) {
			game.setStatus(WON);
		}
	}

	private GameBoardBean updateGameStatus(Long id, GameBoardStatus status) {
		GameBoardBean game = gameBoardDAO.fetchBoardById(id);
		if (LOST.equals(game.getStatus()) || WON.equals(game.getStatus())) {
			throw new GameAlreadyCompletedException(String.format("Game with id=%s is already finished, the status cannot be changed to %s", id, status.name()));
		}
		game.setStatus(status);
		return gameBoardDAO.saveBoard(game);
	}

	private GameBoardBean findActiveGameBoard(Long id) {
		GameBoardBean game = gameBoardDAO.fetchBoardById(id);
		if (NEW.equals(game.getStatus()) || IN_PROGRESS.equals(game.getStatus())) {
			return game;
		}
		throw new GameNotFoundException(String.format("Game with id=%s not found or is not active to play", id));
	}

	private GameBoardBean buildGameBoard(GameRequest gameRequest, List<GameCellBean> cells) {
		return GameBoardBean.builder()
				.mines(gameRequest.getMines())
				.columns(gameRequest.getColumns())
				.rows(gameRequest.getRows())
				.status(NEW)
				.cells(cells)
				.build();
	}
}
