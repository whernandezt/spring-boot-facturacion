package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.Negocio;

public interface INegocioDao extends CrudRepository<Negocio, Long> {

}
