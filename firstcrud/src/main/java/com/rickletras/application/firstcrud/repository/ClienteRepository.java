package com.rickletras.application.firstcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rickletras.application.firstcrud.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}

