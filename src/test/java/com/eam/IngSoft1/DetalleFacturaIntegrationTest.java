package com.eam.IngSoft1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eam.IngSoft1.IRepository.IBodegaRepository;
import com.eam.IngSoft1.IRepository.ICategoriaRepository;
import com.eam.IngSoft1.IRepository.IDetalleFacturaRepository;
import com.eam.IngSoft1.IRepository.IFacturaRepository;
import com.eam.IngSoft1.IRepository.IPedidoRepository;
import com.eam.IngSoft1.IRepository.IProductoRepository;
import com.eam.IngSoft1.IRepository.IProveedorRepository;
import com.eam.IngSoft1.IRepository.IUsuarioRepository;
import com.eam.IngSoft1.domain.Authority;
import com.eam.IngSoft1.domain.Bodega;
import com.eam.IngSoft1.domain.Categoriaproducto;
import com.eam.IngSoft1.domain.Detallefactura;
import com.eam.IngSoft1.domain.Factura;
import com.eam.IngSoft1.domain.Pedido;
import com.eam.IngSoft1.domain.Producto;
import com.eam.IngSoft1.domain.Proveedor;
import com.eam.IngSoft1.domain.Usuario;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DetalleFacturaIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	IProductoRepository repositorioProducto;

	@Autowired
	IBodegaRepository repositorioBodega;

	@Autowired
	IDetalleFacturaRepository repositorioDetalle;

	@Autowired
	IFacturaRepository repositorioFactura;

	@Autowired
	IProveedorRepository repositorioProveedor;

	@Autowired
	ICategoriaRepository repositorioCategoria;

	@Autowired
	IPedidoRepository repositorioPedido;

	@Autowired
	IUsuarioRepository repositorioUsuario;

	@Test
	public void should_find_no_detalle_if_repository_is_empty() {
		Iterable<Detallefactura> detalles = repositorioDetalle.findAll();

		for (Detallefactura detalle : detalles) {
			System.out.println("Detalles:     " + detalle.toString());
		}
		assertThat(detalles).isEmpty();
	}

	@Test
	public void should_store_a_detalle() {

		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Bodega
		Bodega bodega = repositorioBodega.save(new Bodega("Bodega A", "Local principal", null));
		// Generacion de Proveedor
		Proveedor proveedor = repositorioProveedor
				.save(new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", "7374228"));
		// Generacion del Categoria
		Categoriaproducto categoriaproducto = repositorioCategoria.save(new Categoriaproducto("MacBook Pro"));
		// Generacion del Objeto Producto
		Producto producto = repositorioProducto.save(new Producto(100, "apple", 5, "Apple MacBook Pro 2019",
				"Ejemplo URL For", 120000, bodega, categoriaproducto, proveedor));
		// Generacion de Usuario CLIENTE
		Usuario usuario = repositorioUsuario.save(new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña",
				"dirección casa", "3218956845", permisos, null));
		// GENERACION DE Pedio
		Pedido pedido = repositorioPedido.save(new Pedido(false, true, 1004, usuario));
		// Generacion de Factura
		Factura factura = repositorioFactura.save(new Factura(232323, pedido));

		int valorTotal = producto.getValorUnitario() * 1;
		int valorIvaTotal = (int) ((valorTotal * 0.19) + valorTotal);
		// Generacion de detalleFactura
		Detallefactura detalle = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));
		// Pruebas De Aceptacion

		assertThat(detalle).hasFieldOrPropertyWithValue("cantidadProducto", 1);
		assertThat(detalle).hasFieldOrPropertyWithValue("valorTotal", valorTotal);
		assertThat(detalle).hasFieldOrPropertyWithValue("valorIvaTotal", valorIvaTotal);
		assertThat(detalle).hasFieldOrPropertyWithValue("factura", factura);
		assertThat(detalle).hasFieldOrPropertyWithValue("producto", producto);
	}

	@Test
	public void should_find_all_detalle() {
		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Bodega
		Bodega bodega = repositorioBodega.save(new Bodega("Bodega A", "Local principal", null));
		// Generacion de Proveedor
		Proveedor proveedor = repositorioProveedor
				.save(new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", "7374228"));
		// Generacion del Categoria
		Categoriaproducto categoriaproducto = repositorioCategoria.save(new Categoriaproducto("MacBook Pro"));
		// Generacion del Objeto Producto
		Producto producto = repositorioProducto.save(new Producto(100, "apple", 5, "Apple MacBook Pro 2019",
				"Ejemplo URL For", 120000, bodega, categoriaproducto, proveedor));
		// Generacion de Usuario CLIENTE
		Usuario usuario = repositorioUsuario.save(new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña",
				"dirección casa", "3218956845", permisos, null));
		// GENERACION DE Pedio
		Pedido pedido = repositorioPedido.save(new Pedido(false, true, 1004, usuario));
		// Generacion de Factura
		Factura factura = repositorioFactura.save(new Factura(232323, pedido));

		int valorTotal = producto.getValorUnitario() * 1;
		int valorIvaTotal = (int) ((valorTotal * 0.19) + valorTotal);
		// Generacion de detalleFactura

		Detallefactura detalle = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));
		Detallefactura detalle2 = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));
		Detallefactura detalle3 = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));

		Iterable<Detallefactura> detalles = repositorioDetalle.findAll();

		assertThat(detalles).hasSize(3).contains(detalle, detalle2, detalle3);
	}

	@Test
	public void should_find_detalle_by_id() {
		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Bodega
		Bodega bodega = repositorioBodega.save(new Bodega("Bodega A", "Local principal", null));
		entityManager.persist(bodega);
		// Generacion de Proveedor
		Proveedor proveedor = repositorioProveedor
				.save(new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", "7374228"));
		entityManager.persist(proveedor);
		// Generacion del Categoria
		Categoriaproducto categoriaproducto = repositorioCategoria.save(new Categoriaproducto("MacBook Pro"));
		entityManager.persist(categoriaproducto);
		// Generacion del Objeto Producto
		Producto producto = repositorioProducto.save(new Producto(100, "apple", 5, "Apple MacBook Pro 2019",
				"Ejemplo URL For", 120000, bodega, categoriaproducto, proveedor));
		entityManager.persist(producto);
		// Generacion de Usuario CLIENTE
		Usuario usuario = repositorioUsuario.save(new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña",
				"dirección casa", "3218956845", permisos, null));
		entityManager.persist(usuario);
		// GENERACION DE Pedio
		Pedido pedido = repositorioPedido.save(new Pedido(false, true, 1004, usuario));
		entityManager.persist(pedido);
		// Generacion de Factura
		Factura factura = repositorioFactura.save(new Factura(232323, pedido));
		entityManager.persist(factura);

		int valorTotal = producto.getValorUnitario() * 1;
		int valorIvaTotal = (int) ((valorTotal * 0.19) + valorTotal);
		// Generacion de detalleFactura

		Detallefactura detalle = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));
		entityManager.persist(detalle);

		// Busqueda de detalle Por ID
		Detallefactura detalleEncontrado = repositorioDetalle.findById(detalle.getId_detalleFactura()).get();

		assertThat(detalleEncontrado).isEqualTo(detalle);
	}

	@Test
	public void should_update_detalle_by_id() {
		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Bodega
		Bodega bodega = repositorioBodega.save(new Bodega("Bodega A", "Local principal", null));
		entityManager.persist(bodega);
		// Generacion de Proveedor
		Proveedor proveedor = repositorioProveedor
				.save(new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", "7374228"));
		entityManager.persist(proveedor);
		// Generacion del Categoria
		Categoriaproducto categoriaproducto = repositorioCategoria.save(new Categoriaproducto("MacBook Pro"));
		entityManager.persist(categoriaproducto);
		// Generacion del Objeto Producto
		Producto producto = repositorioProducto.save(new Producto(100, "apple", 5, "Apple MacBook Pro 2019",
				"Ejemplo URL For", 120000, bodega, categoriaproducto, proveedor));
		entityManager.persist(producto);
		// Generacion de Usuario CLIENTE
		Usuario usuario = repositorioUsuario.save(new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña",
				"dirección casa", "3218956845", permisos, null));
		entityManager.persist(usuario);
		// GENERACION DE Pedio
		Pedido pedido = repositorioPedido.save(new Pedido(false, true, 1004, usuario));
		entityManager.persist(pedido);
		// Generacion de Factura
		Factura factura = repositorioFactura.save(new Factura(232323, pedido));
		entityManager.persist(factura);

		int valorTotal = producto.getValorUnitario() * 1;
		int valorIvaTotal = (int) ((valorTotal * 0.19) + valorTotal);

		// Generacion de detalleFactura
		Detallefactura detalle = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));
		entityManager.persist(detalle);

		// Generacion de detalleFactura2
		Detallefactura detalle2 = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));
		entityManager.persist(detalle);

		int valorTotal3 = producto.getValorUnitario() * 2;
		int valorIvaTotal3 = (int) ((valorTotal * 0.19) + valorTotal);
		// Generacion de detalleFactura3
		Detallefactura detalle3 = repositorioDetalle
				.save(new Detallefactura(2, valorTotal3, valorIvaTotal3, factura, producto));
		entityManager.persist(detalle);

		Detallefactura detalleAc = repositorioDetalle.findById(detalle.getId_detalleFactura()).get();
		detalleAc.setCantidadProducto(detalle3.getCantidadProducto());

		repositorioDetalle.save(detalleAc);

		Detallefactura checkDetalle = repositorioDetalle.findById(detalle3.getId_detalleFactura()).get();

		assertThat(checkDetalle.getCantidadProducto()).isEqualTo(detalleAc.getCantidadProducto());

	}

	@Test
	public void should_delete_detalle_by_id() {
		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Bodega
		Bodega bodega = repositorioBodega.save(new Bodega("Bodega A", "Local principal", null));
		entityManager.persist(bodega);
		// Generacion de Proveedor
		Proveedor proveedor = repositorioProveedor
				.save(new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", "7374228"));
		entityManager.persist(proveedor);
		// Generacion del Categoria
		Categoriaproducto categoriaproducto = repositorioCategoria.save(new Categoriaproducto("MacBook Pro"));
		entityManager.persist(categoriaproducto);
		// Generacion del Objeto Producto
		Producto producto = repositorioProducto.save(new Producto(100, "apple", 5, "Apple MacBook Pro 2019",
				"Ejemplo URL For", 120000, bodega, categoriaproducto, proveedor));
		entityManager.persist(producto);
		// Generacion de Usuario CLIENTE
		Usuario usuario = repositorioUsuario.save(new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña",
				"dirección casa", "3218956845", permisos, null));
		entityManager.persist(usuario);
		// GENERACION DE Pedio
		Pedido pedido = repositorioPedido.save(new Pedido(false, true, 1004, usuario));
		entityManager.persist(pedido);
		// Generacion de Factura
		Factura factura = repositorioFactura.save(new Factura(232323, pedido));
		entityManager.persist(factura);

		int valorTotal = producto.getValorUnitario() * 1;
		int valorIvaTotal = (int) ((valorTotal * 0.19) + valorTotal);

		// Generacion de detalleFactura
		Detallefactura detalle = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));
		entityManager.persist(detalle);

		// Generacion de detalleFactura2
		Detallefactura detalle2 = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));
		entityManager.persist(detalle);

		int valorTotal3 = producto.getValorUnitario() * 2;
		int valorIvaTotal3 = (int) ((valorTotal * 0.19) + valorTotal);

		// Generacion de detalleFactura3
		Detallefactura detalle3 = repositorioDetalle
				.save(new Detallefactura(2, valorTotal3, valorIvaTotal3, factura, producto));
		entityManager.persist(detalle);

		repositorioDetalle.deleteById(detalle3.getId_detalleFactura());

		Iterable<Detallefactura> detalles = repositorioDetalle.findAll();

		assertThat(detalles).hasSize(2).contains(detalle, detalle2);
	}

	@Test
	public void should_delete_all_detalle() {
		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Bodega
		Bodega bodega = repositorioBodega.save(new Bodega("Bodega A", "Local principal", null));
		entityManager.persist(bodega);
		// Generacion de Proveedor
		Proveedor proveedor = repositorioProveedor
				.save(new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", "7374228"));
		entityManager.persist(proveedor);
		// Generacion del Categoria
		Categoriaproducto categoriaproducto = repositorioCategoria.save(new Categoriaproducto("MacBook Pro"));
		entityManager.persist(categoriaproducto);
		// Generacion del Objeto Producto
		Producto producto = repositorioProducto.save(new Producto(100, "apple", 5, "Apple MacBook Pro 2019",
				"Ejemplo URL For", 120000, bodega, categoriaproducto, proveedor));
		entityManager.persist(producto);
		// Generacion de Usuario CLIENTE
		Usuario usuario = repositorioUsuario.save(new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña",
				"dirección casa", "3218956845", permisos, null));
		entityManager.persist(usuario);
		// GENERACION DE Pedio
		Pedido pedido = repositorioPedido.save(new Pedido(false, true, 1004, usuario));
		entityManager.persist(pedido);
		// Generacion de Factura
		Factura factura = repositorioFactura.save(new Factura(232323, pedido));
		entityManager.persist(factura);

		int valorTotal = producto.getValorUnitario() * 1;
		int valorIvaTotal = (int) ((valorTotal * 0.19) + valorTotal);

		// Generacion de detalleFactura
		Detallefactura detalle = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));
		entityManager.persist(detalle);

		// Generacion de detalleFactura2
		Detallefactura detalle2 = repositorioDetalle
				.save(new Detallefactura(1, valorTotal, valorIvaTotal, factura, producto));
		entityManager.persist(detalle);

		int valorTotal3 = producto.getValorUnitario() * 2;
		int valorIvaTotal3 = (int) ((valorTotal * 0.19) + valorTotal);

		// Generacion de detalleFactura3
		Detallefactura detalle3 = repositorioDetalle
				.save(new Detallefactura(2, valorTotal3, valorIvaTotal3, factura, producto));
		entityManager.persist(detalle);

		repositorioDetalle.deleteAll();

		assertThat(repositorioDetalle.findAll()).isEmpty();
	}

}
