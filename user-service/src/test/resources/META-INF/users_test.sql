DROP TABLE if exists T_users;
-- need to be in one-line otherwise tons of errors..
CREATE TABLE T_users (id varchar(255) primary key, firstName varchar(255) not null, lastName varchar(255) not null, age varchar(255) not null);
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE T_users;
INSERT INTO T_users (id, firstName, lastName, age) VALUES ("1", 'Stephane', 'Nguyen'       , '20');
INSERT INTO T_users (id, firstName, lastName, age) VALUES ("2", 'Fabrice' , 'Hategekimana' , '20');
INSERT INTO T_users (id, firstName, lastName, age) VALUES ("3", 'mohsen'  , 'Hassan Naeini', '20');
INSERT INTO T_users (id, firstName, lastName, age) VALUES ("4", 'ethan'   , 'Icet'         , '20');
INSERT INTO T_users (id, firstName, lastName, age) VALUES ("5", 'raphael' , 'Maggio Aprile', '20');
INSERT INTO T_users (id, firstName, lastName, age) VALUES ("6", 'erwan'   , 'don'          , '20');
