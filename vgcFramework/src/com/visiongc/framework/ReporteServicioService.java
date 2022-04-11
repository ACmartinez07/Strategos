package com.visiongc.framework;

import com.visiongc.commons.VgcService;

import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.model.ReporteServicio;
import com.visiongc.framework.model.Usuario;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ReporteServicioService
    extends VgcService
{

	 public abstract List<ReporteServicio> getReporte(Long paramLong1);
	 
     public abstract int deleteReporte(ReporteServicio paramAuditoria, Usuario paramUsuario);
	  
	 public abstract int saveReporte(ReporteServicio paramAuditoria, Usuario paramUsuario);
	 
	 public abstract void deleteReportes();
	 
}