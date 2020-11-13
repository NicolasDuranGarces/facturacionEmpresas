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

	@NotBlank(message = "La dirección es obligatoria")
	@Size(min= 7, max=255, message="La dirección debe tener entre 7 y 255 caracteres")
	private String direccion;

	@NotBlank(message = "El nombre del proveedor es obligatorio")
	@Size(min= 3, max=50, message="El nombre debe tener entre 3 y 50 caracteres")
	@Pattern(regexp="^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message="El nombre solo admite letras mayúsculas y minúsculas")
	@Column(name="nombre_provedor")
	private String nombreProvedor;

	@NotBlank(message = "El teléfono es obligatorio")
	@Size(min= 7, max=20, message="El teléfono debe tener entre 7 y 20 caracteres")
	//@Pattern(regexp="^[0-9]$", message="Por favor solo ingresar numeros")
	private String telefono;

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

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
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
	public Proveedor(
			@NotBlank(message = "La dirección es obligatoria") @Size(min = 7, max = 255, message = "La dirección debe tener entre 7 y 255 caracteres") String direccion,
			@NotBlank(message = "El nombre del proveedor es obligatorio") @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres") @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message = "El nombre solo admite letras mayúsculas y minúsculas") String nombreProvedor,
			@NotBlank(message = "El teléfono es obligatorio") @Size(min = 7, max = 20, message = "El teléfono debe tener entre 7 y 20 caracteres") String telefono) {
		super();
		this.direccion = direccion;
		this.nombreProvedor = nombreProvedor;
		this.telefono = telefono;
		
	}

}