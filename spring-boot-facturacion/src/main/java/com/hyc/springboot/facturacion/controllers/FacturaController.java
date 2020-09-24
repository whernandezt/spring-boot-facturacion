package com.hyc.springboot.facturacion.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hyc.springboot.facturacion.models.entity.Cliente;
import com.hyc.springboot.facturacion.models.entity.DocumentoInicial;
import com.hyc.springboot.facturacion.models.entity.Factura;
import com.hyc.springboot.facturacion.models.entity.ItemFactura;
import com.hyc.springboot.facturacion.models.entity.Kardex;
import com.hyc.springboot.facturacion.models.entity.Negocio;
import com.hyc.springboot.facturacion.models.entity.Producto;
import com.hyc.springboot.facturacion.models.entity.TipoDocumento;
import com.hyc.springboot.facturacion.models.service.IClienteService;
import com.hyc.springboot.facturacion.models.service.IConfiguracionService;

//Al ponerlo arriba de la clase lo aplica a todos los metodos
@Controller
@RequestMapping("/facturas")
@SessionAttributes({ "factura", "clientes", "tipoDocumentos"})
public class FacturaController {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IConfiguracionService configuracionService;

	// private final Logger log = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String listar(Model model) {

		List<Factura> facturas = clienteService.findFacturas();

