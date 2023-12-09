package com.API.getUser.DTO;

import java.time.LocalDateTime;

public record DadosAtualizacaoProject(Long idProject, String projectName, String projectReadme, String projectDescription, String projectFile, LocalDateTime projectProgress) {
}
