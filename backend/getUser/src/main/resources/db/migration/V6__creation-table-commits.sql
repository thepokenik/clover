CREATE TABLE commits (
    id_commit BIGINT PRIMARY KEY AUTO_INCREMENT,
    commit_message TEXT,
    commit_date DATETIME NOT NULL
) ENGINE=InnoDB;