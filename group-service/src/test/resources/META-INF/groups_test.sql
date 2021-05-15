DROP TABLE if exists T_groups CASCADE;
-- need to be in one-line otherwise tons of errors..
CREATE TABLE T_groups (group_id serial primary key, name varchar(255) not null);
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE T_groups;
INSERT INTO T_groups (name) VALUES ('erwan');
INSERT INTO T_groups (name) VALUES ('mohsen');
INSERT INTO T_groups (name) VALUES ('ethan');
INSERT INTO T_groups (name) VALUES ('raphael');

DROP TABLE if exists T_users CASCADE;
CREATE TABLE T_users (user_id serial primary key, name varchar(255) not null);
-- name not unique
TRUNCATE TABLE T_users;
INSERT INTO T_users (name) VALUES ('user-erwan');
INSERT INTO T_users (name) VALUES ('user-erwan');
INSERT INTO T_users (name) VALUES ('user-ethan');
INSERT INTO T_users (name) VALUES ('user-raphael');

DROP TABLE if exists T_groups_users CASCADE;
CREATE TABLE T_groups_users (group_id int NOT NULL REFERENCES T_users(user_id), user_id int NOT NULL REFERENCES T_groups(group_id));
TRUNCATE TABLE T_groups_users;
INSERT INTO T_groups_users (group_id, user_id) VALUES (1, 1);
INSERT INTO T_groups_users (group_id, user_id) VALUES (1, 2);
INSERT INTO T_groups_users (group_id, user_id) VALUES (2, 3);
INSERT INTO T_groups_users (group_id, user_id) VALUES (2, 4);