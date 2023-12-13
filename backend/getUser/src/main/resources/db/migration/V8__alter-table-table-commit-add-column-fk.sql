
ALTER TABLE commits
ADD COLUMN id_user BIGINT,

ADD CONSTRAINT fk_commits_users
  FOREIGN KEY (id_user) REFERENCES users(id_user);
