package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

import java.util.List;


/**
 * The persistent class for the categoriaproducto database table.
 * 
 */
@Entity
@Data
@NamedQuery(name="Categoriaproducto.findAll", query="SELECT c FROM Categoriaproducto c")
public class Categoriaproducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_categoriaProducto;

	@Column(name="nombre_categoria")
	private String nombreCategoria;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="categoriaproducto")
	private List<Producto> productos;

	

	public Producto addProducto(Producto producto) {
		getProductos().add(producto);
		producto.setCategoriaproducto(this);

		return producto;
	}

	public Producto removeProducto(Producto producto) {
		getProductos().remove(producto);
		producto.setCategoriaproducto(null);

		return producto;
	}

}