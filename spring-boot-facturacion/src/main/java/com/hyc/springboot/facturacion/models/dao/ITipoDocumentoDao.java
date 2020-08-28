package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.TipoDocumento;

public interface ITipoDocumentoDao extends CrudRepository<TipoDocumento, Long> {

}
