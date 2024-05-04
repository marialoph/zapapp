package com.iesvdc.acceso.zapapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iesvdc.acceso.zapapp.models.LineaPedido;
import com.iesvdc.acceso.zapapp.models.Pedido;

@Repository
public interface RepoLineaPedido extends JpaRepository<LineaPedido, Long> {

    List<LineaPedido> findByPedido(Pedido pedido);

}
