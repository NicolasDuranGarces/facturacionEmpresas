package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the detallefactura database table.
 * 
 */
@Entity
@NamedQuery(name="Detallefactura.findAll", query="SELECT d FROM Detallefactura d")
public class Detallefactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id_detalleFactura;

	private int cantidadProducto;

	private int valorTotal;

	//bi-directional many-to-one association to Factura
	@ManyToOne
	@JoinColumn(name="id_factura")
	private Factura factura;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	public Detallefactura() {
	}

	public int getId_detalleFactura() {
		return this.id_detalleFactura;
	}

	public void setId_detalleFactura(int id_detalleFactura) {
		this.id_detalleFactura = id_detalleFactura;
	}

	public int getCantidadProducto() {
		return this.cantidadProducto;
	}

	public void setCantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public int getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(int valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Factura getFactura() {
		return this.factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}