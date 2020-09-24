package com.hyc.springboot.facturacion.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
//import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

//import org.springframework.format.annotation.DateTimeFormat;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity // Indica que es una clase pojo mapeada a una tabla de la base de datos
@Table(name = "clientes") // nombre de la tabla en la base de datos
public class Cliente implements Serializable {

	@Id // indica que el atributo es la llave de la tabla
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Estrategia con la que genera la llave la base de datos
	private Long id;

	@NotBlank
	private String nombre;

	@Email
	private String email;

	@Pattern(regexp = "")
	private String dui;

	private String nit;

	private String telefono;

	private String direccion;

	@Column(length = 30)
	private String usuario;
	
	@Column(name = "create_at") // en caso que el nombre del campo de la tabla sea diferente al del atributo
	@Temporal(TemporalType.TIMESTAMP) // formato con el que se guarda en la base de datos
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

	// Relacion muchas facturas tiene un cliente
	// mappedBy es el campo al que esta mapeado, automaticamente creara cliente_id
	// como foranea
	// CascadeType.ALL si se elimina el cliente se eliminan todas sus facturas
	@JsonManagedReference
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Factura> facturas;

	@PrePersist // Antes de guardar que asigne la fecha actual
	public void prePersist() {
		createAt = new Date();//createAt == null ? new Date() : createAt;
	}

	public Cliente() {

		facturas = new ArrayList<Factura>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDui() {
		return dui;
	}

	public void setDui(String dui) {
		this.dui = dui;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public void addFactura(Factura factura) {
		this.facturas.add(factura);
	}

	@Override
	public String toString() {
		return nombre;
	}

	private static final long serialVersionUID = 1L;

}
