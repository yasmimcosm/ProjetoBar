package com.example.demo.yasmimJose.repository;

import com.example.demo.yasmimJose.model.Pedido;
import com.example.demo.yasmimJose.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByConta(Conta conta);
}
