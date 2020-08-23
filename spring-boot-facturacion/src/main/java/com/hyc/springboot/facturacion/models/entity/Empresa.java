package com.hyc.springboot.facturacion.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

@Entity
@Table(name = "empresas")
public class Empresa implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@Column(name="nombre_corto", length = 25)
	private String nombreCorto;
	
	@Column(length = 25)
	private String nit;
	
	private String giro;
	
	private String telefono;
	
	@NotNull
	private Double iva;
	
	private String direccion;
	
	private String logo;
	
	
	
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



	public String getNombreCorto() {
		return nombreCorto;
	}



	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}



	public String getNit() {
		return nit;
	}



	public void setNit(String nit) {
		this.nit = nit;
	}



	public String getGiro() {
		return giro;
	}



	public void setGiro(String giro) {
		this.giro = giro;
	}



	public String getTelefono() {
		return telefono;
	}



	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}



	public Double getIva() {
		return iva;
	}



	public void setIva(Double iva) {
		this.iva = iva;
	}



	public String getDireccion() {
		return direccion;
	}



	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}



	public String getLogo() {
		return logo;
	}



	public void setLogo(String logo) {
		this.logo = logo;
	}



	private static final long serialVersionUID = 1L;

}
