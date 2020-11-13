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

	private boolean despachado;
	
	private boolean activo;
	
	private int DNI_Encargado;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_pedido")
	private Date fechaPedido;

	//bi-directional many-to-one association to Factura
	@OneToMany(mappedBy="pedido")
	private List<Factura> facturas;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="DNI_cliente")
	private Usuario cliente;
	
	
	
	public Pedido() {
	}

	public int getIdPedido() {
		return this.idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public boolean getDespachado() {
		return this.despachado;
	}

	public void setDespachado(boolean despachado) {
		this.despachado = despachado;
	}
	
	public boolean getActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public int getDNI_vendedor() {
		return this.DNI_Encargado;
	}

	public void setDNI_vendedor(int DNI_vendedor) {
		this.DNI_Encargado = DNI_vendedor;
	}

	public Date getFechaPedido() {
		return this.fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public List<Factura> getFacturas() {
		return this.facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

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

	public Usuario getCliente() {
		return this.cliente;
	}

	public void Usuario(Usuario cliente) {
		this.cliente = cliente;
	}

	public Pedido(boolean despachado, boolean activo, int dNI_Encargado,
			com.eam.IngSoft1.domain.Usuario cliente) {
		super();
		this.despachado = despachado;
		this.activo = activo;
		DNI_Encargado = dNI_Encargado;
		this.cliente = cliente;
	}

}