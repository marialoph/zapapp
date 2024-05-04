package com.iesvdc.acceso.zapapp.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iesvdc.acceso.zapapp.models.Estado;
import com.iesvdc.acceso.zapapp.models.Pedido;
import com.iesvdc.acceso.zapapp.models.Usuario;

@Repository
public interface RepoPedido extends JpaRepository<Pedido, Long> {

    List<Pedido> findByEstadoAndCliente(Estado carrito, Usuario cliente);


   
}
