CREATE TABLE IF NOT EXISTS `patients` (
    `cnp` CHAR(13) NOT NULL PRIMARY KEY,
    `id_user` INTEGER,
    `nume` VARCHAR(50),
    `prenume` VARCHAR(50),
    `email` VARCHAR(70) UNIQUE,
    `telefon` CHAR(10) UNIQUE CHECK (telefon REGEXP '^[0-9]{10}$'),
    `is_active` BOOLEAN,
    `data_nasterii` DATETIME
);

CREATE TRIGGER before_insert_patients
BEFORE INSERT ON `patients`
FOR EACH ROW
BEGIN
    IF DATEDIFF(CURDATE(), NEW.data_nasterii) < 6570 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Pacientii trebuie sa aiba cel putin 18 ani.';
    END IF;
END;

CREATE TABLE IF NOT EXISTS `physicians` (
    `id_doctor` INTEGER AUTO_INCREMENT PRIMARY KEY,
    `id_user` INTEGER,
    `nume` VARCHAR(50),
    `prenume` VARCHAR(50),
    `email` VARCHAR(70) UNIQUE,
    `telefon` CHAR(10) UNIQUE CHECK (telefon REGEXP '^[0-9]{10}$'),
    `specializare` VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS `appointments` (
    `patient_id` CHAR(13) NOT NULL,
    `physician_id` INTEGER NOT NULL,
    `date` DATETIME NOT NULL,
    `status` ENUM('onorata', 'neprezentat', 'anulat') NOT NULL,
    PRIMARY KEY (`patient_id`, `physician_id`, `date`),
    FOREIGN KEY (`patient_id`) REFERENCES `patients` (`cnp`),
    FOREIGN KEY (`physician_id`) REFERENCES `physicians` (`id_doctor`)
);
