package com.eam.IngSoft1.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.eam.IngSoft1.util.Pagination;
import com.eam.IngSoft1.util.Notifications;
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
        	model.addAttribute("categoriaproductos",categoriaRepository.findAll());
        	model.addAttribute("proveedores",proveedorRepository.findAll());
        	model.addAttribute("bodegas",bodegaRepository.findAll());
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
        return "redirect:/admin/listadoProducto/page/1";
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
        System.out.println("HOLAAAA");
        return "redirect:/admin/listadoProducto/page/1";
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
        return "redirect:/admin/listadoProducto/page/1";
    }
     
    
    //Metodo para Eliminar Producto
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @GetMapping("/admin/deleteProducto/{idProducto}")
    public String deleteProducto(@PathVariable("idProducto") int idProducto, Model model) {
    	Producto producto = repositorioProducto.findById(idProducto).orElseThrow(() -> new IllegalArgumentException("Invalido Producto id:" + idProducto));
        repositorioProducto.delete(producto);
        model.addAttribute("productos", repositorioProducto.findAll());
        return "redirect:/admin/listadoProducto/page/1";
    }
    
  
    //Listado de Producto
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
  	@GetMapping("/admin/listadoProducto/page/{page}")
  	public String list(Producto producto, @PathVariable("page") int pageActual, Model model) {
    	int numProd = 5;
 		int max = numProd*pageActual;
 		int min = numProd*(pageActual-1);
 		ArrayList<Producto> todos = (ArrayList<Producto>) repositorioProducto.findAll();
 		ArrayList<Producto> mostrados = new ArrayList<Producto>();
 		ArrayList<Pagination> pages = new ArrayList<Pagination>();
 		for (int i = 0; i<todos.size(); i++) {
 			if (i>=min && i<max) {
 				mostrados.add(todos.get(i));
 			}
 		}
 				/////Paginación///////
 		int numPages = (todos.size()/numProd);
 		if (todos.size()%numProd!=0) {
 			numPages+=1;
 		}
 		for (int i = 1; i<=(numPages); i++) {
 			Pagination page = new Pagination();
 			page.setPagina(""+i);
 			page.setActiva(false);
 			if (i == pageActual) {
 				page.setActiva(true);
 			}
 			
 			pages.add(page);
 		}
 		int prev, next;
 		if (pageActual>1) {
 			prev = pageActual-1;
 		} else {
 			prev = pageActual;
 		}
 		
 		if (pageActual<numPages) {
 			next = pageActual+1;
 		} else {
 			next = pageActual;
 		}
 		 		
 		model.addAttribute("productos", mostrados );
 		model.addAttribute("pages",pages);
 		model.addAttribute("prev", prev);
 		model.addAttribute("next", next);
        return "Producto/listadoProducto";
  	}
  	
  	
  	// metodo productos disponibles ---------------------------------------------
 	@GetMapping("/productos-disponibles/page/{page}")
 	public String traerSoloDisponibles(@PathVariable("page") int pageActual, Model model) {
 		int numProd = 4;
 		int max = numProd*pageActual;
 		int min = numProd*(pageActual-1);
 		ArrayList<Producto> todos = (ArrayList<Producto>) repositorioProducto.cargarProductosActivos();
 		ArrayList<Producto> mostrados = new ArrayList<Producto>();
 		ArrayList<Pagination> pages = new ArrayList<Pagination>();
 		for (int i = 0; i<todos.size(); i++) {
 			if (i>=min && i<max) {
 				mostrados.add(todos.get(i));
 			}
 		}
 				/////Paginación///////
 		int numPages = (todos.size()/numProd);
 		if (todos.size()%numProd!=0) {
 			numPages+=1;
 		}
 		for (int i = 1; i<=(numPages); i++) {
 			Pagination page = new Pagination();
 			page.setPagina(""+i);
 			page.setActiva(false);
 			if (i == pageActual) {
 				page.setActiva(true);
 			}
 			
 			pages.add(page);
 		}
 		int prev, next;
 		if (pageActual>1) {
 			prev = pageActual-1;
 		} else {
 			prev = pageActual;
 		}
 		
 		if (pageActual<numPages) {
 			next = pageActual+1;
 		} else {
 			next = pageActual;
 		}
 		 		
 		model.addAttribute("productos", mostrados );
 		model.addAttribute("categorias", categoriaRepository.findAll());
 		model.addAttribute("pages",pages);
 		model.addAttribute("prev", prev);
 		model.addAttribute("next", next);
 		
 		return "explorarProductos";
 	}
 	
 	// metodo filtrar productos por categorias---------------------------------------------
  	@GetMapping("/categoriaproductos/{nombreCategoria}/page/{page}")
  	public String traerSoloPorCategoria(@PathVariable("nombreCategoria") String nombre, @PathVariable("page") int pageActual, Model model) {
 
  		int numProd = 2;
 		int max = numProd*pageActual;
 		int min = numProd*(pageActual-1);
 		ArrayList<Producto> todos = (ArrayList<Producto>) repositorioProducto.mostrarProductoFiltroCategoria(nombre);
 		ArrayList<Producto> mostrados = new ArrayList<Producto>();
 		ArrayList<Pagination> pages = new ArrayList<Pagination>();
 		for (int i = 0; i<todos.size(); i++) {
 			if (i>=min && i<max) {
 				mostrados.add(todos.get(i));
 			}
 		}
 				/////Paginación///////
 		int numPages = (todos.size()/numProd);
 		if (todos.size()%numProd!=0) {
 			numPages+=1;
 		}
 		for (int i = 1; i<=(numPages); i++) {
 			Pagination page = new Pagination();
 			page.setPagina(""+i);
 			page.setActiva(false);
 			if (i == pageActual) {
 				page.setActiva(true);
 			}
 			
 			pages.add(page);
 		}
 		int prev, next;
 		if (pageActual>1) {
 			prev = pageActual-1;
 		} else {
 			prev = pageActual;
 		}
 		
 		if (pageActual<numPages) {
 			next = pageActual+1;
 		} else {
 			next = pageActual;
 		}
  		
 		model.addAttribute("productos", mostrados );
 		model.addAttribute("categorias", categoriaRepository.findAll());
 		model.addAttribute("pages",pages);
 		model.addAttribute("prev", prev);
 		model.addAttribute("next", next);
 		model.addAttribute("nomCat", nombre);
 		
  		return "explorarProductosCategoria";
  	}
  	
 // metodo mostrar detalle de producto---------------------------------------------
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
   	
   	
  // -------------------Notificaciones---------------------------------------------
   	
   	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
   	@GetMapping("/admin/notificaciones")
   	public String notificaciones( Model model) {
   		List<Producto> productos = (List<Producto>) repositorioProducto.findAll();
   		List<Notifications> productoNotificacionesList = new ArrayList<Notifications>();
   		
   		for (int i = 0; i < productos.size(); i++) {
			if (productos.get(i).getCantidadActual()<productos.get(i).getMinimoInventario()) {
				Notifications notificacionesNotifications = new Notifications();
				notificacionesNotifications.setProducto(productos.get(i));
				notificacionesNotifications.setMensaje("Al Producto: \""+productos.get(i).getNombreProducto()
						+"\"\n - Le quedan pocas unidades \n - Unidades en stock: "+ productos.get(i).getCantidadActual());
				productoNotificacionesList.add(notificacionesNotifications);
			}
		}
   		
   		//Aca se supone que debemos debo de mandar las cosas al HTML
   		model.addAttribute("notificaciones",productoNotificacionesList);
   		
   		return "index";
   	}
  	
  
}
