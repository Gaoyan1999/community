-- auto-generated definition
create table COMMENT
(
    ID           BIGINT auto_increment  not null,
    PARENT_ID    BIGINT  not null,
    TYPE         INTEGER not null,
    COMMENTATOR  BIGINT  not null,
    CONTENT      VARCHAR(256),
    GMT_CREATE   BIGINT  not null,
    GMT_MODIFIED BIGINT  not null,
    LIKE_COUNT   BIGINT default 0,
    constraint COMMENT_PK
        primary key (ID)
);
