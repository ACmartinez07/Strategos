/**
 * 
 */
package com.visiongc.app.strategos.web.struts.indicadores.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.indicadores.forms.ImportarSeleccionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.importacion.ImportacionService;
import com.visiongc.framework.model.Importacion;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class ImportarSeleccionAction extends VgcAction
{
	  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	  {
	  }

	  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	  {
		  super.execute(mapping, form, request, response);

		  String forward = mapping.getParameter();

		  ImportarSeleccionForm importarSeleccionForm = (ImportarSeleccionForm)form;

		  if (importarSeleccionForm.getAtributoOrden() == null) 
			  importarSeleccionForm.setAtributoOrden("nombre");
		    
		  if (importarSeleccionForm.getTipoOrden() == null) 
			  importarSeleccionForm.setTipoOrden("ASC");

		  Map<String, Object> filtros = new HashMap<String, Object>();
		  filtros.put("usuarioId", ((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId().toString());
		  
		  ImportacionService importarService = FrameworkServiceFactory.getInstance().openImportacionService();
		  PaginaLista paginaRegistros = importarService.getImportaciones(0, 0, importarSeleccionForm.getAtributoOrden(), importarSeleccionForm.getTipoOrden(), true, filtros);
		  importarService.close();
		  
		  request.setAttribute("paginaRegistros", paginaRegistros);

		  if (paginaRegistros.getLista() != null && paginaRegistros.getLista().size() > 0) 
		  {
			  Importacion importacion = (Importacion)paginaRegistros.getLista().get(0);
		      importarSeleccionForm.setSeleccionados(importacion.getId().toString());
		      importarSeleccionForm.setValoresSeleccionados(importacion.getNombre());
		  } 
		  else 
			  importarSeleccionForm.setSeleccionados(null);

		  StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
		  OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long((String)request.getSession().getAttribute("organizacionId")));
		  importarSeleccionForm.setRutaCompletaOrganizacion(organizacionStrategos.getNombre());
		  strategosOrganizacionesService.close();
		  
		  return mapping.findForward(forward);
	}
}