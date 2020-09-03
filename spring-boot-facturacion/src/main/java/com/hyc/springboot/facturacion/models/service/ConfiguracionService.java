package com.hyc.springboot.facturacion.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyc.springboot.facturacion.models.dao.INegocioDao;
import com.hyc.springboot.facturacion.models.entity.Negocio;

@Service
public class ConfiguracionService implements IConfiguracionService{
	
	@Autowired
	INegocioDao negocioDao;

	@Override
	public Negocio findNegocioById(Long id) {
		return negocioDao.findById(id).orElse(null);
	}

	@Override
	public void saveNegocio(Negocio negocio) {
		negocioDao.save(negocio);
	}

}
