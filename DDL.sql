
create table category
(
    category_id   serial
        constraint category_pk
            primary key,
    category_name varchar not null
);

alter table category
    owner to postgres;

create unique index category_category_name_uindex
    on category (category_name);


create table recipe
(
    recipe_id    integer default nextval('"recipe_recipeID_seq"'::regclass) not null
        constraint recipe_pk
            primary key,
    recipe_name  varchar                                                    not null,
    ingredients  varchar                                                    not null,
    quantity     integer                                                    not null,
    instructions varchar                                                    not null,
    category_id  integer                                                    not null
        constraint recipe_category_category_id_fk
            references category
);

alter table recipe
    owner to postgres;

create unique index recipe_recipeid_uindex
    on recipe (recipe_id);

create table users
(
    user_id  varchar not null
        constraint users_pk
            primary key,
    name     varchar not null,
    password varchar not null,
    email    varchar,
    token    integer not null
);

alter table users
    owner to postgres;

create unique index users_email_uindex
    on users (name);

create unique index users_userid_uindex
    on users (user_id);

