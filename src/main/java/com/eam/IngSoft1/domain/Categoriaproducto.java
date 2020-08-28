package modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the categoriaproducto database table.
 * 
 */
@Entity
@NamedQuery(name="Categoriaproducto.findAll", query="SELECT c FROM Categoriaproducto c")
public class Categoriaproducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id_categoriaProducto;

	@Column(name="nombre_categoria")
	private String nombreCategoria;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor")
	private Proveedor proveedor;

	//bi-directional many-to-one association to Producto
	@OneToMany(mappedBy="categoriaproducto")
	private List<Producto> productos;

	public Categoriaproducto() {
	}

	public int getId_categoriaProducto() {
		return this.id_categoriaProducto;
	}

	public void setId_categoriaProducto(int id_categoriaProducto) {
		this.id_categoriaProducto = id_categoriaProducto;
	}

	public String getNombreCategoria() {
		return this.nombreCategoria;
	}

	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

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