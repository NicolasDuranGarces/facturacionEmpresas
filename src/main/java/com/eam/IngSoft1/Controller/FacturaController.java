package com.eam.IngSoft1.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eam.IngSoft1.IRepository.IDetalleFacturaRepository;
import com.eam.IngSoft1.IRepository.IFacturaRepository;
import com.eam.IngSoft1.IRepository.IPedidoRepository;
import com.eam.IngSoft1.IRepository.IProductoRepository;
import com.eam.IngSoft1.IRepository.IUsuarioRepository;
import com.eam.IngSoft1.config.MethodSecurityConfig;
import com.eam.IngSoft1.domain.Detallefactura;
import com.eam.IngSoft1.domain.Factura;
import com.eam.IngSoft1.domain.Pedido;
import com.eam.IngSoft1.domain.Producto;
import com.eam.IngSoft1.domain.Usuario;
import com.eam.IngSoft1.util.PDFExporterClass;
import com.eam.IngSoft1.util.Pagination;

@Controller
public class FacturaController {

	private final IFacturaRepository repositorioFactura;
	private final IPedidoRepository repositorioPedido;
	private final IDetalleFacturaRepository repositorioDetalle;
	private final IUsuarioRepository repositorioUsuario;
	private final IProductoRepository repositorioProducto;

	@Autowired
	public FacturaController(IFacturaRepository repositorioFactura, IPedidoRepository repositorioPedido,
			IDetalleFacturaRepository repositorioDetalle, IUsuarioRepository repositorioUsuario,
			IProductoRepository repositorioProducto) {
		this.repositorioPedido = repositorioPedido;
		this.repositorioFactura = repositorioFactura;
		this.repositorioDetalle = repositorioDetalle;
		this.repositorioUsuario = repositorioUsuario;
		this.repositorioProducto = repositorioProducto;
	}

