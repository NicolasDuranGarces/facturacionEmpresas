package com.eam.IngSoft1.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;


/**
 * The persistent class for the empleado database table.
 * 
 */
@Entity
@Data
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotBlank(message = "{dni-mandatory}")
	@Size(min= 5, max=20, message="{dni-size}")
	@Column(unique=true)
	private int dni;
	
	@NotBlank(message = "{name-mandatory}")
	@Size(min= 3, max=50, message="{name-size}")
	@Pattern(regexp="^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message="{name-valid}")
	private String nombre;
	
	@NotBlank(message = "{lastname-mandatory}")
	@Size(min= 3, max=50, message="{lastname-size}")
	@Pattern(regexp="^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$",  message="{lastname-valid}")
	private String apellido;
	
	@Size(min= 5, max=20, message="{username-size}")
	@Column(unique=true)
	private String nombreUsuario;

	@NotBlank(message = "{password-mandatory}")
	@Size(min= 8, max=200, message="{password-size}")
	private String contrasena;

	@NotBlank(message = "{address-mandatory}")
	@Size(min= 7, max=255, message="{address-size}")
	private String direccion;

	@NotBlank(message = "{phone-mandatory}")
	@Size(min= 7, max=20, message="{phone-size}")
	//@Pattern(regexp="^[0-9]$", message="Por favor solo ingresar numeros")
	private String telefono;

	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="authorities_usuarios",
	joinColumns=@JoinColumn(name="usuario_dni"),
	inverseJoinColumns=@JoinColumn(name="authority_id"))
	private Set<Authority> authority;
	
	
	
	//bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos;

	public Usuario() {
	}
	
	public Usuario(@NotBlank(message = "{dni-mandatory}") @Size(min = 5, max = 20, message = "{dni-size}") int dni,
			@NotBlank(message = "{name-mandatory}") @Size(min = 3, max = 50, message = "{name-size}") @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message = "{name-valid}") String nombre,
			@NotBlank(message = "{lastname-mandatory}") @Size(min = 3, max = 50, message = "{lastname-size}") @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message = "{lastname-valid}") String apellido,
			String nombreUsuario,
			@NotBlank(message = "{password-mandatory}") @Size(min = 8, max = 200, message = "{password-size}") String contrasena,
			@NotBlank(message = "{address-mandatory}") @Size(min = 7, max = 255, message = "{address-size}") String direccion,
			@NotBlank(message = "{phone-mandatory}") @Size(min = 7, max = 20, message = "{phone-size}") String telefono,
			Set<Authority> authority, List<Pedido> pedidos) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.direccion = direccion;
		this.telefono = telefono;
		this.authority = authority;
		this.pedidos = pedidos;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getDni() {
		return this.dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getUsuario() {
		return this.nombreUsuario;
	}

	public void setUsuario(String usuario) {
		this.nombreUsuario = usuario;
	}
	
	public List<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	
	public Pedido addPedido(Pedido pedido) {
		getPedidos().add(pedido);
		pedido.setCliente(this);

		return pedido;
	}

	public Pedido removePedido(Pedido pedido) {
		getPedidos().remove(pedido);
		pedido.setCliente(null);

		return pedido;
	}


}