package com.eam.IngSoft1.Controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.eam.IngSoft1.IRepository.IFacturaRepository;
import com.eam.IngSoft1.IRepository.IPedidoRepository;
import com.eam.IngSoft1.domain.Pedido;

@Controller
public class FacturaController {

	private final IFacturaRepository repositorioFactura;
	private final IPedidoRepository repositorioPedido;

	@Autowired
	public FacturaController(IFacturaRepository repositorioFactura, IPedidoRepository repositorioPedido) {
		this.repositorioPedido = repositorioPedido;
		this.repositorioFactura = repositorioFactura;
	}

	public void crearFactura() {
		Pedido pedido = new Pedido();
		//pedido.setCliente();

		pedido.setActivo(true);
		pedido.setDespachado(false);

		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		// Captura La Fecha del Sistema
		//pedido.setFechaPedido(dateFormat.format(date));

	}

}
