package com.visiongc.app.strategos.web.struts.organizaciones.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.MemoOrganizacion;
import com.visiongc.app.strategos.organizaciones.model.MemoOrganizacionPK;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.web.struts.organizaciones.forms.EditarOrganizacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarOrganizacionAction extends VgcAction
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

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String)request.getSession().getAttribute("GuardarOrganizacionAction.ultimoTs");

		String porcentajeZonaAmarillaMinMaxIndicadores = request.getParameter("porcentajeZonaAmarillaMinMaxIndicadores");
		String porcentajeZonaVerdeMetaIndicadores = request.getParameter("porcentajeZonaVerdeMetaIndicadores");
		String porcentajeZonaAmarillaMetaIndicadores = request.getParameter("porcentajeZonaAmarillaMetaIndicadores");
		String porcentajeZonaVerdeIniciativas = request.getParameter("porcentajeZonaVerdeIniciativas");
		String porcentajeZonaAmarillaIniciativas = request.getParameter("porcentajeZonaAmarillaIniciativas");
		if (porcentajeZonaAmarillaMinMaxIndicadores == null || porcentajeZonaAmarillaMinMaxIndicadores.equals(""))
			editarOrganizacionForm.setPorcentajeZonaAmarillaMinMaxIndicadores(null);
		if (porcentajeZonaVerdeMetaIndicadores == null || porcentajeZonaVerdeMetaIndicadores.equals(""))
			editarOrganizacionForm.setPorcentajeZonaVerdeMetaIndicadores(null);
		if (porcentajeZonaAmarillaMetaIndicadores == null || porcentajeZonaAmarillaMetaIndicadores.equals(""))
			editarOrganizacionForm.setPorcentajeZonaAmarillaMetaIndicadores(null);
		if (porcentajeZonaVerdeIniciativas == null || porcentajeZonaVerdeIniciativas.equals(""))
			editarOrganizacionForm.setPorcentajeZonaVerdeIniciativas(null);
		if (porcentajeZonaAmarillaIniciativas == null || porcentajeZonaAmarillaIniciativas.equals(""))
			editarOrganizacionForm.setPorcentajeZonaAmarillaIniciativas(null);
			
		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts))) 
			cancelar = true;

		StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

		if (cancelar)
		{
			strategosOrganizacionesService.unlockObject(request.getSession().getId(), editarOrganizacionForm.getOrganizacionId());
			strategosOrganizacionesService.close();
			return getForwardBack(request, 1, true);
		}

		OrganizacionStrategos organizacionStrategos = new OrganizacionStrategos();
		boolean nuevo = false;
		int respuesta = 10000;

		organizacionStrategos.setOrganizacionId(editarOrganizacionForm.getOrganizacionId());
		organizacionStrategos.setNombre(editarOrganizacionForm.getNombre());

		if ((editarOrganizacionForm.getOrganizacionId() != null) && (!editarOrganizacionForm.getOrganizacionId().equals(Long.valueOf("0"))))
			organizacionStrategos = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, editarOrganizacionForm.getOrganizacionId());
		else
		{
			nuevo = true;
			OrganizacionStrategos organizacionStrategosPadre = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, editarOrganizacionForm.getPadreId());
			organizacionStrategos = new OrganizacionStrategos();
			organizacionStrategos.setOrganizacionId(new Long(0L));
			organizacionStrategos.setPadreId(organizacionStrategosPadre.getOrganizacionId());
			organizacionStrategos.setMemos(new HashSet());
		}

		if (editarOrganizacionForm.getNombre() != null)
			organizacionStrategos.setNombre(editarOrganizacionForm.getNombre());
		else 
			organizacionStrategos.setNombre(null);

		if ((editarOrganizacionForm.getRif() != null) && (!editarOrganizacionForm.getRif().equals("")))
			organizacionStrategos.setRif(editarOrganizacionForm.getRif());
		else 
			organizacionStrategos.setRif(null);

		if (editarOrganizacionForm.getTelefono() != null)
			organizacionStrategos.setTelefono(editarOrganizacionForm.getTelefono());
		else 
			organizacionStrategos.setTelefono(null);

		if (editarOrganizacionForm.getFax() != null)
			organizacionStrategos.setFax(editarOrganizacionForm.getFax());
		else 
			organizacionStrategos.setFax(null);

		if (editarOrganizacionForm.getDireccion() != null)
			organizacionStrategos.setDireccion(editarOrganizacionForm.getDireccion());
		else 
			organizacionStrategos.setDireccion(null);

		if (editarOrganizacionForm.getEnlaceParcial() != null)
			organizacionStrategos.setEnlaceParcial(editarOrganizacionForm.getEnlaceParcial());
		else 
			organizacionStrategos.setEnlaceParcial(null);

		if (editarOrganizacionForm.getPorcentajeZonaAmarillaMinMaxIndicadores() != null)
			organizacionStrategos.setPorcentajeZonaAmarillaMinMaxIndicadores(editarOrganizacionForm.getPorcentajeZonaAmarillaMinMaxIndicadores());
		else 
			organizacionStrategos.setPorcentajeZonaAmarillaMinMaxIndicadores(null);

		if (editarOrganizacionForm.getPorcentajeZonaVerdeMetaIndicadores() != null)
			organizacionStrategos.setPorcentajeZonaVerdeMetaIndicadores(editarOrganizacionForm.getPorcentajeZonaVerdeMetaIndicadores());
		else 
			organizacionStrategos.setPorcentajeZonaVerdeMetaIndicadores(null);

		if (editarOrganizacionForm.getPorcentajeZonaAmarillaMetaIndicadores() != null)
			organizacionStrategos.setPorcentajeZonaAmarillaMetaIndicadores(editarOrganizacionForm.getPorcentajeZonaAmarillaMetaIndicadores());
		else 
			organizacionStrategos.setPorcentajeZonaAmarillaMetaIndicadores(null);

		if (editarOrganizacionForm.getPorcentajeZonaVerdeIniciativas() != null)
			organizacionStrategos.setPorcentajeZonaVerdeIniciativas(editarOrganizacionForm.getPorcentajeZonaVerdeIniciativas());
		else 
			organizacionStrategos.setPorcentajeZonaVerdeIniciativas(null);

		if (editarOrganizacionForm.getPorcentajeZonaAmarillaIniciativas() != null)
			organizacionStrategos.setPorcentajeZonaAmarillaIniciativas(editarOrganizacionForm.getPorcentajeZonaAmarillaIniciativas());
		else 
			organizacionStrategos.setPorcentajeZonaAmarillaIniciativas(null);

		organizacionStrategos.setMesCierre(editarOrganizacionForm.getMesCierre());
		organizacionStrategos.setVisible(editarOrganizacionForm.getVisible());

		organizacionStrategos.getMemos().clear();

		if (editarOrganizacionForm.getDescripcion() != null) 
		{
			MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
			memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionStrategos.getOrganizacionId(), new Integer(0)));
			memoOrganizacion.setDescripcion(editarOrganizacionForm.getDescripcion());
			organizacionStrategos.getMemos().add(memoOrganizacion);
		}

		if (editarOrganizacionForm.getObservaciones() != null) 
		{
			MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
			memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionStrategos.getOrganizacionId(), new Integer(1)));
			memoOrganizacion.setDescripcion(editarOrganizacionForm.getObservaciones());
			organizacionStrategos.getMemos().add(memoOrganizacion);
		}

		if (editarOrganizacionForm.getPersonalDirectivo() != null) 
		{
			MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
			memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionStrategos.getOrganizacionId(), new Integer(2)));
			memoOrganizacion.setDescripcion(editarOrganizacionForm.getPersonalDirectivo());
			organizacionStrategos.getMemos().add(memoOrganizacion);
		}

		if (editarOrganizacionForm.getMision() != null) 
		{
			MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
			memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionStrategos.getOrganizacionId(), new Integer(3)));
			memoOrganizacion.setDescripcion(editarOrganizacionForm.getMision());
			organizacionStrategos.getMemos().add(memoOrganizacion);
		}

		if (editarOrganizacionForm.getVision() != null) 
		{
			MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
			memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionStrategos.getOrganizacionId(), new Integer(4)));
			memoOrganizacion.setDescripcion(editarOrganizacionForm.getVision());
			organizacionStrategos.getMemos().add(memoOrganizacion);
		}

		if (editarOrganizacionForm.getOportunidadesRetos() != null) 
		{
			MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
			memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionStrategos.getOrganizacionId(), new Integer(5)));
			memoOrganizacion.setDescripcion(editarOrganizacionForm.getOportunidadesRetos());
			organizacionStrategos.getMemos().add(memoOrganizacion);
		}

		if (editarOrganizacionForm.getLineamientosEstrategicos() != null) 
		{
			MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
			memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionStrategos.getOrganizacionId(), new Integer(6)));
			memoOrganizacion.setDescripcion(editarOrganizacionForm.getLineamientosEstrategicos());
			organizacionStrategos.getMemos().add(memoOrganizacion);
		}

		if (editarOrganizacionForm.getFactoresClave() != null) 
		{
			MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
			memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionStrategos.getOrganizacionId(), new Integer(7)));
			memoOrganizacion.setDescripcion(editarOrganizacionForm.getFactoresClave());
			organizacionStrategos.getMemos().add(memoOrganizacion);
		}

		if (editarOrganizacionForm.getPoliticas() != null) 
		{
			MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
			memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionStrategos.getOrganizacionId(), new Integer(8)));
			memoOrganizacion.setDescripcion(editarOrganizacionForm.getPoliticas());
			organizacionStrategos.getMemos().add(memoOrganizacion);
		}

		if (editarOrganizacionForm.getValores() != null) 
		{
			MemoOrganizacion memoOrganizacion = new MemoOrganizacion();
			memoOrganizacion.setPk(new MemoOrganizacionPK(organizacionStrategos.getOrganizacionId(), new Integer(9)));
			memoOrganizacion.setDescripcion(editarOrganizacionForm.getValores());
			organizacionStrategos.getMemos().add(memoOrganizacion);
		}

		respuesta = strategosOrganizacionesService.saveOrganizacion(organizacionStrategos, getUsuarioConectado(request));

		if (respuesta == 10000)
		{
			strategosOrganizacionesService.unlockObject(request.getSession().getId(), editarOrganizacionForm.getOrganizacionId());
			forward = "exito";

			if (nuevo)
			{
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearOrganizacion";
			}
			else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
		}
		else if (respuesta == 10003)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
			forward = "duplicado";
		}

		strategosOrganizacionesService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("GuardarOrganizacionAction.ultimoTs", ts);
		
		if (forward.equals("exito")) 
			return getForwardBack(request, 1, true);
		else if (forward.equals("duplicado"))
			return getForwardBack(request, 1, true);

		return mapping.findForward(forward);
	}
}