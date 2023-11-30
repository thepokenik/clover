package com.br.getUsersV2.infra.security;


import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manipulador de exceções para lidar com erros comuns na aplicação.
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorHandling {

    /**
     * Manipula exceções de EntityNotFoundException, retornando um ResponseEntity com status 404.
     *
     * @return ResponseEntity com status 404 (Not Found).
     */
    @ExceptionHandler(UncategorizedMongoDbException.class)
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
    /*@ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Collection<DadosErroValidacao>> handleDataAccessException(DataAccessException ex) {
        if (ex instanceof DuplicateKeyException) {
            // Trate a exceção específica DuplicateKeyException do MongoDB
            String errorMessage = ex.getRootCause().getMessage();
            List<DadosErroValidacao> errors = Collections.singletonList(new DadosErroValidacao("campo_desconhecido", errorMessage));
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.badRequest().build();
    }*/

    /*@ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardError BadRequestError(BadRequestException ex,StandardError standardError){
        StandardError err = new StandardError(standardError.getCampo(), standardError.getMessage());
        return err;
    }*/
    /*public StandardError badRequestError(StandardError standardError){
        StandardError err = new StandardError(standardError.getCampo(), standardError.getMessage());
        return err;
    }*/

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

