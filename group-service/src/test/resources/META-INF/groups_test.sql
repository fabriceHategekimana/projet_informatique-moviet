DROP TABLE if exists T_groups;
-- need to be in one-line otherwise tons of errors..
CREATE TABLE T_groups (id serial primary key, name varchar(255) not null);
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE T_groups;
INSERT INTO T_groups (name) VALUES ('erwan');
INSERT INTO T_groups (name) VALUES ('mohsen');
INSERT INTO T_groups (name) VALUES ('ethan');
INSERT INTO T_groups (name) VALUES ('raphael');