package com.eam.IngSoft1.IRepository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eam.IngSoft1.domain.Pedido;

@Repository
public interface IPedidoRepository extends CrudRepository<Pedido,Integer> {
		
	//----------------------------mostrar los pedidos del sistema--------------
		@Query("SELECT p FROM Pedido p WHERE p.activo= false") 
		public ArrayList<Pedido> traerTodosLosPedidos();
	
	//----------------------------mostrar los pedidos sin despachar------------
		@Query("SELECT p FROM Pedido p WHERE p.activo= false and p.despachado= false") 
		public ArrayList<Pedido> traerTodosLosPedidosSinDespachar();
		
	//----------------------------mostrar los pedidos despachados--------------
		@Query("SELECT p FROM Pedido p WHERE p.activo= false and p.despachado= true") 
		public ArrayList<Pedido> traerTodosLosPedidosDespachados();
		
	//--------------------mostrar los pedidos que un cliente ha realizado---------------
		@Query("SELECT p FROM Pedido p JOIN p.cliente c WHERE c.dni= ?1 and p.activo= false") 
		public ArrayList<Pedido> mostrarPedidosRealizadosCliente(int busqueda);
}