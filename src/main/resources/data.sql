create table user_details
(
    id         INTEGER not null,
    birth_date DATE    not null,
    name       VARCHAR not null
);

insert into user_details(id, birth_date, name)
values ( 10001, CURRENT_DATE(), 'Matias' );

insert into user_details(id, birth_date, name)
values ( 10002, CURRENT_DATE(), 'Matt' );

insert into user_details(id, birth_date, name)
values ( 10003, CURRENT_DATE(), 'Mathew' );