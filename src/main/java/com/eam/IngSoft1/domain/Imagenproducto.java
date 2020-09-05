package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the imagenproducto database table.
 * 
 */
@Entity
@NamedQuery(name="Imagenproducto.findAll", query="SELECT i FROM Imagenproducto i")
public class Imagenproducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_imagen")
	private int idImagen;

	private String url;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	public Imagenproducto() {
	}

	public int getIdImagen() {
		return this.idImagen;
	}

	public void setIdImagen(int idImagen) {
		this.idImagen = idImagen;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}