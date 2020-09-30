INSERT INTO bracketeer_um.role (id, name)
VALUES (1, 'basic_user')
;

INSERT INTO bracketeer_um.privilege (id, name)
VALUES (1, 'user_access');

INSERT INTO bracketeer_um.role2privilege (role_id, privilege_id)
VALUES (1, 1);
