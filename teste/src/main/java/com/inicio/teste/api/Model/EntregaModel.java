package com.inicio.teste.api.Model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.inicio.teste.Domain.Model.StatusEntrega;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregaModel {//dto data transfer object, output(o que mostrar ao cliente.)

    private Long id; //nome não precisa ser igual.
    private ClienteResumoModel cliente;
    private DestinatarioModel destinatario;
    private BigDecimal taxa;
    private StatusEntrega status;
    private OffsetDateTime dataPedido;
    private OffsetDateTime dataFinalizacao;

}
