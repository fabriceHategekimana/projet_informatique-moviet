DROP TABLE if exists T_count;
CREATE TABLE T_count (
    group_id int not null primary key,
    movie_id int not null primary key,
    nb_yes int not null,
    nb_no int not null,
    nb_maybe int not null
);
-- Grant SQL commands: https://www.ibm.com/docs/en/qmf/11.2?topic=privileges-sql-grant-statement
-- GRANT SELECT, UPDATE, INSERT, DELETE ON ALL TABLES IN SCHEMA public to group-service;
-- Truncate is here to purge the table without deleting the table: https://sql.sh/cours/truncate-table
TRUNCATE TABLE T_count;
INSERT INTO T_count (group_id, movie_id, nb_yes, nb_no, nb_maybe) VALUES (1, 15097, 0, 0, 0);   -- ?
INSERT INTO T_count (group_id, movie_id, nb_yes, nb_no, nb_maybe) VALUES (1, 299534, 3, 0, 3);  -- 4.5
INSERT INTO T_count (group_id, movie_id, nb_yes, nb_no, nb_maybe) VALUES (1, 19995, 5, 1, 0);   -- 4
INSERT INTO T_count (group_id, movie_id, nb_yes, nb_no, nb_maybe) VALUES (1, 164249, 3, 3, 0);  -- 0
INSERT INTO T_count (group_id, movie_id, nb_yes, nb_no, nb_maybe) VALUES (1, 240832, 1, 4, 1);  -- -2.5

DROP TABLE if exists T_unprocessed;
CREATE TABLE T_unprocessed (
    group_id int not null primary key,
    added_at timestampz not null default now(),
    movie_id int not null
);
TRUNCATE TABLE T_unprocessed;
INSERT INTO T_unprocessed (group_id, movie_id) VALUES (1, 671);
INSERT INTO T_unprocessed (group_id, movie_id) VALUES (1, 672);
INSERT INTO T_unprocessed (group_id, movie_id) VALUES (1, 673);
INSERT INTO T_unprocessed (group_id, movie_id) VALUES (1, 674);
INSERT INTO T_unprocessed (group_id, movie_id) VALUES (1, 675);
INSERT INTO T_unprocessed (group_id, movie_id) VALUES (1, 767);
INSERT INTO T_unprocessed (group_id, movie_id) VALUES (1, 9648);
INSERT INTO T_unprocessed (group_id, movie_id) VALUES (1, 12444);
INSERT INTO T_unprocessed (group_id, movie_id) VALUES (1, 12445);


DROP TABLE if exists T_processed;
CREATE TABLE T_processed (
    group_id int not null primary key,
    movie_id int not null primary key,
    added_at timestampz not null default now(),
    popularity float not null,
    n_sat_w_genre int not null,
    n_sat_b_genre int not null,
    n_match_w_keyword int not null,
    n_match_b_keyword int not null,
    n_sat_date int not null
);
TRUNCATE TABLE T_processed;
INSERT INTO T_processed (group_id, movie_id, popularity, n_sat_w_genre, n_sat_b_genre, n_match_w_keyword, n_match_b_keyword, n_sat_date) VALUES (1, 11, 65.052, 0, 0, 0, 0, 0);
INSERT INTO T_processed (group_id, movie_id, popularity, n_sat_w_genre, n_sat_b_genre, n_match_w_keyword, n_match_b_keyword, n_sat_date) VALUES (1, 181812, 183.314, 0, 0, 0, 0, 0);
INSERT INTO T_processed (group_id, movie_id, popularity, n_sat_w_genre, n_sat_b_genre, n_match_w_keyword, n_match_b_keyword, n_sat_date) VALUES (1, 348350, 64.984, 0, 0, 0, 0, 0);
INSERT INTO T_processed (group_id, movie_id, popularity, n_sat_w_genre, n_sat_b_genre, n_match_w_keyword, n_match_b_keyword, n_sat_date) VALUES (1, 140607, 63.429, 0, 0, 0, 0, 0);


DROP TABLE if exists T_propositions;
CREATE TABLE T_propositions; (
    group_id int not null primary key,
    movie_id int not null primary key,
    score float not null
);

TRUNCATE TABLE T_propositions;
INSERT INTO T_propositions (group_id, movie_id, score) VALUES (1, 315635, 42);
INSERT INTO T_propositions (group_id, movie_id, score) VALUES (1, 1771, 69);
INSERT INTO T_propositions (group_id, movie_id, score) VALUES (1, 284054, -50);
INSERT INTO T_propositions (group_id, movie_id, score) VALUES (1, 1726, 99);
INSERT INTO T_propositions (group_id, movie_id, score) VALUES (1, 10195, 0);
