package com.hyc.springboot.facturacion.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyc.springboot.facturacion.models.entity.Negocio;
import com.hyc.springboot.facturacion.models.service.IConfiguracionService;
import com.hyc.springboot.facturacion.models.service.IUploadFileService;

@Secured("ROLE_ADMINISTRADOR")
@Controller
@RequestMapping("/negocio")
@SessionAttributes("negocio")
public class NegocioController {

	@Autowired
	IConfiguracionService configuracionService;
	
	@Autowired
	IUploadFileService uploadService;
	
	@GetMapping(value = {"/",""})
	public String ver(Model model, RedirectAttributes flash) {
		Negocio negocio = configuracionService.findNegocioById(Long.valueOf(1)); //clienteService.findFacturaById(id);
		
		if(negocio == null) {
			flash.addFlashAttribute("error", "El negocio no existe en la base de datos");
			return "redirect:/facturas";
		}
		
		model.addAttribute("titulo", "DATOS DEL NEGOCIO");
		model.addAttribute("negocio", negocio);
		
		return "configuracion/negocio";
	}
	
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Negocio negocio, BindingResult result, Model model,
			@RequestParam("file") MultipartFile logo, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "DATOS DEL NEGOCIO");
			return "negocio";
		}
		
		if (!logo.isEmpty()) {
			if(negocio.getId() != null && negocio.getId() > 0 && negocio.getLogo() != null && negocio.getLogo().length() > 0) {
				uploadService.delete( negocio.getLogo());
			}
			
			String uniqueFileName = null;
			try {
				uniqueFileName = uploadService.copy(logo);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			negocio.setLogo(uniqueFileName);
		}
		
		String mensajeFlash = (negocio.getId() != null) ? "¡Negocio editado con éxito!"
				: "¡Negocio registrado con éxito!";

		configuracionService.saveNegocio(negocio);
		//status.setComplete();// Elimina el objeto cliente en sesion
		flash.addFlashAttribute("success", mensajeFlash);
		
		return "redirect:../";
	}

}
