package com.eam.IngSoft1.IRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Detallefactura;
import com.eam.IngSoft1.domain.Producto;

@Repository
public interface IDetalleFacturaRepository extends CrudRepository<Detallefactura, Integer> {
	
	//Metodo Para Listar Productos del Carrito
	
	@Query("SELECT p "
			+ "FROM Detallefactura df "
			+ "JOIN Producto p ON df.producto.idProducto = p.idProducto "
			+ "JOIN Factura factu ON df.factura.idFactura = factu.idFactura "
			+ "WHERE factu.idFactura = ?1 ")
	public Iterable<Producto> listarArticulosCarrito(int dniFacturaActiva);

}
