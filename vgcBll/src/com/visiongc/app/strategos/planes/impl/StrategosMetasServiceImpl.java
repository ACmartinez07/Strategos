package com.visiongc.app.strategos.planes.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.IndicadorPlanPK;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.MetaPK;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.planes.persistence.StrategosMetasPersistenceSession;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Organizacion;
import com.visiongc.framework.model.Usuario;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StrategosMetasServiceImpl
  extends StrategosServiceImpl implements StrategosMetasService
{
  private StrategosMetasPersistenceSession persistenceSession = null;
  
  public StrategosMetasServiceImpl(StrategosMetasPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public int deleteMeta(Meta meta, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (meta.getMetaId() != null)
      {
        resultado = persistenceSession.delete(meta, usuario);
      }
      
      if (resultado == 10000)
      {
        if (transActiva) {
          persistenceSession.commitTransaction();
          transActiva = false;
        }
        
      }
      else if (transActiva) {
        persistenceSession.rollbackTransaction();
        transActiva = false;
      }
      
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    

    return resultado;
  }
  
  public int saveMeta(Meta meta, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[6];
    Object[] fieldValues = new Object[6];
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      if (meta.getValor() == null) {
        resultado = persistenceSession.delete(meta, usuario);
      } else {
        fieldNames[0] = "metaId.indicadorId";
        fieldValues[0] = meta.getMetaId().getIndicadorId();
        fieldNames[1] = "metaId.serieId";
        fieldValues[1] = meta.getMetaId().getSerieId();
        fieldNames[2] = "metaId.ano";
        fieldValues[2] = meta.getMetaId().getAno();
        fieldNames[3] = "metaId.periodo";
        fieldValues[3] = meta.getMetaId().getPeriodo();
        fieldNames[4] = "metaId.planId";
        fieldValues[4] = meta.getMetaId().getPlanId();
        fieldNames[5] = "metaId.tipo";
        fieldValues[5] = meta.getMetaId().getTipo();
        
        if (!persistenceSession.existsObject(meta, fieldNames, fieldValues)) {
          resultado = persistenceSession.insert(meta, usuario);
        } else {
          resultado = persistenceSession.update(meta, usuario);
        }
      }
      

      if (transActiva) {
        persistenceSession.commitTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    
    return resultado;
  }
  
  public int saveMetas(List metas, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[6];
    Object[] fieldValues = new Object[6];
    Map tiposCargaMeta = new HashMap();
    Map indicadores = new HashMap();
    try
    {
      if (!persistenceSession.isTransactionActive()) {
        persistenceSession.beginTransaction();
        transActiva = true;
      }
      
      for (Iterator iter = metas.iterator(); iter.hasNext();)
      {
        Meta meta = (Meta)iter.next();
        
        fieldNames[0] = "metaId.indicadorId";
        fieldValues[0] = meta.getMetaId().getIndicadorId();
        fieldNames[1] = "metaId.serieId";
        fieldValues[1] = meta.getMetaId().getSerieId();
        fieldNames[2] = "metaId.ano";
        fieldValues[2] = meta.getMetaId().getAno();
        fieldNames[3] = "metaId.periodo";
        fieldValues[3] = meta.getMetaId().getPeriodo();
        fieldNames[4] = "metaId.planId";
        fieldValues[4] = meta.getMetaId().getPlanId();
        fieldNames[5] = "metaId.tipo";
        fieldValues[5] = meta.getMetaId().getTipo();
        
        if ((meta.getMetaId().getTipo().byteValue() == TipoMeta.getTipoMetaParcial().byteValue()) && 
          (!tiposCargaMeta.containsKey("indicadorId" + meta.getMetaId().getIndicadorId().toString() + "planId" + meta.getMetaId().getPlanId().toString()))) {
          IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
          indicadorPlanPk.setIndicadorId(meta.getMetaId().getIndicadorId());
          indicadorPlanPk.setPlanId(meta.getMetaId().getPlanId());
          IndicadorPlan indicadorPlan = (IndicadorPlan)persistenceSession.load(IndicadorPlan.class, indicadorPlanPk);
          if (indicadorPlan != null) {
            indicadorPlan.setTipoMedicion(meta.getTipoCargaMeta());
            resultado = persistenceSession.update(indicadorPlan, usuario);
          } else {
            indicadorPlan = new IndicadorPlan();
            indicadorPlan.setPk(indicadorPlanPk);
            indicadorPlan.setTipoMedicion(meta.getTipoCargaMeta());
            resultado = persistenceSession.insert(indicadorPlan, usuario);
          }
          tiposCargaMeta.put("indicadorId" + meta.getMetaId().getIndicadorId().toString() + "planId" + meta.getMetaId().getPlanId().toString(), "indicadorId" + meta.getMetaId().getIndicadorId().toString() + "planId" + meta.getMetaId().getPlanId().toString());
        }
        
        if (meta.getValor() == null)
        {
          if (persistenceSession.existsObject(meta, fieldNames, fieldValues)) {
            resultado = persistenceSession.delete(meta, usuario);
          }
          if (resultado == 10001) {
            resultado = 10000;
          }
          
        }
        else if (meta.getMetaId().getSerieId().byteValue() == SerieTiempo.getSerieValorInicialId().byteValue())
        {
          resultado = persistenceSession.deleteValorInicial(meta);
          
          if (resultado == 10000) {
            resultado = persistenceSession.insert(meta, usuario);
          }
          
        }
        else if (!persistenceSession.existsObject(meta, fieldNames, fieldValues)) {
          resultado = persistenceSession.insert(meta, usuario);
        } else {
          resultado = persistenceSession.update(meta, usuario);
        }
        
        if (resultado != 10000) {
          break;
        }
        if (!indicadores.containsKey("indicadorId" + meta.getMetaId().getIndicadorId() + "planId" + meta.getMetaId().getPlanId())) {
          Indicador indicador = (Indicador)load(Indicador.class, meta.getMetaId().getIndicadorId());
          indicadores.put("indicadorId" + meta.getMetaId().getIndicadorId() + "planId" + meta.getMetaId().getPlanId(), indicador);
        }
      }
      

      if (resultado == 10000)
      {
        if (transActiva) {
          persistenceSession.commitTransaction();
          transActiva = false;
        }
        
      }
      else if (transActiva) {
        persistenceSession.rollbackTransaction();
        transActiva = false;
      }
      
    }
    catch (Throwable t)
    {
      if (transActiva) {
        persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    

    return resultado;
  }
  
  public List getMetasAnuales(Long indicadorId, Long planId, Integer anoDesde, Integer anoHasta, Boolean acumular)
  {
    return persistenceSession.getMetasAnuales(indicadorId, planId, anoDesde, anoHasta, acumular);
  }
  
  public List getMetasAnualesParciales(Long indicadorId, Long planId, Byte frecuencia, Integer anoDesde, Integer anoHasta, Boolean acumular) {
    return persistenceSession.getMetasAnualesParciales(indicadorId, planId, frecuencia, anoDesde, anoHasta, acumular);
  }
  
  public Meta getValorInicial(Long indicadorId, Long planId) {
    return persistenceSession.getValorInicial(indicadorId, planId);
  }
  
  public List getMetasParcialesComoMediciones(Byte frecuenciaOrigen, Byte frecuenciaDestino, Boolean acumular, Byte tipoFuncionIndicador, Boolean valorInicialCero, Byte tipoCorteIndicador, Long indicadorId, Long planId, Integer anoDesde, Integer periodoDesde, Integer anoHasta, Integer periodoHasta) {
    return persistenceSession.getMetasParcialesComoMediciones(frecuenciaOrigen, frecuenciaDestino, acumular, tipoFuncionIndicador, valorInicialCero, tipoCorteIndicador, indicadorId, planId, anoDesde, periodoDesde, anoHasta, periodoHasta);
  }
  
  public List getMetasParcialesPorPeriodo(Long indicadorId, Byte frecuencia, Long planId, Integer ano, Byte tipoMeta) {
    return persistenceSession.getMetasParcialesPorPeriodo(indicadorId, frecuencia, planId, ano, tipoMeta);
  }
  
  public List getMetasParciales(Long indicadorId, Long planId, Byte frecuencia, Byte mesCierre, Byte corte, Byte tipoCargaMedicion, Byte tipoMeta, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta) {
    return persistenceSession.getMetasParciales(indicadorId, planId, frecuencia, mesCierre, corte, tipoCargaMedicion, tipoMeta, anoDesde, anoHasta, periodoDesde, periodoHasta);
  }
  
  public Meta getValorInicial(Long indicadorId, Long planId, Byte frecuencia, Byte mesCierre, Integer ano)
  {
    int periodoDesdeCierre = 0;
    int periodoHastaCierre = 0;
    Byte mesInicio = null;
    
    if (mesCierre != null) {
      mesInicio = PeriodoUtil.getMesInicio(mesCierre);
    } else {
      mesCierre = new Byte("12");
      mesInicio = new Byte("1");
    }
    
    if (mesCierre.byteValue() == 12) {
      periodoDesdeCierre = 1;
      periodoHastaCierre = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano.intValue());
    } else {
      periodoDesdeCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesInicio.intValue(), ano.intValue() - 1), frecuencia.byteValue());
      periodoHastaCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesCierre.intValue(), ano.intValue()), frecuencia.byteValue());
    }
    
    Meta valorInicial = persistenceSession.getValorInicial(indicadorId, planId, ano, new Integer(periodoDesdeCierre));
    
    if (valorInicial == null) {
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
      
      List mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieRealId(), new Integer(ano.intValue() - 1), new Integer(ano.intValue() - 1), new Integer(periodoHastaCierre), new Integer(periodoHastaCierre));
      
      if (mediciones.size() > 0) {
        Medicion medicion = (Medicion)mediciones.get(0);
        valorInicial = new Meta();
        MetaPK metaId = new MetaPK();
        metaId.setIndicadorId(indicadorId);
        metaId.setPlanId(planId);
        metaId.setTipo(TipoMeta.getTipoMetaValorInicial());
        metaId.setSerieId(SerieTiempo.getSerieMetaId());
        metaId.setAno(ano);
        metaId.setPeriodo(new Integer(periodoHastaCierre));
        valorInicial.setMetaId(metaId);
        valorInicial.setProtegido(new Boolean(false));
        valorInicial.setValor(medicion.getValor());
      }
      strategosMedicionesService.close();
    }
    
    if (valorInicial == null) {
      valorInicial = persistenceSession.getUltimoValorInicial(indicadorId, planId);
    }
    
    return valorInicial;
  }
  
  public int deleteMetas(Long indicadorId, Long planId, Byte tipoMeta, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal) {
    return persistenceSession.deleteMetas(indicadorId, planId, tipoMeta, anoInicio, anoFinal, periodoInicio, periodoFinal);
  }
  
  public List getAnosConMetaParcial(Long indicadorId, Long planId)
  {
    return persistenceSession.getAnosConMetaParcial(indicadorId, planId);
  }
  
  public int copyMetas(Long indicadorId, Long planIdOrigen, Long planIdDestino, Usuario usuario)
  {
    List<Meta> metas = persistenceSession.getMetas(indicadorId, planIdOrigen);
    List<Meta> metasCopiadas = new ArrayList();
    
    for (Iterator<Meta> j = metas.iterator(); j.hasNext();)
    {
      Meta meta = (Meta)j.next();
      Meta metaCopiada = new Meta(new MetaPK(planIdDestino, indicadorId, meta.getMetaId().getSerieId(), meta.getMetaId().getTipo(), meta.getMetaId().getAno(), meta.getMetaId().getPeriodo()), meta.getValor(), new Boolean(false));
      metasCopiadas.add(metaCopiada);
    }
    
    if (metasCopiadas.size() > 0) {
      return saveMetas(metasCopiadas, usuario);
    }
    return 10000;
  }
  
  
  
 
  
  public List<Meta> getMetas(Long indicadorId, Long planId, Integer ano)
  {
    return persistenceSession.getMetasEjecutar(indicadorId, planId, ano);
  }

  public List<Meta> getMetasParaEjecutar(Long indicadorId, List<Long> indicadoresIds, Long organizacionId, boolean todasOrganizaciones, Long planId, Integer ano)
  {
   
	List<Meta> metas = null;
	StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService();
    
    if (indicadorId != null)
    {
      metas = getMetas(indicadorId, planId, ano);
      
    }
    else if (indicadoresIds != null)
    {
     List<Meta>metasIndicadores = new ArrayList<Meta>();	
     for(Long ind: indicadoresIds){
    	 if(ind !=null){
    		 metasIndicadores.addAll(getMetas(ind, planId, ano));
    	 }
     }
     metas = metasIndicadores;
    }
    else if (planId != null)
    {	
      metas = getMetas(null, planId, ano);
    }
    else if (todasOrganizaciones)
    {
      metas = getMetas(null, null, ano);
    }
    else if (organizacionId != null)
    {
      List<Meta> metasPlanes = new ArrayList<Meta>();	
      List<Plan> planes=strategosPlanesService.getPlanesPorOrganizacion(organizacionId, null, null);
      for(Plan p: planes){
    	  if(p.getPlanId() != null){
    		  metasPlanes.addAll(getMetas(null, p.getPlanId(), ano));
    	  }
      }
      metas = metasPlanes;
    }
    
    strategosPlanesService.close();
    return metas;
  }

}
