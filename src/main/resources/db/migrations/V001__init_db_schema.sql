create TABLE `game_boards`
(
    `id`                BIGINT NOT NULL AUTO_INCREMENT,
    `created_on`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `rows_amount`       BIGINT NOT NULL,
    `columns_amount`    BIGINT NOT NULL,
    `mines_amount`      BIGINT NOT NULL,
    `game_status`       VARCHAR(15) NOT NULL DEFAULT 'NEW',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

create TABLE `game_cells`
(
    `id`                BIGINT NOT NULL AUTO_INCREMENT,
    `created_on`        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified`     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `row_position`      BIGINT NOT NULL,
    `column_position`   BIGINT NOT NULL,
    `has_mine`          BIT(1) NOT NULL DEFAULT 0,
    `game_board_id`     BIGINT,
    `cell_status`       VARCHAR(15) NOT NULL DEFAULT 'HIDDEN',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
