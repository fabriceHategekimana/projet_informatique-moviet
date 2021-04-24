DROP TABLE if exists group_table;
CREATE TABLE group_table (
    id varchar(255) not null,
    name varchar(255),
    primary key (id)
);
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE group_table;
INSERT INTO group_table (id, name) VALUES ('1', 'erwan');
INSERT INTO group_table (id, name) VALUES ('2', 'mohsen');
INSERT INTO group_table (id, name) VALUES ('3', 'ethan');
INSERT INTO group_table (id, name) VALUES ('42', 'raphael');