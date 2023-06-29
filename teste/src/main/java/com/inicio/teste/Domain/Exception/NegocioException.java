package com.inicio.teste.Domain.Exception;

public class NegocioException extends RuntimeException {
       
       private static final long serialVersionUID = 1L;
       
       public NegocioException(String message){
        super(message); //passa a message pro construtor da classe pai.
       }
}
