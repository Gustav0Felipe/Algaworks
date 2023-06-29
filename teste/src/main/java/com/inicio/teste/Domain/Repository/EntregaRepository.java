package com.inicio.teste.Domain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inicio.teste.Domain.Model.Entrega;


@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    
}
