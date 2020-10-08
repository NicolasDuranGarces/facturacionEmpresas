package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

import java.util.List;


/**
 * The persistent class for the bodega database table.
 * 
 */
@Entity
@Data
@NamedQuery(name="Bodega.findAll", query="SELECT b FROM Bodega b")
public class Bodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_bodega")
	private int idBodega;

	@Column(name="nombre_bodega")
	private String nombreBodega;

	private String ubicacion;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="bodega")
	private List<Producto> productos;


	public Producto addProducto(Producto producto) {
		getProductos().add(producto);
		producto.setBodega(this);

		return producto;
	}

	public Producto removeProducto(Producto producto) {
		getProductos().remove(producto);
		producto.setBodega(null);

		return producto;
	}

}