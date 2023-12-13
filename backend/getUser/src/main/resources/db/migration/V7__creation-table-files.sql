CREATE TABLE files (
    id_file BIGINT PRIMARY KEY AUTO_INCREMENT,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(50),
    file_content LONGBLOB,
    id_project BIGINT,
    id_commit BIGINT,
    FOREIGN KEY (id_project) REFERENCES projects(id_project),
    FOREIGN KEY (id_commit) REFERENCES commits(id_commit)
) ENGINE=INNODB;
