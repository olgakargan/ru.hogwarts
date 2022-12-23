--liquibase formatted sql

--changeset Olga Kargan:create-avatar

create table avatars
(
    id         bigint not null
        primary key references students,
    data       oid,
    file_path  varchar(1024),
    file_size  bigint not null,
    media_type varchar(1024)
);

alter table avatars
    owner to student;