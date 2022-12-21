--liquibase formatted sql

--changeset Olga Kargan:insert-avatars

INSERT INTO public.avatars (id, data, file_path, file_size, media_type)
VALUES (1, 11123, 'avatars\Student{id=1, name=''Ваня''}.JPG', 23232, 'image/jpeg');