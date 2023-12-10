package com.API.getUser.DTO;

import com.API.getUser.users.Users;

import java.time.LocalDateTime;

public record
AutenticarProjects (String projectName,
                                  LocalDateTime creationDate,
                                  LocalDateTime projectProgress,
                                  String projectDescription,
                                  String projectReadme,
                                  String projectFile){

}
