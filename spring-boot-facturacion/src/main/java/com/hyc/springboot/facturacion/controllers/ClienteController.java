package com.hyc.springboot.facturacion.controllers;


import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyc.springboot.facturacion.models.entity.Cliente;
import com.hyc.springboot.facturacion.models.service.IClienteService;

@Controller
@SessionAttributes("cliente") // Mantiene el cliente cuando se pasa a editar y no tener el id un hiddenfield
@RequestMapping("/clientes")
public class ClienteController {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	// @Qualifier("clienteDaoJPA") en caso que mas de una clase implemtente la
	// interfaz
	private IClienteService clienteService;

	
	@Autowired
	private MessageSource messageSource;


	@Secured("ROLE_USER")
	@RequestMapping(value = "/ver/{id}", method = RequestMethod.GET)
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = clienteService.fetchClienteByIdWithFacturas(id);//clienteService.findOne(id);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos.");
			return "redirect:/listar";
		}

		model.put("titulo", "Detalle de Cliente");
		model.put("cliente", cliente);

		return "/clientes/ver";
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String listar(Model model,
			Authentication authentication,
			HttpServletRequest request,
			Locale locale) {

		List<Cliente> clientes = clienteService.findAll();

		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("clientes", clientes);
		return "/clientes/listar";

	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");
		return "/clientes/form";
	}


	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "FORMULARIO DE CLIENTE");
			return "/clientes/form";
		}

		String mensajeFlash = (cliente.getId() != null) ? "¡Cliente editado con éxito!"
				: "¡Cliente registrado con éxito!";

		clienteService.save(cliente);
		status.setComplete();// Elimina el objeto cliente en sesion
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:../clientes";
	}


	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "¡El Id del cliente no existe!");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "¡El Id del cliente debe ser mayor a 0!");
			return "redirect:../";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar Cliente");
		return "/clientes/form";
	}


	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			// Se busca cliente para eliminar la imagen, solo para eliminar no se necesita
			clienteService.delete(id);
			flash.addFlashAttribute("success", "¡Cliente eliminado con éxito!");
		}
		return "redirect:../";
	}
}
