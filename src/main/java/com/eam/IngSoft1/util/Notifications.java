package com.eam.IngSoft1.util;

import javax.persistence.Entity;

import com.eam.IngSoft1.domain.Producto;

import lombok.Data;

public class Notifications {
	
	Producto producto;
	String mensaje;
	
	public Notifications(Producto producto, String mensaje) {
		super();
		this.producto = producto;
		this.mensaje = mensaje;
	}
	public Notifications() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
