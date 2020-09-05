package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.DocumentoInicial;

public interface IDocumentoInicialDao extends CrudRepository<DocumentoInicial, Long> {

}
