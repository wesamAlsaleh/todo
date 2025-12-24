create table users
(
    id         BIGSERIAL                           not null
        constraint users_pk
            primary key,
    email      varchar(255)                        not null
        constraint users_uk
            unique,
    password   varchar(255)                        not null,
    created_at timestamp default current_timestamp not null,
    updated_at timestamp default current_timestamp not null
);

