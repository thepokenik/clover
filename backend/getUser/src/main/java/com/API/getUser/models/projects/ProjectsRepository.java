package com.API.getUser.models.projects;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProjectsRepository extends JpaRepository<Projects, Long> {

}
