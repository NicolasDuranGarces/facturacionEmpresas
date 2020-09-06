package com.eam.IngSoft1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.eam.IngSoft1.IRepository.IProductoRepository;
import com.eam.IngSoft1.domain.Producto;



@Controller
public class ProductoController {
	private final IProductoRepository repositorioProducto;
	@Autowired
	public ProductoController(IProductoRepository repositorioProducto) {
		this.repositorioProducto = repositorioProducto;
	}
	
	
	//Metodo Para Crear Producto
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ingresoProducto")
    public String showSignUpForm(Producto producto) {
        return "Categoria/addProducto";
    }
    
    @PostMapping("/addproducto")
    public String addProducto(Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Categoria/addProducto";
        }
        repositorioProducto.save(producto);
        model.addAttribute("productos", repositorioProducto.findAll());
        return "redirect:/Categoria/listadoProducto";
    }
    
    
    //Metodo Para Actualizar Producto
    @GetMapping("/editProducto/{idProducto}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateForm(@PathVariable("idProducto") int idProducto, Model model) {
    	Producto producto = repositorioProducto.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalido Producto id:" + idProducto));
        model.addAttribute("producto", producto);
        return "Categoria/updateProducto";
    }
    
    
    @PostMapping("Categoria/updateProducto/{idProducto}")
    public String updateProducto(@PathVariable("idProducto") int idProducto,  Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	producto.setIdProducto(idProducto);
            return "Categoria/updateProducto";
        }
        
        repositorioProducto.save(producto);
        model.addAttribute("productos", repositorioProducto.findAll());
        return "redirect:/Categoria/listadoProducto";
    }
    
    
   
    
    
    //Metodo para Eliminar Producto
    @GetMapping("/deleteProducto/{idProducto}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProducto(@PathVariable("idProducto") int idProducto, Model model) {
    	Producto producto = repositorioProducto.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalido Producto id:" + idProducto));
        repositorioProducto.delete(producto);
        model.addAttribute("productos", repositorioProducto.findAll());
        return "redirect:/Categoria/listadoProducto";
    }
    
    
    
    //Listado de Producto
  	@GetMapping("/listadoProducto")
  	//@PreAuthorize("hasRole('ROLE_ADMIN')")
  	public String list(Producto producto, Model model) {
  		model.addAttribute("productos", repositorioProducto.findAll());
        return "Categoria/listadoProducto";
  	}
}
