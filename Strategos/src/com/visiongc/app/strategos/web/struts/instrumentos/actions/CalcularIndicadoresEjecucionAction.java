/**
 * 
 */
package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativaEstatusService;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativa;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planes.StrategosPlanesService;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.EditarInstrumentosForm;
import com.visiongc.app.strategos.web.struts.mediciones.forms.ProtegerLiberarMedicionesForm;
import com.visiongc.app.strategos.web.struts.reportes.forms.ReporteForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Usuario;
import com.visiongc.framework.web.struts.forms.FiltroForm;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;

/**
 * @author Andres
 *
 */
public class CalcularIndicadoresEjecucionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		super.execute(mapping, form, request, response);
		
		boolean cancel = (request.getParameter("cancel") != null) && (request.getParameter("cancel").equals("1"));
	    if (cancel)
	    {
	      request.getSession().removeAttribute("EditarInstrumentosForm");
	      request.getSession().removeAttribute("verArchivoLog");
	      
	      return getForwardBack(request, 1, true);
	    }
	    String forward = mapping.getParameter();
	    	    

		/** Se obtiene el FormBean haciendo el casting respectivo */
	    EditarInstrumentosForm editarInstrumentoForm = (EditarInstrumentosForm)form;

		if (request.getParameter("funcion") != null)
		{
	    	String funcion = request.getParameter("funcion");
	    	
	    	
	    	if (funcion.equals("calcular")) 
	    	{	    		
	    		Calcular(request, editarInstrumentoForm);	    		
	    	}
		}
		return mapping.findForward(forward);
	}
	
	public void Calcular(HttpServletRequest request, EditarInstrumentosForm editarInstrumentoForm) throws Exception{
		
			ActionMessages messages = getMessages(request);
		
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance().openStrategosMedicionesService();
		
			Byte alcance = editarInstrumentoForm.getAlcance();
			Integer anio = editarInstrumentoForm.getAno();
			Integer mesInicial = editarInstrumentoForm.getMesInicial();
			Integer mesFinal = editarInstrumentoForm.getMesFinal();
			
			Map<String, String> filtros = new HashMap();
			
			if(request.getParameter("alcance").equals("1")){//instrumento seleccionado
				
				filtros = new HashMap();
				 
			 	int pagina = 0;
			    String atributoOrden = null;
			    String tipoOrden = null;

			    if (atributoOrden == null) 
			    	atributoOrden = "nombre";
			    if (tipoOrden == null) 
			    	tipoOrden = "ASC";
			    if (pagina < 1) 
			    	pagina = 1;
			    
			    String organizacionId = null;
			    
			   		    
			    if (pagina < 1) {
			      pagina = 1;
			    }
			    if ((organizacionId != null) && (!organizacionId.equals(""))) {
			      filtros.put("organizacionId", organizacionId);
			    }
			    
			    filtros.put("estatusId", "2");   
			    filtros.put("instrumentoId",  editarInstrumentoForm.getInstrumentoId().toString());  
			    
			    Instrumentos instrumento = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, new Long(editarInstrumentoForm.getInstrumentoId()));
			    
			    List<InstrumentoIniciativa> instrumentoIniciativas = strategosInstrumentosService.getIniciativasInstrumento(editarInstrumentoForm.getInstrumentoId());		    
			    List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();
			    List<Medicion> mediciones = new ArrayList<Medicion>();
			    
			    PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, atributoOrden, tipoOrden, true, filtros);
			    iniciativas = paginaIniciativas.getLista();
			    
			    int respuesta = 10000;
			    
			    Boolean hayPesos = true;	
			    
			    if(iniciativas.size() >0) {	
			    	
			    	if(mesInicial == mesFinal) {
			    		
			    		Double valorAcumulado= 0.0;
			    		hayPesos = true;			    		
			    		for(Iniciativa ini: iniciativas) {
				    		Double peso = 0.0;
				    		
				    		for (Iterator<InstrumentoIniciativa> iter = instrumentoIniciativas.iterator(); iter.hasNext();) {
					    		InstrumentoIniciativa instrumentoIniciativa = (InstrumentoIniciativa)iter.next();
					    		
					    		if(instrumentoIniciativa.getIniciativa().getIniciativaId().equals(ini.getIniciativaId())) {
					    			peso = instrumentoIniciativa.getPeso();
					    		}
					    	}
				    		
				    		if(peso != null) {
					    		Long indicadorId = ini.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
					    		Indicador indicadorIniciativa = (Indicador) strategosIndicadoresService.load(Indicador.class, indicadorId);
					    		
					    		SerieIndicador serieIndicadorLoad = null;			    		
					    		List<Medicion> medicionesPeriodo = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieReal().getSerieId().longValue(), anio, anio, mesInicial, mesFinal);
					    		
				    		
				    			for(Medicion med: medicionesPeriodo) {
					    			Double suma = (med.getValor() * (peso/100));
					    			valorAcumulado=valorAcumulado+suma;
					    		}
				    		}else {
				    			hayPesos = false;
				    			
				    		}
				    		
				    		
					    	
				    	}
			    		
			    		if(hayPesos){
			    			Indicador indicadorInstrumento = (Indicador) strategosIndicadoresService.load(Indicador.class, instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				    		
			    			Medicion medicionCalculada = new Medicion(new MedicionPK(new Long(instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento())), new Integer(anio), new Integer(mesInicial), new Long(SerieTiempo.getSerieReal().getSerieId())), valorAcumulado, new Boolean(false));		    	
			    			mediciones.add(medicionCalculada);
			    			respuesta = strategosMedicionesService.saveMediciones(mediciones, null, getUsuarioConectado(request), new Boolean(true), new Boolean(true));
			    			
			    			if(respuesta == 10000) {
			    				
			    				indicadorInstrumento.setUltimaMedicion(valorAcumulado);
			    				indicadorInstrumento.setFechaUltimaMedicion(""+anio.toString()+"/"+mesFinal.toString());
			    				
			    				respuesta = strategosIndicadoresService.saveIndicador(indicadorInstrumento, getUsuarioConectado(request));
			    				
			    				if(respuesta == 10000) {
			    					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.calculo.log.fechafincalculo.instrumentos"));
				    				saveMessages(request, messages);
			    				}
			    						    				
			    			}else if(respuesta == 10003) {
			    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmediciones.mensaje.guardarmediciones.related"));
			    				saveMessages(request, messages);
			    			}
			    		}
		    			
			    		
			    		
			    		
			    	}else {
			    		
			    		
			    		
			    		for(int x=mesInicial-1; x<mesFinal; x++) {
			    			
			    			Double valorAcumulado= 0.0;
			    			int indice =x+1;
			    		
			    			hayPesos = true;
			    			for(Iniciativa ini: iniciativas) {
					    		Double peso = 0.0;
					    		
					    		for (Iterator<InstrumentoIniciativa> iter = instrumentoIniciativas.iterator(); iter.hasNext();) {
						    		InstrumentoIniciativa instrumentoIniciativa = (InstrumentoIniciativa)iter.next();
						    		
						    		if(instrumentoIniciativa.getIniciativa().getIniciativaId().equals(ini.getIniciativaId())) {
						    			peso = instrumentoIniciativa.getPeso();
						    		}
						    	}
					    		
					    		if(peso != null) {
					    		
						    		Long indicadorId = ini.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
						    		Indicador indicadorIniciativa = (Indicador) strategosIndicadoresService.load(Indicador.class, indicadorId);
						    		
						    		SerieIndicador serieIndicadorLoad = null;			    		
						    		List<Medicion> medicionesPeriodo = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieReal().getSerieId().longValue(), anio, anio, indice, indice);
						    							    		
					    		
					    		
					    			for(Medicion med: medicionesPeriodo) {
						    			Double suma = (med.getValor() * (peso/100));
						    			valorAcumulado=valorAcumulado+suma;
						    		}
					    		}else {
					    			hayPesos = false;
					    			
					    		}
					    		
						    	
					    	}
			    			
			    			if(hayPesos){
			    			
				    			Indicador indicadorInstrumento = (Indicador) strategosIndicadoresService.load(Indicador.class, instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
				    			
				    			Medicion medicionCalculada = new Medicion(new MedicionPK(new Long(instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento())), new Integer(anio), new Integer(indice), new Long(SerieTiempo.getSerieReal().getSerieId())), valorAcumulado, new Boolean(false));		    	
				    			mediciones.add(medicionCalculada);
				    			respuesta = strategosMedicionesService.saveMediciones(mediciones, null, getUsuarioConectado(request), new Boolean(true), new Boolean(true));
				    			
				    			if(respuesta == 10000) {
				    				indicadorInstrumento.setUltimaMedicion(valorAcumulado);
				    				indicadorInstrumento.setFechaUltimaMedicion(""+anio.toString()+"/"+indice);
				    				
				    				respuesta = strategosIndicadoresService.saveIndicador(indicadorInstrumento, getUsuarioConectado(request));
				    				
				    				if(respuesta == 10000) {
				    					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.calculo.log.fechafincalculo.instrumentos"));
					    				saveMessages(request, messages);
				    				}
				    			}else if(respuesta == 10003) {
				    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmediciones.mensaje.guardarmediciones.related"));
				    				saveMessages(request, messages);
				    			}
			    			
			    			}
				    	}
			    		
			    	}
			    	
			    	if(!hayPesos) {
			    	
			    		messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.visualizariniciativasinstrumento.pesos.noregistros"));
						saveMessages(request, messages);
			    		
			    	}
			    	
			    }else {
			    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.visualizariniciativasinstrumento.noregistros"));
				    saveMessages(request, messages);
			    }
			    
			    
				
			}else {//todos los instrumentos
				List<Instrumentos> instrumentos = strategosInstrumentosService.getInstrumentos(0, 0, "nombreCorto", "ASC", true, filtros).getLista();       
			    
				if (instrumentos.size() > 0)
				{
					for (Iterator<Instrumentos> iter = instrumentos.iterator(); iter.hasNext();)
					{
						Instrumentos instrumento = (Instrumentos)iter.next();
						
						filtros = new HashMap();
						 
					 	int pagina = 0;
					    String atributoOrden = null;
					    String tipoOrden = null;

					    if (atributoOrden == null) 
					    	atributoOrden = "nombre";
					    if (tipoOrden == null) 
					    	tipoOrden = "ASC";
					    if (pagina < 1) 
					    	pagina = 1;
					    
					    String organizacionId = null;
					    
					   		    
					    if (pagina < 1) {
					      pagina = 1;
					    }
					    if ((organizacionId != null) && (!organizacionId.equals(""))) {
					      filtros.put("organizacionId", organizacionId);
					    }
					    
					    filtros.put("estatusId", "2");   
					    filtros.put("instrumentoId",  instrumento.getInstrumentoId().toString());  
					    
					    List<InstrumentoIniciativa> instrumentoIniciativas = strategosInstrumentosService.getIniciativasInstrumento(editarInstrumentoForm.getInstrumentoId());		    
					    List<Iniciativa> iniciativas = new ArrayList<Iniciativa>();
					    List<Medicion> mediciones = new ArrayList<Medicion>();
					    
					    PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(pagina, 30, atributoOrden, tipoOrden, true, filtros);
					    iniciativas = paginaIniciativas.getLista();
					    
					    int respuesta = 10000;
					    Boolean hayPesos = true;	
					    
					    if(iniciativas.size() >0) {	
					    	
					    	if(mesInicial == mesFinal) {
					    		
					    		Double valorAcumulado= 0.0;
					    		
					    		hayPesos = true;
				    						    		
					    		for(Iniciativa ini: iniciativas) {
						    		Double peso = 0.0;
						    		
						    		for (Iterator<InstrumentoIniciativa> iterIni = instrumentoIniciativas.iterator(); iter.hasNext();) {
							    		InstrumentoIniciativa instrumentoIniciativa = (InstrumentoIniciativa)iterIni.next();
							    		
							    		if(instrumentoIniciativa.getIniciativa().getIniciativaId().equals(ini.getIniciativaId())) {
							    			peso = instrumentoIniciativa.getPeso();
							    		}
							    	}
						    		
						    		if(peso != null) {
						    			Long indicadorId = ini.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
							    		Indicador indicadorIniciativa = (Indicador) strategosIndicadoresService.load(Indicador.class, indicadorId);
							    		
							    		SerieIndicador serieIndicadorLoad = null;			    		
							    		List<Medicion> medicionesPeriodo = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieReal().getSerieId().longValue(), anio, anio, mesInicial, mesFinal);
							    		
							    		for(Medicion med: medicionesPeriodo) {
							    			Double suma = (med.getValor() * (peso/100));
							    			valorAcumulado=valorAcumulado+suma;
							    		}
						    		}else {
						    			hayPesos = false;
						    			
						    		}
						    		
							    	
						    	}
					    		
					    		if(hayPesos){
				    			
						    		Indicador indicadorInstrumento = (Indicador) strategosIndicadoresService.load(Indicador.class, instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
						    		
					    			Medicion medicionCalculada = new Medicion(new MedicionPK(new Long(instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento())), new Integer(anio), new Integer(mesInicial), new Long(SerieTiempo.getSerieReal().getSerieId())), valorAcumulado, new Boolean(false));		    	
					    			mediciones.add(medicionCalculada);
					    			respuesta = strategosMedicionesService.saveMediciones(mediciones, null, getUsuarioConectado(request), new Boolean(true), new Boolean(true));
					    			
					    			if(respuesta == 10000) {
					    				
					    				indicadorInstrumento.setUltimaMedicion(valorAcumulado);
					    				indicadorInstrumento.setFechaUltimaMedicion(""+anio.toString()+"/"+mesFinal.toString());
					    				
					    				respuesta = strategosIndicadoresService.saveIndicador(indicadorInstrumento, getUsuarioConectado(request));
					    				
					    				if(respuesta == 10000) {
					    					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.calculo.log.fechafincalculo.instrumentos"));
						    				saveMessages(request, messages);
					    				}
					    						    				
					    			}else if(respuesta == 10003) {
					    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmediciones.mensaje.guardarmediciones.related"));
					    				saveMessages(request, messages);
					    			}
					    	   }
					    		
					    	}else {
					    		
					    		
					    		
					    		for(int x=mesInicial-1; x<mesFinal; x++) {
					    			
					    			Double valorAcumulado= 0.0;
					    			int indice =x+1;
					    		
					    			hayPesos = true;
					    			
					    			for(Iniciativa ini: iniciativas) {
							    		Double peso = 0.0;
							    		
							    		for (Iterator<InstrumentoIniciativa> iterIni = instrumentoIniciativas.iterator(); iter.hasNext();) {
								    		InstrumentoIniciativa instrumentoIniciativa = (InstrumentoIniciativa)iterIni.next();
								    		
								    		if(instrumentoIniciativa.getIniciativa().getIniciativaId().equals(ini.getIniciativaId())) {
								    			peso = instrumentoIniciativa.getPeso();
								    		}
								    	}
							    		
							    		if(peso != null) {
							    		
								    		Long indicadorId = ini.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento());
								    		Indicador indicadorIniciativa = (Indicador) strategosIndicadoresService.load(Indicador.class, indicadorId);
								    		
								    		SerieIndicador serieIndicadorLoad = null;			    		
								    		List<Medicion> medicionesPeriodo = strategosMedicionesService.getMedicionesPeriodo(indicadorId, SerieTiempo.getSerieReal().getSerieId().longValue(), anio, anio, indice, indice);
								    		
								    		for(Medicion med: medicionesPeriodo) {
								    			Double suma = (med.getValor() * (peso/100));
								    			valorAcumulado=valorAcumulado+suma;
								    		}
							    		}else {
							    			hayPesos = false;
							    			
							    		}
								    	
							    	}
					    			
					    			if(hayPesos){
						    			Indicador indicadorInstrumento = (Indicador) strategosIndicadoresService.load(Indicador.class, instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
						    			
						    			Medicion medicionCalculada = new Medicion(new MedicionPK(new Long(instrumento.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento())), new Integer(anio), new Integer(indice), new Long(SerieTiempo.getSerieReal().getSerieId())), valorAcumulado, new Boolean(false));		    	
						    			mediciones.add(medicionCalculada);
						    			respuesta = strategosMedicionesService.saveMediciones(mediciones, null, getUsuarioConectado(request), new Boolean(true), new Boolean(true));
						    			
						    			if(respuesta == 10000) {
						    				indicadorInstrumento.setUltimaMedicion(valorAcumulado);
						    				indicadorInstrumento.setFechaUltimaMedicion(""+anio.toString()+"/"+indice);
						    				
						    				respuesta = strategosIndicadoresService.saveIndicador(indicadorInstrumento, getUsuarioConectado(request));
						    				
						    				if(respuesta == 10000) {
						    					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.calculo.log.fechafincalculo.instrumentos"));
							    				saveMessages(request, messages);
						    				}
						    			}else if(respuesta == 10003) {
						    				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmediciones.mensaje.guardarmediciones.related"));
						    				saveMessages(request, messages);
						    			}
					    			}
					    		}
					    	}
					    	
					    	if(!hayPesos) {
					    		
						    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.visualizariniciativasinstrumento.pesos.noregistros"));
								saveMessages(request, messages);
						    	
					    	}
					    	
					    }else {
					    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.visualizariniciativasinstrumento.noregistros"));
						    saveMessages(request, messages);
					    }
					    
					}
				}
			}
			
		
	}
}
