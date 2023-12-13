package com.API.getUser.models.commit;

import com.API.getUser.models.projects.Projects;
import com.API.getUser.models.users.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "commits")
public class Commits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commit")
    private Long idCommit;

    @Column(name = "commit_message", nullable = false)
    private String commitMessage;

    @Column(name = "commit_date", nullable = false)
    private LocalDateTime commitDate;

    @ManyToOne
    @JoinColumn(name = "id_project", nullable = false)
    private Projects project;

    @ManyToOne
    @JoinColumn(name = "id_User", nullable = false)
    private Users user;

    public Commits(String commitMessage, LocalDateTime commitDate, Projects project, Users user) {
        this.commitMessage = commitMessage;
        this.commitDate = LocalDateTime.now();
        this.project = project;
        this.user = user;
    }
}
