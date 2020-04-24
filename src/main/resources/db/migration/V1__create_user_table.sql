create table USER
(
    ID            BIGINT auto_increment  not null,
    ACCOUNT_ID   VARCHAR(100) not null,
    NAME         VARCHAR(50),
    TOKEN        VARCHAR(36),
    GMT_CREATE   BIGINT       not null,
    GMT_MODIFIED BIGINT       not null,
    BIO          VARCHAR(256),
    AVATAR_URL   VARCHAR(100),
    constraint USER_PK
        primary key (ID)
);