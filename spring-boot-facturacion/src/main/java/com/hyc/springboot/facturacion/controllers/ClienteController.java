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

	
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String listar(Model model) {

		List<Cliente> clientes = clienteService.findAll();

		model.addAttribute("titulo", "CLIENTES");
		model.addAttribute("clientes", clientes);
		return "/clientes/listar";

	}
	
	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "DETALLE DE CLIENTE");
		return "/clientes/form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			Authentication authentication, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "DETALLE DE CLIENTE");
			return "/clientes/form";
		}

		String mensajeFlash = (cliente.getId() != null) ? "¡Cliente editado con éxito!"
				: "¡Cliente registrado con éxito!";
		
		if(cliente.getId() == null){
			cliente.setUsuario(authentication.getName());
		}
		clienteService.save(cliente);
		status.setComplete();// Elimina el objeto cliente en sesion
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/clientes";
	}

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "¡El Id del cliente no existe!");
				return "redirect:/clientes";
			}
		} else {
			flash.addFlashAttribute("error", "¡El Id del cliente debe ser mayor a 0!");
			return "redirect:/clientes";
		}
		model.put("cliente", cliente);
		model.put("titulo", "DETALLE DE CLIENTE");
		return "/clientes/form";
	}


	@Secured("ROLE_ADMINISTRADOR")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "¡El Id del cliente no existe!");
				return "redirect:/clientes";
			}
			else if(!cliente.getFacturas().isEmpty())
			{
				flash.addFlashAttribute("error", "¡No se puede eliminar el registro porque posee dependencias!");
				return "redirect:/clientes";
			}
			else {
			clienteService.delete(id);
			flash.addFlashAttribute("success", "¡Cliente eliminado con éxito!");
			}
		}
		return "redirect:/clientes";
	}
}
