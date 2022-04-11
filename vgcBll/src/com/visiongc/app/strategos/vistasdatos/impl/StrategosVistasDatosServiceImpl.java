package com.visiongc.app.strategos.vistasdatos.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.vistasdatos.StrategosVistasDatosService;
import com.visiongc.app.strategos.vistasdatos.model.util.TipoVariable;
import com.visiongc.app.strategos.vistasdatos.persistence.StrategosVistasDatosPersistenceSession;
import com.visiongc.commons.util.StringUtil;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.servicio.strategos.planes.model.util.TipoIndicadorEstado;
import com.visiongc.servicio.strategos.util.PeriodoUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StrategosVistasDatosServiceImpl
  extends StrategosServiceImpl implements StrategosVistasDatosService
{
  public StrategosVistasDatosServiceImpl(StrategosVistasDatosPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
  }
  
  public String getValorDimensiones(String variableId, String tiempoDesdeId, String tiempoHastaId, String indicadorId, String planId, String organizacionId, Boolean acumular)
  {
    String valor = "";
    
    if ((!variableId.equals("TF")) && (!variableId.equals("TC")))
    {
      if ((variableId != null) && (!variableId.equals("")) && (new Long(variableId).equals(TipoVariable.getTipoVariableMeta())))
        valor = getMeta(variableId, indicadorId, planId, tiempoDesdeId, tiempoHastaId);
      if ((variableId != null) && (!variableId.equals("")) && (new Long(variableId).equals(TipoVariable.getTipoVariableReal())))
        valor = getReal(variableId, indicadorId, tiempoDesdeId, tiempoHastaId, acumular);
      if ((variableId != null) && (!variableId.equals("")) && (new Long(variableId).equals(TipoVariable.getTipoVariableProgramado())))
        valor = getProgramado(variableId, indicadorId, tiempoDesdeId, tiempoHastaId);
      if ((variableId != null) && (!variableId.equals("")) && (new Long(variableId).equals(TipoVariable.getTipoVariableAlerta())))
        valor = getAlerta(variableId, indicadorId, tiempoDesdeId, tiempoHastaId, planId);
      if ((variableId != null) && (!variableId.equals("")) && (new Long(variableId).equals(TipoVariable.getTipoVariableAdsoluta())))
        valor = getVariableAdsoluta(variableId, indicadorId, tiempoDesdeId, tiempoHastaId, planId);
      if ((variableId != null) && (!variableId.equals("")) && (new Long(variableId).equals(TipoVariable.getTipoVariablePorcentaje())))
        valor = getVariablePorcentual(variableId, indicadorId, tiempoDesdeId, tiempoHastaId, planId);
      if ((variableId != null) && (!variableId.equals("")) && ((new Long(variableId).equals(TipoVariable.getTipoVariablePorcentajeCumplimientoParcial())) || (new Long(variableId).equals(TipoVariable.getTipoVariablePorcentajeCumplimientoAnual())))) {
        valor = getVariablePorcentajeCumplimientoParcialAnual(variableId, indicadorId, tiempoDesdeId, tiempoHastaId, planId);
      }
    }
    return valor;
  }
  
  private String getReal(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId, Boolean acumular)
  {
    String valor = "";
    
    Medicion medicion = getRealObj(variableId, indicadorId, tiempoDesdeId, tiempoHastaId, acumular);
    
    if (medicion != null)
    {
      Double valorMedicion = medicion.getValor();
      if (valorMedicion != null) {
        valor = valorMedicion.toString();
      }
    }
    return valor;
  }
  
  private Medicion getRealObj(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId, Boolean acumular)
  {
    Medicion medicion = null;
    boolean calcularReal = getCalcularReal(variableId, indicadorId, tiempoDesdeId, tiempoHastaId);
    
    if ((calcularReal) && (!tiempoDesdeId.equals("TF")) && (!tiempoDesdeId.equals("TC")) && (!tiempoHastaId.equals("TF")) && (!tiempoHastaId.equals("TC")))
    {
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
      
      Indicador indicador = (Indicador)strategosMedicionesService.load(Indicador.class, new Long(indicadorId));
      
      OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosMedicionesService.load(OrganizacionStrategos.class, indicador.getOrganizacionId());
      
      String[] arrPeriodoAnoDesde = StringUtil.split(tiempoDesdeId, "_");
      Integer periodoDesde = new Integer(arrPeriodoAnoDesde[0]);
      Integer anoDesde = new Integer(arrPeriodoAnoDesde[1]);
      
      String[] arrPeriodoAnoHasta = StringUtil.split(tiempoHastaId, "_");
      Integer periodoHasta = new Integer(arrPeriodoAnoHasta[0]);
      Integer anoHasta = new Integer(arrPeriodoAnoHasta[1]);
      
      List<Medicion> mediciones = new ArrayList();
      
      mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), organizacionStrategos.getMesCierre(), SerieTiempo.getSerieReal().getSerieId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), anoDesde, anoHasta, periodoDesde, periodoHasta);
      
      if ((mediciones.size() > 0) && (acumular.booleanValue()))
      {
        Double valor = null;
        for (Iterator<Medicion> iterAportes = mediciones.iterator(); iterAportes.hasNext();)
        {
          Medicion aporte = (Medicion)iterAportes.next();
          if (aporte.getValor() != null)
          {
            if (valor != null) {
              valor = Double.valueOf(valor.doubleValue() + aporte.getValor().doubleValue());
            } else {
              valor = aporte.getValor();
            }
          }
        }
        medicion = new Medicion(new MedicionPK(indicador.getIndicadorId(), anoHasta, periodoHasta, SerieTiempo.getSerieReal().getSerieId()), valor, new Boolean(false));
      }
      else if (mediciones.size() > 0)
      {
        for (int indexMedicion = mediciones.size() - 1; indexMedicion >= 0; indexMedicion--)
        {
          medicion = (Medicion)mediciones.get(indexMedicion);
          if (medicion.getValor() != null)
            break;
        }
        if (medicion == null) {
          medicion = (Medicion)mediciones.get(mediciones.size() - 1);
        }
      }
      strategosMedicionesService.close();
    }
    
    return medicion;
  }
  
  private String getProgramado(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId)
  {
    String valor = "";
    Medicion medicion = getProgramadoObj(variableId, indicadorId, tiempoDesdeId, tiempoHastaId);
    
    if (medicion != null)
    {
      Double valorMedicion = medicion.getValor();
      if (valorMedicion != null) {
        valor = valorMedicion.toString();
      }
    }
    return valor;
  }
  
  private Medicion getProgramadoObj(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId)
  {
    Medicion medicion = null;
    boolean calcularProgramado = getCalcularProgramado(variableId, indicadorId, tiempoDesdeId, tiempoHastaId);
    
    if ((calcularProgramado) && (!tiempoDesdeId.equals("TF")) && (!tiempoDesdeId.equals("TC")) && (!tiempoHastaId.equals("TF")) && (!tiempoHastaId.equals("TC")))
    {
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
      
      Indicador indicador = (Indicador)strategosMedicionesService.load(Indicador.class, new Long(indicadorId));
      
      OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosMedicionesService.load(OrganizacionStrategos.class, indicador.getOrganizacionId());
      
      String[] arrPeriodoAnoDesde = StringUtil.split(tiempoDesdeId, "_");
      Integer periodoDesde = new Integer(arrPeriodoAnoDesde[0]);
      Integer anoDesde = new Integer(arrPeriodoAnoDesde[1]);
      
      String[] arrPeriodoAnoHasta = StringUtil.split(tiempoHastaId, "_");
      Integer periodoHasta = new Integer(arrPeriodoAnoHasta[0]);
      Integer anoHasta = new Integer(arrPeriodoAnoHasta[1]);
      
      List<Medicion> mediciones = new ArrayList();
      
      mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), organizacionStrategos.getMesCierre(), SerieTiempo.getSerieProgramado().getSerieId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), anoDesde, anoHasta, periodoDesde, periodoHasta);
      
      if (mediciones.size() > 0) {
        medicion = (Medicion)mediciones.get(mediciones.size() - 1);
      }
      strategosMedicionesService.close();
    }
    
    return medicion;
  }
  
  private String getMeta(String variableId, String indicadorId, String planId, String tiempoDesdeId, String tiempoHastaId)
  {
    String valor = "";
    
    Meta meta = getMetaObj(variableId, indicadorId, planId, tiempoDesdeId, tiempoHastaId);
    
    if (meta != null)
    {
      Double valorMeta = meta.getValor();
      if (valorMeta != null) {
        valor = valorMeta.toString();
      }
    }
    return valor;
  }
  
  private Meta getMetaObj(String variableId, String indicadorId, String planId, String tiempoDesdeId, String tiempoHastaId)
  {
    Meta meta = null;
    
    boolean calcularMeta = getCalcularMeta(variableId, indicadorId, planId, tiempoDesdeId, tiempoHastaId);
    
    if ((calcularMeta) && (!tiempoDesdeId.equals("TF")) && (!tiempoDesdeId.equals("TC")) && (!tiempoHastaId.equals("TF")) && (!tiempoHastaId.equals("TC")))
    {
      StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService(this);
      
      Indicador indicador = (Indicador)strategosMetasService.load(Indicador.class, new Long(indicadorId));
      OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosMetasService.load(OrganizacionStrategos.class, indicador.getOrganizacionId());
      
      String[] arrPeriodoAnoDesde = StringUtil.split(tiempoDesdeId, "_");
      Integer periodoDesde = new Integer(arrPeriodoAnoDesde[0]);
      Integer anoDesde = new Integer(arrPeriodoAnoDesde[1]);
      
      String[] arrPeriodoAnoHasta = StringUtil.split(tiempoHastaId, "_");
      Integer periodoHasta = new Integer(arrPeriodoAnoHasta[0]);
      Integer anoHasta = new Integer(arrPeriodoAnoHasta[1]);
      
      Byte tipoMeta = TipoMeta.getTipoMetaParcial();
      if (periodoDesde.equals(new Integer(0))) {
        tipoMeta = TipoMeta.getTipoMetaAnual();
      }
      List<Meta> metas = new ArrayList();
      
      if (tipoMeta.equals(TipoMeta.getTipoMetaParcial())) {
        metas = strategosMetasService.getMetasParciales(indicador.getIndicadorId(), new Long(planId), indicador.getFrecuencia(), organizacionStrategos.getMesCierre(), indicador.getCorte(), indicador.getTipoCargaMedicion(), TipoMeta.getTipoMetaParcial(), anoDesde, anoHasta, periodoDesde, periodoHasta);
      } else if (tipoMeta.equals(TipoMeta.getTipoMetaAnual())) {
        metas = strategosMetasService.getMetasAnuales(indicador.getIndicadorId(), new Long(planId), anoDesde, anoDesde, Boolean.valueOf(false));
      }
      if (metas.size() > 0) {
        meta = (Meta)metas.get(0);
      }
      strategosMetasService.close();
    }
    
    return meta;
  }
  
  private boolean getCalcularMeta(String variableId, String indicadorId, String planId, String tiempoDesdeId, String tiempoHastaId)
  {
    boolean calcularMeta = (variableId != null) && (indicadorId != null) && (planId != null) && (tiempoDesdeId != null) && (tiempoHastaId != null) && (!variableId.equals("")) && (!indicadorId.equals("")) && (!planId.equals("")) && (!tiempoDesdeId.equals("")) && (!tiempoHastaId.equals(""));
    
    if (calcularMeta) {
      calcularMeta = new Long(variableId).equals(TipoVariable.getTipoVariableMeta());
    }
    return calcularMeta;
  }
  
  private boolean getCalcularProgramado(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId)
  {
    boolean calcularPresupuesto = (variableId != null) && (indicadorId != null) && (tiempoDesdeId != null) && (tiempoHastaId != null) && (!variableId.equals("")) && (!indicadorId.equals("")) && (!tiempoDesdeId.equals("")) && (!tiempoHastaId.equals(""));
    
    if (calcularPresupuesto) {
      calcularPresupuesto = new Long(variableId).equals(TipoVariable.getTipoVariableProgramado());
    }
    return calcularPresupuesto;
  }
  
  private boolean getCalcularReal(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId)
  {
    boolean calcularReal = (variableId != null) && (indicadorId != null) && (tiempoDesdeId != null) && (tiempoHastaId != null) && (!variableId.equals("")) && (!indicadorId.equals("")) && (!tiempoDesdeId.equals("")) && (!tiempoHastaId.equals(""));
    
    if (calcularReal) {
      calcularReal = new Long(variableId).equals(TipoVariable.getTipoVariableReal());
    }
    return calcularReal;
  }
  
  private boolean getCalcularVariablePorcentual(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId, String planId)
  {
    boolean calcularVariablePorcentual = (variableId != null) && (indicadorId != null) && (tiempoDesdeId != null) && (tiempoHastaId != null) && (!variableId.equals("")) && (!indicadorId.equals("")) && (!tiempoDesdeId.equals("")) && (!tiempoHastaId.equals(""));
    
    if (calcularVariablePorcentual) {
      calcularVariablePorcentual = new Long(variableId).equals(TipoVariable.getTipoVariablePorcentaje());
    }
    return calcularVariablePorcentual;
  }
  
  private boolean getCalcularVariableAdsoluta(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId, String planId)
  {
    boolean calcularVariableAdsoluta = (variableId != null) && (indicadorId != null) && (tiempoDesdeId != null) && (tiempoHastaId != null) && (!variableId.equals("")) && (!indicadorId.equals("")) && (!tiempoDesdeId.equals("")) && (!tiempoHastaId.equals(""));
    
    if (calcularVariableAdsoluta) {
      calcularVariableAdsoluta = new Long(variableId).equals(TipoVariable.getTipoVariableAdsoluta());
    }
    return calcularVariableAdsoluta;
  }
  
  private boolean getCalcularVariablePorcentajeCumplimientoParcialAnual(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId, String planId)
  {
    boolean calcularVariable = (variableId != null) && (indicadorId != null) && (tiempoDesdeId != null) && (tiempoHastaId != null) && (!variableId.equals("")) && (!indicadorId.equals("")) && (!tiempoDesdeId.equals("")) && (!tiempoHastaId.equals(""));
    
    if (calcularVariable) {
      calcularVariable = (new Long(variableId).equals(TipoVariable.getTipoVariablePorcentajeCumplimientoParcial())) || (new Long(variableId).equals(TipoVariable.getTipoVariablePorcentajeCumplimientoAnual()));
    }
    return calcularVariable;
  }
  
  private boolean getCalcularAlerta(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId, String planId)
  {
    boolean calcularAlerta = (variableId != null) && (indicadorId != null) && (tiempoDesdeId != null) && (tiempoHastaId != null) && (!variableId.equals("")) && (!indicadorId.equals("")) && (!tiempoDesdeId.equals("")) && (!tiempoHastaId.equals(""));
    
    if (calcularAlerta) {
      calcularAlerta = new Long(variableId).equals(TipoVariable.getTipoVariableAlerta());
    }
    return calcularAlerta;
  }
  
  private String getVariablePorcentual(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId, String planId)
  {
    String valor = null;
    boolean calcularVariablePorcentual = getCalcularVariablePorcentual(variableId, indicadorId, tiempoDesdeId, tiempoHastaId, planId);
    
    if (calcularVariablePorcentual)
    {
      StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
      StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService(this);
      StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
      
      Indicador indicador = (Indicador)strategosPlanesService.load(Indicador.class, new Long(indicadorId));
      
      if ((planId != null) && (!planId.equals("")))
      {
        Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));
        
        if ((plan != null) && (strategosPlanesService.getIndicadorEstaAsociadoPlan(indicador.getIndicadorId(), plan.getPlanId())))
        {
          Medicion medicion = getRealObj(TipoVariable.getTipoVariableReal().toString(), indicadorId, tiempoDesdeId, tiempoHastaId, Boolean.valueOf(false));
          Meta meta = getMetaObj(TipoVariable.getTipoVariableMeta().toString(), indicadorId, planId, tiempoDesdeId, tiempoHastaId);
          
          Double ejecutadoReal = null;
          Double metaReal = null;
          if (medicion.getValor() != null)
            ejecutadoReal = medicion.getValor();
          if (meta.getValor() != null) {
            metaReal = meta.getValor();
          }
          if ((ejecutadoReal != null) && (metaReal != null) && (metaReal.doubleValue() != 0.0D)) {
            valor = Double.valueOf(ejecutadoReal.doubleValue() / metaReal.doubleValue() * 100.0D - 100.0D).toString();
          }
        }
      }
      else {
        Medicion medicionEjecutado = getRealObj(TipoVariable.getTipoVariableReal().toString(), indicadorId, tiempoDesdeId, tiempoHastaId, Boolean.valueOf(false));
        Medicion medicionProgramado = getProgramadoObj(TipoVariable.getTipoVariableProgramado().toString(), indicadorId, tiempoDesdeId, tiempoHastaId);
        
        Double ejecutado = null;
        Double meta = null;
        if ((medicionEjecutado != null) && (medicionEjecutado.getValor() != null))
          ejecutado = medicionEjecutado.getValor();
        if ((medicionProgramado != null) && (medicionProgramado.getValor() != null)) {
          meta = medicionProgramado.getValor();
        }
        if ((ejecutado != null) && (meta != null) && (meta.doubleValue() != 0.0D)) {
          valor = Double.valueOf(ejecutado.doubleValue() / meta.doubleValue() * 100.0D).toString();
        }
      }
      strategosIndicadoresService.close();
      strategosMetasService.close();
      strategosMedicionesService.close();
      strategosPlanesService.close();
    }
    
    return valor;
  }
  
  private String getVariablePorcentajeCumplimientoParcialAnual(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId, String planId)
  {
    String valor = null;
    
    boolean calcularVariable = getCalcularVariablePorcentajeCumplimientoParcialAnual(variableId, indicadorId, tiempoDesdeId, tiempoHastaId, planId);
    Byte tipo = new Long(variableId).equals(TipoVariable.getTipoVariablePorcentajeCumplimientoParcial()) ? TipoIndicadorEstado.getTipoIndicadorEstadoParcial() : TipoIndicadorEstado.getTipoIndicadorEstadoAnual();
    
    if (calcularVariable)
    {
      StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
      StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService(this);
      StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
      
      Indicador indicador = (Indicador)strategosPlanesService.load(Indicador.class, new Long(indicadorId));
      
      if ((planId != null) && (!planId.equals("")))
      {
        Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));
        
        if ((plan != null) && (strategosPlanesService.getIndicadorEstaAsociadoPlan(indicador.getIndicadorId(), plan.getPlanId())) && (!tiempoDesdeId.equals("TF")) && (!tiempoDesdeId.equals("TC")) && (!tiempoHastaId.equals("TF")) && (!tiempoHastaId.equals("TC")))
        {
          Medicion medicion = getRealObj(TipoVariable.getTipoVariableReal().toString(), indicadorId, tiempoDesdeId, tiempoHastaId, Boolean.valueOf(false));
          Meta meta = getMetaObj(TipoVariable.getTipoVariableMeta().toString(), indicadorId, planId, tiempoDesdeId, tiempoHastaId);
          
          Double ejecutadoReal = null;
          Double metaReal = null;
          Double ultimaMedicionAnoAnterior = null;
          if (medicion.getValor() != null)
            ejecutadoReal = medicion.getValor();
          if (meta.getValor() != null) {
            metaReal = meta.getValor();
          }
          String[] arrPeriodoAnoDesde = StringUtil.split(tiempoDesdeId, "_");
          int anoDesde = new Integer(arrPeriodoAnoDesde[1]).intValue() - 1;
          int periodoDesdeMaximoIndicador = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), anoDesde);
          
          String[] arrPeriodoAnoHasta = StringUtil.split(tiempoHastaId, "_");
          int anoHasta = new Integer(arrPeriodoAnoHasta[1]).intValue() - 1;
          int periodoHastaMaximoIndicador = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), anoHasta);
          
          List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), Integer.valueOf(anoDesde), Integer.valueOf(anoHasta), Integer.valueOf(periodoDesdeMaximoIndicador), Integer.valueOf(periodoHastaMaximoIndicador));
          if (mediciones.size() > 0)
          {
            Medicion ultimaMedicion = (Medicion)mediciones.iterator().next();
            ultimaMedicionAnoAnterior = ultimaMedicion.getValor();
          }
          
          if ((ultimaMedicionAnoAnterior == null) && (plan != null))
          {
            meta = strategosMetasService.getValorInicial(indicador.getIndicadorId(), plan.getPlanId());
            if ((meta != null) && (meta.getValor() != null)) {
              ultimaMedicionAnoAnterior = meta.getValor();
            }
          }
          if ((ejecutadoReal != null) && (metaReal != null) && (metaReal.doubleValue() != 0.0D))
          {
            indicador.setUltimaMedicion(ejecutadoReal);
            indicador.setUltimoProgramado(metaReal);
            valor = indicador.getPorcentajeCumplimientoFormateado(ultimaMedicionAnoAnterior, tipo);
          }
        }
      }
      else if ((!tiempoDesdeId.equals("TF")) && (!tiempoDesdeId.equals("TC")) && (!tiempoHastaId.equals("TF")) && (!tiempoHastaId.equals("TC")))
      {
        Medicion medicionEjecutado = getRealObj(TipoVariable.getTipoVariableReal().toString(), indicadorId, tiempoDesdeId, tiempoHastaId, Boolean.valueOf(false));
        Medicion medicionProgramado = getProgramadoObj(TipoVariable.getTipoVariableProgramado().toString(), indicadorId, tiempoDesdeId, tiempoHastaId);
        
        Double ejecutado = null;
        Double meta = null;
        Double ultimaMedicionAnoAnterior = null;
        if ((medicionEjecutado != null) && (medicionEjecutado.getValor() != null))
          ejecutado = medicionEjecutado.getValor();
        if ((medicionProgramado != null) && (medicionProgramado.getValor() != null)) {
          meta = medicionProgramado.getValor();
        }
        String[] arrPeriodoAnoDesde = StringUtil.split(tiempoDesdeId, "_");
        int anoDesde = new Integer(arrPeriodoAnoDesde[1]).intValue() - 1;
        int periodoDesdeMaximoIndicador = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), anoDesde);
        
        String[] arrPeriodoAnoHasta = StringUtil.split(tiempoHastaId, "_");
        int anoHasta = new Integer(arrPeriodoAnoHasta[1]).intValue() - 1;
        int periodoHastaMaximoIndicador = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), anoHasta);
        
        List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), Integer.valueOf(anoDesde), Integer.valueOf(anoHasta), Integer.valueOf(periodoDesdeMaximoIndicador), Integer.valueOf(periodoHastaMaximoIndicador));
        if (mediciones.size() > 0)
        {
          Medicion ultimaMedicion = (Medicion)mediciones.iterator().next();
          ultimaMedicionAnoAnterior = ultimaMedicion.getValor();
        }
        
        if ((ejecutado != null) && (meta != null) && (meta.doubleValue() != 0.0D))
        {
          indicador.setUltimaMedicion(ejecutado);
          indicador.setUltimoProgramado(meta);
          if (ultimaMedicionAnoAnterior == null)
            ultimaMedicionAnoAnterior = Double.valueOf(0.0D);
          valor = indicador.getPorcentajeCumplimientoFormateado(ultimaMedicionAnoAnterior, tipo);
        }
      }
      
      strategosIndicadoresService.close();
      strategosMetasService.close();
      strategosMedicionesService.close();
      strategosPlanesService.close();
    }
    
    return valor;
  }
  
  private String getVariableAdsoluta(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId, String planId)
  {
    String valor = null;
    boolean calcularVariableAdsoluta = getCalcularVariableAdsoluta(variableId, indicadorId, tiempoDesdeId, tiempoHastaId, planId);
    
    if (calcularVariableAdsoluta)
    {
      StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
      StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService(this);
      StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
      
      Indicador indicador = (Indicador)strategosPlanesService.load(Indicador.class, new Long(indicadorId));
      
      if ((planId != null) && (!planId.equals("")))
      {
        Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));
        
        if ((plan != null) && (strategosPlanesService.getIndicadorEstaAsociadoPlan(indicador.getIndicadorId(), plan.getPlanId())))
        {
          Medicion medicion = getRealObj(TipoVariable.getTipoVariableReal().toString(), indicadorId, tiempoDesdeId, tiempoHastaId, Boolean.valueOf(false));
          Meta meta = getMetaObj(TipoVariable.getTipoVariableMeta().toString(), indicadorId, planId, tiempoDesdeId, tiempoHastaId);
          
          Double ejecutadoReal = null;
          Double metaReal = null;
          if (medicion.getValor() != null)
            ejecutadoReal = medicion.getValor();
          if (meta.getValor() != null) {
            metaReal = meta.getValor();
          }
          if ((ejecutadoReal != null) && (metaReal != null)) {
            valor = Double.valueOf(ejecutadoReal.doubleValue() - metaReal.doubleValue()).toString();
          }
        }
      }
      else {
        Medicion medicionEjecutado = getRealObj(TipoVariable.getTipoVariableReal().toString(), indicadorId, tiempoDesdeId, tiempoHastaId, Boolean.valueOf(false));
        Medicion medicionProgramado = getProgramadoObj(TipoVariable.getTipoVariableProgramado().toString(), indicadorId, tiempoDesdeId, tiempoHastaId);
        
        Double ejecutado = null;
        Double meta = null;
        if ((medicionEjecutado != null) && (medicionEjecutado.getValor() != null))
          ejecutado = medicionEjecutado.getValor();
        if ((medicionProgramado != null) && (medicionProgramado.getValor() != null)) {
          meta = medicionProgramado.getValor();
        }
        if ((ejecutado != null) && (meta != null)) {
          valor = Double.valueOf(ejecutado.doubleValue() - meta.doubleValue()).toString();
        }
      }
      strategosIndicadoresService.close();
      strategosMetasService.close();
      strategosMedicionesService.close();
      strategosPlanesService.close();
    }
    
    return valor;
  }
  
  private String getAlerta(String variableId, String indicadorId, String tiempoDesdeId, String tiempoHastaId, String planId)
  {
    String valor = null;
    boolean calcularAlerta = getCalcularAlerta(variableId, indicadorId, tiempoDesdeId, tiempoHastaId, planId);
    
    if (calcularAlerta)
    {
      StrategosPlanesService strategosPlanesService = StrategosServiceFactory.getInstance().openStrategosPlanesService(this);
      StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService(this);
      StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService(this);
      StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
      
      Indicador indicador = (Indicador)strategosPlanesService.load(Indicador.class, new Long(indicadorId));
      
      if ((planId != null) && (!planId.equals("")))
      {
        Plan plan = (Plan)strategosPlanesService.load(Plan.class, new Long(planId));
        
        if ((plan != null) && (strategosPlanesService.getIndicadorEstaAsociadoPlan(indicador.getIndicadorId(), plan.getPlanId())))
        {
          Medicion medicion = getRealObj(TipoVariable.getTipoVariableReal().toString(), indicadorId, tiempoDesdeId, tiempoHastaId, Boolean.valueOf(false));
          Meta meta = getMetaObj(TipoVariable.getTipoVariableMeta().toString(), indicadorId, planId, tiempoDesdeId, tiempoHastaId);
          
          Byte alerta = AlertaIndicador.getAlertaNoDefinible();
          if ((medicion.getValor() != null) && (meta.getValor() != null))
          {
            Double zonaVerde = null;
            Double zonaAmarilla = null;
            zonaVerde = strategosIndicadoresService.zonaVerde(indicador, medicion.getMedicionId().getAno(), medicion.getMedicionId().getPeriodo(), meta.getValor(), strategosMedicionesService);
            zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, medicion.getMedicionId().getAno(), medicion.getMedicionId().getPeriodo(), meta.getValor(), zonaVerde, strategosMedicionesService);
            
            alerta = new Servicio().calcularAlertaXPeriodos(Servicio.EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, medicion.getValor(), meta.getValor(), null, null);
          }
          
          if (alerta != null) {
            valor = alerta.toString();
          }
        }
      }
      else {
        Byte alerta = null;
        Medicion medicionEjecutado = getRealObj(TipoVariable.getTipoVariableReal().toString(), indicadorId, tiempoDesdeId, tiempoHastaId, Boolean.valueOf(false));
        Medicion medicionProgramado = getProgramadoObj(TipoVariable.getTipoVariableProgramado().toString(), indicadorId, tiempoDesdeId, tiempoHastaId);
        
        Double ejecutado = null;
        Double meta = null;
        if ((medicionEjecutado != null) && (medicionEjecutado.getValor() != null))
          ejecutado = medicionEjecutado.getValor();
        if ((medicionProgramado != null) && (medicionProgramado.getValor() != null)) {
          meta = medicionProgramado.getValor();
        }
        Double zonaVerde = null;
        Double zonaAmarilla = null;
        if (ejecutado != null)
        {
          if (meta == null)
          {
            int periodoAnterior = PeriodoUtil.getPeriodoAnterior(medicionEjecutado.getMedicionId().getPeriodo().intValue(), medicionEjecutado.getMedicionId().getAno().intValue(), indicador.getFrecuencia().byteValue());
            int anoPeriodoAnterior = PeriodoUtil.getAnoPeriodoAnterior(medicionEjecutado.getMedicionId().getPeriodo().intValue(), medicionEjecutado.getMedicionId().getAno().intValue());
            List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), new Integer(anoPeriodoAnterior), new Integer(anoPeriodoAnterior), new Integer(periodoAnterior), new Integer(periodoAnterior), indicador.getFrecuencia());
            Medicion ejecutadoAnterior = null;
            
            if (mediciones.size() > 0)
            {
              ejecutadoAnterior = (Medicion)mediciones.get(mediciones.size() - 1);
              if (ejecutadoAnterior.getValor() != null) {
                alerta = new Servicio().calcularAlertaXPeriodos(Servicio.EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, ejecutado, meta, null, ejecutadoAnterior.getValor());
              }
            }
          }
          else {
            zonaVerde = strategosIndicadoresService.zonaVerde(indicador, medicionEjecutado.getMedicionId().getAno(), medicionEjecutado.getMedicionId().getPeriodo(), meta, strategosMedicionesService);
            zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, medicionEjecutado.getMedicionId().getAno(), medicionEjecutado.getMedicionId().getPeriodo(), meta, zonaVerde, strategosMedicionesService);
            
            alerta = new Servicio().calcularAlertaXPeriodos(Servicio.EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, ejecutado, meta, null, null);
          }
        }
        
        if (alerta != null) {
          valor = alerta.toString();
        }
      }
      strategosIndicadoresService.close();
      strategosMetasService.close();
      strategosMedicionesService.close();
      strategosPlanesService.close();
    }
    
    return valor;
  }
}
