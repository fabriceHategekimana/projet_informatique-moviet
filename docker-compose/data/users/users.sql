DROP TABLE if exists T_users;
CREATE TABLE T_users (
    id serial primary key,
    name varchar(255) not null
);

CREATE TABLE T_users (
    id serial primary key,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    age varchar(255) not null
);
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE T_users;
INSERT INTO T_users (firstName, lastName, age) VALUES ('Stephane', 'Nguyen'       , '20');
INSERT INTO T_users (firstName, lastName, age) VALUES ('Fabrice' , 'Hategekimana' , '20');
INSERT INTO T_users (firstName, lastName, age) VALUES ('mohsen'  , 'Hassan Naeini', '20');
INSERT INTO T_users (firstName, lastName, age) VALUES ('ethan'   , 'Icet'         , '20');
INSERT INTO T_users (firstName, lastName, age) VALUES ('raphael' , 'Maggio Aprile', '20');
INSERT INTO T_users (firstName, lastName, age) VALUES ('erwan'   , 'don'          , '20');