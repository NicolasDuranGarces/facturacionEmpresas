package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

import java.util.List;


/**
 * The persistent class for the producto database table.
 * 
 */
@Data
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
@ToString(includeFieldNames = true)
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_producto")
	private int idProducto;

	@Column(name="cantidad_actual")
	private int cantidadActual;

	private String marca;

	@Column(name="minimo_inventario")
	private int minimoInventario;

	@Column(name="nombre_producto")
	private String nombreProducto;

	@Lob
	private String urlFoto;

	@Column(name="valor_unitario")
	private int valorUnitario;

	//bi-directional many-to-one association to Detallefactura
	@OneToMany(mappedBy="producto")
	private List<Detallefactura> detallefacturas;

	//bi-directional many-to-one association to Bodega
	@ManyToOne
	@JoinColumn(name="id_bodega")
	private Bodega bodega;

	//bi-directional many-to-one association to Categoriaproducto
	@ManyToOne
	@JoinColumn(name="id_categoriaProducto")
	private Categoriaproducto categoriaproducto;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="id_proveedor")
	private Proveedor proveedor;


	public Detallefactura addDetallefactura(Detallefactura detallefactura) {
		getDetallefacturas().add(detallefactura);
		detallefactura.setProducto(this);

		return detallefactura;
	}

	public Detallefactura removeDetallefactura(Detallefactura detallefactura) {
		getDetallefacturas().remove(detallefactura);
		detallefactura.setProducto(null);

		return detallefactura;
	}


}