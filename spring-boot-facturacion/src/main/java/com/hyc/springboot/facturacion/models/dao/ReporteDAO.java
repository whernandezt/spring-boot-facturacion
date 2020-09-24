package com.hyc.springboot.facturacion.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.Reporte;

public interface ReporteDAO extends CrudRepository<Reporte, Long> {
	//join fetch r.roles r1 join Usuario u on u.username=?1 join fetch u.roles r2 where r1.id = r2.id
	@Query("select r from Reporte r")
	public List<Reporte> findByUsuario(String usuario);
	
	@Query(value = "select * from exec(?1) as t(codigo int , nombre varchar)", nativeQuery = true)
	public List<Object> retornaSelectParametro(String select);

}
