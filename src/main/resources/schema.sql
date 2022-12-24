drop sequence if exists hibernate_sequence cascade;

create sequence hibernate_sequence minvalue 1;

alter sequence hibernate_sequence owner to student;

drop table if exists student cascade;
create table if not exists student
(
    id         bigint  not null
    primary key,
    age        integer not null,
    name       varchar(1024),
    faculty_id bigint,
    foreign key (faculty_id) references faculty,
    avatar_id  bigint,
    foreign key (avatar_id) references avatar
    );

alter table student
    owner to student;

drop table if exists faculty cascade;
create table if not exists faculty
(
    id    bigint not null
    primary key,
    color varchar(1024),
    name  varchar(1024)
    );

alter table faculty
    owner to student;

drop table if exists avatar cascade;
create table if not exists avatar
(
    id         bigint not null
    primary key,
    data       oid,
    file_path  varchar(1024),
    file_size  bigint not null,
    media_type varchar(1024
        )
    );

alter table avatar
    owner to student;
