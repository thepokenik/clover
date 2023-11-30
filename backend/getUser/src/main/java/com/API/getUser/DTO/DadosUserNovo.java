package com.API.getUser.DTO;

import com.API.getUser.users.Users;

public record DadosUserNovo(Long id_User, String username, String email, String password) {

    public DadosUserNovo(Users users) {
    this(users.getId_User(), users.getUsername(), users.getEmail(), users.getPassword());
}
}
