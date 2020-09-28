DELETE
FROM users_roles;
DELETE
FROM roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (login, name, password)
VALUES ('userlogin', 'Some User', 'password'),
       ('adminlogin', 'Admin', 'admin');

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN'),
       ('OPERATOR'),
       ('ANALYST');

INSERT INTO users_roles (user_login, role_id)
VALUES ('adminlogin', 100001),
       ('adminlogin', 100002),
       ('userlogin', 100000),
       ('userlogin', 100003);





