package com.eam.IngSoft1.IRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Producto;

@Repository
public interface IProductoRepository extends CrudRepository<Producto, Integer> {

	// ----------Consulta para traer los productos a mostrar -----------

	@Query("SELECT p FROM Producto p " + " WHERE p.cantidadActual > p.minimoInventario")
	public Iterable<Producto> cargarProductosActivos();

	// --------------------Consulta al abrir la publicacion -----------
	@Query("SELECT p " + "FROM Producto p " + "WHERE p.cantidadActual > p.minimoInventario and p.idProducto= ?1 ")
	public Producto mostrarProductoSeleccionado(Integer id);

	// -------------------------- buscar por título -------------------
	@Query("SELECT p FROM Producto p " + "WHERE p.cantidadActual > p.minimoInventario AND p.nombreProducto= ?1 ")
	public Iterable<Producto> mostrarProductoBusquedaTitulo(String busqueda);

	// ------------------------- filtrar por categoría ---------
	@Query("SELECT p FROM Producto p "
			+ "JOIN p.categoriaproducto c WHERE p.cantidadActual > p.minimoInventario AND c.nombreCategoria= ?1")
	public Iterable<Producto> mostrarProductoFiltroCategoria(String busqueda);
}
