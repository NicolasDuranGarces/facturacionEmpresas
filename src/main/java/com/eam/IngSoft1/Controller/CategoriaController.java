package com.eam.IngSoft1.Controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.eam.IngSoft1.IRepository.ICategoriaRepository;
import com.eam.IngSoft1.domain.Categoriaproducto;


@Controller
public class CategoriaController {
	private final ICategoriaRepository repositorioCategoria;
	@Autowired
	public CategoriaController(ICategoriaRepository repositorioCategoria) {
		this.repositorioCategoria = repositorioCategoria;
	}
	
	
	//Metodo Para Crear Categorias
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/ingresoCategoria")
    public String showSignUpForm(Categoriaproducto categoriaProducto) {
        return "Categoria/addCategoria";
    }
    
    @PostMapping("/addcategoria")
    public String addCategoria(@Valid Categoriaproducto categoriaProducto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Categoria/addCategoria";
        }
        repositorioCategoria.save(categoriaProducto);
        model.addAttribute("categoriaProductos", repositorioCategoria.findAll());
        return "redirect:/Categoria/listadoCategoria";
    }
    
    
    
    //Metodo Para Actualizar Categoria
    @GetMapping("/editCategoria/{id_categoriaProducto}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateForm(@PathVariable("id_categoriaProducto") int idCategoria, Model model) {
    	Categoriaproducto categoriaProducto = repositorioCategoria.findById(idCategoria).orElseThrow(() -> new IllegalArgumentException("Invalido categoria idCategoria:" + idCategoria));
        model.addAttribute("categoriaProducto", categoriaProducto);
        return "Categoria/updateCategoria";
    }
    
    
    @PostMapping("/updateCategoria/{id_categoriaProducto}")
    public String updateCategoria(@PathVariable("id_categoriaProducto") int idCategoria,  Categoriaproducto categoriaProducto, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	categoriaProducto.setId_categoriaProducto(idCategoria);
            return "Categoria/updateCategoria";
        }
        
        repositorioCategoria.save(categoriaProducto);
        model.addAttribute("categoriaProductos", repositorioCategoria.findAll());
        return "redirect:/Categoria/listadoCategoria";
    }
    
    
    
    
    
    //Metodo para Eliminar Categorias
    @GetMapping("/deleteCategoria/{id_categoriaProducto}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCategoria(@PathVariable("id_categoriaProducto") int idCategoria, Model model) {
    	Categoriaproducto categoriaProducto = repositorioCategoria.findById(idCategoria).orElseThrow(() -> new IllegalArgumentException("Invalido categoria idCategoria:" + idCategoria));
        repositorioCategoria.delete(categoriaProducto);
        model.addAttribute("categoriaProductos", repositorioCategoria.findAll());
        return "redirect:/Categoria/listadoCategoria";
    }
    
    
    //Listado de Categorias
  	@GetMapping("listadoCategorias")
  	//@PreAuthorize("hasRole('ROLE_ADMIN')")
  	public String list(Categoriaproducto categoriaProducto, Model model) {
  		model.addAttribute("Categoriaproductos", repositorioCategoria.findAll());
        return "Categoria/listadoCategoria";
  	}

}
