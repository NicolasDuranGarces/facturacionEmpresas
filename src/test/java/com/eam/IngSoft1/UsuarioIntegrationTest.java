package com.eam.IngSoft1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;


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
/*
	@Test
	public void should_store_a_usuario() {
		Usuario usuario = repository.save(new Usuario("Usuario A", "Local principal", null));

		assertThat(usuario).hasFieldOrPropertyWithValue("idUsuario", 1);
		assertThat(usuario).hasFieldOrPropertyWithValue("nombreUsuario", "Usuario A");
		assertThat(usuario).hasFieldOrPropertyWithValue("ubicacion", "Local principal");
	}

	@Test
	public void should_find_all_usuarios() {
		Usuario usuario1 = new Usuario("Usuario A", "Local principal", null);
		entityManager.persist(usuario1);

		Usuario usuario2 = new Usuario("Usuario B", "Local en el centro", null);
		entityManager.persist(usuario2);

		Usuario usuario3 = new Usuario("Usuario C", "Local principal", null);
		entityManager.persist(usuario3);

		Iterable<Usuario> usuarios = repository.findAll();

		assertThat(usuarios).hasSize(3).contains(usuario1, usuario2, usuario3);
	}

	@Test
	public void should_find_usuario_by_id() {
		Usuario usuario1 = new Usuario("Usuario A", "Local principal", null);
		entityManager.persist(usuario1);

		Usuario usuario2 = new Usuario("Usuario B", "Local en el centro", null);
		entityManager.persist(usuario2);

		Usuario foundUsuario = repository.findById(usuario2.getIdUsuario()).get();

		assertThat(foundUsuario).isEqualTo(usuario2);
	}

	@Test
	public void should_update_usuario_by_id() {
		Usuario usuario1 = new Usuario("Usuario A", "Local principal", null);
		entityManager.persist(usuario1);

		Usuario usuario2 = new Usuario("Usuario B", "Local en el centro", null);
		entityManager.persist(usuario2);

		Usuario updatedUsuario = new Usuario("updated name", "updated ubicación", null);

		Usuario usuario = repository.findById(usuario2.getIdUsuario()).get();
		usuario.setNombreUsuario(updatedUsuario.getNombreUsuario());
		usuario.setUbicacion(updatedUsuario.getUbicacion());
		repository.save(usuario);

		Usuario checkUsuario = repository.findById(usuario2.getIdUsuario()).get();

		assertThat(checkUsuario.getIdUsuario()).isEqualTo(usuario2.getIdUsuario());
		assertThat(checkUsuario.getNombreUsuario()).isEqualTo(updatedUsuario.getNombreUsuario());
		assertThat(checkUsuario.getUbicacion()).isEqualTo(updatedUsuario.getUbicacion());

	}

	@Test
	public void should_delete_usuario_by_id() {
		Usuario usuario1 = new Usuario("Usuario A", "Local principal", null);
		entityManager.persist(usuario1);

		Usuario usuario2 = new Usuario("Usuario B", "Local en el centro", null);
		entityManager.persist(usuario2);

		Usuario usuario3 = new Usuario("Usuario C", "Local principal", null);
		entityManager.persist(usuario3);

		repository.deleteById(usuario2.getIdUsuario());

		Iterable<Usuario> usuarios = repository.findAll();

		assertThat(usuarios).hasSize(2).contains(usuario1, usuario3);
	}

	@Test
	public void should_delete_all_usuarios() {
		Usuario usuario1 = new Usuario("Usuario A", "Local principal", null);
		entityManager.persist(usuario1);

		Usuario usuario2 = new Usuario("Usuario B", "Local en el centro", null);
		entityManager.persist(usuario2);

		Usuario usuario3 = new Usuario("Usuario C", "Local principal", null);
		entityManager.persist(usuario3);

		repository.deleteAll();

		assertThat(repository.findAll()).isEmpty();
	}
	
	*/
}
