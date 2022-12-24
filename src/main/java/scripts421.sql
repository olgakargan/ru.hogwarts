create table student
(
    age int not null
);
alter table student
    add constraint age_constraint check ( age >= 25 );
alter table student
    add constraint name_unique unique (name);
alter table student
    alter column name set not null;
alter table faculty
    add constraint name_color_unique unique (name, color);
alter table student
    alter column age
        set default 20;
