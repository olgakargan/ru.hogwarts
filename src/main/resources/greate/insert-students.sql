--liquibase formatted sql

--changeset Olga Kargan:insert-students


INSERT INTO public.students (age, name, faculty_id)
VALUES (35, 'Игорь', 3);
INSERT INTO public.students (age, name, faculty_id)
VALUES (45, 'Сергей', 3);
INSERT INTO public.students (age, name, faculty_id)
VALUES (25, 'Петр', 2);
INSERT INTO public.students (age, name, faculty_id)
VALUES (23, 'Иван', 1);

INSERT INTO public.students (age, name, faculty_id)
VALUES (22, 'Миша', 1);