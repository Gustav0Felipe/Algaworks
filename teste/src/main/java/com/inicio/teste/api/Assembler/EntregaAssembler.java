package com.inicio.teste.api.Assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.inicio.teste.Domain.Model.Entrega;
import com.inicio.teste.api.Model.EntregaModel;
import com.inicio.teste.api.Model.Input.EntregaInput;

import lombok.AllArgsConstructor;

//Assembler/Mapeamento. 
//vai fazer a montagem, pegando o Domain model e filtrando o que quero pro Representation model.
@AllArgsConstructor
@Component //componente do spring
public class EntregaAssembler {
    private ModelMapper modelMapper;
    
    public EntregaModel toModel(Entrega entrega){
        return modelMapper.map(entrega, EntregaModel.class);
    }

    public List<EntregaModel> toCollectionModel(List<Entrega> entregas){
        return entregas.stream()
        .map(this::toModel)
        .collect(Collectors.toList()); //aplico uma função aos elementos da stream e retorno uma stream com o resultado.
    }

    public Entrega toEntity(EntregaInput entregaInput){
        return modelMapper.map(entregaInput, Entrega.class);
    }
}
