package com.goncalves.API.entities.request;

import com.goncalves.API.entities.commits.Commits;
import com.goncalves.API.entities.files.Files;
import com.goncalves.API.entities.user.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByUser(Users user);
}
