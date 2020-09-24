package com.hyc.springboot.facturacion.models.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "reports")
public class Reporte implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50)
	private String nombre;
	
	private String report;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "report_id")
	public List<Parametro> parametros;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "reports_roles", joinColumns = @JoinColumn(name = "report_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = {
			@UniqueConstraint(columnNames = { "report_id", "role_id" }) })
	public List<Role> roles;
	
	public Reporte() {}

	public Reporte(Long id, String nombre, String report, List<Parametro> parametros, List<Role> roles) {
		this.id = id;
		this.nombre = nombre;
		this.report = report;
		this.parametros = parametros;
		this.roles = roles;
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



	public String getReport() {
		return report;
	}



	public void setReport(String report) {
		this.report = report;
	}



	public List<Parametro> getParametros() {
		return parametros;
	}



	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}



	public List<Role> getRoles() {
		return roles;
	}



	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}



	private static final long serialVersionUID = 1L;

}
