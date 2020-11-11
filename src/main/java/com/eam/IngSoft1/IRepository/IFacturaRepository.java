
package com.eam.IngSoft1.IRepository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Detallefactura;
import com.eam.IngSoft1.domain.Factura;
import com.eam.IngSoft1.domain.Pedido;
import com.eam.IngSoft1.domain.Producto;

@Repository
public interface IFacturaRepository extends CrudRepository<Factura, Integer> {

	// ----------Consulta para saber A cual factura Agregar los productos

	/*
	 * @Query("SELECT fac.idFactura FROM Factura fac JOIN fac.pedido pe WHERE pe.activo = 0 "
	 * + "and pe.cliente.dni = ?1") public int codigoFactura(int dniUsuario);
	 */
	@Query("SELECT fac.idFactura FROM Factura fac JOIN Pedido pe ON fac.pedido.idPedido = pe.idPedido WHERE pe.activo = 1 "
			+ "and pe.cliente.dni = ?1")
	public int codigoFactura(int dniUsuario);

	public Factura findByidFactura(int idFactura);

	// ------------------------- filtrar por cliente ---------
	@Query("SELECT f FROM Factura f " + "JOIN f.pedido p JOIN p.cliente c WHERE c.dni= ?1 ")
	public Iterable<Factura> mostrarFacturaFiltroCliente(int busqueda);
	
	
	//---------------------------------------
	@Query("SELECT p FROM Pedido p") 
	public ArrayList<Pedido> mostrarPedidos();
	
	
	//---------------------------------------
	@Query("SELECT f FROM Factura f") 
	public ArrayList<Factura> mostrarFacturas();
	
	
	//---------------------------------------
	@Query("SELECT fac FROM Factura fac JOIN Pedido pe ON fac.pedido.idPedido = pe.idPedido WHERE pe.activo = 1") 
	public ArrayList<Factura> mostrarFacturasActivas();
	
	
		
	
}







