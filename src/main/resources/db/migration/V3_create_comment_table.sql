-- auto-generated definition
create table COMMENT
(
    ID           BIGINT default NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_D96E687A_26BB_472E_9144_ADA486D7F96E" auto_increment,
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
