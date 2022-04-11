package com.visiongc.app.strategos.planes.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.persistence.hibernate.StrategosHibernateSession;
import com.visiongc.app.strategos.planes.model.IndicadorPlan;
import com.visiongc.app.strategos.planes.model.IndicadorPlanPK;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.MetaPK;
import com.visiongc.app.strategos.planes.model.util.MetaAnualParciales;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.planes.persistence.StrategosMetasPersistenceSession;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

public class StrategosMetasHibernateSession
  extends StrategosHibernateSession
  implements StrategosMetasPersistenceSession
{
  public StrategosMetasHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosMetasHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public List getMetasAnuales(Long indicadorId, Long planId, Integer anoDesde, Integer anoHasta, Boolean acumular)
  {
    String sql = "from Meta meta where meta.metaId.indicadorId = :indicadorId and meta.metaId.planId = :planId";
    sql = sql + " and meta.metaId.serieId = " + SerieTiempo.getSerieMetaId() + " and meta.metaId.tipo = " + TipoMeta.getTipoMetaAnual();
    sql = sql + " and (meta.metaId.ano >= :anoDesde and meta.metaId.ano <= :anoHasta)";
    sql = sql + " order by meta.metaId.ano";
    
    Query query = session.createQuery(sql);
    
    if (indicadorId != null) {
      query.setLong("indicadorId", indicadorId.longValue());
    }
    if (planId != null) {
      query.setLong("planId", planId.longValue());
    }
    if (anoDesde != null) {
      query.setInteger("anoDesde", anoDesde.intValue());
    }
    if (anoHasta != null) {
      query.setInteger("anoHasta", anoHasta.intValue());
    }
    List<Meta> metas = new ArrayList();
    
    Iterator<Meta> metasExistentes = query.list().iterator();
    
    int ano = anoDesde.intValue();
    boolean getExistente = metasExistentes.hasNext();
    Meta meta = null;
    Meta metaExistente = null;
    Double valor = Double.valueOf(0.0D);
    
    while (ano <= anoHasta.intValue())
    {
      if (getExistente) {
        metaExistente = (Meta)metasExistentes.next();
      }
      if (metaExistente != null)
      {
        if (metaExistente.getMetaId().getAno().intValue() == ano)
        {
          if (metaExistente.getValor() != null)
          {
            if (acumular.booleanValue())
            {
              valor = Double.valueOf(valor.doubleValue() + metaExistente.getValor().doubleValue());
              metaExistente.setValor(valor);
            }
            metaExistente.setValorGrande(new BigDecimal(metaExistente.getValor().doubleValue()));
          }
          
          meta = metaExistente;
          getExistente = metasExistentes.hasNext();
          metaExistente = null;
        }
        else
        {
          meta = null;
          getExistente = false;
        }
      }
      else
      {
        meta = null;
        getExistente = false;
      }
      
      if (meta == null)
      {
        meta = new Meta();
        MetaPK metaPk = new MetaPK();
        metaPk.setIndicadorId(indicadorId);
        metaPk.setPlanId(planId);
        metaPk.setSerieId(SerieTiempo.getSerieMetaId());
        metaPk.setTipo(TipoMeta.getTipoMetaAnual());
        metaPk.setPeriodo(new Integer(0));
        metaPk.setAno(new Integer(ano));
        meta.setMetaId(metaPk);
        meta.setProtegido(new Boolean(false));
      }
      
      metas.add(meta);
      ano++;
    }
    
    return metas;
  }
  
  public List getMetasAnualesParciales(Long indicadorId, Long planId, Byte frecuencia, Integer anoDesde, Integer anoHasta, Boolean acumular)
  {
    List metasAnualesParciales = new ArrayList();
    List metasAnuales = getMetasAnuales(indicadorId, planId, anoDesde, anoHasta, acumular);
    
    try
    {
      for (Iterator iter = metasAnuales.iterator(); iter.hasNext();)
      {
        Meta metaAnual = (Meta)iter.next();
        metasAnualesParciales.add(getMetaAnualParciales(indicadorId, planId, frecuencia, metaAnual, acumular));
      }
    }
    catch (Exception localException) {}
    


    return metasAnualesParciales;
  }
  
  public Meta getValorInicial(Long indicadorId, Long planId)
  {
    Meta meta = null;
    
    String sql = "from Meta meta where meta.metaId.indicadorId = :indicadorId and meta.metaId.planId = :planId";
    sql = sql + " and meta.metaId.serieId = " + SerieTiempo.getSerieValorInicialId() + " and meta.metaId.tipo = " + TipoMeta.getTipoMetaValorInicial();
    Query query = session.createQuery(sql);
    
    if (indicadorId != null)
      query.setLong("indicadorId", indicadorId.longValue());
    if (planId != null) {
      query.setLong("planId", planId.longValue());
    }
    if (query.list().size() > 0) {
      meta = (Meta)query.list().get(0);
    }
    if (meta == null)
    {
      meta = new Meta();
      MetaPK metaPk = new MetaPK();
      metaPk.setIndicadorId(indicadorId);
      metaPk.setPlanId(planId);
      metaPk.setSerieId(SerieTiempo.getSerieValorInicialId());
      metaPk.setTipo(TipoMeta.getTipoMetaValorInicial());
      meta.setMetaId(metaPk);
      meta.setProtegido(new Boolean(false));
    }
    
    return meta;
  }
  
  public int deleteValorInicial(Meta valorInicial)
  {
    int resultado = 10000;
    
    String sql = "delete from Meta meta where meta.metaId.planId = :planId and meta.metaId.indicadorId = :indicadorId and meta.metaId.serieId = " + SerieTiempo.getSerieValorInicialId() + " and meta.metaId.tipo = " + TipoMeta.getTipoMetaValorInicial();
    Query consulta = session.createQuery(sql);
    
    consulta.setLong("planId", valorInicial.getMetaId().getPlanId().longValue());
    consulta.setLong("indicadorId", valorInicial.getMetaId().getIndicadorId().longValue());
    
    consulta.executeUpdate();
    
    return resultado;
  }
  
  private MetaAnualParciales getMetaAnualParciales(Long indicadorId, Long planId, Byte frecuencia, Meta metaAnual, Boolean acumular) throws Exception
  {
    String sql = "from Meta meta where meta.metaId.indicadorId = :indicadorId and meta.metaId.planId = :planId";
    sql = sql + " and meta.metaId.serieId = " + SerieTiempo.getSerieMetaId() + " and meta.metaId.tipo = " + TipoMeta.getTipoMetaParcial();
    sql = sql + " and meta.metaId.ano = :ano";
    sql = sql + " order by meta.metaId.ano, meta.metaId.periodo";
    
    Locale currentLocale = new Locale("en", "US");
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
    DecimalFormat decimalformat = (DecimalFormat)numberFormatter;
    decimalformat.applyPattern("0.0000000000");
    
    Query query = session.createQuery(sql);
    
    if (indicadorId != null)
      query.setLong("indicadorId", indicadorId.longValue());
    if (planId != null)
      query.setLong("planId", planId.longValue());
    if (metaAnual.getMetaId().getAno() != null) {
      query.setInteger("ano", metaAnual.getMetaId().getAno().intValue());
    }
    List metasParciales = new ArrayList();
    
    Iterator metasParcialesExistentes = query.list().iterator();
    
    int periodo = 1;
    int periodoHasta = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), metaAnual.getMetaId().getAno().intValue());
    boolean getExistente = metasParcialesExistentes.hasNext();
    Meta metaParcial = null;
    Meta metaParcialExistente = null;
    Double valor = Double.valueOf(0.0D);
    
    while (periodo <= periodoHasta)
    {
      if (getExistente)
        metaParcialExistente = (Meta)metasParcialesExistentes.next();
      if (metaParcialExistente != null)
      {
        if (metaParcialExistente.getValor() != null)
        {
          if (acumular.booleanValue())
          {
            valor = Double.valueOf(valor.doubleValue() + metaParcialExistente.getValor().doubleValue());
            metaParcialExistente.setValor(valor);
          }
          metaParcialExistente.setValorGrande(new BigDecimal(metaParcialExistente.getValor().doubleValue()));
          metaParcialExistente.setValor(new Double(VgcFormatter.parsearNumeroFormateado(metaParcialExistente.getValor().toString())));
          metaParcialExistente.setValorString(decimalformat.format(metaParcialExistente.getValor()));
        }
        
        if (metaParcialExistente.getMetaId().getPeriodo().intValue() == periodo)
        {
          metaParcial = metaParcialExistente;
          getExistente = metasParcialesExistentes.hasNext();
        }
        else
        {
          metaParcial = null;
          getExistente = false;
        }
      }
      else
      {
        metaParcial = null;
        getExistente = false;
      }
      
      if (metaParcial == null)
      {
        metaParcial = new Meta();
        MetaPK metaPk = new MetaPK();
        metaPk.setIndicadorId(indicadorId);
        metaPk.setPlanId(planId);
        metaPk.setSerieId(SerieTiempo.getSerieMetaId());
        metaPk.setTipo(TipoMeta.getTipoMetaParcial());
        metaPk.setAno(metaAnual.getMetaId().getAno());
        metaPk.setPeriodo(new Integer(periodo));
        metaParcial.setMetaId(metaPk);
        metaParcial.setProtegido(new Boolean(false));
      }
      
      metasParciales.add(metaParcial);
      periodo++;
    }
    
    MetaAnualParciales metaAnualParciales = new MetaAnualParciales();
    metaAnualParciales.setMetaAnual(metaAnual);
    metaAnualParciales.setMetasParciales(metasParciales);
    
    metaAnualParciales.setNumeroPeriodos(Integer.valueOf(metasParciales.size()));
    
    return metaAnualParciales;
  }
  
  public List getMetasParcialesComoMediciones(Byte frecuenciaOrigen, Byte frecuenciaDestino, Boolean acumular, Byte tipoFuncionIndicador, Boolean valorInicialCero, Byte tipoCorteIndicador, Long indicadorId, 
          Long planId, Integer anoDesde, Integer periodoDesde, Integer anoHasta, Integer periodoHasta)
  {
      List medicionesResultado = new ArrayList();
      int periodoHastaInterno;
      int periodoDesdeInterno;
      if(frecuenciaOrigen.byteValue() == frecuenciaDestino.byteValue())
      {
          periodoDesdeInterno = periodoDesde.intValue();
          periodoHastaInterno = periodoHasta.intValue();
      } else
      {
          periodoDesdeInterno = PeriodoUtil.transformarPeriodoPorFrecuencia(frecuenciaOrigen.byteValue(), frecuenciaDestino.byteValue(), periodoDesde.intValue(), false);
          periodoHastaInterno = PeriodoUtil.transformarPeriodoPorFrecuencia(frecuenciaOrigen.byteValue(), frecuenciaDestino.byteValue(), periodoHasta.intValue(), false);
      }
      List mediciones = null;
      if(acumular.booleanValue())
          mediciones = getMetasParcialesComoMediciones(indicadorId, planId, frecuenciaDestino, tipoFuncionIndicador, anoDesde, new Integer(1), anoHasta, new Integer(periodoHastaInterno));
      else
          mediciones = getMetasParcialesComoMediciones(indicadorId, planId, frecuenciaDestino, tipoFuncionIndicador, anoDesde, new Integer(periodoDesdeInterno), anoHasta, new Integer(periodoHastaInterno));
      if(frecuenciaOrigen.byteValue() != frecuenciaDestino.byteValue() || acumular.booleanValue())
      {
          double total = 0.0D;
          boolean hayTotal = true;
          if(acumular.booleanValue())
          {
              for(Iterator iter = mediciones.iterator(); iter.hasNext();)
              {
                  Medicion medicion = (Medicion)iter.next();
                  if(medicion.getMedicionId().getPeriodo().intValue() < periodoDesdeInterno && medicion.getMedicionId().getAno().intValue() == anoHasta.intValue())
                      if(medicion.getValor() != null && hayTotal)
                          total += medicion.getValor().doubleValue();
                      else
                          hayTotal = false;
              }

              if(!hayTotal)
                  total = 0.0D;
          }
          int ano = anoDesde.intValue();
          int periodo = periodoDesde.intValue();
          int ciclo = 0;
          int anoAuxiliar = ano;
          int multiploFrecuencia = PeriodoUtil.getNumeroPeriodosPorFrecuenciaEnFrecuencia(frecuenciaOrigen.intValue(), frecuenciaDestino.intValue());
          int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuenciaDestino.byteValue(), ano);
          for(Iterator iter = mediciones.iterator(); iter.hasNext();)
          {
              Medicion medicion = (Medicion)iter.next();
              if(medicion.getMedicionId().getPeriodo().intValue() >= periodoDesdeInterno || medicion.getMedicionId().getAno().intValue() != anoDesde.intValue())
              {
                  ciclo++;
                  if(medicion.getMedicionId().getAno().intValue() != anoAuxiliar && valorInicialCero.booleanValue())
                  {
                      anoAuxiliar = medicion.getMedicionId().getAno().intValue();
                      total = 0.0D;
                  }
                  if(medicion.getValor() != null && hayTotal)
                      total += medicion.getValor().doubleValue();
                  else
                      hayTotal = false;
                  if(ciclo == multiploFrecuencia)
                  {
                      ciclo = 0;
                      Medicion medicionNueva = new Medicion();
                      MedicionPK medicionPk = new MedicionPK();
                      medicionNueva.setMedicionId(medicionPk);
                      medicionNueva.getMedicionId().setAno(new Integer(ano));
                      medicionNueva.getMedicionId().setPeriodo(new Integer(periodo));
                      if(tipoCorteIndicador.byteValue() == TipoCorte.getTipoCorteTransversal().byteValue())
                          medicionNueva.setValor(medicion.getValor());
                      else
                      if(tipoCorteIndicador.byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue())
                          if(esMetaAnualAcumulada(indicadorId, planId, frecuenciaDestino, new Integer(ano)))
                          {
                              medicionNueva.setValor(new Double(total));
                          } else
                          {
                              medicionNueva.setValor(medicion.getValor());
                              total = 0.0D;
                          }
                      medicionesResultado.add(medicionNueva);
                      if(!acumular.booleanValue())
                          total = 0.0D;
                      if(++periodo > numeroMaximoPeriodos)
                      {
                          ano++;
                          periodo = 1;
                          if(ano > anoHasta.intValue())
                              return medicionesResultado;
                      }
                      if(ano == anoHasta.intValue() && periodo > periodoHasta.intValue())
                          return medicionesResultado;
                  }
              }
          }

      } else
      {
          medicionesResultado = mediciones;
      }
      return medicionesResultado;
  }
  
  private boolean esMetaAnualAcumulada(Long indicadorId, Long planId, Byte frecuencia, Integer ano)
  {
    boolean esMetaAnualAcumulada = false;
    
    List metasAnuales = getMetasAnuales(indicadorId, planId, ano, ano, Boolean.valueOf(false));
    
    if (metasAnuales.size() > 0)
    {
      double valorMetaAnual = ((Meta)metasAnuales.get(0)).getValor().doubleValue();
      
      String sql = "from Meta meta where meta.metaId.indicadorId = :indicadorId and meta.metaId.planId = :planId";
      sql = sql + " and meta.metaId.serieId = " + SerieTiempo.getSerieMetaId() + " and meta.metaId.tipo = " + TipoMeta.getTipoMetaParcial();
      sql = sql + " and meta.metaId.ano = :ano and meta.metaId.periodo = :periodo";
      
      Query query = session.createQuery(sql);
      
      query.setLong("indicadorId", indicadorId.longValue());
      query.setLong("planId", planId.longValue());
      query.setInteger("ano", ano.intValue());
      query.setInteger("periodo", PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano.intValue()));
      
      List metasParciales = query.list();
      
      if (metasParciales.size() > 0)
      {
        double valorMetaParcial = ((Meta)metasParciales.get(0)).getValor().doubleValue();
        
        if (valorMetaAnual != valorMetaParcial) {
          esMetaAnualAcumulada = true;
        }
      }
    }
    return esMetaAnualAcumulada;
  }
  
  private List getMetasParcialesComoMediciones(Long indicadorId, Long planId, Byte frecuencia, Byte tipoFuncionIndicador, Integer anoDesde, Integer periodoDesde, Integer anoHasta, Integer periodoHasta) {
    List mediciones = new ArrayList();
    
    if (anoDesde.intValue() == anoHasta.intValue()) {
      MetaAnualParciales metaAnualParciales = (MetaAnualParciales)getMetasAnualesParciales(indicadorId, planId, frecuencia, anoDesde, anoHasta, Boolean.valueOf(false)).get(0);
      List metasParciales = metaAnualParciales.getMetasParciales();
      for (int periodo = periodoDesde.intValue(); periodo <= periodoHasta.intValue(); periodo++) {
        Meta metaParcial = (Meta)metasParciales.get(periodo - 1);
        Medicion medicion = metaParcial.clonarComoMedicion();
        if ((medicion.getValor() == null) && 
          (tipoFuncionIndicador.byteValue() != TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue())) {
          Meta metaAnual = metaAnualParciales.getMetaAnual();
          medicion.getMedicionId().setAno(metaAnual.getMetaId().getAno());
          medicion.getMedicionId().setPeriodo(new Integer(periodo));
          medicion.setValor(metaAnual.getValor());
          medicion.setProtegido(metaAnual.getProtegido());
        }
        
        mediciones.add(medicion);
      }
    } else {
      for (int ano = anoDesde.intValue(); ano <= anoHasta.intValue(); ano++) {
        Integer anoTemp = new Integer(ano);
        MetaAnualParciales metaAnualParciales = (MetaAnualParciales)getMetasAnualesParciales(indicadorId, planId, frecuencia, anoTemp, anoTemp, Boolean.valueOf(false)).get(0);
        
        int periodoDesdeInterno = 0;
        int periodoHastaInterno = 0;
        
        if (ano == anoDesde.intValue()) {
          periodoDesdeInterno = periodoDesde.intValue();
          periodoHastaInterno = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
        } else if (ano == anoHasta.intValue()) {
          periodoDesdeInterno = 1;
          periodoHastaInterno = periodoHasta.intValue();
        } else {
          periodoDesdeInterno = 1;
          periodoHastaInterno = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
        }
        
        List metasParciales = metaAnualParciales.getMetasParciales();
        for (int periodo = periodoDesdeInterno; periodo <= periodoHastaInterno; periodo++) {
          Meta metaParcial = (Meta)metasParciales.get(periodo - 1);
          Medicion medicion = metaParcial.clonarComoMedicion();
          if (medicion.getValor() == null) {
            Meta metaAnual = metaAnualParciales.getMetaAnual();
            medicion.getMedicionId().setAno(metaAnual.getMetaId().getAno());
            medicion.getMedicionId().setPeriodo(new Integer(periodo));
            medicion.setValor(metaAnual.getValor());
            medicion.setProtegido(metaAnual.getProtegido());
          }
          mediciones.add(medicion);
        }
      }
    }
    

    return mediciones;
  }
  
  public List getMetasParcialesPorPeriodo(Long indicadorId, Byte frecuencia, Long planId, Integer ano, Byte tipoMeta) {
    return null;
  }
  
  private Byte getTipoCargaMeta(Byte tipoCargaMedicion, Long planId, Long indicadorId)
  {
    Byte tipoCargaMeta = tipoCargaMedicion;
    
    IndicadorPlanPK indicadorPlanPk = new IndicadorPlanPK();
    indicadorPlanPk.setIndicadorId(indicadorId);
    indicadorPlanPk.setPlanId(planId);
    IndicadorPlan indicadorPlan = (IndicadorPlan)load(IndicadorPlan.class, indicadorPlanPk);
    
    if (indicadorPlan != null) {
      tipoCargaMeta = indicadorPlan.getTipoMedicion();
    }
    return tipoCargaMeta;
  }
  
  public List getMetasParciales(Long indicadorId, Long planId, Byte frecuencia, Byte mesCierre, Byte corte, Byte tipoCargaMedicion, Byte tipoMeta, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta) {
    List metas = null;
    if (corte.byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()) {
      Byte tipoCargaMeta = getTipoCargaMeta(tipoCargaMedicion, planId, indicadorId);
      if ((tipoCargaMeta != null) && (tipoCargaMeta.byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())) {
        metas = getMetasParcialesAcumuladas(indicadorId, planId, frecuencia, mesCierre, tipoMeta, anoDesde, anoHasta, periodoDesde, periodoHasta);
      } else {
        metas = getMetasParciales(indicadorId, planId, frecuencia, tipoMeta, anoDesde, anoHasta, periodoDesde, periodoHasta);
      }
    } else {
      metas = getMetasParciales(indicadorId, planId, frecuencia, tipoMeta, anoDesde, anoHasta, periodoDesde, periodoHasta);
    }
    return metas;
  }
  
  private List getMetasParcialesAcumuladas(Long indicadorId, Long planId, Byte frecuencia, Byte mesCierre, Byte tipoMeta, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta)
  {
    String sql = "from Meta meta where meta.metaId.indicadorId = :indicadorId  and meta.metaId.planId = :planId and meta.metaId.tipo = " + tipoMeta.toString();
    
    int anoDesdeInterno = 0;
    int anoHastaInterno = 0;
    int periodoDesdeInterno = 0;
    int periodoHastaInterno = 0;
    int periodoDesdeCierre = 0;
    int periodoHastaCierre = 0;
    Byte mesInicio = null;
    List metas = new ArrayList();
    
    if (mesCierre != null) {
      mesInicio = PeriodoUtil.getMesInicio(mesCierre);
    } else {
      mesCierre = new Byte("12");
      mesInicio = new Byte("1");
    }
    if (anoDesde != null) {
      anoDesdeInterno = anoDesde.intValue();
    }
    if (anoHasta != null) {
      anoHastaInterno = anoHasta.intValue();
    }
    if (periodoDesde != null) {
      periodoDesdeInterno = periodoDesde.intValue();
    }
    if (periodoHasta != null) {
      periodoHastaInterno = periodoHasta.intValue();
    }
    
    if ((anoDesdeInterno > 0) && (anoHastaInterno > 0) && (periodoDesdeInterno > 0) && (periodoHastaInterno > 0)) {
      List metasExistentes = new ArrayList();
      boolean hayValorAcumuladoInicial = false;
      Double valorAcumuladoInicial = null;
      boolean hayMetas = false;
      double acumulado = 0.0D;
      
      for (int ano = anoDesdeInterno; ano <= anoHastaInterno; ano++) {
        acumulado = 0.0D;
        if (mesCierre.byteValue() == 12) {
          periodoDesdeCierre = 1;
          periodoHastaCierre = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
        } else {
          periodoDesdeCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesInicio.intValue(), ano - 1), frecuencia.byteValue());
          periodoHastaCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesCierre.intValue(), ano), frecuencia.byteValue());
        }
        String sqlInt = sql + " and meta.metaId.ano = :ano ";
        int anoAdicional = ano;
        int periodoCierre = 0;
        if (mesCierre.byteValue() != 12) {
          if (ano == anoDesdeInterno) {
            if (periodoDesdeInterno <= periodoHastaCierre) {
              anoAdicional = ano - 1;
              sqlInt = sqlInt + " or (meta.metaId.ano = :anoAdicional and meta.metaId.periodo >= :periodoCierre)";
            } else {
              sqlInt = sqlInt + " and meta.metaId.periodo >= :periodoCierre";
            }
          }
          else if (1 <= periodoHastaCierre) {
            anoAdicional = ano - 1;
            sqlInt = sqlInt + " or (meta.metaId.ano = :anoAdicional and meta.metaId.periodo >= :periodoCierre)";
          } else {
            sqlInt = sqlInt + " and meta.metaId.periodo >= :periodoCierre";
          }
          
          periodoCierre = periodoDesdeCierre;
        }
        sqlInt = sqlInt + " order by meta.metaId.ano asc, meta.metaId.periodo asc";
        
        Query query = session.createQuery(sqlInt);
        
        query.setLong("indicadorId", indicadorId.longValue());
        query.setLong("planId", planId.longValue());
        query.setInteger("ano", ano);
        if (anoAdicional != ano) {
          query.setInteger("anoAdicional", anoAdicional);
        }
        if (periodoCierre > 0) {
          query.setInteger("periodoCierre", periodoCierre);
        }
        
        List metasTemp = query.list();
        if (anoDesdeInterno == anoHastaInterno) {
          for (Iterator m = metasTemp.iterator(); m.hasNext();) {
            Meta metaTemp = (Meta)m.next();
            
            session.evict(metaTemp);
            if ((!hayMetas) && (hayValorAcumuladoInicial)) {
              valorAcumuladoInicial = new Double(acumulado);
            }
            acumulado += metaTemp.getValor().doubleValue();
            if ((metaTemp.getMetaId().getPeriodo().intValue() >= periodoDesdeInterno) && (metaTemp.getMetaId().getPeriodo().intValue() <= periodoHastaInterno)) {
              hayMetas = true;
              metaTemp.setValor(new Double(acumulado));
              metasExistentes.add(metaTemp);
            }
            hayValorAcumuladoInicial = true;
          }
        } else if (ano == anoDesdeInterno) {
          for (Iterator m = metasTemp.iterator(); m.hasNext();) {
            Meta metaTemp = (Meta)m.next();
            session.evict(metaTemp);
            if ((!hayMetas) && (hayValorAcumuladoInicial)) {
              valorAcumuladoInicial = new Double(acumulado);
            }
            acumulado += metaTemp.getValor().doubleValue();
            if (metaTemp.getMetaId().getPeriodo().intValue() >= periodoDesdeInterno) {
              hayMetas = true;
              metaTemp.setValor(new Double(acumulado));
              metasExistentes.add(metaTemp);
            }
            hayValorAcumuladoInicial = true;
          }
        } else if (ano == anoHastaInterno) {
          for (Iterator m = metasTemp.iterator(); m.hasNext();) {
            Meta metaTemp = (Meta)m.next();
            session.evict(metaTemp);
            acumulado += metaTemp.getValor().doubleValue();
            if (metaTemp.getMetaId().getPeriodo().intValue() <= periodoHastaInterno) {
              hayMetas = true;
              metaTemp.setValor(new Double(acumulado));
              metasExistentes.add(metaTemp);
            }
          }
        } else {
          for (Iterator m = metasTemp.iterator(); m.hasNext();) {
            Meta metaTemp = (Meta)m.next();
            session.evict(metaTemp);
            acumulado += metaTemp.getValor().doubleValue();
            hayMetas = true;
            metaTemp.setValor(new Double(acumulado));
            metasExistentes.add(metaTemp);
          }
        }
      }
      

      int ano = anoDesdeInterno;
      Iterator iterMetasExistentes = metasExistentes.iterator();
      Meta primeraMetaExistente = null;
      if (metasExistentes.size() > 0) {
        primeraMetaExistente = (Meta)metasExistentes.get(0);
      }
      boolean getExistente = iterMetasExistentes.hasNext();
      Meta meta = null;
      Meta metaExistente = null;
      
      while (ano <= anoHastaInterno)
      {
        int periodo = 1;
        if (ano == anoDesdeInterno) {
          periodo = periodoDesdeInterno;
        }
        int periodoMaximo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
        if (ano == anoHastaInterno) {
          periodoMaximo = periodoHastaInterno;
        }
        while (periodo <= periodoMaximo) {
          if (getExistente) {
            metaExistente = (Meta)iterMetasExistentes.next();
          }
          if (metaExistente != null) {
            if ((metaExistente.getMetaId().getAno().intValue() == ano) && (metaExistente.getMetaId().getPeriodo().intValue() == periodo)) {
              meta = metaExistente;
              getExistente = iterMetasExistentes.hasNext();
              metaExistente = null;
            } else {
              meta = null;
              getExistente = false;
            }
          } else {
            meta = null;
            getExistente = false;
          }
          if (meta == null) {
            meta = new Meta();
            MetaPK metaPk = new MetaPK();
            metaPk.setIndicadorId(indicadorId);
            metaPk.setPlanId(planId);
            metaPk.setSerieId(SerieTiempo.getSerieMetaId());
            metaPk.setAno(new Integer(ano));
            metaPk.setPeriodo(new Integer(periodo));
            meta.setMetaId(metaPk);
            if ((valorAcumuladoInicial != null) && (primeraMetaExistente != null) && (
              (ano < primeraMetaExistente.getMetaId().getAno().intValue()) || ((ano == primeraMetaExistente.getMetaId().getAno().intValue()) && (periodo < primeraMetaExistente.getMetaId().getPeriodo().intValue())))) {
              meta.setValor(valorAcumuladoInicial);
            }
            
            meta.setProtegido(new Boolean(false));
          }
          
          metas.add(meta);
          periodo++;
        }
        ano++;
      }
    }
    
    return metas;
  }
  
  private List getMetasParciales(Long indicadorId, Long planId, Byte frecuencia, Byte tipoMeta, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta) {
    String sql = "from Meta meta";
    
    if ((indicadorId != null) || (planId != null) || (anoDesde != null) || (anoHasta != null) || (periodoDesde != null) || (periodoHasta != null)) {
      sql = sql + " where ";
    }
    
    sql = sql + "meta.metaId.indicadorId = :indicadorId and meta.metaId.planId = :planId and meta.metaId.tipo = " + tipoMeta.toString();
    
    if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null) && (anoDesde.intValue() == anoHasta.intValue()))
    {
      sql = sql + " and (" + "((meta.metaId.periodo >= :periodoDesde and meta.metaId.ano = :anoDesde) and " + "(meta.metaId.periodo <= :periodoHasta and meta.metaId.ano = :anoHasta)) ";
      sql = sql + ")";
    } else if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null))
    {
      sql = sql + " and ((meta.metaId.periodo >= :periodoDesde and meta.metaId.ano = :anoDesde) or " + "(meta.metaId.periodo <= :periodoHasta and meta.metaId.ano = :anoHasta) " + " or (meta.metaId.ano > :anoDesde and meta.metaId.ano < :anoHasta))";
    } else if ((anoDesde != null) && (periodoDesde != null)) {
      sql = sql + " and (" + "(meta.metaId.periodo >= :periodoDesde and meta.metaId.ano = :anoDesde) or " + "(meta.metaId.ano > :anoDesde)" + ")";
    } else if ((anoHasta != null) && (periodoHasta != null)) {
      sql = sql + " and (" + "(meta.metaId.periodo <= :periodoHasta and meta.metaId.ano = :anoHasta) or " + "(meta.metaId.ano < :anoHasta)" + ")";
    } else if ((anoDesde != null) && (anoHasta != null)) {
      sql = sql + " and (meta.metaId.ano >= :anoDesde and meta.metaId.ano <= :anoHasta)";
    } else if (anoDesde != null) {
      sql = sql + " and meta.metaId.ano >= :anoDesde";
    } else if (anoHasta != null) {
      sql = sql + " and meta.metaId.ano <= :anoHasta";
    }
    
    sql = sql + " order by meta.metaId.indicadorId, meta.metaId.planId, meta.metaId.ano, meta.metaId.periodo";
    
    Query query = session.createQuery(sql);
    
    if (indicadorId != null) {
      query.setLong("indicadorId", indicadorId.longValue());
    }
    if (planId != null) {
      query.setLong("planId", planId.longValue());
    }
    
    if (anoDesde != null) {
      query.setInteger("anoDesde", anoDesde.intValue());
    }
    
    if (anoHasta != null) {
      query.setInteger("anoHasta", anoHasta.intValue());
    }
    
    if (periodoDesde != null) {
      query.setInteger("periodoDesde", periodoDesde.intValue());
    }
    
    if (periodoHasta != null) {
      query.setInteger("periodoHasta", periodoHasta.intValue());
    }
    
    List metasExistentes = query.list();
    List metas = new ArrayList();
    
    if ((anoDesde != null) && (periodoDesde != null) && (anoHasta != null) && (periodoHasta != null) && (frecuencia != null))
    {
      int ano = anoDesde.intValue();
      Iterator iterMetasExistentes = metasExistentes.iterator();
      boolean getExistente = iterMetasExistentes.hasNext();
      Meta meta = null;
      Meta metaExistente = null;
      
      while (ano <= anoHasta.intValue())
      {
        int periodo = 1;
        if (ano == anoDesde.intValue()) {
          periodo = periodoDesde.intValue();
        }
        int periodoMaximo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
        if (ano == anoHasta.intValue()) {
          periodoMaximo = periodoHasta.intValue();
        }
        while (periodo <= periodoMaximo) {
          if (getExistente) {
            metaExistente = (Meta)iterMetasExistentes.next();
          }
          if (metaExistente != null) {
            if ((metaExistente.getMetaId().getAno().intValue() == ano) && (metaExistente.getMetaId().getPeriodo().intValue() == periodo)) {
              meta = metaExistente;
              getExistente = iterMetasExistentes.hasNext();
              metaExistente = null;
            } else {
              meta = null;
              getExistente = false;
            }
          } else {
            meta = null;
            getExistente = false;
          }
          if (meta == null) {
            meta = new Meta();
            MetaPK metaPk = new MetaPK();
            metaPk.setIndicadorId(indicadorId);
            metaPk.setPlanId(SerieTiempo.getSerieMetaId());
            metaPk.setAno(new Integer(ano));
            metaPk.setPeriodo(new Integer(periodo));
            meta.setMetaId(metaPk);
            meta.setProtegido(new Boolean(false));
          }
          
          metas.add(meta);
          periodo++;
        }
        ano++;
      }
    } else {
      metas = metasExistentes;
    }
    
    return metas;
  }
  
  public Meta getValorInicial(Long indicadorId, Long planId, Integer ano, Integer periodo) {
    String sql = "from Meta meta where meta.metaId.indicadorId = :indicadorId and meta.metaId.planId = :planId and meta.metaId.ano = :ano and meta.metaId.periodo = :periodo and meta.metaId.tipo = " + TipoMeta.getTipoMetaValorInicial().toString();
    sql = sql + " order by meta.metaId.indicadorId, meta.metaId.planId, meta.metaId.ano, meta.metaId.periodo";
    
    Query query = session.createQuery(sql);
    query.setLong("indicadorId", indicadorId.longValue());
    query.setLong("planId", planId.longValue());
    query.setInteger("ano", ano.intValue());
    query.setInteger("periodo", periodo.intValue());
    List metas = query.list();
    Meta meta = null;
    if (metas.size() > 0) {
      meta = (Meta)metas.get(0);
    }
    return meta;
  }
  
  public Meta getUltimoValorInicial(Long indicadorId, Long planId) {
    String sql = "from Meta meta where meta.metaId.indicadorId = :indicadorId and meta.metaId.planId = :planId and meta.metaId.tipo = " + TipoMeta.getTipoMetaValorInicial().toString();
    sql = sql + " order by meta.metaId.ano desc, meta.metaId.periodo desc";
    
    Query query = session.createQuery(sql);
    query.setLong("indicadorId", indicadorId.longValue());
    query.setLong("planId", planId.longValue());
    List metas = query.list();
    Meta meta = null;
    if (metas.size() > 0) {
      meta = (Meta)metas.get(0);
    }
    return meta;
  }
  
  public int deleteMetas(Long indicadorId, Long planId, Byte tipoMeta, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal) {
    String hqlUpdate = "delete from Meta meta where meta.metaId.indicadorId = :indicadorId";
    boolean continuar = false;
    
    if ((anoInicio != null) && (anoFinal != null) && (periodoInicio != null) && (periodoFinal != null)) {
      if (anoInicio.intValue() != anoFinal.intValue()) {
        hqlUpdate = hqlUpdate + " and (((meta.metaId.ano = :anoInicio) and (meta.metaId.periodo >= :periodoInicio))";
        hqlUpdate = hqlUpdate + " or ((meta.metaId.ano > :anoInicio) and (meta.metaId.ano < :anoFinal))";
        hqlUpdate = hqlUpdate + " or ((meta.metaId.ano = :anoFinal) and (meta.metaId.periodo <= :periodoFinal)))";
      } else {
        hqlUpdate = hqlUpdate + " and ((meta.metaId.ano = :anoInicio) and (meta.metaId.periodo >= :periodoInicio)";
        hqlUpdate = hqlUpdate + " and (meta.metaId.ano = :anoFinal) and (meta.metaId.periodo <= :periodoFinal))";
      }
      
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) {
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and meta.metaId.ano >= :anoInicio";
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and meta.metaId.ano <= :anoFinal";
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and meta.metaId.ano >= :anoInicio";
      hqlUpdate = hqlUpdate + " and meta.metaId.ano <= :anoFinal";
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio != null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and (((meta.metaId.ano = :anoInicio) and (meta.metaId.periodo >= :periodoInicio))";
      hqlUpdate = hqlUpdate + " or (meta.metaId.ano > :anoInicio))";
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal != null)) {
      hqlUpdate = hqlUpdate + " and (((meta.metaId.ano = :anoFinal) and (meta.metaId.periodo <= :periodoFinal))";
      hqlUpdate = hqlUpdate + " or (meta.metaId.ano < :anoFinal))";
      continuar = true;
    }
    
    if (continuar) {
      if (planId != null) {
        hqlUpdate = hqlUpdate + " and meta.metaId.planId = :planId";
      }
      if (tipoMeta != null) {
        hqlUpdate = hqlUpdate + " and meta.metaId.tipo = :tipo";
      }
      Query update = session.createQuery(hqlUpdate).setLong("indicadorId", indicadorId.longValue());
      
      if (planId != null) {
        update.setLong("planId", planId.longValue());
      }
      if (tipoMeta != null) {
        update.setByte("tipo", tipoMeta.byteValue());
      }
      
      if (anoInicio != null) {
        update.setInteger("anoInicio", anoInicio.intValue());
      }
      if (anoFinal != null) {
        update.setInteger("anoFinal", anoFinal.intValue());
      }
      if (periodoInicio != null) {
        update.setInteger("periodoInicio", periodoInicio.intValue());
      }
      if (periodoFinal != null) {
        update.setInteger("periodoFinal", periodoFinal.intValue());
      }
      
      update.executeUpdate();
    }
    
    return 10000;
  }
  
  public List getAnosConMetaParcial(Long indicadorId, Long planId)
  {
    String sql = "select distinct meta.metaId.ano from Meta meta where meta.metaId.indicadorId = :indicadorId and meta.metaId.planId = :planId and meta.metaId.tipo = " + TipoMeta.getTipoMetaParcial().toString();
    sql = sql + " order by meta.metaId.ano asc";
    
    Query query = session.createQuery(sql);
    query.setLong("indicadorId", indicadorId.longValue());
    query.setLong("planId", planId.longValue());
    
    return query.list();
  }
  
  public List<Meta> getMetas(Long indicadorId, Long planId)
  {
    String sql = "from Meta meta where meta.metaId.indicadorId = :indicadorId and meta.metaId.planId = :planId";
    sql = sql + " order by meta.metaId.serieId, meta.metaId.tipo, meta.metaId.ano, meta.metaId.periodo";
    
    Query query = session.createQuery(sql);
    
    if (indicadorId != null) {
      query.setLong("indicadorId", indicadorId.longValue());
    }
    if (planId != null) {
      query.setLong("planId", planId.longValue());
    }
    return query.list();
  }
  
  public List<Meta> getMetasEjecutar(Long indicadorId, Long planId, Integer ano)
  {
	Query query = null;  
	  
	if(indicadorId == null && planId == null){
		String sql = "from Meta meta where meta.metaId.ano = :ano";
	    sql = sql + " order by meta.metaId.indicadorId, meta.metaId.planId, meta.metaId.serieId, meta.metaId.tipo, meta.metaId.periodo";
	    
	    query = session.createQuery(sql);
	    
	    if (ano != null){
	      query.setLong("ano", ano.intValue());	
	    }

	}else if(indicadorId == null){
		String sql = "from Meta meta where meta.metaId.planId = :planId and meta.metaId.ano = :ano";
	    sql = sql + " order by meta.metaId.indicadorId, meta.metaId.serieId, meta.metaId.tipo, meta.metaId.periodo";
	    
	    query = session.createQuery(sql);
	    
	    if (planId != null) {
	      query.setLong("planId", planId.longValue());
	    }
	    if (ano != null){
	      query.setLong("ano", ano.intValue());	
	    }
	}
	else{
	    String sql = "from Meta meta where meta.metaId.indicadorId = :indicadorId and meta.metaId.planId = :planId and meta.metaId.ano = :ano";
	    sql = sql + " order by meta.metaId.serieId, meta.metaId.tipo, meta.metaId.periodo";
	    
	    query = session.createQuery(sql);
	    
	    if (indicadorId != null) {
	      query.setLong("indicadorId", indicadorId.longValue());
	    }
	    if (planId != null) {
	      query.setLong("planId", planId.longValue());
	    }
	    if (ano != null){
	      query.setLong("ano", ano.intValue());	
	    }
	}
    return query.list();
	
  }
  /*
  
  public int protegerMetas(Map<?, ?> filtros)
  {
    return protegerMetas(filtros, Boolean.valueOf(false));
  }
  
  public int protegerMetas(Map<?, ?> filtros, Boolean liberar)
  {
    String updateSentence = "";
    Integer anoDesde = null;
    Integer anoHasta = null;
    Integer periodoDesde = null;
    Integer periodoHasta = null;
    
    if (filtros.containsKey("fechaFinal")) {
      updateSentence = "update SerieIndicador set fechaBloqueo= :fecha where 1=1 ";
    } else {
      updateSentence = "update Medicion set protegido= :value where 1=1 ";
    }
    if (filtros != null)
    {
      Iterator<?> iter = filtros.keySet().iterator();
      String fieldName = null;
      
      while (iter.hasNext())
      {
        fieldName = (String)iter.next();
        if (fieldName.equals("indicadorId")) {
          updateSentence = updateSentence + " and id.indicadorId=" + (Long)filtros.get(fieldName);
        } else if (fieldName.equals("serieId")) {
          updateSentence = updateSentence + " and id.serieId=" + (Long)filtros.get(fieldName);
        } else if (fieldName.equals("indicadores")) {
          updateSentence = updateSentence + " and id.indicadorId" + getCondicionConsulta(filtros.get(fieldName), "=");
        } else if (fieldName.equals("series"))
        {
          List parametros = (List)filtros.get(fieldName);
          String listSeries = " and id.serieId in (";
          for (Iterator i = parametros.iterator(); i.hasNext();)
          {
            Long serie = (Long)i.next();
            listSeries = listSeries + serie.toString() + ",";
          }
          listSeries = listSeries.substring(0, listSeries.length() - 1) + ") ";
          updateSentence = updateSentence + listSeries;
        }
        else if (fieldName.equals("anoDesde")) {
          anoDesde = (Integer)filtros.get(fieldName);
        } else if (fieldName.equals("anoHasta")) {
          anoHasta = (Integer)filtros.get(fieldName);
        } else if (fieldName.equals("periodoDesde")) {
          periodoDesde = (Integer)filtros.get(fieldName);
        } else if (fieldName.equals("periodoHasta")) {
          periodoHasta = (Integer)filtros.get(fieldName);
        }
      }
      if ((anoDesde != null) || (anoHasta != null))
      {
        if ((periodoHasta == null) && (anoHasta == null))
        {
          if (periodoDesde == null) {
            updateSentence = updateSentence + " and id.ano>=" + anoDesde;
          } else {
            updateSentence = updateSentence + " and ((id.ano>" + anoDesde + ") or (id.ano=" + anoDesde + " and id.periodo>=" + periodoDesde + " ))";
          }
        } else if ((periodoDesde == null) && (anoDesde == null))
        {
          if (periodoHasta == null) {
            updateSentence = updateSentence + " and id.ano<=" + anoHasta;
          } else {
            updateSentence = updateSentence + " and ((id.ano<" + anoHasta + ") or (id.ano=" + anoHasta + " and id.periodo<=" + periodoHasta + " ))";
          }
        } else if ((periodoDesde == null) && (periodoHasta == null)) {
          updateSentence = updateSentence + " and (id.ano>=" + anoDesde + " and id.ano<=" + anoHasta + ")";
        } else if ((periodoDesde != null) && (periodoHasta != null) && (anoDesde != null) && (anoHasta != null)) {
          updateSentence = updateSentence + " and (((id.ano>" + anoDesde + ") or (id.ano=" + anoDesde + " and id.periodo>=" + periodoDesde + ")) and ((id.ano<" + anoHasta + ") or (id.ano=" + anoHasta + " and id.periodo<=" + periodoHasta + ")))";
        } else if ((periodoHasta == null) && (anoDesde != null)) {
          updateSentence = updateSentence + " and (((id.ano>" + anoDesde + ") or (id.ano=" + anoDesde + " and id.periodo>=" + periodoDesde + ")) and (id.ano<=" + anoHasta + "))";
        } else if ((periodoDesde == null) && (anoHasta != null))
          updateSentence = updateSentence + " and ((id.ano>=" + anoDesde + ") and ((id.ano<" + anoHasta + ") or (id.ano=" + anoHasta + " and id.periodo<=" + periodoHasta + ")))";
      }
    }
    Query updateQuery;
    if (filtros.containsKey("fechaFinal")) {
      updateQuery = session.createQuery(updateSentence).setDate("fecha", (Date)filtros.get("fechaFinal"));
    } else {
      updateQuery = session.createQuery(updateSentence).setInteger("value", liberar.booleanValue() ? 0 : 1);
    }
    return updateQuery.executeUpdate();
  }
  */
  
  
}
