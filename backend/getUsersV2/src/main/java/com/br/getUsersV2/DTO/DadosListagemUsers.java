package com.br.getUsersV2.DTO;

import com.br.getUsersV2.users.Users;

import java.time.LocalDateTime;
import java.util.Date;

public record DadosListagemUsers(String id_users, String username, String email, String password, LocalDateTime date_creation) {
    public DadosListagemUsers(Users users) {
        this(users.getId_users(), users.getUsername(), users.getEmail(), users.getPassword(), users.getDate_creation());
    }
}
