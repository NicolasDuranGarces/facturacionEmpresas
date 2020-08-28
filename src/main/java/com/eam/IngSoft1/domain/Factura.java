package modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the factura database table.
 * 
 */
@Entity
@NamedQuery(name="Factura.findAll", query="SELECT f FROM Factura f")
public class Factura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_factura")
	private int idFactura;

	@Column(name="precio_total")
	private int precioTotal;

	//bi-directional many-to-one association to Despachopedido
	@ManyToOne
	@JoinColumn(name="id_despachoPedido")
	private Despachopedido despachopedido;

	//bi-directional many-to-one association to Datallesfactura
	@ManyToOne
	@JoinColumn(name="id_detalleFactura")
	private Datallesfactura datallesfactura;

	public Factura() {
	}

	public int getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public int getPrecioTotal() {
		return this.precioTotal;
	}

	public void setPrecioTotal(int precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Despachopedido getDespachopedido() {
		return this.despachopedido;
	}

	public void setDespachopedido(Despachopedido despachopedido) {
		this.despachopedido = despachopedido;
	}

	public Datallesfactura getDatallesfactura() {
		return this.datallesfactura;
	}

	public void setDatallesfactura(Datallesfactura datallesfactura) {
		this.datallesfactura = datallesfactura;
	}

}