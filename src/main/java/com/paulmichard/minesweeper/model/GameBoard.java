package com.paulmichard.minesweeper.model;

import static javax.persistence.EnumType.STRING;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "game_boards")
public class GameBoard extends TimestampedEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Column(name = "rows_amount", nullable = false)
	private Long rows;

	@Column(name = "columns_amount", nullable = false)
	private Long columns;

	@Column(name = "mines_amount", nullable = false)
	private Long mines;

	@Column(name = "game_status", nullable = false,length = 15)
	@Enumerated(STRING)
	private GameBoardStatus status;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "game_board_id")
	private List<GameCell> cells;
}
