package com.eam.IngSoft1.Controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @GetMapping("/admin/ingresoProducto")
    public String showSignUpForm(Producto producto, Model model) {
    	model.addAttribute("categoriaproductos",categoriaRepository.findAll());
    	model.addAttribute("proveedores",proveedorRepository.findAll());
    	model.addAttribute("bodegas",bodegaRepository.findAll());
        return "Producto/addProducto";
    }
    
    @PostMapping("/addproducto")
    public String addProducto(@Valid Producto producto, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
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
        return "redirect:/admin/listadoProducto";
    }
      
    
    //Metodo Para Actualizar Producto
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @GetMapping("/admin/editProducto/{idProducto}")
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
        return "redirect:/admin/listadoProducto";
    }
    
    
    
  //Actualizar Mercancia de los productos 
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @GetMapping("/admin/updateMercanciaProducto/{idProducto}")
    public String showUpdateInventario(@PathVariable("idProducto") int idProducto, Model model) {
    	Producto producto = repositorioProducto.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalido Producto id:" + idProducto));
        model.addAttribute("producto", producto);
        model.addAttribute("categoriaproductos",categoriaRepository.findAll());
    	model.addAttribute("proveedores",proveedorRepository.findAll());
    	model.addAttribute("bodegas",bodegaRepository.findAll());
        return "Producto/agregarMercanciaProducto";
    }
    
    @PostMapping("/updateMercancia/{idProducto}")
    public String updateMercanciaProducto(@PathVariable("idProducto") int idProducto, @RequestParam(value = "cantidadActualNueva") int cantidadNueva,  Producto producto, BindingResult result, Model model ) {
        if (result.hasErrors()) {
        	producto.setIdProducto(idProducto);
        	
        	model.addAttribute("categoriaproductos",categoriaRepository.findAll());
        	model.addAttribute("proveedores",proveedorRepository.findAll());
        	model.addAttribute("bodegas",bodegaRepository.findAll());
            return "Producto/agregarMercanciaProducto";
        }
        
        System.out.println(producto.toString());
        producto.toString();
    	producto = repositorioProducto.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalido Producto id:" + idProducto));
    	producto.setCantidadActual(producto.getCantidadActual()+cantidadNueva);
        repositorioProducto.save(producto);
        model.addAttribute("productos", repositorioProducto.findAll());
        return "redirect:/admin/listadoProducto";
    }
     
    
    //Metodo para Eliminar Producto
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @GetMapping("/admin/deleteProducto/{idProducto}")
    public String deleteProducto(@PathVariable("idProducto") int idProducto, Model model) {
    	Producto producto = repositorioProducto.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalido Producto id:" + idProducto));
        repositorioProducto.delete(producto);
        model.addAttribute("productos", repositorioProducto.findAll());
        return "redirect:/admin/listadoProducto";
    }
    
  
    //Listado de Producto
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
  	@GetMapping("/admin/listadoProducto")
  	public String list(Producto producto, Model model) {
  		model.addAttribute("productos", repositorioProducto.findAll());
        return "Producto/listadoProducto";
  	}
  	
  	
  	// metodo productos disponibles ---------------------------------------------
 	@GetMapping("/productos-disponibles")
 	public String traerSoloDisponibles(Model model) {
 		
 		model.addAttribute("productos", repositorioProducto.cargarProductosActivos());
 		model.addAttribute("categorias", categoriaRepository.findAll());
 		return "homePageUsuario";
 	}
 	
 	// metodo filtrar productos por categorias---------------------------------------------
  	@GetMapping("/categoriaproductos/{nombreCategoria}")
  	public String traerSoloPorCategoria(@PathVariable("nombreCategoria") String nombre, Model model) {
 
  		model.addAttribute("productos", repositorioProducto.mostrarProductoFiltroCategoria(nombre));
  		model.addAttribute("categorias", categoriaRepository.findAll());
  		return "homePageUsuario";
  	}
  	
 // metodo filtrar productos por categorias---------------------------------------------
   	@GetMapping("/detalleproducto/{idProducto}")
   	public String viewDetail(@PathVariable("idProducto") int idProducto, Model model) {
   		Producto producto = repositorioProducto.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalido Producto id:" + idProducto));
   		model.addAttribute("producto", producto);
   		return "Producto/detalleProducto";
   	}
   	
 // metodo filtrar productos por bodega---------------------------------------------
   	@GetMapping("/admin/productos-bodega/{idBodega}")
   	public String traerProductosBodega(@PathVariable("idBodega") int idBodega, Model model) {
  
   		model.addAttribute("productos", repositorioProducto.mostrarProductoFiltroBodega(idBodega));
   		
   		return "Bodega/listadoProductosBodega";
   	}
  	
  
}
