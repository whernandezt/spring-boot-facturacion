package com.hyc.springboot.facturacion.models.entity;

import java.io.Serializable;
import java.util.Date;

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
import javax.validation.constraints.NotNull;


@Entity
@Table(name="documentos_inicial")
public class DocumentoInicial implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_documento_id")
	private TipoDocumento tipoDocumento;
	
	@Column(length = 25)
	private String serie;
	
	@NotNull
	private Integer desde;
	
	@NotNull
	private Integer hasta;
	
	private Boolean activo;
	
	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@Column(length = 30)
	private String usuario;
	
	@PrePersist 
	public void prePersist() {
		createAt = new Date();
		activo = true;
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



	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	private static final long serialVersionUID = 1L;

}
