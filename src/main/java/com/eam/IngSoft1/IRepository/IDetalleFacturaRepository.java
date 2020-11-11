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
}
