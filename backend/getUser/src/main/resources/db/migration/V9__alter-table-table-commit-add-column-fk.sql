ALTER TABLE commits
ADD COLUMN id_project BIGINT,
ADD CONSTRAINT fk_commits_projects
  FOREIGN KEY (id_project) REFERENCES projects(id_project);
