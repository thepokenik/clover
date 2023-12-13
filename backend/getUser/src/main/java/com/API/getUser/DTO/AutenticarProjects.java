package com.API.getUser.DTO;

import java.time.LocalDateTime;

public record
AutenticarProjects (String projectName,
                                  LocalDateTime creationDate,
                                  LocalDateTime projectProgress,
                                  String projectDescription,
                                  String projectReadme){

}
