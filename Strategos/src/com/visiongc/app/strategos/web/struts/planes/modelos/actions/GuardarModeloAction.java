package com.visiongc.app.strategos.web.struts.planes.modelos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.planes.StrategosModelosService;
import com.visiongc.app.strategos.planes.model.Modelo;
import com.visiongc.app.strategos.planes.model.ModeloPK;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.planes.modelos.forms.EditarModeloForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarModeloAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		EditarModeloForm editarModeloForm = (EditarModeloForm)form;
		
		ActionMessages messages = getMessages(request);
		
		if (request.getSession().getAttribute("modeloPlanId") != null)
			request.getSession().removeAttribute("modeloPlanId");
		
		StrategosModelosService strategosModelosService = StrategosServiceFactory.getInstance().openStrategosModelosService();
		
		String editar = request.getParameter("editar") == null ? "0" : request.getParameter("editar");
		String previsualizar = request.getParameter("previsualizar") == null ? "0" : request.getParameter("previsualizar");
		
		editarModeloForm.setEditar(editar);
		editarModeloForm.setPrevisualizar(previsualizar);

		Modelo modelo = new Modelo();
		int respuesta = 10000;

		ModeloPK pk = new ModeloPK();
		pk.setPlanId(editarModeloForm.getPlanId());
		pk.setModeloId(editarModeloForm.getModeloId());
		modelo = (Modelo)strategosModelosService.load(Modelo.class, pk);

		if (modelo == null)
		{
			modelo = new Modelo();
			modelo.setPk(new ModeloPK());
			modelo.getPk().setPlanId(editarModeloForm.getPlanId());
			modelo.getPk().setModeloId(editarModeloForm.getModeloId());
		}

		if ((editarModeloForm.getBinario() != null) && (editarModeloForm.getBinario().equals(""))) 
			editarModeloForm.setBinario(null);

		modelo.setBinario(editarModeloForm.getBinario());
		modelo.setNombre(editarModeloForm.getNombre());
		modelo.setDescripcion(editarModeloForm.getDescripcion());

		respuesta = strategosModelosService.saveModelo(modelo, getUsuarioConectado(request));

		if (respuesta == 10000)
		{
			strategosModelosService.unlockObject(request.getSession().getId(), editarModeloForm.getPlanId());
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
			editarModeloForm.setStatus(StatusUtil.getStatusSuccess());
		}
		else if (respuesta == 10003)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
			editarModeloForm.setStatus(StatusUtil.getStatusRegistroDuplicado());
		}

		strategosModelosService.close();

		if (editarModeloForm.getPrevisualizar().equals("1")) 
			messages.clear();

		saveMessages(request, messages);

		return mapping.findForward(forward);
	}
}