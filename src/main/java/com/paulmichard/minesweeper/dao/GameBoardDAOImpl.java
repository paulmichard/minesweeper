package com.paulmichard.minesweeper.dao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.paulmichard.minesweeper.bean.GameBoardBean;
import com.paulmichard.minesweeper.exception.GameNotFoundException;
import com.paulmichard.minesweeper.model.GameBoard;
import com.paulmichard.minesweeper.repository.GameBoardRepository;
import ma.glasnost.orika.MapperFacade;

@Component
@RequiredArgsConstructor
public class GameBoardDAOImpl implements GameBoardDAO {
	private final GameBoardRepository gameBoardRepository;
	private final MapperFacade mapper;

	@Override
	public GameBoardBean saveBoard(GameBoardBean gameBoardBean) {
		return mapper.map(gameBoardRepository.save(mapper.map(gameBoardBean, GameBoard.class)), GameBoardBean.class);
	}

	@Override
	public GameBoardBean fetchBoard(Long id) {
		return gameBoardRepository.findById(id)
				.map(gameBoard -> mapper.map(gameBoard, GameBoardBean.class))
				.orElseThrow(() -> new GameNotFoundException(String.format("Game with id=%s not found", id)));
	}
}
