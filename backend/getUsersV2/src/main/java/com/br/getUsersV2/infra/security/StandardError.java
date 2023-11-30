package com.br.getUsersV2.infra.security;

import java.io.Serial;
import java.io.Serializable;

public class StandardError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String campo;
    private String message;

    public StandardError(){}

    public String getCampo() {
        return campo;
    }

    public String getMessage() {
        return message;
    }

    public StandardError(String campo, String message) {
        this.campo = campo;
        this.message = message;
    }
}

