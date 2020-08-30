package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.TipoMovimientoInv;

public interface ITipoMovimientoDao extends CrudRepository<TipoMovimientoInv, Long> {

}
