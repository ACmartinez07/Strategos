package com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions;

import com.visiongc.app.strategos.gantts.model.util.ActividadPeriodo;
import com.visiongc.app.strategos.gantts.model.util.Periodo;
import com.visiongc.app.strategos.gantts.util.VgcGanttGrafico;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.forms.GestionarActividadesForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GestionarActividadesAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrlSinParametros(url, nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)	throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		GestionarActividadesForm gestionarActividadesForm = (GestionarActividadesForm)form;

		String atributoOrden = gestionarActividadesForm.getAtributoOrden();
		String tipoOrden = gestionarActividadesForm.getTipoOrden();
		int pagina = gestionarActividadesForm.getPagina();
		
		Boolean desdeInstrumento = (request.getParameter("desdeInstrumento") != null && request.getParameter("desdeInstrumento") != "") ? Boolean.valueOf(request.getParameter("desdeInstrumento")) : null;
		
		
		String identar = request.getParameter("identar") != null ? request.getParameter("identar") : "";
		String ascender = request.getParameter("ascender") != null ? request.getParameter("ascender") : "";
		String seleccionados = request.getParameter("seleccionados");
		boolean mostrarGantt = false; 
		
		
		
		if (request.getParameter("mostrarGantt") != null)		
			mostrarGantt = new Long(request.getParameter("mostrarGantt")).intValue() == 1;

		if (gestionarActividadesForm.getMostrarGantt() == null) 
			gestionarActividadesForm.setMostrarGantt(new Boolean(false));

		if (request.getParameter("mostrarGantt") != null) 
			gestionarActividadesForm.setMostrarGantt(new Boolean(mostrarGantt));

		if (atributoOrden == null) 
		{
			atributoOrden = "fila";
			gestionarActividadesForm.setAtributoOrden(atributoOrden);
		}
		if (tipoOrden == null) 
		{
			tipoOrden = "ASC";
			gestionarActividadesForm.setTipoOrden(tipoOrden);
		}

		if (pagina < 1) 
			pagina = 1;

		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

		if (seleccionados != null && !seleccionados.equals("") && seleccionados.indexOf(",") == -1) 
		{
			PryActividad actividad = strategosPryActividadesService.getHermanoInmediatoAnterior(new Long(seleccionados));
			if (actividad != null && actividad.getObjetoAsociadoId() != null)
			{
				ActionMessages messages = getMessages(request);
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("jsp.gestionaractividades.identar.actividades.existe.objetoid"));
				saveMessages(request, messages);
			}
			else
			{
				boolean calcularActividad = false;
				if (identar.equals("1"))
				{
					strategosPryActividadesService.setHijoHermanoInmediatoAnterior(new Long(seleccionados), (Usuario)request.getSession().getAttribute("usuario"));
					calcularActividad = true;
				}
				else if (identar.equals("0"))
				{
					strategosPryActividadesService.setHijoAbuelo(new Long(seleccionados), (Usuario)request.getSession().getAttribute("usuario"));
					calcularActividad = true;
				}

				if (ascender.equals("1"))
					strategosPryActividadesService.moverFila(new Long(seleccionados), true, (Usuario)request.getSession().getAttribute("usuario"));
				else if (ascender.equals("0")) 
					strategosPryActividadesService.moverFila(new Long(seleccionados), false, (Usuario)request.getSession().getAttribute("usuario"));
				
				if (calcularActividad && gestionarActividadesForm.getIniciativaId() != null && gestionarActividadesForm.getIniciativaId() != 0L && gestionarActividadesForm.getPlanId() != null && gestionarActividadesForm.getPlanId() != 0L && actividad != null)
					new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction().CalcularPadre(actividad, gestionarActividadesForm.getIniciativaId(), request);
			}
		}

		Map<String, Object> filtros = new HashMap<String, Object>();

		if ((gestionarActividadesForm.getFiltroNombre() != null) && (!gestionarActividadesForm.getFiltroNombre().equals(""))) 
			filtros.put("nombre", gestionarActividadesForm.getFiltroNombre());

		filtros.put("proyectoId", gestionarActividadesForm.getProyectoId());

		if (!gestionarActividadesForm.getVerForma()) 
			filtros.put("visible", true);

		gestionarActividadesForm.setSeleccionados(seleccionados);

		PaginaLista paginaActividades = strategosPryActividadesService.getActividades(pagina, 30, atributoOrden, tipoOrden, true, filtros);
		if ((seleccionados == null || seleccionados.equals("")) && paginaActividades.getLista().size() > 0) 
		{
			gestionarActividadesForm.setSeleccionados(((PryActividad)paginaActividades.getLista().get(0)).getActividadId().toString());
			gestionarActividadesForm.setValoresSeleccionados(((PryActividad)paginaActividades.getLista().get(0)).getNombre().toString());
		}

		gestionarGantt(strategosPryActividadesService, gestionarActividadesForm, request, paginaActividades.getLista());

		paginaActividades.setTamanoSetPaginas(5);

		request.setAttribute("paginaActividades", paginaActividades);
		
		Iniciativa iniciativa = null;
		if (gestionarActividadesForm.getIniciativaId() != null)
			iniciativa = (Iniciativa)strategosPryActividadesService.load(Iniciativa.class, new Long(gestionarActividadesForm.getIniciativaId()));
		else if (gestionarActividadesForm.getProyectoId() != null)
		{
			  StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService(strategosPryActividadesService);
			  iniciativa = (Iniciativa)strategosIniciativasService.getIniciativaByProyecto(gestionarActividadesForm.getProyectoId());
			  strategosIniciativasService.close();
		}
		
		if (iniciativa != null) {
			gestionarActividadesForm.setBloqueado(false);			
		}else {
			gestionarActividadesForm.setBloqueado(false);
		}
		if (!gestionarActividadesForm.getBloqueado())
			gestionarActividadesForm.setBloqueado(iniciativa.getEstatus().getBloquearMedicion());
		gestionarActividadesForm.setNombreIniciativa(iniciativa.getNombre());
		gestionarActividadesForm.setIniciativaClaseId(iniciativa.getClaseId());

		strategosPryActividadesService.close();

		return mapping.findForward(forward);
	}

	public void gestionarGantt(StrategosPryActividadesService strategosPryActividadesService, GestionarActividadesForm gestionarActividadesForm, HttpServletRequest request, List<PryActividad> listaActividades) throws Exception
	{
		Byte zoom = gestionarActividadesForm.getZoom();
		Byte frecuenciaGantt = gestionarActividadesForm.getFrecuenciaGantt();
		
		if (frecuenciaGantt == null) 
			frecuenciaGantt = Frecuencia.getFrecuenciaMensual();

		if (zoom != null) 
		{
			if (!zoom.equals(new Byte((byte) 0)))
				frecuenciaGantt = aplicarZoom(zoom, frecuenciaGantt);
		}
		else 
			gestionarActividadesForm.setFrecuenciaGantt(Frecuencia.getFrecuenciaMensual());

		gestionarActividadesForm.setZoom(new Byte((byte) 0));

		List<?> fechaMinMaxGantt = VgcGanttGrafico.obtenerFechaInicioFinEscala(listaActividades, frecuenciaGantt);

		Calendar fechaDesde = (Calendar)fechaMinMaxGantt.get(0);
		Calendar fechaHasta = (Calendar)fechaMinMaxGantt.get(1);
		if (frecuenciaGantt.equals(Frecuencia.getFrecuenciaDiaria())) 
		{
			fechaDesde.set(5, fechaDesde.get(5) - 7);
			fechaHasta.set(5, fechaHasta.get(5) + 7);
		} 
		else 
			fechaHasta.set(1, fechaHasta.get(1) + 1);

		List<?> escalaPeriodos = VgcGanttGrafico.obtenerEscalaPeriodos(fechaDesde, fechaHasta, frecuenciaGantt, false);

		int totalPixeles = 0;

		if ((escalaPeriodos != null) && (((List<?>)escalaPeriodos.get(0)).size() > 0))
		{
			int numeroAnos = ((List<?>)escalaPeriodos.get(0)).size();

			for (int i = 0; i < numeroAnos; i++) 
				totalPixeles += ((Periodo)((List<?>)escalaPeriodos.get(0)).get(i)).getPixeles().intValue();
		}

		List<ActividadPeriodo> listaActividadesGantt = VgcGanttGrafico.obtenerActividadesPeriodo(listaActividades, fechaDesde, frecuenciaGantt);

		gestionarActividadesForm.setTotalPixeles(new Integer(totalPixeles));
		gestionarActividadesForm.setEscalaSuperior((List<?>)escalaPeriodos.get(0));
		gestionarActividadesForm.setEscalaInferior((List<?>)escalaPeriodos.get(1));
		gestionarActividadesForm.setListaActividades(listaActividadesGantt);
		gestionarActividadesForm.setFrecuenciaGantt(frecuenciaGantt);
		gestionarActividadesForm.setFrecuenciasGantt(Frecuencia.getFrecuencias());
	}

	private Byte aplicarZoom(Byte zoom, Byte frecuencia)
	{
		Byte frecuenciaZoom = new Byte(frecuencia.byteValue());
		Byte frecuenciaActual = null;
		
		List<?> frecuencias = Frecuencia.getFrecuencias();

		for (Iterator<?> i = frecuencias.iterator(); i.hasNext(); )
		{
			Frecuencia objFrecuencia = (Frecuencia)i.next();
			
			frecuenciaActual = new Byte(objFrecuencia.getFrecuenciaId().byteValue());

			if ((!frecuenciaActual.equals(Frecuencia.getFrecuenciaSemanal())) && (!frecuenciaActual.equals(Frecuencia.getFrecuenciaQuincenal()))) 
			{
				if (frecuenciaActual.equals(frecuencia)) 
				{
					if (!zoom.equals(new Byte((byte) 2)))
						break;
					if (frecuenciaActual.equals(Frecuencia.getFrecuenciaDiaria())) 
					{
						frecuenciaZoom = Frecuencia.getFrecuenciaMensual(); 
						break;
					}
					if (frecuenciaActual.equals(Frecuencia.getFrecuenciaAnual())) 
					{
						frecuenciaZoom = Frecuencia.getFrecuenciaAnual(); 
						break;
					}
					objFrecuencia = (Frecuencia)i.next();
					frecuenciaZoom = objFrecuencia.getFrecuenciaId();

					break;
				}
				frecuenciaZoom = new Byte(frecuenciaActual.byteValue());
			}
		}

		return frecuenciaZoom;
	}
}