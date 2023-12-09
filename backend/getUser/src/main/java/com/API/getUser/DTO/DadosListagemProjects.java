package com.API.getUser.DTO;

import com.API.getUser.projects.Projects;

import java.time.LocalDateTime;


public record DadosListagemProjects(Long idProject,
                                    String projectName,
                                    LocalDateTime creationDate,
                                    LocalDateTime projectProgress,
                                    String projectDescription,
                                    String projectReadme,
                                    String projectFile2,
                                    Long users) {
    public DadosListagemProjects(Projects projects){
        this(projects.getIdProjects(), projects.getProjectName(), projects.getCreationDate(), projects.getProjectProgress(), projects.getProjectDescription(), projects.getProjectReadme(), projects.getProjectFile(), projects.getUser().getId_User());
    }
}
