package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.Unidad;

public interface IUnidadDao extends CrudRepository<Unidad, Long> {

}
