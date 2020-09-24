package com.hyc.springboot.facturacion.models.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyc.springboot.facturacion.models.dao.IUsuarioDao;
import com.hyc.springboot.facturacion.models.entity.Role;
import com.hyc.springboot.facturacion.models.entity.Usuario;

@Service("jpaUserDetailsSerive")
public class JpaUserDetailsService implements UserDetailsService{

	@Autowired
	private IUsuarioDao usuarioDao;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario= usuarioDao.findByUsername(username);
		
		if(usuario== null) {
			log.info("Error login: no existe el usuario '" + username + "'");
			throw new UsernameNotFoundException("Usuario " + username + " no existe en la base de datos.");
		}
		
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(Role role: usuario.getRoles()) {
			log.info("Role: ".concat(role.getNombre()));
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getNombre()));
		}
		
		if(authorities.isEmpty()) {
			log.info("Error login: usuario '" + username + "' no tiene roles asignados.");
			throw new UsernameNotFoundException("Error login: usuario '" + username + "' no tiene roles asignados.");
		}
		
		return new User(username, usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

}
