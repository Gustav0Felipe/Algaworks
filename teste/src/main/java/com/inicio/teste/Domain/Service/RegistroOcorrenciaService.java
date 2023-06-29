package com.inicio.teste.Domain.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inicio.teste.Domain.Model.Entrega;
import com.inicio.teste.Domain.Model.Ocorrencia;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistroOcorrenciaService {

    private BuscaEntregaService buscaEntregaService;

    @Transactional
    public Ocorrencia registrar(Long entregaId, String descricao){
        
        Entrega entrega = buscaEntregaService.buscar(entregaId);
    
        return entrega.adicionarOcorrencia(descricao);
        //não preciso de save pois o JPA sincroniza automaticamente quando coloco o Transactional.
        //as alterações no entrega que adicionei ocorrencias 
        //vão ser colocadas no banco de dados na tabela automaticamente.
    }
}
