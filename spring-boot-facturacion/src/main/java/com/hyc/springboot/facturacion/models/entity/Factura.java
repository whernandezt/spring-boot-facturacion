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
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String descripcion;
	private String observacion;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "create_at")
	private Date createAt;

	// Relacion en la base de datos, muchas facturas tiene un cliente
	// Fetch lazy es una carga perozosa para que no traiga los datos del cliente por
	// default solo por el get
	@JsonBackReference //Para serializar json se omite
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "factura_id") // Generar llave foranea en facturas_items, de esta forma cuando no hay relacion
										// de doble sentido
	private List<ItemFactura> items;

	public Factura() {
		items = new ArrayList<ItemFactura>();
	}

	@PrePersist // Antes de guardar que asigne la fecha actual
	public void prePersist() {
		createAt = new Date();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public String getObservacion() {
		return observacion;
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

	public void addItemFactura(ItemFactura item) {
		items.add(item);
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
