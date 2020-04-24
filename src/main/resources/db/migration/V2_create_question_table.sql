-- auto-generated definition
create table QUESTION
(
    ID            BIGINT  default NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_7CF1009D_8773_4579_BF0A_27D23C3ACFEF" auto_increment,
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