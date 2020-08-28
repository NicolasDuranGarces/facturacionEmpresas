package modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the producto database table.
 * 
 */
@Entity
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_producto")
	private int idProducto;

	private int existente;

	@Column(name="nombre_producto")
	private String nombreProducto;

	@Column(name="valor_unitario")
	private int valorUnitario;

	//bi-directional many-to-one association to Datallesfactura
	@OneToMany(mappedBy="producto")
	private List<Datallesfactura> datallesfacturas;

	//bi-directional many-to-one association to Bodega
	@ManyToOne
	@JoinColumn(name="id_bodega")
	private Bodega bodega;

	//bi-directional many-to-one association to Categoriaproducto
	@ManyToOne
	@JoinColumn(name="id_categoriaProducto")
	private Categoriaproducto categoriaproducto;

	public Producto() {
	}

	public int getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getExistente() {
		return this.existente;
	}

	public void setExistente(int existente) {
		this.existente = existente;
	}

	public String getNombreProducto() {
		return this.nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public int getValorUnitario() {
		return this.valorUnitario;
	}

	public void setValorUnitario(int valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public List<Datallesfactura> getDatallesfacturas() {
		return this.datallesfacturas;
	}

	public void setDatallesfacturas(List<Datallesfactura> datallesfacturas) {
		this.datallesfacturas = datallesfacturas;
	}

	public Datallesfactura addDatallesfactura(Datallesfactura datallesfactura) {
		getDatallesfacturas().add(datallesfactura);
		datallesfactura.setProducto(this);

		return datallesfactura;
	}

	public Datallesfactura removeDatallesfactura(Datallesfactura datallesfactura) {
		getDatallesfacturas().remove(datallesfactura);
		datallesfactura.setProducto(null);

		return datallesfactura;
	}

	public Bodega getBodega() {
		return this.bodega;
	}

	public void setBodega(Bodega bodega) {
		this.bodega = bodega;
	}

	public Categoriaproducto getCategoriaproducto() {
		return this.categoriaproducto;
	}

	public void setCategoriaproducto(Categoriaproducto categoriaproducto) {
		this.categoriaproducto = categoriaproducto;
	}

}