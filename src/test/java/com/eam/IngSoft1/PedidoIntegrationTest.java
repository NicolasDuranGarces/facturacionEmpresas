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

import com.eam.IngSoft1.IRepository.IPedidoRepository;
import com.eam.IngSoft1.IRepository.IUsuarioRepository;
import com.eam.IngSoft1.domain.Authority;
import com.eam.IngSoft1.domain.Pedido;
import com.eam.IngSoft1.domain.Usuario;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class PedidoIntegrationTest {
	
		@Autowired
		private TestEntityManager entityManager;

		@Autowired
		IPedidoRepository repositroioPedido;
		@Autowired
		IUsuarioRepository repositroioUsuario;

		@Test
		public void should_find_no_pedidos_if_repository_is_empty() {
			Iterable<Pedido> pedidos = repositroioPedido.findAll();

			for (Pedido pedido : pedidos) {
				System.out.println("Pedido:     " + pedido.toString());
			}
			assertThat(pedidos).isEmpty();
		}

		@Test
		public void should_store_a_pedido() {
			
			//PERMISOS
			Authority permiso = new Authority();
			entityManager.persist(permiso);
			Set<Authority> permisos = new HashSet<Authority>();
			permisos.add(permiso);
			
			//Generacion de Usuario CLIENTE
			Usuario usuario = repositroioUsuario.save(new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null));
			//GENERACION DE Pedio
			Pedido pedido = repositroioPedido.save(new Pedido( false, true, 1004, usuario));

			// Pruebas De Aceptacion
			
			assertThat(pedido).hasFieldOrPropertyWithValue("despachado", false);
			assertThat(pedido).hasFieldOrPropertyWithValue("activo", true);
			assertThat(pedido).hasFieldOrPropertyWithValue("DNI_Encargado", 1004);
			assertThat(pedido).hasFieldOrPropertyWithValue("cliente", usuario);


		}
		
		@Test
		public void should_find_all_pedido() {
			//PERMISOS
			Authority permiso = new Authority();
			entityManager.persist(permiso);
			Set<Authority> permisos = new HashSet<Authority>();
			permisos.add(permiso);
			
			//Generacion de Usuario CLIENTE
			Usuario usuario = repositroioUsuario.save(new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null));
			//GENERACION DE Pedio
			Pedido pedido = repositroioPedido.save(new Pedido( false, true, 1004, usuario));
			//Pedido Numero 2
			Pedido pedido2 = repositroioPedido.save(new Pedido( false, false, 1004, usuario));
			//Pedido Numero 3
			Pedido pedido3 = repositroioPedido.save(new Pedido( true, true, 1004, usuario));
			
			Iterable<Pedido> pedidos = repositroioPedido.findAll();

			assertThat(pedidos).hasSize(3).contains(pedido, pedido2, pedido3);
		}

		@Test
		public void should_find_pedido_by_id() {
			//PERMISOS
			Authority permiso = new Authority();
			entityManager.persist(permiso);
			Set<Authority> permisos = new HashSet<Authority>();
			permisos.add(permiso);
			
			//Generacion de Usuario CLIENTE
			Usuario usuario = new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null);
			entityManager.persist(usuario);
			//GENERACION DE Pedio
			Pedido pedido = new Pedido( false, true, 1004, usuario);
			entityManager.persist(pedido);
			//Pedido Numero 2
			Pedido pedido2 = new Pedido( false, false, 1004, usuario);
			entityManager.persist(pedido2);
			//Pedido Numero 3
			Pedido pedido3 = new Pedido( true, true, 1004, usuario);
			entityManager.persist(pedido3);
			
			

			// Busqueda de Producto Por ID
			Pedido pedidoEncontrado = repositroioPedido.findById(pedido.getIdPedido()).get();

			assertThat(pedidoEncontrado).isEqualTo(pedido);
		}

		@Test
		public void should_update_producto_by_id() {
			//PERMISOS
			Authority permiso = new Authority();
			entityManager.persist(permiso);
			Set<Authority> permisos = new HashSet<Authority>();
			permisos.add(permiso);
			
			//Generacion de Usuario CLIENTE
			Usuario usuario = new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null);
			entityManager.persist(usuario);
			//GENERACION DE Pedio
			Pedido pedido = new Pedido( false, true, 1004, usuario);
			entityManager.persist(pedido);
			//Pedido Numero 2
			Pedido pedido2 = new Pedido( false, false, 1004, usuario);
			entityManager.persist(pedido2);
			//Pedido Numero 3
			Pedido pedido3 = new Pedido( true, true, 1004, usuario);
			entityManager.persist(pedido3);
			

			// Objeto Producto con Parametros Actulizados
			Pedido pedidoActualizado = new Pedido( true, true, 10076, usuario);

			Pedido pedidoAc = repositroioPedido.findById(pedido.getIdPedido()).get();
			pedidoAc.setDNI_vendedor(pedidoAc.getDNI_vendedor());
		
			repositroioPedido.save(pedidoAc);

			Pedido checkPedido = repositroioPedido.findById(pedido.getIdPedido()).get();

			assertThat(checkPedido.getIdPedido()).isEqualTo(pedido.getIdPedido());


		}

		@Test
		public void should_delete_pedido_by_id() {
			//PERMISOS
			Authority permiso = new Authority();
			entityManager.persist(permiso);
			Set<Authority> permisos = new HashSet<Authority>();
			permisos.add(permiso);
			
			//Generacion de Usuario CLIENTE
			Usuario usuario = new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null);
			entityManager.persist(usuario);
			//GENERACION DE Pedio
			Pedido pedido = new Pedido( false, true, 1004, usuario);
			entityManager.persist(pedido);
			//Pedido Numero 2
			Pedido pedido2 = new Pedido( false, false, 1004, usuario);
			entityManager.persist(pedido2);
			//Pedido Numero 3
			Pedido pedido3 = new Pedido( true, true, 1004, usuario);
			entityManager.persist(pedido3);

			repositroioPedido.deleteById(pedido2.getIdPedido());

			Iterable<Pedido> pedidos = repositroioPedido.findAll();

			assertThat(pedidos).hasSize(2).contains(pedido, pedido3);
		}
		
		@Test
		public void should_delete_all_pedidos() {
			//PERMISOS
			Authority permiso = new Authority();
			entityManager.persist(permiso);
			Set<Authority> permisos = new HashSet<Authority>();
			permisos.add(permiso);
			
			//Generacion de Usuario CLIENTE
			Usuario usuario = new Usuario(106359, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null);
			entityManager.persist(usuario);
			//GENERACION DE Pedio
			Pedido pedido = new Pedido( false, true, 1004, usuario);
			entityManager.persist(pedido);
			//Pedido Numero 2
			Pedido pedido2 = new Pedido( false, false, 1004, usuario);
			entityManager.persist(pedido2);
			//Pedido Numero 3
			Pedido pedido3 = new Pedido( true, true, 1004, usuario);
			entityManager.persist(pedido3);

			repositroioPedido.deleteAll();

			assertThat(repositroioPedido.findAll()).isEmpty();
		}
		

		
	}
