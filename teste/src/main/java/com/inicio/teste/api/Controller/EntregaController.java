package com.inicio.teste.api.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inicio.teste.Domain.Model.Entrega;
import com.inicio.teste.Domain.Repository.EntregaRepository;
import com.inicio.teste.Domain.Service.FinalizacaoEntregaService;
import com.inicio.teste.Domain.Service.SolicitacaoEntregaService;
import com.inicio.teste.api.Assembler.EntregaAssembler;
import com.inicio.teste.api.Model.EntregaModel;
import com.inicio.teste.api.Model.Input.EntregaInput;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/entregas")
public class EntregaController {

    private EntregaRepository entregaRepository;
    private SolicitacaoEntregaService solicitacaoEntregaService;
    private FinalizacaoEntregaService finalizacaoEntregaService;
    private EntregaAssembler entregaAssembler; //não é reconhecido pelo spring, um rest controller só tem de atributo outros
    //componentes spring, modelMapper é totalmente independente do Spring, uma biblioteca java.

    @GetMapping
    public List<EntregaModel> listar(){
        return entregaAssembler.toCollectionModel(entregaRepository.findAll());
    }

    @GetMapping("/{entregaId}")
    public ResponseEntity<EntregaModel> listarId(@PathVariable Long entregaId){
        return entregaRepository.findById(entregaId)
        .map(entrega -> ResponseEntity.ok(entregaAssembler.toModel(entrega)))
        .orElse(ResponseEntity.notFound().build());
            //Usando ModelMapper evito codigo BoilerPlate.
            /*
            {
            EntregaModel entregaModel = new EntregaModel();
            entregaModel.setId(entrega.getId());
            entregaModel.setNomeCliente(entrega.getCliente().getNome());
            entregaModel.setDestinatario(new DestinatarioModel());
            entregaModel.getDestinatario().setNome(entrega.getDestinatario().getNome());
            entregaModel.getDestinatario().setLogradouro(entrega.getDestinatario().getLogradouro());
            entregaModel.getDestinatario().setNumero(entrega.getDestinatario().getNumero());
            entregaModel.getDestinatario().setComplemento(entrega.getDestinatario().getComplemento());
            entregaModel.getDestinatario().setBairro(entrega.getDestinatario().getBairro());
            entregaModel.setTaxa(entrega.getTaxa());
            entregaModel.setStatus(entrega.getStatus());
            entregaModel.setDataPedido(entrega.getDataPedido());
            entregaModel.setDataFinalizacao(entrega.getDataFinalizacao());
            return ResponseEntity.ok(entregaModel);
            })
            */
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntregaModel solicitar(@Valid @RequestBody EntregaInput entregaInput){
        Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
        Entrega entregaSolicitada = solicitacaoEntregaService.solicitar(novaEntrega);
        return entregaAssembler.toModel(entregaSolicitada);
    }

    @PutMapping("/{entregaId}/finalizacao") 
    @ResponseStatus(HttpStatus.NO_CONTENT)//foi um sucesso mas não retorno nada.
    public void finalizar(@PathVariable Long entregaId){
        finalizacaoEntregaService.finalizar(entregaId);
    }

}
