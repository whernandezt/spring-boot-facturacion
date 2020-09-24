package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.Role;

public interface IRoleDao extends CrudRepository<Role, Long> {

}
