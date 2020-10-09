package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

import java.util.List;


/**
 * The persistent class for the proveedor database table.
 * 
 */
@Entity
@Data
@NamedQuery(name="Proveedor.findAll", query="SELECT p FROM Proveedor p")
public class Proveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_proveedor")
	private int idProveedor;

	@NotBlank(message = "{address-mandatory}")
	@Size(min= 7, max=255, message="{address-size}")
	private String direccion;

	@NotBlank(message = "{name-mandatory}")
	@Size(min= 3, max=50, message="{name-size}")
	@Pattern(regexp="^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message="{name-valid}")
	@Column(name="nombre_provedor")
	private String nombreProvedor;

	@NotBlank(message = "{phone-mandatory}")
	@Size(min= 7, max=20, message="{phone-size}")
	//@Pattern(regexp="^[0-9]$", message="Por favor solo ingresar numeros")
	private int telefono;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="proveedor")
	private List<Producto> productos;
	
	public Proveedor() {
	}

	public int getIdProveedor() {
		return this.idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombreProvedor() {
		return this.nombreProvedor;
	}

	public void setNombreProvedor(String nombreProvedor) {
		this.nombreProvedor = nombreProvedor;
	}

	public int getTelefono() {
		return this.telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public List<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}


	public Producto addProducto(Producto producto) {
		getProductos().add(producto);
		producto.setProveedor(this);

		return producto;
	}

	public Producto removeProducto(Producto producto) {
		getProductos().remove(producto);
		producto.setProveedor(null);

		return producto;
	}

}