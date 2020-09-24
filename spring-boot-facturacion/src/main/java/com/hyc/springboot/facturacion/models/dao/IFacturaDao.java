package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.Factura;


public interface IFacturaDao extends CrudRepository<Factura, Long> {

	@Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
	public Factura fetchByIdWithClienteWithItemFacturaWithPoducto(Long id);
	
	@Query("select coalesce(max(f.numero),0) + 1 from Factura f where f.tipoDocumento.id=?1 and f.serie=?2")
	public Integer siguienteNumeroRecibo(Long tipoDoc, String serie);
	
	@Query("select coalesce(count(f.id),0)  from Factura f where f.tipoDocumento.id=?1 and f.numero between ?2 and ?3")
	public Integer cuentaFacturas(Long tipoDoc, Integer desde, Integer hasta);
}
