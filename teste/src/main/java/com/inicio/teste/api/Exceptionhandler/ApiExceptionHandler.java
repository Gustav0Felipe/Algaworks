package com.inicio.teste.api.Exceptionhandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.inicio.teste.Domain.Exception.EntidadeNotFoundException;
import com.inicio.teste.Domain.Exception.NegocioException;

@ControllerAdvice //componente spring com a função de tratar exceções de forma global, ou seja, de todos os controllers.
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {//trata já varias exceptions.

    @Autowired //não quero fazer por construtor, mas se fizesse a classe Api Receberia construtor com este atributo.
    private MessageSource messageSource; //interface para resolver messagens.

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

            Problema problema = new Problema();
            List<Problema.Campo> campos = new ArrayList<>();

            //getBindingResult(), o resultado da requisição, onde pegar todos os erros
            for (ObjectError error : ex.getBindingResult().getAllErrors()){
                String nome = ((FieldError) error).getField(); //para fazer o cast tenho que ter certeza
                //que o object error é um FieldError.
                String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
                //String mensagem = error.getDefaultMessage(); //mensagem especifica de erro para este campo.
                //mensagem padrão do erro, que talvez atualmente já esteja traduzida.
                campos.add(new Problema.Campo(nome, mensagem));
            }

            problema.setStatus(status.value());
            problema.setDataHora(OffsetDateTime.now());
            problema.setTitulo("Um ou mais campos estão inválidos, Faça o preenchimento corretamente.");
            problema.setCampos(campos);
                return handleExceptionInternal(ex, problema, headers, status, request);
    } 
   
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Problema problema = new Problema();
        problema.setStatus(status.value());
        problema.setDataHora(OffsetDateTime.now());
        problema.setTitulo(ex.getMessage());

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNotFoundException.class)
    public ResponseEntity<Object> handleEntidadeNotFound(NegocioException ex, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;

        Problema problema = new Problema();
        problema.setStatus(status.value());
        problema.setDataHora(OffsetDateTime.now());
        problema.setTitulo(ex.getMessage());

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
}

