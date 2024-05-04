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

import com.iesvdc.acceso.zapapp.models.CodigoPostal;
import com.iesvdc.acceso.zapapp.repositories.RepoCodigoPostal;
import com.iesvdc.acceso.zapapp.repositories.RepoDireccion;
import com.iesvdc.acceso.zapapp.repositories.RepoUsuario;

import lombok.NonNull;

@Controller
@RequestMapping("/codigoPostal")
public class ControllerCodigoPostal {
    
    @Autowired
    private RepoCodigoPostal repoCodigoPostal;
    
    @Autowired
    private RepoDireccion repoDireccion;

    @Autowired
    private RepoUsuario repoUsuario;

    // Devolver lista de codigoPostal:
    @GetMapping(path = "/")
    public String findAll(Model modelo) {
        List<CodigoPostal> codigoPostal = repoCodigoPostal.findAll();
        modelo.addAttribute("codigoPostal", codigoPostal);
        return "codigoPostal/codigoPostal";
    }

    // Devolver lista de codigoPostal:
    @GetMapping("")
    public String findAllUsers(Model modelo) {
        return findAll(modelo);
    }

     /**
     * Devuelve el formulario para añadir un nuevo codigo postal
     */

    @GetMapping("/add")
    public String addCodigoPostal(Model modelo) {
        modelo.addAttribute("codigoPostal", new CodigoPostal());
        modelo.addAttribute("direcciones", repoDireccion.findAll());
        modelo.addAttribute("usuarios", repoUsuario.findAll());
        return "codigoPostal/add";
    
    }
    @PostMapping("/add")
    public String postMethodName(
        @ModelAttribute("codigoPostal") CodigoPostal codigoPostal)  {
        repoCodigoPostal.save(codigoPostal);
        return "redirect:/codigoPostal";
    }


    /*
     * Editar codigoPostal
    */
    @GetMapping("/edit/{id}")
    public String editCodigoPostalForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<CodigoPostal> codigoPostal = repoCodigoPostal.findById(id);
        if (codigoPostal.isPresent()) {
            modelo.addAttribute(
                    "codigoPostal", codigoPostal.get());
            modelo.addAttribute(
                    "direcciones", repoDireccion.findAll());
            modelo.addAttribute("usuarios", repoUsuario.findAll());
            return "codigoPostal/edit";
        } else {
            modelo.addAttribute("mensaje", "El codigo postal consultado no existe.");
            return "error";
        }
    }
    /*
     * Confirmar que el codigo postal se ha editado
     */
    @PostMapping("/edit/{id}")
    public String editCodigoPostal(@ModelAttribute("codigoPostal") CodigoPostal codigoPostal) {
    repoCodigoPostal.save(codigoPostal);
    return "redirect:/codigoPostal"; 
}

/*Eliminar codigo Postal */
// @GetMapping("/delete")
// public String deleteCodigoPostalForm(Model modelo, @RequestParam("codigoPostal") String codigoPostal) {
//     Optional<CodigoPostal> codigoPostalOpt = repoCodigoPostal.findByCodigoPostal(codigoPostal);
//     if (codigoPostalOpt.isPresent()) {
//         modelo.addAttribute("codigoPostal", codigoPostalOpt.get());
//     } else {
//         modelo.addAttribute("mensaje", "El código postal consultado no existe.");
//         return "error";
//     }
//     return "codigoPostal/delete";
// }

// /*Confirmar que se ha eliminado */
// @PostMapping("/delete")
// public String deleteCodigoPostal(Model modelo, @RequestParam("codigoPostal") String codigoPostal) {
//     try {
//         repoCodigoPostal.findByCodigoPostal(codigoPostal);
        
//     } catch (Exception e) {
//         modelo.addAttribute(
//                 "mensaje", "El código postal no se puede eliminar.");
//         return "error";
//     }

//     return "redirect:/codigoPostal"; 
// }
 



}