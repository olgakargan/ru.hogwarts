--liquibase formatted sql

--changeset Olga Kargan:create-student-name-index

create index student_name_idx on students(name)

--changeset Olga Kargan:create-faculty-name-and-color-index

create index faculty_name_color_idx on faculties(name, color)