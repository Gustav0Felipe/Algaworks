package com.inicio.teste.api.Model;

import java.time.OffsetDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OcorrenciaModel {
    private Long id;
    private String descricao;
    private OffsetDateTime dataRegistro;

}
