PostgreSQL Script : 



create table event
(
    id              serial
        primary key,
    event_code      varchar(100) not null
        unique,
    event_name      varchar(150) not null,
    event_type      varchar(100) not null,
    start_date      date         not null,
    end_date        date,
    location        varchar(200) not null,
    organizer_name  varchar(50),
    description     varchar(1500),
    status          varchar(50)  not null,
    max_participant integer      not null
);

alter table event
    owner to postgres;

create table participant
(
    id                 serial
        primary key,
    participant_code   varchar(100) not null
        unique,
    full_name          varchar(50)  not null,
    gender             varchar(50)  not null,
    address            varchar(100),
    role               varchar(50),
    email              varchar(50),
    phone              varchar(20)  not null unique,
    event_id           integer      not null
        references event,
    registeration_date date         not null,
    payment_status     varchar(20),
    remarks            varchar(200),
    is_attended        varchar(20)
);

alter table participant
    owner to postgres;

