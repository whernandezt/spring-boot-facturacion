package com.hyc.springboot.facturacion.models.service;

import java.util.List;

import com.hyc.springboot.facturacion.models.entity.DocumentoInicial;
import com.hyc.springboot.facturacion.models.entity.Negocio;
import com.hyc.springboot.facturacion.models.entity.Producto;
import com.hyc.springboot.facturacion.models.entity.Role;
import com.hyc.springboot.facturacion.models.entity.Unidad;
import com.hyc.springboot.facturacion.models.entity.Usuario;

public interface IConfiguracionService {
	
	/*Negocio o empresa*/
	
	public Negocio findNegocioById(Long id);
	
	public void saveNegocio(Negocio negocio);
	
	/*Documento Inicial*/
	
	public List<DocumentoInicial> findDocsIniciales();
	
	public DocumentoInicial findDocumentoInicialById(Long id);
	
	public DocumentoInicial findDocumentoInicialActivo(Long id);
	
	public void saveDocumentoInicial(DocumentoInicial documento);
	
	public void deleteDocumentoInicial(Long id);
	
	/*Producto*/
	
	public List<Producto> findProductos();
	
	public Producto findProductoById(Long id);
	
	public void saveProducto(Producto producto);
	
	public void deleteProducto(Long id);
	
	/*Unidad medida*/
	public List<Unidad> findUnidades();
	
	/*Usuarios*/
	public List<Usuario> findUsuarios();
	
	public Usuario findUsuarioById(Long id);
	
	public Integer cuentaUsuarios(String user);
	
	public void saveUsuario(Usuario usuario);
	
	public void deledeUsuario(Long id);
	
	/*Roles*/
	public List<Role> findRoles();
	
}
