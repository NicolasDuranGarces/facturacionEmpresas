package com.eam.IngSoft1.IRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Detallefactura;

@Repository
public interface IDetalleFacturaRepository extends CrudRepository<Detallefactura, Integer>{

}