	/// Método para agregar producto al carrito
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/user/agregar-al-carrito")
	public String crearFactura(@RequestParam(value = "idProducto") int idProducto,
			@RequestParam(value = "cantidad") int cantidad, Model model) {

		// Codigo Para Obtener El Usuario que esta Sesion
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		String userName = userDetails.getUsername();

		// Busqueda de Usuario Mediante nombreUsuario obtenido Anteriormente
		Usuario user = repositorioUsuario.mostrarUsuario(userName);

		// Inicializacion de Id de facturas Para busqueda
		int idFactura = 0;
		int idFacturaNueva = 0;
		int sumaTotal = 0;

		ArrayList<Pedido> pedidos = new ArrayList<>();

		pedidos = repositorioFactura.mostrarPedidosActivos(user.getDni());

		Producto producto = repositorioProducto.findByidProducto(idProducto);

		// Comparacion para saber si hay pedidos
		if (pedidos.size() > 0) {
			// Si Hay pedidos Confirmamos que el pedido este Activo y Obtenemos el idFactura
			// a la que hace referencia
			idFactura = repositorioFactura.codigoFactura(user.getDni());
			Detallefactura detallePrueba = null;
			try {
				detallePrueba = repositorioDetalle.verificarDetalle(idFactura, idProducto);
			} catch (Exception e) {

			}
			if (detallePrueba != null) {

				model.addAttribute("producto", producto);
				model.addAttribute("mensaje",
						"Este producto ya se ha añadido al carrito y se encuentra asociado al pedido");
				return "Producto/detalleProducto";
			}
		}

		// Instanciamos un Objeto de DetalleFactura
		Detallefactura detallefactura = new Detallefactura();

		// confirmamos que el idFactura no sea 0
		if (idFactura != 0) {
			// Si ya hay una factura la asociamos a un Detalle
			detallefactura.setFactura(repositorioFactura.findByidFactura(idFactura));
		} else {
			// Si es 0 Se crea un nuevo pedido en el sistema y se le asocia una factura
			Pedido pedido = new Pedido();
			pedido.setActivo(true);
			pedido.setDespachado(false);
			pedido.setDNI_Encargado(1004);
			pedido.setCliente(repositorioUsuario.findByDni(user.getDni()));

			// Captura La Fecha del Sistema
			java.util.Date fecha = new Date();
			pedido.setFechaPedido(fecha);

			// Guardamos el nuevo pedido en la BD
			repositorioPedido.save(pedido);

			Factura factura = new Factura();
			factura.setPedido(pedido);

			// Guardamos la nueva factura en la BD
			repositorioFactura.save(factura);

			ArrayList<Factura> facturas = repositorioFactura.mostrarFacturas();

			if (facturas.size() != 0) {
				idFacturaNueva = repositorioFactura.codigoFactura(user.getDni());
			}

			detallefactura.setFactura(repositorioFactura.findByidFactura(idFacturaNueva));
		}

		if (producto.getCantidadActual() >= cantidad) {

			detallefactura.setProducto(producto);
			detallefactura.setCantidadProducto(cantidad);

			int valor = producto.getValorUnitario() * cantidad;
			detallefactura.setValorTotal(valor);
			int valorIva = (int) (valor * 0.19);
			detallefactura.setValorIvaTotal(valorIva);

			repositorioDetalle.save(detallefactura);

			//////////////// producto.setCantidadActual(producto.getCantidadActual()-cantidad);
			//////////////// repositorioProducto.save(producto);

			ArrayList<Detallefactura> detalleFactura = repositorioDetalle
					.mostrarDetalles(detallefactura.getFactura().getIdFactura());

			for (int i = 0; i < detalleFactura.size(); i++) {
				sumaTotal += detalleFactura.get(i).getValorTotal();
			}

			Factura facturaConPrecio = repositorioFactura.findByidFactura(detallefactura.getFactura().getIdFactura());
			facturaConPrecio.setPrecioTotal(sumaTotal);
			repositorioFactura.save(facturaConPrecio);
			return "redirect:/home";

		} else {

			model.addAttribute("producto", producto);
			model.addAttribute("mensaje", "Actualmente solo quedan " + producto.getCantidadActual()
					+ " unidades del producto:\n" + producto.getNombreProducto());
			return "Producto/detalleProducto";

		}

	}

	// metodo para listado factura
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
	@GetMapping("/listadofactura/page/{page}")
	public String list(Factura factura, @PathVariable("page") int pageActual, Model model) {

		int numFac = 5;
		int max = numFac * pageActual;
		int min = numFac * (pageActual - 1);
		ArrayList<Factura> todos = (ArrayList<Factura>) repositorioFactura.mostrarFacturas();
		ArrayList<Factura> mostrados = new ArrayList<Factura>();
		ArrayList<Pagination> pages = new ArrayList<Pagination>();
		for (int i = 0; i < todos.size(); i++) {
			if (i >= min && i < max) {
				mostrados.add(todos.get(i));
			}
		}
		///// Paginación///////
		int numPages = (todos.size() / numFac);
		if (todos.size() % numFac != 0) {
			numPages += 1;
		}
		for (int i = 1; i <= (numPages); i++) {
			Pagination page = new Pagination();
			page.setPagina("" + i);
			page.setActiva(false);
			if (i == pageActual) {
				page.setActiva(true);
			}

			pages.add(page);
		}
		int prev, next;
		if (pageActual > 1) {
			prev = pageActual - 1;
		} else {
			prev = pageActual;
		}

		if (pageActual < numPages) {
			next = pageActual + 1;
		} else {
			next = pageActual;
		}

		model.addAttribute("facturas", mostrados);
		model.addAttribute("pages", pages);
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
		model.addAttribute("url", "listadofactura");
		model.addAttribute("useDni", false);
		model.addAttribute("dni", null);
		return "Factura/listaFactura";
	}

