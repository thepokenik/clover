package com.API.getUser.DTO;

import com.API.getUser.models.commit.Commits;

import java.time.LocalDateTime;

public record DadosListagemCommit(Long idCommit, String commitMessage, LocalDateTime commitDate, Long project, Long user) {

    public DadosListagemCommit(Commits commits) {
        this(commits.getIdCommit(), commits.getCommitMessage(), commits.getCommitDate(), commits.getProject().getIdProjects(), commits.getUser().getId_User());
    }
}
