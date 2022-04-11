package com.visiongc.app.strategos.web.struts.mediciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.util.MetaAnualParciales;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.responsables.model.util.TipoResponsabilidad;
import com.visiongc.app.strategos.seriestiempo.StrategosSeriesTiempoService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.unidadesmedida.StrategosUnidadesService;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm.EditarStatus;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm.TipoCargaActividad;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm.TipoSource;
import com.visiongc.app.strategos.web.struts.util.Columna;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;

import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public final class EditarMedicionesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
	    Locale currentLocale = new Locale("en","US");
	    NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
	    DecimalFormat decimalformat = (DecimalFormat)numberFormatter;
	    decimalformat.applyPattern("#,##0.00");

	    EditarMedicionesForm editarMedicionesForm = (EditarMedicionesForm)form;

	    boolean editarMediciones = getPermisologiaUsuario(request).tienePermiso("INDICADOR_MEDICION_CARGAR");
	    
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(getUsuarioConectado(request).getUsuarioId(), "Strategos.Forma.Configuracion.Columnas", "visorLista.Medicion");
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario(); 
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Forma.Configuracion.Columnas");
			pk.setObjeto("visorLista.Medicion");
			pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
			configuracionUsuario.setPk(pk);
			configuracionUsuario.setData(editarMedicionesForm.setColumnasDefault());
			frameworkService.saveConfiguracionUsuario(configuracionUsuario, getUsuarioConectado(request));
		}
		else
		{
	  		DocumentBuilderFactory factory  =  DocumentBuilderFactory.newInstance();
	  		DocumentBuilder builder = factory.newDocumentBuilder();
	        Document doc = builder.parse(new InputSource(new StringReader(TextEncoder.deleteCharInvalid(configuracionUsuario.getData()))));
			NodeList lista = doc.getElementsByTagName("columnas");
			if (lista.getLength() == 0)
			{
				frameworkService.deleteConfiguracionUsuario(configuracionUsuario, getUsuarioConectado(request));
				configuracionUsuario = new ConfiguracionUsuario(); 
				ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
				pk.setConfiguracionBase("Strategos.Forma.Configuracion.Columnas");
				pk.setObjeto("visorLista.Medicion");
				pk.setUsuarioId(getUsuarioConectado(request).getUsuarioId());
				configuracionUsuario.setPk(pk);
				configuracionUsuario.setData(editarMedicionesForm.setColumnasDefault());
				frameworkService.saveConfiguracionUsuario(configuracionUsuario, getUsuarioConectado(request));
			}
		}
		frameworkService.close();
		editarMedicionesForm.setColumnas(configuracionUsuario.getData());
	    
	    String funcion = null;
	    if (request.getParameter("funcion") != null) 
	    	funcion = request.getParameter("funcion");
	    
		ActionMessages messages = getMessages(request);

		String[] serieId = request.getParameterValues("serieId");
		Long inciativaId = null;
		if (request.getParameter("inciativaId") != null)
			inciativaId = new Long(request.getParameter("inciativaId"));
			
		if (serieId == null)
			serieId = editarMedicionesForm.getSerieId();
			
		Byte source = Byte.parseByte(request.getParameter("source"));
		if (source.byteValue() == TipoSource.SOURCE_CLASE)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_CLASE);
		else if (source.byteValue() == TipoSource.SOURCE_PLAN)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_PLAN);
		else if (source.byteValue() == TipoSource.SOURCE_INICIATIVA)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_INICIATIVA);
		else
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_ACTIVIDAD);
		
		if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD)
			serieId = setup(editarMedicionesForm, request);
		editarMedicionesForm.setSerieId(serieId);
		
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(strategosMedicionesService);

		List<Indicador> indicadoresSeleccionados = new ArrayList<Indicador>();
		if (editarMedicionesForm.getIndicadores() != null)
			indicadoresSeleccionados.addAll(editarMedicionesForm.getIndicadores());
		else
			indicadoresSeleccionados = null;

		if (editarMedicionesForm.getIndicadores() == null) 
			setIndicadores(editarMedicionesForm, strategosIndicadoresService);
		else if (!editarMedicionesForm.getSoloSeleccionados().booleanValue() && editarMedicionesForm.getSourceScreen().byteValue() != TipoSource.SOURCE_ACTIVIDAD)
			setIndicadores(editarMedicionesForm, strategosIndicadoresService);
		else if (!editarMedicionesForm.getSoloSeleccionados().booleanValue() && editarMedicionesForm.getSourceScreen().byteValue() == TipoSource.SOURCE_ACTIVIDAD)
			setIndicadores(editarMedicionesForm, strategosIndicadoresService);
		else if ((editarMedicionesForm.getIndicadores() != null) && (editarMedicionesForm.getIndicadores().size() > 0)) 
		{
			byte frecuencia = editarMedicionesForm.getFrecuencia().byteValue();
			int index = 0;
			while (index < editarMedicionesForm.getIndicadores().size()) 
			{
				Indicador indicador = (Indicador)editarMedicionesForm.getIndicadores().get(index);

				if (indicador.getFrecuencia().byteValue() != frecuencia)
					editarMedicionesForm.getIndicadores().remove(index);
				else 
					index++;
			}
		}
    
		if ((editarMedicionesForm.getIndicadores() != null) && (editarMedicionesForm.getIndicadores().size() > 0))
		{
			byte frecuencia = editarMedicionesForm.getFrecuencia().byteValue();
			if (frecuencia == Frecuencia.getFrecuenciaDiaria().byteValue()) 
			{
				String fechaDesde = editarMedicionesForm.getFechaDesde();
				int indice1 = fechaDesde.indexOf("/");
				String dia = fechaDesde.substring(0, indice1);
				int indice2 = indice1 + 1 + fechaDesde.substring(indice1 + 1).indexOf("/");
				String mes = fechaDesde.substring(indice1 + 1, indice2);
				String ano = fechaDesde.substring(indice2 + 1);
				Calendar desde = Calendar.getInstance();
				desde.set(1, Integer.parseInt(ano));
				desde.set(2, Integer.parseInt(mes) - 1);
				desde.set(5, Integer.parseInt(dia));
				editarMedicionesForm.setAnoDesde(ano);
				editarMedicionesForm.setPeriodoDesde(new Integer(desde.get(6)));

				String fechaHasta = editarMedicionesForm.getFechaHasta();
				indice1 = fechaHasta.indexOf("/");
				dia = fechaHasta.substring(0, indice1);
				indice2 = indice1 + 1 + fechaHasta.substring(indice1 + 1).indexOf("/");
				mes = fechaHasta.substring(indice1 + 1, indice2);
				ano = fechaHasta.substring(indice2 + 1);
				Calendar hasta = Calendar.getInstance();
				hasta.set(1, Integer.parseInt(ano));
				hasta.set(2, Integer.parseInt(mes) - 1);
				hasta.set(5, Integer.parseInt(dia));
				editarMedicionesForm.setAnoHasta(ano);
				editarMedicionesForm.setPeriodoHasta(new Integer(hasta.get(6)));
			}

			int anoDesde = Integer.parseInt(editarMedicionesForm.getAnoDesde());
			int anoHasta = Integer.parseInt(editarMedicionesForm.getAnoHasta());
			int periodoDesde = editarMedicionesForm.getPeriodoDesde().intValue();
			int periodoHasta = editarMedicionesForm.getPeriodoHasta().intValue();
			boolean bloqueadoPorFecha = false;
			boolean indicadorBloqueado = false;
			boolean medicionProtegida = false;
			boolean chequearBloqueo = true;
			boolean serieBloqueda = false;
			boolean mensajeResponsableAgregado = false;
			int totalMediciones = 0;
			List<SerieIndicador> seriesIndicadores = new ArrayList<SerieIndicador>();
			SerieIndicador serieIndicadorLoad = null;
      
			String indicadores = "";
			
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(strategosMedicionesService);
			StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService(strategosMedicionesService);
			
			StrategosUnidadesService strategosUnidadesService = StrategosServiceFactory.getInstance().openStrategosUnidadesService();
	    	UnidadMedida unidad = strategosUnidadesService.getUnidadMedidaPorcentaje();
	    	strategosUnidadesService.close();
			
			Iniciativa iniciativa = null;
			PryActividad actividad = null;
			for (Iterator<?> iterInd = editarMedicionesForm.getIndicadores().iterator(); iterInd.hasNext(); )
			{
				Indicador indicador = (Indicador)iterInd.next();
				indicadorBloqueado = false;
				actividad = null;
				if (indicadores.equals(""))
					indicadores = indicador.getIndicadorId().toString();
				else
					indicadores = indicadores + "|" + indicador.getIndicadorId().toString();
				
				if ((indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoNominal())) || (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoOrdinal())))
				{
					indicador = (Indicador) strategosIndicadoresService.load(Indicador.class, indicador.getIndicadorId());
					indicador.getEscalaCualitativa().size();
				}

				chequearBloqueo = true;
				iniciativa = strategosIniciativasService.getIniciativaByIndicador(indicador.getIndicadorId());
				PryActividad act = null;
				if (editarMedicionesForm.getSourceScreen().byteValue() == TipoSource.SOURCE_ACTIVIDAD && indicador.getActividadId() == null)
				{
					act = strategosPryActividadesService.getActividadByIndicador(new Long(indicador.getIndicadorId()));
					indicador.setActividadId(act.getActividadId());
				}
				else if (indicador.getActividadId() != null && indicador.getActividadId().longValue() != 0L)
					act = (PryActividad) strategosPryActividadesService.load(PryActividad.class, indicador.getActividadId());
					
				if (!indicadorBloqueado && indicador.getActividadId() != null && indicador.getActividadId().longValue() != 0L && act != null)
				{
					chequearBloqueo = false;
					indicadorBloqueado = protegerActividades(act);
					if (!indicadorBloqueado)
					{
						Iniciativa ini = strategosIniciativasService.getIniciativaByProyecto(act.getProyectoId());
						if (ini != null)
							indicadorBloqueado = ini.getEstatus().getBloquearMedicion();
					}
				}
				else if (!indicadorBloqueado && editarMedicionesForm.getIniciativaId() != null && editarMedicionesForm.getIniciativaId().longValue() != 0L)
				{
					chequearBloqueo = false;
					if (iniciativa != null && iniciativa.getIniciativaId() != null && iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()) != null && indicador.getIndicadorId().longValue() == iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).longValue())
						indicadorBloqueado = protegerMediciones(editarMedicionesForm.getIniciativaId());
				}
				else if (!indicadorBloqueado && inciativaId != null && inciativaId.longValue() != 0L)
				{
					chequearBloqueo = false;
					if (iniciativa != null && iniciativa.getIniciativaId() != null && iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()) != null && indicador.getIndicadorId().longValue() == iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).longValue())
						indicadorBloqueado = protegerMediciones(inciativaId);
				}
				
				// Chequear si el indicador pertenece a una iniciativa
				if (!indicadorBloqueado && chequearBloqueo)
				{
					if (iniciativa != null && iniciativa.getIniciativaId() != null && iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()) != null && indicador.getIndicadorId().longValue() == iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()).longValue())
						indicadorBloqueado = true;
					else if (iniciativa != null)
						indicadorBloqueado = iniciativa.getEstatus().getBloquearIndicadores();
				}

				// Chequear si el indicador pertenece a una actividad
				actividad = strategosPryActividadesService.getActividadByIndicador(indicador.getIndicadorId());
				if (!indicadorBloqueado && chequearBloqueo && actividad != null && actividad.getActividadId() != null)
					indicadorBloqueado = true;
				
				if (!indicadorBloqueado) 
					indicador.setEstaBloqueado(new Boolean(false));
				else 
					indicador.setEstaBloqueado(new Boolean(true));
				
				for (int iSerie = 0; iSerie < serieId.length; iSerie++)
				{
					serieIndicadorLoad = (SerieIndicador)strategosMedicionesService.load(SerieIndicador.class, new SerieIndicadorPK(new Long(serieId[iSerie]), indicador.getIndicadorId()));

					serieBloqueda = false;
					if (serieIndicadorLoad != null) 
					{
						indicadores = indicadores + ";" + serieIndicadorLoad.getPk().getSerieId().toString();
        	  
						// Chequear si el indicador tiene responsable asociado
						if (!indicadorBloqueado && editarMediciones)
						{
							if (new com.visiongc.app.strategos.web.struts.responsables.actions.SeleccionarResponsablesAction().hayResposabilidad(indicador, serieIndicadorLoad.getPk().getSerieId().longValue() == SerieTiempo.getSerieReal().getSerieId().longValue() ? TipoResponsabilidad.getTipoResponsableCargarEjecutado() : TipoResponsabilidad.getTipoResponsableCargarMeta(), getUsuarioConectado(request)))
							{
								serieBloqueda = !new com.visiongc.app.strategos.web.struts.responsables.actions.SeleccionarResponsablesAction().usuarioEsResponsable(indicador, serieIndicadorLoad.getPk().getSerieId().longValue() == SerieTiempo.getSerieReal().getSerieId().longValue() ? TipoResponsabilidad.getTipoResponsableCargarEjecutado() : TipoResponsabilidad.getTipoResponsableCargarMeta(), getUsuarioConectado(request));
								if (serieBloqueda && !mensajeResponsableAgregado)
								{
									mensajeResponsableAgregado = true;
									if (serieIndicadorLoad.getPk().getSerieId().longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
										messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarmediciones.mensaje.indicadores.noresponsable"));
									else
										messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarmetas.mensaje.indicadores.noresponsable"));
						    		saveMessages(request, messages);
								}
							}
						}
						
						Long tipo = request.getParameter("tipo") != null && !request.getParameter("tipo").equals("") ? new Long(request.getParameter("tipo")) : 0L;
						if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue() && editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD && new Long(serieId[iSerie]).longValue() == SerieTiempo.getSerieProgramado().getSerieId().longValue())
							serieBloqueda = true;
						else if (editarMedicionesForm.getSourceScreen().byteValue() != TipoSource.SOURCE_ACTIVIDAD)
						{
							if (new Long(serieId[iSerie]).longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
								serieBloqueda = !getPermisologiaUsuario(request).tienePermiso("INDICADOR_MEDICION_CARGAR_REAL");
							else if (new Long(serieId[iSerie]).longValue() == SerieTiempo.getSerieProgramado().getSerieId().longValue())
								serieBloqueda = !getPermisologiaUsuario(request).tienePermiso("INDICADOR_MEDICION_CARGAR_PROG");
							else if (new Long(serieId[iSerie]).longValue() == SerieTiempo.getSerieMinimo().getSerieId().longValue())
								serieBloqueda = !getPermisologiaUsuario(request).tienePermiso("INDICADOR_MEDICION_CARGAR_MIN");
							else if (new Long(serieId[iSerie]).longValue() == SerieTiempo.getSerieMaximo().getSerieId().longValue())
								serieBloqueda = !getPermisologiaUsuario(request).tienePermiso("INDICADOR_MEDICION_CARGAR_MAX");
						}
							
						List<?> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), serieIndicadorLoad.getPk().getSerieId(), new Integer(anoDesde), new Integer(anoHasta), new Integer(periodoDesde), new Integer(periodoHasta));
						List<Medicion> medicionesCompletas = new ArrayList<Medicion>();
						int periodoActual = periodoDesde;
						int anoActual = anoDesde;
						boolean medicionProtegidaPorFecha = false;
						medicionProtegida = false;
						int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia, anoActual);

						bloqueadoPorFecha = serieIndicadorLoad.getFechaBloqueo() != null;
						if (!medicionProtegidaPorFecha && serieBloqueda)
							medicionProtegidaPorFecha = serieBloqueda;

						for (Iterator<?> iter = mediciones.iterator(); iter.hasNext(); ) 
						{
							Medicion proxMedicion = (Medicion)iter.next();
							while (anoActual < proxMedicion.getMedicionId().getAno().intValue()) 
							{
								medicionProtegida = false;
								medicionProtegidaPorFecha = false;
								if (bloqueadoPorFecha) 
									medicionProtegidaPorFecha = PeriodoUtil.compareFechaToPeriodo(serieIndicadorLoad.getFechaBloqueo(), periodoActual, anoActual, frecuencia) >= 0;
								
								if (indicadorBloqueado) 
									medicionProtegida = true;
								else if (medicionProtegidaPorFecha) 
									medicionProtegida = true;
								else if (!editarMediciones)
									medicionProtegida = true;
								else if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD && indicador.getActividadId() != null)
									medicionProtegida =  bloquearMedicion(actividad, indicador.getFrecuencia(), anoActual, periodoActual, tipo);
								
								medicionesCompletas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(anoActual), new Integer(periodoActual), serieIndicadorLoad.getPk().getSerieId()), null, new Boolean(medicionProtegida)));
								periodoActual++;
								if (periodoActual > numeroMaximoPeriodos) 
								{
									anoActual++;
									numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia, anoActual);
									periodoActual = 1;
								}
							}
            	  
							while ((periodoActual < proxMedicion.getMedicionId().getPeriodo().intValue()) && (anoActual == proxMedicion.getMedicionId().getAno().intValue())) 
							{
								medicionProtegida = false;
								medicionProtegidaPorFecha = false;
								if (bloqueadoPorFecha) 
									medicionProtegidaPorFecha = PeriodoUtil.compareFechaToPeriodo(serieIndicadorLoad.getFechaBloqueo(), periodoActual, anoActual, frecuencia) >= 0;
								
								if (indicadorBloqueado) 
									medicionProtegida = true;
								else if (medicionProtegidaPorFecha) 
									medicionProtegida = true;
								else if (!editarMediciones)
									medicionProtegida = true;
								else if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD && indicador.getActividadId() != null)
									medicionProtegida =  bloquearMedicion(actividad, indicador.getFrecuencia(), anoActual, periodoActual, tipo);
								
								medicionesCompletas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(anoActual), new Integer(periodoActual), serieIndicadorLoad.getPk().getSerieId()), null, new Boolean(medicionProtegida)));
								periodoActual++;
								if (periodoActual > numeroMaximoPeriodos) 
								{
									anoActual++;
									numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia, anoActual);
									periodoActual = 1;
								}
							}
              
							medicionProtegidaPorFecha = false;
							if (bloqueadoPorFecha) 
								medicionProtegidaPorFecha = PeriodoUtil.compareFechaToPeriodo(serieIndicadorLoad.getFechaBloqueo(), periodoActual, anoActual, frecuencia) >= 0;
							if ((!proxMedicion.getProtegido().booleanValue()) && ((medicionProtegidaPorFecha) || (indicadorBloqueado))) 
								proxMedicion.setProtegido(new Boolean(true));

							if (proxMedicion.getValor() != null)
							{
								proxMedicion.setValor(new Double(VgcFormatter.parsearNumeroFormateado(proxMedicion.getValor().toString())));
								proxMedicion.setValorString(decimalformat.format(proxMedicion.getValor()));
							}
							medicionesCompletas.add(proxMedicion);
							periodoActual++;
							if (periodoActual > numeroMaximoPeriodos) 
							{
								anoActual++;
								numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia, anoActual);
								periodoActual = 1;
							}
						}

						while (anoActual < anoHasta) 
						{
							while (periodoActual <= numeroMaximoPeriodos) 
							{
								medicionProtegida = false;
								medicionProtegidaPorFecha = false;
								if (bloqueadoPorFecha) 
									medicionProtegidaPorFecha = PeriodoUtil.compareFechaToPeriodo(serieIndicadorLoad.getFechaBloqueo(), periodoActual, anoActual, frecuencia) >= 0;

								if (indicadorBloqueado) 
									medicionProtegida = true;
								else if (medicionProtegidaPorFecha) 
									medicionProtegida = true;
								else if (!editarMediciones)
									medicionProtegida = true;
								else if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD && indicador.getActividadId() != null)
									medicionProtegida =  bloquearMedicion(actividad, indicador.getFrecuencia(), anoActual, periodoActual, tipo);
                
								medicionesCompletas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(anoActual), new Integer(periodoActual), serieIndicadorLoad.getPk().getSerieId()), null, new Boolean(medicionProtegida)));
								periodoActual++;
							}
							anoActual++;
							numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia, anoActual);
							periodoActual = 1;
						}

						while ((anoActual == anoHasta) && (periodoActual <= periodoHasta)) 
						{
							medicionProtegida = false;
							medicionProtegidaPorFecha = false;
							if (bloqueadoPorFecha) 
								medicionProtegidaPorFecha = PeriodoUtil.compareFechaToPeriodo(serieIndicadorLoad.getFechaBloqueo(), periodoActual, anoActual, frecuencia) >= 0;
								
							if (indicadorBloqueado) 
								medicionProtegida = true;
							else if (medicionProtegidaPorFecha) 
								medicionProtegida = true;
							else if (!editarMediciones)
								medicionProtegida = true;
							else if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD && indicador.getActividadId() != null)
								medicionProtegida =  bloquearMedicion(actividad, indicador.getFrecuencia(), anoActual, periodoActual, tipo);

							medicionesCompletas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(anoActual), new Integer(periodoActual), serieIndicadorLoad.getPk().getSerieId()), null, new Boolean(medicionProtegida)));
							periodoActual++;
							if (periodoActual > numeroMaximoPeriodos) 
							{
								anoActual++;
								numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(frecuencia, anoActual);
								periodoActual = 1;
							}	
						}
						if ((indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaCualitativoNominal().byteValue()) || (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaCualitativoOrdinal().byteValue())) 
							indicador.getEscalaCualitativa();

						if (editarMedicionesForm.getSourceScreen() == TipoSource.SOURCE_ACTIVIDAD && indicador.getActividadId() != null)
						{
							indicador.setIsPocentaje(true);
					    	if (unidad != null && indicador.getUnidadId().longValue() != unidad.getUnidadId().longValue())
					    		indicador.setIsPocentaje(false);
					    	if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
					    	{
						    	if (actividad.getComienzoReal() != null)
						    		indicador.setFechaInicial(VgcFormatter.formatearFecha(actividad.getComienzoReal(), "formato.fecha.corta"));
						    	else if (actividad.getComienzoPlan() != null)
						    		indicador.setFechaInicial(VgcFormatter.formatearFecha(actividad.getComienzoPlan(), "formato.fecha.corta"));
						    	if (actividad.getFinReal() != null)
						    		indicador.setFechaFinal(VgcFormatter.formatearFecha(actividad.getFinReal(), "formato.fecha.corta"));
						    	else if (actividad.getFinPlan() != null)
						    		indicador.setFechaFinal(VgcFormatter.formatearFecha(actividad.getFinPlan(), "formato.fecha.corta"));
					    	}
						}
						
						Set<Medicion> medicionesAux = new LinkedHashSet<Medicion>();
						medicionesAux.addAll(medicionesCompletas);

						SerieIndicador serieIndicador = new SerieIndicador(serieIndicadorLoad.getPk(), serieIndicadorLoad.getNaturaleza(), serieIndicadorLoad.getFechaBloqueo(), serieIndicadorLoad.getSerieTiempo(), indicador, serieIndicadorLoad.getFormulas(), medicionesAux, serieIndicadorLoad.getIndicadoresCeldas(), serieIndicadorLoad.getNombre());
						seriesIndicadores.add(serieIndicador);
						totalMediciones = medicionesCompletas.size();
					}
				}
			}

			Double anchoMatriz = 0D;
			Double tamanoPeriodo = 0D;
			for (Iterator<?> iter = editarMedicionesForm.getColumnas().iterator(); iter.hasNext(); ) 
			{
				Columna columna = (Columna)iter.next();
				if (columna.getTamano() != null)
				{
					if (!columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
						anchoMatriz = anchoMatriz + Double.parseDouble(columna.getTamano());
					else if (columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
						tamanoPeriodo = Double.parseDouble(columna.getTamano());
				}
			}
			
			if (tamanoPeriodo != 0)
				anchoMatriz += tamanoPeriodo * totalMediciones;
			editarMedicionesForm.setAnchoMatriz(Double.toString(anchoMatriz) + "px");
			
			strategosPryActividadesService.close();
			strategosIniciativasService.close();
			editarMedicionesForm.setIndicadoresSeries(indicadores);

			request.setAttribute("seriesIndicadores", seriesIndicadores);
			if (funcion.equals("Validar") && editarMedicionesForm.getPerspectivaId() != null)
			{
    			Boolean hayMetas = true;
	    		Perspectiva perspectiva = (Perspectiva) strategosIndicadoresService.load(Perspectiva.class, editarMedicionesForm.getPerspectivaId());
	    		if (perspectiva != null)
	    		{
	    			StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();
	    			for (Iterator<?> iterInd = editarMedicionesForm.getIndicadores().iterator(); iterInd.hasNext(); )
	    			{
	    				Indicador indicador = (Indicador)iterInd.next();
	    				List<MetaAnualParciales> metas = strategosMetasService.getMetasAnualesParciales(indicador.getIndicadorId(), perspectiva.getPlanId(), indicador.getFrecuencia(), Integer.parseInt(editarMedicionesForm.getAnoDesde()), Integer.parseInt(editarMedicionesForm.getAnoHasta()), false);
	    		    	for (Iterator<MetaAnualParciales> iter = metas.iterator(); iter.hasNext(); )
	    				{
	    					MetaAnualParciales metaAnualParciales = (MetaAnualParciales)iter.next();
	    					if (metaAnualParciales.getMetaAnual().getValor() == null)
		    				{
		    					hayMetas = false;
		    					break;
		    				}
	    				}
	    			}
	    			strategosMetasService.close();
	    		}
	    		
	    		if (hayMetas)
	    			editarMedicionesForm.setStatus(EditarStatus.getEditarStatusSuccess());
	    		else
	    			editarMedicionesForm.setStatus(EditarStatus.getEditarStatusNoMeta());
			}
			else
				editarMedicionesForm.setStatus(EditarStatus.getEditarStatusSuccess());
			if (editarMedicionesForm.getIndicadores().size() == 1)
				editarMedicionesForm.setBloqueado(indicadorBloqueado);
		}
		else 
		{
    		editarMedicionesForm.setIndicadores(indicadoresSeleccionados);
    		forward = "configurarEdicionMediciones";
    		editarMedicionesForm.setStatus(EditarStatus.getEditarStatusNoSuccess());
    	}

    	strategosIndicadoresService.close();
    	strategosMedicionesService.close();

    	if (!funcion.equals("Validar"))
    	{
    		frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
    		configuracionUsuario = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Configuracion.Medicion.Editar.Parametros", "EDITARMEDICIONES");
    		if (configuracionUsuario == null)
    		{
    			configuracionUsuario = new ConfiguracionUsuario(); 
    			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
    			pk.setConfiguracionBase("Strategos.Configuracion.Medicion.Editar.Parametros");
    			pk.setObjeto("EDITARMEDICIONES");
    			pk.setUsuarioId(this.getUsuarioConectado(request).getUsuarioId());

    			configuracionUsuario.setPk(pk);
    		}
    		configuracionUsuario.setData(editarMedicionesForm.getXml(serieId));
    		
    		frameworkService.saveConfiguracionUsuario(configuracionUsuario, this.getUsuarioConectado(request));
    		frameworkService.close();
    	}
    	
    	if (funcion.equals("Validar"))
    		forward = "configurarEdicionMediciones";
    		
		return mapping.findForward(forward);
	}

	private void setIndicadores(EditarMedicionesForm editarMedicionesForm, StrategosIndicadoresService strategosIndicadoresService)
	{
		Map<String, String> filtros = new HashMap<String, String>();

		if (editarMedicionesForm.getPerspectivaId() != null)
			filtros.put("perspectivaId", editarMedicionesForm.getPerspectivaId().toString());
		else if (editarMedicionesForm.getClaseId() != null) 
			filtros.put("claseId", editarMedicionesForm.getClaseId().toString());
    
		filtros.put("frecuencia", editarMedicionesForm.getFrecuencia().toString());
		
		PaginaLista paginaIndicadores = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "asc", false, filtros, null, null, false);
		
		editarMedicionesForm.setIndicadores(paginaIndicadores.getLista());
	}
  
	private Boolean protegerActividades(PryActividad actividad)
	{
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		Boolean protegerIndicador = false;
	  
		Map<String, String> filtros = new HashMap<String, String>();
		
		String atributoOrden = "fila";
		String tipoOrden = "ASC";
		int pagina = 1;

		filtros = new HashMap<String, String>();
		filtros.put("padreId", actividad.getActividadId().toString());
			
		PaginaLista paginaActividadesHijas = strategosPryActividadesService.getActividades(pagina, 30, atributoOrden, tipoOrden, true, filtros);
			
		if (paginaActividadesHijas.getLista().size() > 0)
			protegerIndicador = true;
		else if (actividad.getObjetoAsociadoId() != null)
			protegerIndicador = true;
	  
		strategosPryActividadesService.close();
	  
		return protegerIndicador;
	}
	
	private Boolean protegerMediciones(Long iniciativaId)
	{
		Boolean protegerIndicador = false;
		
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		Iniciativa iniciativa = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, new Long(iniciativaId));

		if (iniciativa != null && iniciativa.getProyectoId() != null)
		{
			Map<String, String> filtros = new HashMap<String, String>();
			filtros.put("proyectoId", iniciativa.getProyectoId().toString());
			
			String atributoOrden = "fila";
			String tipoOrden = "ASC";
			int pagina = 1;
			PaginaLista paginaActividades = strategosPryActividadesService.getActividades(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		  
			if (paginaActividades.getLista().size() > 0)
				protegerIndicador = true;
		}
	  
		strategosPryActividadesService.close();
		
		if (!protegerIndicador)
			protegerIndicador = iniciativa.getEstatus().getBloquearMedicion();
		
		return protegerIndicador;
	}
	
	private String[] setup(EditarMedicionesForm editarMedicionesForm, HttpServletRequest request)
	{
		editarMedicionesForm.setSoloSeleccionados(true);
		editarMedicionesForm.setDesdePlanificacion(true);
		editarMedicionesForm.setIniciativaId(new Long(request.getParameter("iniciativaId")));
		editarMedicionesForm.setOrganizacionId(new Long(request.getParameter("organizacionId")));
	
		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService(strategosPryActividadesService);
		Iniciativa iniciativa = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, new Long(editarMedicionesForm.getIniciativaId()));
		editarMedicionesForm.setIniciativa(iniciativa.getNombre());
		editarMedicionesForm.setOrganizacion(iniciativa.getOrganizacion().getNombre());
		Long tipo = new Long(request.getParameter("tipo"));

		Date fechaIni = null;
		Date fechaFin = null;
		if (request.getQueryString().indexOf("actividadId=") > -1)
		{
			String strActividadId = request.getParameter("actividadId");
			if (((strActividadId != null ? 1 : 0) & (strActividadId.equals("") ? 0 : 1)) != 0) 
			{
				String[] ids = strActividadId.split(",");
				editarMedicionesForm.setIndicadores(new ArrayList<Indicador>());
				for (int i = 0; i < ids.length; i++) 
				{
					PryActividad actividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(ids[i]));
					if (actividad != null)
					{
						if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
						{
							if (fechaIni == null)
								fechaIni = actividad.getComienzoReal() != null ? actividad.getComienzoReal() : actividad.getComienzoPlan();
							if (fechaFin == null)
								fechaFin = actividad.getFinReal() != null ? actividad.getFinReal() : actividad.getFinPlan();
							
							if (actividad.getComienzoReal() != null && actividad.getComienzoReal().before(fechaIni))
								fechaIni = actividad.getComienzoReal();
							Date fecha = actividad.getFinReal() != null ? actividad.getFinReal() : actividad.getFinPlan();
							if (fecha != null && fecha.after(fechaFin))
								fechaFin = fecha;
						}
						else
						{
							if (fechaIni == null)
								fechaIni = actividad.getComienzoPlan();
							if (fechaFin == null)
								fechaFin = actividad.getFinPlan() != null ? actividad.getFinPlan() : actividad.getComienzoPlan();
							
							if (actividad.getComienzoPlan() != null && actividad.getComienzoPlan().before(fechaIni))
								fechaIni = actividad.getComienzoPlan();
							Date fecha = actividad.getFinPlan() != null ? actividad.getFinPlan() : actividad.getComienzoPlan();
							if (fecha != null && fecha.after(fechaFin))
								fechaFin = fecha;
						}

						Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(actividad.getIndicadorId()));
						editarMedicionesForm.setFrecuencia(indicador.getFrecuencia());
						indicador.setActividadId(actividad.getActividadId());
						editarMedicionesForm.getIndicadores().add(indicador);
					}
				}
			}
		}
		else
		{
			for (Iterator<?> iterInd = editarMedicionesForm.getIndicadores().iterator(); iterInd.hasNext(); )
			{
				Indicador indicador = (Indicador)iterInd.next();
				if (indicador.getActividadId() != null)
				{
					PryActividad actividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, indicador.getActividadId());
					if (actividad != null)
					{
						if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
						{
							if (fechaIni == null)
								fechaIni = actividad.getComienzoReal() != null ? actividad.getComienzoReal() : actividad.getComienzoPlan();
							if (fechaFin == null)
								fechaFin = actividad.getFinReal() != null ? actividad.getFinReal() : actividad.getFinPlan();
							
							if (actividad.getComienzoReal() != null && actividad.getComienzoReal().before(fechaIni))
								fechaIni = actividad.getComienzoReal();
							Date fecha = actividad.getFinReal() != null ? actividad.getFinReal() : actividad.getFinPlan();
							if (fecha != null && fecha.after(fechaFin))
								fechaFin = fecha;
						}
						else
						{
							if (fechaIni == null)
								fechaIni = actividad.getComienzoPlan();
							if (fechaFin == null)
								fechaFin = actividad.getFinPlan() != null ? actividad.getFinPlan() : actividad.getComienzoPlan();
							
							if (actividad.getComienzoPlan() != null && actividad.getComienzoPlan().before(fechaIni))
								fechaIni = actividad.getComienzoPlan();
							Date fecha = actividad.getFinPlan() != null ? actividad.getFinPlan() : actividad.getComienzoPlan();
							if (fecha != null && fecha.after(fechaFin))
								fechaFin = fecha;
						}
					}
				}
			}
		}
		
		List<SerieTiempo> series = new ArrayList<SerieTiempo>();
		if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
		{
			editarMedicionesForm.setTipoCargaDesdePlanificacion(TipoCargaActividad.getTipoCargaActividadReal());
			series.add(SerieTiempo.getSerieReal());
			series.add(SerieTiempo.getSerieProgramado());
		}
		else if (tipo.longValue() == SerieTiempo.getSerieProgramado().getSerieId().longValue())
		{
			editarMedicionesForm.setTipoCargaDesdePlanificacion(TipoCargaActividad.getTipoCargaActividadProgramado());
			series.add(SerieTiempo.getSerieProgramado());
		}
		
		StrategosSeriesTiempoService strategosSeriesTiempoService = StrategosServiceFactory.getInstance().openStrategosSeriesTiempoService(strategosIndicadoresService);
		PaginaLista paginaSeries = strategosSeriesTiempoService.getSeriesTiempoByList(0, 0, "serieId", "asc", true, null, series, getUsuarioConectado(request));
		strategosSeriesTiempoService.close();
		
		editarMedicionesForm.setPaginaSeriesTiempo(paginaSeries);
		
		Calendar calFechaDesde = Calendar.getInstance();
		calFechaDesde.setTime(fechaIni);
		
		Calendar calFechaHasta = Calendar.getInstance();
		calFechaHasta.setTime(fechaFin);

		if (tipo.longValue() == SerieTiempo.getSerieProgramado().getSerieId().longValue())
		{
			editarMedicionesForm.setFechaDesde(VgcFormatter.formatearFecha(calFechaDesde.getTime(), "formato.fecha.corta"));
			editarMedicionesForm.setAnosD(PeriodoUtil.getListaNumeros(new Integer(editarMedicionesForm.getFechaDesde().split("/")[2]), new Integer(editarMedicionesForm.getFechaDesde().split("/")[2])));
			editarMedicionesForm.setAnoDesde(new Integer(calFechaDesde.get(1)).toString());
			editarMedicionesForm.setPeriodoDesde(PeriodoUtil.getPeriodoDeFecha(calFechaDesde, editarMedicionesForm.getFrecuencia()));
			editarMedicionesForm.setPeriodosD(PeriodoUtil.getListaNumeros(editarMedicionesForm.getPeriodoDesde(), editarMedicionesForm.getPeriodoDesde()));
			
			editarMedicionesForm.setFechaHasta(VgcFormatter.formatearFecha(calFechaHasta.getTime(), "formato.fecha.corta"));
			editarMedicionesForm.setAnosH(PeriodoUtil.getListaNumeros(new Integer(editarMedicionesForm.getFechaHasta().split("/")[2]), new Integer(editarMedicionesForm.getFechaHasta().split("/")[2])));
			editarMedicionesForm.setAnoHasta(new Integer(calFechaHasta.get(1)).toString());
			editarMedicionesForm.setPeriodoHasta(PeriodoUtil.getPeriodoDeFecha(calFechaHasta, editarMedicionesForm.getFrecuencia()));
			editarMedicionesForm.setPeriodosH(PeriodoUtil.getListaNumeros(editarMedicionesForm.getPeriodoHasta(), editarMedicionesForm.getPeriodoHasta()));

			editarMedicionesForm.setAnos(PeriodoUtil.getListaNumeros(new Integer(editarMedicionesForm.getFechaDesde().split("/")[2]), new Integer(editarMedicionesForm.getFechaHasta().split("/")[2])));
			editarMedicionesForm.setPeriodos(PeriodoUtil.getListaNumeros(editarMedicionesForm.getPeriodoDesde(), editarMedicionesForm.getPeriodoHasta()));
		}
		
		int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(editarMedicionesForm.getFrecuencia().byteValue(), calFechaHasta.get(1));
		editarMedicionesForm.setNumeroMaximoPeriodos(numeroMaximoPeriodos);
		
		strategosIndicadoresService.close();
		strategosPryActividadesService.close();
		
		String[] serie = null;
		if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
		{
			serie = new String[2];
			serie[0] = SerieTiempo.getSerieReal().getSerieId().toString();
			serie[1] = SerieTiempo.getSerieProgramado().getSerieId().toString();
		}
		else
		{
			serie = new String[1];
			serie[0] = tipo.toString();
		}
		
		return serie;
	}
	
	private Boolean bloquearMedicion(PryActividad actividad, Byte frecuencia, Integer ano, Integer periodo, Long tipo)
	{
		Boolean medicionProtegida = false;
		
		Date fecha = actividad.getComienzoPlan() != null ? actividad.getComienzoPlan() : (actividad.getComienzoReal() != null ? actividad.getComienzoReal() : actividad.getComienzoPlan());
		if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
		{
			if (fecha != null && actividad.getComienzoReal() != null && fecha.before(actividad.getComienzoReal()))
				fecha = actividad.getComienzoReal();
		}
		
		Calendar fechaCalendar = Calendar.getInstance();
		fechaCalendar.setTime(fecha);
		Integer anoFecha = fechaCalendar.get(1);
	    Integer periodoFecha = PeriodoUtil.getPeriodoDeFecha(fechaCalendar, frecuencia);

		String periodoIniStr = periodo.toString().length() == 1 ? ("00" + periodo.toString()) : (periodo.toString().length() == 2 ? ("0" + periodo.toString()) : periodo.toString());
		String perIni = ano.toString() + periodoIniStr;
		
		String periodoFinStr = periodoFecha.toString().length() == 1 ? ("00" + periodoFecha.toString()) : (periodoFecha.toString().length() == 2 ? ("0" + periodoFecha.toString()) : periodoFecha.toString());  
		String perFin = anoFecha.toString() + periodoFinStr;  
		
		if (Integer.parseInt(perIni) < Integer.parseInt(perFin))
			medicionProtegida = true;
	    
        if (!medicionProtegida && tipo.longValue() == SerieTiempo.getSerieProgramado().getSerieId().longValue())
        {
    		fecha = actividad.getFinPlan() != null ? actividad.getFinPlan() : (actividad.getFinReal() != null ? actividad.getFinReal() : actividad.getFinPlan());
    		if (tipo.longValue() == SerieTiempo.getSerieReal().getSerieId().longValue())
    		{
    			if (fecha != null && actividad.getFinReal() != null && fecha.after(actividad.getFinReal()))
    				fecha = actividad.getFinReal();
    		}
    		
    		fechaCalendar = Calendar.getInstance();
    		fechaCalendar.setTime(fecha);
    		anoFecha = fechaCalendar.get(1);
    	    periodoFecha = PeriodoUtil.getPeriodoDeFecha(fechaCalendar, frecuencia);
    		
			periodoIniStr = periodo.toString().length() == 1 ? ("00" + periodo.toString()) : (periodo.toString().length() == 2 ? ("0" + periodo.toString()) : periodo.toString());
			perIni = ano.toString() + periodoIniStr;
			
			periodoFinStr = periodoFecha.toString().length() == 1 ? ("00" + periodoFecha.toString()) : (periodoFecha.toString().length() == 2 ? ("0" + periodoFecha.toString()) : periodoFecha.toString());  
			perFin = anoFecha.toString() + periodoFinStr;  
			
			if (Integer.parseInt(perIni) > Integer.parseInt(perFin))
				medicionProtegida = true;
        }
        
        return medicionProtegida; 
	}
}