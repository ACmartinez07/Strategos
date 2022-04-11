/**
 * 
 */
package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.organizaciones.forms.EditarOrganizacionForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Kerwin
 *
 */
public class CodigoEnlaceOrganizacionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();
		
	    EditarOrganizacionForm editarOrganizacionForm = (EditarOrganizacionForm)form;
	    StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

	    int respuesta = 10000;
	    
	    try
	    {
	    	if (editarOrganizacionForm.getSeleccion() == 1)
	    	{
	    		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();
	    		OrganizacionStrategos organizacionRoot = new OrganizacionStrategos();
	    		
	    		organizacionRoot = (OrganizacionStrategos)arbolesService.getNodoArbolRaiz(organizacionRoot);
	    		respuesta = saveTodoElArbol(strategosOrganizacionesService, organizacionRoot, editarOrganizacionForm);
	    	}
	    	else
	    	{
	    	    OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, editarOrganizacionForm.getOrganizacionId());
	    	    
		    	if (organizacionStrategos != null && editarOrganizacionForm.getSeleccion() == 0)
		    	{
		    		String codigoEnlaceParcial = "";
		    		if (organizacionStrategos.getEnlaceParcial() != null && !organizacionStrategos.getEnlaceParcial().equals(""))
		    			codigoEnlaceParcial = organizacionStrategos.getEnlaceParcial();
		    		if (editarOrganizacionForm.getConcatenarCodigos() != null && editarOrganizacionForm.getConcatenarCodigos())
		    			codigoEnlaceParcial = buscarEnlaceParcialPadre(strategosOrganizacionesService, organizacionStrategos, codigoEnlaceParcial); 
		    		
		    		respuesta = saveIndicadores(organizacionStrategos.getOrganizacionId(), codigoEnlaceParcial, strategosOrganizacionesService);
		    	}
	    	}
		} 
    	catch (Throwable e) 
		{
			e.printStackTrace();
		}
	    
	    if (respuesta == 10000)
	    	forward = "finalizarCodigoEnlace";
	    
	    return mapping.findForward(forward);
	}
	
	private int saveIndicadores(Long organizacionId, String codigoEnlaceParcialOrganizacion, StrategosOrganizacionesService strategosOrganizacionesService) throws Throwable
	{
		int respuesta = 10000;

		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put("organizacionId", organizacionId.toString());
		filtros.put("codigoEnlace", "IS NULL");
		filtros.put("enlaceParcial", "IS NOT NULL");

		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
		List<Indicador> indicadores = strategosIndicadoresService.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, false).getLista();
        for (Iterator<?> i = indicadores.iterator(); i.hasNext();) 
        {
        	Indicador indicador = (Indicador)i.next();
        	if (indicador.getEnlaceParcial() != null);
        	{
        		String codigoEnlaceParcialClase = buscarEnlaceParcialClase(indicador.getClaseId(), strategosIndicadoresService);
        		indicador.setCodigoEnlace(codigoEnlaceParcialOrganizacion + codigoEnlaceParcialClase + indicador.getEnlaceParcial());
        	}
        	
        	respuesta = strategosIndicadoresService.saveCodigoEnlace(indicador.getIndicadorId(), organizacionId, indicador.getCodigoEnlace());
        }

		strategosIndicadoresService.close();
		
		return respuesta;
	}
	
	private String buscarEnlaceParcialClase(Long claseId, StrategosIndicadoresService strategosIndicadoresService)
	{
		String codigoEnlaceParcial = "";
		
		ClaseIndicadores clase = (ClaseIndicadores)strategosIndicadoresService.load(ClaseIndicadores.class, claseId);
		if (clase.getEnlaceParcial() != null)
			codigoEnlaceParcial = clase.getEnlaceParcial();
		if (clase.getPadreId() != null)
			codigoEnlaceParcial = buscarEnlaceParcialClase(clase.getPadreId(), strategosIndicadoresService) + codigoEnlaceParcial;
		
		return codigoEnlaceParcial;
	}

	private int saveTodoElArbol(StrategosOrganizacionesService strategosOrganizacionesService, OrganizacionStrategos organizacionStrategos, EditarOrganizacionForm editarOrganizacionForm) throws Throwable
	{
		int respuesta = 10000;

	    organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, organizacionStrategos.getOrganizacionId());
	    
    	if (organizacionStrategos != null && organizacionStrategos.getEnlaceParcial() != null && !organizacionStrategos.getEnlaceParcial().equals(""))
    	{
    		String codigoEnlaceParcial = organizacionStrategos.getEnlaceParcial();
    		if (editarOrganizacionForm.getConcatenarCodigos() != null && editarOrganizacionForm.getConcatenarCodigos())
    			codigoEnlaceParcial = buscarEnlaceParcialPadre(strategosOrganizacionesService, organizacionStrategos, codigoEnlaceParcial); 
    		
    		respuesta = saveIndicadores(organizacionStrategos.getOrganizacionId(), codigoEnlaceParcial, strategosOrganizacionesService);
    	}

        for (Iterator<?> i = organizacionStrategos.getHijos().iterator(); i.hasNext(); ) 
        {
        	OrganizacionStrategos hijo = (OrganizacionStrategos)i.next();
        	respuesta = saveTodoElArbol(strategosOrganizacionesService, hijo, editarOrganizacionForm);
        }
    	
		return respuesta;
	}
	
	private String buscarEnlaceParcialPadre(StrategosOrganizacionesService strategosOrganizacionesService, OrganizacionStrategos organizacionStrategos, String codigoEnlaceParcial)
	{
		if ((organizacionStrategos.getPadreId() != null) && (!organizacionStrategos.getPadreId().equals(Long.valueOf("0"))))
		{
			OrganizacionStrategos organizacionStrategosPadre = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, organizacionStrategos.getPadreId());		
			if (organizacionStrategosPadre != null && organizacionStrategosPadre.getEnlaceParcial() != null && !organizacionStrategosPadre.getEnlaceParcial().equals(""))
			{
				codigoEnlaceParcial = organizacionStrategosPadre.getEnlaceParcial() + codigoEnlaceParcial;
				codigoEnlaceParcial = buscarEnlaceParcialPadre(strategosOrganizacionesService, organizacionStrategosPadre, codigoEnlaceParcial);
			}
		}
		
		return codigoEnlaceParcial;
	}
}
