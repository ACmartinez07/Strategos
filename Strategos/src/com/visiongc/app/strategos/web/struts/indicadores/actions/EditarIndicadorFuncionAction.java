package com.visiongc.app.strategos.web.struts.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class EditarIndicadorFuncionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		if (request.getParameter("funcion") != null) 
		{
			String funcion = request.getParameter("funcion");
			if (funcion.equals("getInformacionIndicadorBase")) 
				getInformacionIndicadorBase(request);
		}

		return mapping.findForward("ajaxResponse");
	}

	private void getInformacionIndicadorBase(HttpServletRequest request)
	{
		String indicadorId = request.getParameter("indicadorId");

		int index = indicadorId.indexOf("!@!");
		if (index > -1) 
			indicadorId = indicadorId.substring(0, index);

		StrategosIndicadoresService indicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

		Indicador indicador = (Indicador)indicadoresService.load(Indicador.class, new Long(indicadorId));
		
		String informacion = "[frecuenciaId:" + indicador.getFrecuencia().toString() + "][frecuenciaNombre:" + indicador.getFrecuenciaNombre() + "]";
		if (indicador.getUnidad() != null) 
			informacion = informacion + "[unidadId:" + indicador.getUnidadId() + "][unidadNombre:" + indicador.getUnidad().getNombre() + "]";

		indicadoresService.close();

		request.setAttribute("ajaxResponse", informacion);
	}
}