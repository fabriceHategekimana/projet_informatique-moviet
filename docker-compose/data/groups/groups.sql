drop table if exists Group;
create table Group (
    id varchar(255) not null,
    name varchar(255),
    primary key (id)
);

INSERT INTO GROUP (id, name) VALUES ('1', 'erwan');
INSERT INTO GROUP (id, name) VALUES ('2', 'mohsen');
INSERT INTO GROUP (id, name) VALUES ('3', 'ethan');
INSERT INTO GROUP (id, name) VALUES ('42', 'raphael');