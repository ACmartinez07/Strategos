package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.organizaciones.forms.EditarOrganizacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;

import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class EditarOrganizacionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		EditarOrganizacionForm editarOrganizacionForm = (EditarOrganizacionForm)form;

		ActionMessages messages = getMessages(request);

		String organizacionId = request.getParameter("organizacionId");
		
		boolean verForm = getPermisologiaUsuario(request).tienePermiso("ORGANIZACION_VIEWALL");
		boolean editarForm = getPermisologiaUsuario(request).tienePermiso("ORGANIZACION_EDIT");

    	String funcion = request.getParameter("accion");
    	if (funcion != null && funcion.equals("copiar"))
    	{
			// Presentacion
			FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
			ConfiguracionUsuario presentacion = frameworkService.getConfiguracionUsuario(this.getUsuarioConectado(request).getUsuarioId(), "Strategos.Wizar.Organizacion.Copiar.ShowPresentacion", "ShowPresentacion");
			if (presentacion != null && presentacion.getData() != null)
				editarOrganizacionForm.setShowPresentacion(presentacion.getData().equals("1") ? true : false);
			frameworkService.close();

    		StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
    		if (!strategosIndicadoresService.checkLicencia(request))
    		{
    			strategosIndicadoresService.close();
    			
    			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("action.guardarregistro.limite.exedido"));
    			this.saveMessages(request, messages);
    			
    			return this.getForwardBack(request, 1, false);
    		}
    		strategosIndicadoresService.close();
    	}

		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

		if ((organizacionId != null) && (!organizacionId.equals("")) && (!organizacionId.equals("0")))
		{
			OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizacionId));
			Long rootId = 0L;
			if (organizacionStrategos.getPadreId() != null && organizacionStrategos.getPadreId().longValue() != 0)
				rootId = BuscarRootId(strategosOrganizacionesService, organizacionStrategos.getPadreId());
			else
				rootId = organizacionStrategos.getOrganizacionId();
			
			editarOrganizacionForm.setRootId(rootId);

			if (organizacionStrategos != null)
			{
				editarOrganizacionForm.setSoloLectura(organizacionStrategos.getSoloLectura());

				if (editarOrganizacionForm.getSoloLectura().booleanValue())
				{
					editarOrganizacionForm.setBloqueado(new Boolean(true));

					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
				}

				OrganizacionStrategos padre = organizacionStrategos.getPadre();
				Long padreId = null;
				if (padre != null) 
				{
					padreId = padre.getOrganizacionId();
					OrganizacionStrategos organizacionStrategosPadre = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(padreId));
					if (organizacionStrategosPadre != null)
						editarOrganizacionForm.setPadre(organizacionStrategosPadre.getNombre());
				}

				editarOrganizacionForm.setPadreId(padreId);
				editarOrganizacionForm.setOrganizacionId(organizacionStrategos.getOrganizacionId());
				editarOrganizacionForm.setNombre(organizacionStrategos.getNombre());
				editarOrganizacionForm.setRif(organizacionStrategos.getRif());
				editarOrganizacionForm.setTelefono(organizacionStrategos.getTelefono());
				editarOrganizacionForm.setFax(organizacionStrategos.getFax());
				editarOrganizacionForm.setDireccion(organizacionStrategos.getDireccion());
				editarOrganizacionForm.setEnlaceParcial(organizacionStrategos.getEnlaceParcial());
				editarOrganizacionForm.setPorcentajeZonaAmarillaMinMaxIndicadores(organizacionStrategos.getPorcentajeZonaAmarillaMinMaxIndicadores());
				editarOrganizacionForm.setPorcentajeZonaVerdeMetaIndicadores(organizacionStrategos.getPorcentajeZonaVerdeMetaIndicadores());
				editarOrganizacionForm.setPorcentajeZonaAmarillaMetaIndicadores(organizacionStrategos.getPorcentajeZonaAmarillaMetaIndicadores());
				editarOrganizacionForm.setPorcentajeZonaVerdeIniciativas(organizacionStrategos.getPorcentajeZonaVerdeIniciativas());
				editarOrganizacionForm.setPorcentajeZonaAmarillaIniciativas(organizacionStrategos.getPorcentajeZonaAmarillaIniciativas());
				editarOrganizacionForm.setMesCierre(organizacionStrategos.getMesCierre());
				editarOrganizacionForm.setVisible(organizacionStrategos.getVisible());
				
				for (Iterator<?> i = organizacionStrategos.getMemos().iterator(); i.hasNext(); ) 
				{
					MemoOrganizacion oMemo = (MemoOrganizacion)i.next();
					Integer tipoMemo = oMemo.getPk().getTipo();
					if (tipoMemo.equals(new Integer(0)))
						editarOrganizacionForm.setDescripcion(oMemo.getDescripcion());
					else if (tipoMemo.equals(new Integer(1)))
						editarOrganizacionForm.setObservaciones(oMemo.getDescripcion());
					else if (tipoMemo.equals(new Integer(2)))
						editarOrganizacionForm.setPersonalDirectivo(oMemo.getDescripcion());
					else if (tipoMemo.equals(new Integer(3)))
						editarOrganizacionForm.setMision(oMemo.getDescripcion());
					else if (tipoMemo.equals(new Integer(4)))
						editarOrganizacionForm.setVision(oMemo.getDescripcion());
					else if (tipoMemo.equals(new Integer(5)))
						editarOrganizacionForm.setOportunidadesRetos(oMemo.getDescripcion());
					else if (tipoMemo.equals(new Integer(6)))
						editarOrganizacionForm.setLineamientosEstrategicos(oMemo.getDescripcion());
					else if (tipoMemo.equals(new Integer(7)))
						editarOrganizacionForm.setFactoresClave(oMemo.getDescripcion());
					else if (tipoMemo.equals(new Integer(8)))
						editarOrganizacionForm.setPoliticas(oMemo.getDescripcion());
					else if (tipoMemo.equals(new Integer(9))) 
						editarOrganizacionForm.setValores(oMemo.getDescripcion());
				}
			}
			else
			{
				strategosOrganizacionesService.unlockObject(request.getSession().getId(), new Long(organizacionId));

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}

		}
		else
		{
			editarOrganizacionForm.clear();
			OrganizacionStrategos padre = (OrganizacionStrategos)request.getSession().getAttribute("organizacion");
			editarOrganizacionForm.setOrganizacionId(new Long(0L));
			editarOrganizacionForm.setPadreId(padre.getOrganizacionId());
		}
		
		List<?> meses = PeriodoUtil.getListaMeses();
		request.setAttribute("meses", meses);
		
		strategosOrganizacionesService.close();

		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
	
	private Long BuscarRootId(StrategosOrganizacionesService strategosOrganizacionesService, Long organizacionId)
	{
		Long rootId = 0L;
		OrganizacionStrategos organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, new Long(organizacionId));
		if (organizacionStrategos.getPadreId() != null && organizacionStrategos.getPadreId().longValue() != 0)
			rootId = BuscarRootId(strategosOrganizacionesService, organizacionStrategos.getPadreId());
		else
			rootId = organizacionStrategos.getOrganizacionId();
		
		return rootId;
	}
}