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

import com.iesvdc.acceso.zapapp.models.Categoria;
import com.iesvdc.acceso.zapapp.repositories.RepoCategoria;

import lombok.NonNull;

@Controller
@RequestMapping("/categorias")
public class ControllerCategoria {
    @Autowired
    private RepoCategoria repoCategoria;


    @GetMapping("")
    public String findAll(Model modelo) {
        modelo.addAttribute(
            "categorias",  repoCategoria.findAll());
        return "categorias/categorias";
    }

    @GetMapping("/add")
    public String addForm(Model modelo) {
        modelo.addAttribute("categorias", repoCategoria.findAll());        
        return "categorias/add";
    }

    @PostMapping("/add")
    public String postMethodName(
        @ModelAttribute("categoria") Categoria categoria)  {
        repoCategoria.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(
            @PathVariable(name = "id") @NonNull Long id,
            Model modelo) {
        try {
            Optional<Categoria> categoria = repoCategoria.findById(id);
            if (categoria.isPresent()){
                // si existe la categoria
                modelo.addAttribute(
                    "categoria", categoria.get());
                return "categorias/delete";
            } else {
                return "error";
            }

        } catch (Exception e) {
            return "error";
        }
    }
    

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable("id") @NonNull Long id) {
        try {
            repoCategoria.deleteById(id);    
        } catch (Exception e) {
            return "error";
        }
        
        return "redirect:/categorias";
    }


    @GetMapping("/edit/{id}")
    public String editForm(
        @PathVariable @NonNull Long id,
        Model modelo) {

            Optional<Categoria> categoria = 
                repoCategoria.findById(id);
            List<Categoria> categorias = 
                repoCategoria.findAll();
                
            if (categoria.isPresent()){
                modelo.addAttribute("categoria", categoria.get());
                modelo.addAttribute("categorias", categorias);
                return "categorias/edit";
            } else {
                modelo.addAttribute(
                    "mensaje", 
                    "Categoria no encontrada");
                return "error";
            }
            
    }

    
}