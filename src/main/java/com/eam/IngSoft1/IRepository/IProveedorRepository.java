package com.eam.IngSoft1.IRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Proveedor;

@Repository
public interface IProveedorRepository  extends CrudRepository<Proveedor, Integer>{

}
