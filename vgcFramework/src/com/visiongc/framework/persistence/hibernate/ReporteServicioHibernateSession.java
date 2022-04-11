
package com.visiongc.framework.persistence.hibernate;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.visiongc.commons.persistence.hibernate.VgcHibernateSession;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.framework.auditoria.model.Auditoria;
import com.visiongc.framework.auditoria.model.AuditoriaMedicion;
import com.visiongc.framework.auditoria.persistence.AuditoriaMedicionPersistenceSession;
import com.visiongc.framework.auditoria.persistence.AuditoriasPersistenceSession;
import com.visiongc.framework.model.ReporteServicio;
import com.visiongc.framework.model.Sistema;
import com.visiongc.framework.persistence.ReporteServicioPersistenceSession;

public class ReporteServicioHibernateSession extends VgcHibernateSession
implements ReporteServicioPersistenceSession{

	 public ReporteServicioHibernateSession(Session session)
	    {
	        super(session);
	    }

	    public ReporteServicioHibernateSession(VgcHibernateSession parentSession)
	    {
	        super(parentSession);
	    }

	   
		
	
		public List<ReporteServicio> getReporte(Long responsableId) {
			
			
			String sql = "select reporte from ReporteServicio reporte ";
	    	
			sql = (new StringBuilder(String.valueOf(sql))).append("where reporte.responsableId =").append(responsableId).toString();
			   			      
			Query consulta = session.createQuery(sql);
			
			      
			return consulta.list();
		}

		
		public void deleteReportes() {

			String sql = "delete from ReporteServicio reporte";
			
			Query consulta = session.createQuery(sql);
		}
		
		
}

