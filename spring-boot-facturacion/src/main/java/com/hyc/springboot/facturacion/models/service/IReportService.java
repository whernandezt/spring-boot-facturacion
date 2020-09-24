package com.hyc.springboot.facturacion.models.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.hyc.springboot.facturacion.models.entity.Reporte;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JRException;

public interface IReportService {
	
	public List<Reporte> findByUsuario(String usuario);
	
	public Reporte findById(Long id);
	
	public JasperPrint exportFile(String report, Map<String, Object> jasperReport) throws SQLException, JRException, IOException;
	
	public List<Object> retornaSelectParametro(String select);
}
