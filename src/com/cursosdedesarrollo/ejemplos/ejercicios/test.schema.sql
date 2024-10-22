CREATE TABLE `test`.`clientes` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NOT NULL,
    `dir` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
    `tlf` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_unicode_ci' NULL,
    PRIMARY KEY (`id`));