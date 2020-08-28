package modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the despachopedido database table.
 * 
 */
@Entity
@NamedQuery(name="Despachopedido.findAll", query="SELECT d FROM Despachopedido d")
public class Despachopedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id_despachoPedido;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_pedido")
	private Date fechaPedido;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="DNI_cliente")
	private Cliente cliente;

	//bi-directional many-to-one association to Vendedor
	@ManyToOne
	@JoinColumn(name="DNI_vendedor")
	private Vendedor vendedor;

	//bi-directional many-to-one association to Factura
	@OneToMany(mappedBy="despachopedido")
	private List<Factura> facturas;

	public Despachopedido() {
	}

	public int getId_despachoPedido() {
		return this.id_despachoPedido;
	}

	public void setId_despachoPedido(int id_despachoPedido) {
		this.id_despachoPedido = id_despachoPedido;
	}

	public Date getFechaPedido() {
		return this.fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Vendedor getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public List<Factura> getFacturas() {
		return this.facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public Factura addFactura(Factura factura) {
		getFacturas().add(factura);
		factura.setDespachopedido(this);

		return factura;
	}

	public Factura removeFactura(Factura factura) {
		getFacturas().remove(factura);
		factura.setDespachopedido(null);

		return factura;
	}

}