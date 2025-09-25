create extension if not exists "uuid-ossp";

create table if not exists users (
                                     id uuid primary key default uuid_generate_v4(),
                                     email varchar(255) not null unique,
                                     password_hash varchar(255) not null
);

create table if not exists processing_log (
                                              id uuid primary key default uuid_generate_v4(),
                                              user_id uuid not null,
                                              input_text text not null,
                                              output_text text not null,
                                              created_at timestamp with time zone not null default now(),
                                              constraint fk_processing_log_user foreign key (user_id) references users(id)
);
