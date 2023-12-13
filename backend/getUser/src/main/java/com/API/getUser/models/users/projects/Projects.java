package com.API.getUser.projects;

import com.API.getUser.DTO.DadosAtualizacaoProject;
import com.API.getUser.users.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private Long idProjects;

    @Column(name = "project_name", nullable = false)
    @NotEmpty
    private String projectName;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "projects_progress")
    private LocalDateTime projectProgress;

    @Column(name = "projects_readme", columnDefinition = "TEXT")
    private String projectReadme;

    @Column(name = "project_description", columnDefinition = "TEXT")
    private String projectDescription;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users user; // Renomeado para singular para refletir a cardinalidade

    public Projects(String projectName, LocalDateTime creationDate, LocalDateTime projectProgress, String projectDescription, String projectReadme, Users user) {
        this.projectName = projectName;
        this.creationDate = LocalDateTime.now();
        this.projectProgress = LocalDateTime.now();
        this.projectDescription = projectDescription;
        this.projectReadme = projectReadme;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Projects{" +
                "idProjects=" + idProjects +
                ", projectName='" + projectName + '\'' +
                ", creationDate=" + creationDate +
                ", projectProgress=" + projectProgress +
                ", projectReadme='" + projectReadme + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", user=" + user +
                '}';
    }

    public void atualizarProject(DadosAtualizacaoProject dados) {
        if (dados.projectName() != null) {
            this.projectName = dados.projectName();
        }
        if (dados.projectReadme() != null) {
            this.projectReadme = dados.projectReadme();
        }
        if (dados.projectDescription() != null) {
            this.projectDescription = dados.projectDescription();
        }
        if (dados.projectProgress() != null) {
            this.projectProgress = LocalDateTime.now();
        }
    }
}
