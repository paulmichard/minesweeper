package com.paulmichard.minesweeper.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;
import com.paulmichard.minesweeper.dao.GameBoardDAO;
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
		return gameBoardDAO.fetchBoard(id);
	}

	@Override
	public GameBoardBean showCell(Long id, Long cellId) {
		GameBoardBean game = findActiveGameBoard(id);
		game.setStatus(GameBoardStatus.IN_PROGRESS);

		try {
			cellService.revealCell(game, cellId);
		} catch (MineExplodedException mee) {
			log.info("Game with id={} as finished as a mine has exploded", game.getId());
			game.setStatus(GameBoardStatus.LOST);
		}

		return gameBoardDAO.saveBoard(game);
	}

	@Override
	public GameBoardBean flagCellInGame(Long id, Long cellId) {
		GameBoardBean game = findActiveGameBoard(id);
		game.setStatus(GameBoardStatus.IN_PROGRESS);

		cellService.flagCell(game, cellId);

		return gameBoardDAO.saveBoard(game);
	}

	@Override
	public GameBoardBean markCellInGame(Long id, Long cellId) {
		GameBoardBean game = findActiveGameBoard(id);
		game.setStatus(GameBoardStatus.IN_PROGRESS);

		cellService.markCell(game, cellId);

		return gameBoardDAO.saveBoard(game);
	}

	private GameBoardBean findActiveGameBoard(Long id) {
		GameBoardBean game = gameBoardDAO.fetchBoard(id);
		if (GameBoardStatus.NEW.equals(game.getStatus()) || GameBoardStatus.IN_PROGRESS.equals(game.getStatus())) {
			return game;
		}
		throw new GameNotFoundException(String.format("Game with id=%s not found or is not active to play", id));
	}

	private GameBoardBean buildGameBoard(GameRequest gameRequest, List<GameCellBean> cells) {
		return GameBoardBean.builder()
				.mines(gameRequest.getMines())
				.columns(gameRequest.getColumns())
				.rows(gameRequest.getRows())
				.status(GameBoardStatus.NEW)
				.cells(cells)
				.build();
	}
}
