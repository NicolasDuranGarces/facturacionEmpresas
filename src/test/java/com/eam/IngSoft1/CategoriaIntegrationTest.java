package com.eam.IngSoft1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eam.IngSoft1.domain.Categoriaproducto;

import com.eam.IngSoft1.IRepository.ICategoriaRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CategoriaIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	ICategoriaRepository repository;

	@Test
	public void should_find_no_CategoriaProductos_if_repository_is_empty() {
		Iterable<Categoriaproducto> categorias = repository.findAll();

		for (Categoriaproducto categoria : categorias) {
			System.out.println("Categoria:     " + categoria.toString());
		}

		assertThat(categorias).isEmpty();
	}

	@Test
	public void should_find_all_CategoriaProductos() {
		Categoriaproducto categoria1 = new Categoriaproducto("Categoria#1");
		entityManager.persist(categoria1);

		Categoriaproducto categoria2 = new Categoriaproducto("Categoria#2");
		entityManager.persist(categoria2);

		Categoriaproducto categoria3 = new Categoriaproducto("Categoria#3");
		entityManager.persist(categoria3);

		Iterable<Categoriaproducto> users = repository.findAll();

		assertThat(users).hasSize(3).contains(categoria1, categoria2, categoria3);
	}

	@Test
	public void should_find_CategoriaProducto_by_id() {
		Categoriaproducto categoria1 = new Categoriaproducto("Categoria#1");
		entityManager.persist(categoria1);

		Categoriaproducto categoria2 = new Categoriaproducto("Categoria#2");
		entityManager.persist(categoria2);

		Categoriaproducto foundUser = repository.findById(categoria2.getId_categoriaProducto()).get();

		assertThat(foundUser).isEqualTo(categoria2);
	}

	
	@Test
	  public void should_update_Categoriaproducto_by_id() {
		Categoriaproducto categoria1 = new Categoriaproducto("Categoria#1");
		entityManager.persist(categoria1);

		Categoriaproducto categoria2 = new Categoriaproducto("Categoria#2");
		entityManager.persist(categoria2);

		Categoriaproducto updatedCategori = new Categoriaproducto("Categoria Tut#2");

		Categoriaproducto categoria = repository.findById(categoria2.getId_categoriaProducto()).get();
		categoria.setNombreCategoria(updatedCategori.getNombreCategoria());
	    repository.save(categoria);

	    Categoriaproducto checkCategoria = repository.findById(categoria2.getId_categoriaProducto()).get();
	    
	    assertThat(checkCategoria.getId_categoriaProducto()).isEqualTo(categoria2.getId_categoriaProducto());
	    assertThat(checkCategoria.getNombreCategoria()).isEqualTo(updatedCategori.getNombreCategoria());


	  }

	  @Test
	  public void should_delete_Categoriaproducto_by_id() {
		  Categoriaproducto categoria1 = new Categoriaproducto("Categoria#1");
			entityManager.persist(categoria1);

			Categoriaproducto categoria2 = new Categoriaproducto("Categoria#2");
			entityManager.persist(categoria2);

			Categoriaproducto categoria3 = new Categoriaproducto("Categoria#3");
			entityManager.persist(categoria3);

	    repository.deleteById(categoria2.getId_categoriaProducto());

	    Iterable<Categoriaproducto> categorias = repository.findAll();

	    assertThat(categorias).hasSize(2).contains(categoria1, categoria3 );
	  }
}
