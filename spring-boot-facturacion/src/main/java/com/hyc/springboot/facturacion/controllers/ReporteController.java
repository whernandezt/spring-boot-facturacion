package com.hyc.springboot.facturacion.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyc.springboot.facturacion.models.entity.Negocio;
import com.hyc.springboot.facturacion.models.entity.Parametro;
import com.hyc.springboot.facturacion.models.entity.Reporte;
import com.hyc.springboot.facturacion.models.service.IConfiguracionService;
import com.hyc.springboot.facturacion.models.service.IReportService;
import com.hyc.springboot.facturacion.models.service.IUploadFileService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping("/reportes")
public class ReporteController {

	@Autowired
	private IReportService reportService;
	
	@Autowired 
	private IConfiguracionService configuracionService;
	
	@Autowired
	private IUploadFileService uploadFileService;

	//private final Logger log = LoggerFactory.getLogger(getClass());

	@Secured("ROLE_USUARIO")
	@GetMapping("/generar")
	public String listar(Model model, Authentication auth) {
		List<Reporte> reportes = reportService.findByUsuario(auth.getName());
		model.addAttribute("reportes", reportes);
		model.addAttribute("titulo", "GENERAR REPORTES");
		return "/reportes/generar";
	}

	@Secured("ROLE_USUARIO")
	@GetMapping(value = "/generar/cargar-parametros/{id}", produces = { "application/json" })
	public @ResponseBody List<Parametro> cargarParametros(@PathVariable Long id) {

		//Map<String, Object> retorno = new HashedMap<String, Object>();
		
		List<Parametro> parametros = reportService.findById(id).getParametros();
		
		for (Parametro p : parametros) {
			String lista = p.getLista();
			if (!lista.isEmpty() && !lista.isBlank()) {
				//List<Object> select = reportService.retornaSelectParametro(lista);
				p.setResultSelect(reportService.retornaSelectParametro(lista));
				p.setLista(null);
			}
		}
		
		return parametros;
	}
	
	@Secured("ROLE_USUARIO")
	@GetMapping("/generar/export/{id}") ///{format}
	public String export(@PathVariable(value = "id") Long id, /*@PathVariable(value = "format") String format,*/ 
			Model model, Authentication auth, HttpServletResponse response,
			@RequestParam(name = "par_valor[]", required = false) String[] parValor)
			throws IOException, JRException, SQLException {
		
		Reporte reporte = reportService.findById(id);
		
		Negocio negocio = configuracionService.findNegocioById(Long.valueOf(1));
		
		JasperPrint jasperPrint = null;
		Map<String, Object> params = new HashedMap<String, Object>();

		if (parValor != null) {
			for (Integer i = 0; i < parValor.length; i++) {
				params.put("_par" + i.toString(), parValor[i]);;
			}
		}
		
		params.put("imagen", uploadFileService.load(negocio.getLogo()).getURL().getPath());
		params.put("negocio", negocio.getNombre());
		params.put("titulo", reporte.getNombre());

		response.setContentType("application/x-download");
		response.setHeader("Content-Disposition", String
				.format("attachment; filename=\"".concat(reporte.getNombre()).concat(".pdf").concat("\"")));
		
		OutputStream out = response.getOutputStream();

		jasperPrint = reportService.exportFile(reporte.getReport(), params);

		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		/*switch (format) {
		case "xml":
			JasperExportManager.exportReportToXmlStream(jasperPrint, out);
			break;
		default:
			JasperExportManager.exportReportToPdfStream(jasperPrint, out);
			break;
		}*/
		List<Reporte> reportes = reportService.findByUsuario(auth.getName());
		model.addAttribute("reportes", reportes);
		model.addAttribute("titulo", "GENERAR REPORTES");
		return "/reportes/generar";

	}
}
