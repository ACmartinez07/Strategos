package com.visiongc.app.strategos.indicadores.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionInsumo;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
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

public class StrategosMedicionesInsumoHibernateSession extends StrategosHibernateSession implements StrategosMedicionesInsumoPersistenceSession
{
  public StrategosMedicionesInsumoHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosMedicionesInsumoHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }


  public int deleteMedicionInsumo(MedicionInsumo medicion) throws Throwable {
	  int borrados = 0;
	    try
	    {
	      String hqlDelete = "delete MedicionInsumo m where m.indicadorId = :indicadorId and m.formulaId = :formulaId and m.organizacionId = :organizacionId and m.periodoInicial = :periodoInicial and m.periodoFinal = :periodoFinal and m.anoInicial = :anoInicial and m.anoFinal = :anoFinal";
	      borrados = session.createQuery(hqlDelete).setLong("indicadorId", medicion.getIndicadorId()).setLong("formulaId", medicion.getFormulaId()).setLong("organizacionId", medicion.getOrganizacionId()).setInteger("periodoInicial", medicion.getPeriodoInicial()).setInteger("periodoFinal", medicion.getPeriodoFinal()).setInteger("anoInicial", medicion.getAnoInicial()).setInteger("anoFinal", medicion.getAnoFinal()).executeUpdate();
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


  public List<MedicionInsumo> getMedicionesInsumo(Long indicadorId,	Long formulaId, Long organizacionId) {
	  
	  	List<MedicionInsumo> medicionesInsumo = new ArrayList();
	    
	    if (indicadorId !=null && formulaId != null && organizacionId != null)
	    {
	      String sql = "select medicion_insumo from MedicionInsumo medicionInsumo where medicionInsumo.indicadorId ="+indicadorId;
	      sql = sql + "and medicionInsumo.formulaId ="+formulaId;
	      sql = sql + "and medicionInsumo.organizacionId ="+organizacionId;
	      sql = sql + " order by medicionInsumo.indicadorId";
	      
	      Query consulta = session.createQuery(sql);
	      
	      medicionesInsumo = consulta.list();
	    }
	    
	    return medicionesInsumo;
  }
  
 
}
