package com.eam.IngSoft1.Controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

/*
import co.com.eam.avanzada.domain.Usuario;
import co.com.eam.avanzada.repository.IUsuarioRepository;
*/

@Controller
public class AppController {
	/*
	private final IUsuarioRepository IusuarioRepository;
	
	@Autowired
	public AppController(IUsuarioRepository IusuarioRepository) {
		this.IusuarioRepository = IusuarioRepository;
	}
	*/

	@GetMapping({"/","/login"})
	public String login() {
		return "login";
	}
	
	@GetMapping({"/admin/login-empleados"})
	public String loginEmpleados() {
		return "login-empleados";
	}
	
	@GetMapping({"/admin/home-empleado"})
	public String index() {
		return "index";
	}
	
	@GetMapping({"/home"})
	public String menu() {
		return "homePageUsuario";
	}
	/*
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	*/
	
	//@GetMapping("/admin")
	//public String admin() {
	//	return "admin";
	//}
	
	@GetMapping("/header-admin")
	public String headerAdmin() {
		return "header-admin";
	}
	
}
