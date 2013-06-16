# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table location (
  id                        bigint not null,
  ltd                       integer,
  lng                       integer,
  country                   varchar(255),
  city                      varchar(255),
  constraint pk_location primary key (id))
;

create table review (
  id                        bigint not null,
  comment                   varchar(255),
  rating                    integer,
  guide_username            varchar(255),
  tourist_username          varchar(255),
  tour_id                   bigint,
  constraint pk_review primary key (id))
;

create table tour (
  id                        bigint not null,
  name                      varchar(255),
  date                      timestamp,
  price                     double,
  location_id               bigint,
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


create table tour_to_tourist (
  id                             bigint not null,
  username                       varchar(255) not null,
  constraint pk_tour_to_tourist primary key (id, username))
;
create sequence location_seq;

create sequence review_seq;

create sequence tour_seq;

create sequence user_seq;

alter table review add constraint fk_review_guide_1 foreign key (guide_username) references user (username) on delete restrict on update restrict;
create index ix_review_guide_1 on review (guide_username);
alter table review add constraint fk_review_tourist_2 foreign key (tourist_username) references user (username) on delete restrict on update restrict;
create index ix_review_tourist_2 on review (tourist_username);
alter table review add constraint fk_review_tour_3 foreign key (tour_id) references tour (id) on delete restrict on update restrict;
create index ix_review_tour_3 on review (tour_id);
alter table tour add constraint fk_tour_location_4 foreign key (location_id) references location (id) on delete restrict on update restrict;
create index ix_tour_location_4 on tour (location_id);
alter table tour add constraint fk_tour_guide_5 foreign key (guide_username) references user (username) on delete restrict on update restrict;
create index ix_tour_guide_5 on tour (guide_username);



alter table tour_to_tourist add constraint fk_tour_to_tourist_tour_01 foreign key (id) references tour (id) on delete restrict on update restrict;

alter table tour_to_tourist add constraint fk_tour_to_tourist_user_02 foreign key (username) references user (username) on delete restrict on update restrict;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists location;

drop table if exists review;

drop table if exists tour;

drop table if exists tour_to_tourist;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists location_seq;

drop sequence if exists review_seq;

drop sequence if exists tour_seq;

drop sequence if exists user_seq;

