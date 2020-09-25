DELETE
FROM users_roles;
DELETE
FROM roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (login, name, password)
VALUES ('user@yandex.ru', 'Some User', 'password'),
       ('admin@gmail.com', 'Admin', 'admin');

INSERT INTO roles (name)
VALUES ('USER'),
       ('ADMIN'),
       ('OPERATOR'),
       ('ANALYST');

INSERT INTO users_roles (user_login, role_id)
VALUES ('admin@gmail.com', 100001),
       ('admin@gmail.com', 100002),
       ('user@yandex.ru', 100000),
       ('user@yandex.ru', 100003);





