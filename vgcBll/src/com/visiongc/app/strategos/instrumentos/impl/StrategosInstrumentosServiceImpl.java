package com.visiongc.app.strategos.instrumentos.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.impl.StrategosServiceImpl;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.InsumoFormulaPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.PrioridadIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.IndicadorInstrumento;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativa;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativaPK;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.model.util.ConfiguracionInstrumento;
import com.visiongc.app.strategos.instrumentos.persistence.StrategosInstrumentosPersistenceSession;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.servicio.Servicio;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Usuario;

public class StrategosInstrumentosServiceImpl extends StrategosServiceImpl implements StrategosInstrumentosService {

	private StrategosInstrumentosPersistenceSession persistenceSession = null;
		
	
	public StrategosInstrumentosServiceImpl(StrategosInstrumentosPersistenceSession persistenceSession,
			boolean persistenceOwned, StrategosServiceFactory serviceFactory, VgcMessageResources messageResources) {
		super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
		this.persistenceSession = persistenceSession;
	}

	public PaginaLista getInstrumentos(int pagina, int tamanoPagina, String orden, String tipoOrden, boolean getTotal,
			Map filtros) {

		return this.persistenceSession.getInstrumentos(pagina, tamanoPagina, orden, tipoOrden, getTotal, filtros);

	}

	public int deleteInstrumentos(Instrumentos instrumento, Usuario usuario) {

		boolean transActiva = false;
		int resultado = 10000;
		try {
			if (!persistenceSession.isTransactionActive()) {
				persistenceSession.beginTransaction();
				transActiva = true;
			}

			if (instrumento.getInstrumentoId() != null) {
				resultado = persistenceSession.delete(instrumento, usuario);
			}

			if (resultado == 10000) {
				if (transActiva) {
					persistenceSession.commitTransaction();
					transActiva = false;
				}

			} else if (transActiva) {
				persistenceSession.rollbackTransaction();
				transActiva = false;
			}

		} catch (Throwable t) {
			if (transActiva) {
				persistenceSession.rollbackTransaction();
				throw new ChainedRuntimeException(t.getMessage(), t);
			}
		}		

		return resultado;

	}

