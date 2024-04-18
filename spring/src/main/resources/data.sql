insert into logins
values (1, 'lelar', 'qwerty12345'),
       (2, 'qwerty', 'qwerty12345');

insert into permissions
values (1, 'Permission 1'),
       (2, 'Permission 2'),
       (3, 'Permission 3');

insert into users
values (1, 1, 'firstName', 'secondName', 'patrName'),
       (2, 1, 'thirdName', 'fourthName', 'patrName');

insert into squads
values (1, 'firstSquad'),
       (2, 'secondSquad');

insert into squad_bindings
values (1, 1, 1),
       (2, 2, 1),
       (3, 1, 2),
       (4, 2, 2);

insert into permission_bindings
values (1, 1, 1, true),
       (2, 2, 1, false),
       (3, 3, 1, true),
       (4, 2, 2, false);


insert into tournament
values (1, 'name1', '2020-10-01', '2021-10-01', 1, 2, 'M', 'sdfgsdhdsh'),
       (2, 'name2', '2021-01-20', '2022-01-20', 1, 2, 'F', 'sadgdsgh'),
       (3, 'name3', '2022-06-10', '2023-06-10', 2, 1, 'M', 'sdhfsdhsd');
