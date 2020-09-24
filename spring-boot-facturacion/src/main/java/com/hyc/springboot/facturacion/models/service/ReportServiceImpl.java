package com.hyc.springboot.facturacion.models.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hyc.springboot.facturacion.models.dao.ReporteDAO;
import com.hyc.springboot.facturacion.models.entity.Reporte;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class ReportServiceImpl implements IReportService {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate dbConnection;

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private ReporteDAO reporteDao;
	
	@Override
	public JasperPrint exportFile(String report, Map<String, Object> parameters) throws SQLException, JRException, IOException {
		  Connection conn = dbConnection.getDataSource().getConnection();

		  String path = resourceLoader.getResource("classpath:reports/".concat(report).concat(".jrxml")).getURI().getPath();

		  JasperReport jasperReport = JasperCompileManager.compileReport(path);


		  JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, conn);

		  return print;
		 }

	@Override
	public List<Reporte> findByUsuario(String usuario) {
		return reporteDao.findByUsuario(usuario);
	}

	@Override
	public Reporte findById(Long id) {
		return reporteDao.findById(id).orElse(null);
	}

	@Override
	public List<Object> retornaSelectParametro(String select) {
		return reporteDao.retornaSelectParametro(select);
	}
	
	

}
