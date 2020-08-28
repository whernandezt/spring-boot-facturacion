package com.hyc.springboot.facturacion.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyc.springboot.facturacion.models.entity.Cliente;
import com.hyc.springboot.facturacion.models.entity.Factura;
import com.hyc.springboot.facturacion.models.entity.ItemFactura;
import com.hyc.springboot.facturacion.models.entity.Producto;
import com.hyc.springboot.facturacion.models.entity.TipoDocumento;
import com.hyc.springboot.facturacion.models.service.IClienteService;

//Al ponerlo arriba de la clase lo aplica a todos los metodos
@Secured("ROLE_USER")
@Controller
@RequestMapping("/facturas")
@SessionAttributes("factura")
public class FacturaController {
	
	@Autowired
	private IClienteService clienteService;
	
	//private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Secured("ROLE_USER")
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String listar(Model model) {

		List<Factura> facturas = clienteService.findFacturas();

		model.addAttribute("titulo", "VENTAS");
		model.addAttribute("facturas", facturas);
		return "facturas/listar";

	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model, RedirectAttributes flash) {
		
		Factura factura = clienteService.fetchFacturaByIdWithClienteWithItemFacturaWithPoducto(id);//clienteService.findFacturaById(id);
		
		if(factura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la base de datos");
			return "redirect:/listar";
		}
		
		model.addAttribute("titulo", "DETALLE DE VENTA");
		model.addAttribute("factura", factura);
		
		return "facturas/ver";
	}
	
	@GetMapping("/form")
	public String crear(Map<String, Object> model, RedirectAttributes flash) {
		
		List<Cliente> clientes =  clienteService.findAll();
		List<TipoDocumento> tipoDocumentos =  clienteService.findTipoDocumentos();
		
		Factura factura = new Factura();
		
		model.put("factura", factura);
		model.put("clientes", clientes);
		model.put("tipoDocumentos", tipoDocumentos);
		model.put("titulo", "REGISTRO DE VENTA");
		return "facturas/form";
	}
	
	@PostMapping("/form")
	public String guardar(@Valid Factura factura,
			BindingResult result,
			Map<String, Object> model,
			@RequestParam(name="item_id[]", required=false) Long[] itemId,
			@RequestParam(name="cantidad[]", required=false) Double[] cantidad,
			RedirectAttributes flash,
			SessionStatus status) {
		
		if(result.hasErrors()) {
			
			List<Cliente> clientes =  clienteService.findAll();
			List<TipoDocumento> tipoDocumentos =  clienteService.findTipoDocumentos();
			
			model.put("titulo", "REGISTRO DE VENTA");
			model.put("clientes", clientes);
			model.put("tipoDocumentos", tipoDocumentos);
			return "facturas/form";
		}
		
		if(factura.getCliente() == null) {
			List<Cliente> clientes =  clienteService.findAll();
			List<TipoDocumento> tipoDocumentos =  clienteService.findTipoDocumentos();
			
			model.put("titulo", "REGISTRO DE VENTA");
			model.put("error", "Error: Seleccione un cliente.");
			model.put("clientes", clientes);
			model.put("tipoDocumentos", tipoDocumentos);
			return "facturas/form";
		}
		
		if(itemId == null || itemId.length == 0) {
			List<Cliente> clientes =  clienteService.findAll();
			List<TipoDocumento> tipoDocumentos =  clienteService.findTipoDocumentos();
			
			model.put("titulo", "REGISTRO DE VENTA");
			model.put("error", "Error: La factura debe tener al menos un detalle.");
			model.put("clientes", clientes);
			model.put("tipoDocumentos", tipoDocumentos);
			return "facturas/form";
		}
		
		for(int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			
			if( producto.getExistencia() < cantidad[i]) {
				List<Cliente> clientes =  clienteService.findAll();
				List<TipoDocumento> tipoDocumentos =  clienteService.findTipoDocumentos();
				
				model.put("titulo", "REGISTRO DE VENTA");
				model.put("error", "Error: El producto ".concat(producto.getNombre()).concat(" no tiene existencia suficiente (".concat(producto.getExistencia().toString()).concat(")")));
				model.put("clientes", clientes);
				model.put("tipoDocumentos", tipoDocumentos);
				return "facturas/form";
			}
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			linea.setPrecio(producto.getPrecio());
			factura.addItemFactura(linea);
			factura.setNumero(clienteService.siguienteNumeroRecibo(factura.getTipoDocumento().getId()));
		}
		
		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success", "Venta creada con éxito!");
		
		return "redirect:../ver/" + factura.getId().toString();
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		
		Factura factura = clienteService.findFacturaById(id);
		if(factura != null){
			clienteService.deleteFactura(id);
			flash.addFlashAttribute("success", "¡Venta eliminada con éxito!");
			return "redirect:/ver/"+ factura.getCliente().getId().toString();
		}
		flash.addFlashAttribute("error", "¡Venta no existe en la base de dados!");
		return "redirect:/listar";
	}
	
	//@ResponseBody indica que es un jason
	@GetMapping(value="/cargar-productos/{term}", produces= {"application/json"})
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
		//return clienteService.findByNombre(term);
		return clienteService.findByNombre(term);
	}
		
}
