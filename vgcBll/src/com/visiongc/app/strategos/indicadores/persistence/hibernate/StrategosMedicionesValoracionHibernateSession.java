package com.visiongc.app.strategos.indicadores.persistence.hibernate;

import com.visiongc.app.strategos.indicadores.model.MedicionValoracion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesValoracionPersistenceSession;
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

public class StrategosMedicionesValoracionHibernateSession extends StrategosHibernateSession implements StrategosMedicionesValoracionPersistenceSession
{
  public StrategosMedicionesValoracionHibernateSession(Session session)
  {
    super(session);
  }
  
  public StrategosMedicionesValoracionHibernateSession(StrategosHibernateSession parentSession)
  {
    super(parentSession);
  }
  
  public List<MedicionValoracion> getMedicionesPorFrecuencia(Long indicadorId, Long serieId, Integer anoInicial, Integer anoFinal, Integer periodoInicial, Integer periodoFinal, Byte frecuenciaRequerida, Byte frecuenciaOriginal, Boolean acumular, Boolean acumuladoPorPeriodo)
  {
    List<MedicionValoracion> mediciones = new ArrayList();
    List<MedicionValoracion> medicionesPorFrecuencia = new ArrayList();
    int[] periodoInicioDesde = new int[1];
    int[] periodoFinalDesde = new int[1];
    int[] periodoInicioHasta = new int[1];
    int[] periodoFinalHasta = new int[1];
    
    PeriodoUtil.contencionPeriodosFrecuenciaEnFrecuencia(frecuenciaRequerida.intValue(), frecuenciaOriginal.intValue(), periodoInicial.intValue(), periodoFinal.intValue(), periodoInicioDesde, periodoFinalDesde, periodoInicioHasta, periodoFinalHasta);
    
    int periodoDesdeCiclo = periodoInicioDesde[0];
    int periodoHastaCiclo = periodoFinalHasta[0];
    int periodoFrecuenciaRequerida = periodoInicial.intValue();
    
    mediciones = getMedicionesPeriodo(indicadorId, serieId, anoInicial, anoFinal, new Integer(periodoDesdeCiclo), new Integer(periodoHastaCiclo));
    
    double total = 0.0D;
    double totalParcial = 0.0D;
    Double valorMedicion = Double.valueOf(0.0D);
    
    MedicionValoracion medicionSincronizada = new MedicionValoracion();
    int anoActual = anoInicial.intValue();
    MedicionValoracion medicion = new MedicionValoracion();
    boolean obtenerProximaMedicion = true;
    boolean hayMedicion = false;
    boolean medicionInsertada = false;
    int multiploPeriodosFrecuencia = periodoFinalHasta[0] - periodoInicioHasta[0] + 1;
    int contadorPeriodos = 1;
    int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuenciaRequerida.byteValue(), -1);
    
    if (mediciones != null)
    {
      for (Iterator<MedicionValoracion> i = mediciones.iterator(); (anoActual != anoFinal.intValue()) || (periodoFrecuenciaRequerida != periodoFinal.intValue()) || (contadorPeriodos <= multiploPeriodosFrecuencia);)
      {
        if (obtenerProximaMedicion)
        {
          if (i.hasNext()) {
            medicion = (MedicionValoracion)i.next();
          } else
            medicion = null;
          obtenerProximaMedicion = false;
        }
        
        if ((contadorPeriodos == 1) || (contadorPeriodos > multiploPeriodosFrecuencia))
        {
          if (contadorPeriodos > multiploPeriodosFrecuencia)
          {
            periodoFrecuenciaRequerida++;
            if (periodoFrecuenciaRequerida > numeroMaximoPeriodos)
            {
              periodoFrecuenciaRequerida = 1;
              anoActual++;
            }
          }
          
          PeriodoUtil.contencionPeriodosFrecuenciaEnFrecuencia(frecuenciaRequerida.intValue(), frecuenciaOriginal.intValue(), periodoFrecuenciaRequerida, periodoFrecuenciaRequerida, periodoInicioDesde, periodoFinalDesde, periodoInicioHasta, periodoFinalHasta);
          contadorPeriodos = 1;
        }
        
        if ((medicion != null) && (medicion.getMedicionId().getAno().intValue() == anoActual) && (medicion.getMedicionId().getPeriodo().intValue() >= periodoInicioDesde[0]) && (medicion.getMedicionId().getPeriodo().intValue() <= periodoFinalHasta[0]))
        {
          totalParcial = medicion.getValor().doubleValue();
          total += medicion.getValor().doubleValue();
          hayMedicion = true;
          if (medicion.getMedicionId().getPeriodo().intValue() == periodoFinalHasta[0])
          {
            if ((acumular.booleanValue()) || (acumuladoPorPeriodo.booleanValue())) {
              valorMedicion = Double.valueOf(total);
            } else {
              valorMedicion = Double.valueOf(totalParcial);
            }
            medicionSincronizada = new MedicionValoracion(new MedicionPK(indicadorId, medicion.getMedicionId().getAno(), new Integer(periodoFrecuenciaRequerida), serieId), new Double(valorMedicion.doubleValue()));
            medicionesPorFrecuencia.add(medicionSincronizada);
            medicionInsertada = true;
            hayMedicion = false;
            totalParcial = 0.0D;
            if (!acumuladoPorPeriodo.booleanValue()) {
              total = 0.0D;
            }
          }
          obtenerProximaMedicion = true;
        }
        
        if ((!medicionInsertada) && (contadorPeriodos == multiploPeriodosFrecuencia))
        {
          valorMedicion = null;
          if (hayMedicion)
          {
            if (acumular.booleanValue()) {
              valorMedicion = Double.valueOf(total);
            } else
              valorMedicion = Double.valueOf(totalParcial);
            hayMedicion = false;
          }
          
          boolean existeMedicion = false;
          for (Iterator<MedicionValoracion> iterAportes = medicionesPorFrecuencia.iterator(); iterAportes.hasNext();)
          {
            MedicionValoracion aporte = (MedicionValoracion)iterAportes.next();
            if ((aporte.getMedicionId().getIndicadorId().longValue() == indicadorId.longValue()) && 
              (aporte.getMedicionId().getSerieId().longValue() == serieId.longValue()) && 
              (aporte.getMedicionId().getAno().intValue() == anoActual) && 
              (aporte.getMedicionId().getPeriodo().intValue() == periodoFrecuenciaRequerida))
            {
              existeMedicion = true;
              break;
            }
          }
          
          if (!existeMedicion)
          {
            medicionSincronizada = new MedicionValoracion(new MedicionPK(indicadorId, new Integer(anoActual), new Integer(periodoFrecuenciaRequerida), serieId), valorMedicion);
            medicionesPorFrecuencia.add(medicionSincronizada);
          }
        }
        
        medicionInsertada = false;
        contadorPeriodos++;
      }
    }
    
