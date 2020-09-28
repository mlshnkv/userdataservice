DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq AS INTEGER START WITH 100000;

CREATE TABLE users
(
    login    VARCHAR(255) PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE roles
(
    id   INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    name VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (name)
);

CREATE TABLE users_roles
(
    user_login VARCHAR(255) DEFAULT NULL,
    role_id INTEGER DEFAULT NULL,
--     FOREIGN KEY (user_id) REFERENCES users (login),
--     FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT has_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT has_user FOREIGN KEY (user_login) REFERENCES users (login) ON DELETE CASCADE ON UPDATE CASCADE
)