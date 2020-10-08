package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;


/**
 * The persistent class for the detallefactura database table.
 * 
 */
@Entity
@Data
@NamedQuery(name="Detallefactura.findAll", query="SELECT d FROM Detallefactura d")
public class Detallefactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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


}