package com.eam.IngSoft1;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eam.IngSoft1.domain.Bodega;
import com.eam.IngSoft1.domain.Categoriaproducto;
import com.eam.IngSoft1.domain.Producto;
import com.eam.IngSoft1.domain.Proveedor;
import com.eam.IngSoft1.IRepository.IBodegaRepository;
import com.eam.IngSoft1.IRepository.ICategoriaRepository;
import com.eam.IngSoft1.IRepository.IProductoRepository;
import com.eam.IngSoft1.IRepository.IProveedorRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductoIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	IProductoRepository repository;
	@Autowired
	IBodegaRepository repositoryBodega;
	@Autowired
	IProveedorRepository repositoryProveedor;
	@Autowired
	ICategoriaRepository repositoryCategoria;

	@Test
	public void should_find_no_productos_if_repository_is_empty() {
		Iterable<Producto> productos = repository.findAll();

		for (Producto bodega : productos) {
			System.out.println("Producto:     " + bodega.toString());
		}

		assertThat(productos).isEmpty();
	}

	@Test
	public void should_store_a_producto() {
		// Producto tiene Varios Elementos Dependientes (Bodega-Proveedor-Categoria)
		// Entonces Se comineza con La generacion de estos
		// Generacion de Bodega
		Bodega bodega = repositoryBodega.save(new Bodega("Bodega A", "Local principal", null));
		// Generacion de Proveedor
		Proveedor proveedor = repositoryProveedor
				.save(new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", 7374228));
		// Generacion del Categoria
		Categoriaproducto categoriaproducto = repositoryCategoria.save(new Categoriaproducto("MacBook Pro"));
		// Generacion del Objeto Producto
		Producto producto = repository.save(new Producto(100, "apple", 5, "Apple MacBook Pro 2019", "Ejemplo URL For",
				16000000, bodega, categoriaproducto, proveedor));

		// Pruebas De Aceptacion
		
		assertThat(producto).hasFieldOrPropertyWithValue("cantidadActual", 100);
		assertThat(producto).hasFieldOrPropertyWithValue("marca", "apple");
		assertThat(producto).hasFieldOrPropertyWithValue("minimoInventario", 5);
		assertThat(producto).hasFieldOrPropertyWithValue("nombreProducto", "Apple MacBook Pro 2019");
		assertThat(producto).hasFieldOrPropertyWithValue("urlFoto", "Ejemplo URL For");
		assertThat(producto).hasFieldOrPropertyWithValue("valorUnitario", 16000000);
		assertThat(producto).hasFieldOrPropertyWithValue("bodega", bodega);
		assertThat(producto).hasFieldOrPropertyWithValue("categoriaproducto", categoriaproducto);
		assertThat(producto).hasFieldOrPropertyWithValue("proveedor", proveedor);

	}

	@Test
	public void should_find_all_producto() {
		// Generacion de Objetos dependientes de Producto(Bodega-Proveedor-Categoria)
		// Generacion de Bodega
		Bodega bodega = new Bodega("Bodega A", "Local principal", null);
		entityManager.persist(bodega);
		// Generacion de Proveedor
		Proveedor proveedor = new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", 7374228);
		entityManager.persist(proveedor);
		// Genercion de Categoria
		Categoriaproducto categoriaproducto = new Categoriaproducto("MacBook Pro");
		entityManager.persist(categoriaproducto);
		// Generacion de Producto 1
		Producto producto1 = new Producto(100, "apple", 5, "Apple MacBook Pro 2019", "Ejemplo URL For", 16000000,
				bodega, categoriaproducto, proveedor);
		entityManager.persist(producto1);
		// Generacion de Producto 2
		Producto producto2 = new Producto(100, "MSI", 5, "MSI GE73VR 7RF Raider", "Ejemplo URL For", 11000000, bodega,
				categoriaproducto, proveedor);
		entityManager.persist(producto2);
		// Generacion de Producto 3
		Producto producto3 = new Producto(100, "Asus", 5, "Asus VivoBook 2019", "Ejemplo URL For", 6000000, bodega,
				categoriaproducto, proveedor);
		entityManager.persist(producto3);

		Iterable<Producto> productos = repository.findAll();

		assertThat(productos).hasSize(3).contains(producto1, producto2, producto3);
	}

	@Test
	public void should_find_producto_by_id() {
		// Generacion de Bodega
		Bodega bodega = new Bodega("Bodega A", "Local principal", null);
		entityManager.persist(bodega);
		// Generacion de Proveedor
		Proveedor proveedor = new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", 7374228);
		entityManager.persist(proveedor);
		// Genercion de Categoria
		Categoriaproducto categoriaproducto = new Categoriaproducto("MacBook Pro");
		entityManager.persist(categoriaproducto);
		// Generacion de Producto 1
		Producto producto1 = new Producto(100, "apple", 5, "Apple MacBook Pro 2019", "Ejemplo URL For", 16000000,
				bodega, categoriaproducto, proveedor);
		entityManager.persist(producto1);

		// Generacion de Producto 2
		Producto producto2 = new Producto(100, "apple", 5, "Apple MacBook Pro 2019", "Ejemplo URL For", 16000000,
				bodega, categoriaproducto, proveedor);
		entityManager.persist(producto2);

		// Busqueda de Producto Por ID
		Producto productoEncontrado = repository.findById(producto1.getIdProducto()).get();

		assertThat(productoEncontrado).isEqualTo(producto1);
	}

	@Test
	public void should_update_producto_by_id() {
		// Generacion de Bodega
		Bodega bodega = new Bodega("Bodega A", "Local principal", null);
		entityManager.persist(bodega);
		// Generacion de Proveedor
		Proveedor proveedor = new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", 7374228);
		entityManager.persist(proveedor);
		// Genercion de Categoria
		Categoriaproducto categoriaproducto = new Categoriaproducto("MacBook Pro");
		entityManager.persist(categoriaproducto);
		// Generacion de Producto 1
		Producto producto1 = new Producto(100, "apple", 5, "Apple MacBook Pro 2019", "Ejemplo URL For", 16000000,
				bodega, categoriaproducto, proveedor);
		entityManager.persist(producto1);
		// Generacion de Producto 2
		Producto producto2 = new Producto(100, "Asus", 5, "Asus VivoBook 2019", "Ejemplo URL For", 6000000, bodega,
				categoriaproducto, proveedor);
		entityManager.persist(producto2);

		// Objeto Producto con Parametros Actulizados
		Producto updatedProducto = new Producto(100, "Asus", 8, "Asus Rog", "Ejemplo URL For", 12000000, bodega,
				categoriaproducto, proveedor);

		Producto producto = repository.findById(producto1.getIdProducto()).get();
		producto.setNombreProducto(updatedProducto.getNombreProducto());
		producto.setValorUnitario(updatedProducto.getValorUnitario());
		producto.setCantidadActual(updatedProducto.getCantidadActual());
		repository.save(producto);

		Producto checkProdcuto = repository.findById(producto1.getIdProducto()).get();

		assertThat(checkProdcuto.getIdProducto()).isEqualTo(producto1.getIdProducto());
		assertThat(checkProdcuto.getNombreProducto()).isEqualTo(updatedProducto.getNombreProducto());
		assertThat(checkProdcuto.getValorUnitario()).isEqualTo(updatedProducto.getValorUnitario());
		assertThat(checkProdcuto.getCantidadActual()).isEqualTo(updatedProducto.getCantidadActual());

	}

	@Test
	public void should_delete_producto_by_id() {
		// Generacion de Objetos dependientes de Producto(Bodega-Proveedor-Categoria)
		// Generacion de Bodega
		Bodega bodega = new Bodega("Bodega A", "Local principal", null);
		entityManager.persist(bodega);
		// Generacion de Proveedor
		Proveedor proveedor = new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", 7374228);
		entityManager.persist(proveedor);
		// Genercion de Categoria
		Categoriaproducto categoriaproducto = new Categoriaproducto("MacBook Pro");
		entityManager.persist(categoriaproducto);
		// Generacion de Producto 1
		Producto producto1 = new Producto(100, "apple", 5, "Apple MacBook Pro 2019", "Ejemplo URL For", 16000000,
				bodega, categoriaproducto, proveedor);
		entityManager.persist(producto1);
		// Generacion de Producto 2
		Producto producto2 = new Producto(100, "MSI", 5, "MSI GE73VR 7RF Raider", "Ejemplo URL For", 11000000, bodega,
				categoriaproducto, proveedor);
		entityManager.persist(producto2);
		// Generacion de Producto 3
		Producto producto3 = new Producto(100, "Asus", 5, "Asus VivoBook 2019", "Ejemplo URL For", 6000000, bodega,
				categoriaproducto, proveedor);
		entityManager.persist(producto3);

		repository.deleteById(producto2.getIdProducto());

		Iterable<Producto> productos = repository.findAll();

		assertThat(productos).hasSize(2).contains(producto1, producto3);
	}

	@Test
	public void should_delete_all_productos() {
		// Generacion de Objetos dependientes de Producto(Bodega-Proveedor-Categoria)
		// Generacion de Bodega
		Bodega bodega = new Bodega("Bodega A", "Local principal", null);
		entityManager.persist(bodega);
		// Generacion de Proveedor
		Proveedor proveedor = new Proveedor("Toledo Campestre Casa 27", "Nicolas Duran", 7374228);
		entityManager.persist(proveedor);
		// Genercion de Categoria
		Categoriaproducto categoriaproducto = new Categoriaproducto("MacBook Pro");
		entityManager.persist(categoriaproducto);
		// Generacion de Producto 1
		Producto producto1 = new Producto(100, "apple", 5, "Apple MacBook Pro 2019", "Ejemplo URL For", 16000000,
				bodega, categoriaproducto, proveedor);
		entityManager.persist(producto1);
		// Generacion de Producto 2
		Producto producto2 = new Producto(100, "MSI", 5, "MSI GE73VR 7RF Raider", "Ejemplo URL For", 11000000, bodega,
				categoriaproducto, proveedor);
		entityManager.persist(producto2);
		// Generacion de Producto 3
		Producto producto3 = new Producto(100, "Asus", 5, "Asus VivoBook 2019", "Ejemplo URL For", 6000000, bodega,
				categoriaproducto, proveedor);
		entityManager.persist(producto3);

		repository.deleteAll();

		assertThat(repository.findAll()).isEmpty();
	}


}
