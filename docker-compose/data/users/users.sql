  
DROP TABLE if exists T_users;
-- need to be in one-line otherwise tons of errors..
CREATE TABLE T_users (
    id varchar(255) primary key,
    username varchar(255) not null
);
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE T_users;
INSERT INTO T_users (id, username) VALUES ('1', 'Stephane');
INSERT INTO T_users (id, username) VALUES ('2', 'Fabrice');
INSERT INTO T_users (id, username) VALUES ('3', 'mohsen');
INSERT INTO T_users (id, username) VALUES ('a', 'ethan');
INSERT INTO T_users (id, username) VALUES ('b', 'raphael');
INSERT INTO T_users (id, username) VALUES ('c', 'erwan');
