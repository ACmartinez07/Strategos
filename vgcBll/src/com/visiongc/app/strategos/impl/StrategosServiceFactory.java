package com.visiongc.app.strategos.impl;

import com.visiongc.app.strategos.StrategosConfiguration;
import com.visiongc.app.strategos.StrategosService;
import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.categoriasmedicion.impl.StrategosCategoriasServiceImpl;
import com.visiongc.app.strategos.causas.StrategosCausasService;
import com.visiongc.app.strategos.causas.impl.StrategosCausasServiceImpl;
import com.visiongc.app.strategos.estadosacciones.StrategosEstadosService;
import com.visiongc.app.strategos.estadosacciones.impl.StrategosEstadosServiceImpl;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.impl.StrategosExplicacionesServiceImpl;
import com.visiongc.app.strategos.foros.StrategosForosService;
import com.visiongc.app.strategos.foros.impl.StrategosForosServiceImpl;
import com.visiongc.app.strategos.graficos.StrategosGraficosService;
import com.visiongc.app.strategos.graficos.impl.StrategosGraficosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadorAsignarInventarioService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesValoracionService;
import com.visiongc.app.strategos.indicadores.impl.StrategosClasesIndicadoresServiceImpl;
import com.visiongc.app.strategos.indicadores.impl.StrategosIndicadorAsignarInventarioServiceImpl;
import com.visiongc.app.strategos.indicadores.impl.StrategosIndicadoresServiceImpl;
import com.visiongc.app.strategos.indicadores.impl.StrategosMedicionesServiceImpl;
import com.visiongc.app.strategos.indicadores.impl.StrategosMedicionesValoracionServiceImpl;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.impl.StrategosIniciativaEstatusServiceImpl;
import com.visiongc.app.strategos.iniciativas.impl.StrategosIniciativasServiceImpl;
import com.visiongc.app.strategos.iniciativas.impl.StrategosTipoProyectoServiceImpl;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.impl.StrategosCooperantesServiceImpl;
import com.visiongc.app.strategos.instrumentos.impl.StrategosInstrumentosServiceImpl;
import com.visiongc.app.strategos.instrumentos.impl.StrategosTiposConvenioServiceImpl;
import com.visiongc.app.strategos.modulo.codigoenlace.StrategosCodigoEnlaceService;
import com.visiongc.app.strategos.modulo.codigoenlace.impl.StrategosCodigoEnlaceServiceImpl;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.impl.StrategosOrganizacionesServiceImpl;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSessionFactory;
import com.visiongc.app.strategos.plancuentas.StrategosCuentasService;
import com.visiongc.app.strategos.plancuentas.impl.StrategosCuentasServiceImpl;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosModelosService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.StrategosPlantillasPlanesService;
import com.visiongc.app.strategos.planes.impl.StrategosMetasServiceImpl;
import com.visiongc.app.strategos.planes.impl.StrategosModelosServiceImpl;
import com.visiongc.app.strategos.planes.impl.StrategosPerspectivasServiceImpl;
import com.visiongc.app.strategos.planes.impl.StrategosPlanesServiceImpl;
import com.visiongc.app.strategos.planes.impl.StrategosPlantillasPlanesServiceImpl;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.impl.StrategosPrdProductosServiceImpl;
import com.visiongc.app.strategos.planificacionseguimiento.impl.StrategosPryActividadesServiceImpl;
import com.visiongc.app.strategos.planificacionseguimiento.impl.StrategosPryProyectosServiceImpl;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.impl.StrategosPortafoliosServiceImpl;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.StrategosPaginasService;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.impl.StrategosCeldasServiceImpl;
import com.visiongc.app.strategos.presentaciones.impl.StrategosPaginasServiceImpl;
import com.visiongc.app.strategos.presentaciones.impl.StrategosVistasServiceImpl;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.StrategosClasesProblemasService;
import com.visiongc.app.strategos.problemas.StrategosProblemasService;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.impl.StrategosAccionesServiceImpl;
import com.visiongc.app.strategos.problemas.impl.StrategosClasesProblemasServiceImpl;
import com.visiongc.app.strategos.problemas.impl.StrategosProblemasServiceImpl;
import com.visiongc.app.strategos.problemas.impl.StrategosSeguimientosServiceImpl;
import com.visiongc.app.strategos.reportes.StrategosReportesGraficoService;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.reportes.impl.StrategosReportesGraficoServiceImpl;
import com.visiongc.app.strategos.reportes.impl.StrategosReportesServiceImpl;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.impl.StrategosResponsablesServiceImpl;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.impl.StrategosSeriesTiempoServiceImpl;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.impl.StrategosUnidadesServiceImpl;
import com.visiongc.app.strategos.vistasdatos.StrategosVistasDatosService;
import com.visiongc.app.strategos.vistasdatos.impl.StrategosVistasDatosServiceImpl;
import com.visiongc.commons.impl.VgcDefaultServiceFactory;
import com.visiongc.commons.impl.VgcServiceFactory;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.util.lang.ChainedRuntimeException;

