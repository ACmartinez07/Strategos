package com.visiongc.app.strategos.persistence.hibernate;

import com.visiongc.app.strategos.categoriasmedicion.persistence.StrategosCategoriasPersistenceSession;
import com.visiongc.app.strategos.categoriasmedicion.persistence.hibernate.StrategosCategoriasHibernateSession;
import com.visiongc.app.strategos.causas.persistence.StrategosCausasPersistenceSession;
import com.visiongc.app.strategos.causas.persistence.hibernate.StrategosCausasHibernateSession;
import com.visiongc.app.strategos.estadosacciones.persistence.StrategosEstadosPersistenceSession;
import com.visiongc.app.strategos.estadosacciones.persistence.hibernate.StrategosEstadosHibernateSession;
import com.visiongc.app.strategos.explicaciones.persistence.StrategosExplicacionesPersistenceSession;
import com.visiongc.app.strategos.explicaciones.persistence.hibernate.StrategosExplicacionesHibernateSession;
import com.visiongc.app.strategos.foros.persistence.StrategosForosPersistenceSession;
import com.visiongc.app.strategos.foros.persistence.hibernate.StrategosForosHibernateSession;
import com.visiongc.app.strategos.graficos.persistence.StrategosGraficosPersistenceSession;
import com.visiongc.app.strategos.graficos.persistence.hibernate.StrategosGraficosHibernateSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosClasesIndicadoresPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosIndicadorAsignarInventarioPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosIndicadoresPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.StrategosMedicionesValoracionPersistenceSession;
import com.visiongc.app.strategos.indicadores.persistence.hibernate.StrategosClasesIndicadoresHibernateSession;
import com.visiongc.app.strategos.indicadores.persistence.hibernate.StrategosIndicadorAsignarInventarioHibernateSession;
import com.visiongc.app.strategos.indicadores.persistence.hibernate.StrategosIndicadoresHibernateSession;
import com.visiongc.app.strategos.indicadores.persistence.hibernate.StrategosMedicionesHibernateSession;
import com.visiongc.app.strategos.indicadores.persistence.hibernate.StrategosMedicionesValoracionHibernateSession;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosIniciativaEstatusPersistenceSession;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosIniciativasPersistenceSession;
import com.visiongc.app.strategos.iniciativas.persistence.StrategosTipoProyectoPersistenceSession;
import com.visiongc.app.strategos.iniciativas.persistence.hibernate.StrategosIniciativaEstatusHibernateSession;
import com.visiongc.app.strategos.iniciativas.persistence.hibernate.StrategosIniciativasHibernateSession;
import com.visiongc.app.strategos.iniciativas.persistence.hibernate.StrategosTipoProyectoHibernateSession;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosCooperantesPersistenceSession;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosInstrumentosPersistenceSession;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosTiposConvenioPersistenceSession;
import com.visiongc.app.strategos.instrumentos.persistence.hibernate.StrategosCooperantesHibernateSession;
import com.visiongc.app.strategos.instrumentos.persistence.hibernate.StrategosInstrumentosHibernateSession;
import com.visiongc.app.strategos.instrumentos.persistence.hibernate.StrategosTiposConvenioHibernateSession;
import com.visiongc.app.strategos.modulo.codigoenlace.persistence.StrategosCodigoEnlacePersistenceSession;
import com.visiongc.app.strategos.modulo.codigoenlace.persistence.hibernate.StrategosCodigoEnlaceHibernateSession;
import com.visiongc.app.strategos.organizaciones.persistence.StrategosOrganizacionesPersistenceSession;
import com.visiongc.app.strategos.organizaciones.persistence.hibernate.StrategosOrganizacionesHibernateSession;
import com.visiongc.app.strategos.persistence.StrategosPersistenceSession;
import com.visiongc.app.strategos.plancuentas.persistence.StrategosCuentasPersistenceSession;
import com.visiongc.app.strategos.plancuentas.persistence.hibernate.StrategosCuentasHibernateSession;
import com.visiongc.app.strategos.planes.persistence.StrategosMetasPersistenceSession;
import com.visiongc.app.strategos.planes.persistence.StrategosModelosPersistenceSession;
import com.visiongc.app.strategos.planes.persistence.StrategosPerspectivasPersistenceSession;
import com.visiongc.app.strategos.planes.persistence.StrategosPlanesPersistenceSession;
import com.visiongc.app.strategos.planes.persistence.StrategosPlantillasPlanesPersistenceSession;
import com.visiongc.app.strategos.planes.persistence.hibernate.StrategosMetasHibernateSession;
import com.visiongc.app.strategos.planes.persistence.hibernate.StrategosModelosHibernateSession;
import com.visiongc.app.strategos.planes.persistence.hibernate.StrategosPerspectivasHibernateSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPrdProductosPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryActividadesPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryProyectosPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.StrategosPryVistasPersistenceSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.hibernate.StrategosPrdProductosHibernateSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.hibernate.StrategosPryActividadesHibernateSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.hibernate.StrategosPryProyectosHibernateSession;
import com.visiongc.app.strategos.planificacionseguimiento.persistence.hibernate.StrategosPryVistasHibernateSession;
import com.visiongc.app.strategos.portafolios.persistence.StrategosPortafoliosPersistenceSession;
import com.visiongc.app.strategos.portafolios.persistence.hibernate.StrategosPortafoliosHibernateSession;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosCeldasPersistenceSession;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosPaginasPersistenceSession;
import com.visiongc.app.strategos.presentaciones.persistence.StrategosVistasPersistenceSession;
import com.visiongc.app.strategos.presentaciones.persistence.hibernate.StrategosCeldasHibernateSession;
import com.visiongc.app.strategos.presentaciones.persistence.hibernate.StrategosPaginasHibernateSession;
import com.visiongc.app.strategos.presentaciones.persistence.hibernate.StrategosVistasHibernateSession;
import com.visiongc.app.strategos.problemas.persistence.StrategosAccionesPersistenceSession;
import com.visiongc.app.strategos.problemas.persistence.StrategosClasesProblemasPersistenceSession;
import com.visiongc.app.strategos.problemas.persistence.StrategosProblemasPersistenceSession;
import com.visiongc.app.strategos.problemas.persistence.StrategosSeguimientosPersistenceSession;
import com.visiongc.app.strategos.problemas.persistence.hibernate.StrategosAccionesHibernateSession;
import com.visiongc.app.strategos.problemas.persistence.hibernate.StrategosClasesProblemasHibernateSession;
import com.visiongc.app.strategos.problemas.persistence.hibernate.StrategosProblemasHibernateSession;
import com.visiongc.app.strategos.problemas.persistence.hibernate.StrategosSeguimientosHibernateSession;
import com.visiongc.app.strategos.reportes.persistence.StrategosReportesGraficoPersistenceSession;
import com.visiongc.app.strategos.reportes.persistence.StrategosReportesPersistenceSession;
import com.visiongc.app.strategos.reportes.persistence.hibernate.StrategosReportesGraficoHibernateSession;
import com.visiongc.app.strategos.reportes.persistence.hibernate.StrategosReportesHibernateSession;
import com.visiongc.app.strategos.responsables.persistence.StrategosResponsablesPersistenceSession;
import com.visiongc.app.strategos.responsables.persistence.hibernate.StrategosResponsablesHibernateSession;
import com.visiongc.app.strategos.seriestiempo.persistence.StrategosSeriesTiempoPersistenceSession;
import com.visiongc.app.strategos.seriestiempo.persistence.hibernate.StrategosSeriesTiempoHibernateSession;
import com.visiongc.app.strategos.unidadesmedida.persistence.StrategosUnidadesPersistenceSession;
import com.visiongc.app.strategos.unidadesmedida.persistence.hibernate.StrategosUnidadesHibernateSession;
import com.visiongc.app.strategos.vistasdatos.persistence.StrategosVistasDatosPersistenceSession;
import com.visiongc.app.strategos.vistasdatos.persistence.hibernate.StrategosVistasDatosHibernateSession;
import com.visiongc.commons.persistence.hibernate.VgcHibernateSessionFactory;
import org.hibernate.SessionFactory;

