package com.visiongc.app.strategos.web.struts.planes.metas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosMetasService;
import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.MetaPK;
import com.visiongc.app.strategos.planes.model.util.MetaAnualParciales;
import com.visiongc.app.strategos.planes.model.util.MetasIndicador;
import com.visiongc.app.strategos.planes.model.util.TipoMeta;
import com.visiongc.app.strategos.web.struts.planes.metas.forms.EditarMetasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
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

public final class GuardarMetasAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ActionMessages messages = getMessages(request);

		EditarMetasForm editarMetasForm = (EditarMetasForm)form;

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarMetasAction.ultimoTs");

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosMetasService strategosMetasService = StrategosServiceFactory.getInstance().openStrategosMetasService();

		if (cancelar)
		{
			destruirPoolLocksUsoEdicion(request, strategosMetasService);
			
			strategosMetasService.close();
      
			request.getSession().removeAttribute("editarMetasForm");
			request.getSession().removeAttribute("editarMetasParcialesForm");

			return getForwardBack(request, 1, true);
		}

		List<Meta> metasEditadas = new ArrayList<Meta>();
		Map<?, ?> mapaParametros = request.getParameterMap();
		int respuesta = 10000;
		
		for (Iterator<?> iter = mapaParametros.keySet().iterator(); iter.hasNext(); )
		{
			String nombreParametro = (String)iter.next();

			if (nombreParametro.indexOf("valorIndicadorId") == 0) 
			{
				String valorNuevo = ((String[])mapaParametros.get(nombreParametro))[0];
				String valorViejo = ((String[])mapaParametros.get("valorAnterior" + nombreParametro.substring(5)))[0];
				if (!valorNuevo.equals(valorViejo)) 
				{
					int indice1 = 16;
					int indice2 = nombreParametro.indexOf("serieId");
					String indicadorId = nombreParametro.substring(indice1, indice2);
					indice1 = indice2 + 7;
					indice2 = nombreParametro.indexOf("periodo");
					String serieId = nombreParametro.substring(indice1, indice2);
					indice1 = indice2 + 7;
					indice2 = nombreParametro.indexOf("ano");
					String periodo = nombreParametro.substring(indice1, indice2);
					String ano = nombreParametro.substring(indice2 + 3);

					Double valor = null;
					if (!valorNuevo.equals("")) 
						valor = new Double(VgcFormatter.parsearNumeroFormateado(valorNuevo));

					Meta metaEditada = new Meta(new MetaPK(editarMetasForm.getPlanId(), new Long(indicadorId), new Long(serieId), TipoMeta.getTipoMetaAnual(), new Integer(ano), new Integer(periodo)), valor, new Boolean(false));
					metasEditadas.add(metaEditada);
				}
			}
		}

		for (Iterator<?> i = editarMetasForm.getMetasIndicadores().iterator(); i.hasNext(); ) 
		{
			MetasIndicador metasIndicador = (MetasIndicador)i.next();
			for (Iterator<?> j = metasIndicador.getMetasAnualesParciales().iterator(); j.hasNext(); ) 
			{
				MetaAnualParciales metaAnualParciales = (MetaAnualParciales)j.next();
				for (Iterator<?> k = metaAnualParciales.getMetasParciales().iterator(); k.hasNext(); ) 
				{
					Meta metaParcial = (Meta)k.next();
					metaParcial.setTipoCargaMeta(metaAnualParciales.getTipoCargaMeta());
					metasEditadas.add(metaParcial);
				}
			}
		}

		if (metasEditadas.size() > 0) 
			respuesta = strategosMetasService.saveMetas(metasEditadas, getUsuarioConectado(request));

		if (respuesta == 10000)
		{
			destruirPoolLocksUsoEdicion(request, strategosMetasService);
			forward = "exito";

			request.getSession().removeAttribute("editarMetasForm");
			request.getSession().removeAttribute("editarMetasParcialesForm");
			
			if (metasEditadas.size() > 0)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmetas.mensaje.guardarmetas.exito"));
		}
		else if (respuesta == 10003)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarmetas.mensaje.guardarmetas.relacionados"));

		strategosMetasService.close();

		saveMessages(request, messages);

		if (forward.equals("exito")) 
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
}