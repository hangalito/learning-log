CREATE TABLE IF NOT EXISTS `subjects`
(
    `id`       int(11) NOT NULL AUTO_INCREMENT,
    `content`  text    NOT NULL,
    `topic_id` int(11) NOT NULL,
    `author`   int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk_subject_topic` (`topic_id`),
    KEY `fk_subject_author` (`author`),
    CONSTRAINT `fk_subject_author` FOREIGN KEY (`author`) REFERENCES `topics` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `fk_subject_topic` FOREIGN KEY (`topic_id`) REFERENCES `topics` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `topics`
(
    `id`     int(11)     NOT NULL AUTO_INCREMENT,
    `name`   varchar(70) NOT NULL,
    `author` int(11)     NOT NULL,
    `slug`   varchar(70) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_identifier` (`name`, `slug`),
    KEY `fk_topic_author` (`author`),
    CONSTRAINT `fk_topic_author` FOREIGN KEY (`author`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `users`
(
    `id`         int(11)     NOT NULL AUTO_INCREMENT,
    `first_name` varchar(45) NOT NULL,
    `last_name`  varchar(45) NOT NULL,
    `username`   varchar(15) NOT NULL,
    `password`   text        NOT NULL CHECK (octet_length(`password`) >= 8),
    PRIMARY KEY (`id`),
    UNIQUE KEY `username` (`username`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
