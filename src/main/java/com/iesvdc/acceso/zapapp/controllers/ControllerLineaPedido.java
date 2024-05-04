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

import com.iesvdc.acceso.zapapp.models.LineaPedido;
import com.iesvdc.acceso.zapapp.repositories.RepoLineaPedido;
import com.iesvdc.acceso.zapapp.repositories.RepoPedido;
import com.iesvdc.acceso.zapapp.repositories.RepoProducto;

import lombok.NonNull;

@Controller
@RequestMapping("/lineaPedidos")
public class ControllerLineaPedido {
    @Autowired
    private RepoLineaPedido repoLineaPedido;

    @Autowired
    private RepoPedido repoPedido;

    @Autowired
    private RepoProducto repoProducto;


     // Devolver lista :
     @GetMapping(path = "/")
     public String findAll(Model modelo) {
         List<LineaPedido> lineaPedidos = repoLineaPedido.findAll();
         modelo.addAttribute("lineaPedidos", lineaPedidos);
         return "lineaPedidos/lineaPedidos";
       }
 
     // Devolver lista:
     @GetMapping("")
     public String findAllUsers(Model modelo) {
         return findAll(modelo);
     }


     
         /**
     * Devuelve el formulario para añadir un nuevo lineaPedidos
     */
    @GetMapping("/add")
    public String addLineaPedidos(Model modelo) {
        modelo.addAttribute("lineaPedidos", new LineaPedido());
        modelo.addAttribute("pedidos", repoPedido.findAll());
        modelo.addAttribute("productos", repoProducto.findAll());
        return "lineaPedidos/add";
    }
    /*
     * Editar lineaPedidos
    */
    @GetMapping("/edit/{id}")
    public String editLineaPedidosForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<LineaPedido> lineaPedidos = repoLineaPedido.findById(id);
        if (lineaPedidos.isPresent()) {
            modelo.addAttribute(
                    "lineaPedidos", lineaPedidos.get());
            modelo.addAttribute(
                "pedidos", repoPedido.findAll());
                modelo.addAttribute(
                "productos", repoProducto.findAll());
            return "lineaPedidos/edit";
        } else {
            modelo.addAttribute("mensaje", "Linea pedidos consultado no existe.");
            return "error";
        }
    }

           /*
     * Confirmar que se ha editado
     */
    @PostMapping("/edit/{id}")
    public String editLineaPedidos(@ModelAttribute("lineaPedidos") LineaPedido lineaPedidos) {
    repoLineaPedido.save(lineaPedidos);
    return "redirect:/lineaPedidos"; 
    }

    /**
     * Muestra un formulario para confirmar el borrado del rolUsuario
     */
    @GetMapping("/delete/{id}")
    public String deleteLineaPedidosForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<LineaPedido> lineaPedidos = repoLineaPedido.findById(id);
        if (lineaPedidos.isPresent())
            modelo.addAttribute(
                    "lineaPedidos", lineaPedidos.get());
        else {
            modelo.addAttribute(
                    "mensaje", "LineaPedidos consultado no existe.");
            return "error";
        }
        return "lineaPedidos/delete";
    }

    /**
     * Elimina el lineaPedidos de la base de datos si es posible
     */
    @PostMapping("/delete/{id}")
    public String deleteLineaPedidos(
            Model modelo,
            @PathVariable("id") @NonNull Long id) {
        try {
            repoLineaPedido.deleteById(id);
        } catch (Exception e) {
            modelo.addAttribute(
                    "mensaje", "La linea pedidos no se puede eliminar.");
            return "error";
        }

        return "redirect:/lineaPedidos";
    }
    
}
