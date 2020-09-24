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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Kardex implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "tipo_movimiento_id")
	private TipoMovimientoInv tipoMovimientoInv;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "producto_id")
	private Producto producto;
	
	@Column(name="mov_id")
	private Long movId;
	
	private Double cantidad;
	
	private Double costo;
	
	private Double saldo;
	
	@Column(name = "costo_saldo")
	private Double costoSaldo;
	
	@Column(name = "total_saldo")
	private Double totalSaldo;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@PrePersist // Antes de guardar que asigne la fecha actual
	public void prePersist() {
		createAt = new Date();
	}
	
	
	
	public Kardex() {
		super();
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public TipoMovimientoInv getTipoMovimientoInv() {
		return tipoMovimientoInv;
	}



	public void setTipoMovimientoInv(TipoMovimientoInv tipoMovimientoInv) {
		this.tipoMovimientoInv = tipoMovimientoInv;
	}



	public Producto getProducto() {
		return producto;
	}



	public void setProducto(Producto producto) {
		this.producto = producto;
	}



	public Long getMovId() {
		return movId;
	}


	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public void setMovId(Long movId) {
		this.movId = movId;
	}



	public Double getCantidad() {
		return cantidad;
	}



	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}



	public Double getCosto() {
		return costo;
	}



	public void setCosto(Double costo) {
		this.costo = costo;
	}



	public Double getSaldo() {
		return saldo;
	}



	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}



	public Double getCostoSaldo() {
		return costoSaldo;
	}



	public void setCostoSaldo(Double costoSaldo) {
		this.costoSaldo = costoSaldo;
	}



	public Double getTotalSaldo() {
		return totalSaldo;
	}



	public void setTotalSaldo(Double totalSaldo) {
		this.totalSaldo = totalSaldo;
	}



	public Date getCreateAt() {
		return createAt;
	}



	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}



	private static final long serialVersionUID = 1L;
}
