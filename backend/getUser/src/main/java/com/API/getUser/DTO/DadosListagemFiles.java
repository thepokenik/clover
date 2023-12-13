package com.API.getUser.DTO;

import com.API.getUser.models.commit.Commits;
import com.API.getUser.models.files.Files;
import com.API.getUser.models.projects.Projects;

public record DadosListagemFiles(Long idFile,
                                 String fileName,
                                 String fileType,
                                 byte[] fileContent,
                                 Long project,
                                 String commit) {
    public DadosListagemFiles (Files files){
        this(files.getIdFile(), files.getFileName(), files.getFileType(), files.getFileContent(), files.getProject().getIdProjects(), files.getCommit().getCommitMessage());
    }
    public Commits commit(Projects projects) {
         var commits = projects.getCommits();
         var lastCommit = commits.get(commits.size() - 1);
        return lastCommit;
    }
}
