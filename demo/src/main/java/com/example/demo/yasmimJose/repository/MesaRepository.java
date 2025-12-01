package com.example.demo.yasmimJose.repository;

import com.example.demo.yasmimJose.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    // Busca mesa pelo número (único)
    Optional<Mesa> findByNumero(Integer numero);

    // Outros métodos CRUD já estão disponíveis: save, findAll, findById, deleteById
}
