-- auto-generated definition
create table QUESTION
(
    ID           BIGINT auto_increment  not null,
    TITLE         VARCHAR(50)  not null,
    DESCRIPTION   CLOB         not null,
    GMT_CREATE    BIGINT  default 0,
    GMT_MODIFIED  BIGINT  default 0,
    CREATOR       BIGINT,
    COMMENT_COUNT INTEGER default 0,
    VIEW_COUNT    INTEGER default 0,
    LIKE_COUNT    INTEGER default 0,
    TAG           VARCHAR(256) not null,
    constraint QUESTION_PK
        primary key (ID)
);