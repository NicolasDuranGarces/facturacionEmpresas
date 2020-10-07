package com.eam.IngSoft1.IRepository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Bodega;


@Repository
public interface IBodegaRepository extends CrudRepository<Bodega, Integer>{
 
}
