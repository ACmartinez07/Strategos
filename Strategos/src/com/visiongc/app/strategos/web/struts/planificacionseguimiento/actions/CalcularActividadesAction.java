/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public final class CalcularActividadesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
  	{
		super.execute(mapping, form, request, response);
	
		String forward = mapping.getParameter();
	
		return mapping.findForward(forward);
	}	  
	  
	public int CalcularPadre(PryActividad actividad, Long iniciativaId, HttpServletRequest request)
	{
		  int respuesta = 10000;

		  StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		  PryActividad actividadPadre = null;
		  if (actividad.getPadreId() != null)
			  actividadPadre = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(actividad.getPadreId()));
		  
		  String[] serieId = "0,1".split(",");
		  int anoDesde = 0000;
		  int anoHasta = 9999;
		  int periodoDesde = 000;
		  int periodoHasta = 999;
		  List<SerieIndicador> seriesIndicadores = new ArrayList<SerieIndicador>();
		  Map<String, Object> filtros = new HashMap<String, Object>();
		  
		  filtros.put("proyectoId", actividad.getProyectoId().toString());
		  if (actividad.getPadreId() != null)
			  filtros.put("padreId", actividad.getPadreId().toString());
		  else
			  filtros.put("padreId", null);
		
		  String atributoOrden = "fila";
		  String tipoOrden = "ASC";
		  int pagina = 1;
		  Boolean hayMediciones = false;
		  PaginaLista paginaActividades = strategosPryActividadesService.getActividades(pagina, 30, atributoOrden, tipoOrden, true, filtros);

		  long indicadorId;
		  boolean calculandoIniciativa = false;
		  boolean hayPeso = false;
		  Iniciativa iniciativa = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, new Long(iniciativaId));
		  if (actividad.getPadreId() != null)
			  indicadorId = actividadPadre.getIndicadorId();
		  else
		  {
			  indicadorId = iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
			  calculandoIniciativa = true;
		  }

		  StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();
		  UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
		  strategosUnidadesService.close();
		  
		  Indicador indicador = (Indicador)strategosPryActividadesService.load(Indicador.class, new Long(indicadorId));
		  if (paginaActividades.getLista().size() > 0)
		  {
			  StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
        	  for (Iterator<PryActividad> iter = paginaActividades.getLista().iterator(); iter.hasNext(); ) 
			  {
				  PryActividad act = iter.next();
				  if (act.getPryInformacionActividad().getPeso() != null)
					  hayPeso = true;
			  }

        	  //Eliminamos todas las mediciones ya que van a ser recalculadas
			  respuesta = strategosMedicionesService.deleteMediciones(indicador.getIndicadorId());
			  
			  // Buscamos las mediciones de todas las actividades 
			  // involucradas y la multiplicamos por su peso si existe
			  List<Medicion> medicionesActividades = new ArrayList<Medicion>();
			  if (respuesta == VgcReturnCode.DB_OK)
			  {
				  int maximoAnoReal = 0;
				  int maximoPeriodoReal = 0;
				  int maximoAnoProgramado = 0;
				  int maximoPeriodoProgramado = 0;
				  for (int iSerie = 0; iSerie < serieId.length; iSerie++)
				  {
		        	  for (Iterator<PryActividad> iter = paginaActividades.getLista().iterator(); iter.hasNext(); ) 
					  {
						  PryActividad act = iter.next();
						  Long serId = new Long(serieId[iSerie]);
						  if (unidad != null && act.getUnidadId().longValue() != unidad.getUnidadId().longValue())
							  serId = serId.longValue() == 0 ? SerieTiempo.getSeriePorcentajeReal().getSerieId() : (serId.longValue() == 1 ? SerieTiempo.getSeriePorcentajeProgramado().getSerieId() : serId);
						  
			        	  List<?> mediciones = strategosMedicionesService.getMedicionesPeriodo(act.getIndicadorId(), serId, new Integer(anoDesde), new Integer(anoHasta), new Integer(periodoDesde), new Integer(periodoHasta));
			              for (Iterator<?> iter2 = mediciones.iterator(); iter2.hasNext(); ) 
			              {
			            	  Medicion medicion = (Medicion)iter2.next();
			            	  if (serId.longValue() == 0 || serId.longValue() == 5)
			            	  {
			            		  if (medicion.getMedicionId().getAno().intValue() > maximoAnoReal)
			            			  maximoAnoReal = medicion.getMedicionId().getAno();
			            		  if (medicion.getMedicionId().getAno().intValue() >= maximoAnoReal && medicion.getMedicionId().getPeriodo().intValue() > maximoPeriodoReal)
			            			  maximoPeriodoReal = medicion.getMedicionId().getPeriodo();
			            	  }
			            	  
			            	  Medicion medicionEditada = new Medicion(new MedicionPK(new Long(medicion.getMedicionId().getIndicadorId()), new Integer(medicion.getMedicionId().getAno()), new Integer(medicion.getMedicionId().getPeriodo()), new Long(serieId[iSerie])), null, new Boolean(false));
			            	  medicionEditada.setValor((act.getPryInformacionActividad().getPeso() != null ? ((medicion.getValor() * act.getPryInformacionActividad().getPeso()) / 100) : medicion.getValor()));
			            	  medicionesActividades.add(medicionEditada);
			              }
					  }
				  }
				  
				  for (int iSerie = 0; iSerie < serieId.length; iSerie++)
				  {
		        	  List<Medicion> medicionesCompletas = new ArrayList<Medicion>();

		        	  for (Iterator<PryActividad> iter = paginaActividades.getLista().iterator(); iter.hasNext(); ) 
					  {
		        		  Double periodoAnterior = 0D;
		        		  Double valorPeriodo = 0D;
		        		  int ano = 0;
		        		  int periodo = 0;
						  PryActividad act = iter.next();

			              for (Iterator<?> iter2 = medicionesActividades.iterator(); iter2.hasNext(); ) 
			              {
			            	  Medicion medicion = (Medicion)iter2.next();
			            			  
			            	  if (medicion.getMedicionId().getIndicadorId().longValue() == act.getIndicadorId().longValue() &&
			            			  medicion.getMedicionId().getSerieId().longValue() == new Long(serieId[iSerie]).longValue())
			            	  {
				            	  Medicion proxMedicion = new Medicion(new MedicionPK(new Long(medicion.getMedicionId().getIndicadorId()), medicion.getMedicionId().getAno(), medicion.getMedicionId().getPeriodo(), medicion.getMedicionId().getSerieId()), medicion.getValor(), medicion.getProtegido());
				            	  hayMediciones = false;
				            	  Double valor = proxMedicion.getValor();
				            	  ano = proxMedicion.getMedicionId().getAno();
				            	  periodo = proxMedicion.getMedicionId().getPeriodo();
				            	  if (periodo == 1)
				            	  {
				            		  ano = ano - 1;
				            		  periodo = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), ano);
				            	  }
				            	  else
				            		  periodo = periodo - 1;
				            	  valorPeriodo = valor;
				            	  if (indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue() && act.getTipoMedicion().byteValue() == TipoMedicion.getTipoMedicionAlPeriodo().byteValue())
				            		  valor = valor - periodoAnterior;
				            	  periodoAnterior = valorPeriodo;
				            	  for (Iterator<?> iter3 = medicionesCompletas.iterator(); iter3.hasNext(); )
				            	  {
				            		  Medicion addedMedicion = (Medicion)iter3.next();
				            		  
				            		  if (proxMedicion.getMedicionId().getAno().intValue() == addedMedicion.getMedicionId().getAno().intValue() && 
				            				 proxMedicion.getMedicionId().getPeriodo().intValue() == addedMedicion.getMedicionId().getPeriodo().intValue() &&
				            				 proxMedicion.getMedicionId().getSerieId().longValue() == addedMedicion.getMedicionId().getSerieId().longValue())
				            		  {
				            			 if (addedMedicion.getValor() != null)
				            				 addedMedicion.setValor(addedMedicion.getValor() + valor);
				            			 else
				            				 addedMedicion.setValor(valor);
				            			 hayMediciones = true;
				            			 break;
				            		  }
				            	  }
				            	  
				            	  if (!hayMediciones && act.getTipoMedicion().byteValue() == TipoMedicion.getTipoMedicionAlPeriodo().byteValue() && indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionAlPeriodo().byteValue())
				            	  {
					            	  //Buscar el valor ANTERIOR para arrastrarlo al Periodo SUPERIOR
					            	  Double valorAnterior = 0D;
				            		  for (Iterator<PryActividad> iterAnterior = paginaActividades.getLista().iterator(); iterAnterior.hasNext(); )
				            		  {
				            			  PryActividad actAnterior = iterAnterior.next();
						            	  Medicion medicionAnteriorAct = new Medicion(new MedicionPK(new Long(indicador.getIndicadorId()), ano, periodo, new Long(serieId[iSerie])), null, new Boolean(false));

				            			  if (act.getActividadId() != actAnterior.getActividadId())
				            			  {
				            				  boolean buscarValorAnterior = true;
				    			              for (Iterator<?> iterMed = medicionesActividades.iterator(); iterMed.hasNext(); ) 
				    			              {
				    			            	  Medicion antMedicion = (Medicion)iterMed.next();
				    			            	  
							            		  if (antMedicion.getMedicionId().getAno().intValue() == proxMedicion.getMedicionId().getAno().intValue() && 
							            				  antMedicion.getMedicionId().getPeriodo().intValue() == proxMedicion.getMedicionId().getPeriodo().intValue() && 
							            				  antMedicion.getMedicionId().getIndicadorId().longValue() == actAnterior.getIndicadorId().longValue() &&
							            				  antMedicion.getMedicionId().getSerieId().longValue() == new Long(serieId[iSerie]).longValue())
							            		  {
							            			  {
							            				  buscarValorAnterior = false;
							            				  break;
							            			  }
							            		  }
				    			              }
				            				  
				            				  if (buscarValorAnterior)
				            				  {
				            					  DecimalFormat nf3 = new DecimalFormat("#000");
					    			              for (Iterator<?> iterMed = medicionesActividades.iterator(); iterMed.hasNext(); ) 
					    			              {
					    			            	  Medicion antMedicion = (Medicion)iterMed.next();
					    			            	  
					    			            	  int anoPeriodoBuscar = Integer.parseInt(((Integer)ano).toString() + nf3.format(periodo).toString());
					    			            	  int anoPeriodo = Integer.parseInt(antMedicion.getMedicionId().getAno().toString() + nf3.format(antMedicion.getMedicionId().getPeriodo()).toString());
								            		  if (anoPeriodo <= anoPeriodoBuscar &&
								            				  antMedicion.getMedicionId().getIndicadorId().longValue() == actAnterior.getIndicadorId().longValue() &&
								            				  antMedicion.getMedicionId().getSerieId().longValue() == new Long(serieId[iSerie]).longValue())
								            		  {
								            			  int anoPeriodoEncontrado = Integer.parseInt(medicionAnteriorAct.getMedicionId().getAno().toString() + nf3.format(medicionAnteriorAct.getMedicionId().getPeriodo()).toString());
								            			  if ((anoPeriodo >= anoPeriodoEncontrado || medicionAnteriorAct.getValor() == null) && antMedicion.getValor() != null)
								            			  {
								            				  medicionAnteriorAct.getMedicionId().setAno(antMedicion.getMedicionId().getAno());
								            				  medicionAnteriorAct.getMedicionId().setPeriodo(antMedicion.getMedicionId().getPeriodo());
						            						  medicionAnteriorAct.setValor(antMedicion.getValor());
								            			  }
								            		  }
					    			              }
				            				  }
				            				  
				    			              valorAnterior = (medicionAnteriorAct.getValor() != null ? medicionAnteriorAct.getValor() : 0D) + valorAnterior; 
				            			  }
				            		  }

				            		  proxMedicion.setValor(valorAnterior + valor);
				            		  medicionesCompletas.add(proxMedicion);
				            	  }
				            	  else if (!hayMediciones)
				            	  {
				            		  proxMedicion.setValor(valor);
				            		  medicionesCompletas.add(proxMedicion);
				            	  }
			            	  }
			              }
					  }
					  
					  if (calculandoIniciativa && new Long(serieId[iSerie]).longValue() == 1)
					  {
			              for (Iterator<?> iter2 = medicionesCompletas.iterator(); iter2.hasNext(); ) 
			              {
			            	  Medicion medicion = (Medicion)iter2.next();
		            		  if (medicion.getMedicionId().getAno().intValue() > maximoAnoProgramado)
		            			  maximoAnoProgramado = medicion.getMedicionId().getAno();
		            		  if (medicion.getMedicionId().getAno().intValue() >= maximoAnoProgramado && medicion.getMedicionId().getPeriodo().intValue() > maximoPeriodoProgramado)
		            			  maximoPeriodoProgramado = medicion.getMedicionId().getPeriodo();
			              }
						  
						  boolean agregarProgramado = false;
						  if (maximoAnoReal > maximoAnoProgramado)
							  agregarProgramado = true;
						  else if (maximoAnoReal >= maximoAnoProgramado && maximoPeriodoReal > maximoPeriodoProgramado)
							  agregarProgramado = true;
						  
						  int periodoReal = 0;
						  int periodoProgramado = 0;
						  if (agregarProgramado)
						  {
							  for (int iAno = maximoAnoProgramado; iAno < maximoAnoReal+1; iAno++)
							  {
								  if (iAno > maximoAnoProgramado)
									  periodoProgramado = 1;
								  else
									  periodoProgramado = maximoPeriodoProgramado;
								  if (iAno < maximoAnoReal)
									  periodoReal = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), iAno);
								  else 
									  periodoReal = maximoPeriodoReal;

								  for (int iPeriodo = periodoProgramado; iPeriodo < periodoReal+1; iPeriodo++)
								  {
									  agregarProgramado = true;
									  for (Iterator<?> iter2 = medicionesCompletas.iterator(); iter2.hasNext(); ) 
						              {
						            	  Medicion medicion = (Medicion)iter2.next();
						            	  if (medicion.getMedicionId().getAno().intValue() == iAno && medicion.getMedicionId().getPeriodo().intValue() == iPeriodo)
						            	  {
						            		  agregarProgramado = false;
						            		  break;
						            	  }
						              }
						              if (agregarProgramado)
						              {
						            	  Medicion proxMedicion = new Medicion(new MedicionPK(new Long(indicador.getIndicadorId()), iAno, iPeriodo, 1L), (indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionAlPeriodo().byteValue() ? (100D * (!hayPeso ? paginaActividades.getLista().size() : 1)) : 0D), false);
						            	  medicionesCompletas.add(proxMedicion);
						              }
								  }
							  }
						  }
					  }
					  
		        	  if (medicionesCompletas.size() > 0)
		        	  {
		        		  Set<Medicion> medicionesAux = new LinkedHashSet<Medicion>();
			              medicionesAux.addAll(medicionesCompletas);
						  SerieIndicador serieIndicador = (SerieIndicador)strategosMedicionesService.load(SerieIndicador.class, new SerieIndicadorPK(new Long(serieId[iSerie]), indicador.getIndicadorId()));
				          if (serieIndicador == null)
				        	  serieIndicador = new SerieIndicador();
			              
			              serieIndicador.setIndicador(indicador);
			              serieIndicador.setMediciones(medicionesAux);
			              serieIndicador.setPk(new SerieIndicadorPK(new Long(serieId[iSerie]), indicador.getIndicadorId()));
			              seriesIndicadores.add(serieIndicador);
		        	  }
				  }
				  
				  List<Medicion> medicionesEditadas = new ArrayList<Medicion>();
				  for (Iterator<?> iter = seriesIndicadores.iterator(); iter.hasNext(); )
				  {
					  SerieIndicador series = (SerieIndicador)iter.next();
					  Long serie = null;
					  for (Iterator<?> iter2 = series.getMediciones().iterator(); iter2.hasNext(); )
					  {
						  Medicion medAux = (Medicion)iter2.next();
						  serie = medAux.getMedicionId().getSerieId();
						  if (medAux.getValor() != null || medAux.getValor() != 0)
						  {
							  medAux.setValor(medAux.getValor() / (!hayPeso ? paginaActividades.getLista().size() : 1));

					          Medicion medicionEditada = new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(medAux.getMedicionId().getAno()), new Integer(medAux.getMedicionId().getPeriodo()), new Long(serie)), medAux.getValor(), new Boolean(false));
					          medicionesEditadas.add(medicionEditada);
						  }
					  }

					  List<Medicion> medicionesExistentes = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), series.getPk().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
					  for (Iterator<Medicion> iterMediciones = medicionesExistentes.iterator(); iterMediciones.hasNext(); )
					  {
						  Medicion medicion = (Medicion)iterMediciones.next();
						  boolean hayMedicion = false;
						  for (Iterator<Medicion> iter3 = medicionesEditadas.iterator(); iter3.hasNext(); )
						  {
							  Medicion medicionEditada = (Medicion)iter3.next();
							  if (medicionEditada.getMedicionId().getAno().intValue() == medicion.getMedicionId().getAno().intValue() &&
									  medicionEditada.getMedicionId().getPeriodo().intValue() == medicion.getMedicionId().getPeriodo().intValue() &&
									  medicionEditada.getMedicionId().getIndicadorId().longValue() == medicion.getMedicionId().getIndicadorId().longValue() &&
									  medicionEditada.getMedicionId().getSerieId().longValue() == medicion.getMedicionId().getSerieId().longValue())
							  {
								  hayMedicion = true;
								  break;
							  }
						  }
						  if (!hayMedicion)
						  {
					          Medicion medicionEditada = new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(medicion.getMedicionId().getAno()), new Integer(medicion.getMedicionId().getPeriodo()), series.getPk().getSerieId()), null, new Boolean(false));
					          medicionesEditadas.add(medicionEditada);
						  }
					  }
				  }
				  strategosMedicionesService.close();

				  if (medicionesEditadas.size() > 0)
				  {
					  strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
					  
					  List<Medicion> medicionesaGuardar = new ArrayList<Medicion>();
					  for (Iterator<?> iterMedicion = medicionesEditadas.iterator(); iterMedicion.hasNext(); )
					  {
						  Medicion medicion =  (Medicion)iterMedicion.next();
						  medicionesaGuardar.add(new Medicion(new MedicionPK(medicion.getMedicionId().getIndicadorId(), new Integer(medicion.getMedicionId().getAno()), new Integer(medicion.getMedicionId().getPeriodo()), medicion.getMedicionId().getSerieId()), medicion.getValor(), medicion.getProtegido()));
					  }
					  
					  respuesta = strategosMedicionesService.saveMediciones(medicionesaGuardar, null, getUsuarioConectado(request), new Boolean(true), new Boolean(true));
					  if (respuesta == 10000 && medicionesEditadas.size() > 0)
						  respuesta = strategosMedicionesService.actualizarSeriePorPeriodos(anoDesde, anoHasta, periodoDesde, periodoHasta, medicionesEditadas, true, true, getUsuarioConectado(request));

					  if (respuesta == 10000 && calculandoIniciativa)
					  {
						  //Agregar el Programado al indicador Eficacia
						  Long indicadorIdTemp = iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficacia());
						  Indicador indicadorTemp = null;
						  if (indicadorIdTemp != null)
							  indicadorTemp = (Indicador)strategosMedicionesService.load(Indicador.class, new Long(indicadorIdTemp));
						  if (indicadorTemp != null)
						  {
							  List<Medicion> medicionesProgramadas = new ArrayList<Medicion>();
							  for (Iterator<?> iterMedicion = medicionesEditadas.iterator(); iterMedicion.hasNext(); )
							  {
								  Medicion medicion =  (Medicion)iterMedicion.next();
								  if (medicion.getMedicionId().getSerieId().longValue() == SerieTiempo.getSerieProgramado().getSerieId().longValue())
									  medicionesProgramadas.add(new Medicion(new MedicionPK(indicadorTemp.getIndicadorId(), new Integer(medicion.getMedicionId().getAno()), new Integer(medicion.getMedicionId().getPeriodo()), medicion.getMedicionId().getSerieId()), 100D, false));
							  }
							  if (respuesta == 10000 && medicionesProgramadas.size() > 0)
								  respuesta = strategosMedicionesService.saveMediciones(medicionesProgramadas, null, getUsuarioConectado(request), new Boolean(true), new Boolean(true));
						  }
						  
						  //Agregar el Programado al indicador Eficiencia
						  indicadorIdTemp = iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficiencia());
						  indicadorTemp = null;
						  if (indicadorIdTemp != null)
							  indicadorTemp = (Indicador)strategosMedicionesService.load(Indicador.class, new Long(indicadorIdTemp));
						  if (indicadorTemp != null)
						  {
							  List<Medicion> medicionesProgramadas = new ArrayList<Medicion>();
							  for (Iterator<?> iterMedicion = medicionesEditadas.iterator(); iterMedicion.hasNext(); )
							  {
								  Medicion medicion =  (Medicion)iterMedicion.next();
								  if (medicion.getMedicionId().getSerieId().longValue() == SerieTiempo.getSerieProgramado().getSerieId().longValue())
									  medicionesProgramadas.add(new Medicion(new MedicionPK(indicadorTemp.getIndicadorId(), new Integer(medicion.getMedicionId().getAno()), new Integer(medicion.getMedicionId().getPeriodo()), medicion.getMedicionId().getSerieId()), 100D, false));
							  }
							  if (respuesta == 10000 && medicionesProgramadas.size() > 0)
								  respuesta = strategosMedicionesService.saveMediciones(medicionesProgramadas, null, getUsuarioConectado(request), new Boolean(true), new Boolean(true));
						  }				  
					  }

					  if (respuesta == 10000 && actividadPadre != null)
					  {
						  Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), TipoCorte.getTipoCorteTransversal(), indicador.getTipoCargaMedicion());
						  if (medicionReal != null)
						  {
							  List<Medicion> medicionesAlertas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieAlerta(), medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getPeriodo(), medicionReal.getMedicionId().getPeriodo());
							  if (medicionesAlertas.size() > 0 && ((Medicion)medicionesAlertas.get(0)).getValor() != null)
							  {
								  Medicion medicionAlerta = (Medicion)medicionesAlertas.get(0);
								  actividadPadre.setAlerta(AlertaIndicador.ConvertDoubleToByte(medicionAlerta.getValor()));
								  actividadPadre.setFechaUltimaMedicion(medicionAlerta.getMedicionId().getPeriodo() + "/" + medicionAlerta.getMedicionId().getAno());
							  }
							  else
							  {
								  Indicador indicadorUl = (Indicador)strategosMedicionesService.load(Indicador.class, new Long(indicador.getIndicadorId()));
								  actividadPadre.setFechaUltimaMedicion(indicadorUl.getFechaUltimaMedicion());
							  }
						  }
						  else
						  {
							  Indicador indicadorUl = (Indicador)strategosMedicionesService.load(Indicador.class, new Long(indicador.getIndicadorId()));
							  actividadPadre.setFechaUltimaMedicion(indicadorUl.getFechaUltimaMedicion());
						  }

			  			  Double totalReal = null;
			  			  Double totalProgramado = null;
			  			  Double ultimaMedicionReal = null;
						  if (actividadPadre.getTipoMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
						  {
							  List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
							  List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
							  for (Iterator<Medicion> iterMediciones = medicionesReales.iterator(); iterMediciones.hasNext(); ) 
							  {
								  Medicion medicion = (Medicion)iterMediciones.next();
								  if (medicion.getValor() != null && totalReal == null)
									  totalReal = 0D;
								  totalReal = totalReal + medicion.getValor();
								  ultimaMedicionReal = totalReal;
								  for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext(); ) 
								  {	
									  Medicion medicionProgramada = (Medicion)iterMedicionesProgramadas.next();
									  if (medicion.getMedicionId().getAno().intValue() == medicionProgramada.getMedicionId().getAno().intValue() &&
			  			  					medicion.getMedicionId().getPeriodo().intValue() == medicionProgramada.getMedicionId().getPeriodo().intValue())
									  {
										  if (medicionProgramada.getValor() != null && totalProgramado == null)
											  totalProgramado = 0D;
										  totalProgramado = totalProgramado + medicionProgramada.getValor();
										  break;
									  }
								  }
							  }
						  }
						  else
						  {
							  totalReal = medicionReal != null ? medicionReal.getValor() : null;
							  if (medicionReal != null && medicionReal.getValor() != null)
								  ultimaMedicionReal = medicionReal.getValor();

							  if (totalReal != null)
							  {
								  List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
								  DecimalFormat nf3 = new DecimalFormat("#000");
								  int anoPeriodoBuscar = Integer.parseInt(((Integer)medicionReal.getMedicionId().getAno()).toString() + nf3.format(medicionReal.getMedicionId().getPeriodo()).toString());
								  for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext(); ) 
								  {
									  Medicion medProgramada = (Medicion)iterMedicionesProgramadas.next();
									  int anoPeriodo = Integer.parseInt(medProgramada.getMedicionId().getAno().toString() + nf3.format(medProgramada.getMedicionId().getPeriodo()).toString());
									  if (anoPeriodo <= anoPeriodoBuscar)
										  totalProgramado = medProgramada.getValor();
								  }
							  }
						  }
		  			  		
						  actividadPadre.setPorcentajeCompletado(ultimaMedicionReal);
						  actividadPadre.setPorcentajeEjecutado(totalReal);
						  actividadPadre.setPorcentajeEsperado(totalProgramado);
		  			  		
						  respuesta = strategosPryActividadesService.saveActividad(actividadPadre, getUsuarioConectado(request), false);
					  }
					  else if (respuesta == 10000 && actividadPadre == null)
					  {
						  // Si quieres la alerta al ultimo periodo ejecutado es este metodo
						  Medicion medicionReal = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieRealId(), indicador.getValorInicialCero(), TipoCorte.getTipoCorteTransversal(), indicador.getTipoCargaMedicion());
						  if (medicionReal != null)
						  {
							  List<Medicion> medicionesAlertas = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieAlerta(), medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getAno(), medicionReal.getMedicionId().getPeriodo(), medicionReal.getMedicionId().getPeriodo());
							  if (medicionesAlertas.size() > 0 && ((Medicion)medicionesAlertas.get(0)).getValor() != null)
							  {
								  Medicion medicionAlerta = (Medicion)medicionesAlertas.get(0);
								  iniciativa.setAlerta(AlertaIndicador.ConvertDoubleToByte(medicionAlerta.getValor()));
								  iniciativa.setFechaUltimaMedicion(medicionAlerta.getMedicionId().getPeriodo() + "/" + medicionAlerta.getMedicionId().getAno());
							  }
							  else
							  {
								  // Si quieres la alerta del indicador
							  	  Indicador indicadorUl = (Indicador)strategosMedicionesService.load(Indicador.class, new Long(indicador.getIndicadorId()));
							  	  iniciativa.setAlerta(indicadorUl.getAlerta());
							  	  iniciativa.setFechaUltimaMedicion(indicadorUl.getFechaUltimaMedicion());
							  }
						  }
						  else
						  {
							  // Si quieres la alerta del indicador
							  //Verificar si hay mediciones reales
							  List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
							  if (medicionesReales.size() == 0)
							  {
							  	  iniciativa.setAlerta(null);
							  	  iniciativa.setFechaUltimaMedicion(null);
							  	  iniciativa.setPorcentajeCompletado(null);
							  }								  
							  else
							  {
							  	  Indicador indicadorUl = (Indicador)strategosMedicionesService.load(Indicador.class, new Long(indicador.getIndicadorId()));
							  	  iniciativa.setAlerta(indicadorUl.getAlerta());
							  	  iniciativa.setFechaUltimaMedicion(indicadorUl.getFechaUltimaMedicion());
							  }
						  }

						  Double totalReal = null;
						  if (indicador.getTipoCargaMedicion().byteValue() == TipoMedicion.getTipoMedicionEnPeriodo().byteValue())
						  {
							  List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
							  for (Iterator<Medicion> iterMediciones = medicionesReales.iterator(); iterMediciones.hasNext(); ) 
							  {
								  Medicion medicion = (Medicion)iterMediciones.next();
								  if (medicion.getValor() != null && totalReal == null)
									  totalReal = 0D;
								  totalReal = totalReal + medicion.getValor();
							  }
						  }
						  else
						  {
			  			  		if (medicionReal != null && medicionReal.getValor() != null && totalReal == null)
									  totalReal = 0D;
			  			  		totalReal = medicionReal != null ? medicionReal.getValor() : null;
						  }
						  
						  
						  if(totalReal != null){
							  if(totalReal >0){
								  if(totalReal >100){
									  iniciativa.setPorcentajeCompletado(100.00); 
								  }else{
									  iniciativa.setPorcentajeCompletado(totalReal);
								  }
							  }
						  }
										
						  StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
				
						  Indicador indicadorFinal = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadorId));
								
						  if(indicadorFinal != null && indicadorFinal.getUltimaMedicion() !=null && indicadorFinal.getUltimaMedicion() >100){
							 indicadorFinal.setUltimaMedicion(100.00); 
						  }
							  
						  strategosIndicadoresService.saveIndicador(indicadorFinal, getUsuarioConectado(request));
							  
						  
						  
						  if (iniciativa.getEstatus() != null && iniciativa.getEstatus().getId().longValue() != EstatusType.getEstatusCencelado().longValue() && iniciativa.getEstatus().getId().longValue() != EstatusType.getEstatusSuspendido().longValue())
							  iniciativa.setEstatusId(CalcularEstatus(iniciativa.getPorcentajeCompletado()));
		  			  		
						  StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(strategosPryActividadesService);
						  respuesta = strategosIniciativasService.saveIniciativa(iniciativa, getUsuarioConectado(request), false);
						  strategosIniciativasService.close();
						  if (calculandoIniciativa && respuesta == 10000)
							  CalcularIniciativaAsociadas(iniciativa, request);
						  
						  strategosIndicadoresService.close();
					  }
					  
					  
					  strategosMedicionesService.close();
				  }
			  }
			  else
			  {
				  StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(strategosPryActividadesService);
				  iniciativa.setFechaUltimaMedicion(null);
				  iniciativa.setPorcentajeCompletado(null);
				  iniciativa.setAlerta(null);
				  respuesta = strategosIniciativasService.saveIniciativa(iniciativa, getUsuarioConectado(request), false);
				  strategosIniciativasService.close();
				
				  if (respuesta == VgcReturnCode.DB_OK)
					  respuesta = strategosMedicionesService.deleteMediciones(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				  strategosMedicionesService.close();
				  try 
				  {
					  StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
					  if (respuesta == VgcReturnCode.DB_OK)
						  respuesta = strategosIndicadoresService.actualizarDatosIndicador(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()), null, null, null);
					  strategosIndicadoresService.close();
				  } 
				  catch (Throwable e) 
				  {
					  respuesta = VgcReturnCode.DB_PK_AK_VIOLATED;
				  }
				  
			  }
		  }
		  else
		  {
			  StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(strategosPryActividadesService);
			  iniciativa.setFechaUltimaMedicion(null);
			  iniciativa.setPorcentajeCompletado(null);
			  iniciativa.setAlerta(null);
			  respuesta = strategosIniciativasService.saveIniciativa(iniciativa, getUsuarioConectado(request), false);
			  strategosIniciativasService.close();
			
			  StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
			  if (respuesta == VgcReturnCode.DB_OK)
				  respuesta = strategosMedicionesService.deleteMediciones(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
			  strategosMedicionesService.close();
			  try 
			  {
				  StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
				  if (respuesta == VgcReturnCode.DB_OK)
					  respuesta = strategosIndicadoresService.actualizarDatosIndicador(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()), null, null, null);
				  strategosIndicadoresService.close();
			  } 
			  catch (Throwable e) 
			  {
				  respuesta = VgcReturnCode.DB_PK_AK_VIOLATED;
			  }
		  }
		  
		  strategosPryActividadesService.close();

		  if (actividad.getPadreId() != null && actividadPadre != null)
	      	  if (respuesta == VgcReturnCode.DB_OK)
				  respuesta = CalcularPadre(actividadPadre, iniciativaId, request);
		  
		  return respuesta;
	}
	
	public Long CalcularEstatus(Double porcentajeCompletado)
	{
		StrategosIniciativaEstatusService strategosIniciativaEstatusService = StrategosServiceFactory.getInstance().openStrategosIniciativaEstatusService();
		Long estatusId = strategosIniciativaEstatusService.calcularEstatus(porcentajeCompletado);
		strategosIniciativaEstatusService.close();
		
		return estatusId;
	}
	  
	private int CalcularIniciativaAsociadas(Iniciativa iniciativa, HttpServletRequest request)
	{
		  int resultado = 10000;
		  
		  Map<String, Object> filtros = new HashMap<String, Object>();
		  filtros.put("objetoAsociadoId", iniciativa.getIniciativaId());
		  
		  StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		  PaginaLista paginaActividades = strategosPryActividadesService.getActividades(0, 0, "fila", "ASC", false, filtros);
		  
		  StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		  for (Iterator<?> iter = paginaActividades.getLista().iterator(); iter.hasNext(); )
		  {
			  PryActividad actividad = (PryActividad)iter.next();
			  
			  PryProyecto proyecto = (PryProyecto) strategosPryActividadesService.load(PryProyecto.class, actividad.getProyectoId());
			  actividad.setComienzoPlan(proyecto.getComienzoPlan());
			  actividad.setComienzoReal(proyecto.getComienzoReal());
			  actividad.setFinPlan(proyecto.getFinPlan());
			  actividad.setFinReal(proyecto.getFinReal());
			  
			  Indicador indicador = (Indicador)strategosPryActividadesService.load(Indicador.class, new Long(actividad.getIndicadorId()));
			  actividad.setFechaUltimaMedicion(indicador.getFechaUltimaMedicion());
			  Medicion medicionAlerta = strategosMedicionesService.getUltimaMedicion(indicador.getIndicadorId(), indicador.getFrecuencia(), indicador.getMesCierre(), SerieTiempo.getSerieAlerta(), indicador.getValorInicialCero(), TipoCorte.getTipoCorteTransversal(), indicador.getTipoCargaMedicion());
			  if (medicionAlerta != null) 
				  actividad.setAlerta(AlertaIndicador.ConvertDoubleToByte(medicionAlerta.getValor()));
			  
  			  Double totalReal = null;
  			  Double totalProgramado = null;
  			  Double ultimaMedicionReal = null;
			  List<Medicion> medicionesReales = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieReal().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
			  List<Medicion> medicionesProgramada = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieProgramado().getSerieId(), new Integer(0000), new Integer(9999), new Integer(000), new Integer(999));
			  for (Iterator<Medicion> iterMediciones = medicionesReales.iterator(); iterMediciones.hasNext(); ) 
			  {
				  Medicion medicion = (Medicion)iterMediciones.next();
				  if (medicion.getValor() != null && totalReal == null)
					  totalReal = 0D;
				  totalReal = totalReal + medicion.getValor();
				  if (medicion.getValor() != null)
					  ultimaMedicionReal = medicion.getValor();
				  
				  for (Iterator<Medicion> iterMedicionesProgramadas = medicionesProgramada.iterator(); iterMedicionesProgramadas.hasNext(); ) 
				  {	
					  Medicion medicionProgramada = (Medicion)iterMedicionesProgramadas.next();
					  if (medicion.getMedicionId().getAno().intValue() == medicionProgramada.getMedicionId().getAno().intValue() &&
			  					medicion.getMedicionId().getPeriodo().intValue() == medicionProgramada.getMedicionId().getPeriodo().intValue())
					  {
						  if (medicionProgramada.getValor() != null && totalProgramado == null)
							  totalProgramado = 0D;
						  totalProgramado = totalProgramado + medicionProgramada.getValor();
						  break;
					  }
				  }
			  }
				  		
			  actividad.setPorcentajeCompletado(ultimaMedicionReal);
			  actividad.setPorcentajeEjecutado(totalReal);
			  actividad.setPorcentajeEsperado(totalProgramado);
				  		
			  resultado = strategosPryActividadesService.saveActividad(actividad, getUsuarioConectado(request), false);
			  if (resultado == 10000)
			  {
				  resultado = CalcularFechasPadres(actividad.getPadreId(), actividad.getProyectoId(), getUsuarioConectado(request));
				  if (resultado == 10000)
				  {
					  StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(strategosPryActividadesService);
					  Iniciativa ini = (Iniciativa)strategosIniciativasService.getIniciativaByProyecto(actividad.getProyectoId());
					  strategosIniciativasService.close();

					  if (ini != null)
						  resultado = CalcularPadre(actividad, ini.getIniciativaId(), request);
				  }
			  }
			  
			  if (resultado != 10000)
				  break;
		  }
		  
		  strategosMedicionesService.close();
		  strategosPryActividadesService.close();
		  
		  return resultado;
	}
	
	public int CalcularFechasPadres(Long padreId, Long proyectoId, Usuario usuario)
	{
			int respuesta = 10000;
			
			Map<String, Object> filtros = new HashMap<String, Object>();
			List<PryActividad> actividades = new ArrayList<PryActividad>();
		    Date comienzoPlan = null;
		    Date finPlan = null;
		    Date comienzoReal = null;
		    Date finReal = null;
		    
			filtros = new HashMap<String, Object>();
			filtros.put("padreId", padreId);
			filtros.put("proyectoId", proyectoId);
			
			String atributoOrden = "fila";
			String tipoOrden = "ASC";
			int pagina = 0;
			StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
			PryActividad actividadPadre = null;
			if (padreId != null)
				actividadPadre = (PryActividad)strategosPryActividadesService.load(PryActividad.class, padreId);
			actividades = strategosPryActividadesService.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) 
			{
				PryActividad actividad = (PryActividad)iter.next();
				if (comienzoPlan == null)
					comienzoPlan = actividad.getComienzoPlan();
				if (finPlan == null)
					finPlan = actividad.getFinPlan();
				if (comienzoReal == null)
					comienzoReal = actividad.getComienzoReal();
				if (finReal == null)
					finReal = actividad.getFinReal();
				
				if (actividad.getComienzoPlan() != null && actividad.getComienzoPlan().before(comienzoPlan))
					comienzoPlan = actividad.getComienzoPlan();
				if (actividad.getFinPlan() != null && actividad.getFinPlan().after(finPlan))
					finPlan = actividad.getFinPlan();
				if (actividad.getComienzoReal() != null && actividad.getComienzoReal().before(comienzoReal))
					comienzoReal = actividad.getComienzoReal();
				if (actividad.getFinReal() != null && actividad.getFinReal().after(finReal))
					finReal = actividad.getFinReal();
			}
			
			if (padreId == null)
			{
				StrategosPryProyectosService strategosPryProyectosService = StrategosServiceFactory.getInstance().openStrategosProyectosService(strategosPryActividadesService);
				PryProyecto proyecto = (PryProyecto) strategosPryProyectosService.load(PryProyecto.class, proyectoId);
				
			    if (comienzoPlan != null)
			    	proyecto.setComienzoPlan(comienzoPlan);
			    else 
			    	proyecto.setComienzoPlan(null);
			
			    if (finPlan != null)
			    	proyecto.setFinPlan(finPlan);
			    else 
			    	proyecto.setFinPlan(null);

			    if (comienzoReal != null)
			    	proyecto.setComienzoReal(comienzoReal);
			    else 
			    	proyecto.setComienzoReal(null);
			
			    if (finReal != null)
			    	proyecto.setFinReal(finReal);
			    else 
			    	proyecto.setFinReal(null);

			    respuesta = strategosPryProyectosService.saveProyecto(proyecto, usuario);
			    strategosPryProyectosService.close();

				StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(strategosPryActividadesService);
		    	Iniciativa iniciativa = null;
				if (proyectoId != null)
					iniciativa = (Iniciativa)strategosIniciativasService.getIniciativaByProyecto(proyectoId);
				strategosIniciativasService.close();
			    
				if (iniciativa != null)
				{
					filtros = new HashMap<String, Object>();
					filtros.put("objetoAsociadoId", iniciativa.getIniciativaId().toString());
					atributoOrden = "fila";
					tipoOrden = "ASC";
					pagina = 0;
					actividades = strategosPryActividadesService.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();
					
					for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) 
					{
						PryActividad actividad = (PryActividad)iter.next();
						padreId = actividad.getPadreId();
						proyectoId = actividad.getProyectoId(); 
					    if (comienzoPlan != null)
					    	actividad.setComienzoPlan(comienzoPlan);
					    else 
					    	actividad.setComienzoPlan(null);
					
					    if (finPlan != null)
					    	actividad.setFinPlan(finPlan);
					    else 
					    	actividad.setFinPlan(null);

					    if (comienzoReal != null)
					    	actividad.setComienzoReal(comienzoReal);
					    else 
					    	actividad.setComienzoReal(null);
					
					    if (finReal != null)
					    	actividad.setFinReal(finReal);
					    else 
					    	actividad.setFinReal(null);
					    
				    	actividad.setDuracionPlan(null);
					    
					    respuesta = strategosPryActividadesService.saveActividad(actividad, usuario, false);
					    if (respuesta != 10000)
					    	break;
					    if (respuesta == 10000)
					    	respuesta = CalcularFechasPadres(padreId, proyectoId, usuario);
				    }
				}
			}
			else
			{
				if (actividadPadre != null)
				{
					padreId = actividadPadre.getPadreId();
				    if (comienzoPlan != null)
				    	actividadPadre.setComienzoPlan(comienzoPlan);
				    else 
				    	actividadPadre.setComienzoPlan(null);
				
				    if (finPlan != null)
				    	actividadPadre.setFinPlan(finPlan);
				    else 
				    	actividadPadre.setFinPlan(null);

				    if (comienzoReal != null)
				    	actividadPadre.setComienzoReal(comienzoReal);
				    else 
				    	actividadPadre.setComienzoReal(null);
				
				    if (finReal != null)
				    	actividadPadre.setFinReal(finReal);
				    else 
				    	actividadPadre.setFinReal(null);

				    respuesta = strategosPryActividadesService.saveActividad(actividadPadre, usuario, false);
				    if (respuesta == 10000)
				    	respuesta = CalcularFechasPadres(padreId, proyectoId, usuario);
				}
			}
			
			strategosPryActividadesService.close();
			
			return respuesta;
	}
}
