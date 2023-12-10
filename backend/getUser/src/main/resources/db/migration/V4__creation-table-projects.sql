create table projects(
	id_projects bigint PRIMARY KEY AUTO_INCREMENT,
    projects_name VARCHAR(255) NOT NULL,
    creation_date DATETIME,
    projects_progress DATETIME,
    projects_readme TEXT,
    projects_description TEXT,
    projects_file VARCHAR(20),
    id_User bigint,
    FOREIGN KEY (id_User) REFERENCES users(id_User)
)engine InnoDB;