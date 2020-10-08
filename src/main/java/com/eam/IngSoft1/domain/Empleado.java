package com.eam.IngSoft1.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import lombok.Data;


/**
 * The persistent class for the empleado database table.
 * 
 */
@Entity
@Data
@NamedQuery(name="Empleado.findAll", query="SELECT e FROM Empleado e")
public class Empleado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int DNI_empleado;

	private String apellido;

	private String direccion;

	private String nombre;

	private int telefono;

	public Empleado() {
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="authorities_empleados",
	joinColumns=@JoinColumn(name="empleado_dni"),
	inverseJoinColumns=@JoinColumn(name="authority_id"))
	private Set<Authority> authority;

	
	

}