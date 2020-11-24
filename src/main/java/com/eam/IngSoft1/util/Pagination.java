package com.eam.IngSoft1.util;

public class Pagination {
	
	String pagina;
	boolean activa;
	
	
	
	public Pagination() {
		super();
	}

	public Pagination(String pagina, boolean activa) {
		super();
		this.pagina = pagina;
		this.activa = activa;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}
	
	

}
