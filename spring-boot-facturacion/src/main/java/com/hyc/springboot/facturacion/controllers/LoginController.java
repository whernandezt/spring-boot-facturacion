package com.hyc.springboot.facturacion.controllers;

import java.net.MalformedURLException;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyc.springboot.facturacion.models.service.IUploadFileService;

@Controller
public class LoginController {
	
	@Autowired
	IUploadFileService uploadFileService;

	@GetMapping({"/login","/"})
	public String login(@RequestParam(value="error", required= false) String error, 
			@RequestParam(value="logout", required= false) String logout,
			Model model, Principal principal, RedirectAttributes flash) {
		
		if(principal != null) {
			//flash.addFlashAttribute("info", "Ya ha iniciado sesión anteriormente");
			return "redirect:/facturas/";
		}
		
		if(error != null) {
			model.addAttribute("error", "¡Usuario o contraseña incorrecta!");
		}
		
		if(logout != null) {
			model.addAttribute("succes", "¡Ha cerrado sesión con éxito!");
		}
		return "login";
	}
	
	@RequestMapping(value = "/uploads/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
}
