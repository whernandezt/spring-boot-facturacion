package com.hyc.springboot.facturacion.models.dao;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.DocumentoInicial;

public interface IDocumentoInicialDao extends CrudRepository<DocumentoInicial, Long> {
	
	@Modifying
	@Query("update DocumentoInicial d set d.activo = false where d.tipoDocumento.id =?1 and d.activo = true")
	public void setDocsInativos(Long id);
	
	@Query("select d from DocumentoInicial d where d.id= (select max(d2.id) from DocumentoInicial d2 where d2.tipoDocumento.id =?1 and d2.activo = true)")
	public DocumentoInicial findActivo(Long id);
}
