DROP TABLE if exists T_users;
-- need to be in one-line otherwise tons of errors..
CREATE TABLE T_users (id serial primary key, firstName varchar(255) not null, lastName varchar(255) not null, age varchar(255) not null);
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE T_users;
INSERT INTO T_users (name) VALUES ('Stephane', 'Nguyen'       , '20');
INSERT INTO T_users (name) VALUES ('Fabrice' , 'Hategekimana' , '20');
INSERT INTO T_users (name) VALUES ('mohsen'  , 'Hassan Naeini', '20');
INSERT INTO T_users (name) VALUES ('ethan'   , 'Icet'         , '20');
INSERT INTO T_users (name) VALUES ('raphael' , 'Maggio Aprile', '20');
INSERT INTO T_users (name) VALUES ('erwan'   , 'don'          , '20');
