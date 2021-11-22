package com.eam.IngSoft1.Controller;

import java.text.ParseException;
import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
/*
import co.com.eam.avanzada.domain.Usuario;
import co.com.eam.avanzada.repository.IUsuarioRepository;
*/

import com.eam.IngSoft1.IRepository.ICategoriaRepository;
import com.eam.IngSoft1.IRepository.IProductoRepository;
import com.eam.IngSoft1.domain.Producto;

@Controller
public class AppController {
	/*
	private final IUsuarioRepository IusuarioRepository;
	
	@Autowired
	public AppController(IUsuarioRepository IusuarioRepository) {
		this.IusuarioRepository = IusuarioRepository;
	}
	*/
	
	private final IProductoRepository repositorioProducto;
	private final ICategoriaRepository categoriaRepository;
	
	@Autowired
	public AppController(IProductoRepository repositorioProducto, ICategoriaRepository categoriaRepository) {
		super();
		this.repositorioProducto = repositorioProducto;
		this.categoriaRepository = categoriaRepository;
	}

	@GetMapping({"/login"})
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
	
	@GetMapping({"/","/home"})
	public String menu(Model model) {
		ArrayList<Producto> todos = (ArrayList<Producto>) repositorioProducto.cargarProductosActivos();
 		ArrayList<Producto> mostrados = new ArrayList<Producto>();
 		if(mostrados.size()>=1) {
 			for (int i = todos.size()-1; i>=todos.size()-3; i--) {
 				mostrados.add(todos.get(i));
 			}
 		}
		model.addAttribute("productos", mostrados);
		model.addAttribute("categorias", categoriaRepository.findAll());
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
