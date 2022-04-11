package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.TipoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.GestionarExplicacionesForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GestionarExplicacionesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		String objetoId = request.getParameter("objetoId");
		String objetoKey = request.getParameter("objetoKey");
		Integer tipo = Integer.parseInt(request.getParameter("tipo"));

		boolean cancelar = false;
		if ((objetoId == null) || (objetoId.equals("")))
		{
			objetoId = (String)request.getSession().getAttribute("objetoId");
			if ((objetoId == null) || (objetoId.equals(""))) 
				cancelar = true;
		}

		if ((objetoKey == null) || (objetoKey.equals("")))
		{
			objetoKey = (String)request.getSession().getAttribute("objetoKey");
			if ((objetoKey == null) || (objetoKey.equals(""))) 
				cancelar = true;
		}

		if (cancelar)
			return getForwardBack(request, 2, true);

		GestionarExplicacionesForm gestionarExplicacionesForm = (GestionarExplicacionesForm)form;

		if (tipo.intValue() == TipoExplicacion.getTipoExplicacion().intValue())
		{
			gestionarExplicacionesForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("EXPLICACION_VIEW"));
			gestionarExplicacionesForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("EXPLICACION_EDIT"));
			gestionarExplicacionesForm.setAddForma(getPermisologiaUsuario(request).tienePermiso("EXPLICACION_ADD"));
			gestionarExplicacionesForm.setDeleteForma(getPermisologiaUsuario(request).tienePermiso("EXPLICACION_DELETE"));
		}
		else if (tipo.intValue() == TipoExplicacion.getTipoCualitativo().intValue())
		{
			gestionarExplicacionesForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("CUALITATIVOS_VIEW"));
			gestionarExplicacionesForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("CUALITATIVOS_EDIT"));
			gestionarExplicacionesForm.setAddForma(getPermisologiaUsuario(request).tienePermiso("CUALITATIVOS_ADD"));
			gestionarExplicacionesForm.setDeleteForma(getPermisologiaUsuario(request).tienePermiso("CUALITATIVOS_DELETE"));
		}
		else if (tipo.intValue() == TipoExplicacion.getTipoEjecutivo().intValue())
		{
			gestionarExplicacionesForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("EJECUTIVOS_VIEW"));
			gestionarExplicacionesForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("EJECUTIVOS_EDIT"));
			gestionarExplicacionesForm.setAddForma(getPermisologiaUsuario(request).tienePermiso("EJECUTIVOS_ADD"));
			gestionarExplicacionesForm.setDeleteForma(getPermisologiaUsuario(request).tienePermiso("EJECUTIVOS_DELETE"));
		}
		else if (tipo.intValue() == TipoExplicacion.getTipoEventos().intValue())
		{
			gestionarExplicacionesForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("EVENTOS_VIEW"));
			gestionarExplicacionesForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("EVENTOS_EDIT"));
			gestionarExplicacionesForm.setAddForma(getPermisologiaUsuario(request).tienePermiso("EVENTOS_ADD"));
			gestionarExplicacionesForm.setDeleteForma(getPermisologiaUsuario(request).tienePermiso("EVENTOS_DELETE"));
		}
		else if (tipo.intValue() == TipoExplicacion.getTipoNoticia().intValue())
		{
			gestionarExplicacionesForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("NOTICIAS_VIEW"));
			gestionarExplicacionesForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("NOTICIAS_EDIT"));
			gestionarExplicacionesForm.setAddForma(getPermisologiaUsuario(request).tienePermiso("NOTICIAS_ADD"));
			gestionarExplicacionesForm.setDeleteForma(getPermisologiaUsuario(request).tienePermiso("NOTICIAS_DELETE"));
		}
		else if (tipo.intValue() == TipoExplicacion.getTipoInstrumento().intValue())
		{
			gestionarExplicacionesForm.setVerForma(getPermisologiaUsuario(request).tienePermiso("EXPLICACION_VIEW"));
			gestionarExplicacionesForm.setEditarForma(getPermisologiaUsuario(request).tienePermiso("EXPLICACION_EDIT"));
			gestionarExplicacionesForm.setAddForma(getPermisologiaUsuario(request).tienePermiso("EXPLICACION_ADD"));
			gestionarExplicacionesForm.setDeleteForma(getPermisologiaUsuario(request).tienePermiso("EXPLICACION_DELETE"));
		}
		
		
		gestionarExplicacionesForm.setObjetoId(Long.parseLong(objetoId));
		gestionarExplicacionesForm.setTipoObjetoKey(objetoKey);
		gestionarExplicacionesForm.setTipo(tipo);
		
		String atributoOrden = gestionarExplicacionesForm.getAtributoOrden();
		String tipoOrden = gestionarExplicacionesForm.getTipoOrden();
		int pagina = gestionarExplicacionesForm.getPagina();

		if (atributoOrden == null) 
		{
			atributoOrden = "fecha";
			gestionarExplicacionesForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null) 
		{
			tipoOrden = "DESC";
			gestionarExplicacionesForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();

		Map<String, Comparable> filtros = new HashMap<String, Comparable>();

		if (tipo != null) 
			filtros.put("tipo", tipo);
		if ((gestionarExplicacionesForm.getFiltroTitulo() != null) && (!gestionarExplicacionesForm.getFiltroTitulo().equals(""))) 
			filtros.put("titulo", gestionarExplicacionesForm.getFiltroTitulo());
		if ((gestionarExplicacionesForm.getFiltroAutor() != null) && (!gestionarExplicacionesForm.getFiltroAutor().equals(""))) 
			filtros.put("autor", gestionarExplicacionesForm.getFiltroAutor());
		if ((objetoId != null) && (!objetoId.equals("")) && Long.parseLong(objetoId) != 0) 
			filtros.put("objetoId", objetoId);
		if (objetoKey.equals("Indicador"))
			filtros.put("objetoKey", "0");
		if (objetoKey.equals("Celda"))
			filtros.put("objetoKey", "1");
		if (objetoKey.equals("Iniciativa"))
			filtros.put("objetoKey", "2");
		if (objetoKey.equals("Organizacion"))
			filtros.put("objetoKey", "3");
		if (objetoKey.equals("Instrumento"))
			filtros.put("objetoKey", "6");

		PaginaLista paginaExplicaciones = strategosExplicacionesService.getExplicaciones(pagina, 30, atributoOrden, tipoOrden, true, filtros);

		paginaExplicaciones.setTamanoSetPaginas(5);

		request.setAttribute("paginaExplicaciones", paginaExplicaciones);

		if (objetoKey.equals("Indicador"))
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(objetoId));

			request.getSession().setAttribute("indicador", indicador);
			request.getSession().setAttribute("objetoKey", objetoKey);
			request.getSession().setAttribute("objetoId", objetoId);
			
			gestionarExplicacionesForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
			gestionarExplicacionesForm.setNombreObjetoKey(indicador.getNombre());
			
			strategosIndicadoresService.close();
		}

		if (objetoKey.equals("Celda"))
		{
			StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
			Celda celda = (Celda)strategosCeldasService.load(Celda.class, new Long(objetoId));

			String nombreObjetoKey = "";

			if (celda.getIndicadoresCelda() != null) 
			{
				if ((celda.getIndicadoresCelda().size() == 0) || (celda.getIndicadoresCelda().size() > 1)) 
					nombreObjetoKey = celda.getTitulo();
				else if (celda.getIndicadoresCelda().size() == 1) 
				{
					IndicadorCelda indicadorCelda = (IndicadorCelda)celda.getIndicadoresCelda().toArray()[0];
					StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
					Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, indicadorCelda.getIndicador().getIndicadorId());
					nombreObjetoKey = indicador.getNombre();
					strategosIndicadoresService.close();
				}
			}
			else nombreObjetoKey = celda.getTitulo();

			request.getSession().setAttribute("celda", celda);
			request.getSession().setAttribute("objetoKey", objetoKey);
			request.getSession().setAttribute("objetoId", objetoId);

			gestionarExplicacionesForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
			gestionarExplicacionesForm.setNombreObjetoKey(nombreObjetoKey);
			
			strategosCeldasService.close();
		}

		if (objetoKey.equals("Iniciativa"))
		{
			StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
			Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(objetoId));
			
			request.getSession().setAttribute("iniciativa", iniciativa);
			request.getSession().setAttribute("objetoKey", objetoKey);
			request.getSession().setAttribute("objetoId", objetoId);

			gestionarExplicacionesForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
			gestionarExplicacionesForm.setNombreObjetoKey(iniciativa.getNombre());
			
			strategosIniciativasService.close();
		}

		if (objetoKey.equals("Organizacion"))
		{
			OrganizacionStrategos organizacion = ((OrganizacionStrategos)request.getSession().getAttribute("organizacion"));
			
			request.getSession().setAttribute("organizacion", organizacion);
			request.getSession().setAttribute("objetoKey", objetoKey);
			request.getSession().setAttribute("objetoId", objetoId);

			gestionarExplicacionesForm.setNombreOrganizacion(organizacion.getNombre());
			gestionarExplicacionesForm.setNombreObjetoKey(organizacion.getNombre());
		}
		
		if (objetoKey.equals("Instrumento"))
		{
			
			StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
			Instrumentos instrumento = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, new Long(objetoId));
					
			request.getSession().setAttribute("instrumento", instrumento);
			request.getSession().setAttribute("objetoKey", objetoKey);
			request.getSession().setAttribute("objetoId", objetoId);
			
			gestionarExplicacionesForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
			gestionarExplicacionesForm.setNombreObjetoKey(instrumento.getNombreCorto());
			gestionarExplicacionesForm.setDesdeInstrumento(true);
			
			strategosInstrumentosService.close();
		}
		
		strategosExplicacionesService.close();

		if (paginaExplicaciones.getLista().size() > 0) 
		{
			Explicacion explicacion = (Explicacion)paginaExplicaciones.getLista().get(0);
			gestionarExplicacionesForm.setSeleccionados(explicacion.getExplicacionId().toString());
			gestionarExplicacionesForm.setValoresSeleccionados(explicacion.getTitulo());
		} 
		else 
			gestionarExplicacionesForm.setSeleccionados(null);

		return mapping.findForward(forward);
	}	
}