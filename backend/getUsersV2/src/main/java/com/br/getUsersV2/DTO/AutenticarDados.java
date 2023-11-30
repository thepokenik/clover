package com.br.getUsersV2.DTO;
import java.time.LocalDateTime;

public record AutenticarDados(String username, String email, String password, LocalDateTime date_creation) {

}
