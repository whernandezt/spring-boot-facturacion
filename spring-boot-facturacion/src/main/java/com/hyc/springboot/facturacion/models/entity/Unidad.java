package com.hyc.springboot.facturacion.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="unidades")
public class Unidad implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 100)
	private String nombre;
	
	@Column(name="nombre_corto", length = 25)
	private String nombreCorto;
	
	



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





	private static final long serialVersionUID = 1L;

}
