/**
 * 
 */
package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.reportes.StrategosReportesService;
import com.visiongc.app.strategos.vistasdatos.StrategosVistasDatosService;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.GestionarVistasDatosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

/**
 * @author Kerwin
 *
 */
public class GestionarVistaDatosAction extends VgcAction
{
	public static final String ACTION_KEY = "GestionarVistasDatosAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrlSinParametros(url, nombre, new Integer(2));
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    super.execute(mapping, form, request, response);

	    getBarraAreas(request, "strategos").setBotonSeleccionado("vistasDatos");

	    String forward = mapping.getParameter();

	    GestionarVistasDatosForm gestionarVistasDatosForm = (GestionarVistasDatosForm)form;
	    ActionMessages messages = getMessages(request);
	    
	    request.getSession().setAttribute("alerta", new com.visiongc.framework.web.struts.alertas.actions.GestionarAlertasAction().getAlerta(getUsuarioConectado(request)));

	    String organizacionId = (String)request.getSession().getAttribute("organizacionId");
	    String atributoOrden = gestionarVistasDatosForm.getAtributoOrdenVistas();
	    String tipoOrden = gestionarVistasDatosForm.getTipoOrdenVistas();
	    int pagina = gestionarVistasDatosForm.getPaginaSeleccionadaVistas();

	    gestionarVistasDatosForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("VISTA_DATOS_VIEW"));
	    gestionarVistasDatosForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("VISTA_DATOS_EDIT"));
	    
	    if ((atributoOrden == null) || (atributoOrden.equals(""))) 
	    {
	    	atributoOrden = "nombre";
	    	gestionarVistasDatosForm.setAtributoOrdenVistas(atributoOrden);
	    }
	    if ((tipoOrden == null) || (tipoOrden.equals(""))) 
	    {
	    	tipoOrden = "ASC";
	    	gestionarVistasDatosForm.setTipoOrdenVistas(tipoOrden);
	    }

	    if (pagina < 1) 
	    	pagina = 1;

	    Map<String, Object> filtros = new HashMap<String, Object>();

	    if ((organizacionId != null) && (!organizacionId.equals(""))) 
	    	filtros.put("organizacionId", organizacionId);

	    StrategosReportesService strategosReportesService = StrategosServiceFactory.getInstance().openStrategosReportesService();
	    Usuario usuario = getUsuarioConectado(request);
	    PaginaLista paginaVistas = strategosReportesService.getReportes(pagina, 30, atributoOrden, tipoOrden, true, filtros, usuario.getUsuarioId());
	    strategosReportesService.close();

	    paginaVistas.setTamanoSetPaginas(5);
	    request.getSession().setAttribute("paginaVistas", paginaVistas);

	    saveMessages(request, messages);

	    return mapping.findForward(forward);
	}
}