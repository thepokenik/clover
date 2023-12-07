package com.API.getUser.userDTO;

import java.time.LocalDateTime;

public record AutenticarDados(String username, String email , String password, LocalDateTime date_creation) {
}
