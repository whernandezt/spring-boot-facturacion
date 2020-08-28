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
@Table(name="documentos_inicial")
public class DocumentoInicial implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "tipo_documento_id")
	private TipoDocumento tipoDocumento;
	
	private String serie;
	
	private Integer desde;
	
	private Integer hasta;
	
	private Boolean activo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_at")
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



	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}



	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}



	public String getSerie() {
		return serie;
	}



	public void setSerie(String serie) {
		this.serie = serie;
	}



	public Integer getDesde() {
		return desde;
	}



	public void setDesde(Integer desde) {
		this.desde = desde;
	}



	public Integer getHasta() {
		return hasta;
	}



	public void setHasta(Integer hasta) {
		this.hasta = hasta;
	}



	public Boolean isActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}



	private static final long serialVersionUID = 1L;

}
