package com.iesvdc.acceso.zapapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

import com.iesvdc.acceso.zapapp.models.Pedido;
import com.iesvdc.acceso.zapapp.repositories.RepoDireccion;
import com.iesvdc.acceso.zapapp.repositories.RepoLineaPedido;
import com.iesvdc.acceso.zapapp.repositories.RepoPedido;
import com.iesvdc.acceso.zapapp.repositories.RepoUsuario;

import lombok.NonNull;

@Controller
@RequestMapping("/pedidos")
public class ControllerPedido {
      
    @Autowired
    RepoPedido repoPedido;

    @Autowired
    RepoLineaPedido repoLineaPedido;
    
    @Autowired
    RepoDireccion repoDireccion;

    @Autowired
    RepoUsuario repoUsuario;

    
    // Devolver lista de pedidos:
    @GetMapping(path = "/")
    public String findAll(Model modelo) {
        List<Pedido> pedidos = repoPedido.findAll();
        modelo.addAttribute("pedidos", pedidos);
        return "pedidos/pedidos";
      }

    // Devolver lista de pedidos:
    @GetMapping("")
    public String findAllPedidos(Model modelo) {
        return findAll(modelo);
    }

         /**
     * Devuelve el formulario para añadir un nuevo pedido
     */
    @GetMapping("/add")
    public String addPedido(Model modelo) {
        modelo.addAttribute("pedidos", new Pedido());
        modelo.addAttribute("lineaPedido", repoLineaPedido.findAll());
        modelo.addAttribute("direcciones", repoDireccion.findAll());
        modelo.addAttribute("usuarios", repoUsuario.findAll());
        return "pedidos/add";
    }

    @PostMapping("/add")
    public String postMethodName(
        @ModelAttribute("pedido") Pedido pedido)  {
        repoPedido.save(pedido);
        return "redirect:/pedidos";
    }
    /*
     * Editar codigoPostal
    */
    @GetMapping("/edit/{id}")
    public String editPedidoForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Pedido> pedidos = repoPedido.findById(id);
        if (pedidos.isPresent()) {
            modelo.addAttribute(
                    "pedidos", pedidos.get());
            modelo.addAttribute(
                    "direcciones", repoDireccion.findAll());
            modelo.addAttribute(
                "lineaPedido", repoLineaPedido.findAll());
            modelo.addAttribute(
                "usuarios", repoUsuario.findAll());
            return "pedidos/edit";
        } else {
            modelo.addAttribute("mensaje", "El pedido consultado no existe.");
            return "error";
        }
    }
    /*
     * Confirmar que el pedido se ha editado
     */
    @PostMapping("/edit/{id}")
    public String editPedido(@ModelAttribute("pedidos") Pedido pedido) {
    repoPedido.save(pedido);
    return "redirect:/pedidos"; 
    }




}
