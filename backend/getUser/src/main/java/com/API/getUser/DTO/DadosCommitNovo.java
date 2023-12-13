package com.API.getUser.DTO;

import java.time.LocalDateTime;

public record DadosCommitNovo(String commitMessage,
                              LocalDateTime commitDate) {
}
