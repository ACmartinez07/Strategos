package com.visiongc.app.strategos.web.struts.planes.indicadores.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectiva;
import com.visiongc.app.strategos.planes.model.IndicadorPerspectivaPK;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class DesasociarIndicadorPlanAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		ActionMessages messages = getMessages(request);

		Long indicadorId = new Long(request.getParameter("indicadorId"));
		Long perspectivaId = new Long(request.getParameter("perspectivaId"));
		Long planId = new Long(request.getParameter("planId"));
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("DesasociarIndicadorPlanAction.ultimoTs");
		boolean cancelar = false;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((indicadorId == null) || (indicadorId.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(indicadorId + "&" + ts))) 
			cancelar = true;

		if (cancelar)
			return getForwardBack(request, 1, true);

		StrategosPerspectivasService strategosPerspectivaService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

		IndicadorPerspectiva indicadorPerspectiva = new IndicadorPerspectiva();
		indicadorPerspectiva.setPk(new IndicadorPerspectivaPK());
		indicadorPerspectiva.getPk().setIndicadorId(indicadorId);
		indicadorPerspectiva.getPk().setPerspectivaId(perspectivaId);
		IndicadorPerspectiva indicadorAsociadoPerspectiva = (IndicadorPerspectiva)strategosPerspectivaService.load(IndicadorPerspectiva.class, indicadorPerspectiva.getPk());
		
		int resultado =0;
		
		if(indicadorAsociadoPerspectiva.getPerspectiva().getNlParIndicadorId() == null  ){
			resultado = strategosPerspectivaService.desasociarIndicador(indicadorId, planId, indicadorAsociadoPerspectiva, null, getUsuarioConectado(request));
		}else{
			resultado = strategosPerspectivaService.desasociarIndicador(indicadorAsociadoPerspectiva, null, getUsuarioConectado(request));
		}
		
		
		if (resultado != VgcReturnCode.DB_OK)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.asociarindicadorplan.error"));
		else
		{
			if (request.getSession().getAttribute("DesAsociarIndicador") == null)
				request.getSession().setAttribute("DesAsociarIndicador", "true");
		}

		strategosPerspectivaService.close();

		saveMessages(request, messages);
		
		request.getSession().setAttribute("DesasociarIndicadorPlanAction.ultimoTs", indicadorId + "&" + ts);

		return getForwardBack(request, 1, true);
	}
}