package com.eam.IngSoft1.IRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Factura;
import com.eam.IngSoft1.domain.Usuario;

@Repository
public interface IUsuarioRepository extends CrudRepository<Usuario, Integer> {

	
	public Optional<Usuario> findByNombreUsuario(String usuario);
	
	public Usuario findByDni(int dni);
	
	// ------------------------- obtener id por nombre usuario ---------
			@Query("SELECT u FROM Usuario u "
					+ " WHERE u.nombreUsuario= ?1")
			public Usuario mostrarUsuario(String busqueda);
}
