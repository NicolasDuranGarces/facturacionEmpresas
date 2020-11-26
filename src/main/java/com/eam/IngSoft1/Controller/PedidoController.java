package com.eam.IngSoft1.Controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.utils.ObjectUtils;
import com.eam.IngSoft1.IRepository.IDetalleFacturaRepository;
import com.eam.IngSoft1.IRepository.IFacturaRepository;
import com.eam.IngSoft1.IRepository.IPedidoRepository;
import com.eam.IngSoft1.IRepository.IProductoRepository;
import com.eam.IngSoft1.domain.Categoriaproducto;
import com.eam.IngSoft1.domain.Detallefactura;
import com.eam.IngSoft1.domain.Factura;
import com.eam.IngSoft1.domain.Pedido;
import com.eam.IngSoft1.domain.Producto;
import com.eam.IngSoft1.domain.Usuario;



@Controller
public class PedidoController {
	private final IPedidoRepository repositorioPedido;
	private final IProductoRepository repositorioProducto;
	private final IFacturaRepository repositorioFactura;
	private final IDetalleFacturaRepository repositorioDetalle;
	
	
	public PedidoController(IPedidoRepository repositorioPedido, IProductoRepository repositorioProducto,
			IFacturaRepository repositorioFactura, IDetalleFacturaRepository repositorioDetalle) {
		super();
		this.repositorioPedido = repositorioPedido;
		this.repositorioProducto = repositorioProducto;
		this.repositorioFactura = repositorioFactura;
		this.repositorioDetalle = repositorioDetalle;
	}

	//Método para mostrar los detalles de un pedido, incluye los detalles de la factura
	@GetMapping("/admin/pedidos/detallePedido/{idPedido}")
   	public String viewDetail(@PathVariable("idPedido") int idPedido, Model model) {
   		Pedido pedido = repositorioPedido.findById(idPedido).orElseThrow(() -> new IllegalArgumentException("Invalido Pedido id:" + idPedido));
   		Factura factura = repositorioFactura.traerFacturaPorIdPedido(idPedido);
   		Iterable<Detallefactura> detalles = repositorioDetalle.mostrarDetalles(factura.getIdFactura());
   		int precio = factura.getPrecioTotal();
   		model.addAttribute("detalles", detalles);
		model.addAttribute("totalfactura", precio);
		model.addAttribute("pedido",pedido);
		
   		return "Pedido/detallePedido";
   	}
	
	//Método para despachar un pedido
	@GetMapping("/despacharPedido/{idPedido}")
    public String updateMercanciaProducto(@PathVariable("idPedido") int idPedido, Model model ) {
		Pedido pedido = repositorioPedido.findById(idPedido).orElseThrow(() -> new IllegalArgumentException("Invalido Pedido id:" + idPedido));
		pedido.setDespachado(true);
		repositorioPedido.save(pedido);
		
		Factura factura = repositorioFactura.traerFacturaPorIdPedido(idPedido);
   		Iterable<Detallefactura> detalles = repositorioDetalle.mostrarDetalles(factura.getIdFactura());
   		int precio = factura.getPrecioTotal();
   		model.addAttribute("detalles", detalles);
		model.addAttribute("totalfactura", precio);
		model.addAttribute("pedido",pedido);
		return "redirect:/admin/pedidos/detallePedido/"+pedido.getIdPedido();
	}
	
	
	//Método para listar todos los pedidos realizados que están en el sistema
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @GetMapping("/admin/pedidos/todos")
	public String listarTodosLosPedidos(Model model) {
		model.addAttribute("pedidos", repositorioPedido.traerTodosLosPedidos());
		return "Pedido/listaPedidos";
	}
	
	
	//Método para listar todos los pedidos que están sin despachar
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @GetMapping("/admin/pedidos/por-despachar")
	public String listarPedidosSinDespachar(Model model) {
		model.addAttribute("pedidos", repositorioPedido.traerTodosLosPedidosSinDespachar());
		return "Pedido/listaPedidos";
	}
	
	//Método para listar todos los pedidos despachados
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @GetMapping("/admin/pedidos/despachados")
	public String listarPedidosDespachados(Model model) {
		model.addAttribute("pedidos", repositorioPedido.traerTodosLosPedidosDespachados());
		return "Pedido/listaPedidos";
	}
	
	
	//Método para listar todos los pedidos realizados por un cliente
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
    @PostMapping("/admin/pedidos/cliente")
	public String listarPedidosCliente(@RequestParam(value = "dni") int idCliente, Model model) {
		model.addAttribute("pedidos", repositorioPedido.mostrarPedidosRealizadosCliente(idCliente));
		return "Pedido/listaPedidos";
	}
	
	
		
 
}