public class StrategosServiceFactory extends VgcDefaultServiceFactory implements VgcServiceFactory
{
  private StrategosPersistenceSessionFactory persistenceSessionFactory = null;
  private static StrategosServiceFactory instance = null;
  
  public static StrategosServiceFactory getInstance()
  {
    if (instance == null) {
      instance = new StrategosServiceFactory();
    }
    return instance;
  }
  
  public StrategosServiceFactory()
  {
    try
    {
      persistenceSessionFactory = StrategosConfiguration.getInstance().getStrategosPersistenceSessionFactory();
    }
    catch (Throwable ex)
    {
      throw new ChainedRuntimeException("No se pudo inicializar la factoría de Servicios de Framework", ex);
    }
  }
  
  public StrategosUnidadesService openStrategosUnidadesService()
  {
    return new StrategosUnidadesServiceImpl(persistenceSessionFactory.openUnidadesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosUnidadesService openStrategosUnidadesService(StrategosService strategosService)
  {
    return new StrategosUnidadesServiceImpl(persistenceSessionFactory.openUnidadesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCategoriasService openStrategosCategoriasService()
  {
    return new StrategosCategoriasServiceImpl(persistenceSessionFactory.openCategoriasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCategoriasService openStrategosCategoriasService(StrategosService strategosService) {
    return new StrategosCategoriasServiceImpl(persistenceSessionFactory.openCategoriasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosResponsablesService openStrategosResponsablesService()
  {
    return new StrategosResponsablesServiceImpl(persistenceSessionFactory.openResponsablesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosResponsablesService openStrategosResponsablesService(StrategosService strategosService) {
    return new StrategosResponsablesServiceImpl(persistenceSessionFactory.openResponsablesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCuentasService openStrategosCuentasService()
  {
    return new StrategosCuentasServiceImpl(persistenceSessionFactory.openCuentasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCuentasService openStrategosCuentasService(StrategosService strategosService) {
    return new StrategosCuentasServiceImpl(persistenceSessionFactory.openCuentasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCausasService openStrategosCausasService()
  {
    return new StrategosCausasServiceImpl(persistenceSessionFactory.openCausasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCausasService openStrategosCausasService(StrategosService strategosService) {
    return new StrategosCausasServiceImpl(persistenceSessionFactory.openCausasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosEstadosService openStrategosEstadosService()
  {
    return new StrategosEstadosServiceImpl(persistenceSessionFactory.openEstadosPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosEstadosService openStrategosEstadosService(StrategosService strategosService) {
    return new StrategosEstadosServiceImpl(persistenceSessionFactory.openEstadosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosOrganizacionesService openStrategosOrganizacionesService()
  {
    return new StrategosOrganizacionesServiceImpl(persistenceSessionFactory.openOrganizacionesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosOrganizacionesService openStrategosOrganizacionesService(StrategosService strategosService) {
    return new StrategosOrganizacionesServiceImpl(persistenceSessionFactory.openOrganizacionesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosClasesIndicadoresService openStrategosClasesIndicadoresService()
  {
    return new StrategosClasesIndicadoresServiceImpl(persistenceSessionFactory.openClasesIndicadoresPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosClasesIndicadoresService openStrategosClasesIndicadoresService(StrategosService strategosService) {
    return new StrategosClasesIndicadoresServiceImpl(persistenceSessionFactory.openClasesIndicadoresPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosIndicadoresService openStrategosIndicadoresService()
  {
    return new StrategosIndicadoresServiceImpl(persistenceSessionFactory.openIndicadoresPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosIndicadoresService openStrategosIndicadoresService(StrategosService strategosService) {
    return new StrategosIndicadoresServiceImpl(persistenceSessionFactory.openIndicadoresPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosIndicadorAsignarInventarioService openStrategosIndicadorAsignarInventarioService()
  {
    return new StrategosIndicadorAsignarInventarioServiceImpl(persistenceSessionFactory.openIndicadorAsignarInventarioPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosIndicadorAsignarInventarioService openStrategosIndicadorAsignarInventarioService(StrategosService strategosService) {
    return new StrategosIndicadorAsignarInventarioServiceImpl(persistenceSessionFactory.openIndicadorAsignarInventarioPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosMedicionesService openStrategosMedicionesService()
  {
    return new StrategosMedicionesServiceImpl(persistenceSessionFactory.openMedicionesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosMedicionesService openStrategosMedicionesService(StrategosService strategosService) {
    return new StrategosMedicionesServiceImpl(persistenceSessionFactory.openMedicionesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosMedicionesValoracionService openStrategosMedicionesValoracionService()
  {
    return new StrategosMedicionesValoracionServiceImpl(persistenceSessionFactory.openMedicionesValoracionPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosMedicionesValoracionService openStrategosMedicionesValoracionService(StrategosService strategosService) {
    return new StrategosMedicionesValoracionServiceImpl(persistenceSessionFactory.openMedicionesValoracionPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosMetasService openStrategosMetasService()
  {
    return new StrategosMetasServiceImpl(persistenceSessionFactory.openMetasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosMetasService openStrategosMetasService(StrategosService strategosService) {
    return new StrategosMetasServiceImpl(persistenceSessionFactory.openMetasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosSeriesTiempoService openStrategosSeriesTiempoService()
  {
    return new StrategosSeriesTiempoServiceImpl(persistenceSessionFactory.openSeriesTiempoPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosSeriesTiempoService openStrategosSeriesTiempoService(StrategosService strategosService) {
    return new StrategosSeriesTiempoServiceImpl(persistenceSessionFactory.openSeriesTiempoPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPlanesService openStrategosPlanesService()
  {
    return new StrategosPlanesServiceImpl(persistenceSessionFactory.openPlanesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPlanesService openStrategosPlanesService(StrategosService strategosService) {
    return new StrategosPlanesServiceImpl(persistenceSessionFactory.openPlanesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPlantillasPlanesService openStrategosPlantillasPlanesService()
  {
    return new StrategosPlantillasPlanesServiceImpl(persistenceSessionFactory.openPlantillasPlanesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPlantillasPlanesService openStrategosPlantillasPlanesService(StrategosService strategosService) {
    return new StrategosPlantillasPlanesServiceImpl(persistenceSessionFactory.openPlantillasPlanesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosIniciativasService openStrategosIniciativasService()
  {
    return new StrategosIniciativasServiceImpl(persistenceSessionFactory.openIniciativasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosIniciativasService openStrategosIniciativasService(StrategosService strategosService) {
    return new StrategosIniciativasServiceImpl(persistenceSessionFactory.openIniciativasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPryProyectosService openStrategosPryProyectosService()
  {
    return new StrategosPryProyectosServiceImpl(persistenceSessionFactory.openPryProyectosPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPryProyectosService openStrategosPryProyectosService(StrategosService strategosService) {
    return new StrategosPryProyectosServiceImpl(persistenceSessionFactory.openPryProyectosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPryProyectosService openStrategosProyectosService(StrategosService strategosService) {
    return new StrategosPryProyectosServiceImpl(persistenceSessionFactory.openPryProyectosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosProblemasService openStrategosProblemasService()
  {
    return new StrategosProblemasServiceImpl(persistenceSessionFactory.openProblemasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosProblemasService openStrategosProblemasService(StrategosService strategosService) {
    return new StrategosProblemasServiceImpl(persistenceSessionFactory.openProblemasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosClasesProblemasService openStrategosClasesProblemasService()
  {
    return new StrategosClasesProblemasServiceImpl(persistenceSessionFactory.openClasesProblemasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosClasesProblemasService openStrategosClasesProblemasService(StrategosService strategosService) {
    return new StrategosClasesProblemasServiceImpl(persistenceSessionFactory.openClasesProblemasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosSeguimientosService openStrategosSeguimientosService()
  {
    return new StrategosSeguimientosServiceImpl(persistenceSessionFactory.openSeguimientosPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosSeguimientosService openStrategosSeguimientosService(StrategosService strategosService) {
    return new StrategosSeguimientosServiceImpl(persistenceSessionFactory.openSeguimientosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosAccionesService openStrategosAccionesService()
  {
    return new StrategosAccionesServiceImpl(persistenceSessionFactory.openAccionesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosAccionesService openStrategosAccionesService(StrategosService strategosService) {
    return new StrategosAccionesServiceImpl(persistenceSessionFactory.openAccionesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosVistasService openStrategosVistasService()
  {
    return new StrategosVistasServiceImpl(persistenceSessionFactory.openVistasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosVistasService openStrategosVistasService(StrategosService strategosService) {
    return new StrategosVistasServiceImpl(persistenceSessionFactory.openVistasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPaginasService openStrategosPaginasService()
  {
    return new StrategosPaginasServiceImpl(persistenceSessionFactory.openPaginasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPaginasService openStrategosPaginasService(StrategosService strategosService) {
    return new StrategosPaginasServiceImpl(persistenceSessionFactory.openPaginasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCeldasService openStrategosCeldasService()
  {
    return new StrategosCeldasServiceImpl(persistenceSessionFactory.openCeldasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCeldasService openStrategosCeldasService(StrategosService strategosService) {
    return new StrategosCeldasServiceImpl(persistenceSessionFactory.openCeldasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosExplicacionesService openStrategosExplicacionesService()
  {
    return new StrategosExplicacionesServiceImpl(persistenceSessionFactory.openExplicacionesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosExplicacionesService openStrategosExplicacionesService(StrategosService strategosService) {
    return new StrategosExplicacionesServiceImpl(persistenceSessionFactory.openExplicacionesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosForosService openStrategosForosService()
  {
    return new StrategosForosServiceImpl(persistenceSessionFactory.openForosPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosForosService openStrategosForosService(StrategosService strategosService) {
    return new StrategosForosServiceImpl(persistenceSessionFactory.openForosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPortafoliosService openStrategosPortafoliosService()
  {
    return new StrategosPortafoliosServiceImpl(persistenceSessionFactory.openPortafoliosPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPortafoliosService openStrategosPortafoliosService(StrategosService strategosService) {
    return new StrategosPortafoliosServiceImpl(persistenceSessionFactory.openPortafoliosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPryActividadesService openStrategosPryActividadesService()
  {
    return new StrategosPryActividadesServiceImpl(persistenceSessionFactory.openPryActividadesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPryActividadesService openStrategosPryActividadesService(StrategosService strategosService) {
    return new StrategosPryActividadesServiceImpl(persistenceSessionFactory.openPryActividadesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPrdProductosService openStrategosPrdProductosService()
  {
    return new StrategosPrdProductosServiceImpl(persistenceSessionFactory.openPrdProductosPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPrdProductosService openStrategosPrdProductosService(StrategosService strategosService) {
    return new StrategosPrdProductosServiceImpl(persistenceSessionFactory.openPrdProductosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPerspectivasService openStrategosPerspectivasService()
  {
    return new StrategosPerspectivasServiceImpl(persistenceSessionFactory.openPerspectivasPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosPerspectivasService openStrategosPerspectivasService(StrategosService strategosService) {
    return new StrategosPerspectivasServiceImpl(persistenceSessionFactory.openPerspectivasPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosModelosService openStrategosModelosService()
  {
    return new StrategosModelosServiceImpl(persistenceSessionFactory.openModelosPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosModelosService openStrategosModelosService(StrategosService strategosService) {
    return new StrategosModelosServiceImpl(persistenceSessionFactory.openModelosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosVistasDatosService openStrategosVistasDatosService()
  {
    return new StrategosVistasDatosServiceImpl(persistenceSessionFactory.openVistasDatosPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosVistasDatosService openStrategosVistasDatosService(StrategosService strategosService) {
    return new StrategosVistasDatosServiceImpl(persistenceSessionFactory.openVistasDatosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosReportesService openStrategosReportesService()
  {
    return new StrategosReportesServiceImpl(persistenceSessionFactory.openReportesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosReportesService openStrategosReportesService(StrategosService strategosService)
  {
    return new StrategosReportesServiceImpl(persistenceSessionFactory.openReportesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosReportesGraficoService openStrategosReportesGraficoService()
  {
    return new StrategosReportesGraficoServiceImpl(persistenceSessionFactory.openReportesGraficoPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosReportesGraficoService openStrategosReportesGraficoService(StrategosService strategosService)
  {
    return new StrategosReportesGraficoServiceImpl(persistenceSessionFactory.openReportesGraficoPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosGraficosService openStrategosGraficosService()
  {
    return new StrategosGraficosServiceImpl(persistenceSessionFactory.openGraficosPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosGraficosService openStrategosGraficosService(StrategosService strategosService)
  {
    return new StrategosGraficosServiceImpl(persistenceSessionFactory.openGraficosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosIniciativaEstatusService openStrategosIniciativaEstatusService()
  {
    return new StrategosIniciativaEstatusServiceImpl(persistenceSessionFactory.openIniciativaEstatusPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosIniciativaEstatusService openStrategosIniciativaEstatusService(StrategosService strategosService)
  {
    return new StrategosIniciativaEstatusServiceImpl(persistenceSessionFactory.openIniciativaEstatusPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
    
  public StrategosCodigoEnlaceService openStrategosCodigoEnlaceService()
  {
    return new StrategosCodigoEnlaceServiceImpl(persistenceSessionFactory.openCodigoEnlacePersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCodigoEnlaceService openStrategosCodigoEnlaceService(StrategosService strategosService)
  {
    return new StrategosCodigoEnlaceServiceImpl(persistenceSessionFactory.openCodigoEnlacePersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosTipoProyectoService openStrategosTipoProyectoService()
  {
    return new StrategosTipoProyectoServiceImpl(persistenceSessionFactory.openTipoProyectoPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosTipoProyectoService openStrategosTipoProyectoService(StrategosService strategosService)
  {
    return new StrategosTipoProyectoServiceImpl(persistenceSessionFactory.openTipoProyectoPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCooperantesService openStrategosCooperantesService()
  {
    return new StrategosCooperantesServiceImpl(persistenceSessionFactory.openCooperantesPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosCooperantesService openStrategosCooperantesService(StrategosService strategosService) {
    return new StrategosCooperantesServiceImpl(persistenceSessionFactory.openCooperantesPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosTiposConvenioService openStrategosTiposConvenioService()
  {
    return new StrategosTiposConvenioServiceImpl(persistenceSessionFactory.openTiposConvenioPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosTiposConvenioService openStrategosTiposConvenioService(StrategosService strategosService) {
    return new StrategosTiposConvenioServiceImpl(persistenceSessionFactory.openTiposConvenioPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
    
  public StrategosInstrumentosService openStrategosInstrumentosService()	
  {
    return new StrategosInstrumentosServiceImpl(persistenceSessionFactory.openInstrumentosPersistenceSession(), true, this, VgcResourceManager.getMessageResources("Strategos"));
  }
  
  public StrategosInstrumentosService openStrategosInstrumentosService(StrategosService strategosService) {
    return new StrategosInstrumentosServiceImpl(persistenceSessionFactory.openInstrumentosPersistenceSession(strategosService.getStrategosPersistenceSession()), false, this, VgcResourceManager.getMessageResources("Strategos"));
  }
}
