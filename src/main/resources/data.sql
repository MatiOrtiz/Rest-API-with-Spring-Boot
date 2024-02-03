insert into user_details(id, birth_date, name)
values ( 10001, CURRENT_DATE, 'Matias' );

insert into user_details(id, birth_date, name)
values ( 10002, CURRENT_DATE, 'Matt' );

insert into user_details(id, birth_date, name)
values ( 10003, CURRENT_DATE, 'Mathew' );

insert into post(id, description, user_id)
values(20001, 'I learned Spring JPA', 10001);

insert into post(id, description, user_id)
values(20002, 'I learned Spring Framework', 10002);

insert into post(id, description, user_id)
values(20003, 'I learned Spring Bootcamp', 10003);