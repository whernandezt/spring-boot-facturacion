package com.hyc.springboot.facturacion.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "unidad_id")
	private Unidad unidad;

	private String barras;

	private Double precio;

	private Double costo;

	private Boolean compuesto;

	private Boolean inventariable;

	private Boolean exento;

	private Double existencia;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;

	@PrePersist // Antes de guardar que asigne la fecha actual
	public void prePersist() {
		createAt = new Date();
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

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Unidad getUnidad() {
		return unidad;
	}

	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}

	public String getBarras() {
		return barras;
	}

	public void setBarras(String barras) {
		this.barras = barras;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public Boolean getCompuesto() {
		return compuesto;
	}

	public void setCompuesto(Boolean compuesto) {
		this.compuesto = compuesto;
	}

	public Boolean getInventariable() {
		return inventariable;
	}

	public void setInventariable(Boolean inventariable) {
		this.inventariable = inventariable;
	}

	public Boolean getExento() {
		return exento;
	}

	public void setExento(Boolean exento) {
		this.exento = exento;
	}

	public Double getExistencia() {
		return existencia == null ? 0 : existencia;
	}

	public void setExistencia(Double existencia) {
		this.existencia = existencia;
	}

	private static final long serialVersionUID = 1L;

}
