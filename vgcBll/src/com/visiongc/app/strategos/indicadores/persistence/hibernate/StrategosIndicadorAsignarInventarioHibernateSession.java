package com.visiongc.app.strategos.indicadores.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.IndicadorAsignarInventario;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionInsumo;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.indicadores.persistence.StrategosIndicadorAsignarInventarioPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesInsumoPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesPersistenceSession;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.util.ListaMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

public class StrategosIndicadorAsignarInventarioHibernateSession extends StrategosHibernateSession implements StrategosIndicadorAsignarInventarioPersistenceSession
{
  public StrategosIndicadorAsignarInventarioHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosIndicadorAsignarInventarioHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }


 
	public int deleteIndicadorInventario(IndicadorAsignarInventario indicadorInv)
			throws Throwable {
		// TODO Auto-generated method stub
		int borrados = 0;
	    try
	    {
	      String hqlDelete = "delete IndicadorAsignarInventario i where i.asignarId =:asignarId i.indicadorId = :indicadorId and i.indicadorInsumoId = :indicadorInsumoId";
	      borrados = session.createQuery(hqlDelete).setLong("asignarId", indicadorInv.getAsignarId()).setLong("indicadorId", indicadorInv.getIndicadorId()).setLong("indicadorInsumoId", indicadorInv.getIndicadorInsumoId()).executeUpdate();
	    }
	    catch (Throwable t)
	    {
	      if (ConstraintViolationException.class.isInstance(t))
	        return 10006;
	      throw t;
	    }
	    
	    if (borrados == 1) {
	      return 10000;
	    }
	    return 10001;
	}

	
	public List<IndicadorAsignarInventario> getIndicadorInventario(Long indicadorId) {
		// TODO Auto-generated method stub
		List<IndicadorAsignarInventario> indicadores = new ArrayList();
	    
	    if (indicadorId !=null )
	    {
	      String sql = "from IndicadorAsignarInventario indicadorInventario where indicadorInventario.indicadorId ="+indicadorId;
	      
	      sql = sql + " order by indicadorInventario.asignarId";
	      
	      Query consulta = session.createQuery(sql);
	      
	      indicadores = consulta.list();
	    }
	    
	    return indicadores;
	}
  
	public IndicadorAsignarInventario getIndicadorInventario(Long indicadorId , Long indicadorInsumoId) {
		// TODO Auto-generated method stub
		IndicadorAsignarInventario indicadorInventario = new IndicadorAsignarInventario();
	    
	    if (indicadorId !=null && indicadorInsumoId !=null)
	    {
	      String sql = "from IndicadorAsignarInventario indicadorInventario where indicadorInventario.indicadorId ="+indicadorId+" and indicadorInventario.indicadorInsumoId="+indicadorInsumoId;
	      
	      sql = sql + " order by indicadorInventario.asignarId";
	      
	      Query consulta = session.createQuery(sql);
	      
	      indicadorInventario = (IndicadorAsignarInventario) consulta.uniqueResult();
	    }
	    
	    return indicadorInventario;
	}

	
	public boolean getInsumo(Long indicadorInsumoId) {
		// TODO Auto-generated method stub
		IndicadorAsignarInventario indicadorInventario = new IndicadorAsignarInventario();
		
		 if (indicadorInsumoId !=null )
		    {
		      String sql = "from IndicadorAsignarInventario indicadorInventario where indicadorInventario.indicadorInsumoId ="+indicadorInsumoId;
		      
		      sql = sql + " order by indicadorInventario.asignarId";
		      
		      Query consulta = session.createQuery(sql);
		      
		      indicadorInventario = (IndicadorAsignarInventario) consulta.uniqueResult();
		    }
		 
		 if(indicadorInventario != null){
			 return true;
		 }
		 
		return false;
	}
	
	public IndicadorAsignarInventario getIndicadorInsumo(Long indicadorInsumoId) {
		// TODO Auto-generated method stub
		IndicadorAsignarInventario indicadorInventario = new IndicadorAsignarInventario();
		
		 if (indicadorInsumoId !=null )
		    {
		      String sql = "from IndicadorAsignarInventario indicadorInventario where indicadorInventario.indicadorInsumoId ="+indicadorInsumoId;
		      
		      sql = sql + " order by indicadorInventario.asignarId";
		      
		      Query consulta = session.createQuery(sql);
		      
		      indicadorInventario = (IndicadorAsignarInventario) consulta.uniqueResult();
		    }
		 
		return indicadorInventario;
	}
 
}
