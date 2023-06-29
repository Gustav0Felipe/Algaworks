package com.inicio.teste.Domain.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inicio.teste.Domain.Model.Cliente;

//primeiro o tipo da entidade a ser gerenciada pelo repositorio, depois o tipo do ID.
//ao extender eu implemento diversar funcionalidades ao meu repositorio para consultar e gerenciar a entidade cliente.
 
//define que a interface é um componente spring com uma semantica bem definida, é um repositorio que gerencia entidades.
//um componente spring é um tipo onde as intancias desse tipo são gerenciadas pelo proprio container do spring.
//e com isso posso injetar uma instancia desse repositorio em objetos de outras classes com injestão de dependencias.
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

    //tenho q seguir padrão de nomenclatura do spring data jpa.
    List<Cliente> findByNome(String nome); 
    //seguindo esse padrão a implementação da consulta vem automaticamente, não preciso escrever nada no metodo.
    List<Cliente> findByNomeContaining(String nome); //pesquisa não exata, os que contiverem o que eu passar de String.

    Optional<Cliente> findById(Long id);

    Optional<Cliente> findByEmail(String email);

}

