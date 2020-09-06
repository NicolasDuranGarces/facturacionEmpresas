package com.eam.IngSoft1.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.eam.IngSoft1.IRepository.IProveedorRepository;
import com.eam.IngSoft1.domain.Proveedor;


@Controller
public class ProveedorController {
	
	private final IProveedorRepository repositorioProveedor;
	
	@Autowired
	public ProveedorController(IProveedorRepository repositorioProveedor) {
		this.repositorioProveedor = repositorioProveedor;
	}
	
	
	//Metodo Para Crear Proveedor
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ingresoProveedor")
    public String showSignUpForm(Proveedor proveedor) {
        return "Categoria/addProveedor";
    }
    
    @PostMapping("Categoria/addproveedor")
    public String addProveedor(Proveedor proveedor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Categoria/addProveedor";
        }
        repositorioProveedor.save(proveedor);
        model.addAttribute("proveedores", repositorioProveedor.findAll());
        return "redirect:/Categoria/listadoProveedor";
    }
    
    
    //Metodo Para Actualizar Proveedor
    @GetMapping("/editProveedor/{idProveedor}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateForm(@PathVariable("idProveedor") int idProveedor, Model model) {
    	Proveedor proveedor = repositorioProveedor.findById(idProveedor).orElseThrow(() -> new IllegalArgumentException("Invalido Proveedor id:" + idProveedor));
        model.addAttribute("proveedor", proveedor);
        return "Categoria/updateProveedor";
    }
    
    
    @PostMapping("Categoria/updateProveedor/{idProveedor}")
    public String updateProveedor(@PathVariable("idProveedor") int idProveedor,  Proveedor proveedor, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	proveedor.setIdProveedor(idProveedor);
            return "Categoria/updateProveedor";
        }
        
        repositorioProveedor.save(proveedor);
        model.addAttribute("proveedores", repositorioProveedor.findAll());
        return "redirect:/Categoria/listadoProveedores";
    }
    
    
   
    
    
    //Metodo para Eliminar Proveedor
    @GetMapping("/deleteProveedor/{idProveedor}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProveedor(@PathVariable("idProveedor") int idProveedor, Model model) {
    	Proveedor proveedor = repositorioProveedor.findById(idProveedor).orElseThrow(() -> new IllegalArgumentException("Invalido Proveedor id:" + idProveedor));
        repositorioProveedor.delete(proveedor);
        model.addAttribute("proveedor", repositorioProveedor.findAll());
        return "redirect:/Categoria/listadoProveedor";
    }
    
    
    
    //Listado de Proveedores
  	@GetMapping("/listadoProveedores")
  	//@PreAuthorize("hasRole('ROLE_ADMIN')")
  	public String list(Proveedor proveedor, Model model) {
  		model.addAttribute("proveedor", repositorioProveedor.findAll());
        return "Categoria/listadoProveedor";
  	}

}
