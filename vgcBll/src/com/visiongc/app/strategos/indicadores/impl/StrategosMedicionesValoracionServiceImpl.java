package com.visiongc.app.strategos.indicadores.impl;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;

import com.visiongc.app.strategos.indicadores.StrategosMedicionesValoracionService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.MedicionValoracion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesValoracionPersistenceSession;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.servicio.Servicio.EjecutarTipo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.model.Usuario;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class StrategosMedicionesValoracionServiceImpl
  extends StrategosServiceImpl
  implements StrategosMedicionesValoracionService
{
  private StrategosMedicionesValoracionPersistenceSession persistenceSession = null;
  
  public StrategosMedicionesValoracionServiceImpl(StrategosMedicionesValoracionPersistenceSession persistenceSession, boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources)
  {
    super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
    this.persistenceSession = persistenceSession;
  }
  
  public int deleteMedicion(MedicionValoracion medicion, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      if (medicion.getMedicionId() != null) {
        resultado = this.persistenceSession.deleteMedicion(medicion);
      }
      if (resultado == 10000)
      {
        if (transActiva)
        {
          this.persistenceSession.commitTransaction();
          transActiva = false;
        }
      }
      else if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    return resultado;
  }
  
  public int deleteMediciones(Long indicadorId, Long serieId, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal)
  {
    boolean transActiva = false;
    int resDb = 10000;
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      resDb = this.persistenceSession.deleteMediciones(indicadorId, serieId, anoInicio, anoFinal, periodoInicio, periodoFinal);
      if (resDb == 10000)
      {
        if (transActiva)
        {
          this.persistenceSession.commitTransaction();
          transActiva = false;
        }
      }
      else if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        throw new ChainedRuntimeException(t.getMessage(), t);
      }
    }
    return resDb;
  }
  
  public int deleteMediciones(Long indicadorId)
  {
    return deleteMediciones(indicadorId, null, null, null, null, null);
  }
  
  public int saveMedicion(MedicionValoracion medicion, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[4];
    Object[] fieldValues = new Object[4];
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      if (medicion.getValor() == null)
      {
        resultado = this.persistenceSession.delete(medicion, usuario);
      }
      else
      {
        fieldNames[0] = "medicionId.indicadorId";
        fieldValues[0] = medicion.getMedicionId().getIndicadorId();
        fieldNames[1] = "medicionId.serieId";
        fieldValues[1] = medicion.getMedicionId().getSerieId();
        fieldNames[2] = "medicionId.ano";
        fieldValues[2] = medicion.getMedicionId().getAno();
        fieldNames[3] = "medicionId.periodo";
        fieldValues[3] = medicion.getMedicionId().getPeriodo();
        if (!this.persistenceSession.existsObject(medicion, fieldNames, fieldValues)) {
          resultado = this.persistenceSession.insert(medicion, usuario);
        } else {
          resultado = this.persistenceSession.update(medicion, usuario);
        }
      }
      if (transActiva)
      {
        this.persistenceSession.commitTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resultado;
  }
  
  public int saveMediciones(List<MedicionValoracion> mediciones, Long planId, Usuario usuario, Boolean actualizarUltimaMedicion, Boolean actualizarAlerta)
  {
    boolean transActiva = false;
    int resultado = 10000;
    
    ArrayList<Object> indicadores = new ArrayList();
    
    String[] fieldNames = new String[4];
    Object[] fieldValues = new Object[4];
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      Indicador indicador = null;
      for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
      {
        MedicionValoracion medicion = (MedicionValoracion)iter.next();
        indicador = (Indicador)this.persistenceSession.load(Indicador.class, medicion.getMedicionId().getIndicadorId());
        if (!indicadores.contains(indicador)) {
          indicadores.add(indicador);
        }
        if (medicion.getValor() == null)
        {
          resultado = this.persistenceSession.deleteMedicion(medicion);
          if (resultado == 10001) {
            resultado = 10000;
          }
        }
        else
        {
          fieldNames[0] = "medicionId.indicadorId";
          fieldValues[0] = medicion.getMedicionId().getIndicadorId();
          fieldNames[1] = "medicionId.serieId";
          fieldValues[1] = medicion.getMedicionId().getSerieId();
          fieldNames[2] = "medicionId.ano";
          fieldValues[2] = medicion.getMedicionId().getAno();
          fieldNames[3] = "medicionId.periodo";
          fieldValues[3] = medicion.getMedicionId().getPeriodo();
          if (!this.persistenceSession.existsObject(medicion, fieldNames, fieldValues)) {
            resultado = this.persistenceSession.insert(medicion, usuario);
          } else {
            resultado = this.persistenceSession.update(medicion, usuario);
          }
        }
        if (resultado != 10000) {
          break;
        }
      }
      if (resultado == 10000)
      {
        if (resultado == 10000)
        {
          if ((actualizarUltimaMedicion != null) && (actualizarUltimaMedicion.booleanValue()))
          {
            resultado = actualizarUltimaMedicionIndicadores(indicadores, planId, usuario);
            if (transActiva) {
              if (resultado != 10000)
              {
                this.persistenceSession.rollbackTransaction();
                transActiva = false;
              }
              else
              {
                this.persistenceSession.commitTransaction();
                transActiva = false;
              }
            }
          }
          else if (transActiva)
          {
            this.persistenceSession.commitTransaction();
            transActiva = false;
          }
        }
        else if (transActiva)
        {
          this.persistenceSession.rollbackTransaction();
          transActiva = false;
        }
      }
      else if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        transActiva = false;
      }
      if ((!transActiva) && (resultado == 10000) && (!this.persistenceSession.isTransactionActive()) && (actualizarAlerta.booleanValue()))
      {
        ArrayList<Object> indicadoresServicio = new ArrayList();
        for (Iterator<Object> i = indicadores.iterator(); i.hasNext();)
        {
          Indicador objeto = (Indicador)i.next();
          if ((objeto.getOrganizacion() == null) || (objeto.getOrganizacion().getMesCierre() == null))
          {
            OrganizacionStrategos organizacion = (OrganizacionStrategos)this.persistenceSession.load(OrganizacionStrategos.class, objeto.getOrganizacionId());
            objeto.setOrganizacion(organizacion);
            objeto.getOrganizacion().setMesCierre(organizacion.getMesCierre());
          }
          indicadoresServicio.add(objeto.convertService());
        }
        resultado = new Servicio().calcular(Servicio.EjecutarTipo.getEjecucionAlerta().byteValue(), indicadoresServicio, usuario);
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resultado;
  }
  
  public int actualizarUltimaMedicionIndicadores(List<Object> indicadores)
  {
    return actualizarUltimaMedicionIndicadores(indicadores, null, null);
  }
  
  public int actualizarUltimaMedicionIndicadores(List<Object> indicadores, Long planId, Usuario usuario)
  {
    boolean transActiva = false;
    int resDb = 10000;
    StrategosIndicadoresService strategosIndicadoresService = null;
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        transActiva = true;
        this.persistenceSession.beginTransaction();
      }
      Object objeto = indicadores.get(0);
      boolean esObjetoIndicador = true;
      if (objeto.getClass().getName().equals("java.lang.Long")) {
        esObjetoIndicador = false;
      }
      strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
      if (!esObjetoIndicador) {
        for (Iterator<Object> iter = indicadores.listIterator(); iter.hasNext();)
        {
          Long indicadorId = (Long)iter.next();
          
          Indicador indicador = (Indicador)this.persistenceSession.load(Indicador.class, indicadorId);
          resDb = actualizarUltimaMedicionIndicador(indicador, planId, usuario, strategosIndicadoresService);
          if (resDb != 10000) {
            break;
          }
        }
      } else {
        for (Iterator<Object> iter = indicadores.listIterator(); iter.hasNext();)
        {
          Indicador indicador = (Indicador)iter.next();
          resDb = actualizarUltimaMedicionIndicador(indicador, planId, usuario, strategosIndicadoresService);
          if (resDb != 10000) {
            break;
          }
        }
      }
      strategosIndicadoresService.close();
      if (resDb == 10000)
      {
        if (transActiva)
        {
          this.persistenceSession.commitTransaction();
          transActiva = false;
        }
      }
      else if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      if (strategosIndicadoresService != null) {
        strategosIndicadoresService.close();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resDb;
  }
  
  private int actualizarUltimaMedicionIndicador(Indicador indicador, Long planId, Usuario usuario, StrategosIndicadoresService strategosIndicadoresService)
  {
    int resDb = 10000;
    try
    {
      StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();
      UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
      strategosUnidadesService.close();
      
      MedicionValoracion ultimaMedicion = getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
      MedicionValoracion ultimoProgramado = null;
      if (ultimaMedicion != null) {
        if (indicador.getTipoFuncion().byteValue() != TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue())
        {
          List<MedicionValoracion> mediciones = getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieProgramadoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getAno(), ultimaMedicion.getMedicionId().getPeriodo(), ultimaMedicion.getMedicionId().getPeriodo());
          if ((mediciones != null) && (mediciones.size() > 0)) {
            ultimoProgramado = (MedicionValoracion)mediciones.get(0);
          }
        }
        else if ((unidad != null) && (indicador.getUnidadId() != null) && (indicador.getUnidadId().longValue() == unidad.getUnidadId().longValue()) && (ultimaMedicion.getValor().doubleValue() == 100.0D))
        {
          ultimoProgramado = getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieProgramado().getSerieId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion());
        }
        else
        {
          List<MedicionValoracion> mediciones = getMedicionesPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieProgramadoId(), indicador.getValorInicialCero(), indicador.getCorte(), indicador.getTipoCargaMedicion(), null, ultimaMedicion.getMedicionId().getAno(), null, ultimaMedicion.getMedicionId().getPeriodo());
          if ((mediciones != null) && (mediciones.size() > 0))
          {
            DecimalFormat nf3 = new DecimalFormat("#000");
            int anoPeriodoBuscar = Integer.parseInt(ultimaMedicion.getMedicionId().getAno().toString() + nf3.format(ultimaMedicion.getMedicionId().getPeriodo()).toString());
            for (Iterator<?> iterMed = mediciones.iterator(); iterMed.hasNext();)
            {
              MedicionValoracion medicion = (MedicionValoracion)iterMed.next();
              int anoPeriodo = Integer.parseInt(medicion.getMedicionId().getAno().toString() + nf3.format(medicion.getMedicionId().getPeriodo()).toString());
              if (anoPeriodo <= anoPeriodoBuscar) {
                ultimoProgramado = medicion;
              }
            }
          }
        }
      }
      resDb = 10000;
      Double valorUltimaMedicion = null;
      Double valorUltimoProgramado = null;
      String fechaUltimaMedicion = null;
      if ((ultimaMedicion != null) && (ultimaMedicion.getValor() != null))
      {
        valorUltimaMedicion = ultimaMedicion.getValor();
        if ((ultimoProgramado != null) && (ultimoProgramado.getValor() != null)) {
          valorUltimoProgramado = ultimoProgramado.getValor();
        }
        fechaUltimaMedicion = ultimaMedicion.getMedicionId().getPeriodo().toString() + "/" + ultimaMedicion.getMedicionId().getAno().toString();
      }
      resDb = strategosIndicadoresService.actualizarDatosIndicador(indicador.getIndicadorId(), valorUltimaMedicion, valorUltimoProgramado, fechaUltimaMedicion);
    }
    catch (Throwable t)
    {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resDb;
  }
  
  private Indicador getIndicadorProgramado(Long indicadorId)
  {
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
    
    Indicador programado = strategosIndicadoresService.getIndicadorProgramado(indicadorId);
    strategosIndicadoresService.close();
    
    return programado;
  }
  
  private Indicador getIndicadorMaximo(Long indicadorId)
  {
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
    
    Indicador maximo = strategosIndicadoresService.getIndicadorMaximo(indicadorId);
    strategosIndicadoresService.close();
    
    return maximo;
  }
  
  private Indicador getIndicadorMinimo(Long indicadorId)
  {
    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
    
    Indicador minimo = strategosIndicadoresService.getIndicadorMinimo(indicadorId);
    strategosIndicadoresService.close();
    
    return minimo;
  }
  
  public List<MedicionValoracion> getMedicionesPeriodo(Long indicadorId, Long serieId, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta)
  {
    return getMedicionesPeriodo(indicadorId, serieId, anoDesde, anoHasta, periodoDesde, periodoHasta, null);
  }
  
  public List<MedicionValoracion> getMedicionesPeriodo(Long indicadorId, Long serieId, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta, Byte frecuencia)
  {
    if (serieId.equals(SerieTiempo.getSerieProgramadoId()))
    {
      Indicador programado = getIndicadorProgramado(indicadorId);
      if (programado != null)
      {
        indicadorId = programado.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
      }
    }
    else if (serieId.equals(SerieTiempo.getSerieMinimoId()))
    {
      Indicador minimo = getIndicadorMinimo(indicadorId);
      if (minimo != null)
      {
        indicadorId = minimo.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
      }
    }
    else if (serieId.equals(SerieTiempo.getSerieMaximoId()))
    {
      Indicador maximo = getIndicadorMaximo(indicadorId);
      if (maximo != null)
      {
        indicadorId = maximo.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
      }
    }
    List<MedicionValoracion> medicionesTemp = this.persistenceSession.getMedicionesPeriodo(indicadorId, serieId, anoDesde, anoHasta, periodoDesde, periodoHasta);
    List<MedicionValoracion> mediciones = new ArrayList();
    if ((frecuencia != null) && (anoDesde != null) && (anoHasta != null) && (periodoDesde != null) && (periodoHasta != null))
    {
      Iterator<MedicionValoracion> medicionesExistentes = medicionesTemp.iterator();
      boolean getExistente = medicionesExistentes.hasNext();
      MedicionValoracion medicion = null;
      MedicionValoracion medicionExistente = null;
      for (int ano = anoDesde.intValue(); ano <= anoHasta.intValue(); ano++)
      {
        int periodoDesdeInterno = 1;
        int periodoHastaInterno = 0;
        if (ano == anoDesde.intValue()) {
          periodoDesdeInterno = periodoDesde.intValue();
        }
        if (ano == anoHasta.intValue()) {
          periodoHastaInterno = periodoHasta.intValue();
        }
        if (periodoHastaInterno == 0) {
          periodoHastaInterno = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia.byteValue(), ano);
        }
        for (int periodo = periodoDesdeInterno; periodo <= periodoHastaInterno; periodo++)
        {
          if (getExistente) {
            medicionExistente = (MedicionValoracion)medicionesExistentes.next();
          }
          if (medicionExistente != null)
          {
            if ((medicionExistente.getMedicionId().getAno().intValue() == ano) && (medicionExistente.getMedicionId().getPeriodo().intValue() == periodo))
            {
              medicion = medicionExistente;
              getExistente = medicionesExistentes.hasNext();
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
            medicionPk.setSerieId(serieId);
            medicionPk.setAno(new Integer(ano));
            medicionPk.setPeriodo(new Integer(periodo));
            medicion.setMedicionId(medicionPk);
            medicion.setProtegido(new Boolean(false));
          }
          mediciones.add(medicion);
        }
      }
    }
    else
    {
      mediciones = medicionesTemp;
    }
    return mediciones;
  }
  
  public List<MedicionValoracion> getMedicionesPeriodo(Long indicadorId, Byte frecuencia, Byte mesCierre, Long serieId, Boolean valorInicialCero, Byte corte, Byte tipoMedicion, Integer anoDesde, Integer anoHasta, Integer periodoDesde, Integer periodoHasta)
  {
    if (serieId.equals(SerieTiempo.getSerieProgramadoId()))
    {
      Indicador programado = getIndicadorProgramado(indicadorId);
      if (programado != null)
      {
        indicadorId = programado.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
        valorInicialCero = programado.getValorInicialCero();
        corte = programado.getCorte();
        tipoMedicion = programado.getTipoCargaMedicion();
      }
    }
    else if (serieId.equals(SerieTiempo.getSerieMinimoId()))
    {
      Indicador minimo = getIndicadorMinimo(indicadorId);
      if (minimo != null)
      {
        indicadorId = minimo.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
        valorInicialCero = minimo.getValorInicialCero();
        corte = minimo.getCorte();
        tipoMedicion = minimo.getTipoCargaMedicion();
      }
    }
    else if (serieId.equals(SerieTiempo.getSerieMaximoId()))
    {
      Indicador maximo = getIndicadorMaximo(indicadorId);
      if (maximo != null)
      {
        indicadorId = maximo.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
        valorInicialCero = maximo.getValorInicialCero();
        corte = maximo.getCorte();
        tipoMedicion = maximo.getTipoCargaMedicion();
      }
    }
    return this.persistenceSession.getMedicionesPeriodo(indicadorId, frecuencia, mesCierre, serieId, valorInicialCero, corte, tipoMedicion, anoDesde, anoHasta, periodoDesde, periodoHasta);
  }
  
  public List<MedicionValoracion> getMedicionesPorFrecuencia(Long indicadorId, Long serieId, Integer anoInicial, Integer anoFinal, Integer periodoInicial, Integer periodoFinal, Byte frecuenciaRequerida, Byte frecuenciaOriginal, Boolean acumular, Boolean acumuladoPorPeriodo)
  {
    return this.persistenceSession.getMedicionesPorFrecuencia(indicadorId, serieId, anoInicial, anoFinal, periodoInicial, periodoFinal, frecuenciaRequerida, frecuenciaOriginal, acumular, acumuladoPorPeriodo);
  }
  
  public List<MedicionValoracion> getMedicionesPorFrecuencia(Long indicadorId, Long serieId, Boolean acumular, Boolean valorInicialCero, Byte tipoCorteIndicador, Byte frecuenciaOrigen, Byte frecuenciaDestino, Integer anoDesde, Integer periodoDesde, Integer anoHasta, Integer periodoHasta)
  {
    if (serieId.equals(SerieTiempo.getSerieProgramadoId()))
    {
      Indicador programado = getIndicadorProgramado(indicadorId);
      if (programado != null)
      {
        indicadorId = programado.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
      }
    }
    else if (serieId.equals(SerieTiempo.getSerieMinimoId()))
    {
      Indicador minimo = getIndicadorMinimo(indicadorId);
      if (minimo != null)
      {
        indicadorId = minimo.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
      }
    }
    else if (serieId.equals(SerieTiempo.getSerieMaximoId()))
    {
      Indicador maximo = getIndicadorMaximo(indicadorId);
      if (maximo != null)
      {
        indicadorId = maximo.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
      }
    }
    return this.persistenceSession.getMedicionesPorFrecuencia(indicadorId, serieId, acumular, valorInicialCero, tipoCorteIndicador, frecuenciaOrigen, frecuenciaDestino, anoDesde, periodoDesde, anoHasta, periodoHasta);
  }
  
  public ListaMap getMedicionesPorFrecuenciaInterna(Long indicadorId, Long serieId, Integer anoInicial, Integer anoFinal, Integer periodoInicial, Integer periodoFinal, Byte frecuenciaRequerida, Byte frecuenciaOriginal, Boolean acumular)
  {
    if (serieId.equals(SerieTiempo.getSerieProgramadoId()))
    {
      Indicador programado = getIndicadorProgramado(indicadorId);
      if (programado != null)
      {
        indicadorId = programado.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
      }
    }
    else if (serieId.equals(SerieTiempo.getSerieMinimoId()))
    {
      Indicador minimo = getIndicadorMinimo(indicadorId);
      if (minimo != null)
      {
        indicadorId = minimo.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
      }
    }
    else if (serieId.equals(SerieTiempo.getSerieMaximoId()))
    {
      Indicador maximo = getIndicadorMaximo(indicadorId);
      if (maximo != null)
      {
        indicadorId = maximo.getIndicadorId();
        serieId = SerieTiempo.getSerieRealId();
      }
    }
    return this.persistenceSession.getMedicionesPorFrecuenciaInterna(indicadorId, serieId, anoInicial, anoFinal, periodoInicial, periodoFinal, frecuenciaRequerida, frecuenciaOriginal, acumular);
  }
  
  public List<MedicionValoracion> getMedicionesAcumuladasPeriodo(Long indicadorId, Byte frecuencia, Byte mesCierre, Long serieId, Boolean valorInicialCero, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal)
  {
    return this.persistenceSession.getMedicionesAcumuladasPeriodo(indicadorId, frecuencia, mesCierre, serieId, valorInicialCero, anoInicio, anoFinal, periodoInicio, periodoFinal);
  }
  
  public MedicionValoracion getUltimaMedicion(Long indicadorId, Long serieId)
  {
    return this.persistenceSession.getUltimaMedicion(indicadorId, serieId);
  }
  
  public MedicionValoracion getUltimaMedicion(Long indicadorId, Byte frecuencia, Byte mesCierre, Long serieId, Boolean valorInicialCero, Byte corte, Byte tipoMedicion)
  {
    return this.persistenceSession.getUltimaMedicion(indicadorId, frecuencia, mesCierre, serieId, valorInicialCero, corte, tipoMedicion);
  }
  
  public Long getNumeroMediciones(Long indicadorId)
  {
    return this.persistenceSession.getNumeroMediciones(indicadorId);
  }
  
  public List<MedicionValoracion> getUltimasMedicionesIndicadores(List<Long> indicadoresIds, Long serieId)
  {
    return this.persistenceSession.getUltimasMedicionesIndicadores(indicadoresIds, serieId);
  }
  
  public int copiarMediciones(List<MedicionValoracion> mediciones, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    String[] fieldNames = new String[4];
    Object[] fieldValues = new Object[4];
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
      {
        MedicionValoracion medicion = (MedicionValoracion)iter.next();
        if (medicion.getValor() == null)
        {
          resultado = this.persistenceSession.deleteMedicion(medicion);
          if (resultado == 10001) {
            resultado = 10000;
          }
        }
        else
        {
          fieldNames[0] = "medicionId.indicadorId";
          fieldValues[0] = medicion.getMedicionId().getIndicadorId();
          fieldNames[1] = "medicionId.serieId";
          fieldValues[1] = medicion.getMedicionId().getSerieId();
          fieldNames[2] = "medicionId.ano";
          fieldValues[2] = medicion.getMedicionId().getAno();
          fieldNames[3] = "medicionId.periodo";
          fieldValues[3] = medicion.getMedicionId().getPeriodo();
          if (!this.persistenceSession.existsObject(medicion, fieldNames, fieldValues)) {
            resultado = this.persistenceSession.insert(medicion, usuario);
          } else {
            resultado = this.persistenceSession.update(medicion, usuario);
          }
        }
        if (resultado != 10000) {
          break;
        }
      }
      if (resultado == 10000)
      {
        this.persistenceSession.commitTransaction();
        transActiva = false;
      }
      else if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resultado;
  }
  
  public int actualizarSeriePorPeriodos(Integer anoInicio, Integer anoFin, Integer periodoInicio, Integer periodoFin, List<MedicionValoracion> mediciones, Boolean acumularPeriodos, Boolean desdeActividad, Usuario usuario)
  {
    boolean transActiva = false;
    int resultado = 10000;
    ArrayList<Object> indicadoresId = new ArrayList();
    String[] fieldNames = new String[4];
    Object[] fieldValues = new Object[4];
    for (Iterator<MedicionValoracion> iter = mediciones.iterator(); iter.hasNext();)
    {
      MedicionValoracion medicion = (MedicionValoracion)iter.next();
      if (!indicadoresId.contains(medicion.getMedicionId().getIndicadorId())) {
        indicadoresId.add(medicion.getMedicionId().getIndicadorId());
      }
    }
    try
    {
      if (!this.persistenceSession.isTransactionActive())
      {
        this.persistenceSession.beginTransaction();
        transActiva = true;
      }
      StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(this);
      for (Iterator<Object> iter = indicadoresId.iterator(); iter.hasNext();)
      {
        Long indicadorId = (Long)iter.next();
        
        Indicador indicador = (Indicador)this.persistenceSession.load(Indicador.class, indicadorId);
        List<MedicionValoracion> medicionesReales = new ArrayList();
        List<MedicionValoracion> medicionesProgramadas = new ArrayList();
        List<MedicionValoracion> medicionesProgramadasInferior = new ArrayList();
        if (desdeActividad.booleanValue()) {
          acumularPeriodos = Boolean.valueOf((indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()) && (indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue()));
        }
        if (acumularPeriodos.booleanValue())
        {
          medicionesReales = getMedicionesAcumuladasPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), anoInicio, anoFin, periodoInicio, periodoFin);
          if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
          {
            medicionesProgramadas = getMedicionesAcumuladasPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMaximoId(), indicador.getValorInicialCero(), anoInicio, anoFin, periodoInicio, periodoFin);
          }
          else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
          {
            medicionesProgramadas = getMedicionesAcumuladasPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMinimoId(), indicador.getValorInicialCero(), anoInicio, anoFin, periodoInicio, periodoFin);
          }
          else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue())
          {
            medicionesProgramadas = getMedicionesAcumuladasPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMaximoId(), indicador.getValorInicialCero(), anoInicio, anoFin, periodoInicio, periodoFin);
            medicionesProgramadasInferior = getMedicionesAcumuladasPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieMinimoId(), indicador.getValorInicialCero(), anoInicio, anoFin, periodoInicio, periodoFin);
          }
          else
          {
            medicionesProgramadas = getMedicionesAcumuladasPeriodo(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieProgramadoId(), indicador.getValorInicialCero(), anoInicio, anoFin, periodoInicio, periodoFin);
          }
        }
        else
        {
          medicionesReales = getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieRealId(), anoInicio, anoFin, periodoInicio, periodoFin, null);
          if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMaximo().byteValue())
          {
            medicionesProgramadas = getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieMaximoId(), anoInicio, anoFin, periodoInicio, periodoFin, null);
          }
          else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionValorMinimo().byteValue())
          {
            medicionesProgramadas = getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieMinimoId(), anoInicio, anoFin, periodoInicio, periodoFin, null);
          }
          else if (indicador.getCaracteristica().byteValue() == Caracteristica.getCaracteristicaCondicionBandas().byteValue())
          {
            medicionesProgramadas = getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieMaximoId(), anoInicio, anoFin, periodoInicio, periodoFin, null);
            medicionesProgramadasInferior = getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieMinimoId(), anoInicio, anoFin, periodoInicio, periodoFin, null);
          }
          else
          {
            medicionesProgramadas = getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieProgramadoId(), anoInicio, anoFin, periodoInicio, periodoFin, null);
          }
        }
        int index = 0;
        Double ultimoProgramado = null;
        if (medicionesReales.size() > 0)
        {
          for (Iterator<MedicionValoracion> iterMedicionReal = medicionesReales.iterator(); iterMedicionReal.hasNext();)
          {
            Double ejecutado = null;
            Double programado = null;
            Double programadoInferior = null;
            MedicionValoracion medicionReal = (MedicionValoracion)iterMedicionReal.next();
            if (medicionReal.getValor() != null)
            {
              ejecutado = medicionReal.getValor();
              if (indicador.getCaracteristica().byteValue() != Caracteristica.getCaracteristicaCondicionBandas().byteValue())
              {
                DecimalFormat nf3 = new DecimalFormat("#000");
                int anoPeriodoBuscar = Integer.parseInt(medicionReal.getMedicionId().getAno().toString() + nf3.format(medicionReal.getMedicionId().getPeriodo()).toString());
                for (Iterator<MedicionValoracion> iterMedicionProgramado = medicionesProgramadas.iterator(); iterMedicionProgramado.hasNext();)
                {
                  MedicionValoracion medicionProgramado = (MedicionValoracion)iterMedicionProgramado.next();
                  if (medicionProgramado.getValor() != null) {
                    ultimoProgramado = medicionProgramado.getValor();
                  }
                  int anoPeriodo = Integer.parseInt(medicionProgramado.getMedicionId().getAno().toString() + nf3.format(medicionProgramado.getMedicionId().getPeriodo()).toString());
                  if ((anoPeriodo <= anoPeriodoBuscar) && (medicionProgramado.getValor() != null)) {
                    programado = medicionProgramado.getValor();
                  }
                }
              }
              else
              {
                for (Iterator<MedicionValoracion> iterMedicionProgramado = medicionesProgramadas.iterator(); iterMedicionProgramado.hasNext();)
                {
                  MedicionValoracion medicionProgramado = (MedicionValoracion)iterMedicionProgramado.next();
                  if (medicionProgramado.getValor() != null) {
                    ultimoProgramado = medicionProgramado.getValor();
                  }
                  if ((medicionProgramado.getMedicionId().getPeriodo().intValue() == medicionReal.getMedicionId().getPeriodo().intValue()) && 
                    (medicionProgramado.getMedicionId().getAno().intValue() == medicionReal.getMedicionId().getAno().intValue()) && (medicionProgramado.getValor() != null))
                  {
                    programado = medicionProgramado.getValor();
                    break;
                  }
                }
                for (Iterator<MedicionValoracion> iterMedicionProgramado = medicionesProgramadasInferior.iterator(); iterMedicionProgramado.hasNext();)
                {
                  MedicionValoracion medicionProgramado = (MedicionValoracion)iterMedicionProgramado.next();
                  if ((medicionProgramado.getMedicionId().getPeriodo().intValue() == medicionReal.getMedicionId().getPeriodo().intValue()) && 
                    (medicionProgramado.getMedicionId().getAno().intValue() == medicionReal.getMedicionId().getAno().intValue()) && (medicionProgramado.getValor() != null))
                  {
                    programadoInferior = medicionProgramado.getValor();
                    break;
                  }
                }
              }
            }
            if ((desdeActividad.booleanValue()) && (programado == null)) {
              programado = ultimoProgramado;
            }
            Byte alerta = null;
            if ((ejecutado != null) && (programado != null))
            {
              Double zonaVerde = strategosIndicadoresService.zonaVerde(indicador, medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getPeriodo(), programado, (StrategosMedicionesService) this);
              Double zonaAmarilla = strategosIndicadoresService.zonaAmarilla(indicador, medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getPeriodo(), programado, zonaVerde, (StrategosMedicionesService) this);
              Double ejecutadoAnterior = null;
              if (index == 0)
              {
                int anoAnterior = medicionReal.getMedicionId().getAno().intValue();
                int periodoAnterior = medicionReal.getMedicionId().getPeriodo().intValue();
                int numeroMaximoPorPeriodo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), anoAnterior);
                if (periodoAnterior == numeroMaximoPorPeriodo)
                {
                  anoAnterior--;
                  periodoAnterior = 1;
                }
                else
                {
                  periodoAnterior--;
                }
                List<MedicionValoracion> medicionesEjecutadoAnterior = getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieRealId(), Integer.valueOf(anoAnterior), Integer.valueOf(anoAnterior), Integer.valueOf(periodoAnterior), Integer.valueOf(periodoAnterior), null);
                if (medicionesEjecutadoAnterior.size() > 0) {
                  ejecutadoAnterior = ((MedicionValoracion)medicionesEjecutadoAnterior.get(0)).getValor();
                }
              }
              else
              {
                ejecutadoAnterior = ((MedicionValoracion)medicionesReales.get(index - 1)).getValor();
              }
              alerta = new Servicio().calcularAlertaXPeriodos(Servicio.EjecutarTipo.getEjecucionAlertaXPeriodos(), indicador.getCaracteristica(), zonaVerde, zonaAmarilla, ejecutado, programado, programadoInferior, ejecutadoAnterior);
            }
            MedicionValoracion medicion = new MedicionValoracion(new MedicionPK(medicionReal.getMedicionId().getIndicadorId(), medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getPeriodo(), SerieTiempo.getSerieAlerta()), AlertaIndicador.ConvertToDouble(alerta), new Boolean(false));
            if (medicion.getValor() == null)
            {
              resultado = this.persistenceSession.deleteMedicion(medicion);
              if (resultado == 10001) {
                resultado = 10000;
              }
            }
            else
            {
              fieldNames[0] = "medicionId.indicadorId";
              fieldValues[0] = medicion.getMedicionId().getIndicadorId();
              fieldNames[1] = "medicionId.serieId";
              fieldValues[1] = medicion.getMedicionId().getSerieId();
              fieldNames[2] = "medicionId.ano";
              fieldValues[2] = medicion.getMedicionId().getAno();
              fieldNames[3] = "medicionId.periodo";
              fieldValues[3] = medicion.getMedicionId().getPeriodo();
              if (!this.persistenceSession.existsObject(medicion, fieldNames, fieldValues)) {
                resultado = this.persistenceSession.insert(medicion, usuario);
              } else {
                resultado = this.persistenceSession.update(medicion, usuario);
              }
            }
            if (resultado != 10000) {
              break;
            }
            index++;
          }
        }
        else
        {
          medicionesReales = getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieRealId(), new Integer(0), new Integer(9999), new Integer(0), new Integer(999), null);
          if (medicionesReales.size() == 0) {
            for (Iterator<MedicionValoracion> iterMedicionProgramada = medicionesProgramadas.iterator(); iterMedicionProgramada.hasNext();)
            {
              MedicionValoracion medicionProgramada = (MedicionValoracion)iterMedicionProgramada.next();
              MedicionValoracion medicion = new MedicionValoracion(new MedicionPK(medicionProgramada.getMedicionId().getIndicadorId(), medicionProgramada.getMedicionId().getAno(), medicionProgramada.getMedicionId().getPeriodo(), SerieTiempo.getSerieAlerta()), null, new Boolean(false));
              resultado = this.persistenceSession.deleteMedicion(medicion);
              if (resultado == 10001) {
                resultado = 10000;
              }
              if (resultado != 10000) {
                break;
              }
            }
          }
        }
      }
      strategosIndicadoresService.close();
      if (resultado == 10000)
      {
        this.persistenceSession.commitTransaction();
        transActiva = false;
      }
      else if (transActiva)
      {
        this.persistenceSession.rollbackTransaction();
        transActiva = false;
      }
    }
    catch (Throwable t)
    {
      if (transActiva) {
        this.persistenceSession.rollbackTransaction();
      }
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    return resultado;
  }
  
  public MedicionValoracion getUltimaMedicionConValor(List<MedicionValoracion> mediciones)
  {
    MedicionValoracion medicion = null;
    for (Iterator<MedicionValoracion> iterMedicion = mediciones.iterator(); iterMedicion.hasNext();)
    {
      MedicionValoracion med = (MedicionValoracion)iterMedicion.next();
      if (med.getValor() != null) {
        medicion = med;
      }
    }
    return medicion;
  }
  
  public MedicionValoracion getUltimaMedicionConValorEnUnPeriodo(List<MedicionValoracion> mediciones, Integer ano, Integer periodo)
  {
    MedicionValoracion medicion = new MedicionValoracion();
    DecimalFormat nf3 = new DecimalFormat("#000");
    int anoPeriodoBuscar = Integer.parseInt(ano.toString() + nf3.format(periodo).toString());
    for (Iterator<MedicionValoracion> iterMedicion = mediciones.iterator(); iterMedicion.hasNext();)
    {
      MedicionValoracion med = (MedicionValoracion)iterMedicion.next();
      int anoPeriodo = Integer.parseInt(med.getMedicionId().getAno().toString() + nf3.format(med.getMedicionId().getPeriodo()).toString());
      if ((med.getValor() != null) && (anoPeriodo <= anoPeriodoBuscar)) {
        medicion = med;
      }
    }
    return medicion;
  }
  
  public List<MedicionValoracion> getMedicionesPeriodoExactas(Long indicadorId, Long serieId, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal)
  {
    return this.persistenceSession.getMedicionesPeriodoExactas(indicadorId, serieId, anoInicio, anoFinal, periodoInicio, periodoFinal);
  }
  
  public List<MedicionValoracion> getMedicionesPeriodoPorFrecuencia(Long indicadorId, Byte frecuencia, Long serieId, Integer anoInicio, Integer anoFinal, Integer periodoInicio, Integer periodoFinal, List<MedicionValoracion> medicionesExistentes)
  {
    return this.persistenceSession.getMedicionesPeriodoPorFrecuencia(indicadorId, frecuencia, serieId, anoInicio, anoFinal, periodoInicio, periodoFinal, medicionesExistentes);
  }
  
  public List<MedicionValoracion> getMedicionesIndicadores(List<Indicador> indicadores, Long serieId, Integer anoInical, Integer anoFinal)
  {
    Locale currentLocale = new Locale("en", "US");
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
    DecimalFormat decimalformat = (DecimalFormat)numberFormatter;
    decimalformat.applyPattern("0.0000000000");
    
    List<Long> indicadoresIds = new ArrayList();
    for (Iterator<Indicador> i = indicadores.iterator(); i.hasNext();)
    {
      Indicador indicador = (Indicador)i.next();
      indicadoresIds.add(indicador.getIndicadorId());
    }
    List<MedicionValoracion> mediciones = this.persistenceSession.getMedicionesIndicadores(indicadoresIds, serieId, anoInical, anoFinal);
    Integer anoAnterior = anoInical != null ? Integer.valueOf(anoInical.intValue() - 1) : null;
    List<MedicionValoracion> medicionesAnoAnteriores = this.persistenceSession.getMedicionesIndicadores(indicadoresIds, serieId, anoAnterior, anoAnterior);
    
    List<MedicionValoracion> medicionesResult = new ArrayList();
    List<MedicionValoracion> medicionesIndicador = new ArrayList();
    for (Iterator<Indicador> i = indicadores.iterator(); i.hasNext();)
    {
      Indicador indicador = (Indicador)i.next();
      medicionesIndicador = new ArrayList();
      Integer anoActual = null;
      Long serieIdActual = null;
      Boolean nuevoAno = null;
      for (Iterator<MedicionValoracion> med = mediciones.iterator(); med.hasNext();)
      {
        MedicionValoracion medicion = (MedicionValoracion)med.next();
        if (medicion.getMedicionId().getIndicadorId().longValue() == indicador.getIndicadorId().longValue())
        {
          MedicionValoracion medicionInterna = null;
          if (indicador.getCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue())
          {
            if ((indicador.getTipoCargaMedicion() != null) && (indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue()))
            {
              Integer anoMedicion = medicion.getMedicionId().getAno();
              Integer periodo = medicion.getMedicionId().getPeriodo();
              anoActual = anoActual == null ? anoMedicion : anoActual;
              serieIdActual = serieIdActual == null ? medicion.getMedicionId().getSerieId() : serieIdActual;
              if (anoMedicion.intValue() != anoActual.intValue())
              {
                anoActual = anoMedicion;
                nuevoAno = Boolean.valueOf(true);
              }
              else if (serieIdActual.longValue() != medicion.getMedicionId().getSerieId().longValue())
              {
                anoActual = anoMedicion;
                serieIdActual = medicion.getMedicionId().getSerieId();
                nuevoAno = Boolean.valueOf(true);
              }
              else
              {
                nuevoAno = Boolean.valueOf(false);
              }
              int numeroPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia().byteValue(), anoMedicion.intValue());
              periodo = Integer.valueOf(periodo.intValue() - 1);
              if (periodo.intValue() == 0)
              {
                periodo = Integer.valueOf(numeroPeriodos);
                anoMedicion = Integer.valueOf(anoMedicion.intValue() - 1);
                nuevoAno = Boolean.valueOf(true);
              }
              MedicionValoracion medicionExist = this.persistenceSession.getUltimaMedicionByAnoByPeriodo(medicionesIndicador, medicion.getMedicionId().getIndicadorId(), medicion.getMedicionId().getSerieId(), anoMedicion, periodo, Boolean.valueOf(false));
              if (medicionExist == null) {
                medicionExist = this.persistenceSession.getUltimaMedicionByAnoByPeriodo(mediciones, medicion.getMedicionId().getIndicadorId(), medicion.getMedicionId().getSerieId(), anoMedicion, periodo, Boolean.valueOf(false));
              }
              if (medicionExist == null) {
                medicionExist = this.persistenceSession.getUltimaMedicionByAnoByPeriodo(medicionesAnoAnteriores, medicion.getMedicionId().getIndicadorId(), medicion.getMedicionId().getSerieId(), anoMedicion, periodo, Boolean.valueOf(true));
              }
              if (medicionExist == null) {
                medicionExist = this.persistenceSession.getUltimaMedicion(medicionesIndicador);
              }
              Double valor = medicion.getValor();
              if ((!nuevoAno.booleanValue()) && (medicionExist != null) && (medicionExist.getValor() != null)) {
                valor = Double.valueOf(valor.doubleValue() + medicionExist.getValor().doubleValue());
              }
              medicionInterna = new MedicionValoracion();
              MedicionPK medicionPk = new MedicionPK();
              medicionPk.setIndicadorId(medicion.getMedicionId().getIndicadorId());
              medicionPk.setSerieId(medicion.getMedicionId().getSerieId());
              medicionPk.setAno(medicion.getMedicionId().getAno());
              medicionPk.setPeriodo(medicion.getMedicionId().getPeriodo());
              medicionInterna.setMedicionId(medicionPk);
              medicionInterna.setProtegido(medicion.getProtegido());
              medicionInterna.setProtegido(medicion.getProtegido());
              medicionInterna.setValor(valor);
              medicionInterna.setValorString(decimalformat.format(valor));
              medicionInterna.setSerieIndicador(medicion.getSerieIndicador());
            }
            else
            {
              medicionInterna = new MedicionValoracion();
              MedicionPK medicionPk = new MedicionPK();
              medicionPk.setIndicadorId(medicion.getMedicionId().getIndicadorId());
              medicionPk.setSerieId(medicion.getMedicionId().getSerieId());
              medicionPk.setAno(medicion.getMedicionId().getAno());
              medicionPk.setPeriodo(medicion.getMedicionId().getPeriodo());
              medicionInterna.setMedicionId(medicionPk);
              medicionInterna.setProtegido(medicion.getProtegido());
              medicionInterna.setProtegido(medicion.getProtegido());
              medicionInterna.setValor(medicion.getValor());
              medicionInterna.setValorString(medicion.getValorString());
              medicionInterna.setSerieIndicador(medicion.getSerieIndicador());
            }
          }
          else
          {
            medicionInterna = new MedicionValoracion();
            MedicionPK medicionPk = new MedicionPK();
            medicionPk.setIndicadorId(medicion.getMedicionId().getIndicadorId());
            medicionPk.setSerieId(medicion.getMedicionId().getSerieId());
            medicionPk.setAno(medicion.getMedicionId().getAno());
            medicionPk.setPeriodo(medicion.getMedicionId().getPeriodo());
            medicionInterna.setMedicionId(medicionPk);
            medicionInterna.setProtegido(medicion.getProtegido());
            medicionInterna.setValor(medicion.getValor());
            medicionInterna.setValorString(medicion.getValorString());
            medicionInterna.setSerieIndicador(medicion.getSerieIndicador());
          }
          if (medicionInterna != null) {
            medicionesIndicador.add(medicionInterna);
          }
        }
      }
      medicionesResult.addAll(medicionesIndicador);
    }
    return medicionesResult;
  }
  
  public List<MedicionValoracion> getMedicionesBySerie(List<MedicionValoracion> mediciones, Long indicadorId, Long serieId)
  {
    return this.persistenceSession.getMedicionesBySerie(mediciones, indicadorId, serieId);
  }
  
  public MedicionValoracion getUltimaMedicion(List<MedicionValoracion> mediciones)
  {
    return this.persistenceSession.getUltimaMedicion(mediciones);
  }
  
  public double getUltimaMedicionAcumulada(List<MedicionValoracion> mediciones)
  {
    return this.persistenceSession.getUltimaMedicionAcumulada(mediciones);
  }
  
  
  
  public MedicionValoracion getUltimaMedicionByAno(List<MedicionValoracion> mediciones, Integer ano)
  {
    return this.persistenceSession.getUltimaMedicionByAno(mediciones, ano);
  }
  
  public MedicionValoracion getUltimaMedicionByAnoByPeriodo(List<MedicionValoracion> mediciones, Integer ano, Integer periodo)
  {
    Long indicadorId = ((MedicionValoracion)mediciones.get(0)).getMedicionId().getIndicadorId();
    Long serieId = ((MedicionValoracion)mediciones.get(0)).getMedicionId().getSerieId();
    
    return this.persistenceSession.getUltimaMedicionByAnoByPeriodo(mediciones, indicadorId, serieId, ano, periodo, Boolean.valueOf(true));
  }


  public double getValorAcumulado(Long indicadorId, Long serieId) {
	return this.persistenceSession.getValorAcumulado(indicadorId, serieId);
  }



}
