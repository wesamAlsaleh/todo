create table public.items
(
    id          bigserial                           not null
        constraint items_pk
            primary key,
    name        varchar(255)                        not null,
    description text,
    created_at  timestamp default current_timestamp not null,
    updated_at  timestamp default current_timestamp not null,
    category_id bigint                              not null
        constraint items_categories_id_fk
            references public.categories
);

