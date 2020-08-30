package com.hyc.springboot.facturacion.models.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyc.springboot.facturacion.models.dao.IClienteDao;
import com.hyc.springboot.facturacion.models.dao.IFacturaDao;
import com.hyc.springboot.facturacion.models.dao.IKardexDao;
import com.hyc.springboot.facturacion.models.dao.IProductoDao;
import com.hyc.springboot.facturacion.models.dao.ITipoDocumentoDao;
import com.hyc.springboot.facturacion.models.dao.ITipoMovimientoDao;
import com.hyc.springboot.facturacion.models.entity.Cliente;
import com.hyc.springboot.facturacion.models.entity.Factura;
import com.hyc.springboot.facturacion.models.entity.Kardex;
import com.hyc.springboot.facturacion.models.entity.Producto;
import com.hyc.springboot.facturacion.models.entity.TipoDocumento;
import com.hyc.springboot.facturacion.models.entity.TipoMovimientoInv;


@Service
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IFacturaDao facturaDao;
	
	@Autowired
	private ITipoDocumentoDao tipoDocumentoDao;
	
	@Autowired
	private IKardexDao kardexDao;
	
	@Autowired
	private ITipoMovimientoDao tipoMovimiento;
	
	@Override
	@Transactional(readOnly=true)
	public List<Cliente> findAll() {
		return (List<Cliente>) clienteDao.findAll();
	} 
	
	@Override
	@Transactional(readOnly=true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public Cliente fetchClienteByIdWithFacturas(Long id) {
		
		return clienteDao.fetchByIdWithFacturas(id);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);		
	}

	@Override
	@Transactional(readOnly=true)
	public List<Producto> findByNombre(String term) {
		// TODO Auto-generated method stub
		return productoDao.findByNombreLikeIgnoreCase("%" + term + "%");
	}

	@Override
	@Transactional
	public void saveFactura(Factura factura) {
		facturaDao.save(factura);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Producto findProductoById(Long id) {
		
		return productoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly=true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteFactura(Long id) {
		facturaDao.deleteById(id);		
	}

	@Override
	@Transactional(readOnly=true)
	public Factura fetchFacturaByIdWithClienteWithItemFacturaWithPoducto(Long id) {
		return facturaDao.fetchByIdWithClienteWithItemFacturaWithPoducto(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Factura> findFacturas() {
		return (List<Factura>) facturaDao.findAll();
	}

	@Override
	public List<TipoDocumento> findTipoDocumentos() {
		return (List<TipoDocumento>) tipoDocumentoDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public TipoDocumento findTipoDocumentoById(Long id) {
		return tipoDocumentoDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly=true)
	public int siguienteNumeroRecibo(Long tipoDoc) {
		if (tipoDoc == 1) {
			return facturaDao.siguienteNumeroRecibo(tipoDoc);
		}
		return 0;
	}

	@Override
	@Transactional
	public void saveProducto(Producto producto) {
		productoDao.save(producto);		
	}

	@Override
	@Transactional(readOnly=true)
	public Kardex ultimoKardexPrd(Long id) {
		return kardexDao.ultimoKardexPrd(id);
	}
	
	@Override
	@Transactional
	public void saveKardex(Kardex kardex) {
		kardexDao.save(kardex);
	}

	@Override
	public TipoMovimientoInv findTipoMovimientoInvById(Long id) {
		return tipoMovimiento.findById(id).get();
	}
	
}
