package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

import java.util.List;


/**
 * The persistent class for the factura database table.
 * 
 */
@Entity
@Data
@NamedQuery(name="Factura.findAll", query="SELECT f FROM Factura f")
public class Factura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_factura")
	private int idFactura;

	@Column(name="precio_total")
	private int precioTotal;

	//bi-directional many-to-one association to Detallefactura
	@OneToMany(mappedBy="factura")
	private List<Detallefactura> detallefacturas;

	//bi-directional many-to-one association to Pedido
	@ManyToOne
	@JoinColumn(name="id_pedido")
	private Pedido pedido;


	public Detallefactura addDetallefactura(Detallefactura detallefactura) {
		getDetallefacturas().add(detallefactura);
		detallefactura.setFactura(this);

		return detallefactura;
	}

	public Detallefactura removeDetallefactura(Detallefactura detallefactura) {
		getDetallefacturas().remove(detallefactura);
		detallefactura.setFactura(null);

		return detallefactura;
	}


}