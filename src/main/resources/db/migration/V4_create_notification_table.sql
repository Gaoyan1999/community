-- auto-generated definition
create table NOTIFICATION
(
    ID            BIGINT  default NEXT VALUE FOR "PUBLIC"."SYSTEM_SEQUENCE_4A3735DD_D8C9_49D7_B2B6_B0E7BCA28173" auto_increment,
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
