insert into users (first_name, second_name, patronymic, email, birth_date, gender, rating)
values ('firstName', 'secondName', 'patrName', 'firstName@mail.ru','1999-10-01', 'F', 1325),
       ('thirdName', 'fourthName', 'patrName', 'fourthName@gmail.com', '1989-10-01', 'M', 11);

insert into logins
values (next value for seq_pk_login_id, 'lelar', 'qwerty12345', 1),
       (next value for seq_pk_login_id, 'qwerty', 'qwerty12345', 2);

insert into permissions
values (next value for seq_pk_permission_id, 'Обновление классических турниров', 'classic.tournament.update', false),
       (next value for seq_pk_permission_id, 'Обновление пляжных турниров', 'beach.tournament.update', false),
       (next value for seq_pk_permission_id, 'Обновление команд для пляжных турниров', 'beach.tournament.squad.update', false),
       (next value for seq_pk_permission_id, 'Обновление картинок', 'picture.update', false);

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

insert into tournament_status
values (next value for seq_pk_tournament_status_id, 'NEW', 'Новый'),
       (next value for seq_pk_tournament_status_id, 'APPROVED', 'Подтвержденный');

insert into tournaments
values (next value for seq_pk_tournaments_id, 'name1', '2020-10-01', '2021-10-01', 1, 2, 1, 4, 'M', 'sdfgsdhdsh', 1),
       (next value for seq_pk_tournaments_id, 'name2', '2021-01-20', '2022-01-20', 1, 2, 2, 1, 'F', 'sadgdsgh', 1),
       (next value for seq_pk_tournaments_id, 'name3', '2022-06-10', '2023-06-10', 2, 1, 7, 0, 'M', 'sdhfsdhsd', 2);

insert into classic_tournaments
values (next value for seq_pk_classic_tournaments_id, 'name1', '2020-10-01', '2021-10-01', 'squadName1', 'squadName2', 1, 2, 'M', 'sdfgsdhdsh', 1),
       (next value for seq_pk_classic_tournaments_id, 'name2', '2021-01-20', '2022-01-20', 'squadName11', 'squadName21', 1, 2, 'F', 'sadgdsgh', 1),
       (next value for seq_pk_classic_tournaments_id, 'name3', '2022-06-10', '2023-06-10', 'squadName12', 'squadName22', 7, 0, 'M', 'sdhfsdhsd', 2);

insert into picture_formats
values (next value for seq_pk_picture_formats_id, 'jpg');

insert into pictures
values (next value for seq_pk_picture_id, '/pictures/image.jpg', '/image.jpg', 1);

insert into picture_bindings
values (1, 1);

insert into classic_picture_bindings
values (1, 1);
