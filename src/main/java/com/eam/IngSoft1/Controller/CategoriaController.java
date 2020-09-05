package com.eam.IngSoft1.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String showSignUpForm(Categoriaproducto categoria) {
        return "addCategoria";
    }
    
    @PostMapping("/addcategoria")
    public String addCategoria(Categoriaproducto categoria, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addCategoria";
        }
        repositorioCategoria.save(categoria);
        model.addAttribute("categorias", repositorioCategoria.findAll());
        return "redirect:/listadoCategoria";
    }
    
    
    
    
    
    //Metodo Para Actualizar Categoria
    @GetMapping("/editCategoria/{Id_categoriaProducto}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String showUpdateForm(@PathVariable("Id_categoriaProducto") int idCategoria, Model model) {
    	Categoriaproducto categoria = repositorioCategoria.findById(idCategoria).orElseThrow(() -> new IllegalArgumentException("Invalido categoria idCategoria:" + idCategoria));
        model.addAttribute("categoria", categoria);
        return "updateCategoria";
    }
    
    
    @PostMapping("/updateCategoria/{Id_categoriaProducto}")
    public String updateCategoria(@PathVariable("Id_categoriaProducto") int idCategoria,  Categoriaproducto categoria, BindingResult result, Model model) {
        if (result.hasErrors()) {
        	categoria.setId_categoriaProducto(idCategoria);
            return "updateCategoria";
        }
        
        repositorioCategoria.save(categoria);
        model.addAttribute("categorias", repositorioCategoria.findAll());
        return "redirect:/listadoCategoria";
    }
    
    
    
    
    
    //Metodo para Eliminar Categorias
    @GetMapping("/deleteCategoria/{idCategoria}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteCategoria(@PathVariable("Id_categoriaProducto") int idCategoria, Model model) {
    	Categoriaproducto categoria = repositorioCategoria.findById(idCategoria).orElseThrow(() -> new IllegalArgumentException("Invalido categoria idCategoria:" + idCategoria));
        repositorioCategoria.delete(categoria);
        model.addAttribute("categorias", repositorioCategoria.findAll());
        return "redirect:/listadoCategoria";
    }
    
    
    
    
    //Listado de Categorias
  	@GetMapping("/listadoCategorias")
  	//@PreAuthorize("hasRole('ROLE_ADMIN')")
  	public String list(Categoriaproducto categoria, Model model) {
  		model.addAttribute("categorias", repositorioCategoria.findAll());
        return "listadoCategoria";
  	}

}
