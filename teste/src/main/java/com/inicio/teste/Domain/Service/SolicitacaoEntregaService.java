package com.inicio.teste.Domain.Service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inicio.teste.Domain.Exception.NegocioException;
import com.inicio.teste.Domain.Model.Cliente;
import com.inicio.teste.Domain.Model.Entrega;
import com.inicio.teste.Domain.Model.StatusEntrega;
import com.inicio.teste.Domain.Repository.ClienteRepository;
import com.inicio.teste.Domain.Repository.EntregaRepository;

//por isso que faltou aqui AllArgsConstructor.
@Service
public class SolicitacaoEntregaService {
    private EntregaRepository entregaRepository;
    private ClienteRepository clienteRepository;

    //o spring injeta todos os respositorios que tenho em todos os serviçoes que tiverem construtor que receba
    //repositorios, o construtor permite ao spring injetar repositorios neste service.
    SolicitacaoEntregaService(EntregaRepository entregaRepository, ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
        this.entregaRepository = entregaRepository;
    }

    @Transactional
    public Entrega solicitar(Entrega entrega){
        Cliente cliente = clienteRepository.findById(entrega.getCliente().getId())
        .orElseThrow(() -> new NegocioException("cliente não encontrado.")); //findById retorna um Optional. 
        //pega o que tem dentro do optional e coloca na variavel cliente, se não tiver nada lance exceção.
        //para refatorar devo colocar esta variavel Optional no catalogoClienteService como um metodo get.
        entrega.setStatus(StatusEntrega.PENDENTE);
        entrega.setDataPedido(OffsetDateTime.now());
        entrega.setCliente(cliente); //se eu não fizesse tratamento do Optional daria erro.
        return entregaRepository.save(entrega);
    }
}
