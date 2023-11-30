package com.API.getUser.Controller;

import com.API.getUser.infra.security.DadosTokenJWT;
import com.API.getUser.infra.security.ErrorValidation;
import com.API.getUser.infra.security.TokenService;
import com.API.getUser.DTO.AutenticarDados;
import com.API.getUser.users.Users;
import com.API.getUser.users.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Controlador para autenticação de usuários.
 */
@RestController
@RequestMapping("auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UsersRepository repository;

    @Autowired
    private TokenService tokenService;

    /**
     * Endpoint para autenticar um usuário e gerar um token JWT.
     *
     * @param dados Dados de autenticação do usuário.
     * @return ResponseEntity contendo o token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AutenticarDados dados) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(dados.username(), dados.password());

            System.out.println(authenticationToken.isAuthenticated());

            var authentication = manager.authenticate(authenticationToken);
            //Tratamento de erro caso as credenciais estejam erradas

            var tokenJWT = tokenService.generateToken((Users) authentication.getPrincipal());

            return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorValidation("Credenciais inválidas"));
        }
    }

    /**
     * Endpoint para registrar um novo usuário.
     *
     * @param dados                Dados do novo usuário.
     * @param uriComponentsBuilder Builder para construir a URI da resposta.
     * @return ResponseEntity com a resposta da operação.
     */
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid AutenticarDados dados, UriComponentsBuilder uriComponentsBuilder) {
        // Verificar se o usuário já existe
        if (repository.findByUsername(dados.username()) != null) {
            return ResponseEntity.badRequest().body("Já existe um usuário com este nome!");
        }

        // Verificar se a senha é válida
        if (dados.password().isEmpty() || dados.password().length() < 8) {
            Users newUser = new Users(dados.username(), dados.password(), dados.email(), dados.date_creation());
            repository.save(newUser);
        }

        // Criar um novo usuário com a senha criptografada
        String encryptedPassword = new BCryptPasswordEncoder().encode(dados.password());
        Users newUser = new Users(dados.username(), encryptedPassword, dados.email(), dados.date_creation());
        repository.save(newUser);

        // Construir a URI para o novo usuário
        var uri = uriComponentsBuilder.path("/users/{id_User}").buildAndExpand(newUser.getId_User()).toUri();

        // Retornar uma resposta 201 Created com a URI e o corpo do novo usuário
        return ResponseEntity.created(uri).body(newUser);
    }
}
