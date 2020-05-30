package com.paulmichard.minesweeper.model;

import static javax.persistence.EnumType.STRING;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "game_cells")
public class GameCell extends TimestampedEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;

	@Column(name = "row_position", nullable = false)
	private Long rowPosition;

	@Column(name = "column_position", nullable = false)
	private Long columnPosition;

	@Column(name = "has_mine", nullable = false)
	private boolean hasMine;

	@Enumerated(STRING)
	@Column(name = "cell_status", nullable = false, length = 15)
	private GameCellStatus status;
}
