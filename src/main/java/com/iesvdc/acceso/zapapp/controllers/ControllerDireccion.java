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

import com.iesvdc.acceso.zapapp.models.Direccion;
import com.iesvdc.acceso.zapapp.repositories.RepoCodigoPostal;
import com.iesvdc.acceso.zapapp.repositories.RepoDireccion;
import com.iesvdc.acceso.zapapp.repositories.RepoUsuario;

import lombok.NonNull;

@Controller
@RequestMapping("/direccion")
public class ControllerDireccion {
    @Autowired
    private RepoDireccion repoDireccion;

    @Autowired
    private RepoCodigoPostal repoCodigoPostal;

    @Autowired
    private RepoUsuario repoUsuario;

     // Devolver lista de direcciones:
     @GetMapping(path = "/")
     public String findAll(Model modelo) {
         List<Direccion> direccion = repoDireccion.findAll();
         modelo.addAttribute("direcciones", direccion);
         return "direccion/direccion";
       }
 
     // Devolver lista de direcciones:
     @GetMapping("")
     public String findAllUsers(Model modelo) {
         return findAll(modelo);
     }

            /**
     * Devuelve el formulario para añadir un nueva direccion 
     */
    @GetMapping("/add")
    public String addDireccion(Model modelo) {
        modelo.addAttribute("direcciones", new Direccion());
        modelo.addAttribute("codigoPostal", repoCodigoPostal.findAll());
        modelo.addAttribute("usuarios", repoUsuario.findAll());
        return "direccion/add";
    }

    @PostMapping("/add")
    public String postMethodName(
        @ModelAttribute("direcciones") Direccion direccion)  {
        repoDireccion.save(direccion);
        return "redirect:/direccion";
    }

    /*
     * Editar direcciones
    */
    @GetMapping("/edit/{id}")
    public String editDireccionForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Direccion> direcciones = repoDireccion.findById(id);
        if (direcciones.isPresent()) {
            modelo.addAttribute(
                    "direcciones", direcciones.get());
            modelo.addAttribute(
                "codigoPostal", repoCodigoPostal.findAll());
                modelo.addAttribute(
                "usuarios", repoUsuario.findAll());
            return "direccion/edit";
        } else {
            modelo.addAttribute("mensaje", "La dirección consultada no existe.");
            return "error";
        }
    }

     /*
     * Confirmar que se ha editado
     */
    @PostMapping("/edit/{id}")
    public String editDireccion(@ModelAttribute("direccion") Direccion direcciones) {
    repoDireccion.save(direcciones);
    return "redirect:/direccion"; 
    }

     /**
     * Muestra un formulario para confirmar el borrado de la direccion
     */
    @GetMapping("/delete/{id}")
    public String deleteDireccionForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Direccion> direcciones = repoDireccion.findById(id);
        if (direcciones.isPresent())
            modelo.addAttribute(
                    "direcciones", direcciones.get());
        else {
            modelo.addAttribute(
                    "mensaje", "La direccion consultada no existe.");
            return "error";
        }
        return "direccion/delete";
    }

    /**
     * Elimina la direccion de la base de datos si es posible
     */
    @PostMapping("/delete/{id}")
    public String deleteDireccion(
            Model modelo,
            @PathVariable("id") @NonNull Long id) {
        try {
            repoDireccion.deleteById(id);
        } catch (Exception e) {
            modelo.addAttribute(
                    "mensaje", "La direccion no se puede eliminar.");
            return "error";
        }

        return "redirect:/direccion";
    }
    

}
