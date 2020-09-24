package com.hyc.springboot.facturacion.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hyc.springboot.facturacion.models.entity.Kardex;

public interface IKardexDao extends CrudRepository<Kardex, Long> {
	
	@Query("select k.totalSaldo from Kardex k where k.id = ( select max(k2.id) from Kardex k2 where k2.producto.id =?1 )")
	public Double ultimoTotalSaldoKardexPrd(Long productoId);
	
	@Query("select count(k) from Kardex k where k.producto.id = ?1 and k.tipoMovimientoInv.id <> 1")
	public Long cuentaKardexPrd(Long productoId);
	
	@Query("select k.costo from Kardex k where k.id = ( select max(k2.id) from Kardex k2 where k2.movId =?1 and k2.producto.id =?2 and k2.tipoMovimientoInv.id =?3 )")
	public Double findCostoKardexMovimiento(Long movId, Long productoId, Long tipoId);
}
