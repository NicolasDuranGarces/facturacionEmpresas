package com.eam.IngSoft1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eam.IngSoft1.domain.Bodega;
import com.eam.IngSoft1.IRepository.IBodegaRepository;
import com.eam.IngSoft1.domain.Producto;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BodegaIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	IBodegaRepository repository;

	@Test
	public void should_find_no_bodegas_if_repository_is_empty() {
		Iterable<Bodega> bodegas = repository.findAll();

		for (Bodega bodega : bodegas) {
			System.out.println("Bodega:     " + bodega.toString());
		}

		assertThat(bodegas).isEmpty();
	}

	@Test
	public void should_store_a_bodega() {
		Bodega bodega = repository.save(new Bodega("Bodega A", "Local principal", null));

		assertThat(bodega).hasFieldOrPropertyWithValue("idBodega", 1);
		assertThat(bodega).hasFieldOrPropertyWithValue("nombreBodega", "Bodega A");
		assertThat(bodega).hasFieldOrPropertyWithValue("ubicacion", "Local principal");
	}

	@Test
	public void should_find_all_bodegas() {
		Bodega bodega1 = new Bodega("Bodega A", "Local principal", null);
		entityManager.persist(bodega1);

		Bodega bodega2 = new Bodega("Bodega B", "Local en el centro", null);
		entityManager.persist(bodega2);

		Bodega bodega3 = new Bodega("Bodega C", "Local principal", null);
		entityManager.persist(bodega3);

		Iterable<Bodega> bodegas = repository.findAll();

		assertThat(bodegas).hasSize(3).contains(bodega1, bodega2, bodega3);
	}

	@Test
	public void should_find_bodega_by_id() {
		Bodega bodega1 = new Bodega("Bodega A", "Local principal", null);
		entityManager.persist(bodega1);

		Bodega bodega2 = new Bodega("Bodega B", "Local en el centro", null);
		entityManager.persist(bodega2);

		Bodega foundBodega = repository.findById(bodega2.getIdBodega()).get();

		assertThat(foundBodega).isEqualTo(bodega2);
	}

	@Test
	public void should_update_bodega_by_id() {
		Bodega bodega1 = new Bodega("Bodega A", "Local principal", null);
		entityManager.persist(bodega1);

		Bodega bodega2 = new Bodega("Bodega B", "Local en el centro", null);
		entityManager.persist(bodega2);

		Bodega updatedBodega = new Bodega("updated name", "updated ubicación", null);

		Bodega bodega = repository.findById(bodega2.getIdBodega()).get();
		bodega.setNombreBodega(updatedBodega.getNombreBodega());
		bodega.setUbicacion(updatedBodega.getUbicacion());
		repository.save(bodega);

		Bodega checkBodega = repository.findById(bodega2.getIdBodega()).get();

		assertThat(checkBodega.getIdBodega()).isEqualTo(bodega2.getIdBodega());
		assertThat(checkBodega.getNombreBodega()).isEqualTo(updatedBodega.getNombreBodega());
		assertThat(checkBodega.getUbicacion()).isEqualTo(updatedBodega.getUbicacion());

	}

	@Test
	public void should_delete_bodega_by_id() {
		Bodega bodega1 = new Bodega("Bodega A", "Local principal", null);
		entityManager.persist(bodega1);

		Bodega bodega2 = new Bodega("Bodega B", "Local en el centro", null);
		entityManager.persist(bodega2);

		Bodega bodega3 = new Bodega("Bodega C", "Local principal", null);
		entityManager.persist(bodega3);

		repository.deleteById(bodega2.getIdBodega());

		Iterable<Bodega> bodegas = repository.findAll();

		assertThat(bodegas).hasSize(2).contains(bodega1, bodega3);
	}

	@Test
	public void should_delete_all_bodegas() {
		Bodega bodega1 = new Bodega("Bodega A", "Local principal", null);
		entityManager.persist(bodega1);

		Bodega bodega2 = new Bodega("Bodega B", "Local en el centro", null);
		entityManager.persist(bodega2);

		Bodega bodega3 = new Bodega("Bodega C", "Local principal", null);
		entityManager.persist(bodega3);

		repository.deleteAll();

		assertThat(repository.findAll()).isEmpty();
	}
	
	@Test
	public void shoud_get_all_products_in_a_bodega() {		
		ArrayList<Producto> listaProductos = new ArrayList<Producto>();
		
		Producto producto1 = new Producto();
		listaProductos.add(producto1);
		
		Producto producto2 = new Producto();
		listaProductos.add(producto2);
		
		Producto producto3 = new Producto();
		listaProductos.add(producto3);
		
		Bodega bodega1 = new Bodega("Bodega A", "Local principal", listaProductos);
		entityManager.persist(bodega1);
		
		Iterable<Producto> foundProductos = repository.findById(bodega1.getIdBodega()).get().getProductos();
		
		assertThat(foundProductos).hasSize(3).contains(producto1, producto2, producto3);;
		
	}
	
}