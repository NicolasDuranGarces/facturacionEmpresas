package modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the datallesfactura database table.
 * 
 */
@Entity
@NamedQuery(name="Datallesfactura.findAll", query="SELECT d FROM Datallesfactura d")
public class Datallesfactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id_detalleFactura;

	private int cantidad;

	private int total;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	//bi-directional many-to-one association to Factura
	@OneToMany(mappedBy="datallesfactura")
	private List<Factura> facturas;

	public Datallesfactura() {
	}

	public int getId_detalleFactura() {
		return this.id_detalleFactura;
	}

	public void setId_detalleFactura(int id_detalleFactura) {
		this.id_detalleFactura = id_detalleFactura;
	}

	public int getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getTotal() {
		return this.total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public List<Factura> getFacturas() {
		return this.facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public Factura addFactura(Factura factura) {
		getFacturas().add(factura);
		factura.setDatallesfactura(this);

		return factura;
	}

	public Factura removeFactura(Factura factura) {
		getFacturas().remove(factura);
		factura.setDatallesfactura(null);

		return factura;
	}

}