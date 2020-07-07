create table examiner
(
    id                serial                              not null
        constraint examiner_pkey
            primary key,
    first_name        text,
    last_name         text,
    login             text                                not null,
    password          varchar(68)                         not null,
    role              text                                not null,
    date_created      timestamp default CURRENT_TIMESTAMP not null,
    date_last_updated timestamp default CURRENT_TIMESTAMP not null
);

create unique index examiner_login_uindex
    on examiner (login);

create table exams
(
    id                serial                              not null
        constraint exams_pkey
            primary key,
    title             text,
    password          varchar(68),
    start_date        timestamp with time zone            not null,
    end_date          timestamp with time zone            not null,
    examiner_id       integer   default 0                 not null,
    date_created      timestamp default CURRENT_TIMESTAMP not null,
    date_last_updated timestamp default CURRENT_TIMESTAMP not null
);

create table student_entry
(
    id                serial                              not null
        constraint student_entry_pkey
            primary key,
    exam_id           integer   default 0                 not null,
    index             integer                             not null,
    first_name        text,
    last_name         text,
    finish_date       timestamp with time zone,
    date_created      timestamp default CURRENT_TIMESTAMP not null,
    date_last_updated timestamp default CURRENT_TIMESTAMP not null,
    done              boolean   default false,
    extra_time        integer,
    path              text,
    group_number      integer   default 0                 not null
);

create table exampath
(
    exam_id  integer not null,
    group_id integer not null,
    path     text    not null,
    id       serial  not null
        constraint exampath_pk
            primary key
);

create unique index exampath_id_uindex
    on exampath (id);
