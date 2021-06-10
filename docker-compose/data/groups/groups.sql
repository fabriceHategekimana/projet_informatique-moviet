DROP TABLE if exists T_groups_users_keywords CASCADE;
DROP TABLE if exists T_groups_users_genres CASCADE;
DROP TABLE if exists T_groups_users CASCADE;
DROP TABLE if exists T_groups CASCADE;
DROP TABLE if exists T_users CASCADE;
DROP TYPE if exists status_type;
-- choosing : for short term preferences, ready: before vote
CREATE TYPE status_type AS ENUM ('CHOOSING','READY', 'VOTING', 'DONE');
-- need to be in one-line otherwise tons of errors..
-- best website to test SQL online ! https://sqliteonline.com
CREATE TABLE T_groups (
    group_id serial primary key,
    name varchar(255) not null,
    admin_id varchar(255) not null,
    group_status status_type DEFAULT 'CHOOSING'
);
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE T_groups;
INSERT INTO T_groups (name, admin_id) VALUES ('erwan', 1);
INSERT INTO T_groups (name, admin_id) VALUES ('mohsen', 1);
INSERT INTO T_groups (name, admin_id) VALUES ('ethan', 1);
INSERT INTO T_groups (name, admin_id) VALUES ('raphael', 1);

-- no auto id incremented
CREATE TABLE T_users (
    user_id varchar(255) primary key
);
TRUNCATE TABLE T_users;
INSERT INTO T_users (user_id) VALUES (1);
INSERT INTO T_users (user_id) VALUES (2);
INSERT INTO T_users (user_id) VALUES (3);
INSERT INTO T_users (user_id) VALUES (4);

-- DROP TYPE if exists status_type;
-- choosing : for short term preferences, ready: before vote
-- CREATE TYPE status_type AS ENUM ('CHOOSING','READY', 'VOTING', 'DONE');  -- change of status changes in the same order
-- cannot have users ready while others are voting..
-- status status_type not null,
CREATE TABLE T_groups_users (
    id serial primary key,
    group_id int REFERENCES T_groups(group_id),
    user_id varchar(255) REFERENCES T_users(user_id),
    user_status status_type NOT NULL DEFAULT 'CHOOSING',
    year_from int,
    year_to int
);
-- user_status is modified through the class User by using @SecondaryTable..
-- https://www.baeldung.com/jpa-mapping-single-entity-to-multiple-tables
TRUNCATE TABLE T_groups_users;
INSERT INTO T_groups_users (group_id, user_id, user_status) VALUES (1, 1, 'CHOOSING');
INSERT INTO T_groups_users (group_id, user_id, user_status) VALUES (1, 2, 'READY');
INSERT INTO T_groups_users (group_id, user_id, user_status) VALUES (2, 1, 'CHOOSING');
INSERT INTO T_groups_users (group_id, user_id, user_status) VALUES (2, 2, 'CHOOSING');
INSERT INTO T_groups_users (group_id, user_id, user_status) VALUES (2, 3, 'READY');
INSERT INTO T_groups_users (group_id, user_id, user_status) VALUES (2, 4, 'READY');

-- https://www.postgresql.org/docs/current/sql-droptable.html

-- --------------------------------------------------------------------------------
-- TABLES FOR ELEMENT COLLECTION IN JPA
-- https://javabydeveloper.com/mapping-collection-of-basic-value-types-jpa-with-hibernate/
CREATE TABLE T_groups_users_keywords (
    group_id int NOT NULL REFERENCES T_groups(group_id),
    user_id varchar(255) NOT NULL REFERENCES T_users(user_id),
    keyword_id int NOT NULL,
    primary key (group_id, user_id, keyword_id)
);
TRUNCATE TABLE T_groups_users_keywords;

INSERT INTO T_groups_users_keywords (group_id, user_id, keyword_id) VALUES (1, 1, 9715);
INSERT INTO T_groups_users_keywords (group_id, user_id, keyword_id) VALUES (1, 1, 265894);
INSERT INTO T_groups_users_keywords (group_id, user_id, keyword_id) VALUES (2, 1, 265894);

CREATE TABLE T_groups_users_genres (
    group_id int NOT NULL REFERENCES T_groups(group_id),
    user_id varchar(255) NOT NULL REFERENCES T_users(user_id),
    genre_id int NOT NULL,
    primary key (group_id, user_id, genre_id)
);
TRUNCATE TABLE T_groups_users_genres;

INSERT INTO T_groups_users_genres (group_id, user_id, genre_id) VALUES (1, 1, 878);
INSERT INTO T_groups_users_genres (group_id, user_id, genre_id) VALUES (1, 1, 18);
INSERT INTO T_groups_users_genres (group_id, user_id, genre_id) VALUES (2, 1, 878);
