package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.ToString;

import java.util.List;


/**
 * The persistent class for the producto database table.
 * 
 */
@Data
@Entity
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
@ToString(includeFieldNames = true)
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_producto")
	private int idProducto;

	@NotBlank(message = "{cantidadActual-mandatory}")
	@Size(min= 3, max=999, message="{cantidadActual-size}")
	@Column(name="cantidad_actual")
	private int cantidadActual;

	private String marca;

	@NotBlank(message = "{minimoInventario-mandatory}")
	@Size(min= 1, max=10, message="{minimoInventario-size}")
	@Column(name="minimo_inventario")
	private int minimoInventario;

	@NotBlank(message = "{name-mandatory}")
	@Size(min= 3, max=50, message="{name-size}")
	@Pattern(regexp="^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message="{name-valid}")
	@Column(name="nombre_producto")
	private String nombreProducto;

	@Lob
	private String urlFoto;

	@NotBlank(message = "{valor-unitario-mandatory}")
	@Size(min= 50, max=9999999, message="{valor-unitario-size}")
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
	
	public Producto() {
	}

	public int getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public int getCantidadActual() {
		return this.cantidadActual;
	}

	public void setCantidadActual(int cantidadActual) {
		this.cantidadActual = cantidadActual;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public int getMinimoInventario() {
		return this.minimoInventario;
	}

	public void setMinimoInventario(int minimoInventario) {
		this.minimoInventario = minimoInventario;
	}

	public String getNombreProducto() {
		return this.nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getUrlFoto() {
		return this.urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

	public int getValorUnitario() {
		return this.valorUnitario;
	}

	public void setValorUnitario(int valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public List<Detallefactura> getDetallefacturas() {
		return this.detallefacturas;
	}

	public void setDetallefacturas(List<Detallefactura> detallefacturas) {
		this.detallefacturas = detallefacturas;
	}
	


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

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Producto(
			@NotBlank(message = "{cantidadActual-mandatory}") @Size(min = 3, max = 999, message = "{cantidadActual-size}") int cantidadActual,
			String marca,
			@NotBlank(message = "{minimoInventario-mandatory}") @Size(min = 1, max = 10, message = "{minimoInventario-size}") int minimoInventario,
			@NotBlank(message = "{name-mandatory}") @Size(min = 3, max = 50, message = "{name-size}") @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message = "{name-valid}") String nombreProducto,
			String urlFoto,
			@NotBlank(message = "{valor-unitario-mandatory}") @Size(min = 50, max = 9999999, message = "{valor-unitario-size}") int valorUnitario,
			Bodega bodega, Categoriaproducto categoriaproducto,
			Proveedor proveedor) {
		super();
		this.cantidadActual = cantidadActual;
		this.marca = marca;
		this.minimoInventario = minimoInventario;
		this.nombreProducto = nombreProducto;
		this.urlFoto = urlFoto;
		this.valorUnitario = valorUnitario;
		this.bodega = bodega;
		this.categoriaproducto = categoriaproducto;
		this.proveedor = proveedor;
	}
	
	


}