    return medicionesPorFrecuencia;
  }
  
  public List<MedicionValoracion> getMedicionesPorFrecuencia(Long indicadorId, Long serieId, Boolean acumular, Boolean valorInicialCero, Byte tipoCorteIndicador, Byte frecuenciaOrigen, Byte frecuenciaDestino, Integer anoDesde, Integer periodoDesde, Integer anoHasta, Integer periodoHasta)
  {
    List<MedicionValoracion> medicionesResultado = new ArrayList();
    int periodoHastaInterno;
    int periodoDesdeInterno;
    if (frecuenciaOrigen.byteValue() == frecuenciaDestino.byteValue())
    {
      periodoDesdeInterno = periodoDesde.intValue();
      periodoHastaInterno = periodoHasta.intValue();
    }
    else
    {
      periodoDesdeInterno = PeriodoUtil.transformarPeriodoPorFrecuencia(frecuenciaOrigen.byteValue(), frecuenciaDestino.byteValue(), periodoDesde.intValue(), false);
      periodoHastaInterno = PeriodoUtil.transformarPeriodoPorFrecuencia(frecuenciaOrigen.byteValue(), frecuenciaDestino.byteValue(), periodoHasta.intValue(), false);
    }
    
    List<MedicionValoracion> mediciones = getMedicionesPeriodo(indicadorId, frecuenciaOrigen, serieId, anoDesde, anoHasta, new Integer(periodoDesdeInterno), new Integer(periodoHastaInterno));
    double total = 0.0D;
    boolean hayTotal = false;
    int ano = 0;
    int periodo = 0;
    int ciclo = 0;
    int anoAuxiliar = 0;
    int multiploFrecuencia = 0;
    int numeroMaximoPeriodos = 0;
    
    if ((frecuenciaOrigen.byteValue() != frecuenciaDestino.byteValue()) || (acumular.booleanValue()))
    {
      total = 0.0D;
      hayTotal = true;
      
      if (acumular.booleanValue())
      {
        for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
        {
          MedicionValoracion medicion = (MedicionValoracion)iter.next();
          if ((medicion.getMedicionId().getPeriodo().intValue() < periodoDesdeInterno) && (medicion.getMedicionId().getAno().intValue() == anoHasta.intValue()))
          {
            if ((medicion.getValor() != null) && (hayTotal)) {
              total += medicion.getValor().doubleValue();
            } else {
              hayTotal = false;
            }
          }
        }
        if (!hayTotal) {
          total = 0.0D;
        }
      }
    }
    ano = anoDesde.intValue();
    periodo = periodoDesde.intValue();
    ciclo = 0;
    anoAuxiliar = ano;
    
    multiploFrecuencia = PeriodoUtil.getNumeroPeriodosPorFrecuenciaEnFrecuencia(frecuenciaOrigen.intValue(), frecuenciaDestino.intValue());
    numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuenciaDestino.byteValue(), ano);
    
    for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
    {
      MedicionValoracion medicion = (MedicionValoracion)iter.next();
      if ((medicion.getMedicionId().getPeriodo().intValue() >= periodoDesdeInterno) || (medicion.getMedicionId().getAno().intValue() != anoDesde.intValue()))
      {
        ciclo++;
        if ((medicion.getMedicionId().getAno().intValue() != anoAuxiliar) && (valorInicialCero.booleanValue()))
        {
          anoAuxiliar = medicion.getMedicionId().getAno().intValue();
          total = 0.0D;
        }
        
        if ((medicion.getValor() != null) && (hayTotal)) {
          total += medicion.getValor().doubleValue();
        } else {
          hayTotal = false;
        }
        if (ciclo == multiploFrecuencia)
        {
          ciclo = 0;
          
          MedicionValoracion medicionNueva = new MedicionValoracion();
          MedicionPK medicionPk = new MedicionPK();
          medicionNueva.setMedicionId(medicionPk);
          medicionNueva.getMedicionId().setAno(new Integer(ano));
          medicionNueva.getMedicionId().setPeriodo(new Integer(periodo));
          
          if (tipoCorteIndicador.byteValue() == TipoCorte.getTipoCorteTransversal().byteValue()) {
            medicionNueva.setValor(medicion.getValor());
          } else if (tipoCorteIndicador.byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue())
            medicionNueva.setValor(new Double(total));
          medicionesResultado.add(medicionNueva);
          
          if (!acumular.booleanValue()) {
            total = 0.0D;
          }
          periodo++;
          if (periodo > numeroMaximoPeriodos)
          {
            ano++;
            periodo = 1;
            if (ano > anoHasta.intValue())
              break;
          } else {
            if ((ano == anoHasta.intValue()) && (periodo > periodoHasta.intValue()))
              break;
          }
        }
      }
    }
    return medicionesResultado;
  }
  
  public ListaMap getMedicionesPorFrecuenciaInterna(Long indicadorId, Long serieId, Integer anoInicial, Integer anoFinal, Integer periodoInicial, Integer periodoFinal, Byte frecuenciaRequerida, Byte frecuenciaOriginal, Boolean acumular)
  {
    List<MedicionValoracion> mediciones = new ArrayList();
    ListaMap medicionesPorFrecuencia = new ListaMap();
    int[] periodoInicioDesde = new int[1];
    int[] periodoFinalDesde = new int[1];
    int[] periodoInicioHasta = new int[1];
    int[] periodoFinalHasta = new int[1];
    
    PeriodoUtil.contencionPeriodosFrecuenciaEnFrecuencia(frecuenciaRequerida.intValue(), frecuenciaOriginal.intValue(), periodoInicial.intValue(), periodoFinal.intValue(), periodoInicioDesde, periodoFinalDesde, periodoInicioHasta, periodoFinalHasta);
    
    int periodoDesdeCiclo = periodoInicioDesde[0];
    int periodoHastaCiclo = periodoFinalHasta[0];
    
    int periodoFrecuenciaRequerida = 0;
    
    periodoFrecuenciaRequerida = periodoInicial.intValue();
    
    mediciones = getMedicionesPeriodo(indicadorId, serieId, anoInicial, anoFinal, new Integer(periodoDesdeCiclo), new Integer(periodoHastaCiclo));
    
    double total = 0.0D;
    double totalParcial = 0.0D;
    double valorMedicion = 0.0D;
    
    MedicionValoracion medicionSincronizada = new MedicionValoracion();
    int anoActual = anoInicial.intValue();
    MedicionValoracion medicion = new MedicionValoracion();
    boolean obtenerProximaMedicion = true;
    boolean medicionInsertada = false;
    int multiploPeriodosFrecuencia = periodoFinalHasta[0] - periodoInicioHasta[0] + 1;
    int contadorPeriodos = 1;
    int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuenciaRequerida.byteValue(), -1);
    
    if (mediciones != null)
    {
      for (Iterator<?> i = mediciones.iterator(); (anoActual != anoFinal.intValue()) || (periodoFrecuenciaRequerida != periodoFinal.intValue()) || (contadorPeriodos <= multiploPeriodosFrecuencia);)
      {
        if (obtenerProximaMedicion) {
          if (i.hasNext()) {
            medicion = (MedicionValoracion)i.next();
          } else {
            medicion = null;
          }
          obtenerProximaMedicion = false;
        }
        
        if ((contadorPeriodos == 1) || (contadorPeriodos > multiploPeriodosFrecuencia))
        {
          if (contadorPeriodos > multiploPeriodosFrecuencia) {
            periodoFrecuenciaRequerida++;
            
            if (periodoFrecuenciaRequerida > numeroMaximoPeriodos) {
              periodoFrecuenciaRequerida = 1;
              anoActual++;
            }
          }
          

          PeriodoUtil.contencionPeriodosFrecuenciaEnFrecuencia(frecuenciaRequerida.intValue(), frecuenciaOriginal.intValue(), periodoFrecuenciaRequerida, periodoFrecuenciaRequerida, periodoInicioDesde, periodoFinalDesde, periodoInicioHasta, periodoFinalHasta);
          contadorPeriodos = 1;
        }
        
        if ((medicion != null) && (medicion.getMedicionId().getAno().intValue() == anoActual) && (medicion.getMedicionId().getPeriodo().intValue() >= periodoInicioDesde[0]) && (medicion.getMedicionId().getPeriodo().intValue() <= periodoFinalHasta[0]))
        {
          totalParcial += medicion.getValor().doubleValue();
          total += medicion.getValor().doubleValue();
          
          if (medicion.getMedicionId().getPeriodo().intValue() == periodoFinalHasta[0])
          {
            if (acumular.booleanValue()) {
              valorMedicion = total;
            } else {
              valorMedicion = totalParcial;
            }
            
            medicionSincronizada = new MedicionValoracion(new MedicionPK(indicadorId, medicion.getMedicionId().getAno(), new Integer(periodoFrecuenciaRequerida), serieId), new Double(valorMedicion));
            
            medicionesPorFrecuencia.add(medicionSincronizada, medicionSincronizada.getMedicionId().getAno().toString() + "_" + medicionSincronizada.getMedicionId().getPeriodo().toString());
            
            medicionInsertada = true;
            
            totalParcial = 0.0D;
          }
          
          obtenerProximaMedicion = true;
        }
        
        if ((!medicionInsertada) && (contadorPeriodos == multiploPeriodosFrecuencia))
        {
          medicionSincronizada = new MedicionValoracion(new MedicionPK(indicadorId, new Integer(anoActual), new Integer(periodoFrecuenciaRequerida), serieId), null);
          
          medicionesPorFrecuencia.add(medicionSincronizada, medicionSincronizada.getMedicionId().getAno().toString() + "_" + medicionSincronizada.getMedicionId().getPeriodo().toString());
        }
        
        medicionInsertada = false;
        contadorPeriodos++;
      }
    }
    
    return medicionesPorFrecuencia;
  }
  
  public int deleteMediciones(Long indicadorId, Long serieId, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal) {
    String hqlUpdate = "delete Medicion where id.indicadorId = :indicadorId";
    
    boolean continuar = false;
    
    if ((anoInicio != null) && (anoFinal != null) && (periodoInicio != null) && (periodoFinal != null)) {
      if (anoInicio.intValue() != anoFinal.intValue()) {
        hqlUpdate = hqlUpdate + " and (((id.ano = :anoInicio) and (id.periodo >= :periodoInicio))";
        hqlUpdate = hqlUpdate + " or ((id.ano > :anoInicio) and (id.ano < :anoFinal))";
        hqlUpdate = hqlUpdate + " or ((id.ano = :anoFinal) and (id.periodo <= :periodoFinal)))";
      } else {
        hqlUpdate = hqlUpdate + " and ((id.ano = :anoInicio) and (id.periodo >= :periodoInicio)";
        hqlUpdate = hqlUpdate + " and (id.ano = :anoFinal) and (id.periodo <= :periodoFinal))";
      }
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) {
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and id.ano >= :anoInicio";
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and id.ano <= :anoFinal";
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and id.ano >= :anoInicio";
      hqlUpdate = hqlUpdate + " and id.ano <= :anoFinal";
      continuar = true;
    } else if ((anoInicio != null) && (anoFinal == null) && (periodoInicio != null) && (periodoFinal == null)) {
      hqlUpdate = hqlUpdate + " and (((id.ano = :anoInicio) and (id.periodo >= :periodoInicio))";
      hqlUpdate = hqlUpdate + " or (id.ano > :anoInicio))";
      continuar = true;
    } else if ((anoInicio == null) && (anoFinal != null) && (periodoInicio == null) && (periodoFinal != null)) {
      hqlUpdate = hqlUpdate + " and (((id.ano = :anoFinal) and (id.periodo <= :periodoFinal))";
      hqlUpdate = hqlUpdate + " or (id.ano < :anoFinal))";
      continuar = true;
    }
    
    if (continuar) {
      if (serieId != null) {
        hqlUpdate = hqlUpdate + " and id.serieId = :serieId";
      }
      
      Query update = session.createQuery(hqlUpdate).setLong("indicadorId", indicadorId.longValue());
      
      if (serieId != null) {
        update.setLong("serieId", serieId.longValue());
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
  
  public List<MedicionValoracion> getMedicionesPeriodo(Long indicadorId, Long serieId, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal)
  {
    return getMedicionesPeriodo(indicadorId, null, serieId, anoInicio, anoFinal, periodoInicio, periodoFinal);
  }
  
  public List<MedicionValoracion> getMedicionesPeriodoExactas(Long indicadorId, Long serieId, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal)
  {
    String sql = "from Medicion med";
    
    if ((indicadorId != null) || (serieId != null) || (anoInicio != null) || (anoFinal != null) || (periodoInicio != null) || (periodoFinal != null)) {
      sql = sql + " where ";
    }
    sql = sql + "med.medicionId.indicadorId = :indicadorId ";
    if (serieId != null) {
      sql = sql + " and med.medicionId.serieId = :serieId ";
    }
    if ((anoInicio != null) && (periodoInicio != null) && (anoFinal != null) && (periodoFinal != null) && (anoInicio.intValue() == anoFinal.intValue()))
    {
      sql = sql + " and (" + "((med.medicionId.periodo >= :periodoInicio and med.medicionId.ano = :anoInicio) and " + "(med.medicionId.periodo <= :periodoFinal and med.medicionId.ano = :anoFinal)) ";
      sql = sql + ")";
    }
    else if ((anoInicio != null) && (periodoInicio != null) && (anoFinal != null) && (periodoFinal != null)) {
      sql = sql + " and ((med.medicionId.periodo >= :periodoInicio and med.medicionId.ano = :anoInicio) or " + "(med.medicionId.periodo <= :periodoFinal and med.medicionId.ano = :anoFinal) " + " or (med.medicionId.ano > :anoInicio and med.medicionId.ano < :anoFinal))";
    } else if ((anoInicio != null) && (periodoInicio != null)) {
      sql = sql + " and (" + "(med.medicionId.periodo >= :periodoInicio and med.medicionId.ano = :anoInicio) or " + "(med.medicionId.ano > :anoInicio)" + ")";
    } else if ((anoFinal != null) && (periodoFinal != null)) {
      sql = sql + " and (" + "(med.medicionId.periodo <= :periodoFinal and med.medicionId.ano = :anoFinal) or " + "(med.medicionId.ano < :anoFinal)" + ")";
    } else if ((anoInicio != null) && (anoFinal != null)) {
      sql = sql + " and (med.medicionId.ano >= :anoInicio and med.medicionId.ano <= :anoFinal)";
    } else if (anoInicio != null) {
      sql = sql + " and med.medicionId.ano >= :anoInicio";
    } else if (anoFinal != null) {
      sql = sql + " and med.medicionId.ano <= :anoFinal";
    }
    sql = sql + " order by med.medicionId.indicadorId, med.medicionId.serieId, med.medicionId.ano, med.medicionId.periodo";
    
    Query query = session.createQuery(sql);
    
    if (indicadorId != null)
      query.setLong("indicadorId", indicadorId.longValue());
    if (serieId != null)
      query.setLong("serieId", serieId.longValue());
    if (anoInicio != null)
      query.setInteger("anoInicio", anoInicio.intValue());
    if (anoFinal != null)
      query.setInteger("anoFinal", anoFinal.intValue());
    if (periodoInicio != null)
      query.setInteger("periodoInicio", periodoInicio.intValue());
    if (periodoFinal != null) {
      query.setInteger("periodoFinal", periodoFinal.intValue());
    }
    List<MedicionValoracion> medicionesExistentes = query.list();
    
    return medicionesExistentes;
  }
  
  public List<MedicionValoracion> getMedicionesPeriodo(Long indicadorId, Byte frecuencia, Long serieId, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal)
  {
    List<MedicionValoracion> medicionesExistentes = getMedicionesPeriodoExactas(indicadorId, serieId, anoInicio, anoFinal, periodoInicio, periodoFinal);
    
    return getMedicionesPeriodoPorFrecuencia(indicadorId, frecuencia, serieId, anoInicio, anoFinal, periodoInicio, periodoFinal, medicionesExistentes);
  }
  
  public List<MedicionValoracion> getMedicionesPeriodoPorFrecuencia(Long indicadorId, Byte frecuencia, Long serieId, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal, List<MedicionValoracion> medicionesExistentes)
  {
    List<MedicionValoracion> mediciones = new ArrayList();
    if ((anoInicio != null) && (periodoInicio != null) && (anoFinal != null) && (periodoFinal != null) && (frecuencia != null))
    {
      int ano = anoInicio.intValue();
      Iterator<MedicionValoracion> iterMedicionesExistentes = medicionesExistentes.iterator();
      boolean getExistente = iterMedicionesExistentes.hasNext();
      MedicionValoracion medicion = null;
      MedicionValoracion medicionExistente = null;
      
      while (ano <= anoFinal.intValue())
      {
        int periodo = 1;
        if (ano == anoInicio.intValue())
          periodo = periodoInicio.intValue();
        int periodoMaximo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
        if (ano == anoFinal.intValue())
          periodoMaximo = periodoFinal.intValue();
        while (periodo <= periodoMaximo)
        {
          if (getExistente)
            medicionExistente = (MedicionValoracion)iterMedicionesExistentes.next();
          if (medicionExistente != null)
          {
            if ((medicionExistente.getMedicionId().getAno().intValue() == ano) && (medicionExistente.getMedicionId().getPeriodo().intValue() == periodo))
            {
              medicion = medicionExistente;
              getExistente = iterMedicionesExistentes.hasNext();
              medicionExistente = null;
            }
            else
            {
              medicion = null;
              getExistente = false;
            }
          }
          else
          {
            medicion = null;
            getExistente = false;
          }
          if (medicion == null)
          {
            medicion = new MedicionValoracion();
            MedicionPK medicionPk = new MedicionPK();
            medicionPk.setIndicadorId(indicadorId);
            medicionPk.setSerieId(SerieTiempo.getSerieMetaId());
            medicionPk.setAno(new Integer(ano));
            medicionPk.setPeriodo(new Integer(periodo));
            medicion.setMedicionId(medicionPk);
            medicion.setProtegido(new Boolean(false));
          }
          
          mediciones.add(medicion);
          periodo++;
        }
        ano++;
      }
    }
    else {
      mediciones = medicionesExistentes;
    }
    return mediciones;
  }
  
  public List<MedicionValoracion> getMedicionesPeriodo(Long indicadorId, Byte frecuencia, Byte mesCierre, Long serieId, Boolean valorInicialCero, Byte corte, Byte tipoMedicion, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal)
  {
    List<MedicionValoracion> mediciones = null;
    if (corte.byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue())
    {
      if ((tipoMedicion != null) && (tipoMedicion.byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())) {
        mediciones = getMedicionesAcumuladasPeriodo(indicadorId, frecuencia, mesCierre, serieId, valorInicialCero, anoInicio, anoFinal, periodoInicio, periodoFinal);
      } else {
        mediciones = getMedicionesPeriodo(indicadorId, frecuencia, serieId, anoInicio, anoFinal, periodoInicio, periodoFinal);
      }
    } else {
      mediciones = getMedicionesPeriodo(indicadorId, frecuencia, serieId, anoInicio, anoFinal, periodoInicio, periodoFinal);
    }
    return mediciones;
  }
  
  public List<MedicionValoracion> getMedicionesAcumuladasPeriodo(Long indicadorId, Byte frecuencia, Byte mesCierre, Long serieId, Boolean valorInicialCero, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal)
  {
    String sql = "from Medicion med where med.medicionId.indicadorId = :indicadorId  and med.medicionId.serieId = :serieId ";
    
    int anoInicioInterno = 0;
    int anoFinalInterno = 0;
    int periodoInicioInterno = 0;
    int periodoFinalInterno = 0;
    int periodoDesdeCierre = 0;
    int periodoHastaCierre = 0;
    Byte mesInicio = null;
    List<MedicionValoracion> mediciones = new ArrayList();
    
    if (mesCierre != null) {
      mesInicio = PeriodoUtil.getMesInicio(mesCierre);
    }
    else {
      mesCierre = new Byte("12");
      mesInicio = new Byte("1");
    }
    
    if (anoInicio != null)
      anoInicioInterno = anoInicio.intValue();
    if (anoFinal != null)
      anoFinalInterno = anoFinal.intValue();
    if (periodoInicio != null)
      periodoInicioInterno = periodoInicio.intValue();
    if (periodoFinal != null) {
      periodoFinalInterno = periodoFinal.intValue();
    }
    if ((anoInicio == null) || (periodoInicio == null))
    {
      MedicionValoracion medTemp = getPrimeraMedicion(indicadorId, serieId);
      if (medTemp != null) {
        if (anoInicio == null)
          anoInicioInterno = medTemp.getMedicionId().getAno().intValue();
        if (periodoInicio == null)
          periodoInicioInterno = medTemp.getMedicionId().getPeriodo().intValue();
      }
    }
    if ((anoFinal == null) || (periodoFinal == null))
    {
      MedicionValoracion medTemp = getUltimaMedicion(indicadorId, serieId);
      if (medTemp != null)
      {
        if (anoFinal == null)
          anoFinalInterno = medTemp.getMedicionId().getAno().intValue();
        if (periodoFinal == null) {
          periodoFinalInterno = medTemp.getMedicionId().getPeriodo().intValue();
        }
      }
    }
    if ((anoInicioInterno == 0) && (anoFinalInterno == 9999) && (periodoInicioInterno == 0) && (periodoFinalInterno == 999))
    {
      List<MedicionValoracion> medicionesExistentes = new ArrayList();
      String sqlInt = sql + "order by med.medicionId.ano asc, med.medicionId.periodo asc";
      Query query = session.createQuery(sqlInt);
      query.setLong("indicadorId", indicadorId.longValue());
      query.setLong("serieId", serieId.longValue());
      medicionesExistentes = query.list();
      double acumulado = 0.0D;
      
      for (Iterator<MedicionValoracion> m = medicionesExistentes.iterator(); m.hasNext();)
      {
        MedicionValoracion medTemp = (MedicionValoracion)m.next();
        session.evict(medTemp);
        acumulado += medTemp.getValor().doubleValue();
        medTemp.setValor(new Double(acumulado));
        mediciones.add(medTemp);
      }
    }
    else if ((anoInicioInterno > 0) && (anoFinalInterno > 0) && (periodoInicioInterno > 0) && (periodoFinalInterno > 0))
    {
      List<MedicionValoracion> medicionesExistentes = new ArrayList();
      boolean hayValorAcumuladoInicial = false;
      Double valorAcumuladoInicial = null;
      boolean hayMediciones = false;
      double acumulado = 0.0D;
      if (valorInicialCero.booleanValue())
      {
        for (int ano = anoInicioInterno; ano <= anoFinalInterno; ano++)
        {
          acumulado = 0.0D;
          if (mesCierre.byteValue() == 12)
          {
            periodoDesdeCierre = 1;
            periodoHastaCierre = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
          }
          else
          {
            periodoDesdeCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesInicio.intValue(), ano - 1), frecuencia.byteValue());
            periodoHastaCierre = PeriodoUtil.getPeriodoDeFecha(PeriodoUtil.getCalendarFinMes(mesCierre.intValue(), ano), frecuencia.byteValue());
          }
          
          String sqlInt = sql + " and med.medicionId.ano = :ano ";
          int anoAdicional = ano;
          int periodoCierre = 0;
          if (mesCierre.byteValue() != 12)
          {
            if (ano == anoInicioInterno)
            {
              if (periodoInicioInterno <= periodoHastaCierre)
              {
                anoAdicional = ano - 1;
                sqlInt = sqlInt + " or (med.medicionId.ano = :anoAdicional and med.medicionId.periodo >= :periodoCierre)";
              }
              else {
                sqlInt = sqlInt + " and med.medicionId.periodo >= :periodoCierre";
              }
            } else if (1 <= periodoHastaCierre)
            {
              anoAdicional = ano - 1;
              sqlInt = sqlInt + " or (med.medicionId.ano = :anoAdicional and med.medicionId.periodo >= :periodoCierre)";
            }
            else {
              sqlInt = sqlInt + " and med.medicionId.periodo >= :periodoCierre";
            }
            periodoCierre = periodoDesdeCierre;
          }
          sqlInt = sqlInt + "order by med.medicionId.ano asc, med.medicionId.periodo asc";
          
          Query query = session.createQuery(sqlInt);
          
          query.setLong("indicadorId", indicadorId.longValue());
          query.setLong("serieId", serieId.longValue());
          query.setInteger("ano", ano);
          if (anoAdicional != ano)
            query.setInteger("anoAdicional", anoAdicional);
          if (periodoCierre > 0) {
            query.setInteger("periodoCierre", periodoCierre);
          }
          List<MedicionValoracion> medicionesTemp = query.list();
          if (anoInicioInterno == anoFinalInterno) {
            for (Iterator<MedicionValoracion> m = medicionesTemp.iterator(); m.hasNext();)
            {
              MedicionValoracion medTemp = (MedicionValoracion)m.next();
              
              session.evict(medTemp);
              if ((!hayMediciones) && (hayValorAcumuladoInicial))
                valorAcumuladoInicial = new Double(acumulado);
              acumulado += medTemp.getValor().doubleValue();
              if ((medTemp.getMedicionId().getPeriodo().intValue() >= periodoInicioInterno) && (medTemp.getMedicionId().getPeriodo().intValue() <= periodoFinalInterno))
              {
                hayMediciones = true;
                medTemp.setValor(new Double(acumulado));
                medicionesExistentes.add(medTemp);
              }
              hayValorAcumuladoInicial = true;
            }
          } else if (ano == anoInicioInterno) {
            for (Iterator<MedicionValoracion> m = medicionesTemp.iterator(); m.hasNext();)
            {
              MedicionValoracion medTemp = (MedicionValoracion)m.next();
              session.evict(medTemp);
              if ((!hayMediciones) && (hayValorAcumuladoInicial))
                valorAcumuladoInicial = new Double(acumulado);
              acumulado += medTemp.getValor().doubleValue();
              if (medTemp.getMedicionId().getPeriodo().intValue() >= periodoInicioInterno)
              {
                hayMediciones = true;
                medTemp.setValor(new Double(acumulado));
                medicionesExistentes.add(medTemp);
              }
              hayValorAcumuladoInicial = true;
            }
          } else if (ano == anoFinalInterno) {
            for (Iterator<MedicionValoracion> m = medicionesTemp.iterator(); m.hasNext();)
            {
              MedicionValoracion medTemp = (MedicionValoracion)m.next();
              session.evict(medTemp);
              acumulado += medTemp.getValor().doubleValue();
              if (medTemp.getMedicionId().getPeriodo().intValue() <= periodoFinalInterno)
              {
                hayMediciones = true;
                medTemp.setValor(new Double(acumulado));
                medicionesExistentes.add(medTemp);
              }
            }
          } else {
            for (Iterator<MedicionValoracion> m = medicionesTemp.iterator(); m.hasNext();)
            {
              MedicionValoracion medTemp = (MedicionValoracion)m.next();
              session.evict(medTemp);
              acumulado += medTemp.getValor().doubleValue();
              hayMediciones = true;
              medTemp.setValor(new Double(acumulado));
              medicionesExistentes.add(medTemp);
            }
          }
        }
      }
      else {
        acumulado = 0.0D;
        String sqlInt = sql + " and med.medicionId.ano <= :ano order by med.medicionId.ano asc, med.medicionId.periodo asc";
        
        Query query = session.createQuery(sqlInt);
        
        query.setLong("indicadorId", indicadorId.longValue());
        query.setLong("serieId", serieId.longValue());
        query.setInteger("ano", anoFinalInterno);
        
        List<MedicionValoracion> medicionesTemp = query.list();
        
        if (anoInicioInterno == anoFinalInterno)
        {
          for (Iterator<MedicionValoracion> m = medicionesTemp.iterator(); m.hasNext();)
          {
            MedicionValoracion medTemp = (MedicionValoracion)m.next();
            session.evict(medTemp);
            if ((!hayMediciones) && (hayValorAcumuladoInicial))
              valorAcumuladoInicial = new Double(acumulado);
            acumulado += medTemp.getValor().doubleValue();
            if ((medTemp.getMedicionId().getAno().intValue() == anoInicioInterno) && (medTemp.getMedicionId().getPeriodo().intValue() >= periodoInicioInterno) && (medTemp.getMedicionId().getPeriodo().intValue() <= periodoFinalInterno))
            {
              hayMediciones = true;
              medTemp.setValor(new Double(acumulado));
              medicionesExistentes.add(medTemp);
            }
            hayValorAcumuladoInicial = true;
          }
          
        }
        else {
          for (Iterator<MedicionValoracion> m = medicionesTemp.iterator(); m.hasNext();)
          {
            MedicionValoracion medTemp = (MedicionValoracion)m.next();
            session.evict(medTemp);
            if ((!hayMediciones) && (hayValorAcumuladoInicial))
              valorAcumuladoInicial = new Double(acumulado);
            acumulado += medTemp.getValor().doubleValue();
            medTemp.setValor(new Double(acumulado));
            if (medTemp.getMedicionId().getAno().intValue() == anoInicioInterno)
            {
              if (medTemp.getMedicionId().getPeriodo().intValue() >= periodoInicioInterno)
              {
                hayMediciones = true;
                medicionesExistentes.add(medTemp);
              }
              hayValorAcumuladoInicial = true;
            }
            else if (medTemp.getMedicionId().getAno().intValue() == anoFinalInterno)
            {
              if (medTemp.getMedicionId().getPeriodo().intValue() <= periodoFinalInterno)
              {
                hayMediciones = true;
                medicionesExistentes.add(medTemp);
              }
            }
            else if ((medTemp.getMedicionId().getAno().intValue() > anoInicioInterno) && (medTemp.getMedicionId().getAno().intValue() < anoFinalInterno))
            {
              hayMediciones = true;
              medicionesExistentes.add(medTemp);
            }
          }
        }
      }
      
      int ano = anoInicioInterno;
      Iterator<MedicionValoracion> iterMedicionesExistentes = medicionesExistentes.iterator();
      MedicionValoracion primeraMedicionExistente = null;
      if (medicionesExistentes.size() > 0)
        primeraMedicionExistente = (MedicionValoracion)medicionesExistentes.get(0);
      boolean getExistente = iterMedicionesExistentes.hasNext();
      MedicionValoracion medicion = null;
      MedicionValoracion medicionExistente = null;
      
      while (ano <= anoFinalInterno)
      {
        int periodo = 1;
        if (ano == anoInicioInterno)
          periodo = periodoInicioInterno;
        int periodoMaximo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
        if (ano == anoFinalInterno)
          periodoMaximo = periodoFinalInterno;
        while (periodo <= periodoMaximo)
        {
          if (getExistente)
            medicionExistente = (MedicionValoracion)iterMedicionesExistentes.next();
          if (medicionExistente != null)
          {
            if ((medicionExistente.getMedicionId().getAno().intValue() == ano) && (medicionExistente.getMedicionId().getPeriodo().intValue() == periodo))
            {
              medicion = medicionExistente;
              getExistente = iterMedicionesExistentes.hasNext();
              medicionExistente = null;
            }
            else
            {
              medicion = null;
              getExistente = false;
            }
          }
          else
          {
            medicion = null;
            getExistente = false;
          }
          if (medicion == null)
          {
            medicion = new MedicionValoracion();
            MedicionPK medicionPk = new MedicionPK();
            medicionPk.setIndicadorId(indicadorId);
            medicionPk.setSerieId(SerieTiempo.getSerieMetaId());
            medicionPk.setAno(new Integer(ano));
            medicionPk.setPeriodo(new Integer(periodo));
            medicion.setMedicionId(medicionPk);
            if ((valorAcumuladoInicial != null) && (primeraMedicionExistente != null) && ((ano < primeraMedicionExistente.getMedicionId().getAno().intValue()) || ((ano == primeraMedicionExistente.getMedicionId().getAno().intValue()) && (periodo < primeraMedicionExistente.getMedicionId().getPeriodo().intValue())))) {
              medicion.setValor(valorAcumuladoInicial);
            }
            medicion.setProtegido(new Boolean(false));
          }
          
          mediciones.add(medicion);
          periodo++;
        }
        ano++;
      }
    }
    
    return mediciones;
  }
  
  public MedicionValoracion getUltimaMedicion(Long indicadorId, Byte frecuencia, Byte mesCierre, Long serieId, Boolean valorInicialCero, Byte corte, Byte tipoMedicion)
  {
    List<MedicionValoracion> mediciones = null;
    
    mediciones = getMedicionesPeriodo(indicadorId, frecuencia, mesCierre, serieId, valorInicialCero, corte, tipoMedicion, null, null, null, null);
    
    MedicionValoracion medicion = null;
    if ((mediciones != null) && (mediciones.size() > 0)) {
      medicion = (MedicionValoracion)mediciones.get(mediciones.size() - 1);
    }
    return medicion;
  }
  
  public MedicionValoracion getMedicion(Long indicadorId, Long serieId, Integer ano, Integer periodo)
  {
    Criteria consulta = session.createCriteria(MedicionValoracion.class);
    
    consulta.add(Restrictions.eq("id.indicadorId", indicadorId));
    consulta.add(Restrictions.eq("id.serieId", serieId));
    consulta.add(Restrictions.eq("id.ano", ano));
    consulta.add(Restrictions.eq("id.periodo", periodo));
    
    List<MedicionValoracion> mediciones = consulta.list();
    
    if ((mediciones != null) && (mediciones.size() > 0)) {
      return (MedicionValoracion)mediciones.get(0);
    }
    return null;
  }
  
  public MedicionValoracion getUltimaMedicion(Long indicadorId, Long serieId)
  {
    Criteria consulta = session.createCriteria(MedicionValoracion.class);
    
    consulta.add(Restrictions.eq("id.indicadorId", indicadorId));
    consulta.add(Restrictions.eq("id.serieId", serieId));
    consulta.addOrder(Order.desc("id.ano"));
    consulta.addOrder(Order.desc("id.periodo"));
    
    List<MedicionValoracion> mediciones = consulta.list();
    
    MedicionValoracion medicion = null;
    if (mediciones.size() > 0)
      medicion = (MedicionValoracion)mediciones.get(0);
    return medicion;
  }
  
  public MedicionValoracion getPrimeraMedicion(Long indicadorId, Long serieId)
  {
    Criteria consulta = session.createCriteria(MedicionValoracion.class);
    
    consulta.add(Restrictions.eq("id.indicadorId", indicadorId));
    consulta.add(Restrictions.eq("id.serieId", serieId));
    consulta.addOrder(Order.asc("id.ano"));
    consulta.addOrder(Order.asc("id.periodo"));
    
    List<MedicionValoracion> mediciones = consulta.list();
    
    if ((mediciones != null) && (mediciones.size() > 0)) {
      return (MedicionValoracion)mediciones.get(0);
    }
    return null;
  }
  
  public Long getNumeroMediciones(Long indicadorId)
  {
    Query consulta = session.createQuery("select count(*) from Medicion medicion where medicion.medicionId.indicadorId = ?").setLong(0, indicadorId.longValue());
    
    List<Long> resultado = consulta.list();
    
    if (resultado.get(0) != null) {
      return (Long)resultado.get(0);
    }
    return new Long(0L);
  }
  
  public int deleteMedicion(MedicionValoracion medicion) throws Throwable
  {
    int borrados = 0;
    try
    {
      String hqlDelete = "delete Medicion m where m.medicionId.indicadorId = :indicadorId and m.medicionId.serieId = :serieId and m.medicionId.ano = :ano and m.medicionId.periodo = :periodo";
      borrados = session.createQuery(hqlDelete).setLong("indicadorId", medicion.getMedicionId().getIndicadorId().longValue()).setLong("serieId", medicion.getMedicionId().getSerieId().longValue()).setInteger("ano", medicion.getMedicionId().getAno().intValue()).setInteger("periodo", medicion.getMedicionId().getPeriodo().intValue()).executeUpdate();
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
  
  public List<MedicionValoracion> getMedicionesIndicadores(List<Long> indicadoresIds, Long serieId, Integer anoInical, Integer anoFinal)
  {
    List<MedicionValoracion> mediciones = new ArrayList();
    
    if ((indicadoresIds != null) && (indicadoresIds.size() > 0))
    {
      String sql = "select medicion from Medicion medicion where medicion.medicionId.indicadorId in (";
      for (Iterator<Long> iter = indicadoresIds.iterator(); iter.hasNext();)
      {
        Long indicadorId = (Long)iter.next();
        sql = sql + indicadorId.toString() + ", ";
      }
      sql = sql.substring(0, sql.length() - 2) + ") ";
      
      if (serieId != null)
        sql = sql + " and medicion.medicionId.serieId = " + serieId;
      if (anoInical != null)
        sql = sql + " and medicion.medicionId.ano >= " + anoInical;
      if (anoInical != null) {
        sql = sql + " and medicion.medicionId.ano <= " + anoFinal;
      }
      sql = sql + " order by medicion.medicionId.indicadorId, medicion.medicionId.serieId, medicion.medicionId.ano asc, medicion.medicionId.periodo asc";
      
      Query consulta = session.createQuery(sql);
      
      mediciones = consulta.list();
    }
    
    return mediciones;
  }
  
  public List<MedicionValoracion> getUltimasMedicionesIndicadores(List<Long> indicadoresIds, Long serieId)
  {
    List<MedicionValoracion> ultimasMediciones = new ArrayList();
    
    if ((indicadoresIds != null) && (indicadoresIds.size() > 0))
    {
      String sql = "select medicion from Medicion medicion where medicion.medicionId.indicadorId in (";
      for (Iterator<Long> iter = indicadoresIds.iterator(); iter.hasNext();)
      {
        Long indicadorId = (Long)iter.next();
        sql = sql + indicadorId.toString() + ", ";
      }
      sql = sql.substring(0, sql.length() - 2) + ") and medicion.medicionId.serieId = " + serieId + " order by medicion.medicionId.ano desc, medicion.medicionId.periodo desc";
      Query consulta = session.createQuery(sql);
      
      List<MedicionValoracion> mediciones = consulta.list();
      
      if (mediciones.size() > 0)
      {
        MedicionValoracion medicion = (MedicionValoracion)mediciones.get(0);
        
        int ano = medicion.getMedicionId().getAno().intValue();
        int periodo = medicion.getMedicionId().getPeriodo().intValue();
        
        for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
        {
          medicion = (MedicionValoracion)iter.next();
          
          if ((medicion.getMedicionId().getAno().intValue() != ano) || (medicion.getMedicionId().getPeriodo().intValue() != periodo)) break;
          ultimasMediciones.add(medicion);
        }
      }
    }
    
    return ultimasMediciones;
  }
  
  public List<MedicionValoracion> getMedicionesBySerie(List<MedicionValoracion> mediciones, Long indicadorId, Long serieId)
  {
    List<MedicionValoracion> med = new ArrayList();
    
    for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
    {
      MedicionValoracion medicion = (MedicionValoracion)iter.next();
      if ((medicion.getMedicionId().getSerieId().longValue() == serieId.longValue()) && (medicion.getMedicionId().getIndicadorId().longValue() == indicadorId.longValue())) {
        med.add(medicion);
      }
    }
    return med;
  }
  
  public MedicionValoracion getUltimaMedicion(List<MedicionValoracion> mediciones)
  {
    MedicionValoracion medicion = null;
    Integer ano = null;
    Integer periodo = null;
    
    for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
    {
      MedicionValoracion med = (MedicionValoracion)iter.next();
      if ((ano == null) && (periodo == null))
        medicion = med;
      if (ano == null)
        ano = med.getMedicionId().getAno();
      if (periodo == null) {
        periodo = med.getMedicionId().getPeriodo();
      }
      if (med.getMedicionId().getAno().intValue() > ano.intValue())
      {
        ano = med.getMedicionId().getAno();
        periodo = med.getMedicionId().getPeriodo();
        medicion = med;
      }
      else if ((med.getMedicionId().getAno().intValue() == ano.intValue()) && (med.getMedicionId().getPeriodo().intValue() > periodo.intValue()))
      {
        periodo = med.getMedicionId().getPeriodo();
        medicion = med;
      }
    }
    
    return medicion;
  }
  
  
  public double getUltimaMedicionAcumulada(List<MedicionValoracion> mediciones)
  {
    MedicionValoracion medicion = null;
    Integer ano = null;
    Integer anoInicial = null;
    Integer periodo = null;
    
    double valorAcumulado = 0.0;
    
    int tamano=mediciones.size();
    int cont=0;
    
    for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
    {
      MedicionValoracion med = (MedicionValoracion)iter.next();
      cont ++;
      
      if(cont == 1){
    	  anoInicial = med.getMedicionId().getAno();
      }
      
      if(anoInicial == med.getMedicionId().getAno().intValue()){
    	  valorAcumulado= med.getValor();
      }
      
      if ((ano == null) && (periodo == null))
        medicion = med;
      if (ano == null)
        ano = med.getMedicionId().getAno();
      if (periodo == null) {
        periodo = med.getMedicionId().getPeriodo();
      }
      if (med.getMedicionId().getAno().intValue() > ano.intValue())
      {  
        ano = med.getMedicionId().getAno();
        periodo = med.getMedicionId().getPeriodo();
        medicion = med;
      }
      else if ((med.getMedicionId().getAno().intValue() == ano.intValue()) && (med.getMedicionId().getPeriodo().intValue() > periodo.intValue()))
      {
        periodo = med.getMedicionId().getPeriodo();
        medicion = med;
      }
      
      if(tamano == cont){
    	  valorAcumulado=valorAcumulado+ med.getValor(); 
      }
    }
    
    
    return valorAcumulado;
  }
  
  public MedicionValoracion getUltimaMedicionByAno(List<MedicionValoracion> mediciones, Integer ano)
  {
    MedicionValoracion medicion = null;
    Integer periodo = null;
    
    for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
    {
      MedicionValoracion med = (MedicionValoracion)iter.next();
      if (periodo == null)
        medicion = med;
      if (periodo == null) {
        periodo = med.getMedicionId().getPeriodo();
      }
      if ((med.getMedicionId().getAno().intValue() == ano.intValue()) && (med.getMedicionId().getPeriodo().intValue() > periodo.intValue()))
      {
        periodo = med.getMedicionId().getPeriodo();
        medicion = med;
      }
    }
    
    return medicion;
  }
  
  public MedicionValoracion getUltimaMedicionByAnoByPeriodo(List<MedicionValoracion> mediciones, Long indicadorId, Long serieId, Integer ano, Integer periodo, Boolean searDatabase)
  {
    MedicionValoracion medicion = null;
    
    for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
    {
      MedicionValoracion med = (MedicionValoracion)iter.next();
      if ((med.getMedicionId().getAno().intValue() == ano.intValue()) && (med.getMedicionId().getPeriodo().intValue() == periodo.intValue()) && 
        (med.getMedicionId().getIndicadorId().longValue() == indicadorId.longValue()) && (med.getMedicionId().getSerieId().longValue() == serieId.longValue()))
      {
        periodo = med.getMedicionId().getPeriodo();
        medicion = med;
      }
    }
    
    if ((medicion == null) && (mediciones.size() > 0) && (searDatabase.booleanValue())) {
      medicion = getMedicion(indicadorId, serieId, ano, periodo);
    }
    return medicion;
  }


  public double getValorAcumulado(Long indicadorId, Long serieId) {
	
	  Query consulta = session.createQuery("select valor from Medicion m where m.medicionId.indicadorId = :indicadorId and m.medicionId.serieId = :serieId").setLong("indicadorId", indicadorId).setLong("serieId", serieId);
	    
	  List<Double> mediciones = consulta.list();  
	  Double valor= null;
	  double valorAcumulado= 0.0;
	  
	  if (mediciones.size() > 0)
      {   
        for (Iterator<Double> iter = mediciones.iterator(); iter.hasNext();)
        {
          valor = iter.next();
          valorAcumulado= valorAcumulado+valor.doubleValue();   
        }
      }
	return valorAcumulado;
  }
}
