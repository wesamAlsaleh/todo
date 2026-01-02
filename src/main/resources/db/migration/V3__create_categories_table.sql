create table public.categories
(
    id          BIGSERIAL                           not null
        constraint categories_pk
            primary key,
    name        varchar(255)                        not null,
    description text,
    created_at  timestamp default current_timestamp not null,
    updated_at  timestamp default current_timestamp not null,
    user_id     bigint                              not null
        constraint categories_users_id_fk
            references public.users
);

