package com.inicio.teste.Domain.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inicio.teste.Domain.Model.Entrega;
import com.inicio.teste.Domain.Repository.EntregaRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FinalizacaoEntregaService {
    
    private EntregaRepository entregaRepository;
    private BuscaEntregaService buscaEntregaService;

    @Transactional
    public void finalizar(Long entregaId){
        Entrega entrega = buscaEntregaService.buscar(entregaId);

        entrega.finalizar(); //regras de negocio não é bom por todas em service, devo usar tambem a entidade
        //para que não fique anemica(com poucas responsabilidades), isso ajuda no entedimento.
        entregaRepository.save(entrega);
    }
}
