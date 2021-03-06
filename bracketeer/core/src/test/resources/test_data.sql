--
DELETE FROM match_result where match_id < 0;
DELETE FROM participant where id < 0;
DELETE FROM `match` where id < 0;
DELETE FROM round where id < 0;
DELETE FROM tournament where id < 0;


INSERT INTO `bracketeer`.`tournament`
(`id`,`code`,`name`,`type`)
VALUES (-1, 'abc123', 'test Db Name', 'SWISS');

INSERT INTO `bracketeer`.`participant`
(`id`,`name`,`code`,`starting_score`,`starting_byes`,`tournament_id`)
VALUES (-1, 'aaron','1', 0, 0, -1);

INSERT INTO `bracketeer`.`participant`
(`id`,`name`,`code`,`starting_score`,`starting_byes`,`tournament_id`)
VALUES (-2, 'berenika', '2', 0, 0, -1);

INSERT INTO `bracketeer`.`participant`
(`id`,`name`,`code`,`starting_score`,`starting_byes`,`tournament_id`)
VALUES (-3, 'camille', '3', 0, 0, -1);

INSERT INTO `bracketeer`.`participant`
(`id`,`name`,`code`,`starting_score`,`starting_byes`,`tournament_id`)
VALUES (-4, 'derek', '4', 0, 0, -1);

INSERT INTO `bracketeer`.`round`
(`id`, `tournament_id`, `round_number`, `best_of`)
VALUES (-1, -1, 1, 3);

INSERT INTO `bracketeer`.`match`
(`id`, `is_bye`, `round_id`, `match_number`)
VALUES
(-1, 0, -1, 1);

INSERT INTO `bracketeer`.`match`
(`id`, `is_bye`, `round_id`, `match_number`)
VALUES
(-2, 0, -1, 2);

INSERT INTO `bracketeer`.`match_result`
(`participant_id`, `match_id`, `games_won`)
VALUES
(-1, -1, 2);

INSERT INTO `bracketeer`.`match_result`
(`participant_id`, `match_id`, `games_won`)
VALUES
(-2, -1, 0);

INSERT INTO `bracketeer`.`match_result`
(`participant_id`, `match_id`, `games_won`)
VALUES
(-3, -2, 1);

INSERT INTO `bracketeer`.`match_result`
(`participant_id`, `match_id`, `games_won`)
VALUES
(-4, -2, 2);