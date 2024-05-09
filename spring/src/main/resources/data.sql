insert into users (first_name, second_name, patronymic, birth_date)
values ('firstName', 'secondName', 'patrName', '1999-10-01'),
       ('thirdName', 'fourthName', 'patrName', '1989-10-01');

insert into logins
values (next value for seq_pk_login_id, 'lelar', 'qwerty12345', 1),
       (next value for seq_pk_login_id, 'qwerty', 'qwerty12345', 2);

insert into permissions
values (next value for seq_pk_permission_id, 'Permission 1', false),
       (next value for seq_pk_permission_id, 'Permission 2', true),
       (next value for seq_pk_permission_id, 'Permission 3', true);

insert into squads
values (next value for seq_pk_squad_id, 'firstSquad'),
       (next value for seq_pk_squad_id, 'secondSquad');

insert into squad_bindings
values (1, 1),
       (2, 1),
       (1, 2),
       (2, 2);

insert into permission_bindings
values (1, 1, true),
       (2, 1, false),
       (3, 1, true),
       (2, 2, false);

insert into tournaments
values (next value for seq_pk_tournaments_id, 'name1', '2020-10-01', '2021-10-01', 1, 2, 'M', 'sdfgsdhdsh'),
       (next value for seq_pk_tournaments_id, 'name2', '2021-01-20', '2022-01-20', 1, 2, 'F', 'sadgdsgh'),
       (next value for seq_pk_tournaments_id, 'name3', '2022-06-10', '2023-06-10', 2, 1, 'M', 'sdhfsdhsd');

insert into picture_formats
values (next value for seq_pk_picture_formats_id, 'jpg');

insert into pictures
values (next value for seq_pk_picture_id, '/pictures/image.jpg', '/image.jpg', 1);

insert into picture_bindings
values (1, 1);
