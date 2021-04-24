DROP TABLE if exists T_groups;
CREATE TABLE T_groups (
    id varchar(255) SERIAL PRIMARY KEY,
    name varchar(255) not null
);
-- Auto increment : https://chartio.com/resources/tutorials/how-to-define-an-auto-increment-primary-key-in-postgresql/
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE T_groups;
INSERT INTO T_groups (id, name) VALUES ('1', 'erwan');
INSERT INTO T_groups (id, name) VALUES ('2', 'mohsen');
INSERT INTO T_groups (id, name) VALUES ('3', 'ethan');
INSERT INTO T_groups (id, name) VALUES ('42', 'raphael');