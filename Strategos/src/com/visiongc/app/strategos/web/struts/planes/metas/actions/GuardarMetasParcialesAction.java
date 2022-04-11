package com.visiongc.app.strategos.web.struts.planes.metas.actions;

import com.visiongc.app.strategos.planes.model.Meta;
import com.visiongc.app.strategos.planes.model.util.MetaAnualParciales;
import com.visiongc.app.strategos.planes.model.util.MetasIndicador;
import com.visiongc.app.strategos.web.struts.planes.metas.forms.EditarMetasForm;
import com.visiongc.app.strategos.web.struts.planes.metas.forms.EditarMetasParcialesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public final class GuardarMetasParcialesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarMetasForm editarMetasForm = (EditarMetasForm)request.getSession().getAttribute("editarMetasForm");
		EditarMetasParcialesForm editarMetasParcialesForm = (EditarMetasParcialesForm)form;

		boolean cancelar = false;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarMetasParcialesAction.ultimoTs");
		Map<String, String[]> mapaParametros = request.getParameterMap();
		
		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		if (cancelar)
		{
			request.getSession().removeAttribute("editarMetasParcialesForm");
			return mapping.findForward(forward);
		}

		Byte tipoCargaMeta = null;
		String strTipoCargaMeta = request.getParameter("tipoCargaMeta");
		if ((strTipoCargaMeta != null) && (!strTipoCargaMeta.equals(""))) 
			tipoCargaMeta = new Byte(strTipoCargaMeta);

		setMetasParciales(editarMetasParcialesForm, editarMetasForm, mapaParametros, tipoCargaMeta);

		return mapping.findForward("editarMetasParcialesFinalizar");
	}

	private void setMetasParciales(EditarMetasParcialesForm editarMetasParcialesForm, EditarMetasForm editarMetasForm, Map<String, String[]> mapaParametros, Byte tipoCargaMeta) throws Exception 
	{
		if ((editarMetasParcialesForm.getIndicadorId() != null) && (editarMetasParcialesForm.getIndicadorId().longValue() > 0L) && 
				(editarMetasParcialesForm.getSerieId() != null) && (editarMetasParcialesForm.getSerieId().longValue() > 0L) && 
				(editarMetasParcialesForm.getAno() != null) && (editarMetasParcialesForm.getAno().intValue() > 0))
		{
			for (Iterator<?> i = editarMetasForm.getMetasIndicadores().iterator(); i.hasNext(); ) 
			{
				MetasIndicador metasIndicador = (MetasIndicador)i.next();
				if (metasIndicador.getIndicador().getIndicadorId().longValue() == editarMetasParcialesForm.getIndicadorId().longValue()) 
				{
					setMetasParciales2(editarMetasParcialesForm, metasIndicador, mapaParametros, tipoCargaMeta);
					break;
				}
			}
		}
	}

	private void setMetasParciales2(EditarMetasParcialesForm editarMetasParcialesForm, MetasIndicador metasIndicador, Map<String, String[]> mapaParametros, Byte tipoCargaMeta) throws Exception
	{
		for (Iterator<String> iter = mapaParametros.keySet().iterator(); iter.hasNext(); )
		{
			String nombreParametro = (String)iter.next();

			if (nombreParametro.indexOf("valorPeriodo") != 0) 
				continue;
			String parametroPeriodo = nombreParametro.substring(12);
			String parametroValor = ((String[])mapaParametros.get(nombreParametro))[0];

			if (parametroValor.equals(""))
				continue;

			for (Iterator<?> j = metasIndicador.getMetasAnualesParciales().iterator(); j.hasNext(); ) 
			{
				MetaAnualParciales metaAnualParciales = (MetaAnualParciales)j.next();
				for (Iterator<?> k = metaAnualParciales.getMetasParciales().iterator(); k.hasNext(); ) 
				{
					Meta metaParcial = (Meta)k.next();
					Integer ano = metaParcial.getMetaId().getAno();
					Long serieId = metaParcial.getMetaId().getSerieId();
					Integer periodo = metaParcial.getMetaId().getPeriodo();
					if ((editarMetasParcialesForm.getAno().byteValue() == ano.byteValue()) && (editarMetasParcialesForm.getSerieId().longValue() == serieId.longValue()) && (new Integer(parametroPeriodo).intValue() == periodo.intValue())) 
					{
						metaParcial.setValor(new Double(VgcFormatter.parsearNumeroFormateado(parametroValor)));
						metaAnualParciales.setTipoCargaMeta(tipoCargaMeta);
					}
				}
			}
		}
	}
}