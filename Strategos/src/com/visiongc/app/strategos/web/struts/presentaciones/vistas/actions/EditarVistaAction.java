package com.visiongc.app.strategos.web.struts.presentaciones.vistas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.presentaciones.StrategosVistasService;
import com.visiongc.app.strategos.presentaciones.model.Vista;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.presentaciones.vistas.forms.EditarVistaForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.web.NavigationBar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarVistaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarVistaForm editarVistaForm = (EditarVistaForm)form;

		ActionMessages messages = getMessages(request);

		String vistaId = request.getParameter("vistaId");

		boolean verForm = getPermisologiaUsuario(request).tienePermiso("VISTA_VIEWALL");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("VISTA_EDIT");
		boolean bloqueado = false;

		StrategosVistasService strategosVistasService = StrategosServiceFactory.getInstance().openStrategosVistasService();

		if ((vistaId != null) && (!vistaId.equals("")) && (!vistaId.equals("0")))
		{
			bloqueado = !strategosVistasService.lockForUpdate(request.getSession().getId(), vistaId, null);

			editarVistaForm.setBloqueado(new Boolean(bloqueado));

			Vista vista = (Vista)strategosVistasService.load(Vista.class, new Long(vistaId));

			if (vista != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

				editarVistaForm.setNombre(vista.getNombre());

				if (vista.getDescripcion() != null)
					editarVistaForm.setDescripcion(vista.getDescripcion());
				else 
					editarVistaForm.setDescripcion(null);

				if (vista.getFechaInicio() != null) 
				{
					String[] dato = vista.getFechaInicio().split(EditarVistaForm.getSeparadorPeriodos());
					editarVistaForm.setMesInicio(Byte.valueOf(dato[0]));
					editarVistaForm.setAnoInicio(Integer.valueOf(dato[1]));
				}
				else 
					editarVistaForm.setFechaInicio(null);

				if (vista.getFechaFin() != null) 
				{
					String[] dato = vista.getFechaFin().split(EditarVistaForm.getSeparadorPeriodos());
					editarVistaForm.setMesFinal(Byte.valueOf(dato[0]));
					editarVistaForm.setAnoFinal(Integer.valueOf(dato[1]));
				}
				else 
					editarVistaForm.setFechaFin(null);

				editarVistaForm.setVisible(vista.getVisible());
			}
			else
			{
				strategosVistasService.unlockObject(request.getSession().getId(), new Long(vistaId));
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
			editarVistaForm.clear();

		int anoActual = FechaUtil.getAno(new Date());
		List anos = PeriodoUtil.getListaNumeros(new Integer(anoActual), new Byte((byte) 5));
		editarVistaForm.setAnos(anos);

		List meses = PeriodoUtil.getListaMeses();
		editarVistaForm.setMeses(meses);

		strategosVistasService.close();

		if (!bloqueado && verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarVistaForm.setBloqueado(true);
		}
		else if (!bloqueado && !verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
    
		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
}