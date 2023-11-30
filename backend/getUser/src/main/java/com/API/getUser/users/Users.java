package com.API.getUser.users;

import com.API.getUser.DTO.DadosAtualizacaoUsers;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entidade que representa um usuário no sistema.
 * Implementa UserDetails para integração com o Spring Security.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id_User")
public class Users implements UserDetails {

    // Identificador único do usuário no banco de dados.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_User")
    private Long id_User;

    // Nome de usuário do usuário.
    @Column(name = "username", length = 90, nullable = false)
    @NotEmpty(message = "Campo Usuário é obrigatório!")
    @Size(min = 3, message = "Nome deve ter no mínimo 3 caracteres")
    private String username;

    // Endereço de e-mail do usuário.
    @Column(name = "email", length = 90, nullable = false)
    @NotEmpty(message = "Campo Email é obrigatório!")
    @Email(message = "Insira um email válido!")
    private String email;

    // Senha do usuário.
    @Column(name = "password", columnDefinition = "TEXT", nullable = false)
    @NotEmpty(message = "Campo Senha é obrigatório!")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String password;

    @Column
    private LocalDateTime date_creation;

    // Construtor para criar um usuário com informações básicas.
    public Users(String username, String password, String email, LocalDateTime date_creation) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.date_creation = LocalDateTime.now();
    }

    // Método para atualizar informações do usuário com base nos dados fornecidos.
    public void atualizarInformacoes(DadosAtualizacaoUsers dados) {
        if (dados.username() != null) {
            this.username = dados.username();
        }
        if (dados.password() != null) {
            this.password = dados.password();
        }
    }

    // Implementações de métodos da interface UserDetails para integração com Spring Security.

    // Retorna as autoridades associadas ao usuário (ROLE_USER neste caso).
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    // Retorna o nome de usuário.
    @Override
    public String getUsername() {
        return username;
    }

    // Retorna a senha do usuário.
    @Override
    public String getPassword() {
        return password;
    }

    // Métodos a seguir indicam que a conta não expirou, não está bloqueada, as credenciais não expiraram e a conta está habilitada.
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
