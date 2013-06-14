# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table location (
  id                        integer not null,
  length                    integer,
  width                     integer,
  name                      varchar(255),
  constraint pk_location primary key (id))
;

create table review (
  id                        bigint not null,
  comment                   varchar(255),
  rating                    integer,
  constraint pk_review primary key (id))
;

create table tour (
  id                        bigint not null,
  name                      varchar(255),
  date                      timestamp,
  price                     double,
  location_id               integer,
  guide_username            varchar(255),
  description_full          TEXT,
  description_short         TEXT,
  description_mini          varchar(255),
  photo_name                varchar(255),
  constraint pk_tour primary key (id))
;

create table user (
  username                  varchar(255) not null,
  password                  varchar(255),
  email                     varchar(255),
  phone                     varchar(255),
  photo                     varchar(255),
  firstname                 varchar(255),
  lastname                  varchar(255),
  rating                    integer,
  country                   varchar(255),
  constraint pk_user primary key (username))
;

create sequence location_seq;

create sequence review_seq;

create sequence tour_seq;

create sequence user_seq;

alter table tour add constraint fk_tour_location_1 foreign key (location_id) references location (id) on delete restrict on update restrict;
create index ix_tour_location_1 on tour (location_id);
alter table tour add constraint fk_tour_guide_2 foreign key (guide_username) references user (username) on delete restrict on update restrict;
create index ix_tour_guide_2 on tour (guide_username);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists location;

drop table if exists review;

drop table if exists tour;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists location_seq;

drop sequence if exists review_seq;

drop sequence if exists tour_seq;

drop sequence if exists user_seq;

