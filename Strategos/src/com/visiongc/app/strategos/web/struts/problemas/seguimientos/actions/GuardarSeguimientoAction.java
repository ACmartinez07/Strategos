package com.visiongc.app.strategos.web.struts.problemas.seguimientos.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.StrategosSeguimientosService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.MemoSeguimiento;
import com.visiongc.app.strategos.problemas.model.MemoSeguimientoPK;
import com.visiongc.app.strategos.problemas.model.Seguimiento;
import com.visiongc.app.strategos.web.struts.problemas.seguimientos.forms.EditarSeguimientoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarSeguimientoAction extends VgcAction
{
	private static final String ACTION_KEY = "GuardarSeguimientoAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarSeguimientoForm editarSeguimientoForm = (EditarSeguimientoForm)form;

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarSeguimientoAction.ultimoTs");
		Accion accionCorrectiva = (Accion)request.getSession().getAttribute("accionCorrectiva");

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosSeguimientosService strategosSeguimientosService = StrategosServiceFactory.getInstance().openStrategosSeguimientosService();

		if (cancelar)
		{
			strategosSeguimientosService.unlockObject(request.getSession().getId(), editarSeguimientoForm.getSeguimientoId());
			
			strategosSeguimientosService.close();

			return getForwardBack(request, 1, true);
		}

		Seguimiento seguimiento = new Seguimiento();
		boolean nuevo = false;
		int respuesta = 10000;

		if ((editarSeguimientoForm.getSeguimientoId() != null) && (!editarSeguimientoForm.getSeguimientoId().equals(Long.valueOf("0"))))
		{
			seguimiento = (Seguimiento)strategosSeguimientosService.load(Seguimiento.class, editarSeguimientoForm.getSeguimientoId());

			if ((seguimiento.getRecepcionEnviado() != null) && (!seguimiento.getRecepcionEnviado().booleanValue()))
			{
				seguimiento.setFechaRecepcion(new Date());
				seguimiento.setEstadoId(editarSeguimientoForm.getEstadoId());
				seguimiento.setRecepcionEnviado(new Boolean(true));
				seguimiento.setPreparadoPor(editarSeguimientoForm.getNombreResponsable());
			}
			else if ((seguimiento.getRecepcionEnviado() != null) && (seguimiento.getRecepcionEnviado().booleanValue()))
			{
				seguimiento.setFechaAprobacion(new Date());
				seguimiento.setAprobado(editarSeguimientoForm.getAprobado());
				seguimiento.setAprobadoPor(editarSeguimientoForm.getNombreSupervisor());
			}
		}
		else
		{
			nuevo = true;
			seguimiento.setSeguimientoId(new Long(0L));
			seguimiento.setAccionId(accionCorrectiva.getAccionId());
			seguimiento.setFechaEmision(new Date());
			seguimiento.setEmisionEnviado(new Boolean(true));
			seguimiento.setMemosSeguimiento(new HashSet());
			seguimiento.setNota(editarSeguimientoForm.getNota());
		}

		if ((editarSeguimientoForm.getMemoResumen() != null) && (!editarSeguimientoForm.getMemoResumen().equals(""))) 
		{
			MemoSeguimiento memoSeguimiento = new MemoSeguimiento();
			memoSeguimiento.setPk(new MemoSeguimientoPK(seguimiento.getSeguimientoId(), new Byte((byte) 0)));
			memoSeguimiento.setMemo(editarSeguimientoForm.getMemoResumen());
			seguimiento.getMemosSeguimiento().add(memoSeguimiento);
		}

		if ((editarSeguimientoForm.getMemoComentario() != null) && (!editarSeguimientoForm.getMemoComentario().equals(""))) 
		{
			MemoSeguimiento memoSeguimiento = new MemoSeguimiento();
			memoSeguimiento.setPk(new MemoSeguimientoPK(seguimiento.getSeguimientoId(), new Byte((byte) 1)));
			memoSeguimiento.setMemo(editarSeguimientoForm.getMemoComentario());
			seguimiento.getMemosSeguimiento().add(memoSeguimiento);
		}

		respuesta = strategosSeguimientosService.saveSeguimiento(seguimiento, getUsuarioConectado(request));

		strategosSeguimientosService.close();
		
		if ((editarSeguimientoForm.getAprobado() != null) && (editarSeguimientoForm.getAprobado().booleanValue()))
		{
			StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService();
			
			Accion accion = (Accion)strategosAccionesService.load(Accion.class, accionCorrectiva.getAccionId());

			accion.setEstadoId(editarSeguimientoForm.getEstadoId());
			respuesta = strategosAccionesService.saveAccion(accion, getUsuarioConectado(request));

			strategosAccionesService.close();
		}

		if (respuesta == 10000)
		{
			strategosSeguimientosService.unlockObject(request.getSession().getId(), editarSeguimientoForm.getSeguimientoId());
			
			if (nuevo)
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarseguimiento.confirmar.envioseguimiento"));
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		}

		saveMessages(request, messages);
		
		request.getSession().setAttribute("GuardarSeguimientoAction.ultimoTs", ts);

		return mapping.findForward(forward);
	}
}