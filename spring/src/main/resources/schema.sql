create table if not exists permissions
(
    id   number       not null,
    name varchar(255) not null,
    constraint pk_permission_id primary key (id)
);
create sequence if not exists seq_pk_permission_id start with 1 increment by 1;

create table if not exists logins
(
    id       number       not null,
    login    varchar(255) not null,
    password varchar(255) not null,
    constraint pk_login_id primary key (id)
);
create sequence if not exists seq_pk_login_id start with 1 increment by 1;

create table if not exists squads
(
    id   number       not null,
    name varchar(255) not null,
    constraint pk_squad_id primary key (id)
);
create sequence if not exists seq_pk_squad_id start with 1 increment by 1;

create table if not exists pictures
(
    id          number       not null,
    server_path varchar(255) not null,
    local_path  varchar(255) not null,
    constraint pk_picture_id primary key (id)
);
create sequence if not exists seq_pk_picture_id start with 1 increment by 1;

create table if not exists users
(
    id          number       not null,
    login_id    number       not null,
    first_name  varchar(255) not null,
    second_name varchar(255) not null,
    patronymic  varchar(255) not null,
    constraint pk_user_id primary key (id),
    constraint fk_users_login_id foreign key (login_id) references logins (id)
);
create sequence if not exists seq_pk_user_id start with 1 increment by 1;

create table if not exists tournament
(
    id                 number       not null,
    name               varchar(255) not null,
    start_date         timestamp    not null,
    end_date           timestamp    not null,
    squad_id           number,
    opponents_squad_id number,
    gender_type        varchar(1)   not null,
    address            varchar(255) not null,
    constraint pk_tournament_id primary key (id),
    constraint fk_tournament_squad_id foreign key (squad_id) references squads (id),
    constraint fk_tournament_opponents_squad_id foreign key (opponents_squad_id) references squads (id)
);
create sequence if not exists seq_pk_tournament_id start with 1 increment by 1;

create table if not exists squad_bindings
(
    id       number not null,
    squad_id number not null,
    user_id  number not null,
    constraint pk_squad_binding_id primary key (id),
    constraint fk_squad_binding_user_id foreign key (user_id) references users (id),
    constraint fk_squad_binding_squad_id foreign key (squad_id) references squads (id)
);
create sequence if not exists seq_pk_squad_binding_id start with 1 increment by 1;

create table if not exists permission_bindings
(
    id            number  not null,
    permission_id number  not null,
    user_id       number  not null,
    allowed       boolean not null,
    constraint pk_permission_binding_id primary key (id),
    constraint fk_permission_binding_user_id foreign key (user_id) references users (id),
    constraint fk_permission_binding_permission_id foreign key (permission_id) references permissions (id)
);
create sequence if not exists seq_pk_permission_binding_id start with 1 increment by 1;


--todo need to add triggers on next val primary keys and on update PERMISSIONS table and users permission list