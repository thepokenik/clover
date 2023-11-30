package com.API.getUser.infra.security;

import com.API.getUser.users.Users;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Serviço para geração e verificação de tokens JWT.
 */
@Service
public class TokenService {

    // Chave secreta para assinatura e verificação do token. Pode ser configurada externamente.
    @Value("${JWT_SECRET:123456789}")
    private String secret;

    /**
     * Gera um token JWT para o usuário.
     *
     * @param users Usuário para o qual o token será gerado.
     * @return Token JWT gerado.
     * @throws RuntimeException Se ocorrer um erro durante a geração do token.
     */
    public String generateToken(Users users) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(users.getUsername())
                    .withExpiresAt(dataExpiracao())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token!", exception);
        }
    }

    /**
     * Obtém o assunto (subject) de um token JWT.
     *
     * @param tokenJWT Token JWT a ser verificado.
     * @return O assunto do token (normalmente o nome de usuário).
     * @throws RuntimeException Se o token JWT for inválido ou expirado.
     */
    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }

    /**
     * Obtém a data de expiração do token (2 horas a partir do momento atual).
     *
     * @return A data de expiração do token.
     */
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
