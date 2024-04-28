insert into logins
values (next value for seq_pk_login_id, 'lelar', 'qwerty12345'),
       (next value for seq_pk_login_id, 'qwerty', 'qwerty12345');

insert into permissions
values (next value for seq_pk_permission_id, 'Permission 1', false),
       (next value for seq_pk_permission_id, 'Permission 2', true),
       (next value for seq_pk_permission_id, 'Permission 3', true);

insert into users
values (next value for seq_pk_user_id, 1, 'firstName', 'secondName', 'patrName'),
       (next value for seq_pk_user_id, 1, 'thirdName', 'fourthName', 'patrName');

insert into squads
values (next value for seq_pk_squad_id, 'firstSquad'),
       (next value for seq_pk_squad_id, 'secondSquad');

insert into squad_bindings
values (next value for seq_pk_squad_binding_id, 1, 1),
       (next value for seq_pk_squad_binding_id, 2, 1),
       (next value for seq_pk_squad_binding_id, 1, 2),
       (next value for seq_pk_squad_binding_id, 2, 2);

insert into permission_bindings
values (next value for seq_pk_permission_binding_id, 1, 1, true),
       (next value for seq_pk_permission_binding_id, 2, 1, false),
       (next value for seq_pk_permission_binding_id, 3, 1, true),
       (next value for seq_pk_permission_binding_id, 2, 2, false);

insert into tournaments
values (next value for seq_pk_tournaments_id, 'name1', '2020-10-01', '2021-10-01', 1, 2, 'M', 'sdfgsdhdsh'),
       (next value for seq_pk_tournaments_id, 'name2', '2021-01-20', '2022-01-20', 1, 2, 'F', 'sadgdsgh'),
       (next value for seq_pk_tournaments_id, 'name3', '2022-06-10', '2023-06-10', 2, 1, 'M', 'sdhfsdhsd');

insert into picture_formats
values (next value for seq_pk_picture_formats_id, 'jpg');

insert into pictures
values (next value for seq_pk_picture_id, '/pictures/image.jpg', '/image.jpg', 1);

insert into picture_bindings
values (next value for seq_pk_picture_binding_id, 1, 1);
