package com.eam.IngSoft1.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the empleado database table.
 * 
 */
@Entity
@NamedQuery(name="Empleado.findAll", query="SELECT e FROM Empleado e")
public class Empleado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int DNI_empleado;

	private String apellido;

	private String direccion;

	private String nombre;

	private int telefono;



}