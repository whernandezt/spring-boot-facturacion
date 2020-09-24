package com.hyc.springboot.facturacion.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyc.springboot.facturacion.models.dao.IDocumentoInicialDao;
import com.hyc.springboot.facturacion.models.dao.INegocioDao;
import com.hyc.springboot.facturacion.models.dao.IProductoDao;
import com.hyc.springboot.facturacion.models.dao.IRoleDao;
import com.hyc.springboot.facturacion.models.dao.IUnidadDao;
import com.hyc.springboot.facturacion.models.dao.IUsuarioDao;
import com.hyc.springboot.facturacion.models.entity.DocumentoInicial;
import com.hyc.springboot.facturacion.models.entity.Negocio;
import com.hyc.springboot.facturacion.models.entity.Producto;
import com.hyc.springboot.facturacion.models.entity.Role;
import com.hyc.springboot.facturacion.models.entity.Unidad;
import com.hyc.springboot.facturacion.models.entity.Usuario;

@Service
public class ConfiguracionServiceImpl implements IConfiguracionService {

	@Autowired
	private INegocioDao negocioDao;

	@Autowired
	private IDocumentoInicialDao documentoInicialDao;

	@Autowired
	private IProductoDao productoDao;

	@Autowired
	private IUnidadDao unidadDao;
	
	@Autowired 
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IRoleDao roleDao;

	@Override
	@Transactional(readOnly = true)
	public Negocio findNegocioById(Long id) {
		return negocioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void saveNegocio(Negocio negocio) {
		negocioDao.save(negocio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DocumentoInicial> findDocsIniciales() {
		return (List<DocumentoInicial>) documentoInicialDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public DocumentoInicial findDocumentoInicialById(Long id) {
		return documentoInicialDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public DocumentoInicial findDocumentoInicialActivo(Long id) {
		return documentoInicialDao.findActivo(id);
	}

	@Override
	@Transactional
	@Modifying
	public void saveDocumentoInicial(DocumentoInicial documento) {
		if(documento.getId() == null){
			documentoInicialDao.setDocsInativos(documento.getTipoDocumento().getId());
		}
		documentoInicialDao.save(documento);
	}

	@Override
	@Transactional
	public void deleteDocumentoInicial(Long id) {
		documentoInicialDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findProductos() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional
	public void saveProducto(Producto producto) {
		productoDao.save(producto);
	}

	@Override
	@Transactional
	public void deleteProducto(Long id) {
		productoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Unidad> findUnidades() {
		return (List<Unidad>) unidadDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findUsuarios() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findUsuarioById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void saveUsuario(Usuario usuario) {
		usuarioDao.save(usuario);		
	}

	@Override
	@Transactional
	public void deledeUsuario(Long id) {
		usuarioDao.deleteById(id);
	}

	@Override
	public List<Role> findRoles() {
		return (List<Role>) roleDao.findAll();
	}

	@Override
	@Transactional
	public Integer cuentaUsuarios(String user) {
		return usuarioDao.cuentaUsuarios(user);
	}

}
