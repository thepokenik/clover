package com.API.getUser.DTO;

import java.time.LocalDateTime;

public record AutenticarDados(String username, String email , String password, LocalDateTime date_creation) {
}
