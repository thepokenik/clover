package com.API.getUser.infra.security;

import com.API.getUser.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro de segurança para autenticação e autorização baseada em tokens JWT.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsersRepository repository;

    /**
     * Método que é executado para cada requisição HTTP.
     *
     * @param request     O pedido HTTP.
     * @param response    A resposta HTTP.
     * @param filterChain A cadeia de filtros para continuar o processamento.
     * @throws ServletException Se houver um erro durante o processamento do filtro.
     * @throws IOException      Se houver um erro de entrada/saída.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recupera o token JWT da requisição.
        var tokenJWT = recuperationToken(request);

        // Se o token existir, realiza a autenticação e autorização.
        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            var user = repository.findByUsername(subject);

            // Cria um token de autenticação e define no contexto de segurança.
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua o processamento da cadeia de filtros.
        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token JWT do cabeçalho de autorização.
     *
     * @param request O pedido HTTP.
     * @return O token JWT, ou null se não estiver presente.
     */
    private String recuperationToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            // Remove o prefixo "Bearer" para obter apenas o token.
            return authorizationHeader.replace("Bearer", "").trim();
        }
        return null;
    }
}
