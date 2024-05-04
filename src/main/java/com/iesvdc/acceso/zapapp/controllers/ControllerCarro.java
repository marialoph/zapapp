package com.iesvdc.acceso.zapapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iesvdc.acceso.zapapp.models.Estado;
import com.iesvdc.acceso.zapapp.models.LineaPedido;
import com.iesvdc.acceso.zapapp.models.Pedido;
import com.iesvdc.acceso.zapapp.models.Producto;
import com.iesvdc.acceso.zapapp.models.Usuario;
import com.iesvdc.acceso.zapapp.repositories.RepoCategoria;
import com.iesvdc.acceso.zapapp.repositories.RepoLineaPedido;
import com.iesvdc.acceso.zapapp.repositories.RepoPedido;
import com.iesvdc.acceso.zapapp.repositories.RepoProducto;
import com.iesvdc.acceso.zapapp.repositories.RepoUsuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.NonNull;


@Controller
@RequestMapping("/carro")
public class ControllerCarro {

    @Autowired
    RepoProducto repoProducto;

    @Autowired
    RepoCategoria repoCategoria;

    @Autowired
    RepoUsuario repoUsuario;

     @Autowired
    RepoPedido repoPedido;
    @Autowired
    RepoLineaPedido repoLineaPedido;

    // Todo - Devolver carro de la compra:
       private Usuario getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario cliente = repoUsuario.findByUsername(username).get(0);
        return cliente;
    }

    @GetMapping("/carro")
    public String findCarro(Model modelo) {
    List<LineaPedido> lineaPedidos = null;
    Usuario cliente = getLoggedUser();
    long total = 0;
    List<Pedido> pedidos = repoPedido.findByEstadoAndCliente(Estado.CARRITO, cliente);
        if(pedidos.size()>0){
            lineaPedidos = repoLineaPedido.findByPedido(pedidos.get(0));
            for(LineaPedido lp : pedidos.get(0).getLineaPedidos()){
                total +=lp.getCantidad()*lp.getProducto().getPrecio();
            }
        }
        modelo.addAttribute("pedido", pedidos.size()>0 ? pedidos.get(0): new Pedido());
        modelo.addAttribute("lineapedidos", lineaPedidos);
        modelo.addAttribute("total", total);
        return "carro/carro";
    }

    @GetMapping("/carro/add/{id}")
    public String addCarro(@PathVariable @NonNull Long id, Model modelo) {
        String vista = "carro/add";
        Optional <Producto> producto = repoProducto.findById(id);

        if(producto.isPresent()){
            modelo.addAttribute("producto", producto.get());
        }else{
            modelo.addAttribute("titulo", "Error al añadir a la cesta");
            modelo.addAttribute("mensaje", "No se ha podido encontrar el producto");
            vista = "error";
        }
    return vista; 
}
    
    
    // Todo - Mostrar formulario para añadir un producto a la cesta:
    @PostMapping("/carro/add/{id}")
    public String add(Model modelo, @PathVariable("id") @NonNull Long id, Integer cantidad) {
        
        String vista = "redirect:/carro";
        Optional <Producto> producto = repoProducto.findById(id);
        Usuario cliente = getLoggedUser();
        Pedido carrito = new Pedido();
        List<Pedido> pedidos = repoPedido.findByEstadoAndCliente(Estado.CARRITO, cliente);

        if(pedidos.size()>0){
            carrito = pedidos.get(0);
        }else{
            carrito.setCliente(cliente);
            carrito.setEstado(Estado.CARRITO);
            carrito=repoPedido.save(carrito);
        }
        if(producto.isPresent() && cantidad>0){
            if(cantidad<producto.get().getStock()){
                LineaPedido lineaPedido = new LineaPedido();
                lineaPedido.setProducto(producto.get());
                lineaPedido.setCantidad(cantidad);
                lineaPedido.setPedido(carrito);
                lineaPedido=repoLineaPedido.save(lineaPedido);
            }else{
                modelo.addAttribute("titulo","Error al añadir" + producto.get().getNombre() + " al carrito");
                modelo.addAttribute("mensaje", "No hay suficiente stock, quedan " + producto.get().getStock() + " unidades");
                vista = "error";
            }
        }else{
            modelo.addAttribute("titulo", "error al añadir a la cesta");
            modelo.addAttribute("mensaje", "No se ha podido encontrar ese producto");
            vista = "error";
        }

        return vista;
    }
    
    // Listado de productos para comprar:
    @GetMapping("/productos")
    public String shop(Model modelo) {
        modelo.addAttribute("productos", repoProducto.findAll());
        return "carro/productos";
    }

    // Todo - Formulario para añadir el producto con ese ID a la cesta:
    @GetMapping("/productos/{id}")
    public String addProductoForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Producto> producto = repoProducto.findById(id);
        
        return new String();
    }
    

    // Todo - Envio?
    @PostMapping("/productos/{id}")
    public String addProduct(@RequestBody String entity) {
        
        return entity;
    }
    
    

    
    
    

     



    
}
