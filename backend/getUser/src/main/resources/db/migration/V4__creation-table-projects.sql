CREATE TABLE projects (
    id_project BIGINT PRIMARY KEY AUTO_INCREMENT,
    project_name VARCHAR(255) NOT NULL,
    creation_date DATETIME,
    projects_progress DATETIME,
    projects_readme TEXT,
    project_description TEXT,
    id_user BIGINT,
    FOREIGN KEY (id_user) REFERENCES users(id_user)
) ENGINE=InnoDB;
