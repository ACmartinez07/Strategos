/**
 * 
 */
package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectivaPK;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryInformacionActividad;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.AsignarPesosActividadForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

/**
 * @author Kerwin
 *
 */
public class AsignarPesosActividadAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

	    String forward = mapping.getParameter();

	    if (request.getParameter("funcion") != null) 
	    {
	    	String funcion = request.getParameter("funcion");
	    	if (funcion.equals("cancelar")) 
	    	{
	    		request.setAttribute("ajaxResponse", "");
	    		return mapping.findForward("ajaxResponse");
	    	}
	    }

	    AsignarPesosActividadForm asignarPesosActividadForm = (AsignarPesosActividadForm)form;

	    ActionMessages messages = getMessages(request);

	    StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

	    String actividadId = request.getParameter("actividadId");
	    Map<String, Comparable> filtros = new HashMap<String, Comparable>();
	    
	    if ((actividadId != null) && (!actividadId.equals("")) && (!actividadId.equals("0")))
	    {
  		  String iniciativaId = request.getParameter("iniciativaId");
  		  Iniciativa iniciativa = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, new Long(iniciativaId));
  		  asignarPesosActividadForm.setPadreNombre(iniciativa.getNombre());
	      asignarPesosActividadForm.setOrganizacionNombre(iniciativa.getOrganizacion().getNombre());

  		  PryActividad pryActividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(actividadId));

	      if (pryActividad != null)
	      {
	    	  filtros.put("proyectoId", pryActividad.getProyectoId().toString());
	    	  asignarPesosActividadForm.setTipoPadre((byte) 0);
	    	  if (pryActividad.getPadreId() != null)
	    	  {
		    	filtros.put("padreId", pryActividad.getPadreId().toString());
		    	
		    	PryActividad pryActividadPadre = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(pryActividad.getPadreId()));
		    	asignarPesosActividadForm.setPadreNombre(pryActividadPadre.getNombre());
		    	asignarPesosActividadForm.setTipoPadre((byte) 1);
	    	  }
	    	  else
	    		  filtros.put("padreId", "null");
	    	  asignarPesosActividadForm.setProyectoId(new Long(pryActividad.getProyectoId()));
	    	  asignarPesosActividadForm.setIniciativaId(new Long(iniciativaId));
	    	  asignarPesosActividadForm.setOrganizacionId(iniciativa.getOrganizacionId());
	      }
	    }
	    
	    asignarPesosActividadForm.setActividadId(new Long(actividadId));
	    
	    String atributoOrden = "fila";
	    String tipoOrden = "ASC";
	    int pagina = 1;
	    PaginaLista paginaActividades = strategosPryActividadesService.getActividades(pagina, 30, atributoOrden, tipoOrden, true, filtros);

	    for (Iterator<PryActividad> iter = paginaActividades.getLista().iterator(); iter.hasNext(); ) 
	    {
	    	PryActividad actividad = iter.next();
	    	actividad.setPeso(actividad.getPryInformacionActividad().getPeso());
	    }
	    
	    paginaActividades.setTamanoSetPaginas(5);

	    request.setAttribute("asignarPesosActividad.paginaActividades", paginaActividades);

	    saveMessages(request, messages);

	    if (request.getParameter("funcion") != null) 
	    {
	    	String funcion = request.getParameter("funcion");
	    	if (funcion.equals("guardar"))
	    	{
	    		request.setAttribute("cerrarAsignarPesosActividad", "true");
	    		guardarPesosActividad(strategosPryActividadesService, asignarPesosActividadForm, request);
	    	}
	    }
	    else if (request.getParameter("funcionCierre") == null) 
	      asignarPesosActividadForm.setFuncionCierre(null);

	    strategosPryActividadesService.close();

	    return mapping.findForward(forward);
	  }

	  private void guardarPesosActividad(StrategosPryActividadesService strategosPryActividadesService, AsignarPesosActividadForm asignarPesosActividadForm, HttpServletRequest request) throws Exception
	  {
		  List<PryActividad> actividades = new ArrayList<PryActividad>();
		  Map<?, ?> nombresParametros = request.getParameterMap();
		  
		  for (Iterator<?> iter = nombresParametros.keySet().iterator(); iter.hasNext(); ) 
		  {
			  String nombre = (String)iter.next();
			  int index = nombre.indexOf("pesoActividad");
			  if (index > -1) 
			  {
				  PryActividad actividad = new PryActividad();
				  actividad.setActividadId(new Long(nombre.substring("pesoActividad".length())));
				  if ((request.getParameter(nombre) != null) && (!request.getParameter(nombre).equals(""))) 
				  {
					  actividad.setPryInformacionActividad(new PryInformacionActividad());
					  actividad.getPryInformacionActividad().setPeso(new Double(VgcFormatter.parsearNumeroFormateado(request.getParameter(nombre))));
				  }
				  actividades.add(actividad);
			  }
		  }
	    
		  strategosPryActividadesService.updatePesosActividad(actividades, getUsuarioConectado(request));
	  }
	}