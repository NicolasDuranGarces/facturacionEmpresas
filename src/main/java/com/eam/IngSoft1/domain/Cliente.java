package com.eam.IngSoft1.domain;
import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int dni;

	private String apellido;

	private String direccion;

	private String nombre;

	private int telefono;

	//bi-directional many-to-one association to Despachopedido
	@OneToMany(mappedBy="cliente")
	private List<Despachopedido> despachopedidos;

	public Cliente() {
	}

	public int getDni() {
		return this.dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTelefono() {
		return this.telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public List<Despachopedido> getDespachopedidos() {
		return this.despachopedidos;
	}

	public void setDespachopedidos(List<Despachopedido> despachopedidos) {
		this.despachopedidos = despachopedidos;
	}

	public Despachopedido addDespachopedido(Despachopedido despachopedido) {
		getDespachopedidos().add(despachopedido);
		despachopedido.setCliente(this);

		return despachopedido;
	}

	public Despachopedido removeDespachopedido(Despachopedido despachopedido) {
		getDespachopedidos().remove(despachopedido);
		despachopedido.setCliente(null);

		return despachopedido;
	}

}