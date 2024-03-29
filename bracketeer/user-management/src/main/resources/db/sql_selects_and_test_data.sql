select * from bracketeer_um.user;

select * from bracketeer_um.role;
select * from bracketeer_um.user2role;

SELECT * FROM bracketeer_um.privilege;
SELECT * FROM bracketeer_um.role2privilege;


DELETE FROM bracketeer_um.user2role WHERE role_id != 0;
DELETE FROM bracketeer_um.role2privilege WHERE role_id != 0;
DELETE FROM bracketeer_um.user WHERE id != 0;
DELETE FROM bracketeer_um.role WHERE id != 0;
DELETE FROM bracketeer_um.privilege WHERE id != 0;


INSERT INTO `bracketeer_um.user`
VALUES (-1,'bracketeer','{bcrypt}$2a$10$ehfPb/GiMFugw26ObJzja.iflGYnvtNfLjTwBxgB3eTQMZL16pedW','2020-09-26 20:41:22',NULL,'test@test.com',NULL)
;

INSERT INTO bracketeer_um.role (id, name)
VALUES (-1, 'basic_user')
;
INSERT INTO bracketeer_um.user2role (user_id, role_id)
VALUES (-1, -1)
;

INSERT INTO bracketeer_um.privilege (id, name)
VALUES (-1, 'user_access')
;
INSERT INTO bracketeer_um.role2privilege (role_id, privilege_id)
VALUES (-1, -1)
;

SELECT *
FROM bracketeer_um.user u
join bracketeer_um.role ro on (select role_id from bracketeer_um.user2role where user_id = u.id) = ro.id
join bracketeer_um.privilege p on (select privilege_id from bracketeer_um.role2privilege where role_id = ro.id) = p.id
;