package com.eam.IngSoft1.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * The persistent class for the vendedor database table.
 * 
 */
@Entity
@NamedQuery(name="Vendedor.findAll", query="SELECT v FROM Vendedor v")
public class Vendedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int DNI_vendedor;

	private String apellido;

	private String direccion;

	private String nombre;

	private int telefono;

	//bi-directional many-to-one association to Despachopedido
	@OneToMany(mappedBy="vendedor")
	private List<Despachopedido> despachopedidos;

	public Vendedor() {
	}

	public int getDNI_vendedor() {
		return this.DNI_vendedor;
	}

	public void setDNI_vendedor(int DNI_vendedor) {
		this.DNI_vendedor = DNI_vendedor;
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
		despachopedido.setVendedor(this);

		return despachopedido;
	}

	public Despachopedido removeDespachopedido(Despachopedido despachopedido) {
		getDespachopedidos().remove(despachopedido);
		despachopedido.setVendedor(null);

		return despachopedido;
	}

}