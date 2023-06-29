package com.inicio.teste.api.Model.Input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EntregaInput {

    @NotNull
    private ClienteIdInput cliente;

    @Valid
    @NotNull
    private DestionatarioInput destinatario;

    @NotNull
    private BigDecimal taxa;

}
