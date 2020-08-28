package com.hyc.springboot.facturacion.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hyc.springboot.facturacion.models.entity.Cliente;
import com.hyc.springboot.facturacion.models.entity.Factura;
import com.hyc.springboot.facturacion.models.entity.Producto;
import com.hyc.springboot.facturacion.models.entity.TipoDocumento;



public interface IClienteService {
	
	/*Clientes*/
	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable pageable);
	
	public void save(Cliente cliente);

	public Cliente findOne(Long id);

	public void delete(Long id);
	
	/*Facturas*/
	public List<Factura> findFacturas();
	
	public List<TipoDocumento> findTipoDocumentos();
	
	public List<Producto> findByNombre(String term);
	
	public void saveFactura(Factura factura);
	
	public Producto findProductoById(Long id);
	
	public TipoDocumento findTipoDocumentoById(Long id);
	
	public Factura findFacturaById(Long id);
	
	public void deleteFactura(Long id);
	
	public int siguienteNumeroRecibo(Long tipoDoc);
	
	public Factura fetchFacturaByIdWithClienteWithItemFacturaWithPoducto(Long id);
	
	public Cliente fetchClienteByIdWithFacturas(Long id);
}
