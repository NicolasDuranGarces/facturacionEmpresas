package com.eam.IngSoft1.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.eam.IngSoft1.IRepository.ICategoriaRepository;
import com.eam.IngSoft1.IRepository.IProductoRepository;
import com.eam.IngSoft1.IRepository.IProveedorRepository;
import com.eam.IngSoft1.domain.Producto;



@Controller
public class ProductoController {
	private final IProductoRepository repositorioProducto;
	private final ICategoriaRepository categoriaRepository;
	private final IProveedorRepository proveedorRepository;
	@Autowired
	public ProductoController(IProductoRepository repositorioProducto,ICategoriaRepository categoriaRepository,IProveedorRepository proveedorRepository) {
		this.repositorioProducto = repositorioProducto;
		this.categoriaRepository = categoriaRepository;
		this.proveedorRepository = proveedorRepository;
	}
	
	
	//Metodo Para Crear Producto
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ingresoProducto")
    public String showSignUpForm(Producto producto, Model model) {
    	model.addAttribute("categoriaproductos",categoriaRepository.findAll());
    	model.addAttribute("proveedores",proveedorRepository.findAll());
        return "Producto/addProducto";
    }
    
    @PostMapping("/addproducto")
    public String addProducto(@Valid Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Producto/addProducto";
        }
        repositorioProducto.save(producto);
        model.addAttribute("productos", repositorioProducto.findAll());
        return "redirect:/listadoProducto";
    }
    
    
    //Metodo Para Actualizar Producto
    @GetMapping("/editProducto/{idProducto}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateForm(@PathVariable("idProducto") int idProducto, Model model) {
    	Producto producto = repositorioProducto.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalido Producto id:" + idProducto));
        model.addAttribute("producto", producto);
        return "Categoria/updateProducto";
    }
    
    
    @PostMapping("/updateProducto/{idProducto}")
    public String updateProducto(@PathVariable("idProducto") int idProducto,  Producto producto, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	producto.setIdProducto(idProducto);
            return "Producto/updateProducto";
        }
        
        repositorioProducto.save(producto);
        model.addAttribute("productos", repositorioProducto.findAll());
        return "redirect:/listadoProducto";
    }
    
    
   
    
    
    //Metodo para Eliminar Producto
    @GetMapping("/deleteProducto/{idProducto}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProducto(@PathVariable("idProducto") int idProducto, Model model) {
    	Producto producto = repositorioProducto.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalido Producto id:" + idProducto));
        repositorioProducto.delete(producto);
        model.addAttribute("productos", repositorioProducto.findAll());
        return "redirect:/listadoProducto";
    }
    
    
    
    //Listado de Producto
  	@GetMapping("/listadoProducto")
  	//@PreAuthorize("hasRole('ROLE_ADMIN')")
  	public String list(Producto producto, Model model) {
  		model.addAttribute("productos", repositorioProducto.findAll());
        return "Categoria/listadoProducto";
  	}
}
