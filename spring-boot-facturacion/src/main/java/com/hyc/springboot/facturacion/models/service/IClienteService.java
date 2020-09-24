package com.hyc.springboot.facturacion.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.hyc.springboot.facturacion.models.entity.Cliente;
import com.hyc.springboot.facturacion.models.entity.Factura;
import com.hyc.springboot.facturacion.models.entity.Kardex;
import com.hyc.springboot.facturacion.models.entity.Producto;
import com.hyc.springboot.facturacion.models.entity.TipoDocumento;
import com.hyc.springboot.facturacion.models.entity.TipoMovimientoInv;



public interface IClienteService {
	
	/*Clientes*/
	public List<Cliente> findAll();

	public Page<Cliente> findAll(Pageable pageable);
	
	public void save(Cliente cliente);

	public Cliente findOne(Long id);

	public void delete(Long id);
	
	/*Productos*/
	public Producto findProductoById(Long id);
	
	public List<Producto> findByNombre(String term);
	
	public void saveProducto(Producto producto);
	
	/*Facturas*/
	public List<Factura> findFacturas();
	
	public List<TipoDocumento> findTipoDocumentos();
	
	public void saveFactura(Factura factura);
	
	public TipoDocumento findTipoDocumentoById(Long id);
	
	public Factura findFacturaById(Long id);
	
	public void deleteFactura(Long id);
	
	public int siguienteNumeroRecibo(Long tipoDoc, String serie);
	
	public Factura fetchFacturaByIdWithClienteWithItemFacturaWithPoducto(Long id);
	
	public Cliente fetchClienteByIdWithFacturas(Long id);
	
	public Integer cuentaFacturas(Long tipodDoc, Integer desde, Integer hasta);
	
	/*Kardex*/
	public Double ultimoTotalSaldoKardexPrd(Long id);
	
	public Double findCostoKardexMovimiento(Long movId, Long productoId, Long tipoId);
	
	public void saveKardex(Kardex kardex);
	
	public TipoMovimientoInv findTipoMovimientoInvById(Long id);
	
	public Long cuentaKardexPrd(Long idPrd);
}
