package com.hyc.springboot.facturacion.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyc.springboot.facturacion.models.dao.IDocumentoInicialDao;
import com.hyc.springboot.facturacion.models.dao.INegocioDao;
import com.hyc.springboot.facturacion.models.entity.DocumentoInicial;
import com.hyc.springboot.facturacion.models.entity.Negocio;

@Service
public class ConfiguracionServiceImpl implements IConfiguracionService{
	
	@Autowired
	INegocioDao negocioDao;
	
	@Autowired
	IDocumentoInicialDao documentoInicialDao;

	@Override
	public Negocio findNegocioById(Long id) {
		return negocioDao.findById(id).orElse(null);
	}

	@Override
	public void saveNegocio(Negocio negocio) {
		negocioDao.save(negocio);
	}

	@Override
	public List<DocumentoInicial> findDocsIniciales() {
		return (List<DocumentoInicial>)documentoInicialDao.findAll();
	}

	@Override
	public void saveDocumentoInicial(DocumentoInicial documento) {
		documentoInicialDao.save(documento);		
	}

	@Override
	public void deleteDocumentoInicial(Long id) {
		documentoInicialDao.deleteById(id);		
	}

}
