package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.ClaseIndicadores;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.web.struts.indicadores.forms.SeleccionarIndicadoresForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class SeleccionarIndicadoresAction extends VgcAction
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
			if (funcion.equals("getRutaCompletaIndicadoresSeleccionados")) 
			{
				getRutaCompletaIndicadoresSeleccionados(request);
				return mapping.findForward("ajaxResponse");
			}
		}

		String actualizar = request.getParameter("actualizar");
		if ((actualizar != null) && (actualizar.equalsIgnoreCase("true")))
			return mapping.findForward(forward);

		SeleccionarIndicadoresForm seleccionarIndicadoresForm = (SeleccionarIndicadoresForm)form;

		seleccionarIndicadoresForm.clear();

		request.getSession().removeAttribute("seleccionarIndicadoresArbolClasesBean");
		request.getSession().removeAttribute("seleccionarIndicadoresArbolIniciativasBean");
		request.getSession().removeAttribute("seleccionarIndicadoresArbolPlanesBean");

		seleccionarIndicadoresForm.setNombreForma(request.getParameter("nombreForma"));
		seleccionarIndicadoresForm.setNombreCampoOculto(request.getParameter("nombreCampoOculto"));
		seleccionarIndicadoresForm.setNombreCampoValor(request.getParameter("nombreCampoValor"));
		seleccionarIndicadoresForm.setNombreCampoRutasCompletas(request.getParameter("nombreCampoRutasCompletas"));
		seleccionarIndicadoresForm.setSeleccionados(request.getParameter("seleccionados"));
		seleccionarIndicadoresForm.setFuncionCierre(request.getParameter("funcionCierre"));
		seleccionarIndicadoresForm.setSeleccionadosPlanId(request.getParameter("planesIds"));
		seleccionarIndicadoresForm.setAgregarSerieMeta(request.getParameter("agregarSerieMeta") != null && request.getParameter("agregarSerieMeta") != "" ? Boolean.parseBoolean(request.getParameter("agregarSerieMeta")) : false);

		String permitirCambiarOrganizacion = request.getParameter("permitirCambiarOrganizacion");
		String organizacionId = request.getParameter("organizacionId");
		String permitirCambiarClase = request.getParameter("permitirCambiarClase");
		String claseId = request.getParameter("claseId");
		String frecuencia = request.getParameter("frecuencia");
		String mostrarSeriesTiempo = request.getParameter("mostrarSeriesTiempo");
		String frecuenciasContenidas = request.getParameter("frecuenciasContenidas");
		String seleccionMultiple = request.getParameter("seleccionMultiple");
		String permitirCualitativos = request.getParameter("permitirCualitativos");
		String indicadorId = request.getParameter("indicadorId");
		String permitirPlanes = request.getParameter("permitirPlanes");

		if ((permitirCambiarClase != null) && (permitirCambiarClase.equalsIgnoreCase("true"))) 
		{
			seleccionarIndicadoresForm.setPermitirCambiarClase(new Boolean(true));
			if ((permitirCambiarOrganizacion != null) && (permitirCambiarOrganizacion.equalsIgnoreCase("true"))) 
				seleccionarIndicadoresForm.setPermitirCambiarOrganizacion(new Boolean(true));
		}
		if ((permitirPlanes != null) && (permitirPlanes.equalsIgnoreCase("false"))) 
			seleccionarIndicadoresForm.setPermitirPlanes(new Boolean(false));
		else
			seleccionarIndicadoresForm.setPermitirPlanes(new Boolean(true));
		if ((claseId != null) && (!claseId.equals("")) && (!claseId.equals("0"))) 
		{
			StrategosIndicadoresService indicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			ClaseIndicadores claseIndicadores = (ClaseIndicadores)indicadoresService.load(ClaseIndicadores.class, new Long(claseId));
			if (claseIndicadores != null) 
			{
				seleccionarIndicadoresForm.setClaseSeleccionadaId(claseIndicadores.getClaseId());
				seleccionarIndicadoresForm.setOrganizacionSeleccionadaId(claseIndicadores.getOrganizacionId());
			}
			indicadoresService.close();
		}
		if (seleccionarIndicadoresForm.getClaseSeleccionadaId() == null) 
		{
			if ((organizacionId != null) && (!organizacionId.equals("")) && (!organizacionId.equals("0")))
				seleccionarIndicadoresForm.setOrganizacionSeleccionadaId(new Long(organizacionId));
			else 
				seleccionarIndicadoresForm.setOrganizacionSeleccionadaId(new Long((String)request.getSession().getAttribute("organizacionId")));
		}
		if ((indicadorId != null) && (!indicadorId.equals("")) && (!indicadorId.equals("0")))
			seleccionarIndicadoresForm.setIndicadorId(new Long(indicadorId));
		if ((frecuencia != null) && (!frecuencia.equals(""))) 
			seleccionarIndicadoresForm.setFrecuenciaSeleccionada(new Byte(frecuencia));
		if ((frecuenciasContenidas != null) && (!frecuenciasContenidas.equals(""))) 
			seleccionarIndicadoresForm.setFrecuenciasContenidasSeleccionada(new Byte(frecuenciasContenidas));
		if ((mostrarSeriesTiempo != null) && (mostrarSeriesTiempo.equalsIgnoreCase("true"))) 
			seleccionarIndicadoresForm.setMostrarSeriesTiempo(new Boolean(true));
		if ((seleccionMultiple != null) && (seleccionMultiple.equalsIgnoreCase("true"))) 
			seleccionarIndicadoresForm.setSeleccionMultiple(new Boolean(true));
		if ((permitirCualitativos != null) && (permitirCualitativos.equalsIgnoreCase("true"))) 
			seleccionarIndicadoresForm.setPermitirCualitativos(new Boolean(true));
		if (seleccionarIndicadoresForm.getFuncionCierre() != null) 
		{
			if (!seleccionarIndicadoresForm.getFuncionCierre().equals("")) 
			{
				if (seleccionarIndicadoresForm.getFuncionCierre().indexOf(";") < 0)
					seleccionarIndicadoresForm.setFuncionCierre(seleccionarIndicadoresForm.getFuncionCierre() + ";");
			}
			else 
				seleccionarIndicadoresForm.setFuncionCierre(null);
		}

		return mapping.findForward(forward);
	}

	private void getRutaCompletaIndicadoresSeleccionados(HttpServletRequest request)
	{
		String seleccionados = request.getParameter("seleccionados");
		String rutasCompletasIndicadoresSeleccionados = "";
		String[] arregloIndicadoresSeleccionados = seleccionados.split("!;!");

		StrategosIndicadoresService indicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		for (int i = 0; i < arregloIndicadoresSeleccionados.length; i++) 
		{
			String[] partes = arregloIndicadoresSeleccionados[i].split("!@!");
			String seleccionadoId = partes[0];
			String rutaCompletaIndicadorSeleccionado = "";
			Indicador indicador = (Indicador) indicadoresService.load(Indicador.class, (new Long(seleccionadoId)));
			if (indicador != null) 
				rutaCompletaIndicadorSeleccionado = indicadoresService.getRutaCompletaIndicador(indicador, "!#!");
			else 
				rutaCompletaIndicadorSeleccionado = "!ELIMINADO!";

			rutasCompletasIndicadoresSeleccionados = rutasCompletasIndicadoresSeleccionados + "!;!" + rutaCompletaIndicadorSeleccionado;
		}
    
		rutasCompletasIndicadoresSeleccionados = rutasCompletasIndicadoresSeleccionados.substring("!;!".length());
		indicadoresService.close();
		request.setAttribute("ajaxResponse", rutasCompletasIndicadoresSeleccionados);
	}
}