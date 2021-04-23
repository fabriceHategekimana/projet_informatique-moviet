drop table if exists Group;
create table Group (
    id varchar(255) not null,
    name varchar(255),
    primary key (id)
);
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE GROUP;
INSERT INTO GROUP (id, name) VALUES ('1', 'erwan');
INSERT INTO GROUP (id, name) VALUES ('2', 'mohsen');
INSERT INTO GROUP (id, name) VALUES ('3', 'ethan');
INSERT INTO GROUP (id, name) VALUES ('42', 'raphael');