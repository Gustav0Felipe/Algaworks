package com.inicio.teste.Domain.Service;

import org.springframework.stereotype.Service;

import com.inicio.teste.Domain.Exception.EntidadeNotFoundException;
import com.inicio.teste.Domain.Model.Entrega;
import com.inicio.teste.Domain.Repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class BuscaEntregaService {

    private EntregaRepository entregaRepository;

    public Entrega buscar(Long entregaId){
         return entregaRepository.findById(entregaId)
        .orElseThrow(() -> new EntidadeNotFoundException("Entrega não encontrada."));

    }
}