	// metodo para filtrar factura por un cliente en especifico
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO')")
	@PostMapping("/filtrarclientefactura/page/{page}")
	public String flitrarFactura(@RequestParam(value = "dni") int idCliente, @PathVariable("page") int pageActual,
			Model model) {

		int numFac = 2;
		int max = numFac * pageActual;
		int min = numFac * (pageActual - 1);
		ArrayList<Factura> todos = (ArrayList<Factura>) repositorioFactura.mostrarFacturaFiltroCliente(idCliente);
		ArrayList<Factura> mostrados = new ArrayList<Factura>();
		ArrayList<Pagination> pages = new ArrayList<Pagination>();
		for (int i = 0; i < todos.size(); i++) {
			if (i >= min && i < max) {
				mostrados.add(todos.get(i));
			}
		}
		///// Paginación///////
		int numPages = (todos.size() / numFac);
		if (todos.size() % numFac != 0) {
			numPages += 1;
		}
		for (int i = 1; i <= (numPages); i++) {
			Pagination page = new Pagination();
			page.setPagina("" + i);
			page.setActiva(false);
			if (i == pageActual) {
				page.setActiva(true);
			}

			pages.add(page);
		}
		int prev, next;
		if (pageActual > 1) {
			prev = pageActual - 1;
		} else {
			prev = pageActual;
		}

		if (pageActual < numPages) {
			next = pageActual + 1;
		} else {
			next = pageActual;
		}
		model.addAttribute("facturas", mostrados);
		model.addAttribute("pages", pages);
		model.addAttribute("prev", prev);
		model.addAttribute("next", next);
		model.addAttribute("url", "filtrarclientefactura");
		model.addAttribute("useDni", true);
		model.addAttribute("dni", idCliente);
		return "Factura/listaFacturaFiltroCliente";
	}

	// metodo para listado facturas del usuario
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user/listfactura")
	public String listFacCliente(Factura factura, Model model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		String userName = userDetails.getUsername();

		// Busqueda de Usuario Mediante nombreUsuario obtenido Anteriormente
		Usuario user = repositorioUsuario.mostrarUsuario(userName);

		model.addAttribute("facturas", repositorioFactura.facturasNoActivas(user.getDni()));
		return "Factura/listaFacturaCliente";
	}

	// metodo para redireccionar al Carrito de Compra
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user/listCarrito")
	public String irVerCarrito(Model model, Detallefactura detallefactura) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		String userName = userDetails.getUsername();
		Usuario user = repositorioUsuario.mostrarUsuario(userName);
		// int idFactura = repositorioFactura.codigoFactura(user.getDni());

		int idFactura = 0;

		ArrayList<Pedido> pedidos = new ArrayList<>();
		// Boolean pedidosActivos = false;
		/*
		 * try { pedidos = repositorioFactura.mostrarPedidosActivos(user.getDni()); for
		 * (Pedido pedido : pedidos) {
		 * System.out.println("Pedido activo: "+pedido.getIdPedido()); } pedidosActivos
		 * = true; } catch (Exception e) { pedidosActivos = false; }
		 */

		pedidos = repositorioFactura.mostrarPedidosActivos(user.getDni());

		// Comparacion para saber si hay pedidos
		// System.out.println("Tiene pedidos activos: "+pedidosActivos);
		if (pedidos.size() > 0) {
			// Si Hay pedidos Confirmamos que el pedido este Activo y Obtenemos el idFactura
			// a la que hace referencia

			idFactura = repositorioFactura.codigoFactura(user.getDni());

		} else {

			// Si es 0 Se crea un nuevo pedido en el sistema y se le asocia una factura
			Pedido pedido = new Pedido();
			pedido.setActivo(true);
			pedido.setDespachado(false);
			pedido.setDNI_Encargado(1004);
			pedido.setCliente(repositorioUsuario.findByDni(user.getDni()));

			// Captura La Fecha del Sistema
			java.util.Date fecha = new Date();
			pedido.setFechaPedido(fecha);

			// Guardamos el nuevo pedido en la BD
			repositorioPedido.save(pedido);

			Factura factura = new Factura();
			factura.setPedido(pedido);

			// Guardamos la nueva factura en la BD
			repositorioFactura.save(factura);
			idFactura = repositorioFactura.codigoFactura(user.getDni());
		}

