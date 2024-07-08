CREATE TABLE IF NOT EXISTS `users`
(
    `id`         INT         NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(45) NOT NULL,
    `last_name`  VARCHAR(45) NOT NULL,
    `username`   VARCHAR(15) NOT NULL UNIQUE,
    `password`   TEXT        NOT NULL CHECK (octet_length(`password`) >= 8),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `topics`
(
    `id`     INT         NOT NULL AUTO_INCREMENT,
    `name`   VARCHAR(70) NOT NULL,
    `author` INT         NOT NULL,
    `slug`   VARCHAR(70) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `unique_identifier` UNIQUE (`name`, `slug`),
    CONSTRAINT `fk_topic_author` FOREIGN KEY (`author`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS `subjects`
(
    `id`       INT  NOT NULL AUTO_INCREMENT,
    `content`  TEXT NOT NULL,
    `topic_id` INT  NOT NULL,
    `author`   INT  NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_subject_author` FOREIGN KEY (`author`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `fk_subject_topic` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
);
