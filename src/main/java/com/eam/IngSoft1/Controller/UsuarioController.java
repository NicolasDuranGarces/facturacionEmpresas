package com.eam.IngSoft1.Controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.eam.IngSoft1.IRepository.IUsuarioRepository;
import com.eam.IngSoft1.domain.Usuario;

import com.eam.IngSoft1.IRepository.IAuthorityRepository;
import com.eam.IngSoft1.util.Passgenerator;

import com.eam.IngSoft1.domain.Authority;


@Controller
public class UsuarioController {

	private final IUsuarioRepository repositorioEmpleado;
	
	@Autowired
   	private IAuthorityRepository authorityRepository;
	
	@Autowired
    Passgenerator passgenerator;
	
	@Autowired
	public UsuarioController (IUsuarioRepository repositorioEmpleado){
		this.repositorioEmpleado = repositorioEmpleado;
	}
	
	//Metodo Para Registrar Cliente
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
        
        Authority autorizacion= authorityRepository.findByAuthority("ROLE_USER");
        
        Set<Authority> authority= new HashSet<Authority>();
        authority.add(autorizacion);
        
        empleado.setAuthority(authority);
        empleado.setContrasena(passgenerator.enciptarPassword(empleado.getContrasena()));
        
        repositorioEmpleado.save(empleado);
        model.addAttribute("usuario", repositorioEmpleado.findAll());
        return "redirect:/login";
    }
    
    //Metodo de crear empleado desde el Administrador
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/admin/crear-empleado")
    public String showCreateEmpleadoForm(Usuario empleado) {
        return "Empleado/addEmpleado";
    }
    
    @PostMapping("/addempleado")
    public String addEmpleado(@Valid Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Empleado/addEmpleado";
        }
        
        Authority autorizacion= authorityRepository.findByAuthority("ROLE_EMPLEADO");
        
        Set<Authority> authority= new HashSet<Authority>();
        authority.add(autorizacion);
        
        usuario.setAuthority(authority);
        usuario.setContrasena(passgenerator.enciptarPassword(usuario.getContrasena()));
        
        repositorioEmpleado.save(usuario);
        model.addAttribute("usuario", repositorioEmpleado.findAll());
        return "redirect:/listadoUsuario";
    }
    
    
    //Metodo Para Actualizar usuario
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/editUsuario/{dni}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateForm(@PathVariable("dni") int dni, Model model) {
    	Usuario empleado = repositorioEmpleado.findById(dni).orElseThrow(() -> new IllegalArgumentException("Invalido Empleado id:" + dni));
        model.addAttribute("usuario", empleado);
        return "Usuario/updateUsuario";
    }
    
    
    @PostMapping("/admin/updateUsuario/{dni}")
    public String updateEmpleado(@PathVariable("dni") int dni,  Usuario empleado, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	empleado.setDni(dni);
            return "Usuario/updateUsuario";
        }
        
        repositorioEmpleado.save(empleado);
        model.addAttribute("usuario", repositorioEmpleado.findAll());
        return "redirect:/admin/listadoUsuario";
    }
    
    
   
    //Metodo para Eliminar usuario
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/deleteUsuario/{dni}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteEmpleado(@PathVariable("dni") int dni, Model model) {
    	Usuario empleado = repositorioEmpleado.findById(dni).orElseThrow(() -> new IllegalArgumentException("Invalido Empleado id:" + dni));
        repositorioEmpleado.delete(empleado);
        model.addAttribute("usuario", repositorioEmpleado.findAll());
        return "redirect:/listadoUsuario";
    }
    
    
    
    //Listado de usuarios
    @PreAuthorize("hasRole('ROLE_ADMIN')")
  	@GetMapping("/admin/listadoUsuario")
  	//@PreAuthorize("hasRole('ROLE_ADMIN')")
  	public String list(Usuario empleado, Model model) {
  		model.addAttribute("usuario", repositorioEmpleado.findAll());
        return "/Usuario/listadoUsuario";
  	}
	
}
