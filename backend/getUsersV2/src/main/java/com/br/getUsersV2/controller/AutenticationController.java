package com.br.getUsersV2.controller;

import com.br.getUsersV2.DTO.AutenticarDados;
import com.br.getUsersV2.infra.security.*;
import com.br.getUsersV2.users.Users;
import com.br.getUsersV2.users.UsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador para autenticação de usuários.
 */
@RestController
@RequestMapping("auth")
public class AutenticationController {

    @Autowired
    private ErrorHandling errorHandling;

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
    public ResponseEntity register(@RequestBody @Valid AutenticarDados dados, StandardError standardError ,UriComponentsBuilder uriComponentsBuilder) {
        // Verificar se o usuário já existe
        if (repository.findByUsername(dados.username()) != null) {
            return ResponseEntity.badRequest().body("Já existe um usuário com este nome!");
        }

        if (dados.username().isBlank() || dados.username().length() < 3) {
            StandardError err = new StandardError("username: ", " Campo usuário deve ter no mínimo 3 caracteres!");
            return ResponseEntity.badRequest().body(err);
        }
        if (dados.email().isBlank()) {
            throw new BadRequestException("email: ", " Campo email vazio!");
        }
        if (dados.password().isBlank() || dados.password().length() < 8) {
            throw new BadRequestException("password: ", " Campo senha deve ter no mínimo 8 caracteres!");
        }

        // Criar um novo usuário com a senha criptografada
        String encryptedPassword = new BCryptPasswordEncoder().encode(dados.password());
        Users newUser = new Users(dados.username(), encryptedPassword, dados.email(), LocalDateTime.now());
        repository.save(newUser);

        // Construir a URI para o novo usuário
        var uri = uriComponentsBuilder.path("/users/{id_User}").buildAndExpand(newUser.getId_users()).toUri();

        // Retornar uma resposta 201 Created com a URI e o corpo do novo usuário
        return ResponseEntity.created(uri).body(newUser);
    }
}
