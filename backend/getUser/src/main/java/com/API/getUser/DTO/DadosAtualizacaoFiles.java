package com.API.getUser.DTO;

import com.API.getUser.models.commit.Commits;

public record DadosAtualizacaoFiles(Long idFile, byte[] fileContent, Commits commits) {
}
