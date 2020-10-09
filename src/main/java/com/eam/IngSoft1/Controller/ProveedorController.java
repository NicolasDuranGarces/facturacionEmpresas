package com.eam.IngSoft1.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @GetMapping("/admin/ingresoProveedor")
    public String showSignUpForm(Proveedor proveedor) {
        return "Proveedor/addProveedor";
    }
    
    @PostMapping("/addproveedor")
    public String addProveedor(@Valid Proveedor proveedor, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Proveedor/addProveedor";
        }
        repositorioProveedor.save(proveedor);
        model.addAttribute("proveedores", repositorioProveedor.findAll());
        return "redirect:/admin/listadoProveedores";
    }
    
    
    //Metodo Para Actualizar Proveedor
    @GetMapping("/admin/editProveedor/{idProveedor}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateForm(@PathVariable("idProveedor") int idProveedor, Model model) {
    	Proveedor proveedor = repositorioProveedor.findById(idProveedor).orElseThrow(() -> new IllegalArgumentException("Invalido Proveedor id:" + idProveedor));
        model.addAttribute("proveedor", proveedor);
        return "Proveedor/updateProveedor";
    }
    
    
    @PostMapping("/updateProveedor/{idProveedor}")
    public String updateProveedor(@PathVariable("idProveedor") int idProveedor,  Proveedor proveedor, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	proveedor.setIdProveedor(idProveedor);
            return "Proveedor/updateProveedor";
        }
        
        repositorioProveedor.save(proveedor);
        model.addAttribute("proveedores", repositorioProveedor.findAll());
        return "redirect:/admin/listadoProveedores";
    }
    
    
   
    //Metodo para Eliminar Proveedor
    @GetMapping("/admin/deleteProveedor/{idProveedor}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProveedor(@PathVariable("idProveedor") int idProveedor, Model model) {
    	Proveedor proveedor = repositorioProveedor.findById(idProveedor).orElseThrow(() -> new IllegalArgumentException("Invalido Proveedor id:" + idProveedor));
        repositorioProveedor.delete(proveedor);
        model.addAttribute("proveedores", repositorioProveedor.findAll());
        return "redirect:/admin/listadoProveedores";
    }
    
    
    
    //Listado de Proveedores
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
  	@GetMapping("/admin/listadoProveedores")
  	//@PreAuthorize("hasRole('ROLE_ADMIN')")
  	public String list(Proveedor proveedor, Model model) {
  		model.addAttribute("proveedores", repositorioProveedor.findAll());
        return "/Proveedor/listadoProveedor";
  	}

}
