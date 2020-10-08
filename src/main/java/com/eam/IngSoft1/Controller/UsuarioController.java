package com.eam.IngSoft1.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.eam.IngSoft1.IRepository.IUsuarioRepository;
import com.eam.IngSoft1.domain.Usuario;


@Controller
public class UsuarioController {

	private final IUsuarioRepository repositorioEmpleado;
	
	@Autowired
	public UsuarioController (IUsuarioRepository repositorioEmpleado){
		this.repositorioEmpleado = repositorioEmpleado;
	}
	
	//Metodo Para Crear Empleado
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ingresoEmpleado")
    public String showSignUpForm(Usuario empleado) {
        return "Empleado/addEmpleado";
    }
    
    @PostMapping("/addempleado")
    public String addEmpleado(@Valid Usuario empleado, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Empleado/addEmpleado";
        }
        repositorioEmpleado.save(empleado);
        model.addAttribute("empleado", repositorioEmpleado.findAll());
        return "redirect:/listadoEmpleados";
    }
    
    
    //Metodo Para Actualizar empleado
    @GetMapping("/editEmpleado/{DNI_empleado}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateForm(@PathVariable("DNI_empleado") int DNI_empleado, Model model) {
    	Usuario empleado = repositorioEmpleado.findById(DNI_empleado).orElseThrow(() -> new IllegalArgumentException("Invalido Empleado id:" + DNI_empleado));
        model.addAttribute("empleado", empleado);
        return "Empleado/updateEmpleado";
    }
    
    
    @PostMapping("/updateEmpleado/{DNI_empleado}")
    public String updateEmpleado(@PathVariable("DNI_empleado") int DNI_empleado,  Usuario empleado, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	empleado.setDni(DNI_empleado);
            return "Empleado/updateEmpleado";
        }
        
        repositorioEmpleado.save(empleado);
        model.addAttribute("empleado", repositorioEmpleado.findAll());
        return "redirect:/listadoEmpleados";
    }
    
    
   
    //Metodo para Eliminar empleado
    @GetMapping("/deleteEmpleado/{DNI_empleado}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteEmpleado(@PathVariable("DNI_empleado") int DNI_empleado, Model model) {
    	Usuario empleado = repositorioEmpleado.findById(DNI_empleado).orElseThrow(() -> new IllegalArgumentException("Invalido Empleado id:" + DNI_empleado));
        repositorioEmpleado.delete(empleado);
        model.addAttribute("empleado", repositorioEmpleado.findAll());
        return "redirect:/listadoEmpleados";
    }
    
    
    
    //Listado de empleado
  	@GetMapping("/listadoEmpleados")
  	//@PreAuthorize("hasRole('ROLE_ADMIN')")
  	public String list(Usuario empleado, Model model) {
  		model.addAttribute("empleado", repositorioEmpleado.findAll());
        return "/Empleado/listadoEmpleado";
  	}
	
}
