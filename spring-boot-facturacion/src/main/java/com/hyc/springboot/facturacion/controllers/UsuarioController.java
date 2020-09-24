package com.hyc.springboot.facturacion.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.hyc.springboot.facturacion.models.entity.Role;
import com.hyc.springboot.facturacion.models.entity.Usuario;
import com.hyc.springboot.facturacion.models.service.IConfiguracionService;

@Secured("ROLE_ADMINISTRADOR")
@Controller
@SessionAttributes({ "usuario", "roles" })
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private IConfiguracionService configuracionService;

	@Autowired
	private BCryptPasswordEncoder passwordEconder;
	
	private final Logger log = LoggerFactory.getLogger(getClass());

	@GetMapping({ "/", "" })
	public String listar(Model model) {
		List<Usuario> usuarios = configuracionService.findUsuarios();
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("titulo", "USUARIOS");
		return "/configuracion/usuarios";
	}

	@GetMapping("/form")
	public String crear(Map<String, Object> model) {
		Usuario usuario = new Usuario();
		List<Role> roles = configuracionService.findRoles();
		model.put("usuario", usuario);
		model.put("roles", roles);
		model.put("titulo", "DETALLE DE USUARIO");
		System.out.println(roles.size());
		return "/configuracion/usuarios-form";
	}

	@PostMapping("/form")
	public String guardar(@Valid Usuario usuario, BindingResult result, Model model, Authentication auth,
			RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "DETALLE DE USUARIO");
			return "/configuracion/usuarios-form";
		}

		String mensajeFlash = (usuario.getId() != null) ? "¡Usuario editado con éxito!"
				: "¡Usuario registrado con éxito!";

		if (usuario.getId() == null) {
			
			int espacio = usuario.getApellido().indexOf(" ");
			
			String user = usuario.getNombre().substring(0, 1)
					+ (espacio == -1 ? usuario.getApellido() : usuario.getApellido().substring(0, espacio));
			
			user = user.toLowerCase().replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u").replace("ñ", "n").replace("ü", "u");
			
			Integer cont = configuracionService.cuentaUsuarios(user);
			log.info(cont.toString());
			user = cont == 0 ? user : user + cont.toString();
			usuario.setUsername(user);
			usuario.setPassword(passwordEconder.encode(user));
		}

		configuracionService.saveUsuario(usuario);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/usuarios";
	}

	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Usuario usuario = null;
		if (id > 0) {
			usuario = configuracionService.findUsuarioById(id);
			if (usuario == null) {
				flash.addFlashAttribute("error", "¡El Id del usuario no existe!");
				return "redirect:/usuarios";
			}
		} else {
			flash.addFlashAttribute("error", "¡El Id del usuario debe ser mayor a 0!");
			return "redirect:/usuarios";
		}
		List<Role> roles = configuracionService.findRoles();
		model.addAttribute("usuario", usuario);
		model.addAttribute("roles", roles);
		model.addAttribute("titulo", "DETALLE DE USUARIO");
		return "/configuracion/usuarios-form";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "i") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Usuario usuario = configuracionService.findUsuarioById(id);
			if (usuario == null) {
				flash.addFlashAttribute("error", "¡El Id del usuario no existe!");
				return "redirect:/usuarios";
			} else {
				configuracionService.deledeUsuario(id);
				flash.addFlashAttribute("succes", "¡Usuario eliminado con éxito!");
				return "redirect:/usuarios";
			}
		}
		return "redirect:/usuarios";
	}

}
