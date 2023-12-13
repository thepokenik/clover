package com.API.getUser.DTO;

import com.API.getUser.models.projects.Projects;

import java.time.LocalDateTime;

public record DadosProjectsNovo(Long idProject, String ProjectName, String ProjectReadme, String ProjectDescription, LocalDateTime projectProgress) {
    public DadosProjectsNovo(Projects projects) {
        this(projects.getIdProjects(), projects.getProjectName(),projects.getProjectReadme(), projects.getProjectDescription(), LocalDateTime.now());
    }
}
