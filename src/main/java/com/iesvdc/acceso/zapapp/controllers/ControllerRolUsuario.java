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

import com.iesvdc.acceso.zapapp.models.RolUsuario;
import com.iesvdc.acceso.zapapp.repositories.RepoRolUsuario;
import com.iesvdc.acceso.zapapp.repositories.RepoUsuario;

import lombok.NonNull;

@Controller
@RequestMapping("/rolUsuario")
public class ControllerRolUsuario {
    @Autowired
    private RepoRolUsuario repoRolUsuario;

    @Autowired
    private RepoUsuario repoUsuario;

    // Devolver lista :
    @GetMapping(path = "/")
    public String findAll(Model modelo) {
        List<RolUsuario> rolUsuario = repoRolUsuario.findAll();
        modelo.addAttribute("rolUsuario", rolUsuario);
        return "rolUsuario/rolUsuario";
      }

    // Devolver lista:
    @GetMapping("")
    public String findAllUsers(Model modelo) {
        return findAll(modelo);
    }

         /**
     * Devuelve el formulario para añadir un nuevo rol Usuario
     */
    @GetMapping("/add")
    public String addRolUsuario(Model modelo) {
        modelo.addAttribute("rolUsuario", new RolUsuario());
        modelo.addAttribute("usuarios", repoUsuario.findAll());
        return "rolUsuario/add";
    }
    /*
     * Editar rolUsuario
    */
    @GetMapping("/edit/{id}")
    public String editRolUsuarioForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<RolUsuario> rolUsuario = repoRolUsuario.findById(id);
        if (rolUsuario.isPresent()) {
            modelo.addAttribute(
                    "rolUsuario", rolUsuario.get());
            modelo.addAttribute(
                "usuarios", repoUsuario.findAll());
            return "rolUsuario/edit";
        } else {
            modelo.addAttribute("mensaje", "El usuario y rol consultado no existe.");
            return "error";
        }
    }

          /*
     * Confirmar que se ha editado
     */
    @PostMapping("/edit/{id}")
    public String editRolUsuario(@ModelAttribute("rolUsuario") RolUsuario rolUsuario) {
    repoRolUsuario.save(rolUsuario);
    return "redirect:/rolUsuario"; 
    }

    /**
     * Muestra un formulario para confirmar el borrado del rolUsuario
     */
    @GetMapping("/delete/{id}")
    public String deleteRolUsuarioForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<RolUsuario> rolUsuario = repoRolUsuario.findById(id);
        if (rolUsuario.isPresent())
            modelo.addAttribute(
                    "rolUsuario", rolUsuario.get());
        else {
            modelo.addAttribute(
                    "mensaje", "El usuario y rol consultado no existe.");
            return "error";
        }
        return "rolUsuario/delete";
    }

    /**
     * Elimina el rolUsuario de la base de datos si es posible
     */
    @PostMapping("/delete/{id}")
    public String deleteRolUsuario(
            Model modelo,
            @PathVariable("id") @NonNull Long id) {
        try {
            repoRolUsuario.deleteById(id);
        } catch (Exception e) {
            modelo.addAttribute(
                    "mensaje", "El usuario y rol no se puede eliminar.");
            return "error";
        }

        return "redirect:/rolUsuario";
    }

}
