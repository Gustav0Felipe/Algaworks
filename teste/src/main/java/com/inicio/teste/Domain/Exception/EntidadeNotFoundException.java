package com.inicio.teste.Domain.Exception;

public class EntidadeNotFoundException extends NegocioException{

    private static final long serialVersionUID = 1L;

    public EntidadeNotFoundException(String message) {
        super(message);
    }
    
}
