package com.br.getUsersV2.infra.security;

import java.io.Serial;

public class BadRequestException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;


    public BadRequestException(String campo, String message) {
        super(campo + message);
    }
}
