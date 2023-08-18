create database if not exists popping
use popping
drop table if exists favorite
drop table if exists member
drop table if exists message
drop table if exists terms
drop table if exists terms_agreement
create table favorite (
    favorite_id bigint not null,
    member_id bigint not null,
    primary key (favorite_id, member_id)
) engine=InnoDB
create table member (
    created_time datetime(6),
    last_visited_time datetime(6),
    member_id bigint not null auto_increment,
    share_id bigint not null,
    withdrawal_date datetime(6),
    member_type enum ('GOOGLE','KAKAO') not null,
    bio varchar(30) not null,
    nickname varchar(30) not null,
    role enum ('ADMIN','USER'),
    social_login_id varchar(255) not null,
    primary key (member_id)
) engine=InnoDB
create table message (
    reply varchar(1) not null,
    reply_available varchar(1) not null,
    create_time datetime(6),
    expiration_time datetime(6) not null,
    message_id bigint not null auto_increment,
    receiver bigint not null,
    sender bigint,
    nickname varchar(20) not null,
    content varchar(255) not null,
    receiver_nickname varchar(255),
    state enum ('EXPIRED','READ','UNREAD'),
    primary key (message_id)
) engine=InnoDB
create table terms (
    mandatory varchar(1) not null,
    created_time datetime(6),
    terms_id bigint not null auto_increment,
    updated_time datetime(6),
    title varchar(30) not null,
    content varchar(1000) not null,
    primary key (terms_id)
) engine=InnoDB
create table terms_agreement (
    agreement_date datetime(6),
    member_id bigint not null,
    terms_agreement_id bigint not null auto_increment,
    terms_id bigint not null,
    state enum ('ACTIVE','PENDING','REJECTED'),
    primary key (terms_agreement_id)
) engine=InnoDB
alter table if exists member 
    add constraint UK_oupsg7dcejy77gpmdh04r2bch unique (share_id)