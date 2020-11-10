package com.eam.IngSoft1.Controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.utils.ObjectUtils;
import com.eam.IngSoft1.IRepository.IPedidoRepository;
import com.eam.IngSoft1.IRepository.IProductoRepository;
import com.eam.IngSoft1.domain.Pedido;
import com.eam.IngSoft1.domain.Producto;


@Controller
public class PedidoController {
	private final IPedidoRepository repositorPedido;
	private final IProductoRepository repositorioProducto;
	
	@Autowired
	public PedidoController(IProductoRepository repositorioProducto,IPedidoRepository repositorPedido) {
		this.repositorioProducto = repositorioProducto;
		this.repositorPedido = repositorPedido;

	}
		
 	//metodo para redireccionar al carrito de compra
   	@GetMapping("/listCarrito")
   	public String irVerCarrito(Model model) {
   		return "Pedido/listadoCarrito";
   	}
}
