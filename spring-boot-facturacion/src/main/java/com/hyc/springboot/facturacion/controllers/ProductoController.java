package com.hyc.springboot.facturacion.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

import com.hyc.springboot.facturacion.models.entity.Kardex;
import com.hyc.springboot.facturacion.models.entity.Producto;
import com.hyc.springboot.facturacion.models.entity.Unidad;
import com.hyc.springboot.facturacion.models.service.IClienteService;
import com.hyc.springboot.facturacion.models.service.IConfiguracionService;

@RequestMapping("productos")
@SessionAttributes({"producto","unidades"})
@Controller
public class ProductoController {
	@Autowired
	private IConfiguracionService configuracionService;
	
	@Autowired 
	private IClienteService clienteService;

	@GetMapping({ "", "/" })
	public String listar(Model model) {
		List<Producto> productos = configuracionService.findProductos();
		model.addAttribute("titulo", "PRODUCTOS");
		model.addAttribute("productos", productos);
		return "configuracion/productos";
	}

	@GetMapping("/form")
	public String crear(Model model) {
		Producto producto = new Producto();
		producto.setInventariable(true);
		List<Unidad> unidades = configuracionService.findUnidades();
		model.addAttribute("titulo", "NUEVO PRODUCTO");
		model.addAttribute("producto", producto);
		model.addAttribute("unidades", unidades);
		return "configuracion/productos-form";
	}

	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Producto producto = null;

		if (id > 0) {
			producto = configuracionService.findProductoById(id);
			if (producto == null) {
				flash.addFlashAttribute("error", "¡El Id del producto no existe!");
				return "redirect:/productos";
			}
		} else {
			flash.addFlashAttribute("error", "¡El Id del producto debe ser mayor a 0!");
			return "redirect:/productos";
		}
		List<Unidad> unidades = configuracionService.findUnidades();
		model.put("titulo", "EDITAR PRODUCTO");
		model.put("producto", producto);
		model.put("unidades", unidades);
		return "configuracion/productos-form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Producto producto, BindingResult result, Model model,
			Authentication authentication, RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()){
			model.addAttribute("titulo", "DETALLE DE CLIENTE");
			return "configuracion/productos-form";
		}
		Kardex kardex = new Kardex();
		if(producto.getId() == null && producto.getExistencia() > 0) {
			kardex.setProducto(producto);
			kardex.setDescripcion("Inventario inicial");
			kardex.setTipoMovimientoInv(clienteService.findTipoMovimientoInvById(Long.valueOf(1)));
			kardex.setSaldo(producto.getExistencia());
			kardex.setCostoSaldo(producto.getCosto());
			kardex.setTotalSaldo(producto.getExistencia() * producto.getCosto());
		}
		
		String mensajeFlash = (producto.getId() != null) ? "¡Producto modificado con éxito!" : "¡Producto insertado con éxito!";
		configuracionService.saveProducto(producto);
		if(kardex.getProducto() != null) {
			clienteService.saveKardex(kardex);
		}
		status.setComplete();// Elimina el objeto cliente en sesion
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/productos";
	}
	
	@Secured("ROLE_ADMINISTRADOR")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Producto producto = configuracionService.findProductoById(id);
			if (producto == null) {
				flash.addFlashAttribute("error", "¡El producto no existe!");
				return "redirect:/productos";
			}
			else if(clienteService.cuentaKardexPrd(producto.getId())  > Long.valueOf(0)) {
				flash.addFlashAttribute("error", "¡No se puede eliminar el registro porque posee dependencias!");
				return "redirect:/productos";
			}
			else{
				configuracionService.deleteProducto(id);
				flash.addFlashAttribute("success", "¡Producto eliminado con éxito!");
			}
		}
		return "redirect:/clientes";
	}
}
