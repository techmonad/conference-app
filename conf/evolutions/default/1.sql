# --- !Ups
CREATE TABLE "conference" ("id" SERIAL PRIMARY KEY ,"name" varchar , "from" varchar  ,"to" varchar,"place" varchar);
INSERT INTO "conference" values (1,'Scala Days', 'April 18th 2017','April 21st 2017','Chicago');
INSERT INTO "conference" values (2,'Spark Summit', 'FEBRUARY 7th, 2017', 'FEBRUARY 9th, 2017', 'Boston');


# --- !Downs

DROP TABLE "conference";