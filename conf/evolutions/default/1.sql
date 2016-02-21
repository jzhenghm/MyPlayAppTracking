# --- !Ups

create table "journal" (
  "id" bigint generated by default as identity(start with 1) not null primary key,
  "env" varchar(10) not null,
  "app" varchar(20) not null,
  "version" varchar(20) not null,
  "username" varchar not null,
  "date" date not null
 );

create table "current_deploy" (
  "id" bigint generated by default as identity(start with 1) not null primary key,
  "env" varchar(10) not null,
  "app" varchar(20) not null,
  "version"	varchar(20) not null
);

insert into "journal" VALUES (110,'prod', 'pin code service','0.2.4','john','2016-01-29');
insert into "journal" VALUES (109,'prod', 'fulfillment service','0.5.3','mack','2016-01-28');
insert into "journal" VALUES (108,'prod', 'e-commerce web','0.0.2','john','2016-01-27');
insert into "journal" VALUES (107,'prod', 'pin code service','0.2.1','john','2016-01-27');
insert into "journal" VALUES (106,'prod', 'e-commerce web','0.1.1','john','2016-01-28');
insert into "journal" VALUES (105,'prod', 'pin code service','0.2.3','john','2016-01-29');
insert into "journal" VALUES (104,'prod', 'pin code service','0.2.2','john','2016-01-28');
insert into "journal" VALUES (103,'prod', 'e-commerce web','0.1.0','john','2016-01-25');
insert into "journal" VALUES (102,'prod', 'pin code service','0.2.1','john','2016-01-24');
insert into "journal" VALUES (100,'prod', 'e-commerce web','0.0.9','john','2016-01-24');
insert into "journal" VALUES (101,'qa', 'e-commerce web','0.0.9','john','2016-01-12');
insert into "journal" VALUES (99,'labs', 'e-commerce web','0.0.9','mack','2016-01-15');
insert into "journal" VALUES (98,'stage', 'e-commerce web','0.0.9','jack','2016-01-12');
insert into "journal" VALUES (97,'qa', 'pin code service','0.2.2','mack','2016-01-12');
insert into "journal" VALUES (96,'labs', 'e-commerce web','0.0.8','john','2016-01-02');
insert into "journal" VALUES (95,'stage', 'pin code service','0.0.2','jack','2016-01-02');
insert into "journal" VALUES (94,'prod', 'e-commerce web','0.0.8','john','2016-01-02');
insert into "journal" VALUES (93,'qa', 'e-commerce web','0.0.7','john','2016-01-02');
insert into "journal" VALUES (92,'qa', 'e-commerce web','0.0.6','john','2016-01-02');
insert into "journal" VALUES (91,'qa', 'e-commerce web','0.0.5','john','2016-01-02');
insert into "journal" VALUES (90,'labs', 'e-commerce web','0.0.4','john','2016-01-02');
insert into "journal" VALUES (89,'qa', 'fulfillment service','0.5.3','john','2016-01-02');
insert into "journal" VALUES (88,'qa', 'e-commerce web','0.0.3','john','2015-12-12');

insert into "current_deploy" VALUES(1,'prod','pin code service','0.2.4');
insert into "current_deploy" VALUES(2,'prod','fulfillment service','0.5.3');
insert into "current_deploy" VALUES(3,'qa','fulfillment service','0.5.3');
insert into "current_deploy" VALUES(4,'prod', 'e-commerce web','0.1.1');
insert into "current_deploy" VALUES (5,'qa', 'pin code service','0.2.2');
insert into "current_deploy" VALUES (6,'qa', 'e-commerce web','0.0.9');
insert into "current_deploy" VALUES (7,'stage', 'e-commerce web','0.0.9');
insert into "current_deploy" VALUES (8,'stage', 'pin code service','0.0.2');
insert into "current_deploy" VALUES (9,'labs', 'e-commerce web','0.0.9');
# --- !Downs


drop table "journal" if exists;
drop table "current_deploy"