		// System.out.println("id factura: "+idFactura);
		Factura factura1 = repositorioFactura.findByidFactura(idFactura);

		Iterable<Detallefactura> detalles = repositorioDetalle.mostrarDetalles(idFactura);
		int precio = factura1.getPrecioTotal();

		model.addAttribute("detallefactura", detalles);
		model.addAttribute("totalfactura", precio);

		// model.addAttribute("hide",true);
		return "Pedido/listadoCarrito";
	}

	// Metodo para quitar producto del carrito
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user/deletedetalle/{idDetalle}")
	public String deleteDetalle(@PathVariable("idDetalle") int idDetalle, Model model) {
		Detallefactura detalleFactura = repositorioDetalle.findById(idDetalle)
				.orElseThrow(() -> new IllegalArgumentException("Invalido detalle idDetalle:" + idDetalle));
		/// Producto producto =
		/// repositorioProducto.findByidProducto(detalleFactura.getProducto().getIdProducto());
		/// producto.setCantidadActual(producto.getCantidadActual()+detalleFactura.getCantidadProducto());
		/// repositorioProducto.save(producto);
		Factura factura = detalleFactura.getFactura();
		factura.setPrecioTotal(factura.getPrecioTotal() - detalleFactura.getValorTotal());
		repositorioFactura.save(factura);
		repositorioDetalle.delete(detalleFactura);

		// model.addAttribute("hide",true);
		return "redirect:/user/listCarrito";
	}

	// Metodo para sumar una unidad al producto del carrito
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user/sumaruno/{idDetalle}")
	public String sumarUnidad(@PathVariable("idDetalle") int idDetalle, Model model) {

		Detallefactura detalleFactura = repositorioDetalle.findById(idDetalle)
				.orElseThrow(() -> new IllegalArgumentException("Invalido detalle idDetalle:" + idDetalle));

		int cantidad = detalleFactura.getCantidadProducto() + 1;
		Producto producto = detalleFactura.getProducto();
		int valor = producto.getValorUnitario() * cantidad;
		detalleFactura.setValorTotal(valor);
		int valorIva = (int) (valor * 0.19);
		detalleFactura.setValorIvaTotal(valorIva);
		detalleFactura.setCantidadProducto(cantidad);
		Factura factura = detalleFactura.getFactura();
		factura.setPrecioTotal(factura.getPrecioTotal() + producto.getValorUnitario());
		repositorioFactura.save(factura);
		repositorioDetalle.save(detalleFactura);

		// model.addAttribute("hide",true);
		return "redirect:/user/listCarrito";
	}

	// Metodo para restar una unidad al producto del carrito
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/user/restaruno/{idDetalle}")
	public String restarUnidad(@PathVariable("idDetalle") int idDetalle, Model model) {

		Detallefactura detalleFactura = repositorioDetalle.findById(idDetalle)
				.orElseThrow(() -> new IllegalArgumentException("Invalido detalle idDetalle:" + idDetalle));

		int cantidad = detalleFactura.getCantidadProducto() - 1;
		if (cantidad <= 0) {
			return "redirect:/user/listCarrito";
		}
		Producto producto = detalleFactura.getProducto();
		int valor = producto.getValorUnitario() * cantidad;
		detalleFactura.setValorTotal(valor);
		int valorIva = (int) (valor * 0.19);
		detalleFactura.setValorIvaTotal(valorIva);
		detalleFactura.setCantidadProducto(cantidad);
		Factura factura = detalleFactura.getFactura();
		factura.setPrecioTotal(factura.getPrecioTotal() - producto.getValorUnitario());
		repositorioFactura.save(factura);
		repositorioDetalle.save(detalleFactura);

		return "redirect:/user/listCarrito";
	}

	// Metodo para confirmar el pedido
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/user/confirmar-pedido")
	public String confirmarPedido(Model model) {

		String mensaje = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		String userName = userDetails.getUsername();
		Usuario user = repositorioUsuario.mostrarUsuario(userName);

		int idFactura = repositorioFactura.codigoFactura(user.getDni());

		ArrayList<Detallefactura> detalles = repositorioDetalle.mostrarDetalles(idFactura);
		if (detalles.size() == 0) {
			model.addAttribute("mensajebueno", "Primero debes seleccionar al menos un producto");
			return "Pedido/listadoCarrito";
		}

		/*
		 * for (int i=0; i<detalles.size(); i++) { Detallefactura detalle =
		 * detalles.get(i); Producto producto = detalle.getProducto(); int
		 * cantidadActual = producto.getCantidadActual(); if
		 * (detalle.getCantidadProducto()>cantidadActual) { String msg =
		 * "Actualmente solo quedan "+cantidadActual+" unidades del producto:\n"
		 * +producto.getNombreProducto(); ObjectError objectError = new
		 * ObjectError("ERROR_"+i, msg); result.addError(objectError); mensaje =
		 * mensaje+"\n"+msg; } }
		 * 
		 * if (result.hasErrors()) { model.addAttribute("hide",false);
		 * 
		 * model.addAttribute("mensaje",
		 * mensaje+"\n\n"+"Corrija la cantidad de unidades a pedir"); return
		 * "/user/listCarrito"; }
		 * 
		 */

		for (int i = 0; i < detalles.size(); i++) {
			Detallefactura detalle = detalles.get(i);
			Producto producto = detalle.getProducto();
			int cantidadActual = producto.getCantidadActual();
			if (detalle.getCantidadProducto() > cantidadActual) {
				String msg = "Actualmente solo quedan " + cantidadActual + " unidades del producto:\n"
						+ producto.getNombreProducto();

				mensaje = mensaje + "\n --- " + msg;
			}
		}

		if (!mensaje.equals("")) {
			// model.addAttribute("hide",false);

			Factura factura1 = repositorioFactura.findByidFactura(idFactura);
			model.addAttribute("mensaje",
					mensaje + "\n\n" + " --- Por favor corrija la cantidad de unidades a pedir ---");
			int precio = factura1.getPrecioTotal();

			model.addAttribute("detallefactura", detalles);
			model.addAttribute("totalfactura", precio);
			return "Pedido/listadoCarrito";
		} else {

			for (int i = 0; i < detalles.size(); i++) {
				Detallefactura detalle = detalles.get(i);
				Producto producto = detalle.getProducto();
				producto.setCantidadActual(producto.getCantidadActual() - detalle.getCantidadProducto());
				repositorioProducto.save(producto);
			}

			Pedido pedido = repositorioFactura.findByidFactura(idFactura).getPedido();
			pedido.setActivo(false);
			repositorioPedido.save(pedido);

			// model.addAttribute("hide",false);
			// model.addAttribute("detallefactura",detalles);
			model.addAttribute("mensajebueno", "Pedido realizado con éxito");
			return "redirect:/user/listCarrito";
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLEADO') or hasRole('ROLE_USER')")
	@GetMapping("/export/pdf/{idFactura}")
	public void exportToPDF(HttpServletResponse response, @PathVariable("idFactura") int idFactura)
			throws DocumentException, IOException {
		response.setContentType("application/pdf");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormatter.format(new Date());

		List<Detallefactura> listaDetalle = repositorioDetalle.mostrarDetalles(idFactura);
		Factura factura = repositorioFactura.findByidFactura(idFactura);

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=factura_" + factura.getPedido().getCliente().getNombreUsuario() + "_"
				+ idFactura + ".pdf";
		response.setHeader(headerKey, headerValue);

		PDFExporterClass exporter = new PDFExporterClass(listaDetalle, factura);
		exporter.export(response);

	}

}