package com.eam.IngSoft1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eam.IngSoft1.IRepository.IProveedorRepository;
import com.eam.IngSoft1.domain.Proveedor;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProveedorIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	IProveedorRepository repository;

	@Test
	public void should_find_no_Proveedors_if_repository_is_empty() {
		Iterable<Proveedor> proveedors = repository.findAll();

		for (Proveedor proveedor : proveedors) {
			System.out.println("Proveedor:     " + proveedor.toString());
		}

		assertThat(proveedors).isEmpty();
	}

	@Test
	public void should_find_all_Proveedors() {
		Proveedor proveedor1 = new Proveedor("Carrera a verificar#1", "Proveedir#1", "77557");
		entityManager.persist(proveedor1);

		Proveedor proveedor2 = new Proveedor("Carrera a verificar#2", "Proveedir#2", "77557");
		entityManager.persist(proveedor2);

		Proveedor proveedor3 = new Proveedor("Carrera a verificar#3", "Proveedir#3", "77557");
		entityManager.persist(proveedor3);

		Iterable<Proveedor> proveedors = repository.findAll();

		assertThat(proveedors).hasSize(3).contains(proveedor1, proveedor2, proveedor3);
	}

	@Test
	public void should_find_Proveedor_by_id() {
		Proveedor proveedor1 = new Proveedor("Carrera a verificar#1", "Proveedir#1", "77557");
		entityManager.persist(proveedor1);

		Proveedor proveedor2 = new Proveedor("Carrera a verificar#2", "Proveedir#2", "77557");
		entityManager.persist(proveedor2);

		Proveedor foundProveedor = repository.findById(proveedor2.getIdProveedor()).get();

		assertThat(foundProveedor).isEqualTo(proveedor2);
	}


	@Test
	public void should_update_Proveedor_by_id() {
		Proveedor proveedor1 = new Proveedor("Carrera a verificar#1", "Proveedor#1", "77557");
		entityManager.persist(proveedor1);

		Proveedor proveedor2 = new Proveedor("Carrera a verificar#2", "Proveedor#2", "77557");
		entityManager.persist(proveedor2);

		Proveedor updatedProveedor = new Proveedor("Carrera update Tut#2", "Proveedor Upd", "5555");

		Proveedor proveedor = repository.findById(proveedor2.getIdProveedor()).get();
		
		proveedor.setNombreProvedor(updatedProveedor.getNombreProvedor());
		proveedor.setDireccion(updatedProveedor.getDireccion());
		proveedor.setTelefono(updatedProveedor.getTelefono());
		repository.save(proveedor);

		Proveedor checkProveedor = repository.findById(proveedor2.getIdProveedor()).get();

		assertThat(checkProveedor.getIdProveedor()).isEqualTo(proveedor2.getIdProveedor());
		assertThat(checkProveedor.getNombreProvedor()).isEqualTo(updatedProveedor.getNombreProvedor());
		assertThat(checkProveedor.getDireccion()).isEqualTo(updatedProveedor.getDireccion());
		assertThat(checkProveedor.getTelefono()).isEqualTo(updatedProveedor.getTelefono());

	}

	@Test
	public void should_delete_Proveedor_by_id() {
		Proveedor proveedor1 = new Proveedor("Carrera a verificar#1", "Proveedor#1", "77557");
		entityManager.persist(proveedor1);

		Proveedor proveedor2 = new Proveedor("Carrera a verificar#2", "Proveedor#2", "77557");
		entityManager.persist(proveedor2);

		Proveedor proveedor3 = new Proveedor("Carrera a verificar#3", "Proveedorr#3", "77557");
		entityManager.persist(proveedor3);

		repository.deleteById(proveedor1.getIdProveedor());

		Iterable<Proveedor> proveedors = repository.findAll();

		assertThat(proveedors).hasSize(2).contains(proveedor2, proveedor3);
	}
}
