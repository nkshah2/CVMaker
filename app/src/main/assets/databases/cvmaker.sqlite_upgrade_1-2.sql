--adds a database theme table to maintain the current theme of the application

CREATE TABLE "database_theme" (
"current_theme" varchar primary key not null default 'green');

insert into database_theme (current_theme) values ('green');

update database_theme set current_theme='green';

CREATE TABLE "adcheck"(
"is_enabled" INTEGER primary key not null);

insert into "adcheck"(is_enabled) values(1);