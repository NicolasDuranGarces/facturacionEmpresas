package com.eam.IngSoft1.IRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Producto;

@Repository
public interface IProductoRepository extends CrudRepository<Producto, Integer>{
	
}
