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
	
	//Metodo Para Crear Usuario
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ingresoUsuario")
    public String showSignUpForm(Usuario empleado) {
        return "Usuario/addUsuario";
    }
    
    @PostMapping("/addusuario")
    public String addUsuario(@Valid Usuario empleado, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Usuario/addUsuario";
        }
        repositorioEmpleado.save(empleado);
        model.addAttribute("usuario", repositorioEmpleado.findAll());
        return "redirect:/listadoUsuario";
    }
    
    
    //Metodo Para Actualizar usuario
    @GetMapping("/editUsuario/{dni}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateForm(@PathVariable("dni") int dni, Model model) {
    	Usuario empleado = repositorioEmpleado.findById(dni).orElseThrow(() -> new IllegalArgumentException("Invalido Empleado id:" + dni));
        model.addAttribute("usuario", empleado);
        return "Usuario/updateUsuario";
    }
    
    
    @PostMapping("/updateUsuario/{dni}")
    public String updateEmpleado(@PathVariable("dni") int dni,  Usuario empleado, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	empleado.setDni(dni);
            return "Usuario/updateUsuario";
        }
        
        repositorioEmpleado.save(empleado);
        model.addAttribute("usuario", repositorioEmpleado.findAll());
        return "redirect:/listadoUsuario";
    }
    
    
   
    //Metodo para Eliminar usuario
    @GetMapping("/deleteUsuario/{dni}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteEmpleado(@PathVariable("dni") int dni, Model model) {
    	Usuario empleado = repositorioEmpleado.findById(dni).orElseThrow(() -> new IllegalArgumentException("Invalido Empleado id:" + dni));
        repositorioEmpleado.delete(empleado);
        model.addAttribute("usuario", repositorioEmpleado.findAll());
        return "redirect:/listadoUsuario";
    }
    
    
    
    //Listado de empleado
  	@GetMapping("/listadoUsuario")
  	//@PreAuthorize("hasRole('ROLE_ADMIN')")
  	public String list(Usuario empleado, Model model) {
  		model.addAttribute("usuario", repositorioEmpleado.findAll());
        return "/Usuario/listadoUsuario";
  	}
	
}
