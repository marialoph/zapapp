package com.iesvdc.acceso.zapapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iesvdc.acceso.zapapp.models.Direccion;
import com.iesvdc.acceso.zapapp.models.Telefono;
import com.iesvdc.acceso.zapapp.models.Usuario;
import com.iesvdc.acceso.zapapp.repositories.RepoDireccion;
import com.iesvdc.acceso.zapapp.repositories.RepoRolUsuario;
import com.iesvdc.acceso.zapapp.repositories.RepoTelefono;
import com.iesvdc.acceso.zapapp.repositories.RepoUsuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/usuarios")
public class ControllerUsuario {
    
    @Autowired
    private RepoUsuario repoUsuario;
    
    @Autowired
    private RepoDireccion repoDireccion;
    
    @Autowired
    private RepoTelefono repoTelefono;

    @Autowired 
    private RepoRolUsuario repoRolUsuario;

    // Devolver lista de usuarios:
    @GetMapping(path = "/")
    public String findAll(Model modelo) {
        List<Usuario> usuarios = repoUsuario.findAll();
        modelo.addAttribute("usuarios", usuarios);
        return "usuarios/usuarios";
    }

    // Devolver lista de usuarios:
    @GetMapping("")
    public String findAllUsers(Model modelo) {
        return findAll(modelo);
    }

    // Formulario para registrar un nuevo usuario:
    @GetMapping("/add")
    public String addUser(Model modelo) {
        modelo.addAttribute("usuario", new Usuario());
        modelo.addAttribute("telefonos", repoTelefono.findAll());
        modelo.addAttribute("direcciones", repoDireccion.findAll());
        modelo.addAttribute("roles", repoRolUsuario.findAll());
        return "usuarios/add";
    }
    
    /**  
     * Lanzar los datos recogidos del nuevo usuario a la BBDD: 
     * Usuario nuevo, listado de teléfonos y listado de direcciones del mismo.
    */
    @PostMapping("/add")
    public String addUser(@ModelAttribute("usuario") @NonNull Usuario usuario) {
        // Actualizar la lista de telefonos del nuevo usuario:
        List<Telefono> telefonos = usuario.getTelefonos();
        if (telefonos != null) {
            for (Telefono telefono : telefonos) {
                if (telefono != null) {
                    repoTelefono.save(telefono);
                }
            }
        }

        // Actualizar la lista de direcciones del nuevo usuario:
        List<Direccion> direcciones = usuario.getDirecciones();
        if (direcciones != null) {
            for (Direccion direccion : direcciones) {
                if (direccion != null) {
                    repoDireccion.save(direccion);
                }
            }
        }

        // Actualizar la lista de usuarios con el nuevo usuario:
        repoUsuario.save(usuario);

        return "redirect:/usuarios";
    }
    
