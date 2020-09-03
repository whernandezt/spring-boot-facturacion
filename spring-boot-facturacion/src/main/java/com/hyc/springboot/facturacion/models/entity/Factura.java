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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;


import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_documento_id") 
	private TipoDocumento tipoDocumento;
	
	private String serie;
	
	private Integer numero;

	
	private String descripcion;
	
	private Boolean anulada;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_at")
	private Date createAt;

	@JsonBackReference //Para serializar json se omite
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id") // Generar llave foranea en facturas_items, de esta forma cuando no hay relacion
										// de doble sentido
	private List<ItemFactura> items;
	
	@Column(length = 30)
	private String usuario;

	public Factura() {
		items = new ArrayList<ItemFactura>();
	}

	@PrePersist // Antes de guardar que asigne la fecha actual
	public void prePersist() {
		createAt = new Date();
		anulada = false;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	@XmlTransient
	public Cliente getCliente() {
		return cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
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

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Boolean isAnulada() {
		return anulada;
	}

	public void setAnulada(Boolean anulada) {
		this.anulada = anulada;
	}

	public void addItemFactura(ItemFactura item) {
		items.add(item);
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Double getTotal() {
		Double total = 0.0;

		for (int i = 0; i < items.size(); i++) {
			total += items.get(i).calcularImporte();
		}
		
		return total;
	}

	private static final long serialVersionUID = 1L;

}
