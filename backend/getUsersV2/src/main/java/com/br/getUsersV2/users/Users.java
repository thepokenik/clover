package com.br.getUsersV2.users;

import com.mongodb.lang.NonNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@EqualsAndHashCode(of = "id_users")
public class Users implements UserDetails {

    @Id
    private String id_users;

    @Field("username")
    @NotEmpty(message = "Campo username não pode ser nulo")
    @Size(min = 3, message = "Usuário deve ter no mínimo 3 caracteres")

    private String username;
    @Field("email")
    @Email (message = "Email inválido!")
    @NotEmpty(message = "Campo email não pode ser nulo")

    private String email;
    @Field("password")
    @NotEmpty(message = "Campo password não pode ser nulo")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String password;

    @Field("date_creation")
    private LocalDateTime date_creation;


    public Users(String username, String password, String email,LocalDateTime date_creation) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.date_creation = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
    @Override
    public String getUsername() {
        return username;
    }

    // Retorna a senha do usuário.
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

