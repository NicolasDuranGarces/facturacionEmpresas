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

import com.eam.IngSoft1.IRepository.IBodegaRepository;
import com.eam.IngSoft1.domain.Bodega;

@Controller
public class BodegaController {

	private final IBodegaRepository repositorioBodega;
	
	@Autowired
	public BodegaController (IBodegaRepository repositorioBodega) {
		this.repositorioBodega = repositorioBodega;
	}
	
	//Metodo Para Crear Bodegas
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @GetMapping("/admin/ingresoBodega")
    public String showSignUpForm(Bodega bodega) {
        return "Bodega/addBodega";
    }
    
    @PostMapping("/addbodega")
    public String addBodega(@Valid Bodega bodega, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Bodega/addBodega";
        }
        repositorioBodega.save(bodega);
        model.addAttribute("bodega", repositorioBodega.findAll());
        return "redirect:/admin/listadoBodega";
    }
    
    
    //Metodo Para Actualizar Bodega
    @GetMapping("/admin/editBodega/{idBodega}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    public String showUpdateForm(@PathVariable("idBodega") int idBodega, Model model) {
    	Bodega bodega = repositorioBodega.findById(idBodega).orElseThrow(() -> new IllegalArgumentException("Invalido Bodega idBodega:" + idBodega));
        model.addAttribute("bodega", bodega);
        return "Bodega/updateBodega";
    }
    
    
    @PostMapping("/admin/updateBodega/{idBodega}")
    public String updateBodega(@PathVariable("idBodega") int idBodega,@Valid Bodega bodega, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	bodega.setIdBodega(idBodega);
            return "Bodega/updateBodega";
        }
        
        repositorioBodega.save(bodega);
        model.addAttribute("bodega", repositorioBodega.findAll());
        return "redirect:/admin/listadoBodega";
    }
    
    
    //Metodo para Eliminar Bodegas
    @GetMapping("/admin/deleteBodega/{idBodega}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    public String deleteBodega(@PathVariable("idBodega") int idBodega, Model model) {
    	Bodega bodega = repositorioBodega.findById(idBodega).orElseThrow(() -> new IllegalArgumentException("Invalido Bodega idBodega:" + idBodega));
        repositorioBodega.delete(bodega);
        model.addAttribute("bodega", repositorioBodega.findAll());
        return "redirect:/admin/listadoBodega";
    }
    
    
  	@GetMapping("/admin/listadoBodega")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
  	public String list(Bodega bodega, Model model) {
  		model.addAttribute("bodega", repositorioBodega.findAll());
        return "Bodega/listadoBodega";
  	}
	
}
