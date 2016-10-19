CREATE TABLE "database_theme" (
"current_theme" varchar primary key not null default 'green');

insert into database_themes (current_theme) values ('green');

update database_themes set current_theme='green';