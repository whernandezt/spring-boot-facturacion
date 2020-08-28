package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.Factura;


public interface IFacturaDao extends CrudRepository<Factura, Long> {

	@Query("select f from Factura f join fetch f.cliente c join fetch f.items l join fetch l.producto where f.id=?1")
	public Factura fetchByIdWithClienteWithItemFacturaWithPoducto(Long id);
	
	@Query("select coalesce(max(f.numero),0) + 1 from Factura f where f.tipoDocumento.id=?1")
	public Integer siguienteNumeroRecibo(Long tipoDoc);
}
