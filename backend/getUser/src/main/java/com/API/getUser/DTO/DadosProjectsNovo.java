package com.API.getUser.DTO;

import com.API.getUser.projects.Projects;

import java.time.LocalDateTime;

public record DadosProjectsNovo(Long idProject, String ProjectName, String ProjectReadme, String ProjectDescription, String ProjectFile, LocalDateTime projectProgress) {
    public DadosProjectsNovo(Projects projects) {
        this(projects.getIdProjects(), projects.getProjectName(),projects.getProjectReadme(), projects.getProjectDescription(), projects.getProjectFile(), projects.getProjectProgress());
    }
}
