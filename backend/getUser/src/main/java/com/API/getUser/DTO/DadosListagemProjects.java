package com.API.getUser.DTO;

import com.API.getUser.models.projects.Projects;

import java.time.LocalDateTime;


public record DadosListagemProjects(Long idProjects,
                                    String projectName,
                                    LocalDateTime creationDate,
                                    LocalDateTime projectProgress,
                                    String projectDescription,
                                    String projectReadme,
                                    Long users) {
    public DadosListagemProjects(Projects projects){
        this(projects.getIdProjects(), projects.getProjectName(), projects.getCreationDate(), projects.getProjectProgress(), projects.getProjectDescription(), projects.getProjectReadme(), projects.getUser().getId_User());
    }
}
