package com.visiongc.framework.persistence;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.model.ReporteServicio;


public interface ReporteServicioPersistenceSession 
extends VgcPersistenceSession{
	
	public abstract List<ReporteServicio> getReporte(Long paramLong1);
	
	public abstract void deleteReportes();
}
