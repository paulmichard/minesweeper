package com.paulmichard.minesweeper.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.bean.GameCellBean;
import com.paulmichard.minesweeper.bean.GameRequest;
import com.paulmichard.minesweeper.dao.GameBoardDAO;
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
	public GameBoardBean flagCellInGame(Long id, Long cellId) {
		GameBoardBean game = gameBoardDAO.fetchBoard(id);

		cellService.flagCell(game, cellId);

		return gameBoardDAO.saveBoard(game);
	}

	@Override
	public GameBoardBean markCellInGame(Long id, Long cellId) {
		GameBoardBean game = gameBoardDAO.fetchBoard(id);

		cellService.markCell(game, cellId);

		return gameBoardDAO.saveBoard(game);
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
