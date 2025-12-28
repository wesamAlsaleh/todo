create table public.users_profile
(
    id                  BIGINT       not null
        constraint user_profile_user_id_fk
            references public.users,
    first_name          varchar(255) not null,
    last_name           varchar(255) not null,
    profile_description varchar(255)
);

