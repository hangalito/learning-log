CREATE TABLE IF NOT EXISTS `user`
(
    `id`         INT AUTO_INCREMENT,
    `first_name` VARCHAR(45) NOT NULL,
    `last_name`  VARCHAR(45) NOT NULL,
    `username`   VARCHAR(15) NOT NULL UNIQUE,
    `password`   TEXT        NOT NULL CHECK (LENGTH(`password`) > 8),
    PRIMARY KEY (`id`)
) ENGINE = InnoDb
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `topic`
(
    `id`     INT AUTO_INCREMENT,
    `name`   VARCHAR(70) NOT NULL,
    `author` INT         NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_topic_author`
        FOREIGN KEY (`author`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDb
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `subject`
(
    `id`       INT AUTO_INCREMENT,
    `content`  TEXT NOT NULL,
    `topic_id` INT  NOT NULL,
    `author`   INT  NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_subject_topic`
        FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
    CONSTRAINT `fk_subject_author`
        FOREIGN KEY (`author`) REFERENCES `topic` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE = InnoDb
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;
