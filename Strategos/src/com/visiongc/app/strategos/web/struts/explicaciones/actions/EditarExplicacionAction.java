package com.visiongc.app.strategos.web.struts.explicaciones.actions;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.AdjuntoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.ObjetivoKey;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.TipoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoObjetoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.presentaciones.StrategosCeldasService;
import com.visiongc.app.strategos.presentaciones.model.Celda;
import com.visiongc.app.strategos.presentaciones.model.IndicadorCelda;
import com.visiongc.app.strategos.web.struts.explicaciones.forms.EditarExplicacionForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarExplicacionAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarExplicacionForm editarExplicacionForm = (EditarExplicacionForm)form;
		editarExplicacionForm.clear();

		ActionMessages messages = getMessages(request);

		String explicacionId = request.getParameter("explicacionId");
		Integer tipo = Integer.parseInt(request.getParameter("tipo"));

		Boolean verForm = null;
		Boolean editarForm = null;
		Boolean publicar = null;
		if (tipo.intValue() == TipoExplicacion.getTipoExplicacion().intValue())
		{
			verForm = getPermisologiaUsuario(request).tienePermiso("EXPLICACION_VIEW");
			editarForm = getPermisologiaUsuario(request).tienePermiso("EXPLICACION_EDIT");
			publicar = getPermisologiaUsuario(request).tienePermiso("EXPLICACION_PUBLIC");
		}
		else if (tipo.intValue() == TipoExplicacion.getTipoCualitativo().intValue())
		{
			verForm = getPermisologiaUsuario(request).tienePermiso("CUALITATIVOS_VIEW");
			editarForm = getPermisologiaUsuario(request).tienePermiso("CUALITATIVOS_EDIT");
			publicar = getPermisologiaUsuario(request).tienePermiso("CUALITATIVOS_PUBLIC");
		}
		else if (tipo.intValue() == TipoExplicacion.getTipoEjecutivo().intValue())
		{
			verForm = getPermisologiaUsuario(request).tienePermiso("EJECUTIVOS_VIEW");
			editarForm = getPermisologiaUsuario(request).tienePermiso("EJECUTIVOS_EDIT");
			publicar = getPermisologiaUsuario(request).tienePermiso("EJECUTIVOS_PUBLIC");
		}
		else if (tipo.intValue() == TipoExplicacion.getTipoEventos().intValue())
		{
			verForm = getPermisologiaUsuario(request).tienePermiso("EVENTOS_VIEW");
			editarForm = getPermisologiaUsuario(request).tienePermiso("EVENTOS_EDIT");
			publicar = getPermisologiaUsuario(request).tienePermiso("EVENTOS_PUBLIC");
		}
		else if (tipo.intValue() == TipoExplicacion.getTipoNoticia().intValue())
		{
			verForm = getPermisologiaUsuario(request).tienePermiso("NOTICIAS_VIEW");
			editarForm = getPermisologiaUsuario(request).tienePermiso("NOTICIAS_EDIT");
			publicar = getPermisologiaUsuario(request).tienePermiso("NOTICIAS_PUBLIC");
		}
		else if (tipo.intValue() == TipoExplicacion.getTipoInstrumento().intValue())
		{
			verForm = getPermisologiaUsuario(request).tienePermiso("EXPLICACION_VIEW");
			editarForm = getPermisologiaUsuario(request).tienePermiso("EXPLICACION_EDIT");
			publicar = getPermisologiaUsuario(request).tienePermiso("EXPLICACION_PUBLIC");
		}
		
		
		boolean bloqueado = false;
		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");

		StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();

		if ((explicacionId != null) && (!explicacionId.equals("")) && (!explicacionId.equals("0")))
		{
			bloqueado = !strategosExplicacionesService.lockForUpdate(request.getSession().getId(), explicacionId, null);

			editarExplicacionForm.setBloqueado(new Boolean(bloqueado));

			Explicacion explicacion = (Explicacion)strategosExplicacionesService.load(Explicacion.class, new Long(explicacionId));

			if (explicacion != null)
			{
				if (bloqueado)
					messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));

				editarExplicacionForm.setExplicacionId(explicacion.getExplicacionId());
				editarExplicacionForm.setCreadoId(explicacion.getCreadoId());
				editarExplicacionForm.setObjetoKey(explicacion.getObjetoKey());
				editarExplicacionForm.setObjetoId(explicacion.getObjetoId());
				editarExplicacionForm.setTitulo(explicacion.getTitulo());
				editarExplicacionForm.setNombreUsuarioCreado(explicacion.getUsuarioCreado().getFullName());
				editarExplicacionForm.setFechaCreado(VgcFormatter.formatearFecha(explicacion.getCreado(), "formato.fecha.corta"));
				editarExplicacionForm.setNombreTipoObjetoKey(TipoObjetoExplicacion.getTipoExplicacion(explicacion.getObjetoKey().byteValue()));
				editarExplicacionForm.setTipo(explicacion.getTipo());
				editarExplicacionForm.setPublico(explicacion.getPublico());

				if (explicacion.getFecha() != null)
					editarExplicacionForm.setFecha(VgcFormatter.formatearFecha(explicacion.getFecha(), "formato.fecha.corta"));
				else 
					editarExplicacionForm.setFecha(null);
				if (explicacion.getFechaCompromiso() != null)
					editarExplicacionForm.setFechaCompromiso(VgcFormatter.formatearFecha(explicacion.getFechaCompromiso(), "formato.fecha.corta"));
				else 
					editarExplicacionForm.setFechaCompromiso(null);
				if (explicacion.getFechaReal() != null)
					editarExplicacionForm.setFechaReal(VgcFormatter.formatearFecha(explicacion.getFechaReal(), "formato.fecha.corta"));
				else 
					editarExplicacionForm.setFechaReal(null);

				if (explicacion.getAdjuntosExplicacion() != null)
				{
					editarExplicacionForm.setAdjuntosExplicacion(new ArrayList());
					for (Iterator<?> iter = explicacion.getAdjuntosExplicacion().iterator(); iter.hasNext(); )
					{
						AdjuntoExplicacion adjunto = (AdjuntoExplicacion)iter.next();
						editarExplicacionForm.getAdjuntosExplicacion().add(adjunto);
					}
					editarExplicacionForm.setNumeroAdjuntos(new Long(editarExplicacionForm.getAdjuntosExplicacion().size()));
				}

				if (explicacion.getObjetoKey().byteValue() == ObjetivoKey.getKeyIndicador().byteValue())
				{
					StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
					
					Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, explicacion.getObjetoId());

					editarExplicacionForm.setNombreObjetoKey(indicador.getNombre());

					StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();

					OrganizacionStrategos organizacion = (OrganizacionStrategos)strategosOrganizacionesService.load(OrganizacionStrategos.class, indicador.getOrganizacionId());

					editarExplicacionForm.setNombreOrganizacion(organizacion.getNombre());

					strategosIndicadoresService.close();
					strategosOrganizacionesService.close();
				}
				
				if (explicacion.getObjetoKey().byteValue() == ObjetivoKey.getKeyOrganizacion().byteValue())
				{
					OrganizacionStrategos organizacion = ((OrganizacionStrategos)request.getSession().getAttribute("organizacion"));
					editarExplicacionForm.setNombreOrganizacion(organizacion.getNombre());
					editarExplicacionForm.setNombreObjetoKey(organizacion.getNombre());
					editarExplicacionForm.setObjetoKey(ObjetivoKey.getKeyOrganizacion());
					editarExplicacionForm.setObjetoId(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
				}
				
				if (explicacion.getObjetoKey().byteValue() == ObjetivoKey.getKeyInstrumento().byteValue())
				{
					
					StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
					StrategosOrganizacionesService strategosOrganizacionesService = StrategosServiceFactory.getInstance().openStrategosOrganizacionesService();
					
					Instrumentos instrumento = (Instrumentos) strategosInstrumentosService.load(Instrumentos.class, explicacion.getObjetoId());
					editarExplicacionForm.setNombreObjetoKey(instrumento.getNombreCorto());
					editarExplicacionForm.setObjetoKey(ObjetivoKey.getKeyInstrumento());
					
					OrganizacionStrategos organizacion = ((OrganizacionStrategos)request.getSession().getAttribute("organizacion"));
					editarExplicacionForm.setNombreOrganizacion(organizacion.getNombre());
					
					strategosOrganizacionesService.close();
					strategosInstrumentosService.close();
										
				}

				for (Iterator<?> i = explicacion.getMemosExplicacion().iterator(); i.hasNext(); ) 
				{
					MemoExplicacion memoExplicacion = (MemoExplicacion)i.next();
					Byte tipoMemo = memoExplicacion.getPk().getTipo();
					if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
						editarExplicacionForm.setMemoDescripcion(memoExplicacion.getMemo());
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_CAUSAS)))
						editarExplicacionForm.setMemoCausas(memoExplicacion.getMemo());
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_CORRECTIVOS)))
						editarExplicacionForm.setMemoCorrectivos(memoExplicacion.getMemo());
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_IMPACTOS)))
						editarExplicacionForm.setMemoImpactos(memoExplicacion.getMemo());
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_PERSPECTIVAS)))
						editarExplicacionForm.setMemoPerspectivas(memoExplicacion.getMemo());
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_URL))) 
						editarExplicacionForm.setMemoUrl(memoExplicacion.getMemo());
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_LOGRO1))) 
						editarExplicacionForm.setLogro1(memoExplicacion.getMemo());
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_LOGRO2))) 
						editarExplicacionForm.setLogro2(memoExplicacion.getMemo());
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_LOGRO3))) 
						editarExplicacionForm.setLogro3(memoExplicacion.getMemo());
					else if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_LOGRO4))) 
						editarExplicacionForm.setLogro4(memoExplicacion.getMemo());
				}
			}
			else
			{
				strategosExplicacionesService.unlockObject(request.getSession().getId(), new Long(explicacionId));
				
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
				forward = "noencontrado";
			}
		}
		else
		{
			editarExplicacionForm.clear();

			editarExplicacionForm.setNombreOrganizacion(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
			editarExplicacionForm.setNombreTipoObjetoKey((String)request.getSession().getAttribute("objetoKey"));
			editarExplicacionForm.setNombreUsuarioCreado(((Usuario)request.getSession().getAttribute("usuario")).getFullName());
			editarExplicacionForm.setCreadoId(((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId());
			editarExplicacionForm.setFechaCreado(VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta"));
			editarExplicacionForm.setNombreUsuarioModificado(((Usuario)request.getSession().getAttribute("usuario")).getFullName());
			editarExplicacionForm.setModificadoId(((Usuario)request.getSession().getAttribute("usuario")).getUsuarioId());
			editarExplicacionForm.setFechaModificado(VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta"));
			editarExplicacionForm.setTipo(tipo);
			
			if (publicar == null && usuario.getIsAdmin())
				editarExplicacionForm.setPublico(true);
			else if (publicar == null)
				editarExplicacionForm.setPublico(false);
			else if (publicar != null)
				editarExplicacionForm.setPublico(publicar);
			if (tipo.intValue() == TipoExplicacion.getTipoCualitativo().intValue())
				editarExplicacionForm.setFecha(VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta"));
			else if (tipo.intValue() == TipoExplicacion.getTipoEjecutivo().intValue())
				editarExplicacionForm.setFecha(VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta"));
			else if (tipo.intValue() == TipoExplicacion.getTipoEventos().intValue())
				editarExplicacionForm.setFecha(VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta"));
			else if (tipo.intValue() == TipoExplicacion.getTipoNoticia().intValue())
				editarExplicacionForm.setFecha(VgcFormatter.formatearFecha(new Date(), "formato.fecha.corta"));
			
			if (editarExplicacionForm.getNombreTipoObjetoKey().equals("Indicador")) 
			{
				editarExplicacionForm.setNombreObjetoKey(((Indicador)request.getSession().getAttribute("indicador")).getNombre());
				editarExplicacionForm.setObjetoKey(ObjetivoKey.getKeyIndicador());
				editarExplicacionForm.setObjetoId(((Indicador)request.getSession().getAttribute("indicador")).getIndicadorId());
			}

			if (editarExplicacionForm.getNombreTipoObjetoKey().equals("Celda"))
			{
				StrategosCeldasService strategosCeldasService = StrategosServiceFactory.getInstance().openStrategosCeldasService();
				Celda celda = (Celda)strategosCeldasService.load(Celda.class, ((Celda)request.getSession().getAttribute("celda")).getCeldaId());

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
					}
				}	
				else 
					nombreObjetoKey = celda.getTitulo();

				editarExplicacionForm.setNombreObjetoKey(nombreObjetoKey);
				editarExplicacionForm.setObjetoKey(ObjetivoKey.getKeyCelda());
				editarExplicacionForm.setObjetoId(((Celda)request.getSession().getAttribute("celda")).getCeldaId());
			}

			if (editarExplicacionForm.getNombreTipoObjetoKey().equals("Iniciativa")) 
			{
				editarExplicacionForm.setNombreObjetoKey(((Iniciativa)request.getSession().getAttribute("iniciativa")).getNombre());
				editarExplicacionForm.setObjetoKey(ObjetivoKey.getKeyIniciativa());
				editarExplicacionForm.setObjetoId(((Iniciativa)request.getSession().getAttribute("iniciativa")).getIniciativaId());
			}
			
			if (editarExplicacionForm.getNombreTipoObjetoKey().equals("Organizacion"))
			{
				editarExplicacionForm.setNombreObjetoKey(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getNombre());
				editarExplicacionForm.setObjetoKey(ObjetivoKey.getKeyOrganizacion());
				editarExplicacionForm.setObjetoId(((OrganizacionStrategos)request.getSession().getAttribute("organizacion")).getOrganizacionId());
			}
			
			if (editarExplicacionForm.getNombreTipoObjetoKey().equals("Instrumento"))
			{
				editarExplicacionForm.setNombreObjetoKey(((Instrumentos)request.getSession().getAttribute("instrumento")).getNombreCorto());
				editarExplicacionForm.setObjetoKey(ObjetivoKey.getKeyInstrumento());
				editarExplicacionForm.setObjetoId(((Instrumentos)request.getSession().getAttribute("instrumento")).getInstrumentoId());
			}
		}

		if (tipo.intValue() == TipoExplicacion.getTipoExplicacion().intValue())
			editarExplicacionForm.setOnlyView(getPermisologiaUsuario(request).tienePermiso("EXPLICACION_VIEW"));
		else if (tipo.intValue() == TipoExplicacion.getTipoCualitativo().intValue())
			editarExplicacionForm.setOnlyView(getPermisologiaUsuario(request).tienePermiso("CUALITATIVOS_VIEW"));
		else if (tipo.intValue() == TipoExplicacion.getTipoEjecutivo().intValue())
			editarExplicacionForm.setOnlyView(getPermisologiaUsuario(request).tienePermiso("EJECUTIVOS_VIEW"));
		else if (tipo.intValue() == TipoExplicacion.getTipoEventos().intValue())
			editarExplicacionForm.setOnlyView(getPermisologiaUsuario(request).tienePermiso("EVENTOS_VIEW"));
		else if (tipo.intValue() == TipoExplicacion.getTipoNoticia().intValue())
			editarExplicacionForm.setOnlyView(getPermisologiaUsuario(request).tienePermiso("NOTICIAS_VIEW"));
		else if (tipo.intValue() == TipoExplicacion.getTipoInstrumento().intValue())
			editarExplicacionForm.setOnlyView(getPermisologiaUsuario(request).tienePermiso("EXPLICACION_VIEW"));
		editarExplicacionForm.setAddPublico(publicar == null ? (usuario.getIsAdmin() ? true : false) : publicar);

		strategosExplicacionesService.close();

		if (!bloqueado && verForm && !editarForm)
		{
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sololectura"));
			editarExplicacionForm.setBloqueado(true);
		}
		else if (!bloqueado && !verForm && !editarForm)
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.sinpermiso"));
		
		saveMessages(request, messages);

		if (forward.equals("noencontrado")) 
			return getForwardBack(request, 1, true);
    
		return mapping.findForward(forward);
	}
}