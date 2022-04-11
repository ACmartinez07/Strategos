package com.visiongc.app.strategos.web.struts.problemas.acciones.actions;

import com.visiongc.app.strategos.estadosacciones.model.EstadoAcciones;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.problemas.StrategosAccionesService;
import com.visiongc.app.strategos.problemas.model.Accion;
import com.visiongc.app.strategos.problemas.model.MemoProblema;
import com.visiongc.app.strategos.problemas.model.Problema;
import com.visiongc.app.strategos.problemas.model.ResponsableAccion;
import com.visiongc.app.strategos.web.struts.problemas.acciones.forms.GestionarAccionesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.web.controles.SplitControl;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GestionarAccionesAction extends VgcAction
{
	public static final String ACTION_KEY = "GestionarAccionesAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(TreeviewWeb.getUrlTreeview(url), nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarAccionesForm gestionarAccionesForm = (GestionarAccionesForm)form;

		ActionMessages messages = getMessages(request);

		String selectedAccionCorrectivaId = request.getParameter("selectedAccionCorrectivaId");
		String openAccionCorrectivaId = request.getParameter("openAccionCorrectivaId");
		String closeAccionCorrectivaId = request.getParameter("closeAccionCorrectivaId");
		Accion accionCorrectiva = (Accion)request.getSession().getAttribute("accionCorrectiva");
		Long organizacionId = new Long(request.getSession().getAttribute("organizacionId").toString());

		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("ACCION_VIEWALL", organizacionId.longValue());
		boolean cambioProblema = false;

		if (accionCorrectiva != null) 
			cambioProblema = !accionCorrectiva.getProblemaId().toString().equals(gestionarAccionesForm.getProblemaId().toString());

		ArbolesService nodosArbolService = FrameworkServiceFactory.getInstance().openArbolesService();

		if ((request.getSession().getAttribute("accionCorrectiva") == null) || (cambioProblema))
		{
			Object[] arregloIdentificadores = new Object[2];
			arregloIdentificadores[0] = "problemaId";
			arregloIdentificadores[1] = gestionarAccionesForm.getProblemaId();
			
			Accion accionCorrectivaRoot = new Accion();

			accionCorrectivaRoot = (Accion)nodosArbolService.getNodoArbolRaiz(accionCorrectivaRoot, arregloIdentificadores);

			if (accionCorrectivaRoot == null) 
			{
				StrategosAccionesService strategosAccionesService = StrategosServiceFactory.getInstance().openStrategosAccionesService();
				accionCorrectivaRoot = strategosAccionesService.crearAccionRaiz(gestionarAccionesForm.getProblemaId(), getUsuarioConectado(request));
				strategosAccionesService.close();
			}

			TreeviewWeb.publishTree("arbolAccionesCorrectivas", accionCorrectivaRoot.getAccionId().toString(), "session", request, true);
			nodosArbolService.refreshNodosArbol(accionCorrectivaRoot, (String)request.getSession().getAttribute("arbolAccionesCorrectivas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), null, null, "orden");
			request.getSession().setAttribute("accionCorrectivaRoot", accionCorrectivaRoot);
			
			request.getSession().setAttribute("accionCorrectiva", accionCorrectivaRoot);
			request.getSession().setAttribute("accionCorrectivaId", accionCorrectivaRoot.getAccionId().toString());
			setValoresAccionCorrectiva(accionCorrectivaRoot.getAccionId(), gestionarAccionesForm, nodosArbolService);
		}
		else
		{
			Accion accionCorrectivaSeleccionada = null;

			if (request.getAttribute("GestionarAccionesAction.reloadId") != null) 
				accionCorrectivaSeleccionada = (Accion)nodosArbolService.load(Accion.class, (Long)request.getAttribute("GestionarAccionesAction.reloadId"));
			else if ((selectedAccionCorrectivaId != null) && (!selectedAccionCorrectivaId.equals(""))) 
				accionCorrectivaSeleccionada = (Accion)nodosArbolService.load(Accion.class, new Long(selectedAccionCorrectivaId));
			else if ((openAccionCorrectivaId != null) && (!openAccionCorrectivaId.equals(""))) 
			{
				TreeviewWeb.publishTreeOpen("arbolAccionesCorrectivas", openAccionCorrectivaId, "session", request);
				accionCorrectivaSeleccionada = (Accion)nodosArbolService.load(Accion.class, new Long(openAccionCorrectivaId));
			} 
			else if ((closeAccionCorrectivaId != null) && (!closeAccionCorrectivaId.equals(""))) 
			{
				TreeviewWeb.publishTreeClose("arbolAccionesCorrectivas", closeAccionCorrectivaId, "session", request);
				accionCorrectivaSeleccionada = (Accion)nodosArbolService.load(Accion.class, new Long(closeAccionCorrectivaId));
			} 
			else 
				accionCorrectivaSeleccionada = (Accion)nodosArbolService.load(Accion.class, new Long((String)request.getSession().getAttribute("accionCorrectivaId")));
			Long reloadId;
			if (accionCorrectivaSeleccionada == null) 
			{
				accionCorrectivaSeleccionada = (Accion)request.getSession().getAttribute("accionCorrectivaRoot");
				reloadId = accionCorrectivaSeleccionada.getAccionId();
				TreeviewWeb.publishTree("arbolAccionesCorrectivas", accionCorrectivaSeleccionada.getAccionId().toString(), "session", request, true);
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			} 
			else 
			{
				reloadId = accionCorrectivaSeleccionada.getAccionId();
				if (closeAccionCorrectivaId == null) 
					TreeviewWeb.publishTreeOpen("arbolAccionesCorrectivas", reloadId.toString(), "session", request);
			}
			
			nodosArbolService.refreshNodosArbol((Accion)request.getSession().getAttribute("accionCorrectivaRoot"), (String)request.getSession().getAttribute("arbolAccionesCorrectivas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId, null, "orden");
			
			request.getSession().setAttribute("accionCorrectiva", accionCorrectivaSeleccionada);
			request.getSession().setAttribute("accionCorrectivaId", accionCorrectivaSeleccionada.getAccionId().toString());
			setValoresAccionCorrectiva(accionCorrectivaSeleccionada.getAccionId(), gestionarAccionesForm, nodosArbolService);
		}
		
		nodosArbolService.close();
		
		SplitControl.setConfiguracion(request, "splitSeguimientos");
		
		request.getSession().setAttribute("verClase", getPermisologiaUsuario(request).tienePermiso("ACCION_VIEWALL"));
		request.getSession().setAttribute("editarClase", getPermisologiaUsuario(request).tienePermiso("ACCION_EDIT"));
		request.getSession().setAttribute("verClaseProblema", getPermisologiaUsuario(request).tienePermiso("CLASE_PROBLEMA_VIEWALL"));
		request.getSession().setAttribute("editarClaseProblema", getPermisologiaUsuario(request).tienePermiso("CLASE_PROBLEMA_EDIT"));
		
		saveMessages(request, messages);

		return mapping.findForward(forward);
	}

	private void setValoresAccionCorrectiva(Long accionCorrectivaId, GestionarAccionesForm gestionarAccionesForm, ArbolesService servicio)
	{
		gestionarAccionesForm.clear();
		
		Problema problema = (Problema)servicio.load(Problema.class, gestionarAccionesForm.getProblemaId());

		Accion accionCorrectiva = (Accion)servicio.load(Accion.class, accionCorrectivaId);
		
		if (problema.getFechaEstimadaInicio() != null) 
			gestionarAccionesForm.setFechaEstimadaInicioProblema(VgcFormatter.formatearFecha(problema.getFechaEstimadaInicio(), "formato.fecha.corta"));
		if (problema.getFechaEstimadaFinal() != null) 
			gestionarAccionesForm.setFechaEstimadaFinalProblema(VgcFormatter.formatearFecha(problema.getFechaEstimadaFinal(), "formato.fecha.corta"));

		if (accionCorrectiva.getPadreId() == null)
		{
			if (problema.getEstadoId() != null) 
			{
				EstadoAcciones estadoAcciones = (EstadoAcciones)servicio.load(EstadoAcciones.class, problema.getEstadoId());
				gestionarAccionesForm.setNombreEstado(estadoAcciones.getNombre());
			}

			for (Iterator i = problema.getMemosProblema().iterator(); i.hasNext(); ) 
			{
				MemoProblema memoProblema = (MemoProblema)i.next();
				Byte tipoMemo = memoProblema.getPk().getTipo();
				if (tipoMemo.equals(new Byte((byte) 0))) 
					gestionarAccionesForm.setDescripcion(memoProblema.getMemo());
			}

			if (problema.getFechaEstimadaInicio() != null) 
				gestionarAccionesForm.setFechaEstimadaInicio(VgcFormatter.formatearFecha(problema.getFechaEstimadaInicio(), "formato.fecha.corta"));
			if (problema.getFechaEstimadaFinal() != null) 
				gestionarAccionesForm.setFechaEstimadaFin(VgcFormatter.formatearFecha(problema.getFechaEstimadaFinal(), "formato.fecha.corta"));
			if (problema.getFechaRealInicio() != null) 
				gestionarAccionesForm.setFechaRealInicio(VgcFormatter.formatearFecha(problema.getFechaRealInicio(), "formato.fecha.corta"));
			if (problema.getFechaRealFinal() != null) 
				gestionarAccionesForm.setFechaRealFin(VgcFormatter.formatearFecha(problema.getFechaRealFinal(), "formato.fecha.corta"));
			if (problema.getResponsable() != null) 
				gestionarAccionesForm.setNombreResponsable(problema.getResponsable().getNombreCargo());
			if (problema.getAuxiliar() != null) 
				gestionarAccionesForm.setNombreAuxiliar(problema.getAuxiliar().getNombreCargo());
		}
		else
		{
			if (accionCorrectiva.getEstadoId() != null) 
			{
				EstadoAcciones estadoAcciones = (EstadoAcciones)servicio.load(EstadoAcciones.class, accionCorrectiva.getEstadoId());
				gestionarAccionesForm.setNombreEstado(estadoAcciones.getNombre());
			}

			if (accionCorrectiva.getDescripcion() != null) 
				gestionarAccionesForm.setDescripcion(accionCorrectiva.getDescripcion());
			if (accionCorrectiva.getFechaEstimadaInicio() != null) 
				gestionarAccionesForm.setFechaEstimadaInicio(VgcFormatter.formatearFecha(accionCorrectiva.getFechaEstimadaInicio(), "formato.fecha.corta"));
			if (accionCorrectiva.getFechaEstimadaFinal() != null) 
				gestionarAccionesForm.setFechaEstimadaFin(VgcFormatter.formatearFecha(accionCorrectiva.getFechaEstimadaFinal(), "formato.fecha.corta"));
			if (accionCorrectiva.getFechaRealInicio() != null) 
				gestionarAccionesForm.setFechaRealInicio(VgcFormatter.formatearFecha(accionCorrectiva.getFechaRealInicio(), "formato.fecha.corta"));
			if (accionCorrectiva.getFechaRealFinal() != null) 
				gestionarAccionesForm.setFechaRealFin(VgcFormatter.formatearFecha(accionCorrectiva.getFechaRealFinal(), "formato.fecha.corta"));
			if (accionCorrectiva.getFrecuencia() != null) 
				gestionarAccionesForm.setFrecuencia(accionCorrectiva.getFrecuencia().toString());
			if (accionCorrectiva.getOrden() != null) 
				gestionarAccionesForm.setOrden(accionCorrectiva.getOrden().toString());

			for (Iterator i = accionCorrectiva.getResponsablesAccion().iterator(); i.hasNext(); ) 
			{
				ResponsableAccion responsableAccion = (ResponsableAccion)i.next();
				Byte tipoResponsable = responsableAccion.getTipo();
				if (tipoResponsable.equals(new Byte((byte) 1))) 
					gestionarAccionesForm.setNombreResponsable(responsableAccion.getResponsable().getNombreCargo());
				if (tipoResponsable.equals(new Byte((byte) 2)))
					gestionarAccionesForm.setNombreAuxiliar(responsableAccion.getResponsable().getNombreCargo());
			}
		}
	}
}