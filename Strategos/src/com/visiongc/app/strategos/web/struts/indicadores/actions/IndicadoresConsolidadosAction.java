/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Formula;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.InsumoFormula;
import com.visiongc.app.strategos.indicadores.model.InsumoFormulaPK;
import com.visiongc.app.strategos.indicadores.model.SerieIndicador;
import com.visiongc.app.strategos.indicadores.model.SerieIndicadorPK;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.planes.model.Plan;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.app.strategos.web.struts.indicadores.forms.IndicadorConsolidadoForm;
import com.visiongc.app.strategos.web.struts.indicadores.forms.IndicadorConsolidadoForm.ConsolidarStatus;
import com.visiongc.app.strategos.web.struts.indicadores.validators.IndicadorValidator;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class IndicadoresConsolidadosAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();
		ActionMessages messages = getMessages(request);
		
		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
	    String ts = request.getParameter("ts");

		if (cancelar)
			return getForwardBack(request, 1, true);

	    if (!((ts == null) || (ts.equals(""))))
	    	forward = "finalizarCopiarClase";

	    IndicadorConsolidadoForm indicadorConsolidadoForm = (IndicadorConsolidadoForm)form;
	    Long organizacionId = new Long(0L);
	    if (request.getParameter("organizacionId") != null)
	    	organizacionId = new Long(request.getParameter("organizacionId"));
	    else
	    	organizacionId = indicadorConsolidadoForm.getOrganizacionId();
	    
	    indicadorConsolidadoForm.setOrganizacionId(organizacionId);
	    indicadorConsolidadoForm.setStatus(ConsolidarStatus.getConsolidarStatusLoad());
	    
		if (request.getParameter("funcion") != null)
		{
	    	String funcion = request.getParameter("funcion");
	    	if (funcion.equals("Consolidar")) 
	    	{
	    		indicadorConsolidadoForm.setStatus(Consolidar(request, indicadorConsolidadoForm));
	    		
	    		if (indicadorConsolidadoForm.getStatus().byteValue() == ConsolidarStatus.getConsolidarStatusNoSuccess().byteValue())
	    		{
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.consolidado.fin.reporte.archivo.error"));
	    			saveMessages(request, messages);
	    		}
	    		else if (indicadorConsolidadoForm.getStatus().byteValue() == ConsolidarStatus.getConsolidarStatusSuccess().byteValue() && (indicadorConsolidadoForm.getLogErrores() || indicadorConsolidadoForm.getLogIndicadores()))
	    			indicadorConsolidadoForm.setStatus(ConsolidarStatus.getConsolidarStatusReporte());
	    		else
	    		{
	    			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.asistente.consolidado.fin.reporte.archivo.exito.reporte"));
	    			saveMessages(request, messages);
	    		}
	    	}
		}

	    return mapping.findForward(forward);
	}
	
	private byte Consolidar(HttpServletRequest request, IndicadorConsolidadoForm indicadorConsolidadoForm)
	{
		String[] clases = request.getParameter("clases").split(indicadorConsolidadoForm.getSeparadorClases());
		Long claseId = new Long(request.getParameter("claseId"));
		Long organizacionId = new Long(request.getParameter("organizacionId"));
		String parametroClases = "";
		int respuesta = 10000;

		StringBuffer log = new StringBuffer();
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");
		
	    log.append(messageResources.getResource("jsp.asistente.consolidado.log.titulo") + "\n");

	    Calendar ahora = Calendar.getInstance();
	    String[] argsReemplazo = new String[2];
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");
	    log.append(messageResources.getResource("jsp.asistente.consolidado.log.fechainicio", argsReemplazo) + "\n\n");

		List<String> campos = new ArrayList<String>();
		campos.add("nombre");
		campos.add("frecuencia");
		
		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		
		String orden = "nombre";
		String tipoOrden = "ASC";

		Map<String, Object> filtros = new HashMap<String, Object>();

		for (int i = 0; i < clases.length; i++)
			parametroClases = parametroClases + clases[i] + ", ";
		
		if (!parametroClases.equals(""))
			parametroClases = parametroClases.substring(0, parametroClases.length() - 2);
		
		filtros.put("clases", parametroClases);
		
		List<Indicador> indicadores = strategosIndicadoresService.getIndicadoresNombreFrecuencia(orden, tipoOrden, campos, true, filtros);
		
		List<Long> ids; 
		Indicador indicador = new Indicador();
		Indicador indicadorConsolidado = new Indicador();
    	for (Iterator<?> iter = indicadores.iterator(); iter.hasNext(); ) 
        {
    		indicador = (Indicador)iter.next();
    		
    		filtros = new HashMap<String, Object>();
    		filtros.put("nombre", indicador.getNombre());
    		filtros.put("clases", parametroClases);
    		filtros.put("frecuencia", indicador.getFrecuencia());
    		
    		ids = strategosIndicadoresService.getIndicadoresIds(filtros);
    		
    		if (ids.size() > 0)
    		{
    			Indicador indicadorFuente = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(ids.get(0)));
    			
	    		indicadorConsolidado = new Indicador();
	    		indicadorConsolidado.setIndicadorId(new Long(0L));
	    		indicadorConsolidado.setClaseId(claseId);
	    		indicadorConsolidado.setOrganizacionId(organizacionId);
	    		indicadorConsolidado.setEscalaCualitativa(new ArrayList<Object>());
	    		indicadorConsolidado.setSeriesIndicador(new HashSet<Object>());
	    		indicadorConsolidado.setTipoFuncion(TipoFuncionIndicador.getTipoFuncionNormal());
	    		indicadorConsolidado.setNombre(indicadorFuente.getNombre());
	    		indicadorConsolidado.setNombreCorto(indicadorFuente.getNombre());
	    		indicadorConsolidado.setNaturaleza(Naturaleza.getNaturalezaFormula());
	    		indicadorConsolidado.setFrecuencia(indicadorFuente.getFrecuencia());
	    		indicadorConsolidado.setTipoSumaMedicion(indicadorFuente.getTipoSumaMedicion());
	    		indicadorConsolidado.setUnidadId(indicadorFuente.getUnidadId());
	    		indicadorConsolidado.setUnidad(indicadorFuente.getUnidad());
	    		indicadorConsolidado.setPrioridad(indicadorFuente.getPrioridad());
	    		indicadorConsolidado.setMostrarEnArbol(indicadorFuente.getMostrarEnArbol());
	    		indicadorConsolidado.setNumeroDecimales(indicadorFuente.getNumeroDecimales());
	    		indicadorConsolidado.setDescripcion(indicadorFuente.getDescripcion());
	    		indicadorConsolidado.setComportamiento(indicadorFuente.getComportamiento());
	    		indicadorConsolidado.setFuente(indicadorFuente.getFuente());
	    		indicadorConsolidado.setIndicadorAsociadoTipo(null);
	    		indicadorConsolidado.setIndicadorAsociadoId(null);
	    		indicadorConsolidado.setIndicadorAsociadoRevision(null);
	    		indicadorConsolidado.setResponsableFijarMetaId(null);
	    		indicadorConsolidado.setResponsableLograrMetaId(null);
	    		indicadorConsolidado.setResponsableSeguimientoId(null);
	    		indicadorConsolidado.setResponsableCargarMetaId(null);
	    		indicadorConsolidado.setResponsableCargarEjecutadoId(null);
	    		indicadorConsolidado.setCaracteristica(indicadorFuente.getCaracteristica());
	    		indicadorConsolidado.setCorte(indicadorFuente.getCorte());
	    		indicadorConsolidado.setTipoCargaMedicion(indicadorFuente.getTipoCargaMedicion());
	    		indicadorConsolidado.setGuia(indicadorFuente.getGuia());
	    		indicadorConsolidado.setValorInicialCero(indicadorFuente.getValorInicialCero());
	    		indicadorConsolidado.setParametroSuperiorValorFijo(indicadorFuente.getParametroSuperiorValorFijo());
	    		indicadorConsolidado.setParametroSuperiorIndicadorId(indicadorFuente.getParametroSuperiorIndicadorId());
	    		indicadorConsolidado.setParametroInferiorValorFijo(indicadorFuente.getParametroInferiorValorFijo());
	    		indicadorConsolidado.setParametroInferiorIndicadorId(indicadorFuente.getParametroInferiorIndicadorId());
	    		indicadorConsolidado.setParametroSuperiorValorFijo(indicadorFuente.getParametroSuperiorValorFijo());
	    		indicadorConsolidado.setParametroSuperiorIndicadorId(indicadorFuente.getParametroSuperiorIndicadorId());
	    		indicadorConsolidado.setAlertaTipoZonaAmarilla(indicadorFuente.getAlertaTipoZonaAmarilla());
	    		indicadorConsolidado.setAlertaTipoZonaVerde(indicadorFuente.getAlertaTipoZonaVerde());
	    		indicadorConsolidado.setAlertaMetaZonaVerde(indicadorFuente.getAlertaMetaZonaVerde());
	    		indicadorConsolidado.setAlertaMetaZonaAmarilla(indicadorFuente.getAlertaMetaZonaAmarilla());
	    		indicadorConsolidado.setAlertaIndicadorIdZonaVerde(indicadorFuente.getAlertaIndicadorIdZonaVerde());
	    		indicadorConsolidado.setAlertaIndicadorIdZonaAmarilla(indicadorFuente.getAlertaIndicadorIdZonaAmarilla());
	    		indicadorConsolidado.setAlertaValorVariableZonaAmarilla(indicadorFuente.getAlertaValorVariableZonaAmarilla());
	    		indicadorConsolidado.setAlertaValorVariableZonaVerde(indicadorFuente.getAlertaValorVariableZonaVerde());

	    		SerieIndicador serieReal = null;
	    		Set<SerieIndicador> seriesIndicador = indicadorFuente.getSeriesIndicador();
	    		indicadorConsolidado.getSeriesIndicador().clear();

	    		for (Iterator<SerieIndicador> i = seriesIndicador.iterator(); i.hasNext(); ) 
	    		{
	    			SerieIndicador serie = i.next();
	              	SerieIndicador serieIndicador = new SerieIndicador();
	              	serieIndicador.setIndicador(indicadorConsolidado);
	              	serieIndicador.setPk(new SerieIndicadorPK());
	              	serieIndicador.getPk().setSerieId(serie.getPk().getSerieId());
	              	serieIndicador.getPk().setIndicadorId(indicadorConsolidado.getIndicadorId());
	              	serieIndicador.setFormulas(new HashSet<Object>());
	              	if (serie.getPk().getSerieId().byteValue() == SerieTiempo.getSerieReal().getSerieId().byteValue())
	              	{
	    	            serieIndicador.setNaturaleza(indicadorFuente.getNaturaleza());
	              		serieReal = serieIndicador;
	              	}
	              	else 
	    	            serieIndicador.setNaturaleza(Naturaleza.getNaturalezaSimple());

	              	indicadorConsolidado.getSeriesIndicador().add(serieIndicador);
	    		}
	    		
	        	indicadorConsolidado.setUrl(indicadorFuente.getUrl());
	        	indicadorConsolidado.setPlanId(null);
	        	indicadorConsolidado.setPerspectivaId(null);
	
	        	indicadorConsolidado.setCodigoEnlace(null);
	        	indicadorConsolidado.setEnlaceParcial(null);
	        	indicadorConsolidado.getEscalaCualitativa().clear();
	
	            Formula formulaIndicador = new Formula();
	            formulaIndicador.setInsumos(new HashSet<Object>());
	            formulaIndicador.setExpresion(IndicadorValidator.ConstruirFormula(ids, 0, "+"));
	       	
	            Long insumo;
	        	for (Iterator<?> id = ids.iterator(); id.hasNext(); ) 
	            {
	        		insumo = (Long)id.next();

	        		InsumoFormula insumoFormula = new InsumoFormula();
	                insumoFormula.setPk(new InsumoFormulaPK());
	                insumoFormula.getPk().setPadreId(indicadorConsolidado.getIndicadorId());
	                insumoFormula.getPk().setSerieId(new Long("0"));
	                insumoFormula.getPk().setIndicadorId(new Long(insumo));
	                insumoFormula.getPk().setInsumoSerieId(new Long("0"));
	                formulaIndicador.getInsumos().add(insumoFormula);
	            }
	        	serieReal.getFormulas().add(formulaIndicador);
	
	            respuesta = strategosIndicadoresService.saveIndicador(indicadorConsolidado, getUsuarioConectado(request));
	            if (respuesta != 10000)
	            {
		            log.append(messageResources.getResource("action.guardarregistro.duplicado") + "\n");
	            	break;
	            }
	            else
	            {
	        	    String[] args = new String[1];
	        	    args[0] = indicadorFuente.getNombre();
	            	log.append(messageResources.getResource("jsp.asistente.consolidado.log.indicador.success", args) + "\n");
	            }
    		}
        }
		
		strategosIndicadoresService.close();

	    ahora = Calendar.getInstance();
	    argsReemplazo[0] = VgcFormatter.formatearFecha(ahora.getTime(), "dd/MM/yyyy");
	    argsReemplazo[1] = VgcFormatter.formatearFecha(ahora.getTime(), "hh:mm:ss a");

	    log.append("\n\n");
	    log.append(messageResources.getResource("jsp.asistente.consolidado.log.fechafin", argsReemplazo) + "\n\n");
	    if (respuesta == 10000)
	    	log.append(messageResources.getResource("jsp.asistente.consolidado.log.success") + "\n\n");
	    else
	    	log.append(messageResources.getResource("jsp.asistente.consolidado.log.nosuccess") + "\n\n");
	    request.getSession().setAttribute("verArchivoLog", log);
		
		if (respuesta == 10000)
			return ConsolidarStatus.getConsolidarStatusSuccess();
		else
			return ConsolidarStatus.getConsolidarStatusNoSuccess();
	}
}
