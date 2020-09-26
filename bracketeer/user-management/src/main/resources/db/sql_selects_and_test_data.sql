select * from bracketeer_um.user;

select * from bracketeer_um.role;

select * from bracketeer_um.user2role;


INSERT INTO `bracketeer_um.user`
VALUES (-1,'bracketeer','{bcrypt}$2a$10$ehfPb/GiMFugw26ObJzja.iflGYnvtNfLjTwBxgB3eTQMZL16pedW','2020-09-26 20:41:22',NULL,'test@test.com',NULL)
;

INSERT INTO bracketeer_um.role (id, name)
VALUES (-1, 'basic_user')
;

INSERT INTO bracketeer_um.user2role (user_id, role_id)
VALUES (-1, -1)
;

