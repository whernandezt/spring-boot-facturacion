package com.hyc.springboot.facturacion.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "parametros")
public class Parametro implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50)
	private String nombre;
	
	@Column(length = 1)
	private String tipo;
	
	@Column(length = 1000)
	private String lista;
	
	@Column(length = 150)
	private String valor;
	
	
	@Transient
	public List<Object> resultSelect;
	
	public Parametro() {}

	public Parametro(Long id, String nombre, String tipo, String lista, String valor, Integer orden) {
		this.id = id;
		this.nombre = nombre;
		this.tipo = tipo;
		this.lista = lista;
		this.valor = valor;
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



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getLista() {
		return lista;
	}



	public void setLista(String lista) {
		this.lista = lista;
	}


	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}



	public List<Object> getResultSelect() {
		return resultSelect;
	}

	public void setResultSelect(List<Object> resultSelect) {
		this.resultSelect = resultSelect;
	}



	private static final long serialVersionUID = 1L;

}
