package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the pedido database table.
 * 
 */
@Entity
@Data
@NamedQuery(name="Pedido.findAll", query="SELECT p FROM Pedido p")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido")
	private int idPedido;

	private byte despachado;

	private int DNI_vendedor;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_pedido")
	private Date fechaPedido;

	//bi-directional many-to-one association to Factura
	@OneToMany(mappedBy="pedido")
	private List<Factura> facturas;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="DNI_cliente")
	private Cliente cliente;

	public Factura addFactura(Factura factura) {
		getFacturas().add(factura);
		factura.setPedido(this);

		return factura;
	}

	public Factura removeFactura(Factura factura) {
		getFacturas().remove(factura);
		factura.setPedido(null);

		return factura;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}