public class StrategosHibernateSessionFactory implements com.visiongc.app.strategos.persistence.StrategosPersistenceSessionFactory
{
  public StrategosHibernateSessionFactory() {}
  
  public StrategosUnidadesPersistenceSession openUnidadesPersistenceSession()
  {
    StrategosUnidadesPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosUnidadesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Unidades de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosUnidadesPersistenceSession openUnidadesPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosUnidadesPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosUnidadesHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Unidades de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosCategoriasPersistenceSession openCategoriasPersistenceSession()
  {
    StrategosCategoriasPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosCategoriasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Categorias de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosCategoriasPersistenceSession openCategoriasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosCategoriasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosCategoriasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n  de persistencia de hibernate de Categorias de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosResponsablesPersistenceSession openResponsablesPersistenceSession()
  {
    StrategosResponsablesPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosResponsablesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Responsables de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosResponsablesPersistenceSession openResponsablesPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosResponsablesPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosResponsablesHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Responsables de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosCuentasPersistenceSession openCuentasPersistenceSession()
  {
    StrategosCuentasPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosCuentasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Cuentas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosCuentasPersistenceSession openCuentasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosCuentasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosCuentasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Cuentas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosCausasPersistenceSession openCausasPersistenceSession()
  {
    StrategosCausasPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosCausasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Causas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosCausasPersistenceSession openCausasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosCausasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosCausasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Causas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosEstadosPersistenceSession openEstadosPersistenceSession()
  {
    StrategosEstadosPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosEstadosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Estados de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosEstadosPersistenceSession openEstadosPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosEstadosPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosEstadosHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Estados de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosSeriesTiempoPersistenceSession openSeriesTiempoPersistenceSession()
  {
    StrategosSeriesTiempoPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosSeriesTiempoHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Series de Tiempo de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosSeriesTiempoPersistenceSession openSeriesTiempoPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosSeriesTiempoPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosSeriesTiempoHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Series de Tiempo de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosOrganizacionesPersistenceSession openOrganizacionesPersistenceSession()
  {
    StrategosOrganizacionesPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosOrganizacionesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Organizaciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosOrganizacionesPersistenceSession openOrganizacionesPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosOrganizacionesPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosOrganizacionesHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Organizaciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosClasesIndicadoresPersistenceSession openClasesIndicadoresPersistenceSession()
  {
    StrategosClasesIndicadoresPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosClasesIndicadoresHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Clases de Indicadores de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosClasesIndicadoresPersistenceSession openClasesIndicadoresPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosClasesIndicadoresPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosClasesIndicadoresHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Clases de Indicadores de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosIndicadoresPersistenceSession openIndicadoresPersistenceSession()
  {
    StrategosIndicadoresPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosIndicadoresHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Indicadores de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosIndicadoresPersistenceSession openIndicadoresPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosIndicadoresPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosIndicadoresHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Indicadores de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosIndicadorAsignarInventarioPersistenceSession openIndicadorAsignarInventarioPersistenceSession()
  {
	  StrategosIndicadorAsignarInventarioPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosIndicadorAsignarInventarioHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Indicadores de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosIndicadorAsignarInventarioPersistenceSession openIndicadorAsignarInventarioPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
	  StrategosIndicadorAsignarInventarioPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosIndicadorAsignarInventarioHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Indicadores de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosMedicionesPersistenceSession openMedicionesPersistenceSession()
  {
    StrategosMedicionesPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosMedicionesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Mediciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosMedicionesPersistenceSession openMedicionesPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosMedicionesPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosMedicionesHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Mediciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosMedicionesValoracionPersistenceSession openMedicionesValoracionPersistenceSession()
  {
    StrategosMedicionesValoracionPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosMedicionesValoracionHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Mediciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosMedicionesValoracionPersistenceSession openMedicionesValoracionPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosMedicionesValoracionPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosMedicionesValoracionHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Mediciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPlantillasPlanesPersistenceSession openPlantillasPlanesPersistenceSession()
  {
    StrategosPlantillasPlanesPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new com.visiongc.app.strategos.planes.persistence.hibernate.StrategosPlantillasPlanesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Plantillas de Planes de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPlantillasPlanesPersistenceSession openPlantillasPlanesPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosPlantillasPlanesPersistenceSession persistenceSession = null;
    
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new com.visiongc.app.strategos.planes.persistence.hibernate.StrategosPlantillasPlanesHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Plantillas de Planes de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosIniciativasPersistenceSession openIniciativasPersistenceSession()
  {
    StrategosIniciativasPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosIniciativasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Iniciativas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosIniciativasPersistenceSession openIniciativasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosIniciativasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosIniciativasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Iniciativas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPryProyectosPersistenceSession openPryProyectosPersistenceSession()
  {
    StrategosPryProyectosPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosPryProyectosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de PryProyectos de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPryProyectosPersistenceSession openPryProyectosPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosPryProyectosPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosPryProyectosHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de PryProyectos de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPryActividadesPersistenceSession openPryActividadesPersistenceSession()
  {
    StrategosPryActividadesPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosPryActividadesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de PryActividades de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPryActividadesPersistenceSession openPryActividadesPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosPryActividadesPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosPryActividadesHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de PryActividades de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPryVistasPersistenceSession openPryVistasPersistenceSession()
  {
    StrategosPryVistasPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosPryVistasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de PryVistas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPryVistasPersistenceSession openPryVistasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosPryVistasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosPryVistasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de PryVistas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosProblemasPersistenceSession openProblemasPersistenceSession()
  {
    StrategosProblemasPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosProblemasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Problemas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosProblemasPersistenceSession openProblemasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosProblemasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosProblemasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Problemas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosClasesProblemasPersistenceSession openClasesProblemasPersistenceSession()
  {
    StrategosClasesProblemasPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosClasesProblemasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Clases de Problemas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosClasesProblemasPersistenceSession openClasesProblemasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosClasesProblemasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosClasesProblemasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Clases de Problemas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosSeguimientosPersistenceSession openSeguimientosPersistenceSession()
  {
    StrategosSeguimientosPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosSeguimientosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Problemas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosSeguimientosPersistenceSession openSeguimientosPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosSeguimientosPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosSeguimientosHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Problemas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    

    return persistenceSession;
  }
  
  public StrategosAccionesPersistenceSession openAccionesPersistenceSession()
  {
    StrategosAccionesPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosAccionesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Problemas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosAccionesPersistenceSession openAccionesPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosAccionesPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosAccionesHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Problemas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    

    return persistenceSession;
  }
  
  public StrategosVistasPersistenceSession openVistasPersistenceSession()
  {
    StrategosVistasPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosVistasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Vistas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosVistasPersistenceSession openVistasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosVistasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosVistasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Vistas de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosExplicacionesPersistenceSession openExplicacionesPersistenceSession()
  {
    StrategosExplicacionesPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosExplicacionesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Explicaciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosExplicacionesPersistenceSession openExplicacionesPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosExplicacionesPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosExplicacionesHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Explicaciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosForosPersistenceSession openForosPersistenceSession()
  {
    StrategosForosPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosForosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Foros de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosForosPersistenceSession openForosPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosForosPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosForosHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Foros de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPortafoliosPersistenceSession openPortafoliosPersistenceSession()
  {
    StrategosPortafoliosPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosPortafoliosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Portafolios de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPortafoliosPersistenceSession openPortafoliosPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosPortafoliosPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosPortafoliosHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Portafolios de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPaginasPersistenceSession openPaginasPersistenceSession()
  {
    StrategosPaginasPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosPaginasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Importaciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPaginasPersistenceSession openPaginasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosPaginasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosPaginasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Importaciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosCeldasPersistenceSession openCeldasPersistenceSession()
  {
    StrategosCeldasPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosCeldasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Importaciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosCeldasPersistenceSession openCeldasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosCeldasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosCeldasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Importaciones de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPrdProductosPersistenceSession openPrdProductosPersistenceSession() {
    StrategosPrdProductosPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosPrdProductosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Productos de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPrdProductosPersistenceSession openPrdProductosPersistenceSession(StrategosPersistenceSession strategosPersistenceSession) {
    StrategosPrdProductosPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosPrdProductosHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Productos de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPlanesPersistenceSession openPlanesPersistenceSession() {
    StrategosPlanesPersistenceSession persistenceSession = null;
    try
    {
      persistenceSession = new com.visiongc.app.strategos.planes.persistence.hibernate.StrategosPlanesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Planes", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPlanesPersistenceSession openPlanesPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosPlanesPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try
    {
      persistenceSession = new com.visiongc.app.strategos.planes.persistence.hibernate.StrategosPlanesHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Planes ", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosMetasPersistenceSession openMetasPersistenceSession()
  {
    StrategosMetasPersistenceSession persistenceSession = null;
    try
    {
      persistenceSession = new StrategosMetasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Metas", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosMetasPersistenceSession openMetasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosMetasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try
    {
      persistenceSession = new StrategosMetasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Planes ", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPerspectivasPersistenceSession openPerspectivasPersistenceSession()
  {
    StrategosPerspectivasPersistenceSession persistenceSession = null;
    try
    {
      persistenceSession = new StrategosPerspectivasHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Perspectivas", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosPerspectivasPersistenceSession openPerspectivasPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosPerspectivasPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try
    {
      persistenceSession = new StrategosPerspectivasHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Perspectivas", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosModelosPersistenceSession openModelosPersistenceSession()
  {
    StrategosModelosPersistenceSession persistenceSession = null;
    try
    {
      persistenceSession = new StrategosModelosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Modelos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosModelosPersistenceSession openModelosPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosModelosPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try
    {
      persistenceSession = new StrategosModelosHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Modelos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosVistasDatosPersistenceSession openVistasDatosPersistenceSession()
  {
    StrategosVistasDatosPersistenceSession persistenceSession = null;
    try {
      persistenceSession = new StrategosVistasDatosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de VistasDatos de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosVistasDatosPersistenceSession openVistasDatosPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosVistasDatosPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try {
      persistenceSession = new StrategosVistasDatosHibernateSession(strategosHibernateSession);
    } catch (Exception e) {
      try {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de VistasDatos de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    return persistenceSession;
  }
  
  public StrategosReportesPersistenceSession openReportesPersistenceSession()
  {
    StrategosReportesPersistenceSession persistenceSession = null;
    try
    {
      persistenceSession = new StrategosReportesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    }
    catch (Exception e)
    {
      try
      {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Reportes de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosReportesPersistenceSession openReportesPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosReportesPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try
    {
      persistenceSession = new StrategosReportesHibernateSession(strategosHibernateSession);
    }
    catch (Exception e)
    {
      try
      {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Reporte de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosReportesGraficoPersistenceSession openReportesGraficoPersistenceSession()
  {
    StrategosReportesGraficoPersistenceSession persistenceSession = null;
    try
    {
      persistenceSession = new StrategosReportesGraficoHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    }
    catch (Exception e)
    {
      try
      {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Reportes de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosReportesGraficoPersistenceSession openReportesGraficoPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  
  {
    StrategosReportesGraficoPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try
    {
      persistenceSession = new StrategosReportesGraficoHibernateSession(strategosHibernateSession);
    }
    catch (Exception e)
    {
      try
      {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Reporte de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosGraficosPersistenceSession openGraficosPersistenceSession()
  {
    StrategosGraficosPersistenceSession persistenceSession = null;
    try
    {
      persistenceSession = new StrategosGraficosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    }
    catch (Exception e)
    {
      try
      {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Graficos de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosGraficosPersistenceSession openGraficosPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosGraficosPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try
    {
      persistenceSession = new StrategosGraficosHibernateSession(strategosHibernateSession);
    }
    catch (Exception e)
    {
      try
      {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Grafico de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosIniciativaEstatusPersistenceSession openIniciativaEstatusPersistenceSession()
  {
    StrategosIniciativaEstatusPersistenceSession persistenceSession = null;
    try
    {
      persistenceSession = new StrategosIniciativaEstatusHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    }
    catch (Exception e)
    {
      try
      {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Estatus de la Iniciativa de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosIniciativaEstatusPersistenceSession openIniciativaEstatusPersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosIniciativaEstatusPersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try
    {
      persistenceSession = new StrategosIniciativaEstatusHibernateSession(strategosHibernateSession);
    }
    catch (Exception e)
    {
      try
      {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Estatus de la Iniciativa de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosCodigoEnlacePersistenceSession openCodigoEnlacePersistenceSession()
  {
    StrategosCodigoEnlacePersistenceSession persistenceSession = null;
    try
    {
      persistenceSession = new StrategosCodigoEnlaceHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
    }
    catch (Exception e)
    {
      try
      {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Codigo de Enlace de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }
  
  public StrategosCodigoEnlacePersistenceSession openCodigoEnlacePersistenceSession(StrategosPersistenceSession strategosPersistenceSession)
  {
    StrategosCodigoEnlacePersistenceSession persistenceSession = null;
    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
    try
    {
      persistenceSession = new StrategosCodigoEnlaceHibernateSession(strategosHibernateSession);
    }
    catch (Exception e)
    {
      try
      {
        throw new Exception("No se pudo crear una sesi贸n de persistencia de hibernate de Codigo de Enlace de Strategos", e);
      }
      catch (Exception localException1) {}
    }
    
    return persistenceSession;
  }


	public StrategosTipoProyectoPersistenceSession openTipoProyectoPersistenceSession() {
		StrategosTipoProyectoPersistenceSession persistenceSession = null;
	    try
	    {
	      persistenceSession = new StrategosTipoProyectoHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
	    }
	    catch (Exception e)
	    {
	      try
	      {
	        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Codigo de Enlace de Strategos", e);
	      }
	      catch (Exception localException1) {}
	    }
	    
	    return persistenceSession;
	}


	public StrategosTipoProyectoPersistenceSession openTipoProyectoPersistenceSession(
			StrategosPersistenceSession strategosPersistenceSession) {
		
		StrategosTipoProyectoPersistenceSession persistenceSession = null;
	    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
	    try
	    {
	      persistenceSession = new StrategosTipoProyectoHibernateSession(strategosHibernateSession);
	    }
	    catch (Exception e)
	    {
	      try
	      {
	        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Codigo de Enlace de Strategos", e);
	      }
	      catch (Exception localException1) {}
	    }
	    
	    return persistenceSession;
	}
  
	
	
	public StrategosTiposConvenioPersistenceSession openTiposConvenioPersistenceSession() {
		StrategosTiposConvenioPersistenceSession persistenceSession = null;
	    try
	    {
	      persistenceSession = new StrategosTiposConvenioHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
	    }
	    catch (Exception e)
	    {
	      try
	      {
	        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Codigo de Enlace de Strategos", e);
	      }
	      catch (Exception localException1) {}
	    }
	    
	    return persistenceSession;
	}

	

	public StrategosTiposConvenioPersistenceSession openTiposConvenioPersistenceSession(
			StrategosPersistenceSession strategosPersistenceSession) {
		
		StrategosTiposConvenioPersistenceSession persistenceSession = null;
	    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
	    try
	    {
	      persistenceSession = new StrategosTiposConvenioHibernateSession(strategosHibernateSession);
	    }
	    catch (Exception e)
	    {
	      try
	      {
	        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Codigo de Enlace de Strategos", e);
	      }
	      catch (Exception localException1) {}
	    }
	    
	    return persistenceSession;
	}
	
	public StrategosCooperantesPersistenceSession openCooperantesPersistenceSession() {
		StrategosCooperantesPersistenceSession persistenceSession = null;
	    try
	    {
	      persistenceSession = new StrategosCooperantesHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
	    }
	    catch (Exception e)
	    {
	      try
	      {
	        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Codigo de Enlace de Strategos", e);
	      }
	      catch (Exception localException1) {}
	    }
	    
	    return persistenceSession;
	}


	public StrategosCooperantesPersistenceSession openCooperantesPersistenceSession(
			StrategosPersistenceSession strategosPersistenceSession) {
		
		StrategosCooperantesPersistenceSession persistenceSession = null;
	    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
	    try
	    {
	      persistenceSession = new StrategosCooperantesHibernateSession(strategosHibernateSession);
	    }
	    catch (Exception e)
	    {
	      try
	      {
	        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Codigo de Enlace de Strategos", e);
	      }
	      catch (Exception localException1) {}
	    }
	    
	    return persistenceSession;
	}
  
	public StrategosInstrumentosPersistenceSession openInstrumentosPersistenceSession() {
		StrategosInstrumentosPersistenceSession persistenceSession = null;
	    try
	    {
	      persistenceSession = new StrategosInstrumentosHibernateSession(VgcHibernateSessionFactory.getHibernateSessionFactory().openSession());
	    }
	    catch (Exception e)
	    {
	      try
	      {
	        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Codigo de Enlace de Strategos", e);
	      }
	      catch (Exception localException1) {}
	    }
	    
	    return persistenceSession;
	}


	public StrategosInstrumentosPersistenceSession openInstrumentosPersistenceSession(
			StrategosPersistenceSession strategosPersistenceSession) {
		
		StrategosInstrumentosPersistenceSession persistenceSession = null;
	    StrategosHibernateSession strategosHibernateSession = (StrategosHibernateSession)strategosPersistenceSession;
	    try
	    {
	      persistenceSession = new StrategosInstrumentosHibernateSession(strategosHibernateSession);
	    }
	    catch (Exception e)
	    {
	      try
	      {
	        throw new Exception("No se pudo crear una sesi髇 de persistencia de hibernate de Codigo de Enlace de Strategos", e);
	      }
	      catch (Exception localException1) {}
	    }
	    
	    return persistenceSession;
	}
	
}