    // Formulario para aceptar el borrado de un usuario seleccionado:
    @GetMapping("/delete/{id}")
    public String deleteUserForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Usuario> usuarioABorrar = repoUsuario.findById(id);
        if (usuarioABorrar.isPresent()) {
            modelo.addAttribute("usuario", usuarioABorrar.get());
        } else {
            modelo.addAttribute("mensaje", "Atención: El usuario indicado no existe");
            return "error";
        }
        return "usuarios/delete";
    }

    // Eliminación del usuario de la BBDD (si se puede):
    @PostMapping("/delete/{id}")
    public String deleteUser(Model modelo, @PathVariable("id") @NonNull Long id) {
        try {
            repoUsuario.deleteById(id);
        } catch (Exception e) {
            modelo.addAttribute("mensaje", "El usuario no puede ser eliminado: Tiene compras en su historial");
            return "error";
        }
        
        return "redirect:/usuario";
    }

    // Formulario para editar el usuario (¿Cliente?). Para guardar se hace POST a "/add":
    @GetMapping("/edit/{id}")
    public String editUserForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Usuario> usuarioAEditar = repoUsuario.findById(id);
        if (usuarioAEditar.isPresent()) {
            modelo.addAttribute("usuario", usuarioAEditar.get());
            modelo.addAttribute("roles", repoRolUsuario.findAll());
        } else {
            modelo.addAttribute("mensaje", "Atención: El usuario indicado no existe");
            return "error";
        }
        return "usuario/edit";
    }

    // Formulario para crear un teléfono al usuario ya cargado para editar:
    @GetMapping("/{id}/telefonos/add")
    public String usuarioAddPhoneForm(@PathVariable @NonNull Long id, Model modelo) {
        Optional<Usuario> usuarioAEditar = repoUsuario.findById(id);
        if (usuarioAEditar.isPresent()) {
            modelo.addAttribute("usuario", usuarioAEditar.get());
            modelo.addAttribute("roles", repoRolUsuario.findAll());
        } else {
            modelo.addAttribute("mensaje", "Atención: El usuario indicado no existe");
            return "error";
        }

        Telefono telefono = new Telefono();
        telefono.setUsuario(usuarioAEditar.get());

        modelo.addAttribute("telefono", telefono);
        modelo.addAttribute("usuario", usuarioAEditar.get());

        return "usuario/telefonos/add";
    }
    
    // Confirmación de la adición del teléfono al usuario escogido:
    @PostMapping("{id}/telefonos/add")
    public String usuarioAddPhone(@PathVariable @NonNull Long id,
            @ModelAttribute("telefono") @NonNull Telefono telefono, Model modelo) {
        
        Optional<Usuario> usuarioAEditar = repoUsuario.findById(id); 
        
        if (!usuarioAEditar.isPresent()) {
            modelo.addAttribute("mensaje", "Atención: El usuario indicado no existe");
            return "error";
        }
        
        repoTelefono.save(telefono);
        
        return "redirect:/usuarios/" + id + "/telefonos";
    }
    
    // LLamar a todos los teléfonos del usuario registrado:
    @GetMapping("/{id}/telefonos")
    public String getPhonesByIdUser(@PathVariable @NonNull Long id, Model modelo) {
        Optional<Usuario> usuarioAEditar = repoUsuario.findById(id); 
        
        if (!usuarioAEditar.isPresent()) {
            modelo.addAttribute("mensaje", "Atención: El usuario indicado no existe");
            return "error";
        }

        modelo.addAttribute("usuarios", repoUsuario.findAll());
        modelo.addAttribute("usuarioLogueado", usuarioAEditar.get());
        modelo.addAttribute("telefonos", usuarioAEditar.get().getTelefonos());

        return "usuarios/telefonos/telefonos";
    }

    // Formulario para editar los teléfonos del usuario registrado:
    @GetMapping("/{idUser}/telefonos/{idPhone}/edit")
    public String editPhonesByIdUserForm(
            @PathVariable @NonNull Long idUser, 
            @PathVariable @NonNull Long idPhone, Model modelo) {
        
        Optional<Usuario> usuarioAEditar = repoUsuario.findById(idUser); 
        Optional<Telefono> telefonoAEditar = repoTelefono.findById(idPhone);

        if (!usuarioAEditar.isPresent() || !telefonoAEditar.isPresent()) {
            modelo.addAttribute("mensaje", "El usuario/teléfono no existen");
            return "error";
        }

        modelo.addAttribute("usuario", usuarioAEditar.get());

        modelo.addAttribute("telefono", telefonoAEditar.get());

        return "usuarios/telefonos/edit";
    }

    // Formulario para borrar uno de los teléfonos del usuario logueado
    @GetMapping("/{idUser}/telefonos/{idPhone}/delete")
    public String deletePhonesByIdUserForm(
            @PathVariable @NonNull Long idUser,
            @PathVariable @NonNull Long idPhone, Model modelo) {

        Optional<Usuario> usuarioAEditar = repoUsuario.findById(idUser); 
        Optional<Telefono> telefonoABorrar = repoTelefono.findById(idPhone);
        
        if (!usuarioAEditar.isPresent() || !telefonoABorrar.isPresent()) {
            modelo.addAttribute("mensaje", "El usuario/teléfono no existen");
            return "error";
        }

        modelo.addAttribute("usuario", usuarioAEditar.get());

        modelo.addAttribute("telefono", telefonoABorrar.get());

        return "usuarios/telefonos/delete";
    }

    // Confirmación del borrado de teléfono del usuario logueado
    @PostMapping("/{idUser}/telefonos/{idPhone}/delete")
    public String deletePhonesByIdUser(
            @PathVariable @NonNull Long idUser,
            @PathVariable @NonNull Long idPhone, Model modelo) {
        
        Optional<Usuario> usuarioAEditar = repoUsuario.findById(idUser); 
        Optional<Telefono> telefonoABorrar = repoTelefono.findById(idPhone);

        if (!usuarioAEditar.isPresent() || !telefonoABorrar.isPresent()) {
            modelo.addAttribute("mensaje", "El usuario/teléfono no existen");
            return "error";
        }

        if (usuarioAEditar.get().getId() != telefonoABorrar.get().getUsuario().getId()) {
            modelo.addAttribute("mensaje", "El teléfono no pertenece al usuario señalado");
            return "error";
        }

        repoTelefono.delete(telefonoABorrar.get());

        return "redirect:/usuarios/" + usuarioAEditar.get().getId() + "/telefonos";
    }
    
}