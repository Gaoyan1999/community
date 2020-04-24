-- auto-generated definition
create table NOTIFICATION
(
    ID        BIGINT auto_increment  not null,
    NOTIFIER      BIGINT            not null,
    RECEIVER      BIGINT            not null,
    OUTERID       BIGINT            not null,
    TYPE          INTEGER           not null,
    GMT_CREATE    BIGINT            not null,
    STATUS        INTEGER default 0 not null,
    NOTIFIER_NAME VARCHAR(100),
    OUTER_TITLE   VARCHAR(256),
    constraint NOTIFICATION_PK
        primary key (ID)
);
