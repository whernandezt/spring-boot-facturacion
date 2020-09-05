package com.hyc.springboot.facturacion.models.service;

import java.util.List;

import com.hyc.springboot.facturacion.models.entity.DocumentoInicial;
import com.hyc.springboot.facturacion.models.entity.Negocio;

public interface IConfiguracionService {
	
	/*Negocio o empresa*/
	
	public Negocio findNegocioById(Long id);
	
	public void saveNegocio(Negocio negocio);
	
	public List<DocumentoInicial> findDocsIniciales();
	
	public void saveDocumentoInicial(DocumentoInicial documento);
	
	public void deleteDocumentoInicial(Long id);
}
