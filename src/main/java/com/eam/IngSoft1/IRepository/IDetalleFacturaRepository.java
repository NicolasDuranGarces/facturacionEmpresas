package com.eam.IngSoft1.IRepository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Detallefactura;

@Repository
public interface IDetalleFacturaRepository extends CrudRepository<Detallefactura, Integer>{

	@Query("SELECT d FROM Detallefactura d JOIN d.factura f "
			+ "where f.idFactura=?1 ")
	public ArrayList<Detallefactura> mostrarDetalles(int idFactura);
	
	@Query("SELECT d FROM Detallefactura d JOIN d.factura f ON d.factura.idFactura = f.idFactura "
			+ "JOIN d.producto p ON d.producto.idProducto = p.idProducto WHERE f.idFactura= ?1 AND p.idProducto= ?2 ")
	public Detallefactura verificarDetalle(int idFactura, int idProducto);
}
