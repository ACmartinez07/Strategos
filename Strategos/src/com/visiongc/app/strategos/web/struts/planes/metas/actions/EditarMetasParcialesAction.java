package com.visiongc.app.strategos.web.struts.planes.metas.actions;

import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.planes.model.util.MetaAnualParciales;
import com.visiongc.app.strategos.planes.model.util.MetasIndicador;
import com.visiongc.app.strategos.web.struts.planes.metas.forms.EditarMetasForm;
import com.visiongc.app.strategos.web.struts.planes.metas.forms.EditarMetasParcialesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EditarMetasParcialesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarMetasParcialesForm editarMetasParcialesForm = (EditarMetasParcialesForm)form;
		EditarMetasForm editarMetasForm = (EditarMetasForm)request.getSession().getAttribute("editarMetasForm");

		if ((request.getParameter("numeroDecimales") == null) && (!request.getParameter("numeroDecimales").equals(""))) 
			editarMetasParcialesForm.setNumeroDecimales(new Byte((byte) 2));

		double valor = VgcFormatter.parsearNumeroFormateado(editarMetasParcialesForm.getValor());
		boolean editarValores = getPermisologiaUsuario(request).tienePermiso("INDICADOR_VALOR_META_CARGAR");
		editarMetasParcialesForm.setValor(VgcFormatter.formatearNumero(new Double(valor), editarMetasParcialesForm.getNumeroDecimales().intValue()));

		if ((request.getParameter("tipoCorte") != null) && (!request.getParameter("tipoCorte").equals("")) && (editarMetasParcialesForm.getTipoCorte().byteValue() == TipoCorte.getTipoCorteLongitudinal().byteValue()) && (editarMetasParcialesForm.getTipoCargaMeta() == null)) 
			editarMetasParcialesForm.setTipoCargaMeta(editarMetasParcialesForm.getTipoCargaMedicion());

		editarMetasParcialesForm.setCerrarVentana(new Boolean(false));

		if ((editarMetasParcialesForm.getIndicadorId() != null) && (editarMetasParcialesForm.getIndicadorId().longValue() > 0L) && 
				(editarMetasParcialesForm.getSerieId() != null) && (editarMetasParcialesForm.getSerieId().longValue() > 0L) && 
				(editarMetasParcialesForm.getAno() != null) && (editarMetasParcialesForm.getAno().intValue() > 0) && 
				(editarMetasParcialesForm.getNumeroPeriodos() != null) && (editarMetasParcialesForm.getNumeroPeriodos().intValue() > 0) && (editarMetasParcialesForm.getValor() != null)) 
		{
			for (Iterator<?> i = editarMetasForm.getMetasIndicadores().iterator(); i.hasNext(); ) 
			{
				MetasIndicador metasIndicador = (MetasIndicador)i.next();
				for (Iterator<?> j = metasIndicador.getMetasAnualesParciales().iterator(); j.hasNext(); ) 
				{
					MetaAnualParciales metaAnualParciales = (MetaAnualParciales)j.next();
					Integer ano = metaAnualParciales.getMetaAnual().getMetaId().getAno();
					Long indicadorId = metaAnualParciales.getMetaAnual().getMetaId().getIndicadorId();
					Long serieId = metaAnualParciales.getMetaAnual().getMetaId().getSerieId();
					if ((editarMetasParcialesForm.getAno().byteValue() == ano.byteValue()) && (editarMetasParcialesForm.getIndicadorId().longValue() == indicadorId.longValue()) && (editarMetasParcialesForm.getSerieId().longValue() == serieId.longValue())) 
						editarMetasParcialesForm.setMetaAnualParciales(metaAnualParciales);
				}
			}
		}

		if (editarMetasParcialesForm.getMetaAnualParciales() == null) 
			editarMetasParcialesForm.setCerrarVentana(new Boolean(true));
		
		editarMetasParcialesForm.setBloquear(!editarValores);

		return mapping.findForward(forward);
	}
}