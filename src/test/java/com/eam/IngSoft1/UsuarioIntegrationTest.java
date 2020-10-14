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

import com.eam.IngSoft1.domain.Authority;
import com.eam.IngSoft1.domain.Usuario;
import com.eam.IngSoft1.IRepository.IUsuarioRepository;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class UsuarioIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	IUsuarioRepository repository;
	
	
	@Test
	public void should_find_no_usuarios_if_repository_is_empty() {
		Iterable<Usuario> usuarios = repository.findAll();

		for (Usuario usuario : usuarios) {
			System.out.println("Usuario:     " + usuario.toString());
		}

		assertThat(usuarios).isEmpty();
	}

	@Test
	public void should_store_a_usuario() {
		Authority permiso = new Authority("ROLE_USER");
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);
		Usuario usuario = repository.save(new Usuario(10635940, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null));

		assertThat(usuario).hasFieldOrPropertyWithValue("dni", 10635940);
		assertThat(usuario).hasFieldOrPropertyWithValue("nombre", "Juan");
		assertThat(usuario).hasFieldOrPropertyWithValue("apellido", "Pérez");
		assertThat(usuario).hasFieldOrPropertyWithValue("nombreUsuario", "jperez");
		assertThat(usuario).hasFieldOrPropertyWithValue("contrasena", "contraseña");
		assertThat(usuario).hasFieldOrPropertyWithValue("direccion", "dirección casa");
		assertThat(usuario).hasFieldOrPropertyWithValue("telefono", "3218956845");
		assertThat(usuario).hasFieldOrPropertyWithValue("authority", permisos);
		assertThat(usuario).hasFieldOrPropertyWithValue("pedidos", null);
	}

	@Test
	public void should_find_all_usuarios() {
		Authority permiso = new Authority("ROLE_USER");
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);
		
		Usuario usuario1 = new Usuario(10635940, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null);
		entityManager.persist(usuario1);

		Usuario usuario2 = new Usuario(10635941, "Andrés", "Villanueva", "avilla", "contraseña", "dirección casa", "3218956846", permisos, null);
		entityManager.persist(usuario2);

		Usuario usuario3 = new Usuario(10635942, "Camilo", "Botero", "cbotero", "contraseña", "dirección casa", "3218956847", permisos, null);
		entityManager.persist(usuario3);

		Iterable<Usuario> usuarios = repository.findAll();

		assertThat(usuarios).hasSize(3).contains(usuario1, usuario2, usuario3);
	}

	@Test
	public void should_find_usuario_by_id() {
		Authority permiso = new Authority("ROLE_USER");
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);
		
		Usuario usuario1 = new Usuario(10635940, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null);
		entityManager.persist(usuario1);

		Usuario usuario2 = new Usuario(10635941, "Andrés", "Villanueva", "avilla", "contraseña", "dirección casa", "3218956846", permisos, null);
		entityManager.persist(usuario2);

		Usuario foundUsuario = repository.findById(usuario2.getDni()).get();

		assertThat(foundUsuario).isEqualTo(usuario2);
	}

	@Test
	public void should_update_usuario_by_id() {
		Authority permiso = new Authority("ROLE_USER");
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);
		
		Usuario usuario1 = new Usuario(10635940, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null);
		entityManager.persist(usuario1);

		Usuario usuario2 = new Usuario(10635941, "Andrés", "Villanueva", "avilla", "contraseña", "dirección casa", "3218956846", permisos, null);
		entityManager.persist(usuario2);

		Authority permiso2 = new Authority("ROLE_EMPLEADO");
		entityManager.persist(permiso2);
		Set<Authority> permisos2 = new HashSet<Authority>();
		permisos2.add(permiso2);
		Usuario updatedUsuario = new Usuario(10635941, "Camilo", "Botero", "cbotero", "contraseña nueva", "dirección nueva casa", "3218956847", permisos2, null);

		Usuario usuario = repository.findById(usuario2.getDni()).get();
		usuario.setNombre(updatedUsuario.getNombre());
		usuario.setApellido(updatedUsuario.getApellido());
		usuario.setNombreUsuario(updatedUsuario.getNombreUsuario());
		usuario.setContrasena(updatedUsuario.getContrasena());
		usuario.setDireccion(updatedUsuario.getDireccion());
		usuario.setTelefono(updatedUsuario.getTelefono());
		usuario.setAuthority(updatedUsuario.getAuthority());
		usuario.setPedidos(updatedUsuario.getPedidos());
		repository.save(usuario);

		Usuario checkUsuario = repository.findById(usuario2.getDni()).get();

		assertThat(checkUsuario.getDni()).isEqualTo(usuario2.getDni());
		assertThat(checkUsuario.getNombre()).isEqualTo(updatedUsuario.getNombre());
		assertThat(checkUsuario.getApellido()).isEqualTo(updatedUsuario.getApellido());
		assertThat(checkUsuario.getNombreUsuario()).isEqualTo(updatedUsuario.getNombreUsuario());
		assertThat(checkUsuario.getContrasena()).isEqualTo(updatedUsuario.getContrasena());
		assertThat(checkUsuario.getDireccion()).isEqualTo(updatedUsuario.getDireccion());
		assertThat(checkUsuario.getTelefono()).isEqualTo(updatedUsuario.getTelefono());
		assertThat(checkUsuario.getAuthority()).isEqualTo(updatedUsuario.getAuthority());
		assertThat(checkUsuario.getPedidos()).isEqualTo(updatedUsuario.getPedidos());

	}
	
	

	@Test
	public void should_delete_usuario_by_id() {
		Authority permiso = new Authority("ROLE_USER");
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);
		
		Usuario usuario1 = new Usuario(10635940, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null);
		entityManager.persist(usuario1);

		Usuario usuario2 = new Usuario(10635941, "Andrés", "Villanueva", "avilla", "contraseña", "dirección casa", "3218956846", permisos, null);
		entityManager.persist(usuario2);

		Usuario usuario3 = new Usuario(10635942, "Camilo", "Botero", "cbotero", "contraseña", "dirección casa", "3218956847", permisos, null);
		entityManager.persist(usuario3);

		repository.deleteById(usuario2.getDni());

		Iterable<Usuario> usuarios = repository.findAll();

		assertThat(usuarios).hasSize(2).contains(usuario1, usuario3);
	}
	

	@Test
	public void should_delete_all_usuarios() {
		Authority permiso = new Authority("ROLE_USER");
		entityManager.persist(permiso);
		Set<Authority> permisos = new HashSet<Authority>();
		permisos.add(permiso);
		
		Usuario usuario1 = new Usuario(10635940, "Juan", "Pérez", "jperez", "contraseña", "dirección casa", "3218956845", permisos, null);
		entityManager.persist(usuario1);

		Usuario usuario2 = new Usuario(10635941, "Andrés", "Villanueva", "avilla", "contraseña", "dirección casa", "3218956846", permisos, null);
		entityManager.persist(usuario2);

		Usuario usuario3 = new Usuario(10635942, "Camilo", "Botero", "cbotero", "contraseña", "dirección casa", "3218956847", permisos, null);
		entityManager.persist(usuario3);

		repository.deleteAll();

		assertThat(repository.findAll()).isEmpty();
	}
	
	
}
