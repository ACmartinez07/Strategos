package com.visiongc.app.strategos.web.struts.reportes.actions;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Wrapper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;
import org.apache.taglibs.standard.lang.jpath.adapter.Convert;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.impl.SessionImpl;
import org.hibernate.jmx.HibernateService;
import org.w3c.dom.NodeList;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.persistence.hibernate.StrategosIniciativasHibernateSession;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.model.util.LapsoTiempo;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.IndicadorEstado;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.planes.model.PlantillaPlanes;
import com.visiongc.app.strategos.planes.model.util.ConfiguracionPlan;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryProyectosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryProyecto;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativa;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;
import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.report.TablaPDF;
import com.visiongc.commons.report.VgcFormatoReporte;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.framework.web.struts.forms.NavegadorForm;
import com.visiongc.framework.web.struts.forms.servicio.GestionarServiciosForm;

public class ReporteExplicacionInstrumentoPdf extends VgcReporteBasicoAction {
	private static Session sesion;
	private int lineas = 0;
	private int tamanoPagina = 0;
	private int inicioLineas = 1;
	private int inicioTamanoPagina = 57;
	private int maxLineasAntesTabla = 4;
	
	protected String agregarTitulo(HttpServletRequest request,	MessageResources mensajes) throws Exception 
	{
		return mensajes.getMessage("jsp.reportes.explicacion.instrumento");
	}
	
	
	
	protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento) throws Exception 
	{
			    
		MessageResources mensajes = getResources(request);
		ReporteForm reporte = new ReporteForm();
		reporte.clear();
		
		String instrumentoId = (request.getParameter("instrumentoId"));
		
		
		String atributoOrden = "";
	
		int pagina = 0;
		
		if (pagina < 1) 
			pagina = 1;


		Map<String, Comparable> filtros = new HashMap<String, Comparable>();
	
		Paragraph texto;
		int columna = 1;
		
		documento.add(lineaEnBlanco(getConfiguracionPagina(request).getFuente()));
		
		Calendar fecha = Calendar.getInstance();
        int anoTemp = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        
       		
		Font fuente = getConfiguracionPagina(request).getFuente();
        MessageResources messageResources = getResources(request);
        
        StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
        StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
	
    	
		if ((instrumentoId != null) && (!instrumentoId.equals("")) && Long.parseLong(instrumentoId) != 0) 
			filtros.put("objetoId", instrumentoId);
		
		List<Explicacion> explicaciones = strategosExplicacionesService.getExplicaciones(pagina, 30, "fecha", "DESC", true, filtros).getLista();
				
		if(explicaciones != null && explicaciones.size() > 0) {
			
			Instrumentos instrumentos = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, new Long(instrumentoId));

			Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());
			
		    //Nombre de instrumento
			font.setSize(8);
			font.setStyle(Font.BOLD);
			
			
			texto = new Paragraph("Instrumento: "+ instrumentos.getNombreCorto(), font);
			texto.setAlignment(Element.ALIGN_LEFT);
			texto.setIndentationLeft(16);
			documento.add(texto);
			
			
			documento.add(lineaEnBlanco(fuente));		
			
			
			TablaPDF tabla = null;
	        tabla = new TablaPDF(getConfiguracionPagina(request), request);
	        int[] columnas = new int[7];
	        
	               
	        columnas[0] = 15;
	        columnas[1] = 10;
	        columnas[2] = 20;
	        columnas[3] = 7;
	        columnas[4] = 25;
	        columnas[5] = 20;
	        columnas[6] = 20;
			
	        tabla.setAmplitudTabla(100);
	        tabla.crearTabla(columnas);
	        
	        tabla.setAlineacionHorizontal(0);
	        
	        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarexplicaciones.columna.autor"));
	        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarexplicaciones.columna.fecha"));
	        tabla.agregarCelda(messageResources.getMessage("jsp.gestionarexplicaciones.columna.titulo"));
	        tabla.agregarCelda(messageResources.getMessage("jsp.editarexplicacion.ficha.publicar"));
	        tabla.agregarCelda(messageResources.getMessage("jsp.editarexplicacion.ficha.adjuntos"));
	        tabla.agregarCelda(messageResources.getMessage("jsp.pagina.instrumentos.avance"));
	        tabla.agregarCelda(messageResources.getMessage("action.reporte.estatus.iniciativa.observacion"));
	        
			
	        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
		    tabla.setAlineacionVertical(TablaBasicaPDF.V_ALINEACION_TOP);
			//
		    
		    for(Explicacion exp: explicaciones) {
		    	
		    	if(exp.getUsuarioCreado() != null) {
		    		tabla.agregarCelda(exp.getUsuarioCreado().getFullName());
		    	}else {
		    		tabla.agregarCelda("");
		    	}
		        
		    	
		    	if(exp.getFechaFormateada() != null) {
		        	tabla.agregarCelda(exp.getFechaFormateada());
		        }else {
		        	tabla.agregarCelda("");
		        }
		    			    	    	
		        tabla.agregarCelda(exp.getTitulo());
		        		    	
		    	if(exp.getPublico() == true) {
		        	tabla.agregarCelda("Si");
		        }else {
		        	tabla.agregarCelda("No");
		        }
		    	
		    	String cadena = "";
		    	
		    	if (exp.getAdjuntosExplicacion() != null)
				{
				
		    		for (Iterator<?> iter = exp.getAdjuntosExplicacion().iterator(); iter.hasNext(); )
					{
						AdjuntoExplicacion adjunto = (AdjuntoExplicacion)iter.next();
						cadena += " " + adjunto.getTitulo() + ",";
					}
					
		    		cadena = cadena.substring(0, cadena.length()-1);
				}
		    	
		    	tabla.agregarCelda(cadena);
		    	
		    	String memoDescripcion="";
		    	String memoCausas="";
		    	
		    	for (Iterator<?> i = exp.getMemosExplicacion().iterator(); i.hasNext(); ) 
				{
					MemoExplicacion memoExplicacion = (MemoExplicacion)i.next();
					Byte tipoMemo = memoExplicacion.getPk().getTipo();
					if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
						memoDescripcion=memoExplicacion.getMemo();
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_CAUSAS)))
						memoCausas=memoExplicacion.getMemo();
					
				}
		    	
		    	tabla.agregarCelda(memoDescripcion);
		        tabla.agregarCelda(memoCausas);
		       
		        documento.add(lineaEnBlanco(fuente));
		    }
		    
		    documento.add(tabla.getTabla());			
			documento.add(lineaEnBlanco(fuente));
						
		}
		
		
		
		
				        
		documento.newPage();
		strategosExplicacionesService.close();
        strategosInstrumentosService.close(); 
		
	}
	
		
}
	