		model.addAttribute("titulo", "VENTAS");
		model.addAttribute("facturas", facturas);
		return "facturas/listar";

	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model, RedirectAttributes flash) {

		Factura factura = clienteService.fetchFacturaByIdWithClienteWithItemFacturaWithPoducto(id);// clienteService.findFacturaById(id);

		if (factura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la base de datos");
			return "redirect:/listar";
		}

		model.addAttribute("titulo", "DETALLE DE VENTA");
		model.addAttribute("factura", factura);

		return "facturas/ver";
	}

	@GetMapping("/form")
	public String crear(Map<String, Object> model, RedirectAttributes flash) {

		List<Cliente> clientes = clienteService.findAll();
		List<TipoDocumento> tipoDocumentos = clienteService.findTipoDocumentos();

		Factura factura = new Factura();

		model.put("factura", factura);
		model.put("clientes", clientes);
		model.put("tipoDocumentos", tipoDocumentos);
		model.put("titulo", "REGISTRO DE VENTA");
		return "facturas/form";
	}

	@PostMapping("/form")
	public String guardar(@Valid Factura factura, BindingResult result, Map<String, Object> model,
			Authentication authentication, @RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Double[] cantidad, RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.put("titulo", "REGISTRO DE VENTA");
			return "facturas/form";
		}

		if (factura.getCliente() == null) {
			model.put("titulo", "REGISTRO DE VENTA");
			model.put("error", "Error: Seleccione un cliente.");
			return "facturas/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.put("titulo", "REGISTRO DE VENTA");
			model.put("error", "Error: La factura debe tener al menos un detalle.");
			return "facturas/form";
		}

		Negocio negocio = configuracionService.findNegocioById(Long.valueOf(1));
		Long tipoDocId = factura.getTipoDocumento().getId();
		
		int numero = 0;
		if (tipoDocId > 1) {
			DocumentoInicial documentoInicial = configuracionService.findDocumentoInicialActivo(tipoDocId);
			if (documentoInicial == null) {
				model.put("titulo", "REGISTRO DE VENTA");
				model.put("error", "Error: No existe un documento inical activo para "
						.concat(factura.getTipoDocumento().toString()));

				return "facturas/form";
			}
			factura.setSerie(documentoInicial.getSerie());
			numero = clienteService.siguienteNumeroRecibo(tipoDocId, documentoInicial.getSerie());

			numero = (numero == 1 ? documentoInicial.getDesde() : numero);
		} else {
			factura.setSerie("");
			numero = clienteService.siguienteNumeroRecibo(tipoDocId, "");
		}
		factura.setNumero(numero);
		factura.setUsuario(authentication.getName());

		List<Producto> listaPrd = new ArrayList<Producto>();
		List<Kardex> listaKardex = new ArrayList<Kardex>();

		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			Double existencia = producto.getExistencia();
			if (existencia < cantidad[i] && producto.getInventariable()) {
				model.put("titulo", "REGISTRO DE VENTA");
				model.put("error", "Error: El producto ".concat(producto.getNombre()).concat(
						" no tiene existencia suficiente (".concat(producto.getExistencia().toString()).concat(")")));

				return "facturas/form";
			}
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			linea.setPrecio(producto.getPrecio());
			if (tipoDocId != Long.valueOf(1) && !producto.getExento()) {
				linea.setIva(producto.getPrecio() - (producto.getPrecio() / ((negocio.getIva() / 100.00) + 1)));
			} else {
				linea.setIva(0.00);
			}
			factura.addItemFactura(linea);
			if (producto.getInventariable()) {

				Kardex kardex = new Kardex();

				kardex.setProducto(producto);
				kardex.setTipoMovimientoInv(clienteService.findTipoMovimientoInvById(Long.valueOf(3)));
				kardex.setCantidad(cantidad[i]);
				kardex.setCosto(producto.getCosto());
				kardex.setSaldo(producto.getExistencia() - cantidad[i]);
				kardex.setCostoSaldo(producto.getCosto());
				kardex.setTotalSaldo((existencia - cantidad[i]) * producto.getCosto());
				listaKardex.add(kardex);
				producto.setExistencia(existencia - cantidad[i]);
			}
			listaPrd.add(producto);
		}
		
		clienteService.saveFactura(factura);

		for (Producto p : listaPrd) {
			clienteService.saveProducto(p);
		}

		for (Kardex k : listaKardex) {
			k.setMovId(factura.getId());
			clienteService.saveKardex(k);
		}

		status.setComplete();
		flash.addFlashAttribute("success", "Venta creada con éxito!");

		return "redirect:../ver/" + factura.getId().toString();
	}

	/*
	 * @Secured("ROLE_ADMIN")
	 * 
	 * @GetMapping("/eliminar/{id}") public String eliminar(@PathVariable Long id,
	 * RedirectAttributes flash) {
	 * 
	 * Factura factura = clienteService.findFacturaById(id); if(factura != null){
	 * clienteService.deleteFactura(id); flash.addFlashAttribute("success",
	 * "¡Venta eliminada con éxito!"); return "redirect:/ver/"+
	 * factura.getCliente().getId().toString(); } flash.addFlashAttribute("error",
	 * "¡Venta no existe en la base de dados!"); return "redirect:/listar"; }
	 */
	// @ResponseBody indica que es un jason
	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable String term) {
		// return clienteService.findByNombre(term);
		return clienteService.findByNombre(term);
	}

	@Secured("ROLE_ADMINISTRADOR")
	@GetMapping("/anular/{id}")
	public String anular(@PathVariable Long id, RedirectAttributes flash) {

		Factura factura = clienteService.findFacturaById(id);
		if (factura != null) {
			factura.setAnulada(true);
			clienteService.saveFactura(factura);
			Producto producto;
			Double costo, ultimoCostoTotal;
			for (ItemFactura i : factura.getItems()) {
				if (i.getProducto().getInventariable()) {
					Kardex kardex = new Kardex();

					producto = i.getProducto();
					costo = clienteService.findCostoKardexMovimiento(factura.getId(), producto.getId(),
							Long.valueOf(3));
					ultimoCostoTotal = clienteService.ultimoTotalSaldoKardexPrd(producto.getId());

					kardex.setProducto(producto);
					kardex.setDescripcion("Anulación de " + factura.getTipoDocumento().toString() + " No. "
							+ factura.getNumero().toString());
					kardex.setTipoMovimientoInv(clienteService.findTipoMovimientoInvById(Long.valueOf(4)));
					kardex.setCantidad(i.getCantidad());
					kardex.setCosto(costo);
					kardex.setSaldo(producto.getExistencia() + i.getCantidad());
					kardex.setCostoSaldo((ultimoCostoTotal + (costo * i.getCantidad()))
							/ (producto.getExistencia() + i.getCantidad()));
					kardex.setTotalSaldo(ultimoCostoTotal + (costo * i.getCantidad()));
					clienteService.saveKardex(kardex);

					producto.setExistencia(producto.getExistencia() + i.getCantidad());
					producto.setCosto((ultimoCostoTotal + (costo * i.getCantidad()))
							/ (producto.getExistencia() + i.getCantidad()));
					configuracionService.saveProducto(producto);
				}
			}
			flash.addFlashAttribute("success", "¡Documento anulado con éxito!");
			return "redirect:../ver/" + factura.getId().toString();
		}
		flash.addFlashAttribute("error", "¡Venta no existe en la base de dados!");
		return "redirect:../";
	}
}
