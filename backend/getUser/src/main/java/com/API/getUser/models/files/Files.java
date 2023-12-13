package com.API.getUser.models.files;

import com.API.getUser.models.commit.Commits;
import com.API.getUser.models.projects.Projects;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_file")
    private Long idFile;

    @Column(name = "file_name", nullable = false)
    @NotEmpty
    private String fileName;

    @Column(name = "file_type", length = 50)
    private String fileType;

    @Column(name = "file_content", columnDefinition = "TEXT")
    private byte[] fileContent;

    @ManyToOne
    @JoinColumn(name = "id_project")
    private Projects project;

    @ManyToOne
    @JoinColumn(name = "id_commit")
    private Commits commit;

    public Files(String fileName, String fileType, byte[] fileContent, Projects project, Commits commit) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileContent = fileContent;
        this.project = project;
        this.commit = commit;
    }

}
