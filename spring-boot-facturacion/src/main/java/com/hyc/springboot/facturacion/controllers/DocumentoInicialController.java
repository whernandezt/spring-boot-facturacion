package com.hyc.springboot.facturacion.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyc.springboot.facturacion.models.entity.DocumentoInicial;
import com.hyc.springboot.facturacion.models.entity.TipoDocumento;
import com.hyc.springboot.facturacion.models.service.IClienteService;
import com.hyc.springboot.facturacion.models.service.IConfiguracionService;


@Controller
@SessionAttributes("documento")
@RequestMapping("/documentos")
public class DocumentoInicialController {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private IConfiguracionService configuracionService;

	@Autowired
	private IClienteService clienteService;

	@GetMapping({ "", "/" })
	public String listar(Model model) {
		List<DocumentoInicial> documentos = configuracionService.findDocsIniciales();
		model.addAttribute("titulo", "DOCUMENTOS INICIALES");
		model.addAttribute("documentos", documentos);
		return "/configuracion/documentos";
	}

	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		DocumentoInicial documento = new DocumentoInicial();
		List<TipoDocumento> tiposDocumento = clienteService.findTipoDocumentos();
		
		documento.setActivo(true);
		model.put("documento", documento);
		model.put("tiposDocumento", tiposDocumento);
		model.put("titulo", "DETALLE DE DOCUMENTO");

		return "/configuracion/documentos-form";
	}

	@PostMapping("/form")
	public String guardar(@Valid DocumentoInicial documento, BindingResult result, Model model,
			Authentication authentication, RedirectAttributes flash, SessionStatus status) {
		//log.info("Post: id ".concat(documento.getId().toString()));
		if (result.hasErrors()) {
			model.addAttribute("titulo", "DETALLE DE DOCUMENTO");
			return "/configuracion/documentos-form";
		}

		if (documento.getDesde() <= 0) {
			model.addAttribute("error", "¡Desde debe ser mayor a 0!");
			model.addAttribute("titulo", "DETALLE DE DOCUMENTO");
			return "/configuracion/documentos-form";
		}

		if (documento.getDesde() > documento.getHasta()) {
			model.addAttribute("error", "¡Desde debe ser menor que hasta!");
			model.addAttribute("titulo", "DETALLE DE DOCUMENTO");
			return "/configuracion/documentos-form";
		}

		String mensajeFlash = (documento.getId() != null) ? "¡Documento editado con éxito!"
				: "¡Documento registrado con éxito!";

		if (documento.getId() == null) {
			//documento.setActivo(true);
			documento.setUsuario(authentication.getName());
		}
		
		
		configuracionService.saveDocumentoInicial(documento);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/documentos";
	}

	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		DocumentoInicial documento = null;

		if (id > 0) {
			documento = configuracionService.findDocumentoInicialById(id);
			if (documento == null) {
				flash.addFlashAttribute("error", "¡El Id del documento no existe!");
				return "redirect:/documentos";
			}
		} else {
			flash.addFlashAttribute("error", "¡El Id del documento debe ser mayor a 0!");
			return "redirect:/documentos";
		}
		
		List<TipoDocumento> tiposDocumento = clienteService.findTipoDocumentos();
		model.put("documento", documento);
		model.put("tiposDocumento", tiposDocumento);
		model.put("titulo", "DETALLE DE DOCUMENTO");
		return "/configuracion/documentos-form";
	}
	
	@Secured("ROLE_ADMINISTRADOR")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			DocumentoInicial documento = configuracionService.findDocumentoInicialById(id);
			
			if (documento == null) {
				flash.addFlashAttribute("error", "¡El Id del documento no existe!");
				return "redirect:/documentos";
			}
			else if(clienteService.cuentaFacturas(documento.getTipoDocumento().getId(), documento.getDesde(), documento.getHasta()) > 0)
			{
				flash.addFlashAttribute("error", "¡No se puede eliminar el registro porque posee dependencias!");
				return "redirect:/documentos";
			}
			else {
				configuracionService.deleteDocumentoInicial(id);
				flash.addFlashAttribute("success", "¡Documento eliminado con éxito!");
			}
		}
		return "redirect:/documentos";
	}

}
