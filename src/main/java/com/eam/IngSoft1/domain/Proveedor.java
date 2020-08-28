package modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the proveedor database table.
 * 
 */
@Entity
@NamedQuery(name="Proveedor.findAll", query="SELECT p FROM Proveedor p")
public class Proveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_proveedor")
	private int idProveedor;

	private String direccion;

	@Column(name="nombre_provedor")
	private String nombreProvedor;

	private int telefono;

	//bi-directional many-to-one association to Categoriaproducto
	@OneToMany(mappedBy="proveedor")
	private List<Categoriaproducto> categoriaproductos;

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

	public List<Categoriaproducto> getCategoriaproductos() {
		return this.categoriaproductos;
	}

	public void setCategoriaproductos(List<Categoriaproducto> categoriaproductos) {
		this.categoriaproductos = categoriaproductos;
	}

	public Categoriaproducto addCategoriaproducto(Categoriaproducto categoriaproducto) {
		getCategoriaproductos().add(categoriaproducto);
		categoriaproducto.setProveedor(this);

		return categoriaproducto;
	}

	public Categoriaproducto removeCategoriaproducto(Categoriaproducto categoriaproducto) {
		getCategoriaproductos().remove(categoriaproducto);
		categoriaproducto.setProveedor(null);

		return categoriaproducto;
	}

}