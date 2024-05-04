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

import com.iesvdc.acceso.zapapp.models.Producto;
import com.iesvdc.acceso.zapapp.repositories.RepoCategoria;
import com.iesvdc.acceso.zapapp.repositories.RepoProducto;
import com.iesvdc.acceso.zapapp.repositories.RepoUsuario;

import lombok.NonNull;

@Controller
@RequestMapping("/productos")
public class ControllerProducto {
    @Autowired
    RepoProducto repoProducto;

    @Autowired
    RepoCategoria repoCategoria;

    @Autowired
    RepoUsuario repoUsuario;

    // Devolver lista de productos:
    @GetMapping(path = "/")
    public String findAll(Model modelo) {
        List<Producto> productos = repoProducto.findAll();
        modelo.addAttribute("productos", productos);
        return "productos/productos";
    }

    // Devolver lista de productos:
    @GetMapping("")
    public String findAllUsers(Model modelo) {
        return findAll(modelo);
    }

    // Devuelve el formulario para añadir un nuevo producto:
    @GetMapping("/add")
    public String addProducto(Model modelo) {
        modelo.addAttribute("producto", new Producto());
        modelo.addAttribute("categorias", repoCategoria.findAll());
        modelo.addAttribute("usuarios", repoUsuario.findAll());
        return "productos/add";
    }

    // Redirecciona para ver todos los productos después de añadir uno nuevo:
    @PostMapping("/add")
    public String postMethodName(
            @ModelAttribute("producto") Producto producto) {
        repoProducto.save(producto);
        return "redirect:/productos";
    }
    
    // Editar productos:
    @GetMapping("/edit/{id}")
    public String editProductosForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Producto> productos = repoProducto.findById(id);
        if (productos.isPresent()) {
            modelo.addAttribute(
                    "productos", productos.get());
            modelo.addAttribute(
                    "categorias", repoCategoria.findAll());
            modelo.addAttribute(
                    "usuarios", repoUsuario.findAll());
            return "productos/edit";
        } else {
            modelo.addAttribute("mensaje", "El producto consultado no existe.");
            return "error";
        }
    }
    
    // Confirmar que el producto se ha editado:
    @PostMapping("/edit/{id}")
    public String editProductos(@ModelAttribute("productos") Producto productos) {
        repoProducto.save(productos);
        return "redirect:/productos";
    }
    
    // Eliminar producto:
    @GetMapping("/delete/{id}")
    public String deleteProductosForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Producto> producto = repoProducto.findById(id);
        if (producto.isPresent()) {
            modelo.addAttribute("producto", producto.get());
        } else {
            modelo.addAttribute("mensaje", "El producto consultado no existe.");
            return "error";
        }
        return "productos/delete";
    }
    
    // Confirmar que se ha eliminado:
    @PostMapping("/delete/{id}")
    public String deleteProductos(Model modelo, @PathVariable("id") @NonNull Long id) {
        try {
            repoProducto.findById(id);

        } catch (Exception e) {
            modelo.addAttribute(
                    "mensaje", "El producto no se puede eliminar.");
            return "error";
        }

        return "redirect:/productos";
    }
}

// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.lang.NonNull;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.iesvdc.acceso.zapapp.models.Producto;
// import com.iesvdc.acceso.zapapp.repositories.RepoProducto;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;

// @Controller
// @RequestMapping("/productos")
// public class ControllerProducto {
// @Autowired
// RepoProducto repoProducto;

// // Devuelve la lista de productos:
// @GetMapping("/")
// public String findAll(Model modelo) {
// modelo.addAttribute(
// "productos",
// repoProducto.findAll());
// return "productos/productos";
// }

// @GetMapping("")
// public String findAllBis(Model modelo) {
// return findAll(modelo);
// }

// // Carga el formulario para crear un nuevo producto:
// @GetMapping("/add")
// public String createForm(Model modelo) {
// modelo.addAttribute(
// "productos",
// repoProducto.findAll());
// return "productos/add";
// }

// // Guarda el producto nuevo en la BBDD:
// @PostMapping("/add")
// public String postMethodName(
// @ModelAttribute("producto") Producto producto) {
// repoProducto.save(producto);
// return "redirect:/productos";
// }

// // Carga el formulario para borrar un producto de la BBDD (según su ID):
// @GetMapping("/delete/{id}")
// public String deleteForm(
// @PathVariable(name = "id") @NonNull Long id,
// Model modelo) {
// try {
// Optional<Producto> productoABorrar = repoProducto.findById(id);
// if (productoABorrar.isPresent()) {
// // Si existe el producto con la ID:
// modelo.addAttribute(
// "producto", productoABorrar.get());
// return "productos/delete";
// } else {
// return "error";
// }
// } catch (Exception e) {
// return "error";
// }
// }

// // Borra un producto de la BBDD (según su ID):
// @PostMapping("/delete/{id}")
// public String delete(
// @PathVariable("id") @NonNull Long id) {
// try {
// repoProducto.deleteById(id);
// } catch (Exception e) {
// return "error";
// }

// return "redirect:/productos";
// }

// // Formulario para actualizar un producto:
// @GetMapping("/edit/{id}")
// public String updateForm(
// @PathVariable @NonNull Long id,
// Model modelo) {
// Optional<Producto> productoAActualizar = repoProducto.findById(id);
// List<Producto> productos = repoProducto.findAll();

// if (productoAActualizar.isPresent()) {
// modelo.addAttribute("producto", productoAActualizar.get());
// modelo.addAttribute("productos", productos);
// return "productos/edit";
// } else {
// modelo.addAttribute("mensaje", "Producto no encontrado");
// return "error";
// }
// }

// }
