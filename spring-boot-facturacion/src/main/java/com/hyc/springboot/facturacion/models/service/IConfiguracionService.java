package com.hyc.springboot.facturacion.models.service;

import com.hyc.springboot.facturacion.models.entity.Negocio;

public interface IConfiguracionService {
	
	/*Negocio o empresa*/
	
	public Negocio findNegocioById(Long id);
	
	public void saveNegocio(Negocio negocio);
}