	public int saveInstrumentos(Instrumentos instrumento, Usuario usuario, Boolean actualizarIndicador) {
		
		System.out.print("\n\nEntra a saveInstrumentos (serviceImpl) \n\n");

		boolean transActiva = false;
		int resultado = 10000;
		String[] fieldNames = new String[1];
		Object[] fieldValues = new Object[1];
		try {
			if (!persistenceSession.isTransactionActive()) {
				persistenceSession.beginTransaction();
				transActiva = true;
			}

			fieldNames[0] = "nombreCorto";
			fieldValues[0] = instrumento.getNombreCorto();

			if ((instrumento.getInstrumentoId() == null) || (instrumento.getInstrumentoId().longValue() == 0L)) {
				
				System.out.print("\n\nNO Existe Instrumento \n\n");
				
				if (persistenceSession.existsObject(instrumento, fieldNames, fieldValues)) {
					resultado = 10003;
				} else {
					instrumento.setInstrumentoId(new Long(persistenceSession.getUniqueId()));
					
					System.out.print("\n\nPrevio a Insert Instrumento, usuario \n\n");
					persistenceSession.insert(instrumento, usuario);
					
					System.out.print("\n\nResultado: \n\n" +  resultado);
					
					System.out.print("\n\nPrevio a saveClaseIndicadores \n\n");
					resultado = saveClaseIndicadores(instrumento, usuario);
					if (resultado == 10000 ) {
						ConfiguracionInstrumento configuracionInstrumento = getConfiguracionInstrumento();
						resultado = saveIndicadorAutomatico(instrumento,
								TipoFuncionIndicador.getTipoFuncionSeguimiento(), configuracionInstrumento, usuario);
						if ((resultado == 10000) && (configuracionInstrumento
								.getInstrumentoIndicadorPresupuestoMostrar().booleanValue()))
							resultado = saveIndicadorAutomatico(instrumento,
									TipoFuncionIndicador.getTipoFuncionPresupuesto(), configuracionInstrumento,
									usuario);
						if ((resultado == 10000)
								&& (configuracionInstrumento.getInstrumentoIndicadorEficaciaMostrar().booleanValue()))
							resultado = saveIndicadorAutomatico(instrumento,
									TipoFuncionIndicador.getTipoFuncionEficacia(), configuracionInstrumento, usuario);
						if ((resultado == 10000) && (configuracionInstrumento.getInstrumentoIndicadorEficienciaMostrar()
								.booleanValue())) {
							resultado = saveIndicadorAutomatico(instrumento,
									TipoFuncionIndicador.getTipoFuncionEficiencia(), configuracionInstrumento, usuario);
						}
					}
					if (resultado == 10000) {
						resultado = persistenceSession.insert(instrumento, usuario);
						if (resultado == 10000) {
							resultado = asociarIndicador(instrumento, usuario);
						}
					}
				}

			} else {
				
				System.out.print("\n\nExiste Instrumento \n\n");
				
				String[] idFieldNames = new String[1];
				Object[] idFieldValues = new Object[1];

				idFieldNames[0] = "instrumentoId";
				idFieldValues[0] = instrumento.getInstrumentoId();
				if (persistenceSession.existsObject(instrumento, fieldNames, fieldValues, idFieldNames,
						idFieldValues)) {
					resultado = 10003;
				} else {

					if (actualizarIndicador.booleanValue()) {
						if (instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()) != null) {
							Instrumentos instrumentoOriginal = getValoresOriginales(instrumento.getInstrumentoId());
							if (instrumentoOriginal.getFrecuencia().byteValue() != instrumento.getFrecuencia()
									.byteValue()) {
								ConfiguracionInstrumento configuracionInstrumento = getConfiguracionInstrumento();
								resultado = updateIndicadorAutomatico(instrumento,
										TipoFuncionIndicador.getTipoFuncionSeguimiento(), configuracionInstrumento,
										usuario);
								if ((resultado == 10000) && (configuracionInstrumento
										.getInstrumentoIndicadorPresupuestoMostrar().booleanValue()))
									resultado = updateIndicadorAutomatico(instrumento,
											TipoFuncionIndicador.getTipoFuncionPresupuesto(), configuracionInstrumento,
											usuario);
								if ((resultado == 10000) && (configuracionInstrumento
										.getInstrumentoIndicadorEficaciaMostrar().booleanValue()))
									resultado = updateIndicadorAutomatico(instrumento,
											TipoFuncionIndicador.getTipoFuncionEficacia(), configuracionInstrumento,
											usuario);
								if ((resultado == 10000) && (configuracionInstrumento
										.getInstrumentoIndicadorEficienciaMostrar().booleanValue())) {
									resultado = updateIndicadorAutomatico(instrumento,
											TipoFuncionIndicador.getTipoFuncionEficiencia(), configuracionInstrumento,
											usuario);
								}
							} else {
								StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory
										.getInstance().openStrategosIndicadoresService();
								Indicador indicador = null;
								String nombre = "";
								ConfiguracionInstrumento configuracionInstrumento = null;

								if (resultado == 10000) {
									Long indicadorId = instrumento
											.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
									if (indicadorId != null) {
										indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
												indicadorId);

										configuracionInstrumento = getConfiguracionInstrumento();
										nombre = "";
										if (configuracionInstrumento.getInstrumentoIndicadorAvanceAnteponer()
												.booleanValue()) {
											nombre = configuracionInstrumento.getInstrumentoIndicadorAvanceNombre()
													+ " - ";
											nombre = nombre + instrumento.getNombreCorto();
										}
										if (nombre.length() > 100)
											nombre = nombre.substring(0, 100);
										indicador.setNombre(nombre);
										if (nombre.length() > 50)
											nombre = nombre.substring(0, 50);
										indicador.setNombreCorto(nombre);

										resultado = strategosIndicadoresService.saveIndicador(indicador, usuario);
										if (resultado == 10003) {
											Map<String, Object> filtros = new HashMap();

											filtros.put("claseId", indicador.getClaseId());
											filtros.put("nombre", indicador.getNombre());
											List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0,
													"nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false))
													.getLista();
											if (inds.size() > 0) {
												indicador = (Indicador) inds.get(0);
												resultado = 10000;
											}
										}
									}
								}

								if (resultado == 10000) {
									Long indicadorId = instrumento
											.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto());
									if (indicadorId != null) {
										indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
												indicadorId);
										nombre = "";
										nombre = configuracionInstrumento.getInstrumentoIndicadorPresupuestoNombre()
												+ " - ";
										nombre = nombre + instrumento.getNombreCorto();
										if (nombre.length() > 100)
											nombre = nombre.substring(0, 100);
										indicador.setNombre(nombre);
										if (nombre.length() > 50)
											nombre = nombre.substring(0, 50);
										indicador.setNombreCorto(nombre);

										resultado = strategosIndicadoresService.saveIndicador(indicador, usuario);
										if (resultado == 10003) {
											Map<String, Object> filtros = new HashMap();

											filtros.put("claseId", indicador.getClaseId());
											filtros.put("nombre", indicador.getNombre());
											List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0,
													"nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false))
													.getLista();
											if (inds.size() > 0) {
												indicador = (Indicador) inds.get(0);
												resultado = 10000;
											}
										}
									}
								}

								if (resultado == 10000) {
									Long indicadorId = instrumento
											.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficacia());
									if (indicadorId != null) {
										indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
												indicadorId);
										nombre = "";
										nombre = configuracionInstrumento.getInstrumentoIndicadorEficaciaNombre() + " - ";
										nombre = nombre + instrumento.getNombreCorto();
										if (nombre.length() > 100)
											nombre = nombre.substring(0, 100);
										indicador.setNombre(nombre);
										if (nombre.length() > 50)
											nombre = nombre.substring(0, 50);
										indicador.setNombreCorto(nombre);

										resultado = strategosIndicadoresService.saveIndicador(indicador, usuario);
										if (resultado == 10003) {
											Map<String, Object> filtros = new HashMap();

											filtros.put("claseId", indicador.getClaseId());
											filtros.put("nombre", indicador.getNombre());
											List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0,
													"nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false))
													.getLista();
											if (inds.size() > 0) {
												indicador = (Indicador) inds.get(0);
												resultado = 10000;
											}
										}
									}
								}

								if (resultado == 10000) {
									Long indicadorId = instrumento
											.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficiencia());
									if (indicadorId != null) {
										indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
												indicadorId);
										nombre = "";
										nombre = configuracionInstrumento.getInstrumentoIndicadorEficienciaNombre()
												+ " - ";
										nombre = nombre + instrumento.getNombreCorto();
										if (nombre.length() > 100)
											nombre = nombre.substring(0, 100);
										indicador.setNombre(nombre);
										if (nombre.length() > 50)
											nombre = nombre.substring(0, 50);
										indicador.setNombreCorto(nombre);

										resultado = strategosIndicadoresService.saveIndicador(indicador, usuario);
										if (resultado == 10003) {
											Map<String, Object> filtros = new HashMap();

											filtros.put("claseId", indicador.getClaseId());
											filtros.put("nombre", indicador.getNombre());
											List<Indicador> inds = strategosIndicadoresService.getIndicadores(0, 0,
													"nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false))
													.getLista();
											if (inds.size() > 0) {
												indicador = (Indicador) inds.get(0);
												resultado = 10000;
											}
										}
									}
								}
								strategosIndicadoresService.close();

							}
						}
					}
					if (resultado == 10000) {
						resultado = persistenceSession.update(instrumento, usuario);
					}
				}
			}
			/*
			 * String valorEnteEjecutorVacio = "-"; if ((instrumento.getEnteEjecutor() ==
			 * "") || (iniciativa.getEnteEjecutor() == null)) {
			 * iniciativa.setEnteEjecutor(valorEnteEjecutorVacio); }
			 */
			if (transActiva) {
				if (resultado == 10000) {
					persistenceSession.commitTransaction();
				} else {
					persistenceSession.rollbackTransaction();
				}
				transActiva = false;
			}

			if ((!transActiva) && (resultado == 10000)) {
				List<Object> indicadores = new ArrayList();
				indicadores.add(instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				resultado = new Servicio().calcular(Servicio.EjecutarTipo.getEjecucionAlerta().byteValue(), indicadores,
						usuario);
			}

		} catch (Throwable t) {
			if (transActiva) {
				persistenceSession.rollbackTransaction();
			}
			throw new ChainedRuntimeException(t.getMessage(), t);
		}

		return resultado;

	}

	public int asociarInstrumento(Long instrumentoId, Long iniciativaId, Usuario usuario) {
		boolean transActiva = false;
		int resultado = 10000;
		String[] fieldNames = new String[2];
		Object[] fieldValues = new Object[2];
		try {
			if (!persistenceSession.isTransactionActive()) {
				persistenceSession.beginTransaction();
				transActiva = true;
			}

			InstrumentoIniciativa instrumentoIniciativa = new InstrumentoIniciativa();

			instrumentoIniciativa.setPk(new InstrumentoIniciativaPK());
			instrumentoIniciativa.getPk().setIniciativaId(iniciativaId);
			instrumentoIniciativa.getPk().setInstrumentoId(instrumentoId);

			fieldNames[0] = "pk.iniciativaId";
			fieldValues[0] = instrumentoIniciativa.getPk().getIniciativaId();
			fieldNames[1] = "pk.instrumentoId";
			fieldValues[1] = instrumentoIniciativa.getPk().getInstrumentoId();

			if (!persistenceSession.existsObject(instrumentoIniciativa, fieldNames, fieldValues)) {
				resultado = persistenceSession.insert(instrumentoIniciativa, usuario);
			}
			if (transActiva) {
				persistenceSession.commitTransaction();
				transActiva = false;
			}
		} catch (Throwable t) {
			if (transActiva)
				persistenceSession.rollbackTransaction();
			throw new ChainedRuntimeException(t.getMessage(), t);
		}

		return resultado;
	}

	public int desasociarInstrumento(Long instrumentoId, Long iniciativaId, Usuario usuario) {
		boolean transActiva = false;
		int resultado = 10000;
		try {
			if (!persistenceSession.isTransactionActive()) {
				persistenceSession.beginTransaction();
				transActiva = true;
			}

			InstrumentoIniciativa instrumentoIniciativa = new InstrumentoIniciativa();

			instrumentoIniciativa.setPk(new InstrumentoIniciativaPK());
			instrumentoIniciativa.getPk().setIniciativaId(iniciativaId);
			instrumentoIniciativa.getPk().setInstrumentoId(instrumentoId);

			resultado = persistenceSession.delete(instrumentoIniciativa, usuario);

			if (resultado == 10000) {
				if (transActiva) {
					persistenceSession.commitTransaction();
					transActiva = false;
				}
			} else if (transActiva) {
				persistenceSession.rollbackTransaction();
				transActiva = false;
			}
		} catch (Throwable t) {
			if (transActiva) {
				persistenceSession.rollbackTransaction();
				throw new ChainedRuntimeException(t.getMessage(), t);
			}
		}

		return resultado;
	}

	public List<InstrumentoIniciativa> getIniciativasInstrumento(Long instrumentoId) {
		return persistenceSession.getIniciativasInstrumento(instrumentoId);
	}

	public int saveIniciativaInstrumento(List<InstrumentoIniciativa> instrumentoIniciativas, Usuario usuario) {

		boolean transActiva = false;
		int resultado = 10000;
		String[] fieldNames = new String[2];
		Object[] fieldValues = new Object[2];

		try {
			if (!persistenceSession.isTransactionActive()) {
				persistenceSession.beginTransaction();
				transActiva = true;
			}

			for (Iterator<InstrumentoIniciativa> iter = instrumentoIniciativas.iterator(); iter.hasNext();) {
				InstrumentoIniciativa instrumentoIniciativa = (InstrumentoIniciativa) iter.next();

				fieldNames[0] = "pk.iniciativaId";
				fieldValues[0] = instrumentoIniciativa.getPk().getIniciativaId();
				fieldNames[1] = "pk.instrumentoId";
				fieldValues[1] = instrumentoIniciativa.getPk().getInstrumentoId();

				if (persistenceSession.existsObject(instrumentoIniciativa, fieldNames, fieldValues)) {
					resultado = persistenceSession.updatePesos(instrumentoIniciativa, usuario);
				}
				if (resultado != 10000) {
					break;
				}
			}
			if (transActiva) {
				if (resultado == 10000) {
					persistenceSession.commitTransaction();
				} else {
					persistenceSession.rollbackTransaction();
					transActiva = false;
				}
			}

		} catch (Throwable t) {
			if (transActiva)
				persistenceSession.rollbackTransaction();
			throw new ChainedRuntimeException(t.getMessage(), t);
		}

		return resultado;
	}

	public int updatePesos(InstrumentoIniciativa instrumentoIniciativa, Usuario ususario) {
		return persistenceSession.updatePesos(instrumentoIniciativa, ususario);
	}

	private int saveClaseIndicadores(Instrumentos instrumento, Usuario usuario) {
		
		System.out.print("\n\nEntra a saveCLaseIndicadores");

		StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosClasesIndicadoresService(this);

		ClaseIndicadores clase = new ClaseIndicadores();
		ClaseIndicadores claseRoot = strategosClasesIndicadoresService.getClaseRaizIniciativa(
				instrumento.getInstrumentoId(), TipoClaseIndicadores.getTipoClasePlanificacionSeguimiento(),
				messageResources.getResource("iniciativa.clase.nombre"), usuario);

		clase.setPadreId(claseRoot.getClaseId());
		clase.setNombre(instrumento.getNombreCorto());
		clase.setOrganizacionId(1L);
		clase.setTipo(TipoClaseIndicadores.getTipoClasePlanificacionSeguimiento());
		clase.setVisible(new Boolean(true));

		System.out.print("\n\nPrevio a Entra a saveCLaseIndicadores de indicadores service");
		int resultado = strategosClasesIndicadoresService.saveClaseIndicadores(clase, usuario);
		System.out.print("\n\nSigue de claseIndicadores\n\n");
		if (resultado == 10003) {
			Map<String, Object> filtros = new HashMap();

			filtros.put("organizacionId", clase.getOrganizacionId().toString());
			filtros.put("nombre", clase.getNombre());
			filtros.put("padreId", clase.getPadreId());
			List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClases(filtros);
			if (clases.size() > 0) {
				clase = (ClaseIndicadores) clases.get(0);
				resultado = 10000;
			}
		}

		if (resultado == 10000) {
			instrumento.setClaseId(clase.getClaseId());
			System.out.print("\n\nClaseId; " + instrumento.getClaseId() + "\n\n");
		}
		strategosClasesIndicadoresService.close();

		
		System.out.print("\n\nResultado; " + resultado + "\n\n");
		return resultado;
	}

	private int saveIndicadorAutomatico(Instrumentos instrumento, Byte tipo,
			ConfiguracionInstrumento configuracionInstrumento, Usuario usuario) {

		int resultado = 10000;
		
		System.out.print("\n\nEntra a save indicador automatico\n\n");

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService(this);
		Indicador indicador = new Indicador();
		indicador.setClaseId(instrumento.getClaseId());
		String nombre = "";
		if ((tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue())
				&& (configuracionInstrumento.getInstrumentoIndicadorAvanceAnteponer().booleanValue())) {
			nombre = configuracionInstrumento.getInstrumentoIndicadorAvanceNombre() + " - ";
		} else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
			nombre = configuracionInstrumento.getInstrumentoIndicadorPresupuestoNombre() + " - ";
		} else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue()) {
			nombre = configuracionInstrumento.getInstrumentoIndicadorEficaciaNombre() + " - ";
		} else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue())
			nombre = configuracionInstrumento.getInstrumentoIndicadorEficienciaNombre() + " - ";
		nombre = nombre + instrumento.getNombreCorto();
		if (nombre.length() > 100)
			nombre = nombre.substring(0, 100);
		indicador.setNombre(nombre);
		if (nombre.length() > 50)
			nombre = nombre.substring(0, 50);
		indicador.setNombreCorto(nombre);
		indicador.setFrecuencia(instrumento.getFrecuencia());
		if (tipo.byteValue() != TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
			StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance()
					.openStrategosUnidadesService(this);
			UnidadMedida porcentaje = strategosUnidadesService.getUnidadMedidaPorcentaje();
			indicador.setUnidadId(porcentaje.getUnidadId());
			strategosUnidadesService.close();
		}
		indicador.setPrioridad(PrioridadIndicador.getPrioridadIndicadorBaja());
		indicador.setMostrarEnArbol(new Boolean(true));
		if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
			indicador.setCaracteristica(Caracteristica.getCaracteristicaCondicionValorMaximo());
		} else
			indicador.setCaracteristica(Caracteristica.getCaracteristicaRetoAumento());
		indicador.setTipoFuncion(tipo);
		indicador.setGuia(new Boolean(false));
		indicador.setValorInicialCero(new Boolean(true));
		indicador.setNumeroDecimales(new Byte("2"));
		indicador.setNaturaleza(Naturaleza.getNaturalezaSimple());
		if ((tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue())
				|| (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue())) {
			indicador.setCorte(TipoCorte.getTipoCorteTransversal());
			indicador.setTipoCargaMedicion(TipoMedicion.getTipoMedicionAlPeriodo());
			if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue()) {
				indicador.setNaturaleza(Naturaleza.getNaturalezaFormula());
				resultado = crearIndicadorFormulaEficacia(instrumento, indicador);
			} else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue()) {
				indicador.setNaturaleza(Naturaleza.getNaturalezaFormula());
				resultado = crearIndicadorFormulaEficiencia(instrumento, indicador);
			}
		} else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
			indicador.setCorte(TipoCorte.getTipoCorteTransversal());
			indicador.setTipoCargaMedicion(TipoMedicion.getTipoMedicionAlPeriodo());
		} else {
			indicador.setCorte(TipoCorte.getTipoCorteLongitudinal());
			indicador.setTipoCargaMedicion(TipoMedicion.getTipoMedicionEnPeriodo());
		}

		if (resultado == 10000)
			resultado = strategosIndicadoresService.saveIndicador(indicador, usuario);
		if (resultado == 10003) {
			Map<String, Object> filtros = new HashMap();

			filtros.put("claseId", indicador.getClaseId());
			filtros.put("nombre", indicador.getNombre());
			List<Indicador> inds = strategosIndicadoresService
					.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, Boolean.valueOf(false))
					.getLista();
			if (inds.size() > 0) {
				indicador = (Indicador) inds.get(0);
				resultado = 10000;
			}
		}

		if (resultado == 10000) {
			instrumento.setIndicadorId(indicador.getIndicadorId(), tipo);
		}
		strategosIndicadoresService.close();

		return resultado;
	}

	public int asociarIndicador(Instrumentos instrumento, Usuario usuario) {

		boolean transActiva = false;
		int resultado = 10000;
		String[] fieldNames = new String[2];
		Object[] fieldValues = new Object[2];
		
		System.out.print("\n\nEntra a Asociar indicador\n\n");

		try {

			if (!persistenceSession.isTransactionActive()) {
				persistenceSession.beginTransaction();
				transActiva = true;
			}

			for (Iterator<IndicadorInstrumento> iter = instrumento.getInstrumentoIndicadores().iterator(); iter
					.hasNext();) {
				IndicadorInstrumento instrumentoIndicador = (IndicadorInstrumento) iter.next();

				fieldNames[0] = "pk.indicadorId";
				fieldValues[0] = instrumentoIndicador.getPk().getIndicadorId();
				fieldNames[1] = "pk.instrumentoId";
				fieldValues[1] = instrumentoIndicador.getPk().getInstrumentoId();
				if (!persistenceSession.existsObject(instrumentoIndicador, fieldNames, fieldValues))
					resultado = persistenceSession.insert(instrumentoIndicador, usuario);
				if (resultado != 10000) {
					break;
				}
			}
			if (transActiva) {
				persistenceSession.commitTransaction();
				transActiva = false;
			}

		} catch (Throwable t) {
			if (transActiva)
				persistenceSession.rollbackTransaction();
			throw new ChainedRuntimeException(t.getMessage(), t);
		}

		return resultado;
	}

	public int desasociarIndicadores(IndicadorInstrumento instrumentoIndicador, Usuario usuario) {
		boolean transActiva = false;
		int resultado = 10000;
		try {
			if (!persistenceSession.isTransactionActive()) {
				persistenceSession.beginTransaction();
				transActiva = true;
			}

			if (instrumentoIndicador != null) {
				resultado = persistenceSession.delete(instrumentoIndicador, usuario);
			}
			if (resultado == 10000) {
				if (transActiva) {
					persistenceSession.commitTransaction();
					transActiva = false;
				}
			} else if (transActiva) {
				persistenceSession.rollbackTransaction();
				transActiva = false;
			}
		} catch (Throwable t) {
			if (transActiva) {
				persistenceSession.rollbackTransaction();
				throw new ChainedRuntimeException(t.getMessage(), t);
			}
		}

		return resultado;
	}

	public int updateIndicadorAutomatico(Instrumentos instrumento, Byte tipo,
			ConfiguracionInstrumento configuracionInstrumento, Usuario usuario) {

		int resultado = 10000;

		return resultado;
	}

	public Instrumentos getValoresOriginales(Long instrumentoId) {
		return persistenceSession.getValoresOriginales(instrumentoId);
	}

	private int crearIndicadorFormulaEficacia(Instrumentos instrumento, Indicador indicador) {
		int resultado = 10000;

		SerieIndicador serieReal = null;
		Set<SerieIndicador> seriesIndicador = indicador.getSeriesIndicador();
		for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext();) {
			SerieIndicador serie = (SerieIndicador) i.next();
			if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue()) {
				serieReal = serie;
				break;
			}
		}

		Formula formulaIndicador = new Formula();
		formulaIndicador.setInsumos(new HashSet());

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
				.openStrategosIndicadoresService(this);
		Indicador indicadorInsumo = (Indicador) strategosIndicadoresService.load(Indicador.class,
				instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
		strategosIndicadoresService.close();

		String formula = "";
		if (instrumento.getTipoMedicion().byteValue() == TipoMedicion.getTipoMedicionAlPeriodo().byteValue()) {
			formula = "([" + instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString()
					+ ".0]" + "/" + "["
					+ instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString()
					+ ".1])*100";
		} else {
			formula = "([" + instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString()
					+ ".0]:S" + "/" + "["
					+ instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString()
					+ ".1]:S)*100";
		}

		formulaIndicador.setExpresion(formula);

		InsumoFormula insumoFormula = new InsumoFormula();
		insumoFormula.setPk(new InsumoFormulaPK());
		insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
		insumoFormula.getPk().setSerieId(new Long("0"));
		insumoFormula.getPk()
				.setIndicadorId(instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
		insumoFormula.getPk().setInsumoSerieId(new Long("0"));
		formulaIndicador.getInsumos().add(insumoFormula);

		insumoFormula = new InsumoFormula();
		insumoFormula.setPk(new InsumoFormulaPK());
		insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
		insumoFormula.getPk().setSerieId(new Long("0"));
		insumoFormula.getPk()
				.setIndicadorId(instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
		insumoFormula.getPk().setInsumoSerieId(new Long("1"));
		formulaIndicador.getInsumos().add(insumoFormula);

		serieReal.getFormulas().add(formulaIndicador);

		return resultado;
	}

	private int crearIndicadorFormulaEficiencia(Instrumentos instrumento, Indicador indicador) {
		int resultado = 10000;

		SerieIndicador serieReal = null;
		Set<SerieIndicador> seriesIndicador = indicador.getSeriesIndicador();
		for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext();) {
			SerieIndicador serie = (SerieIndicador) i.next();
			if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue()) {
				serieReal = serie;
				break;
			}
		}

		Formula formulaIndicador = new Formula();
		formulaIndicador.setInsumos(new HashSet());

		String formula = "";
		if (instrumento.getTipoMedicion().byteValue() == TipoMedicion.getTipoMedicionAlPeriodo().byteValue()) {
			formula =

					"([" + instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString() + "."
							+ SerieTiempo.getSerieReal().getSerieId().byteValue() + "]" + "*" + "["
							+ instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()).toString()
							+ "." + SerieTiempo.getSerieMaximo().getSerieId().byteValue() + "])" + "/" + "["
							+ instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()).toString()
							+ "." + SerieTiempo.getSerieReal().getSerieId().byteValue() + "]";
		} else {
			formula =

					"([" + instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).toString() + "."
							+ SerieTiempo.getSerieReal().getSerieId().byteValue() + "]:S" + "*" + "["
							+ instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()).toString()
							+ "." + SerieTiempo.getSerieMaximo().getSerieId().byteValue() + "])" + "/" + "["
							+ instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()).toString()
							+ "." + SerieTiempo.getSerieReal().getSerieId().byteValue() + "]";
		}

		formulaIndicador.setExpresion(formula);

		InsumoFormula insumoFormula = new InsumoFormula();
		insumoFormula.setPk(new InsumoFormulaPK());
		insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
		insumoFormula.getPk().setSerieId(SerieTiempo.getSerieReal().getSerieId());
		insumoFormula.getPk()
				.setIndicadorId(instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
		insumoFormula.getPk().setInsumoSerieId(SerieTiempo.getSerieReal().getSerieId());
		formulaIndicador.getInsumos().add(insumoFormula);

		insumoFormula = new InsumoFormula();
		insumoFormula.setPk(new InsumoFormulaPK());
		insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
		insumoFormula.getPk().setSerieId(SerieTiempo.getSerieReal().getSerieId());
		insumoFormula.getPk()
				.setIndicadorId(instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()));
		insumoFormula.getPk().setInsumoSerieId(SerieTiempo.getSerieMaximo().getSerieId());
		formulaIndicador.getInsumos().add(insumoFormula);

		insumoFormula = new InsumoFormula();
		insumoFormula.setPk(new InsumoFormulaPK());
		insumoFormula.getPk().setPadreId(indicador.getIndicadorId());
		insumoFormula.getPk().setSerieId(SerieTiempo.getSerieReal().getSerieId());
		insumoFormula.getPk()
				.setIndicadorId(instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionPresupuesto()));
		insumoFormula.getPk().setInsumoSerieId(SerieTiempo.getSerieReal().getSerieId());
		formulaIndicador.getInsumos().add(insumoFormula);

		serieReal.getFormulas().add(formulaIndicador);

		return resultado;
	}

	public ConfiguracionInstrumento getConfiguracionInstrumento() {
		ConfiguracionInstrumento configuracionInstrumento = new ConfiguracionInstrumento();
		try {
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Instrumentos");
			frameworkService.close();

			if (configuracion != null) {

				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
				doc.getDocumentElement().normalize();
				NodeList nList = doc.getElementsByTagName("properties");
				Element eElement = (Element) nList.item(0);

				configuracionInstrumento.setInstrumentoNombre(VgcAbstractService.getTagValue("nombre", eElement));

				nList = doc.getElementsByTagName("indicador");
				if (nList.getLength() > 0) {
					for (int i = 0; i < nList.getLength(); i++) {
						Node node = nList.item(i);
						Element elemento = (Element) node;

						Byte tipo = VgcAbstractService.getTagValue("tipo", elemento) != ""
								? Byte.valueOf(Byte.parseByte(getTagValue("tipo", elemento)))
								: null;
						if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionSeguimiento().byteValue()) {
							configuracionInstrumento.setInstrumentoIndicadorAvanceNombre(
									VgcAbstractService.getTagValue("nombre", elemento));
							configuracionInstrumento.setInstrumentoIndicadorAvanceMostrar(
									Boolean.valueOf(VgcAbstractService.getTagValue("crear", elemento).equals("1")));
							configuracionInstrumento.setInstrumentoIndicadorAvanceAnteponer(
									Boolean.valueOf(VgcAbstractService.getTagValue("anteponer", elemento).equals("1")));
						} else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionPresupuesto().byteValue()) {
							configuracionInstrumento.setInstrumentoIndicadorPresupuestoNombre(
									VgcAbstractService.getTagValue("nombre", elemento));
							configuracionInstrumento.setInstrumentoIndicadorPresupuestoMostrar(
									Boolean.valueOf(VgcAbstractService.getTagValue("crear", elemento).equals("1")));
						} else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficacia().byteValue()) {
							configuracionInstrumento.setInstrumentoIndicadorEficaciaNombre(
									VgcAbstractService.getTagValue("nombre", elemento));
							configuracionInstrumento.setInstrumentoIndicadorEficaciaMostrar(
									Boolean.valueOf(VgcAbstractService.getTagValue("crear", elemento).equals("1")));
						} else if (tipo.byteValue() == TipoFuncionIndicador.getTipoFuncionEficiencia().byteValue()) {
							configuracionInstrumento.setInstrumentoIndicadorEficienciaNombre(
									VgcAbstractService.getTagValue("nombre", elemento));
							configuracionInstrumento.setInstrumentoIndicadorEficienciaMostrar(
									Boolean.valueOf(VgcAbstractService.getTagValue("crear", elemento).equals("1")));
						}
					}
					
					
				}
			} else {
				VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

				configuracionInstrumento
						.setInstrumentoNombre(messageResources.getResource("jsp.modulo.iniciativa.titulo.singular"));
				configuracionInstrumento.setInstrumentoIndicadorAvanceNombre(
						messageResources.getResource("jsp.configuracion.sistema.iniciativas.indicador.avance.nombre"));
				configuracionInstrumento.setInstrumentoIndicadorAvanceMostrar(Boolean.valueOf(true));
				configuracionInstrumento.setInstrumentoIndicadorPresupuestoNombre(messageResources
						.getResource("jsp.configuracion.sistema.iniciativas.indicador.presupuesto.nombre"));
				configuracionInstrumento.setInstrumentoIndicadorPresupuestoMostrar(Boolean.valueOf(true));
				configuracionInstrumento.setInstrumentoIndicadorEficaciaNombre(messageResources
						.getResource("jsp.configuracion.sistema.iniciativas.indicador.eficacia.nombre"));
				configuracionInstrumento.setInstrumentoIndicadorEficaciaMostrar(Boolean.valueOf(true));
				configuracionInstrumento.setInstrumentoIndicadorEficienciaNombre(messageResources
						.getResource("jsp.configuracion.sistema.iniciativas.indicador.eficiencia.nombre"));
				configuracionInstrumento.setInstrumentoIndicadorEficienciaMostrar(Boolean.valueOf(true));
				configuracionInstrumento.setInstrumentoIndicadorAvanceAnteponer(Boolean.valueOf(true));
			}
		} catch (Exception localException) {
		}

		return configuracionInstrumento;
	}
}
