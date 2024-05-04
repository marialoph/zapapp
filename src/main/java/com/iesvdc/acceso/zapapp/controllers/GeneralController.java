package com.iesvdc.acceso.zapapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.iesvdc.acceso.zapapp.models.Rol;
import com.iesvdc.acceso.zapapp.models.RolUsuario;
import com.iesvdc.acceso.zapapp.models.Usuario;
import com.iesvdc.acceso.zapapp.repositories.RepoRolUsuario;
import com.iesvdc.acceso.zapapp.repositories.RepoUsuario;

import io.micrometer.common.lang.NonNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Clase controlador para los métodos generales de la Web:
@Controller
public class GeneralController {

    @Autowired
    RepoUsuario repoUsuario;

    @Autowired
    RepoRolUsuario repoRolUsuario;
    
    @GetMapping("/ayuda")
    public String showAyuda() {
        return "ayuda";
    }

    @GetMapping("/acerca")
    public String showAcerca() {
        return "acerca";
    }

    @GetMapping("/error")
    public String showError() {
        return "error";
    }

    @GetMapping("/")
    public String showIndex() {
        return "index";
    }

    // Añadir otro método PostMapping("/login") si el login se personaliza con éste:
    @GetMapping("/login") 
    public String showLogin() {
        return "login";
    }
    
    // @GetMapping("/denegado")
    // public String showLogin() {
    //     return "denegado";
    // }
    
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }
    @PostMapping("/register")
    public String register(
        Model model, 
        @NonNull @RequestParam String username, 
        @NonNull @RequestParam String password, 
        @NonNull @RequestParam String nombre,
        @NonNull @RequestParam String apellidos,
        @NonNull @RequestParam String email) {
          
        Usuario usuario = new Usuario();
        BCryptPasswordEncoder passwords = new BCryptPasswordEncoder();

            try{
                usuario.setUsername(username);
                usuario.setPassword(passwords.encode(password));
                usuario.setNombre(nombre);
                usuario.setApellido(apellidos);
                usuario.setEmail(email);
                usuario.setEnabled(true); 
                usuario=repoUsuario.save(usuario);

                RolUsuario rolUsuario = new RolUsuario();
                rolUsuario.setRol(Rol.CLIENTE);
                rolUsuario.setUsuario(usuario);
                repoRolUsuario.save(rolUsuario);


            model.addAttribute("titulo", "Alta nuevo usuario");
            model.addAttribute("mensaje", "El usuario se está dando de alta");
            
            return "redirect:/";
            }catch(Exception e){
                model.addAttribute("titulo", "ERROR al dar de alta al nuevo usuario");
                model.addAttribute("mensaje", "El usuario se se puede dar de alta");
                return "error";
            }

        
    }
}
