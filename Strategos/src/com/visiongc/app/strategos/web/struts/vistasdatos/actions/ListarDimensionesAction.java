package com.visiongc.app.strategos.web.struts.vistasdatos.actions;

import com.visiongc.app.strategos.vistasdatos.model.util.TipoDimension;
import com.visiongc.app.strategos.web.struts.vistasdatos.forms.ConfigurarVistaDatosForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.ObjetoValorNombre;
import com.visiongc.commons.web.NavigationBar;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

public final class ListarDimensionesAction extends VgcAction
{
	public static final String ACTION_KEY = "ConfigurarVistaDatosAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		ActionMessages messages = getMessages(request);

		ConfigurarVistaDatosForm configurarVistaDatosForm = (ConfigurarVistaDatosForm)form;

		configurarVistaDatosForm.setDimensiones(convertirListaDimensionesListaObjetoValorNombre(TipoDimension.getTiposDimensiones()));
		configurarVistaDatosForm.setSeleccionadosDimensiones(TipoDimension.getTipoDimensionVariable().toString());
		configurarVistaDatosForm.setValoresSeleccionadosDimensiones(TipoDimension.getNombre(TipoDimension.getTipoDimensionVariable().byteValue()));
		
		saveMessages(request, messages);

		return mapping.findForward(forward);
	}

	private List convertirListaDimensionesListaObjetoValorNombre(List dimensiones)
	{
		List elementos = new ArrayList();

		for (int i = 0; i < dimensiones.size(); i++) 
		{
			TipoDimension tipoDimension = (TipoDimension)dimensiones.get(i);
			
			ObjetoValorNombre objetoValorNombre = new ObjetoValorNombre();
			objetoValorNombre.setValor(tipoDimension.getTipoDimensionId().toString());
			objetoValorNombre.setNombre(tipoDimension.getNombre());
			
			elementos.add(objetoValorNombre);
		}

		return elementos;
	}
}