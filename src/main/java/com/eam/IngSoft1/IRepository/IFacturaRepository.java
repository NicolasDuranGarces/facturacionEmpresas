
package com.eam.IngSoft1.IRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Factura;

@Repository
public interface IFacturaRepository extends CrudRepository<Factura, Integer> {

	// ----------Consulta para saber A cual factura Agregar los productos

		@Query("SELECT fac.idFactura FROM Factura fac JOIN Pedido pe ON fac.pedido.idPedido = pe.idPedido WHERE pe.activo = 0 "
				+ "and pe.cliente.dni = ?1")
		public int codigoFactura(int dniUsuario);

}