/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.actions;

import java.util.ArrayList;
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
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativa;
import com.visiongc.app.strategos.portafolios.model.PortafolioIniciativaPK;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.portafolios.forms.EditarPortafolioForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class AsignarPesosIniciativasPortafolioAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

	    String forward = mapping.getParameter();

	    EditarPortafolioForm editarPortafolioForm = (EditarPortafolioForm)form;

	    StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();

	    Long id = (request.getParameter("id") != null && request.getParameter("id") != "" ? Long.parseLong(request.getParameter("id")) : 0L);
	    Portafolio portafolio = (Portafolio)strategosPortafoliosService.load(Portafolio.class, new Long(id));
	    PaginaLista paginaIniciativas = new PaginaLista();

	    if (portafolio != null)	    
	    {
		    editarPortafolioForm.clear();
		    editarPortafolioForm.setId(id);
		    
		    if (request.getParameter("funcion") != null) 
		    {
		    	String funcion = request.getParameter("funcion");
		    	if (funcion.equals("guardar"))
		    	{
		    		int respuesta = VgcReturnCode.DB_OK;
		    		respuesta = guardarPesos(strategosPortafoliosService, editarPortafolioForm, request);
		    	    if (respuesta == VgcReturnCode.DB_OK)
		    			editarPortafolioForm.setStatus(StatusUtil.getStatusSuccess());
		    	    else
		    	    	editarPortafolioForm.setStatus(StatusUtil.getStatusInvalido());
		    	}
		    }
	
		    List<PortafolioIniciativa> portafolioIniciativas = strategosPortafoliosService.getIniciativasPortafolio(portafolio.getId());
	
	    	for (Iterator<PortafolioIniciativa> iter = portafolioIniciativas.iterator(); iter.hasNext(); ) 
	    	{
	    		PortafolioIniciativa portafolioIniciativa = (PortafolioIniciativa)iter.next();
	    		Iniciativa iniciativa = (Iniciativa) strategosPortafoliosService.load(Iniciativa.class, portafolioIniciativa.getIniciativa().getIniciativaId());
	    		portafolioIniciativa.setIniciativa(iniciativa);
	    	}
	    	
	    	OrganizacionStrategos organizacion = (OrganizacionStrategos) strategosPortafoliosService.load(OrganizacionStrategos.class, portafolio.getOrganizacionId());
	    	
		    editarPortafolioForm.setNombre(portafolio.getNombre());
		    editarPortafolioForm.setOrganizacion(organizacion);
		    editarPortafolioForm.setPortafolio(portafolio);
			
		    paginaIniciativas = new PaginaLista();
		    paginaIniciativas.setLista(portafolioIniciativas);
		    paginaIniciativas.setTamanoSetPaginas(5);
	    }
	    else
	    {
	    	if (editarPortafolioForm != null)
	    	{
	    		editarPortafolioForm.clear();
	    		editarPortafolioForm.setStatus(StatusUtil.getStatusInvalido());
	    	}
	    	ActionMessages messages = getMessages(request);
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.calcularregistro.noencontrado"));
	    	saveMessages(request, messages);
	    	
		    paginaIniciativas.setLista(new ArrayList<PortafolioIniciativa>());
		    paginaIniciativas.setTamanoSetPaginas(5);
	    }

	    request.setAttribute("asignarPesosIniciativasPortafolio.paginaIniciativas", paginaIniciativas);
	    
	    strategosPortafoliosService.close();

	    return mapping.findForward(forward);
	}

	private int guardarPesos(StrategosPortafoliosService strategosPortafoliosService, EditarPortafolioForm editarPortafolioForm, HttpServletRequest request) throws Exception
	{
		  List<PortafolioIniciativa> portafolioIniciativas = new ArrayList<PortafolioIniciativa>();
		  Map<?, ?> nombresParametros = request.getParameterMap();
		  
		  for (Iterator<?> iter = nombresParametros.keySet().iterator(); iter.hasNext(); ) 
		  {
			  String nombre = (String)iter.next();
			  int index = nombre.indexOf("pesoIniciativa");
			  if (index > -1) 
			  {
				  PortafolioIniciativa portafolioIniciativa = new PortafolioIniciativa();
				  PortafolioIniciativaPK pk = new PortafolioIniciativaPK(); 
				  pk.setIniciativaId(new Long(nombre.substring("pesoIniciativa".length())));
				  pk.setPortafolioId(editarPortafolioForm.getId());
				  portafolioIniciativa.setPk(pk);
				  if ((request.getParameter(nombre) != null) && (!request.getParameter(nombre).equals("")))
					  portafolioIniciativa.setPeso(new Double(VgcFormatter.parsearNumeroFormateado(request.getParameter(nombre))));
				  portafolioIniciativas.add(portafolioIniciativa);
			  }
		  }
	    
		  return strategosPortafoliosService.saveIniciativasPortafolio(portafolioIniciativas, getUsuarioConectado(request));
	}
}