--

DELETE FROM bracketeer_um.user where id < 0;

INSERT INTO bracketeer_um.user (id, username, password_hash, creation_date, last_password_change_date, email, last_update_date)
VALUES (-1, 'bracketeer', '{bcrypt}$2a$10$ehfPb/GiMFugw26ObJzja.iflGYnvtNfLjTwBxgB3eTQMZL16pedW', NOW(), null, 'test@test.com', null)
;

