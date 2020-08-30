package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.Kardex;

public interface IKardexDao extends CrudRepository<Kardex, Long> {
	
	@Query("select k from Kardex k where k.id = ( select max(k2.id) from Kardex k2 where k2.producto.id =?1 )")
	public Kardex ultimoKardexPrd(Long productoId);
}
