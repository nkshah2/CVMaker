
ALTER TABLE "database_theme"
ADD "entry_id" varchar;

update "database_theme" set "entry_id" = "1" where "current_theme"="grey";