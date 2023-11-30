package com.API.getUser.infra.security;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manipulador de exceções para lidar com erros comuns na aplicação.
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorHandling {

    /**
     * Manipula exceções de EntityNotFoundException, retornando um ResponseEntity com status 404.
     *
     * @return ResponseEntity com status 404 (Not Found).
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    /**
     * Manipula exceções de ConstraintViolationException, retornando um ResponseEntity com status 400
     * e uma lista de erros de validação.
     *
     * @param ex A exceção de ConstraintViolationException.
     * @return ResponseEntity com status 400 (Bad Request) e uma lista de erros de validação.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Collection<DadosErroValidacao>> handleConstraintViolationException(ConstraintViolationException ex) {
        // Converte as violações de restrição em uma lista de objetos DadosErroValidacao.
        List<DadosErroValidacao> errors = ex.getConstraintViolations().stream()
                .map(violation -> new DadosErroValidacao(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toList());

        // Retorna ResponseEntity com a lista de erros de validação.
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Classe interna (record) que representa dados de erro de validação.
     */
    private record DadosErroValidacao(String campo, String mensagem) {
        // Construtor do record para inicialização dos campos.
        public DadosErroValidacao(String campo, String mensagem) {
            this.campo = campo;
            this.mensagem = mensagem;
        }
    }
}
