package com.eam.IngSoft1.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
	@NotNull(message = "El DNI es obligatorio")
	//@Size(min= 5, max=11, message="El DNI debe tener entre 5 y 11 caracteres")
	@Min(10000)
	@Column(unique=true, length=11)
	private int dni;
	
	@NotBlank(message = "El nombre es obligatorio")
	@Size(min= 3, max=50, message="El nombre debe tener entre 3 y 50 caracteres")
	@Pattern(regexp="^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message="El nombre solo admite letras mayúsculas y minúsculas")
	private String nombre;
	
	@NotBlank(message = "El apellido es obligatorio")
	@Size(min= 3, max=50, message="El apellido debe tener entre 3 y 50 caracteres")
	@Pattern(regexp="^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$",  message="El apellido solo admite letras mayúsculas y minúsculas")
	private String apellido;
	
	@NotBlank(message = "El nombre de usuario es obligatorio")
	@Size(min= 5, max=20, message="El nombre de usuario debe tener entre 5 y 20 caracteres")
	@Column(unique=true)
	private String nombreUsuario;

	@NotBlank(message = "La contraseña es obligatoria")
	@Size(min= 8, max=200, message="La contraseña debe tener entre 8 y 20 caracteres")
	private String contrasena;

	@NotBlank(message = "La dirección es obligatoria")
	@Size(min= 7, max=255, message="La dirección debe tener entre 7 y 255 caracteres")
	private String direccion;

	@NotBlank(message = "El teléfono es obligatorio")
	@Size(min= 7, max=20, message="El teléfono debe tener entre 7 y 20 caracteres")
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
	
	public Usuario(@NotBlank(message = "El DNI es obligatorio") @Min(10000) int dni,
			@NotBlank(message = "El nombre es obligatorio") @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres") @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message = "El nombre solo admite letras mayúsculas y minúsculas") String nombre,
			@NotBlank(message = "El apellido es obligatorio") @Size(min = 3, max = 50, message = "El apellido debe tener entre 3 y 50 caracteres") @Pattern(regexp = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$", message = "El apellido solo admite letras mayúsculas y minúsculas") String apellido,
			String nombreUsuario,
			@NotBlank(message = "La contraseña es obligatoria") @Size(min = 8, max = 200, message = "La contraseña debe tener entre 8 y 20 caracteres") String contrasena,
			@NotBlank(message = "La dirección es obligatoria") @Size(min = 7, max = 255, message = "La dirección debe tener entre 7 y 255 caracteres") String direccion,
			@NotBlank(message = "El teléfono es obligatorio") @Size(min = 7, max = 20, message = "El teléfono debe tener entre 7 y 20 caracteres") String telefono,
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