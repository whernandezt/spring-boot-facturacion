package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.hyc.springboot.facturacion.models.entity.Usuario;


public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	public Usuario findByUsername(String username);
	
	@Query("select coalesce(count(u.id),0) from Usuario u where u.username LIKE ?1% ")
	public Integer cuentaUsuarios(String user);
}
