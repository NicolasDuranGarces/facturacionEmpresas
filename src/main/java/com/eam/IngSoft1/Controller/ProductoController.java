package com.eam.IngSoft1.Controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.utils.ObjectUtils;
import com.eam.IngSoft1.IRepository.IBodegaRepository;
import com.eam.IngSoft1.IRepository.ICategoriaRepository;
import com.eam.IngSoft1.IRepository.IProductoRepository;
import com.eam.IngSoft1.IRepository.IProveedorRepository;
import com.eam.IngSoft1.domain.Producto;
import com.eam.IngSoft1.config.CloudinaryConfig;



@Controller
public class ProductoController {
	private final IProductoRepository repositorioProducto;
	private final ICategoriaRepository categoriaRepository;
	private final IProveedorRepository proveedorRepository;
	private final IBodegaRepository bodegaRepository;
	@Autowired
	public ProductoController(IProductoRepository repositorioProducto,ICategoriaRepository categoriaRepository,IProveedorRepository proveedorRepository,
			IBodegaRepository bodegaRepository) {
		this.repositorioProducto = repositorioProducto;
		this.categoriaRepository = categoriaRepository;
		this.proveedorRepository = proveedorRepository;
		this.bodegaRepository = bodegaRepository;
	}
	@Autowired
    private CloudinaryConfig cloudc;
	
	
	//Metodo Para Crear Producto
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ingresoProducto")
    public String showSignUpForm(Producto producto, Model model) {
    	model.addAttribute("categoriaproductos",categoriaRepository.findAll());
    	model.addAttribute("proveedores",proveedorRepository.findAll());
    	model.addAttribute("bodegas",bodegaRepository.findAll());
        return "Producto/addProducto";
    }
    
    @PostMapping("/addproducto")
    public String addProducto(@Valid Producto producto, BindingResult result, Model model,@RequestParam("file") MultipartFile file) {
        if (result.hasErrors()) {
            return "Producto/addProducto";
        }
        
        try {
        	Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            System.out.println(uploadResult.get("url").toString());
            producto.setUrlFoto(uploadResult.get("url").toString());	
        } catch (Exception e) {
        	System.out.println(e.getMessage());
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
        model.addAttribute("categoriaproductos",categoriaRepository.findAll());
    	model.addAttribute("proveedores",proveedorRepository.findAll());
    	model.addAttribute("bodegas",bodegaRepository.findAll());
        return "Producto/updateProducto";
    }
    
    
    @PostMapping("/updateProducto/{idProducto}")
    public String updateProducto(@PathVariable("idProducto") int idProducto,  Producto producto, BindingResult result, Model model ,@RequestParam("file") MultipartFile file, @RequestParam("cambioUrl") boolean cambioUrl) {
        if (result.hasErrors()) {
        	producto.setIdProducto(idProducto);
        	model.addAttribute("categoriaproductos",categoriaRepository.findAll());
        	model.addAttribute("proveedores",proveedorRepository.findAll());
        	model.addAttribute("bodegas",bodegaRepository.findAll());
            return "Producto/updateProducto";
        }
        
        if (cambioUrl) {
			try {
	            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
	            System.out.println(uploadResult.get("url").toString());
	            producto.setUrlFoto(uploadResult.get("url").toString());	
	        } catch (Exception e) {
	        	System.out.println(e.getMessage());
	        }
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
        return "Producto/listadoProducto";
  	}
  	
  
}
