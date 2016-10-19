--adds a database theme table to maintain the current theme of the application

CREATE TABLE "database_theme" (
"current_theme" varchar primary key not null default "green");

insert into "database_theme" values("green");
