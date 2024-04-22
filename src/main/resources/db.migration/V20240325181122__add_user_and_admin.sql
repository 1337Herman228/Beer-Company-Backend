insert into roles(role_id, position) values (1, 'Администратор');
insert into roles(role_id, position) values (2, 'Пользователь');

insert into person(person_id, email, name, phone, surname) values (1, 'email@mail', 'name', 'phone', 'surname');
insert into person(person_id, email, name, phone, surname) values (2, 'email@mail2', 'name2', 'phone2', 'surname2');

insert into users(person_id, role_id, user_id, login, password) values (2, 2, 2, 'login', '$2a$10$hyNuuXNnZBVjis0SY2xRxuYIvPIcr45Dq/Z9SiMK9f1eso6QgO4Sa');
insert into users(person_id, role_id, user_id, login, password) values (1, 1, 1, 'admin', '$2a$10$hyNuuXNnZBVjis0SY2xRxuYIvPIcr45Dq/Z9SiMK9f1eso6QgO4Sa');
