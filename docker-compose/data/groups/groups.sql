DROP TABLE if exists T_groups CASCADE;
-- need to be in one-line otherwise tons of errors..
CREATE TABLE T_groups (
    group_id serial primary key,
    name varchar(255) not null,
    admin_id int not null
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
DROP TABLE if exists T_users CASCADE;
CREATE TABLE T_users (
    user_id int primary key
);
TRUNCATE TABLE T_users;
INSERT INTO T_users (user_id) VALUES (1);
INSERT INTO T_users (user_id) VALUES (2);
INSERT INTO T_users (user_id) VALUES (3);
INSERT INTO T_users (user_id) VALUES (4);

DROP TABLE if exists T_groups_users CASCADE;
CREATE TABLE T_groups_users (
    group_id int REFERENCES T_groups(group_id),
    user_id int REFERENCES T_users(user_id)
);
TRUNCATE TABLE T_groups_users;
INSERT INTO T_groups_users (group_id, user_id) VALUES (1, 1);
INSERT INTO T_groups_users (group_id, user_id) VALUES (1, 2);
INSERT INTO T_groups_users (group_id, user_id) VALUES (2, 1);
INSERT INTO T_groups_users (group_id, user_id) VALUES (2, 2);
INSERT INTO T_groups_users (group_id, user_id) VALUES (2, 3);
INSERT INTO T_groups_users (group_id, user_id) VALUES (2, 4);

-- https://www.postgresql.org/docs/current/sql-droptable.html
