/**
 * 
 */
package com.visiongc.app.strategos.web.struts.mediciones.actions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.EditarMedicionesForm.TipoSource;
import com.visiongc.app.strategos.web.struts.util.Columna;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class ImprimirMedicionesPDFAction  extends VgcReporteBasicoAction
{
	private String titulo = null;
	private MessageResources mensajes;
	
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception
	{
		this.mensajes = mensajes;
		titulo = mensajes.getMessage("jsp.editarmediciones.ficha.organizacion") + ": " + request.getSession().getAttribute("organizacionNombre").toString();
		return titulo;
	}

	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
	    Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

	    EditarMedicionesForm editarMedicionesForm = new EditarMedicionesForm();

		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
		ConfiguracionUsuario configuracionUsuario = frameworkService.getConfiguracionUsuario(usuario.getUsuarioId(), "Strategos.Forma.Configuracion.Columnas", "visorLista.Medicion");
		if (configuracionUsuario == null)
		{
			configuracionUsuario = new ConfiguracionUsuario(); 
			ConfiguracionUsuarioPK pk = new ConfiguracionUsuarioPK();
			pk.setConfiguracionBase("Strategos.Forma.Configuracion.Columnas");
			pk.setObjeto("visorLista.Medicion");
			pk.setUsuarioId(usuario.getUsuarioId());
			configuracionUsuario.setPk(pk);
			configuracionUsuario.setData(editarMedicionesForm.setColumnasDefault());
			frameworkService.saveConfiguracionUsuario(configuracionUsuario, usuario);
		}
		frameworkService.close();
		editarMedicionesForm.setColumnas(configuracionUsuario.getData());
		
		List<SerieIndicador> seriesIndicadores = cargarDatos(editarMedicionesForm, request);
		
		construirReporte(seriesIndicadores, editarMedicionesForm, documento, request);
	}

	public List<SerieIndicador> cargarDatos(EditarMedicionesForm editarMedicionesForm, HttpServletRequest request) throws Exception
	{
	    Locale currentLocale = new Locale("en","US");
	    NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
	    DecimalFormat decimalformat = (DecimalFormat)numberFormatter;
	    decimalformat.applyPattern("#,##0.00");

		List<SerieIndicador> seriesIndicadores = new ArrayList<SerieIndicador>();
	    String[] indicadores = ((String) request.getParameter("indicadores")).split("!;!");
	    String anoD = request.getParameter("anoDesde");
	    String anoH = request.getParameter("anoHasta");
	    String periodoD = request.getParameter("periodoDesde");
	    String periodoH = request.getParameter("periodoHasta");
	    List<Long> series = new ArrayList<Long>();
	    
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

	    editarMedicionesForm.setIndicadores(new ArrayList<Indicador>());
		for (int iIndicador = 0; iIndicador < indicadores.length; iIndicador++)
		{
			String[] valores = indicadores[iIndicador].split("!:!");
			Indicador indicador = (Indicador) strategosIndicadoresService.load(Indicador.class, new Long(valores[0]));
			if (!editarMedicionesForm.getIndicadores().contains(indicador))
				editarMedicionesForm.getIndicadores().add(indicador);
			Long serieId = new Long(valores[1]);
			if (!series.contains(serieId))
				series.add(serieId);
		}
		
		editarMedicionesForm.setAnoDesde(anoD);
		editarMedicionesForm.setPeriodoDesde(new Integer(periodoD));
		editarMedicionesForm.setAnoHasta(anoH);
		editarMedicionesForm.setPeriodoHasta(new Integer(periodoH));
		
		if ((editarMedicionesForm.getIndicadores() != null) && (editarMedicionesForm.getIndicadores().size() > 0))
		{
			int anoDesde = Integer.parseInt(editarMedicionesForm.getAnoDesde());
			int anoHasta = Integer.parseInt(editarMedicionesForm.getAnoHasta());
			int periodoDesde = editarMedicionesForm.getPeriodoDesde().intValue();
			int periodoHasta = editarMedicionesForm.getPeriodoHasta().intValue();
			
			SerieIndicador serieIndicador = null;
			for (Iterator<?> iterInd = editarMedicionesForm.getIndicadores().iterator(); iterInd.hasNext(); )
			{
				Indicador indicador = (Indicador)iterInd.next();
				
				if ((indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoNominal())) || (indicador.getNaturaleza().equals(Naturaleza.getNaturalezaCualitativoOrdinal())))
				{
					indicador = (Indicador) strategosIndicadoresService.load(Indicador.class, indicador.getIndicadorId());
					indicador.getEscalaCualitativa().size();
				}

				for (Iterator<?> iterSerieId = series.iterator(); iterSerieId.hasNext(); )
				{
					Long serieId = (Long)iterSerieId.next();
					
					serieIndicador = (SerieIndicador)strategosMedicionesService.load(SerieIndicador.class, new SerieIndicadorPK(serieId, indicador.getIndicadorId()));

					if (serieIndicador != null) 
					{
						List<?> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), serieIndicador.getPk().getSerieId(), new Integer(anoDesde), new Integer(anoHasta), new Integer(periodoDesde), new Integer(periodoHasta));
						List<Medicion> medicionesCompletas = new ArrayList<Medicion>();
						int periodoActual = periodoDesde;
						int anoActual = anoDesde;
						int numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), anoActual);

						for (Iterator<?> iter = mediciones.iterator(); iter.hasNext(); ) 
						{
							Medicion proxMedicion = (Medicion)iter.next();
							while (anoActual < proxMedicion.getMedicionId().getAno().intValue()) 
							{
								medicionesCompletas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(anoActual), new Integer(periodoActual), serieIndicador.getPk().getSerieId()), null, false));
								periodoActual++;
								if (periodoActual > numeroMaximoPeriodos) 
								{
									anoActual++;
									numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), anoActual);
									periodoActual = 1;
								}
							}
            	  
							while ((periodoActual < proxMedicion.getMedicionId().getPeriodo().intValue()) && (anoActual == proxMedicion.getMedicionId().getAno().intValue())) 
							{
								medicionesCompletas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(anoActual), new Integer(periodoActual), serieIndicador.getPk().getSerieId()), null, false));
								periodoActual++;
								if (periodoActual > numeroMaximoPeriodos) 
								{
									anoActual++;
									numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), anoActual);
									periodoActual = 1;
								}
							}
              
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
								numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), anoActual);
								periodoActual = 1;
							}
						}

						while (anoActual < anoHasta) 
						{
							while (periodoActual <= numeroMaximoPeriodos) 
							{
								medicionesCompletas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(anoActual), new Integer(periodoActual), serieIndicador.getPk().getSerieId()), null, false));
								periodoActual++;
							}
							anoActual++;
							numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), anoActual);
							periodoActual = 1;
						}

						while ((anoActual == anoHasta) && (periodoActual <= periodoHasta)) 
						{
							medicionesCompletas.add(new Medicion(new MedicionPK(indicador.getIndicadorId(), new Integer(anoActual), new Integer(periodoActual), serieIndicador.getPk().getSerieId()), null, false));
							periodoActual++;
							if (periodoActual > numeroMaximoPeriodos) 
							{
								anoActual++;
								numeroMaximoPeriodos = PeriodoUtil.getNumeroMaximoPeriodosPorFrecuencia(indicador.getFrecuencia(), anoActual);
								periodoActual = 1;
							}	
						}

						Set<Medicion> medicionesAux = new LinkedHashSet<Medicion>();
						medicionesAux.addAll(medicionesCompletas);

						serieIndicador.setIndicador(indicador);
						serieIndicador.setMediciones(medicionesAux);
						
						if ((indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaCualitativoNominal().byteValue()) || (indicador.getNaturaleza().byteValue() == Naturaleza.getNaturalezaCualitativoOrdinal().byteValue())) 
							indicador.getEscalaCualitativa();
						seriesIndicadores.add(serieIndicador);
					}
				}
			}
		}
		
		strategosIndicadoresService.close();
		strategosMedicionesService.close();
		
		return seriesIndicadores;
	}
	
	private void construirReporte(List<SerieIndicador> seriesIndicadores, EditarMedicionesForm editarMedicionesForm, Document documento, HttpServletRequest request) throws Exception
	{
		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    
		Byte source = Byte.parseByte(request.getParameter("source"));
		if (source.byteValue() == TipoSource.SOURCE_CLASE)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_CLASE);
		else if (source.byteValue() == TipoSource.SOURCE_PLAN)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_PLAN);
		else if (source.byteValue() == TipoSource.SOURCE_INICIATIVA)
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_INICIATIVA);
		else
			editarMedicionesForm.setSourceScreen(TipoSource.SOURCE_ACTIVIDAD);
	    
		String subTitulo = null;
		if (source.byteValue() == TipoSource.SOURCE_CLASE)
		{
			if (request.getQueryString().indexOf("claseId=") > -1) 
				editarMedicionesForm.setClaseId(new Long(request.getParameter("claseId")));
			
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			ClaseIndicadores clase = (ClaseIndicadores)strategosIndicadoresService.load(ClaseIndicadores.class, editarMedicionesForm.getClaseId());
			strategosIndicadoresService.close();
			
			if (clase != null) 
			{
				editarMedicionesForm.setClase(clase.getNombre());
				subTitulo = mensajes.getMessage("jsp.editarmediciones.ficha.clase") + ": " + editarMedicionesForm.getClase(); 
			}
		}
				
		if (subTitulo != null)
		{
		    agregarSubTitulo(documento, getConfiguracionPagina(request), subTitulo, true, true, 13.0F);
			documento.add(new Paragraph(" "));
		}
			    
		int numeroColumnas = 0;
		SerieIndicador serie = seriesIndicadores.get(0);
		for (Iterator<?> iter = editarMedicionesForm.getColumnas().iterator(); iter.hasNext(); ) 
		{
			Columna columna = (Columna)iter.next();
			if (!columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
				numeroColumnas++;
			else if (columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
				numeroColumnas = numeroColumnas + serie.getMediciones().size();
		}

	    int[] columnas = new int[numeroColumnas];
	    int f = 0;
	    int i = 0;
	    int divisor = 10;
	    int tamanoPeriodo = 0;
	    String[] columnasTitulo = new String[numeroColumnas];
		for (Iterator<?> iter = editarMedicionesForm.getColumnas().iterator(); iter.hasNext(); ) 
		{
			Columna columna = (Columna)iter.next();
			if (!columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
			{
				columnas[f] = Integer.parseInt(columna.getTamano()) / divisor;
				columnasTitulo[i] = columna.getNombre();
				f++;
				i++;
			}
			else if (columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
			{
				tamanoPeriodo = Integer.parseInt(columna.getTamano()) / divisor;
	    		Indicador indicador = serie.getIndicador();
	    		for (Iterator<?> iterSerie = serie.getMediciones().iterator(); iterSerie.hasNext(); )
	    		{
	    			Medicion medicion = (Medicion)iterSerie.next();
	    			columnasTitulo[i] = PeriodoUtil.convertirPeriodoToTexto(medicion.getMedicionId().getPeriodo(), indicador.getFrecuencia(), medicion.getMedicionId().getAno());
	    			i++;
	    		}
			}
		}
	    
		for (int q = f; q < columnas.length; q++)
	    	columnas[q] = tamanoPeriodo;
		
		if (seriesIndicadores.size() > 0)
		{
			font.setSize(8);
	        font.setColor(255, 255, 255); //Blanco
			tabla = creatTabla(columnas, request);
	        tabla.setColorFondo(67, 67, 67);
	        tabla.setColorBorde(120, 114, 77);
			tabla.encabezadoTabla(font, false, documento, columnas, columnasTitulo, "", "");

	        font.setColor(0, 0, 0); //Negro
	        tabla.setColorFondo(255, 255, 255);
	        tabla.setColorBorde(0, 0, 0);
	        Long indicadorId = 0L;
			for (Iterator<?> iter = seriesIndicadores.iterator(); iter.hasNext(); )
			{
				SerieIndicador fila = (SerieIndicador)iter.next();
				for (Iterator<?> iterColumna = editarMedicionesForm.getColumnas().iterator(); iterColumna.hasNext(); ) 
				{
					Columna columna = (Columna)iterColumna.next();
					if (!columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
					{
						if (indicadorId.longValue() != fila.getIndicador().getIndicadorId().longValue())
						{
							if (columna.getNombre().equals(mensajes.getMessage("jsp.editarmediciones.ficha.indicador")))
								tabla.agregarCelda(fila.getIndicador().getNombre(), font);
							else if (columna.getNombre().equals(mensajes.getMessage("jsp.editarmediciones.ficha.unidad")))
								tabla.agregarCelda(fila.getIndicador().getUnidad() != null ? fila.getIndicador().getUnidad().getNombre() : "", font);
							else if (columna.getNombre().equals(mensajes.getMessage("jsp.editarmediciones.ficha.serie")))
								tabla.agregarCelda(fila.getSerieTiempo().getNombre(), font);
						}
						else if (columna.getNombre().equals(mensajes.getMessage("jsp.editarmediciones.ficha.serie")))
							tabla.agregarCelda(fila.getSerieTiempo().getNombre(), font);
						else
							tabla.agregarCelda("", font);
					}
					else if (columna.getNombre().equals("Periodos") && columna.getMostrar().equals("true"))
					{
						indicadorId = fila.getIndicador().getIndicadorId();
			    		for (Iterator<?> iterMedicion = fila.getMediciones().iterator(); iterMedicion.hasNext(); )
			    		{
			    			Medicion medicion = (Medicion)iterMedicion.next();
			    			tabla.agregarCelda(medicion.getValorString() != null ? medicion.getValorString() : "", font);
			    		}
					}
				}
			}
			
			agregarTabla(documento, tabla);
		}
	}
}
