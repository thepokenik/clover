package com.API.getUser.userProjects;

import com.API.getUser.users.Users;
import com.API.getUser.users.UsersRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Entity
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Projects{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_projects")
    private Long idProjects;

    @Column(name = "projects_name")
    private String projectName;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "projects_progress")
    private LocalDateTime projectProgress;

    @Column(name = "projects_readme")
    private String projectReadme;

    @Column(name = "projects_description")
    private String projectDescription;

    @Column(name = "projects_file")
    private String projectFile;

    @ManyToOne
    @JoinColumn(name = "id_User")
    private Users user;  // Renomeado para singular para refletir a cardinalidade


    public Projects(String projectName, LocalDateTime creationDate, LocalDateTime projectProgress, String projectDescription, String projectReadme, String projectFile, Users user) {
        this.projectName = projectName;
        this.creationDate = LocalDateTime.now();
        this.projectProgress = LocalDateTime.now();
        this.projectDescription = projectDescription;
        this.projectReadme = projectReadme;
        this.projectFile = projectFile;
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
                ", projectFile='" + projectFile + '\'' +
                ", user=" + user +
                '}';
    }
}