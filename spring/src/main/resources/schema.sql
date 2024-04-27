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

create table if not exists picture_formats
(
    id     number     not null,
    format varchar(3) not null,
    constraint pk_picture_format_id primary key (id)
);
create sequence if not exists seq_pk_picture_formats_id start with 1 increment by 1;

create table if not exists pictures
(
    id          number       not null,
    server_path varchar(255) not null,
    local_path  varchar(255) not null,
    format_id   number       not null,
    constraint pk_picture_id primary key (id),
    constraint fk_picture_formats_format_id foreign key (format_id) references picture_formats (id)
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

create table if not exists tournaments
(
    id                 number       not null,
    tournament_name               varchar(255) not null,
    start_date         timestamp    not null,
    end_date           timestamp    not null,
    squad_id           number,
    opponents_squad_id number,
    gender_type        varchar(1)   not null,
    address            varchar(255) not null,
    constraint pk_tournaments_id primary key (id),
    constraint fk_tournaments_squad_id foreign key (squad_id) references squads (id),
    constraint fk_tournaments_opponents_squad_id foreign key (opponents_squad_id) references squads (id)
);
create sequence if not exists seq_pk_tournaments_id start with 1 increment by 1;

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

create table if not exists picture_bindings
(
    id            number not null,
    tournament_id number not null,
    picture_id    number not null,
    constraint pk_picture_binding_id primary key (id),
    constraint fk_picture_binding_tournament_id foreign key (tournament_id) references tournaments (id),
    constraint fk_picture_binding_picture_id foreign key (picture_id) references pictures (id)
);
create sequence if not exists seq_pk_picture_binding_id start with 1 increment by 1;

--todo need to add triggers on next val primary keys and on insert PERMISSIONS table and users permission list