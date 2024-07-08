CREATE TABLE IF NOT EXISTS "users"
(
    "id"         SERIAL,
    "first_name" VARCHAR(45) NOT NULL,
    "last_name"  VARCHAR(45) NOT NULL,
    "username"   VARCHAR(15) NOT NULL,
    "password"   TEXT        NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "username" UNIQUE ("username"),
    CONSTRAINT "password_check" CHECK (OCTET_LENGTH("password") >= 8)
);

CREATE TABLE IF NOT EXISTS "topics"
(
    "id"     SERIAL,
    "name"   VARCHAR(70) NOT NULL,
    "author" INT         NOT NULL,
    "slug"   VARCHAR(70) NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "unique_identifier" UNIQUE ("name", "slug"),
    CONSTRAINT "fk_topic_author" FOREIGN KEY ("author") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS "subjects"
(
    "id"       SERIAL,
    "content"  TEXT NOT NULL,
    "topic_id" INT  NOT NULL,
    "author"   INT  NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk_subject_author" FOREIGN KEY ("author") REFERENCES "users" ("id") ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT "fk_subject_topic" FOREIGN KEY ("topic_id") REFERENCES "topics" ("id") ON DELETE CASCADE ON UPDATE NO ACTION
);
