-- !Ups

CREATE TABLE SLATES (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    title varchar(255) NOT NULL,
    creator varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE QUESTIONS (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    slate_id bigint(20) references SLATES(id),
    text varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE CANDIDATES (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    description varchar(255) NULL,
    question_id bigint(20) references QUESTIONS(id),
    slate_id bigint(20) references SLATES(id),
    PRIMARY KEY (id)
);



-- !Downs

DROP TABLE SLATES;
DROP TABLE QUESTIONS;
DROP TABLE CANDIDATES;