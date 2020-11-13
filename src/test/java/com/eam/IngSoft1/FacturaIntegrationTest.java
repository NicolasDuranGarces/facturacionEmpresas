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

import com.eam.IngSoft1.IRepository.IFacturaRepository;
import com.eam.IngSoft1.IRepository.IPedidoRepository;
import com.eam.IngSoft1.IRepository.IUsuarioRepository;
import com.eam.IngSoft1.domain.Authority;
import com.eam.IngSoft1.domain.Factura;
import com.eam.IngSoft1.domain.Pedido;
import com.eam.IngSoft1.domain.Usuario;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FacturaIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	IPedidoRepository repositroioPedido;

	@Autowired
	IFacturaRepository repositroioFactura;

	@Autowired
	IUsuarioRepository repositroioUsuario;

	@Test
	public void should_find_no_factura_if_repository_is_empty() {
		Iterable<Factura> facturas = repositroioFactura.findAll();

		for (Factura factura : facturas) {
			System.out.println("Facturas:     " + factura.toString());
		}
		assertThat(facturas).isEmpty();
	}

	@Test
	public void should_store_a_factura() {

		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Usuario CLIENTE
		Usuario usuario = repositroioUsuario.save(new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña",
				"dirección casa", "3218956845", permisos, null));
		// GENERACION DE Pedio
		Pedido pedido = repositroioPedido.save(new Pedido(false, true, 1004, usuario));
		// Generacion de Factura
		Factura factura = repositroioFactura.save(new Factura(232323, pedido));

		// Pruebas De Aceptacion

		assertThat(factura).hasFieldOrPropertyWithValue("precioTotal", 232323);
		assertThat(factura).hasFieldOrPropertyWithValue("pedido", pedido);
	}

	@Test
	public void should_find_all_factura() {
		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Usuario CLIENTE
		Usuario usuario = repositroioUsuario.save(new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña",
				"dirección casa", "3218956845", permisos, null));
		// GENERACION DE Pedio
		Pedido pedido = repositroioPedido.save(new Pedido(false, true, 1004, usuario));

		Factura factura = repositroioFactura.save(new Factura(232323, pedido));
		Factura factura2 = repositroioFactura.save(new Factura(232323, pedido));
		Factura factura3 = repositroioFactura.save(new Factura(232323, pedido));

		Iterable<Factura> facturas = repositroioFactura.findAll();

		assertThat(facturas).hasSize(3).contains(factura, factura2, factura3);
	}

	@Test
	public void should_find_factura_by_id() {
		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Usuario CLIENTE
		Usuario usuario = new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845",
				permisos, null);
		entityManager.persist(usuario);
		// GENERACION DE Pedio
		Pedido pedido = new Pedido(false, true, 1004, usuario);
		entityManager.persist(pedido);
		// Factura
		Factura factura = new Factura(232323, pedido);
		entityManager.persist(factura);

		// Busqueda de factura Por ID
		Factura facturaEncontrado = repositroioFactura.findById(factura.getIdFactura()).get();

		assertThat(facturaEncontrado).isEqualTo(factura);
	}

	@Test
	public void should_update_factura_by_id() {
		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Usuario CLIENTE
		Usuario usuario = new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845",
				permisos, null);
		entityManager.persist(usuario);
		// GENERACION DE Pedio
		Pedido pedido = new Pedido(false, true, 1004, usuario);
		entityManager.persist(pedido);
		// Factura
		Factura factura = new Factura(232323, pedido);
		entityManager.persist(factura);

		// Factura 2
		Factura factura2 = new Factura(232323, pedido);
		entityManager.persist(factura2);

		// Factura 3
		Factura factura3 = new Factura(3232323, pedido);
		entityManager.persist(factura3);

		Factura facturaAc = repositroioFactura.findById(factura.getIdFactura()).get();
		facturaAc.setPrecioTotal(factura3.getPrecioTotal());

		repositroioFactura.save(facturaAc);

		Factura checkPedido = repositroioFactura.findById(factura3.getIdFactura()).get();

		assertThat(checkPedido.getPrecioTotal()).isEqualTo(facturaAc.getPrecioTotal());

	}

	@Test
	public void should_delete_factura_by_id() {
		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Usuario CLIENTE
		Usuario usuario = new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845",
				permisos, null);
		entityManager.persist(usuario);
		// GENERACION DE Pedio
		Pedido pedido = new Pedido(false, true, 1004, usuario);
		entityManager.persist(pedido);
		// Factura
		Factura factura = new Factura(232323, pedido);
		entityManager.persist(factura);

		// Factura 2
		Factura factura2 = new Factura(232323, pedido);
		entityManager.persist(factura2);

		// Factura 3
		Factura factura3 = new Factura(3232323, pedido);
		entityManager.persist(factura3);

		repositroioFactura.deleteById(factura3.getIdFactura());

		Iterable<Factura> facturas = repositroioFactura.findAll();

		assertThat(facturas).hasSize(2).contains(factura, factura2);
	}

	@Test
	public void should_delete_all_factura() {
		// PERMISOS
		Authority permiso = new Authority();
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);

		// Generacion de Usuario CLIENTE
		Usuario usuario = new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845",
				permisos, null);
		entityManager.persist(usuario);
		// GENERACION DE Pedio
		Pedido pedido = new Pedido(false, true, 1004, usuario);
		entityManager.persist(pedido);
		// Factura
		Factura factura = new Factura(232323, pedido);
		entityManager.persist(factura);

		// Factura 2
		Factura factura2 = new Factura(232323, pedido);
		entityManager.persist(factura2);

		// Factura 3
		Factura factura3 = new Factura(3232323, pedido);
		entityManager.persist(factura3);

		repositroioFactura.deleteAll();

		assertThat(repositroioFactura.findAll()).isEmpty();
	}

}
