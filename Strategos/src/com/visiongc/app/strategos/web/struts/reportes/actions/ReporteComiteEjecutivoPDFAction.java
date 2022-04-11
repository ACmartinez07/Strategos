package com.visiongc.app.strategos.web.struts.reportes.actions;

import com.lowagie.text.Document;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosClasesIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteComiteEjecutivoForm;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.VgcFormatter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

/** Clase ReporteComiteEjecutivoPDFAction
 * Contruir reporte Comite Ejecutivo en PDF
 *   
 * @author Kerwin
 */
public class ReporteComiteEjecutivoPDFAction extends VgcReporteBasicoAction
{
	
	int _nivel = 0;							/** Nivel de la clase */
	Integer _minimoNivelActivo = null;		/** Nivel minimo para los totales Activos*/
	Integer _minimoNivelPasivo = null;		/** Nivel minimo para los totales Pasivos*/
	Integer _minimoNivelOrganizacion = null;		/** Nivel minimo para los totales Pasivos*/
	
	/** agregarTitulo(HttpServletRequest request, MessageResources mensajes) 
	 * 
	 * Inserta un título en el reporte.
	 * 
	 * @param request			HttpServletRequest
	 * @param mensajes			Recursos
	 * 
	 * @return					El titulo del reporte
	 */
	protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes) throws Exception 
	{
		return mensajes.getMessage("action.reportecomiteejecutivo.titulo");
	}

	/** construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
	 * Contruir el Reporte
	 * 
	 * @param form				Forma
	 * @param request			HttpServletRequest
	 * @param response			HttpServletResponse
	 * @param documento			Documento a mostrar
	 * @param mensajes			Recursos
	 */
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception
	{
		documento.setPageSize(PageSize.LETTER.rotate());
		
		// Guardar en Configuracion
		FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
	    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		
	    Configuracion configuracion = new Configuracion();
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version='1.0' encoding='UTF-8' standalone='no'?><ReporteComiteEjecutivo><Parametros>");
		xml.append("<vista>" + request.getParameter("vista").toString() + "</vista>");
		xml.append("<fecha>" + request.getParameter("fecha").toString() + "</fecha>");
		xml.append("<organizaciones>" + request.getParameter("organizaciones").toString() + "</organizaciones>");
		xml.append("<clases>" + request.getParameter("clases").toString() + "</clases>");
		xml.append("<indicadores>" + request.getParameter("indicadores").toString() + "</indicadores>");
		xml.append("</Parametros></ReporteComiteEjecutivo>");

		configuracion.setParametro("Strategos.Reportes.ComiteEjecutivo.Parametros");
		configuracion.setValor(xml.toString());
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		frameworkService.saveConfiguracion(configuracion, usuario);
		
		Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    MessageResources mensajes = getResources(request);

		agregarSubTitulo(documento, getConfiguracionPagina(request), mensajes.getMessage("action.reportecomiteejecutivo.expresado"), true, true, 12.0F);
	    
	    Integer vista = Integer.parseInt(request.getParameter("vista"));
	    String[] fecha = request.getParameter("fecha").split("/");
	    int diaReporte = Integer.parseInt(fecha[0]);
	    int mesReporte = Integer.parseInt(fecha[1]);
	    int anoReporte = Integer.parseInt(fecha[2]);
	    String[] organizaciones = request.getParameter("organizaciones").split(ReporteComiteEjecutivoForm.SEPARADOR_CAMPOS); 
	    String[] clases = request.getParameter("clases").split(ReporteComiteEjecutivoForm.SEPARADOR_CAMPOS); 
	    String[] indicadores = request.getParameter("indicadores").split(ReporteComiteEjecutivoForm.SEPARADOR_CAMPOS); 
	    List<ClaseIndicadores> lstClasesActivos = new ArrayList<ClaseIndicadores>();
	    List<ClaseIndicadores> lstClasesPasivos = new ArrayList<ClaseIndicadores>();
	    _minimoNivelActivo = null;
	    _minimoNivelPasivo = null;
	    _minimoNivelOrganizacion = null;
	    
	    //1 recorro organizaciones y agrego en una lista
	    for (int i = 0; i < organizaciones.length; i++) 
	    {
	    	OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizaciones[i]));
	    	if (vista == 2)
	    	{
		    	_nivel = 0;
		    	BuscarNivel(organizacionStrategos, strategosOrganizacionesService);
		    	if (_minimoNivelOrganizacion == null)
		    		_minimoNivelOrganizacion = _nivel;
		    	if (_minimoNivelOrganizacion > _nivel)
		    		_minimoNivelOrganizacion = _nivel;
	    	}
	    	
		    if (vista == 1)
		    {
		    	//2 si la vista es producto lleno la lista de clases, determinando si es activa o pasiva
		    	for (int j = 0; j < clases.length; j++) 
		    	{
		    		ClaseIndicadores claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(clases[j]));
		    		Map<String, Object> filtros = new HashMap<String, Object>();
		    	    filtros.put("nombre", claseIndicadores.getNombre());
		    	    filtros.put("organizacionId", organizacionStrategos.getOrganizacionId().toString());
					List<ClaseIndicadores> clase = strategosClasesIndicadoresService.getClases(filtros);
					
					if (clase.size() > 0)
					{
						for (Iterator<?> list = clase.iterator(); list.hasNext(); ) 
						{
							ClaseIndicadores oClase = (ClaseIndicadores)list.next();
							if (oClase.getPadreId() != null && oClase.getNombre().equals(claseIndicadores.getNombre()))
							{
								_nivel = 0;
								Boolean EsActivo = ClaseEsActivo(oClase);
						    	if (EsActivo && _minimoNivelActivo == null)
						    		_minimoNivelActivo = _nivel;
						    	if (!EsActivo && _minimoNivelPasivo == null)
						    		_minimoNivelPasivo = _nivel;
						    	if (EsActivo && _minimoNivelActivo != null && _minimoNivelActivo > _nivel)
						    		_minimoNivelActivo = _nivel;
						    	if (!EsActivo && _minimoNivelPasivo != null && _minimoNivelPasivo > _nivel)
						    		_minimoNivelPasivo = _nivel;
						    	oClase.setNivel(_nivel);
								if (EsActivo)
								{
									if (!lstClasesActivos.contains(oClase))
									{
										lstClasesActivos.add(oClase);
										break;
									}
								}
								else
								{
									if (!lstClasesPasivos.contains(oClase))
									{
										lstClasesPasivos.add(oClase);
										break;
									}
								}
							}
						}
					}
		    	}
		    }
			else if (vista == 2)
			{
				Map<String, Object> filtros = new HashMap<String, Object>();
	    	    filtros.put("organizacionId", organizacionStrategos.getOrganizacionId().toString());
				List<ClaseIndicadores> clasesVista = strategosClasesIndicadoresService.getClases(filtros);
				if (clasesVista.size() > 0)
				{
					for (int h = 0; h < clasesVista.size(); h++)
			    	{
						_nivel = 0;
						Boolean EsActivo = ClaseEsActivo(clasesVista.get(h));
						clasesVista.get(h).setNivel(_nivel);
						if (EsActivo)
						{
							if (!lstClasesActivos.contains(clasesVista.get(h)))
								lstClasesActivos.add(clasesVista.get(h));
						}
						else
						{
							if (!lstClasesPasivos.contains(clasesVista.get(h)))
								lstClasesPasivos.add(clasesVista.get(h));
						}
			    	}
				}
			}	    
	    }
	    
	    if (vista == 2)
		{
	    	int pages = 0;
	    	for (int j = 0; j < indicadores.length; j++) 
	    	{
	    		if (pages > 0)
        		{
        			documento.newPage();
        			documento.add(new Paragraph(" "));
        			documento.add(new Paragraph(" "));
        		}
	    		
	        	Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadores[j]));
	        	if (indicador == null) continue;
	        	boolean haypages = false;
	        	TablaBasicaPDF tabla;
	        	if (lstClasesPasivos.size() > 0)
	        	{
				    Encabezado(font, documento, vista, mensajes, request.getParameter("fecha"), indicador.getNombre(), "");
	
				    //Tabla Pasivos  
				    tabla = GenerarTablaVistaBanca(documento, lstClasesPasivos, organizaciones, indicador, vista, "", "Pasivos (Captaciones)", "Captaciones", diaReporte, mesReporte, anoReporte, request);			    
			    	documento.add(tabla.getTabla());
			    	haypages = true;
	        	}

	        	if (lstClasesActivos.size() > 0)
	        	{
			    	//Tabla Activos
	        		if (haypages)
	        		{
	        			documento.newPage();
	        			documento.add(new Paragraph(" "));
	        			documento.add(new Paragraph(" "));
	        		}
			    	Encabezado(font, documento, vista, mensajes, request.getParameter("fecha"), indicador.getNombre(), "");
				    
			    	tabla = GenerarTablaVistaBanca(documento, lstClasesActivos, organizaciones, indicador, vista, "", "Activos (Cartera de Créditos)", "Cartera de Créditos", diaReporte, mesReporte, anoReporte, request);
			    	documento.add(tabla.getTabla());
			    	haypages = true;
	        	}
	        	
		    	pages = 0;
	    	}
		}
	    else
		{
		    //recorrer organizaciones
	    	int pages = 0;
		    for (int i = 0; i < organizaciones.length; i++) 
		    {
		    	for (int j = 0; j < indicadores.length; j++) 
		    	{
		    		if (pages > 0)
	        		{
	        			documento.newPage();
	        			documento.add(new Paragraph(" "));
	        			documento.add(new Paragraph(" "));
	        		}
		    		
		        	OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizaciones[i]));
					ClaseIndicadores clase = null;
		    		Map<String, Object> filtros = new HashMap<String, Object>();
		    		Indicador indicador = null;
		        	for (int k = 0; k < clases.length; k++)
		        	{
			        	ClaseIndicadores claseIndicadores = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, new Long(clases[k]));
			        	indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(indicadores[j]));
			        	
		        		filtros = new HashMap<String, Object>();
			    	    filtros.put("nombre", claseIndicadores.getNombre());
			    	    filtros.put("organizacionId", organizacionStrategos.getOrganizacionId().toString());
						List<ClaseIndicadores> cl = strategosClasesIndicadoresService.getClases(filtros);
						
						if (cl.size() > 0)
						{
							if (((ClaseIndicadores)cl.get(0)).getPadreId() != null)
								clase = (ClaseIndicadores)cl.get(0);
						}
						
						if (clase != null)
							break;
		        	}
					
		        	Byte mesCierre = organizacionStrategos.getMesCierre() != null ? organizacionStrategos.getMesCierre() : 12;

		        	if (clase != null)
		        	{
		        		filtros = new HashMap<String, Object>();
			    	    filtros.put("nombre", indicador.getNombre());
			    	    filtros.put("organizacionId", organizacionStrategos.getOrganizacionId().toString());
			    	    filtros.put("claseId", clase.getClaseId().toString());
			    	    List<Indicador> inds = new ArrayList<Indicador>();
			    	    inds = strategosIndicadoresService.getIndicadores(filtros);
		        		indicador = null;
			    	    if (inds.size() > 0)
			    	    	indicador = (Indicador)inds.get(0);
		        	}
		        	else
		        		indicador = null;
		    	    
		        	if (indicador == null) continue;
		        	TablaBasicaPDF tabla = null;
		        	boolean haypages = false;
		        	if (lstClasesPasivos.size() > 0)
		        	{
					    Encabezado(font, documento, vista, mensajes, request.getParameter("fecha"), indicador.getNombre(), organizacionStrategos.getNombre());
					    
					    //Tabla Pasivos  
					    tabla = GenerarTabla(documento, lstClasesPasivos, new Long(organizaciones[i]), indicador, vista, "Capt. Público + Partipaciones", "Pasivos (Captaciones)", "Captaciones", diaReporte, mesReporte, anoReporte, mesCierre, request, true);			    
				    	documento.add(tabla.getTabla());
				    	haypages = true;
		        	}
			    	
		        	if (lstClasesActivos.size() > 0)
		        	{
				    	//Tabla Activos
		        		if (haypages)
		        		{
		        			documento.newPage();
		        			documento.add(new Paragraph(" "));
		        			documento.add(new Paragraph(" "));
		        		}
				    	Encabezado(font, documento, vista, mensajes, request.getParameter("fecha"), indicador.getNombre(), organizacionStrategos.getNombre());
					    
				    	tabla = GenerarTabla(documento, lstClasesActivos, new Long(organizaciones[i]), indicador, vista, "Cartera Créditos Bruta", "Activos (Cartera de Créditos)", "Cartera de Créditos", diaReporte, mesReporte, anoReporte, mesCierre, request, false);
				    	documento.add(tabla.getTabla());
				    	haypages = true;
		        	}
			    	
		        	if (haypages)
		        		pages++;
		    	}
		    }
		}
	    
	    frameworkService.close();
	    strategosOrganizacionesService.close();
	    strategosIndicadoresService.close();
	    strategosClasesIndicadoresService.close();
	}
	
	/** Encabezado(Font font, Document documento, int vista, MessageResources mensajes, String fecha, String indNombre, String orgNombre)
	 * Contruir el Encabezado del reporte
	 * 
	 * @param font				Font
	 * @param documento			Documento a mostrar
	 * @param vista				Tipo de Vista
	 * @param mensajes			Recursos
	 * @param fecha				Fecha del reporte
	 * @param indNombre			Indicador Nombre
	 * @param orgNombre			Organizacion Nombre
	 */
	private void Encabezado(Font font, Document documento, int vista, MessageResources mensajes, String fecha, String indNombre, String orgNombre) throws DocumentException
	{
	    Paragraph texto = new Paragraph(mensajes.getMessage("action.reportecomiteejecutivo.fecha") + ": " + fecha, font);
	    texto.setAlignment(0);
	    documento.add(texto);
	    
	    String vistaNombre = "";
	    if (vista == 1)
	    	vistaNombre = mensajes.getMessage("jsp.reportes.parametroscomiteejecutivo.vista.producto");
		else if (vista == 2)
			vistaNombre = mensajes.getMessage("jsp.reportes.parametroscomiteejecutivo.vista.bancasegmento");
	    	
	    Paragraph texto2 = new Paragraph(mensajes.getMessage("jsp.reportes.parametroscomiteejecutivo.vista") + ": " + vistaNombre + " " + mensajes.getMessage("action.reportecomiteejecutivo.expresado"), font);
	    texto2.setAlignment(0);
	    texto2.setSpacingBefore(1.0F);
	    documento.add(texto2);

	    if (vista == 1)
	    {
	    	Paragraph texto3 = new Paragraph(mensajes.getMessage("action.reportecomiteejecutivo.organizacion") + ": " + orgNombre, font);
		    texto3.setAlignment(0);
		    texto3.setSpacingBefore(1.0F);
		    documento.add(texto3);
	    }
	    
	    Paragraph texto4 = new Paragraph(mensajes.getMessage("action.reportecomiteejecutivo.indicador") + ": " + indNombre, font);
	    texto4.setAlignment(0);
	    texto4.setSpacingBefore(1.0F);
	    documento.add(texto4);
	}
	
	/** GenerarTablaVistaBanca(List<ClaseIndicadores> lista, String[] organizaciones, Indicador indicador, int vista, String tituloUltimaColumna, String tituloCol, String tituloComparacion, int diaReporte, int mesReporte, int anoReporte, HttpServletRequest request)
	 * Generar el reporte Vista tipo Banca
	 * 
	 * @param list					Lista de Indicadores
	 * @param organizaciones		Lista de Organizaciones
	 * @param indicador				Indicador Base
	 * @param vista					Tipo de Vista
	 * @param tituloUltimaColumna	Titulo Columna Base
	 * @param tituloCol				Titulo Columnas
	 * @param tituloComparacion		Titulo Columna Comparacion
	 * @param diaReporte			dia del reporte
	 * @param mesReporte			mes del reporte
	 * @param anoReporte			ano del reporte
	 * @param request				HttpServletRequest
	 * 
	 *  @return TablaBasicaPDF		Tabla PDF
	 */
	private TablaBasicaPDF GenerarTablaVistaBanca(Document documento, List<ClaseIndicadores> lista, String[] organizaciones, Indicador indicador, int vista, String tituloUltimaColumna, String tituloCol, String tituloComparacion, int diaReporte, int mesReporte, int anoReporte, HttpServletRequest request) throws Exception
	{
		StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
	    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
		String tituloFechaBase, tituloFechaAyer, tituloFechaAntier, tituloFechaAntiayer;
	    Calendar FechaBase = Calendar.getInstance();
	    Calendar FechaAyer = Calendar.getInstance();
	    Calendar FechaAntier = Calendar.getInstance();
	    Calendar FechaAntiayer = Calendar.getInstance();
	    
	    Calendar dias = Calendar.getInstance();
	    dias.set(1, anoReporte);
	    dias.set(2, mesReporte - 1);
	    dias.set(5, diaReporte);
	    tituloFechaBase = Integer.toString(dias.get(Calendar.DATE)) + "-" + NombreMes(dias.get(Calendar.MONTH)+1, dias.get(Calendar.YEAR), false);
	    FechaBase = (Calendar)dias.clone();
	    		
	    dias.add(Calendar.DATE, -1);
	    tituloFechaAyer = Integer.toString(dias.get(Calendar.DATE)) + "-" + NombreMes(dias.get(Calendar.MONTH)+1, dias.get(Calendar.YEAR), false);
	    FechaAyer = (Calendar)dias.clone();
	    
	    dias.add(Calendar.DATE, -1);
	    tituloFechaAntier = Integer.toString(dias.get(Calendar.DATE)) + "-" + NombreMes(dias.get(Calendar.MONTH)+1, dias.get(Calendar.YEAR), false);
	    FechaAntier = (Calendar)dias.clone();
	    
	    dias.add(Calendar.DATE, -1);
	    tituloFechaAntiayer = Integer.toString(dias.get(Calendar.DATE)) + "-" + NombreMes(dias.get(Calendar.MONTH)+1, dias.get(Calendar.YEAR), false);
	    FechaAntiayer = (Calendar)dias.clone();
	    
	    TablaBasicaPDF tabla = null;
	    int numeroLineas = 0;
	    int tamanoLineas = 16;
	    
	    OrganizacionStrategos organizacionBaseStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizaciones[0]));
    	Byte mesCierre = organizacionBaseStrategos.getMesCierre() != null ? organizacionBaseStrategos.getMesCierre() : 12;
	    
    	tabla = PrintEncabezado(tituloCol, request, font, false, documento, tabla, vista, mesCierre, anoReporte, mesReporte, tituloFechaAntiayer, tituloFechaAntier, tituloFechaAyer, tituloFechaBase);
    	
	    Double totalMedicionMesAnterior = 0D;
	    Double totalMedicionCierreAnterior = 0D;
	    Double totalMedicionFechaBase = 0D;
	    Double totalMedicionFechaAyer = 0D;
	    Double totalMedicionFechaAntier = 0D;
	    Double totalMedicionFechaAntiayer = 0D;
	    Double totalMedicionVariacionDia = 0D;
	    Double totalMedicionVariacionMes = 0D;
	    Double totalMedicionVariacionAno = 0D;
	    Double totalMedicionCrecimientoMes = 0D;
	    Double totalMedicionDesviacion = 0D;
    	Indicador indicadorMensual = null;
		String nombreIndicador = indicador.getNombre();
		int lenProducto = 100; 
	    
    	for (int i = 0; i < organizaciones.length; i++) 
    	{
			if (numeroLineas >= tamanoLineas)
			{
				numeroLineas = 0;
				tamanoLineas = 19;
				tabla = PrintEncabezado(tituloCol, request, font, true, documento, tabla, vista, mesCierre, anoReporte, mesReporte, tituloFechaAntiayer, tituloFechaAntier, tituloFechaAyer, tituloFechaBase);
			}

    		OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizaciones[i]));
    		_nivel = 0;
    		BuscarNivel(organizacionStrategos, strategosOrganizacionesService);
	    	mesCierre = organizacionBaseStrategos.getMesCierre() != null ? organizacionBaseStrategos.getMesCierre() : 12;
	    	
	    	Double sumaMedicionMesAnterior = 0D;
	    	Double sumaMedicionCierreAnterior = 0D;
	    	Double sumaMedicionFechaBase = 0D;
	    	Double sumaMedicionFechaAyer = 0D;
	    	Double sumaMedicionFechaAntier = 0D;
	    	Double sumaMedicionFechaAntiayer = 0D;
	    	Double sumaMedicionCrecimientoMes = 0D;
	    	Double sumaCumplimiento = 0D;
	    	
	    	tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
			tabla.agregarCelda(IdentarNombre(_nivel, organizacionStrategos.getNombre(), lenProducto));
			tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_RIGHT);
	    	for (int z = 0; z < lista.size(); z++)
	    	{
	    		ClaseIndicadores claseA = lista.get(z);
	    		if (claseA.getOrganizacionId().equals(organizacionStrategos.getOrganizacionId()) && !claseA.getNombre().equals(tituloComparacion))
	    		{
            		Map<String, String> filtros = new HashMap<String, String>();
            		filtros = new HashMap<String, String>();
    	    	    filtros.put("nombre", nombreIndicador);
    	    	    filtros.put("organizacionId", organizacionStrategos.getOrganizacionId().toString());
    	    	    filtros.put("claseId", claseA.getClaseId().toString());
    	    	    List<Indicador> inds = new ArrayList<Indicador>();
    	    	    inds = strategosIndicadoresService.getIndicadores(filtros);
            		indicador = null;
    	    	    if (inds.size() > 0)
    	    	    	indicador = (Indicador)inds.get(0);
	            	if (indicador == null) continue;

	            	// Buscar el indicador Mensual
            		filtros = new HashMap<String, String>();
    	    	    filtros.put("nombre", nombreIndicador.replace("(D)", "(M)"));
    	    	    filtros.put("organizacionId", organizacionStrategos.getOrganizacionId().toString());
    	    	    filtros.put("claseId", claseA.getClaseId().toString());
    	    	    inds = new ArrayList<Indicador>();
    	    	    inds = strategosIndicadoresService.getIndicadores(filtros);
            		indicadorMensual = null;
    	    	    if (inds.size() > 0)
    	    	    	indicadorMensual = (Indicador)inds.get(0);
	            	if (indicadorMensual == null) continue;
	            	
	    			//Cierre Anterior
	    			Calendar periodoCierreAnterior = Calendar.getInstance();
	    			if (mesCierre == 12)
	    			{
		    			periodoCierreAnterior.set(1, anoReporte-1);
	    				periodoCierreAnterior.set(2, 11);
		    			periodoCierreAnterior.set(5, 31);
	    			}
	    			else
	    			{
		    			periodoCierreAnterior.set(1, anoReporte);
	    				periodoCierreAnterior.set(2, mesCierre - 1);
		    			periodoCierreAnterior.set(5, PeriodoUtil.ultimoDiaMes(mesCierre, anoReporte));
	    			}
					int DAY_OF_YEAR = periodoCierreAnterior.get(6);
					int ANO_OF_YEAR = periodoCierreAnterior.get(1);
					int MES_OF_YEAR = periodoCierreAnterior.get(2)+1;
	            	
	    			List<Medicion> mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorMensual.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, MES_OF_YEAR, MES_OF_YEAR, indicadorMensual.getFrecuencia());
	    			Medicion med = (Medicion)mediciones.get(0);
	    			
	    			if (med.getValor() != null)
	    				if (claseA.getNivel() != null && claseA.getNivel().intValue() == 1)
	    					sumaMedicionCierreAnterior += ValorEnMillones(med.getValor());
	    			
	    			//Mes anterior
	    			Calendar periodoMesAnterior = Calendar.getInstance();
	    			int mesAnterior = mesReporte - 1;
	    			int anoMesAnterior = anoReporte;
	    			if (mesAnterior == 0)
	    			{
	    				anoMesAnterior = anoReporte - 1;
	    				mesAnterior = 12;
	    			}
	    			else
	    				mesAnterior = mesAnterior - 1;
	    			periodoMesAnterior.set(1, anoMesAnterior);
	    			periodoMesAnterior.set(2, mesAnterior);
	    			periodoMesAnterior.set(5, PeriodoUtil.ultimoDiaMes(mesAnterior, anoMesAnterior));
					DAY_OF_YEAR = periodoMesAnterior.get(6);
					ANO_OF_YEAR = periodoMesAnterior.get(1);
					MES_OF_YEAR = periodoMesAnterior.get(2);
	    			
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorMensual.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, MES_OF_YEAR, MES_OF_YEAR, indicadorMensual.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			if (med.getValor() != null)
	    				if (claseA.getNivel() != null && claseA.getNivel().intValue() == 1)
	    					sumaMedicionMesAnterior += ValorEnMillones(med.getValor());
	    			
	    			//CIFRAS SEMANA
	    			// dia 1
	    			DAY_OF_YEAR = FechaAntiayer.get(6);
	    			ANO_OF_YEAR = FechaAntiayer.get(1);
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indicador.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			if (med.getValor() != null)
	    				if (claseA.getNivel() != null && claseA.getNivel().intValue() == 1)
	    					sumaMedicionFechaAntiayer += ValorEnMillones(med.getValor());
	    			
	    			// dia 2
	    			DAY_OF_YEAR = FechaAntier.get(6);	
	    			ANO_OF_YEAR = FechaAntier.get(1);
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indicador.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			if (med.getValor() != null)
	    				if (claseA.getNivel() != null && claseA.getNivel().intValue() == 1)
	    					sumaMedicionFechaAntier += ValorEnMillones(med.getValor());
	    			
	    			// dia 3
	    			DAY_OF_YEAR = FechaAyer.get(6);	
	    			ANO_OF_YEAR = FechaAyer.get(1);
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indicador.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			if (med.getValor() != null)
	    				if (claseA.getNivel() != null && claseA.getNivel().intValue() == 1)
	    					sumaMedicionFechaAyer += ValorEnMillones(med.getValor());
	    			
	    			// dia 4
	    			DAY_OF_YEAR = FechaBase.get(6);		
	    			ANO_OF_YEAR = FechaBase.get(1);
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicador.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indicador.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			if (med.getValor() != null)
	    				if (claseA.getNivel() != null && claseA.getNivel().intValue() == 1)
	    					sumaMedicionFechaBase += ValorEnMillones(med.getValor());
	    			
	    			// OBJETIVOS MES
	    			// Buscar Presupuesto
			    	DAY_OF_YEAR = periodoMesAnterior.get(6);
			    	ANO_OF_YEAR = periodoMesAnterior.get(1);
			    	MES_OF_YEAR = periodoMesAnterior.get(2)+1;
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorMensual.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), ANO_OF_YEAR, ANO_OF_YEAR, MES_OF_YEAR, MES_OF_YEAR, indicadorMensual.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			if (med.getValor() != null)
	    				if (claseA.getNivel() != null && claseA.getNivel().intValue() == 1)
	    					sumaMedicionCrecimientoMes += ValorEnMillones(med.getValor());
	    		}
	    	}
	    	
	    	sumaCumplimiento = Cumplimiento((sumaMedicionFechaBase - sumaMedicionMesAnterior), sumaMedicionCrecimientoMes);
	    	
	    	String totalesColumna[] = {
	    			VgcFormatter.formatearNumero(sumaMedicionCierreAnterior), 
	    			VgcFormatter.formatearNumero(sumaMedicionMesAnterior), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaAntiayer), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaAntier), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaAyer), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaBase), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaBase - sumaMedicionFechaAyer), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaBase - sumaMedicionMesAnterior), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaBase - sumaMedicionCierreAnterior), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaBase - sumaMedicionMesAnterior), 
	    			VgcFormatter.formatearNumero(sumaMedicionCrecimientoMes),
	    			VgcFormatter.formatearNumero(Desviacion((sumaMedicionFechaBase - sumaMedicionMesAnterior), sumaMedicionCrecimientoMes)),
	    			VgcFormatter.formatearNumero(sumaCumplimiento) + "%"}; 
		    tabla.agregarFila(totalesColumna);
	    
		    if (_nivel == _minimoNivelOrganizacion)
		    {
		    	totalMedicionMesAnterior += sumaMedicionMesAnterior;
		    	totalMedicionCierreAnterior += sumaMedicionCierreAnterior;
		    	totalMedicionFechaBase += sumaMedicionFechaBase;
		    	totalMedicionFechaAyer += sumaMedicionFechaAyer;
		    	totalMedicionFechaAntier += sumaMedicionFechaAntier;
		    	totalMedicionFechaAntiayer += sumaMedicionFechaAntiayer;
		    	totalMedicionVariacionDia += (sumaMedicionFechaBase - sumaMedicionFechaAyer);
		    	totalMedicionVariacionMes += (sumaMedicionFechaBase - sumaMedicionMesAnterior);
		    	totalMedicionVariacionAno += (sumaMedicionFechaBase - sumaMedicionCierreAnterior);
		    	totalMedicionCrecimientoMes += sumaMedicionCrecimientoMes;
		    	totalMedicionDesviacion += (Desviacion((sumaMedicionFechaBase - sumaMedicionMesAnterior), sumaMedicionCrecimientoMes));
		    }

		    numeroLineas++;
		}

		if (numeroLineas >= tamanoLineas)
		{
			numeroLineas = 0;
			tabla = PrintEncabezado(tituloCol, request, font, true, documento, tabla, vista, mesCierre, anoReporte, mesReporte, tituloFechaAntiayer, tituloFechaAntier, tituloFechaAyer, tituloFechaBase);
		}
    	
    	tabla.setColorLetra(255, 255, 255);
	    tabla.setColorFondo(0, 0, 205);
	    
    	tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
		tabla.agregarCelda(organizacionBaseStrategos.getNombre());
		tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_RIGHT);
		
    	String totalesColumna[] = {
    			VgcFormatter.formatearNumero(totalMedicionCierreAnterior), 
    			VgcFormatter.formatearNumero(totalMedicionMesAnterior), 
    			VgcFormatter.formatearNumero(totalMedicionFechaAntiayer), 
    			VgcFormatter.formatearNumero(totalMedicionFechaAntier), 
    			VgcFormatter.formatearNumero(totalMedicionFechaAyer), 
    			VgcFormatter.formatearNumero(totalMedicionFechaBase), 
    			VgcFormatter.formatearNumero(totalMedicionVariacionDia), 
    			VgcFormatter.formatearNumero(totalMedicionVariacionMes), 
    			VgcFormatter.formatearNumero(totalMedicionVariacionAno), 
    			VgcFormatter.formatearNumero(totalMedicionVariacionMes), 
    			VgcFormatter.formatearNumero(totalMedicionCrecimientoMes),
    			VgcFormatter.formatearNumero(totalMedicionDesviacion),
    			VgcFormatter.formatearNumero(Cumplimiento(totalMedicionVariacionMes, totalMedicionCrecimientoMes)) + "%"};
	    tabla.agregarFila(totalesColumna);
	    
	    tabla.setDefaultColorFondo();
	    tabla.setDefaultColorLetra();
    	
	    strategosMedicionesService.close();
	    strategosIndicadoresService.close();
	    strategosClasesIndicadoresService.close();
	    strategosOrganizacionesService.close();

	    return tabla;
	}
	
	/** GenerarTabla(List<ClaseIndicadores> lista, Long organizacionId, Indicador indicador, int vista, String tituloUltimaColumna, String tituloCol, String tituloComparacion, int diaReporte, int mesReporte, int anoReporte, Byte mesCierre, HttpServletRequest request)
	 * Generar el reporte Vista tipo Banca
	 * 
	 * @param list					Lista de Indicadores
	 * @param organizacionId		Organizacion Id
	 * @param indicador				Indicador Base
	 * @param vista					Tipo de Vista
	 * @param tituloUltimaColumna	Titulo Columna Base
	 * @param tituloCol				Titulo Columnas
	 * @param tituloComparacion		Titulo Columna Comparacion
	 * @param diaReporte			dia del reporte
	 * @param mesReporte			mes del reporte
	 * @param anoReporte			ano del reporte
	 * @param mesCierre				mes de cierre de la organizacion
	 * @param request				HttpServletRequest
	 * 
	 *  @return TablaBasicaPDF		Tabla PDF
	 */
	private TablaBasicaPDF GenerarTabla(Document documento, List<ClaseIndicadores> lista, Long organizacionId, Indicador indicador, int vista, String tituloUltimaColumna, String tituloCol, String tituloComparacion, int diaReporte, int mesReporte, int anoReporte, Byte mesCierre, HttpServletRequest request, boolean esPasivo) throws Exception
	{
	    StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
	    StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
	    StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();

	    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    Font fontBold = new Font(getConfiguracionPagina(request).getCodigoFuente());
	    fontBold.setStyle(Font.BOLD);
		String tituloFechaBase, tituloFechaAyer, tituloFechaAntier, tituloFechaAntiayer;
	    Calendar FechaBase = Calendar.getInstance();
	    Calendar FechaAyer = Calendar.getInstance();
	    Calendar FechaAntier = Calendar.getInstance();
	    Calendar FechaAntiayer = Calendar.getInstance();
	    
	    Calendar dias = Calendar.getInstance();
	    dias.set(1, anoReporte);
	    dias.set(2, mesReporte - 1);
	    dias.set(5, diaReporte);
	    tituloFechaBase = Integer.toString(dias.get(Calendar.DATE)) + "-" + NombreMes(dias.get(Calendar.MONTH)+1, dias.get(Calendar.YEAR), false);
	    FechaBase = (Calendar)dias.clone();
	    		
	    dias.add(Calendar.DATE, -1);
	    tituloFechaAyer = Integer.toString(dias.get(Calendar.DATE)) + "-" + NombreMes(dias.get(Calendar.MONTH)+1, dias.get(Calendar.YEAR), false);
	    FechaAyer = (Calendar)dias.clone();
	    
	    dias.add(Calendar.DATE, -1);
	    tituloFechaAntier = Integer.toString(dias.get(Calendar.DATE)) + "-" + NombreMes(dias.get(Calendar.MONTH)+1, dias.get(Calendar.YEAR), false);
	    FechaAntier = (Calendar)dias.clone();
	    
	    dias.add(Calendar.DATE, -1);
	    tituloFechaAntiayer = Integer.toString(dias.get(Calendar.DATE)) + "-" + NombreMes(dias.get(Calendar.MONTH)+1, dias.get(Calendar.YEAR), false);
	    FechaAntiayer = (Calendar)dias.clone();
	    
	    TablaBasicaPDF tabla = null;
	    int numeroLineas = 0;
	    int tamanoLineas = 15;
	    int lenProducto = 100;

	    tabla = PrintEncabezado(tituloCol, request, font, false, documento, tabla, vista, mesCierre, anoReporte, mesReporte, tituloFechaAntiayer, tituloFechaAntier, tituloFechaAyer, tituloFechaBase);

	    //Agregar productos y valores
	    if (vista == 1)
	    {
	    	//Buscar clase indicador para Cuota de Mercado
	    	String nombreInd = indicador.getNombre();
			Map<String, Object> filtros = new HashMap<String, Object>();
			ClaseIndicadores claseCuotaMercado = new ClaseIndicadores();
			Indicador indCuotaMercado = new Indicador();
			
			if (nombreInd.equals("Saldo Final (D)") || nombreInd.equals("Saldo Final (M)"))
			{
				if (esPasivo)
					filtros.put("nombre", "Cuota Mercado Saldo Final (pasivo)");
				else
					filtros.put("nombre", "Cuota Mercado Saldo Final");
	    	    filtros.put("organizacionId", organizacionId.toString());
				List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClases(filtros);
				if (clases.size() > 0)
					claseCuotaMercado = clases.get(0);
			}
			else if (nombreInd.equals("Saldo Intereses (D)") || nombreInd.equals("Saldo Intereses (M)"))
			{
				if (esPasivo)
					filtros.put("nombre", "Cuota Mercado Saldo Intereses (pasivo)");
				else
					filtros.put("nombre", "Cuota Mercado Saldo Intereses");
	    	    filtros.put("organizacionId", organizacionId.toString());
				List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClases(filtros);
				if (clases.size() > 0)
					claseCuotaMercado = clases.get(0);
			}
			else if (nombreInd.equals("Saldo Promedio (D)") || nombreInd.equals("Saldo Promedio (M)"))
			{
				if (esPasivo)
					filtros.put("nombre", "Cuota Mercado Saldo Promedio (pasivo)");
				else
					filtros.put("nombre", "Cuota Mercado Saldo Promedio");
	    	    filtros.put("organizacionId", organizacionId.toString());
				List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClases(filtros);
				if (clases.size() > 0)
					claseCuotaMercado = clases.get(0);
			}
			else if (nombreInd.equals("Saldo Vencido (D)") || nombreInd.equals("Saldo Vencido (M)"))
			{
				if (esPasivo)
					filtros.put("nombre", "Cuota Mercado Saldo Vencido (pasivo)");
				else
					filtros.put("nombre", "Cuota Mercado Saldo Vencido");
	    	    filtros.put("organizacionId", organizacionId.toString());
				List<ClaseIndicadores> clases = strategosClasesIndicadoresService.getClases(filtros);
				if (clases.size() > 0)
					claseCuotaMercado = clases.get(0);
			}
			
			if (claseCuotaMercado.getClaseId() != null)
			{
				filtros.clear();
				filtros.put("nombre", "Cuota Mercado");
	    	    filtros.put("organizacionId", organizacionId.toString());
	    	    filtros.put("claseId", claseCuotaMercado.getClaseId().toString());
				List<Indicador> indicadores = strategosIndicadoresService.getIndicadores(filtros);
				if (indicadores.size() > 0)
					indCuotaMercado = indicadores.get(0);
			}
			
			Double cuotaMercadoMesAnterior = 0D;
	    	Double cuotaMercadoCierreAnterior = 0D;
	    	Double cuotaMercadoFechaBase = 0D;
	    	Double cuotaMercadoFechaAyer = 0D;
	    	Double cuotaMercadoFechaAntier = 0D;
	    	Double cuotaMercadoFechaAntiayer = 0D;
	    	Double cuotaMercadoVariacionDia = 0D;
	    	Double cuotaMercadoVariacionMes = 0D;
	    	Double cuotaMercadoVariacionAno = 0D;
	    	Double cuotaMercadoCrecimientoMes = 0D;
			
	    	//acumuladores
	    	Double sumaMedicionMesAnterior = 0D;
	    	Double sumaMedicionCierreAnterior = 0D;
	    	Double sumaMedicionFechaBase = 0D;
	    	Double sumaMedicionFechaAyer = 0D;
	    	Double sumaMedicionFechaAntier = 0D;
	    	Double sumaMedicionFechaAntiayer = 0D;
	    	Double sumaMedicionVariacionDia = 0D;
	    	Double sumaMedicionVariacionMes = 0D;
	    	Double sumaMedicionVariacionAno = 0D;
	    	Double sumaMedicionCrecimientoMes = 0D;
	    	Double sumaMedicionDesviacion = 0D;
	    	Indicador indicadorMensual = null;
	    	Indicador indicadorDiario = null;
	    	List<Medicion> mediciones = new ArrayList<Medicion>();
	    	Medicion med = new Medicion(); 
	    	
	    	for (int z = 0; z < lista.size(); z++)
	    	{
	    		ClaseIndicadores claseA = lista.get(z);
	    		if (claseA.getOrganizacionId().equals(organizacionId) && !claseA.getNombre().equals(tituloComparacion))
	    		{
	    			if (numeroLineas >= tamanoLineas)
	    			{
	    				numeroLineas = 0;
	    				tamanoLineas = 19;
	    				tabla = PrintEncabezado(tituloCol, request, font, true, documento, tabla, vista, mesCierre, anoReporte, mesReporte, tituloFechaAntiayer, tituloFechaAntier, tituloFechaAyer, tituloFechaBase);
	    			}
		        	
	    			if (claseA != null)
		        	{
		        		filtros = new HashMap<String, Object>();
			    	    filtros.put("nombre", indicador.getNombre().replace("(D)", "(M)"));
			    	    filtros.put("organizacionId", claseA.getOrganizacionId().toString());
			    	    filtros.put("claseId", claseA.getClaseId().toString());
			    	    List<Indicador> inds = new ArrayList<Indicador>();
			    	    inds = strategosIndicadoresService.getIndicadores(filtros);
			    	    indicadorMensual = null;
			    	    if (inds.size() > 0)
			    	    	indicadorMensual = (Indicador)inds.get(0);
		        	}
		        	else
		        		indicadorMensual = null;
		        	if (indicadorMensual == null) continue;
	    			
	    			tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
	    			if (claseA.getNombre().trim().equalsIgnoreCase("Activos (Cartera de Créditos)") || claseA.getNombre().trim().equalsIgnoreCase("Pasivos (Captaciones)"))
	    			{
	    				tabla.setFormatoFont(fontBold.style());
	    				tabla.agregarCelda(IdentarNombre(claseA.getNivel(), claseA.getNombre(), lenProducto));
	    			}
	    			else
	    			{
	    				tabla.setFormatoFont(font.style());
	    				tabla.agregarCelda(IdentarNombre(claseA.getNivel(), claseA.getNombre(), lenProducto));
	    			}
	    			tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_RIGHT);
	    			
	    			// MEDICIONES MES Y CIERRE ANTERIOR
	    			//Cierre Anterior
	    			Calendar periodoCierreAnterior = Calendar.getInstance();
	    			if (mesCierre == 12)
	    			{
		    			periodoCierreAnterior.set(1, anoReporte-1);
	    				periodoCierreAnterior.set(2, 11);
		    			periodoCierreAnterior.set(5, 31);
	    			}
	    			else
	    			{
		    			periodoCierreAnterior.set(1, anoReporte);
	    				periodoCierreAnterior.set(2, mesCierre - 1);
		    			periodoCierreAnterior.set(5, PeriodoUtil.ultimoDiaMes(mesCierre, anoReporte));
	    			}
					int DAY_OF_YEAR = periodoCierreAnterior.get(6);
					int ANO_OF_YEAR = periodoCierreAnterior.get(1);
					int MES_OF_YEAR = periodoCierreAnterior.get(2)+1;
	    			
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorMensual.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, MES_OF_YEAR, MES_OF_YEAR, indicadorMensual.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			Double medicionCierreAnterior = 0D;
	    			
	    			if (med.getValor() != null)
	    				medicionCierreAnterior = ValorEnMillones(med.getValor());
	    			
	    			if (claseA.getNivel() != null && ((_minimoNivelActivo != null && claseA.getNivel().intValue() == _minimoNivelActivo && tituloCol == "Activos (Cartera de Créditos)") || (_minimoNivelPasivo != null && claseA.getNivel().intValue() == _minimoNivelPasivo && tituloCol == "Pasivos (Captaciones)")))
	    				sumaMedicionCierreAnterior += medicionCierreAnterior;

	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionCierreAnterior));	
	    			
	    			//Mes anterior
	    			Calendar periodoMesAnterior = Calendar.getInstance();
	    			int mesAnterior = mesReporte - 1;
	    			int anoMesAnterior = anoReporte;
	    			if (mesAnterior == 0)
	    			{
	    				anoMesAnterior = anoReporte - 1;
	    				mesAnterior = 12;
	    			}
	    			else
	    				mesAnterior = mesAnterior - 1;
	    			periodoMesAnterior.set(1, anoMesAnterior);
	    			periodoMesAnterior.set(2, mesAnterior);
	    			periodoMesAnterior.set(5, PeriodoUtil.ultimoDiaMes(mesAnterior, anoMesAnterior));
					DAY_OF_YEAR = periodoMesAnterior.get(6);
					ANO_OF_YEAR = periodoMesAnterior.get(1);
					MES_OF_YEAR = periodoMesAnterior.get(2);
	    			
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorMensual.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, MES_OF_YEAR, MES_OF_YEAR, indicadorMensual.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			Double medicionMesAnterior = 0D;
	    			if (med.getValor() != null)
	    				medicionMesAnterior = ValorEnMillones(med.getValor());
	    			
	    			if (claseA.getNivel() != null && ((_minimoNivelActivo != null && claseA.getNivel().intValue() == _minimoNivelActivo && tituloCol == "Activos (Cartera de Créditos)") || (_minimoNivelPasivo != null && claseA.getNivel().intValue() == _minimoNivelPasivo && tituloCol == "Pasivos (Captaciones)")))
	    				sumaMedicionMesAnterior += medicionMesAnterior;
	    			
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionMesAnterior));
	    			
		        	if (claseA != null)
		        	{
		        		filtros = new HashMap<String, Object>();
			    	    filtros.put("nombre", indicador.getNombre());
			    	    filtros.put("organizacionId", claseA.getOrganizacionId().toString());
			    	    filtros.put("claseId", claseA.getClaseId().toString());
			    	    List<Indicador> inds = new ArrayList<Indicador>();
			    	    inds = strategosIndicadoresService.getIndicadores(filtros);
			    	    indicadorDiario = null;
			    	    if (inds.size() > 0)
			    	    	indicadorDiario = (Indicador)inds.get(0);
		        	}
		        	else
		        		indicadorDiario = null;
		        	if (indicadorDiario == null) continue;
	    			
	    			//CIFRAS SEMANA
	    			// dia 1
	    			DAY_OF_YEAR = FechaAntiayer.get(6);	
	    			ANO_OF_YEAR = FechaAntiayer.get(1);
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorDiario.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indicadorDiario.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			Double medicionFechaAntiAyer = 0D;
	    			
	    			if (med.getValor() != null)
	    				medicionFechaAntiAyer = ValorEnMillones(med.getValor());
	    			
	    			if (claseA.getNivel() != null && ((_minimoNivelActivo != null && claseA.getNivel().intValue() == _minimoNivelActivo && tituloCol == "Activos (Cartera de Créditos)") || (_minimoNivelPasivo != null && claseA.getNivel().intValue() == _minimoNivelPasivo && tituloCol == "Pasivos (Captaciones)")))
	    				sumaMedicionFechaAntiayer += medicionFechaAntiAyer;
	    			
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionFechaAntiAyer));
	    			
	    			// dia 2
	    			DAY_OF_YEAR = FechaAntier.get(6);
	    			ANO_OF_YEAR = FechaAntier.get(1);
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorDiario.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indicadorDiario.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			Double medicionFechaAntier = 0D;
	    			
	    			if (med.getValor() != null)
	    				medicionFechaAntier = ValorEnMillones(med.getValor());
	    			
	    			if (claseA.getNivel() != null && ((_minimoNivelActivo != null && claseA.getNivel().intValue() == _minimoNivelActivo && tituloCol == "Activos (Cartera de Créditos)") || (_minimoNivelPasivo != null && claseA.getNivel().intValue() == _minimoNivelPasivo && tituloCol == "Pasivos (Captaciones)")))
	    				sumaMedicionFechaAntier += medicionFechaAntier;
	    			
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionFechaAntier));
	    			
	    			// dia 3
	    			DAY_OF_YEAR = FechaAyer.get(6);
	    			ANO_OF_YEAR = FechaAyer.get(1);
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorDiario.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indicadorDiario.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			Double medicionFechaAyer = 0D;
	    			
	    			if (med.getValor() != null)
	    				medicionFechaAyer = ValorEnMillones(med.getValor());
	    			
	    			if (claseA.getNivel() != null && ((_minimoNivelActivo != null && claseA.getNivel().intValue() == _minimoNivelActivo && tituloCol == "Activos (Cartera de Créditos)") || (_minimoNivelPasivo != null && claseA.getNivel().intValue() == _minimoNivelPasivo && tituloCol == "Pasivos (Captaciones)")))
	    				sumaMedicionFechaAyer += medicionFechaAyer;
	    			
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionFechaAyer));
	    			
	    			// dia 4
	    			DAY_OF_YEAR = FechaBase.get(6);		
	    			ANO_OF_YEAR = FechaBase.get(1);
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorDiario.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indicadorDiario.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			Double medicionFechaBase = 0D;
	    			
	    			if (med.getValor() != null)
	    				medicionFechaBase = ValorEnMillones(med.getValor());
	    			
	    			if (claseA.getNivel() != null && ((_minimoNivelActivo != null && claseA.getNivel().intValue() == _minimoNivelActivo && tituloCol == "Activos (Cartera de Créditos)") || (_minimoNivelPasivo != null && claseA.getNivel().intValue() == _minimoNivelPasivo && tituloCol == "Pasivos (Captaciones)")))
	    				sumaMedicionFechaBase += medicionFechaBase;
	    			
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionFechaBase));
	    			
	    			//VARIACIONES
	    			if (claseA.getNivel() != null && ((_minimoNivelActivo != null && claseA.getNivel().intValue() == _minimoNivelActivo && tituloCol == "Activos (Cartera de Créditos)") || (_minimoNivelPasivo != null && claseA.getNivel().intValue() == _minimoNivelPasivo && tituloCol == "Pasivos (Captaciones)")))
	    			{
		    			sumaMedicionVariacionDia += (medicionFechaBase - medicionFechaAyer);
				    	sumaMedicionVariacionMes += (medicionFechaBase - medicionMesAnterior);
				    	sumaMedicionVariacionAno += (medicionFechaBase - medicionCierreAnterior);
	    			}
	    			
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionFechaBase - medicionFechaAyer));
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionFechaBase - medicionMesAnterior));
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionFechaBase - medicionCierreAnterior));
	    			
	    			// OBJETIVOS MES
	    			// Buscar Presupuesto
			    	DAY_OF_YEAR = periodoMesAnterior.get(6);
			    	ANO_OF_YEAR = periodoMesAnterior.get(1);
			    	MES_OF_YEAR = periodoMesAnterior.get(2)+1;
	    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indicadorMensual.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), ANO_OF_YEAR, ANO_OF_YEAR, MES_OF_YEAR, MES_OF_YEAR, indicadorMensual.getFrecuencia());
	    			med = (Medicion)mediciones.get(0);
	    			Double medicionPresupuesto = 0D;
	    			if (med.getValor() != null)
	    				medicionPresupuesto = ValorEnMillones(med.getValor());
	    			
	    			if (claseA.getNivel() != null && ((_minimoNivelActivo != null && claseA.getNivel().intValue() == _minimoNivelActivo && tituloCol == "Activos (Cartera de Créditos)") || (_minimoNivelPasivo != null && claseA.getNivel().intValue() == _minimoNivelPasivo && tituloCol == "Pasivos (Captaciones)")))
    					sumaMedicionCrecimientoMes += medicionPresupuesto;
	    			
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionFechaBase - medicionMesAnterior));
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(medicionPresupuesto));
	    			Double desviacion = Desviacion((medicionFechaBase - medicionMesAnterior), medicionPresupuesto); 
	    			
	    			if (claseA.getNivel() != null && ((_minimoNivelActivo != null && claseA.getNivel().intValue() == _minimoNivelActivo && tituloCol == "Activos (Cartera de Créditos)") || (_minimoNivelPasivo != null && claseA.getNivel().intValue() == _minimoNivelPasivo && tituloCol == "Pasivos (Captaciones)")))
	    				sumaMedicionDesviacion += desviacion;
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(desviacion));
	    			tabla.agregarCelda(VgcFormatter.formatearNumero(Cumplimiento((medicionFechaBase - medicionMesAnterior), medicionPresupuesto)) + "%");
	    			
	    			numeroLineas++;
	    		}
	    	}
	    	
			//Cuota Mercado Anterior
			//Cierre Anterior
			Calendar periodoCierreAnterior = Calendar.getInstance();
			if (mesCierre == 12)
			{
    			periodoCierreAnterior.set(1, anoReporte-1);
				periodoCierreAnterior.set(2, 11);
    			periodoCierreAnterior.set(5, 31);
			}
			else
			{
    			periodoCierreAnterior.set(1, anoReporte);
				periodoCierreAnterior.set(2, mesCierre - 1);
    			periodoCierreAnterior.set(5, PeriodoUtil.ultimoDiaMes(mesCierre, anoReporte));
			}
			int DAY_OF_YEAR = periodoCierreAnterior.get(6);
			int ANO_OF_YEAR = periodoCierreAnterior.get(1);
			if (indCuotaMercado.getIndicadorId() != null)
			{
    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indCuotaMercado.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indCuotaMercado.getFrecuencia());
    			med = (Medicion)mediciones.get(0);
    			if (med.getValor() != null)
					cuotaMercadoCierreAnterior = med.getValor();
			}
			
			//Cuota Mercado Mes Anterior
			Calendar periodoMesAnterior = Calendar.getInstance();
			int mesAnterior = mesReporte - 1;
			int anoMesAnterior = anoReporte;
			
			periodoMesAnterior.set(1, anoMesAnterior);
			periodoMesAnterior.set(2, mesAnterior);
			periodoMesAnterior.set(5, 1);
			periodoMesAnterior.add(5, -1);
			
			DAY_OF_YEAR = periodoMesAnterior.get(6);
			ANO_OF_YEAR = periodoMesAnterior.get(1);
			if (indCuotaMercado.getIndicadorId() != null)
			{
    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indCuotaMercado.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indCuotaMercado.getFrecuencia());
    			med = (Medicion)mediciones.get(0);
    			if (med.getValor() != null)
    				cuotaMercadoMesAnterior = med.getValor();
			}
			
			//Cuota Mercado Dia 1
			DAY_OF_YEAR = FechaAntiayer.get(6);	
			ANO_OF_YEAR = FechaAntiayer.get(1);
			if (indCuotaMercado.getIndicadorId() != null)
			{
    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indCuotaMercado.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indCuotaMercado.getFrecuencia());
    			med = (Medicion)mediciones.get(0);
    			if (med.getValor() != null)
    				cuotaMercadoFechaAntiayer = med.getValor();
			}
			
			//Cuota Mercado dia 2
			DAY_OF_YEAR = FechaAntier.get(6);
			ANO_OF_YEAR = FechaAntier.get(1);
			if (indCuotaMercado.getIndicadorId() != null)
			{
    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indCuotaMercado.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indCuotaMercado.getFrecuencia());
    			med = (Medicion)mediciones.get(0);
    			if (med.getValor() != null)
    				cuotaMercadoFechaAntier = med.getValor();
			}
			
			//Cuota Mercado dia 3
			DAY_OF_YEAR = FechaAyer.get(6);
			ANO_OF_YEAR = FechaAyer.get(1);
			if (indCuotaMercado.getIndicadorId() != null)
			{
    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indCuotaMercado.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indCuotaMercado.getFrecuencia());
    			med = (Medicion)mediciones.get(0);
    			if (med.getValor() != null)
    				cuotaMercadoFechaAyer = med.getValor();
			}

			//Cuota Mercado dia 4
			DAY_OF_YEAR = FechaBase.get(6);		
			ANO_OF_YEAR = FechaBase.get(1);
			if (indCuotaMercado.getIndicadorId() != null)
			{
    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indCuotaMercado.getIndicadorId(), SerieTiempo.getSerieRealId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indCuotaMercado.getFrecuencia());
    			med = (Medicion)mediciones.get(0);
    			if (med.getValor() != null)
    				cuotaMercadoFechaBase = med.getValor();
			}
			
			//Cuota Mercado Presupuesto
	    	DAY_OF_YEAR = periodoMesAnterior.get(6);
	    	ANO_OF_YEAR = periodoMesAnterior.get(1);
			if (indCuotaMercado.getIndicadorId() != null)
			{
    			mediciones = strategosMedicionesService.getMedicionesPeriodo(indCuotaMercado.getIndicadorId(), SerieTiempo.getSerieProgramadoId(), ANO_OF_YEAR, ANO_OF_YEAR, DAY_OF_YEAR, DAY_OF_YEAR, indCuotaMercado.getFrecuencia());
    			med = (Medicion)mediciones.get(0);
    			if (med.getValor() != null)
    				cuotaMercadoCrecimientoMes = med.getValor();
			}
	    	
			cuotaMercadoVariacionDia = (cuotaMercadoFechaBase - cuotaMercadoFechaAyer);
	    	cuotaMercadoVariacionMes = (cuotaMercadoFechaBase - cuotaMercadoMesAnterior);
	    	cuotaMercadoVariacionAno = (cuotaMercadoFechaBase - cuotaMercadoCierreAnterior);
			
	    	//Agregar fila con totales
	    	double totalCumplimiento = Cumplimiento(sumaMedicionVariacionMes, sumaMedicionCrecimientoMes);
	    	
			if (numeroLineas >= tamanoLineas)
			{
				numeroLineas = 0;
				tabla = PrintEncabezado(tituloCol, request, font, true, documento, tabla, vista, mesCierre, anoReporte, mesReporte, tituloFechaAntiayer, tituloFechaAntier, tituloFechaAyer, tituloFechaBase);
			}
	    	
			tabla.setColorLetra(255, 255, 255);
		    tabla.setColorFondo(0, 0, 205);
		    
			tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
			tabla.agregarCelda(tituloUltimaColumna);
			tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_RIGHT);
			
	    	String totalesColumna[] = {
	    			VgcFormatter.formatearNumero(sumaMedicionCierreAnterior), 
	    			VgcFormatter.formatearNumero(sumaMedicionMesAnterior), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaAntiayer), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaAntier), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaAyer), 
	    			VgcFormatter.formatearNumero(sumaMedicionFechaBase), 
	    			VgcFormatter.formatearNumero(sumaMedicionVariacionDia), 
	    			VgcFormatter.formatearNumero(sumaMedicionVariacionMes), 
	    			VgcFormatter.formatearNumero(sumaMedicionVariacionAno), 
	    			VgcFormatter.formatearNumero(sumaMedicionVariacionMes), 
	    			VgcFormatter.formatearNumero(sumaMedicionCrecimientoMes),
	    			VgcFormatter.formatearNumero(sumaMedicionDesviacion), 
	    			VgcFormatter.formatearNumero(totalCumplimiento) + "%"};
		    tabla.agregarFila(totalesColumna);
	    	
	    	//Agregar fila con indicador Cuota de Mercado
		    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
			tabla.agregarCelda("CUOTA MERCADO (%)");
			tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_RIGHT);
			
			String cuotasMercado[] = {
					VgcFormatter.formatearNumero(cuotaMercadoCierreAnterior), 
					VgcFormatter.formatearNumero(cuotaMercadoMesAnterior), 
					VgcFormatter.formatearNumero(cuotaMercadoFechaAntiayer), 
					VgcFormatter.formatearNumero(cuotaMercadoFechaAntier), 
					VgcFormatter.formatearNumero(cuotaMercadoFechaAyer), 
					VgcFormatter.formatearNumero(cuotaMercadoFechaBase), 
					VgcFormatter.formatearNumero(cuotaMercadoVariacionDia), 
					VgcFormatter.formatearNumero(cuotaMercadoVariacionMes), 
					VgcFormatter.formatearNumero(cuotaMercadoVariacionAno), 
					VgcFormatter.formatearNumero(cuotaMercadoVariacionMes), 
					VgcFormatter.formatearNumero(cuotaMercadoCrecimientoMes),
	    			VgcFormatter.formatearNumero(Desviacion(cuotaMercadoVariacionMes, cuotaMercadoCrecimientoMes)),
	    			VgcFormatter.formatearNumero(Cumplimiento(cuotaMercadoVariacionMes, cuotaMercadoCrecimientoMes)) + "%"};
		    tabla.agregarFila(cuotasMercado);
		    tabla.setDefaultColorFondo();
		    tabla.setDefaultColorLetra();
	    }
	    
	    strategosMedicionesService.close();
	    strategosIndicadoresService.close();
	    strategosClasesIndicadoresService.close();

	    return tabla;
	}
	
	/** ValorEnMillones(Double valor)
	 * divide el valor en 1000000
	 * 
	 * @param valor					valor
	 * 
	 *  @return el valor dividido
	 */
	private Double ValorEnMillones(Double valor)
	{
		return (valor / 1000000);
	}
	
	/** ClaseEsActivo(ClaseIndicadores clase)
	 * Permite saber si la clase pertenece al los activos
	 * 
	 * @param clase					Objeto Clase
	 * 
	 *  @return true si pertenece a la clase activo, o false si no
	 */
	private Boolean ClaseEsActivo(ClaseIndicadores clase)
	{
		Boolean resultado = false;
		if (clase.getNombre().trim().equalsIgnoreCase("Activos (Cartera de Créditos)"))
			resultado = true;
		else if (clase.getNombre().trim().equalsIgnoreCase("Pasivos (Captaciones)"))
			resultado = false;
		else
		{
			if (clase.getPadreId() != null)
			{
				StrategosClasesIndicadoresService strategosClasesIndicadoresService = StrategosServiceFactory.getInstance().openStrategosClasesIndicadoresService();
			    ClaseIndicadores clasePadre = (ClaseIndicadores)strategosClasesIndicadoresService.load(ClaseIndicadores.class, clase.getPadreId());
			    _nivel ++;
			    resultado = ClaseEsActivo(clasePadre);
				strategosClasesIndicadoresService.close();
			}	
		}
		
		return resultado;
	}
	
	/** NombreMes(int mes, int ano, boolean conAno)
	 * Generar el nombre del mes
	 * 
	 * @param mes					mes
	 * @param ano					ano
	 * @param conAno				Variable bool para agregar el ano al nombre
	 * 
	 *  @return nombre del Mes
	 */
	private String NombreMes(int mes, int ano, boolean conAno)
	{
		String nombreMes = "";
        switch (new Integer(mes)) 
        { 
	        case 1:
	        	nombreMes = "Ene";
	        case 2:
	        	nombreMes = "Feb"; break;
	        case 3:
	        	nombreMes = "Mar"; break;
	        case 4:
	        	nombreMes = "Abr"; break;
	        case 5:
	        	nombreMes = "May"; break;
	        case 6:
	        	nombreMes = "Jun"; break;
	        case 7:
	        	nombreMes = "Jul"; break;
	        case 8:
	        	nombreMes = "Ago"; break;
	        case 9:
	        	nombreMes = "Sep"; break;
	        case 10:
	        	nombreMes = "Oct"; break;
	        case 11:
	        	nombreMes = "Nov"; break;
	        case 12:
	        	nombreMes = "Dic"; break;
        }
        
        if (conAno)
        	return nombreMes + "-" + Integer.toString(ano).substring(2);
        else
        	return nombreMes;
		
	}
	
	/** IdentarNombre(Integer nivel, String nombre)
	 * Agregar espacios en blanco a un nombre
	 * 
	 * @param nivel					nivel de la clase o numero de espacios a agregar
	 * @param nombre				nombre
	 * 
	 *  @return el nombre con espacios en blanco al principio del nombre
	 */
	private String IdentarNombre(Integer nivel, String nombre, int tamano)
	{
		tamano = nombre.length() > tamano ? tamano : nombre.length(); 
		nombre = nombre.substring(0, tamano);
		StringBuilder nombreStringBuilder = new StringBuilder();
				
		if (nivel != null && nivel > 0) 
			for(int i = 0; i< nivel; i++)
				nombreStringBuilder.append(" ");
		nombreStringBuilder.append(nombre);
		
		return nombreStringBuilder.toString();
	}
	
	/** BuscarNivel(OrganizacionStrategos organizacion, StrategosOrganizacionesService strategosOrganizacionesService)
	 * Permite saber si la clase pertenece al los activos
	 * 
	 * @param organizacion						Objeto organizacion
	 * @param strategosOrganizacionesService	Instancia Organizacion
	 * 
	 *  @return true si pertenece Cuando llegue a la organizacion deseada
	 */
	private Boolean BuscarNivel(OrganizacionStrategos organizacion, StrategosOrganizacionesService strategosOrganizacionesService)
	{
		Boolean resultado = false;
		if (organizacion.getNombre().trim().equalsIgnoreCase("Banco de Venezuela"))
			resultado = true;
		else
		{
			if (organizacion.getPadreId() != null)
			{
				OrganizacionStrategos organizacionBaseStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizacion.getPadreId()));
			    _nivel ++;
			    resultado = BuscarNivel(organizacionBaseStrategos, strategosOrganizacionesService);
			}	
		}
		
		return resultado;
	}
	
	private double Cumplimiento(double variacionMes, double crecimientoMes)
	{
	    Locale currentLocale = new Locale("en","US");
	    NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
	    DecimalFormat decimalformat = (DecimalFormat)numberFormatter;
	    decimalformat.applyPattern("0.00");

		double varMes = Double.parseDouble(decimalformat.format(variacionMes));
		double creMes = Double.parseDouble(decimalformat.format(crecimientoMes));
		double cumplimiento = 0;
		
		if (creMes > 0 && varMes > 0)
			cumplimiento = (varMes / creMes) * 100;

		return cumplimiento;
	}
	
	private double Desviacion(double variacionMes, double crecimientoMes)
	{
	    Locale currentLocale = new Locale("en","US");
	    NumberFormat numberFormatter = NumberFormat.getNumberInstance(currentLocale);
	    DecimalFormat decimalformat = (DecimalFormat)numberFormatter;
	    decimalformat.applyPattern("0.00");

		double varMes = Double.parseDouble(decimalformat.format(variacionMes));
		double creMes = Double.parseDouble(decimalformat.format(crecimientoMes));
		
		return (varMes - creMes);
	}
	
	private TablaBasicaPDF PrintEncabezado(String tituloCol, HttpServletRequest request, Font font, boolean saltarPagina, Document documento, TablaBasicaPDF tabla, int vista, int mesCierre, int anoReporte, int mesReporte, String tituloFechaAntiayer, String tituloFechaAntier, String tituloFechaAyer, String tituloFechaBase) throws Exception
	{
		if (saltarPagina)
		{
			documento.add(tabla.getTabla());
			documento.newPage();
			documento.add(new Paragraph(" "));			
		}
		
	    tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
	    int[] columnas = new int[14];
	    columnas[0] = 24;
	    columnas[1] = 6;
	    columnas[2] = 6;
	    columnas[3] = 6;
	    columnas[4] = 6;
	    columnas[5] = 6;
	    columnas[6] = 6;
	    columnas[7] = 6;
	    columnas[8] = 6;
	    columnas[9] = 6;
	    columnas[10] = 6;
	    columnas[11] = 6;
	    columnas[12] = 6;
	    columnas[13] = 6;

	    tabla.setAnchoBorde(0);
	    tabla.setAmplitudTabla(100);
	    tabla.crearTabla(columnas);
	    tabla.setFormatoFont(font.style());
	    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
	    tabla.setTamanoFont(10);
	    tabla.setCellpadding(0);
	    tabla.setColorLetra(255, 255, 255);
	    tabla.setColorFondo(0, 0, 205);
	    tabla.setColorBorde(120, 114, 77);

	    tabla.setColspan(1);
	    tabla.agregarCelda(tituloCol);
	    
	    tabla.setTamanoFont(8);
	    tabla.setColspan(1);
	    tabla.agregarCelda("Año Ant");

	    tabla.setColspan(1);
	    tabla.agregarCelda("Mes Ant");
	    
	    tabla.setTamanoFont(10);
	    tabla.setColspan(4);
	    tabla.agregarCelda("Cifras Semana");
	    
	    tabla.setColspan(3);
	    tabla.agregarCelda("Variaciones");
	    
	    tabla.setColspan(4);
	    tabla.agregarCelda("Objetivos Mes");
		
	    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
	    tabla.setColorLetra(255, 255, 255);
	    tabla.setColorFondo(0, 0, 205);

		tabla.setTamanoFont(8);
    	tabla.setColspan(1);
    	String titulo2 = "";
	    if (vista == 1)
	    	titulo2 = "Producto"; //mensajes.getMessage("jsp.reportes.parametroscomiteejecutivo.vista.producto");
		else if (vista == 2)
			titulo2 = "Banca"; // mensajes.getMessage("jsp.reportes.parametroscomiteejecutivo.vista.bancasegmento");
	    
	    String tituloColumnas[] = {titulo2, NombreMes(mesCierre, anoReporte - 1, true), NombreMes(mesReporte - 1, anoReporte, true), tituloFechaAntiayer, tituloFechaAntier, tituloFechaAyer, tituloFechaBase, "Día", "Mes", "Año", "Var Mes", "Crec Mes", "Desv Abs", "Cump. %"};
	    tabla.agregarFila(tituloColumnas);
	    
	    tabla.setDefaultColorFondo();
	    tabla.setDefaultColorLetra();
	    
	    return tabla;
	}
}
