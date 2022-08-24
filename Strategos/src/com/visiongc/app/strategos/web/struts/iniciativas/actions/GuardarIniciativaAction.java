package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.MemoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.ResultadoEspecificoIniciativa;
import com.visiongc.app.strategos.iniciativas.model.ResultadoEspecificoIniciativaPK;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectiva;
import com.visiongc.app.strategos.planes.model.IniciativaPerspectivaPK;
import com.visiongc.app.strategos.planes.model.IniciativaPlan;
import com.visiongc.app.strategos.planes.model.IniciativaPlanPK;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.EditarIniciativaForm;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.StringUtil;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.model.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

public class GuardarIniciativaAction extends VgcAction {
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarIniciativaForm editarIniciativaForm = (EditarIniciativaForm) form;

		ActionMessages messages = getMessages(request);

		boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
		String ts = request.getParameter("ts");
		String ultimoTs = (String) request.getSession().getAttribute("GuardarIniciativaAction.ultimoTs");
		String organizacionId = (String) request.getSession().getAttribute("organizacionId");
		Long instrumentoId = (request.getParameter("instrumentoId") != null
				&& request.getParameter("instrumentoId") != "") ? Long.parseLong(request.getParameter("instrumentoId"))
						: null;
		Boolean desdeInstrumento = (request.getParameter("desdeInstrumento") != null
				&& request.getParameter("desdeInstrumento") != "")
						? Boolean.parseBoolean(request.getParameter("desdeInstrumento"))
						: null;

		if ((ts == null) || (ts.equals("")))
			cancelar = true;
		else if ((ultimoTs != null) && (ultimoTs.equals(ts)))
			cancelar = true;

		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance()
				.openStrategosIniciativasService();

		if (cancelar) {
			strategosIniciativasService.unlockObject(request.getSession().getId(),
					editarIniciativaForm.getIniciativaId());

			strategosIniciativasService.close();

			if (desdeInstrumento != null && desdeInstrumento) {
				return getForwardBack(request, 2, true);
			} else {
				return getForwardBack(request, 1, true);
			}

		}

		boolean nuevo = false;
		Iniciativa iniciativa = null;

		int respuesta = VgcReturnCode.DB_OK;

		if ((editarIniciativaForm.getIniciativaId() != null)
				&& (!editarIniciativaForm.getIniciativaId().equals(Long.valueOf("0"))))
			iniciativa = (Iniciativa) strategosIniciativasService.load(Iniciativa.class,
					editarIniciativaForm.getIniciativaId());
		else {
			nuevo = true;
			iniciativa = new Iniciativa();
			iniciativa.setIniciativaId(new Long(0L));
			iniciativa.setMemoIniciativa(new MemoIniciativa());
			iniciativa.setResultadosEspecificosIniciativa(new HashSet<ResultadoEspecificoIniciativa>());
			iniciativa.setIniciativaPerspectivas(new HashSet<IniciativaPerspectiva>());
			iniciativa.setIniciativaPlanes(new HashSet<IniciativaPlan>());
			if (desdeInstrumento != null && desdeInstrumento) {
				iniciativa.setOrganizacionId(editarIniciativaForm.getOrganizacionId());
			} else {
				iniciativa.setOrganizacionId(new Long(organizacionId));
			}

		}

		iniciativa.setNombre(editarIniciativaForm.getNombre());

		if ((editarIniciativaForm.getNombreLargo() != null) && (!editarIniciativaForm.getNombreLargo().equals("")))
			iniciativa.setNombreLargo(editarIniciativaForm.getNombreLargo());
		else
			iniciativa.setNombreLargo(null);

		if ((editarIniciativaForm.getEnteEjecutor() != null) && (!editarIniciativaForm.getEnteEjecutor().equals("")))
			iniciativa.setEnteEjecutor(editarIniciativaForm.getEnteEjecutor());
		else
			iniciativa.setEnteEjecutor(null);

		if ((editarIniciativaForm.getAnioFormulacion() != null)
				&& (!editarIniciativaForm.getAnioFormulacion().equals("")))
			iniciativa.setAnioFormulacion(editarIniciativaForm.getAnioFormulacion());
		else
			iniciativa.setAnioFormulacion(null);

		// Campos nuevos

		if ((editarIniciativaForm.getResponsableProyecto() != null)
				&& (editarIniciativaForm.getResponsableProyecto() != ""))
			iniciativa.setResponsableProyecto(editarIniciativaForm.getResponsableProyecto());
		else
			iniciativa.setResponsableProyecto(null);

		if ((editarIniciativaForm.getCargoResponsable() != null)
				&& (!editarIniciativaForm.getCargoResponsable().equals("")))
			iniciativa.setCargoResponsable(editarIniciativaForm.getCargoResponsable());
		else
			iniciativa.setCargoResponsable(null);

		if ((editarIniciativaForm.getOrganizacionesInvolucradas() != null)
				&& (!editarIniciativaForm.getOrganizacionesInvolucradas().equals("")))
			iniciativa.setOrganizacionesInvolucradas(editarIniciativaForm.getOrganizacionesInvolucradas());
		else
			iniciativa.setOrganizacionesInvolucradas(null);

		if ((editarIniciativaForm.getObjetivoEstrategico() != null)
				&& (!editarIniciativaForm.getObjetivoEstrategico().equals("")))
			iniciativa.setObjetivoEstrategico(editarIniciativaForm.getObjetivoEstrategico());
		else
			iniciativa.setObjetivoEstrategico(null);

		if ((editarIniciativaForm.getFuenteFinanciacion() != null)
				&& (!editarIniciativaForm.getFuenteFinanciacion().equals("")))
			iniciativa.setFuenteFinanciacion(editarIniciativaForm.getFuenteFinanciacion());
		else
			iniciativa.setFuenteFinanciacion(null);

		if ((editarIniciativaForm.getMontoFinanciamiento() != null)
				&& (!editarIniciativaForm.getMontoFinanciamiento().equals("")))
			iniciativa.setMontoFinanciamiento(editarIniciativaForm.getMontoFinanciamiento());
		else
			iniciativa.setMontoFinanciamiento(null);

		if ((editarIniciativaForm.getIniciativaEstrategica() != null)
				&& (!editarIniciativaForm.getIniciativaEstrategica().equals("")))
			iniciativa.setIniciativaEstrategica(editarIniciativaForm.getIniciativaEstrategica());
		else
			iniciativa.setIniciativaEstrategica(null);

		if ((editarIniciativaForm.getLiderIniciativa() != null)
				&& (!editarIniciativaForm.getLiderIniciativa().equals("")))
			iniciativa.setLiderIniciativa(editarIniciativaForm.getLiderIniciativa());
		else
			iniciativa.setLiderIniciativa(null);

		if ((editarIniciativaForm.getTipoIniciativa() != null)
				&& (!editarIniciativaForm.getTipoIniciativa().equals("")))
			iniciativa.setTipoIniciativa(editarIniciativaForm.getTipoIniciativa());
		else
			iniciativa.setTipoIniciativa(null);

		if ((editarIniciativaForm.getPoblacionBeneficiada() != null)
				&& (!editarIniciativaForm.getPoblacionBeneficiada().equals("")))
			iniciativa.setPoblacionBeneficiada(editarIniciativaForm.getPoblacionBeneficiada());
		else
			iniciativa.setPoblacionBeneficiada(null);

		if ((editarIniciativaForm.getContexto() != null) && (!editarIniciativaForm.getContexto().equals("")))
			iniciativa.setContexto(editarIniciativaForm.getContexto());
		else
			iniciativa.setContexto(null);

		if ((editarIniciativaForm.getDefinicionProblema() != null)
				&& (!editarIniciativaForm.getDefinicionProblema().equals("")))
			iniciativa.setDefinicionProblema(editarIniciativaForm.getDefinicionProblema());
		else
			iniciativa.setDefinicionProblema(null);

		if ((editarIniciativaForm.getAlcance() != null) && (!editarIniciativaForm.getAlcance().equals("")))
			iniciativa.setAlcance(editarIniciativaForm.getAlcance());
		else
			iniciativa.setAlcance(null);

		if ((editarIniciativaForm.getObjetivoGeneral() != null)
				&& (!editarIniciativaForm.getObjetivoGeneral().equals("")))
			iniciativa.setObjetivoGeneral(editarIniciativaForm.getObjetivoGeneral());
		else
			iniciativa.setObjetivoGeneral(null);

		if ((editarIniciativaForm.getObjetivoEspecificos() != null)
				&& (!editarIniciativaForm.getObjetivoEspecificos().equals("")))
			iniciativa.setObjetivoEspecificos(editarIniciativaForm.getObjetivoEspecificos());
		else
			iniciativa.setObjetivoEspecificos(null);

		String selectTipoProyecto = request.getParameter("selectTipoProyecto");

		if (selectTipoProyecto != null && !selectTipoProyecto.equals("") && !selectTipoProyecto.equals("0")) {
			iniciativa.setTipoId(Long.parseLong(selectTipoProyecto));
		} else {
			iniciativa.setTipoId(null);
		}

		if (editarIniciativaForm.getAlertaZonaAmarilla() != null
				&& editarIniciativaForm.getHayValorPorcentajeAmarillo())
			iniciativa.setAlertaZonaAmarilla(editarIniciativaForm.getAlertaZonaAmarilla());
		else
			iniciativa.setAlertaZonaAmarilla(null);

		if (editarIniciativaForm.getAlertaZonaVerde() != null && editarIniciativaForm.getHayValorPorcentajeVerde())
			iniciativa.setAlertaZonaVerde(editarIniciativaForm.getAlertaZonaVerde());
		else
			iniciativa.setAlertaZonaVerde(null);

		Boolean cambioFrecuencia = (iniciativa != null && iniciativa.getFrecuencia() != null
				&& iniciativa.getFrecuencia().byteValue() != editarIniciativaForm.getFrecuencia().byteValue());
		if (cambioFrecuencia) {
			iniciativa.setFechaUltimaMedicion(null);
			iniciativa.setPorcentajeCompletado(null);
			iniciativa.setAlerta(null);
		}
		iniciativa.setFrecuencia(editarIniciativaForm.getFrecuencia());

		if (editarIniciativaForm.getResponsableFijarMetaId().equals(new Long(0L)))
			iniciativa.setResponsableFijarMetaId(null);
		else
			iniciativa.setResponsableFijarMetaId(editarIniciativaForm.getResponsableFijarMetaId());

		if (editarIniciativaForm.getResponsableLograrMetaId().equals(new Long(0L)))
			iniciativa.setResponsableLograrMetaId(null);
		else
			iniciativa.setResponsableLograrMetaId(editarIniciativaForm.getResponsableLograrMetaId());

		if (editarIniciativaForm.getResponsableSeguimientoId().equals(new Long(0L)))
			iniciativa.setResponsableSeguimientoId(null);
		else
			iniciativa.setResponsableSeguimientoId(editarIniciativaForm.getResponsableSeguimientoId());

		if (editarIniciativaForm.getResponsableCargarMetaId().equals(new Long(0L)))
			iniciativa.setResponsableCargarMetaId(null);
		else
			iniciativa.setResponsableCargarMetaId(editarIniciativaForm.getResponsableCargarMetaId());

		if (editarIniciativaForm.getResponsableCargarEjecutadoId().equals(new Long(0L)))
			iniciativa.setResponsableCargarEjecutadoId(null);
		else
			iniciativa.setResponsableCargarEjecutadoId(editarIniciativaForm.getResponsableCargarEjecutadoId());

		iniciativa.setTipoAlerta((byte) 0); // Calculo por alerta

		if ((editarIniciativaForm.getDescripcion() != null) && (!editarIniciativaForm.getDescripcion().equals("")))
			iniciativa.getMemoIniciativa().setDescripcion(editarIniciativaForm.getDescripcion());		
		else if (iniciativa.getMemoIniciativa() != null)
			iniciativa.getMemoIniciativa().setDescripcion(null);

		if ((editarIniciativaForm.getResultado() != null) && (!editarIniciativaForm.getResultado().equals("")))
			iniciativa.getMemoIniciativa().setResultado(editarIniciativaForm.getResultado());
		else if (iniciativa.getMemoIniciativa() != null)
			iniciativa.getMemoIniciativa().setResultado(null);

		iniciativa.getResultadosEspecificosIniciativa().clear();

		if ((editarIniciativaForm.getResultadoEspecificoIniciativa() != null)
				&& (!editarIniciativaForm.getResultadoEspecificoIniciativa().equals(""))) {
			String[] resultadosEspecificos = StringUtil.split(editarIniciativaForm.getResultadoEspecificoIniciativa(),
					editarIniciativaForm.getSEPARADOR());
			int anoCentral = FechaUtil.getAno(new Date());

			int anoTemp = anoCentral - 5;
			for (int i = 0; i < resultadosEspecificos.length; i++) {
				String resultadoEspecifico = resultadosEspecificos[i];
				if (!resultadoEspecifico.equals("")) {
					ResultadoEspecificoIniciativa resultadoEspecificoIniciativa = new ResultadoEspecificoIniciativa();

					resultadoEspecificoIniciativa.setPk(
							new ResultadoEspecificoIniciativaPK(iniciativa.getIniciativaId(), new Integer(anoTemp)));
					resultadoEspecificoIniciativa.setResultado(resultadoEspecifico);
					iniciativa.getResultadosEspecificosIniciativa().add(resultadoEspecificoIniciativa);
				}
				anoTemp++;
			}
		}

		if ((editarIniciativaForm.getPerspectivaId() != null)
				&& (editarIniciativaForm.getPerspectivaId().longValue() != 0L)) {
			IniciativaPlan iniciativaPlan = new IniciativaPlan();
			iniciativaPlan.setPk(new IniciativaPlanPK());
			iniciativaPlan.getPk().setIniciativaId(iniciativa.getIniciativaId());
			iniciativaPlan.getPk().setPlanId(editarIniciativaForm.getPlanId());
			iniciativa.getIniciativaPlanes().add(iniciativaPlan);

			IniciativaPerspectiva iniciativaPerspectiva = new IniciativaPerspectiva();
			iniciativaPerspectiva.setPk(new IniciativaPerspectivaPK());
			iniciativaPerspectiva.getPk().setIniciativaId(iniciativa.getIniciativaId());
			iniciativaPerspectiva.getPk().setPerspectivaId(editarIniciativaForm.getPerspectivaId());
			iniciativa.getIniciativaPerspectivas().add(iniciativaPerspectiva);
		} else if ((editarIniciativaForm.getPlanId() != null) && (editarIniciativaForm.getPlanId().longValue() != 0L)) {
			IniciativaPlan iniciativaPlan = new IniciativaPlan();
			iniciativaPlan.setPk(new IniciativaPlanPK());
			iniciativaPlan.getPk().setIniciativaId(iniciativa.getIniciativaId());
			iniciativaPlan.getPk().setPlanId(editarIniciativaForm.getPlanId());
			iniciativa.getIniciativaPlanes().add(iniciativaPlan);
		}
		iniciativa.setTipoMedicion(editarIniciativaForm.getTipoMedicion());

		String selectEstatusType = request.getParameter("selectEstatusType");

		if (selectEstatusType != null && !selectEstatusType.equals("") && !selectEstatusType.equals("0")
				&& ((new Long(selectEstatusType)).longValue() == EstatusType.getEstatusCencelado().longValue()
						|| (new Long(selectEstatusType)).longValue() == EstatusType.getEstatusSuspendido().longValue()))
			iniciativa.setEstatusId(Long.parseLong(selectEstatusType));
		else if (selectEstatusType != null && !selectEstatusType.equals("") && !selectEstatusType.equals("0")
				&& iniciativa.getEstatusId() != null
				&& (iniciativa.getEstatusId().longValue() == EstatusType.getEstatusCencelado().longValue()
						|| iniciativa.getEstatusId().longValue() == EstatusType.getEstatusSuspendido().longValue()))
			iniciativa.setEstatusId(
					new com.visiongc.app.strategos.web.struts.planificacionseguimiento.actions.CalcularActividadesAction()
							.CalcularEstatus(iniciativa.getPorcentajeCompletado()));

		if (editarIniciativaForm.getEliminarMediciones())
			respuesta = eliminarMediciones(iniciativa, getUsuarioConectado(request));
		if (respuesta == VgcReturnCode.DB_OK) {

			if (iniciativa.getEstatusId() != null
					&& iniciativa.getEstatusId().longValue() != EstatusType.getEstatusCencelado().longValue()
					&& iniciativa.getEstatusId().longValue() != EstatusType.getEstatusSuspendido().longValue()
					&& editarIniciativaForm.getEliminarMediciones())
				iniciativa.setEstatusId(EstatusType.getEstatusSinIniciar());
			respuesta = strategosIniciativasService.saveIniciativa(iniciativa, getUsuarioConectado(request), true);
		}
		if (respuesta == VgcReturnCode.DB_OK)
			respuesta = actualizarActividades(cambioFrecuencia, iniciativa, getUsuarioConectado(request),
					strategosIniciativasService);

		if (respuesta == VgcReturnCode.DB_OK) {
			editarIniciativaForm.setStatus(StatusUtil.getStatusSuccess());
			strategosIniciativasService.unlockObject(request.getSession().getId(),
					editarIniciativaForm.getIniciativaId());
			if (request.getSession().getAttribute("actualizarForma") == null)
				request.getSession().setAttribute("actualizarForma", "true");

			forward = "exito";
			if (nuevo) {
				StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance()
						.openStrategosInstrumentosService();

				if (instrumentoId != null && instrumentoId != 0L) {
					Instrumentos instrumento = (Instrumentos) strategosInstrumentosService.load(Instrumentos.class,
							instrumentoId);
					if (instrumento != null)
						strategosInstrumentosService.asociarInstrumento(instrumentoId, iniciativa.getIniciativaId(),
								getUsuarioConectado(request));
					else
						messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
								new ActionMessage("action.editarregistro.noencontrado"));
				}

				strategosInstrumentosService.close();

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("action.guardarregistro.nuevo.ok"));
				forward = "crearIniciativa";
			} else
				messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
						new ActionMessage("action.guardarregistro.modificar.ok"));
		} else if (respuesta == VgcReturnCode.DB_PK_AK_VIOLATED) {
			editarIniciativaForm.setStatus(StatusUtil.getStatusNoSuccess());
			forward = "exito";
			if (nuevo)
				forward = "crearIniciativa";
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE",
					new ActionMessage("action.guardarregistro.duplicado"));
		}

		strategosIniciativasService.close();

		saveMessages(request, messages);

		request.getSession().setAttribute("GuardarIniciativaAction.ultimoTs", ts);

		if (editarIniciativaForm.getFrecuencias() == null)
			editarIniciativaForm.setFrecuencias(Frecuencia.getFrecuencias());
		if (editarIniciativaForm.getGrupoAnos() == null) {
			Integer ano = FechaUtil.getAno(new Date());
			editarIniciativaForm.setGrupoAnos(PeriodoUtil.getListaNumeros(ano, new Byte((byte) 5)));
		}

		if (forward.equals("exito")) {
			if (desdeInstrumento != null && desdeInstrumento) {
				return getForwardBack(request, 2, true);
			} else {
				return getForwardBack(request, 1, true);
			}
		}

		return mapping.findForward(forward);

	}

	public int actualizarActividades(Boolean cambioFrecuencia, Iniciativa iniciativa, Usuario usuario,
			StrategosIniciativasService strategosIniciativasService) {
		int respuesta = VgcReturnCode.DB_OK;
		;

		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService(strategosIniciativasService);

		Map<String, Object> filtros = new HashMap<String, Object>();
		List<PryActividad> actividades = new ArrayList<PryActividad>();
		Date comienzoPlan = null;
		Date finPlan = null;
		Date comienzoReal = null;
		Date finReal = null;

		if (iniciativa.getProyectoId() != null) {
			filtros = new HashMap<String, Object>();
			filtros.put("proyectoId", iniciativa.getProyectoId().toString());
			filtros.put("padreId", null);

			String atributoOrden = "fila";
			String tipoOrden = "ASC";
			int pagina = 0;
			actividades = strategosPryActividadesService
					.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = (PryActividad) iter.next();
				if (comienzoPlan == null)
					comienzoPlan = actividad.getComienzoPlan();
				if (finPlan == null)
					finPlan = actividad.getFinPlan();
				if (comienzoReal == null)
					comienzoReal = actividad.getComienzoReal();
				if (finReal == null)
					finReal = actividad.getFinReal();

				if (actividad.getComienzoPlan() != null && actividad.getComienzoPlan().before(comienzoPlan))
					comienzoPlan = actividad.getComienzoPlan();
				if (actividad.getFinPlan() != null && actividad.getFinPlan().after(finPlan))
					finPlan = actividad.getFinPlan();
				if (actividad.getComienzoReal() != null && actividad.getComienzoReal().before(comienzoReal))
					comienzoReal = actividad.getComienzoReal();
				if (actividad.getFinReal() != null && actividad.getFinReal().after(finReal))
					finReal = actividad.getFinReal();
			}

			if (cambioFrecuencia) {
				filtros = new HashMap<String, Object>();
				filtros.put("proyectoId", iniciativa.getProyectoId().toString());

				atributoOrden = "fila";
				tipoOrden = "ASC";
				pagina = 0;
				StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
						.openStrategosIndicadoresService();
				actividades = strategosPryActividadesService
						.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();
				for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
					PryActividad actividad = (PryActividad) iter.next();

					actividad.setFechaUltimaMedicion(null);
					actividad.setPorcentajeCompletado(null);
					actividad.setPorcentajeEjecutado(null);
					actividad.setPorcentajeEsperado(null);
					actividad.setAlerta(null);
					if (respuesta == VgcReturnCode.DB_OK)
						respuesta = strategosPryActividadesService.saveActividad(actividad, usuario, false);

					Indicador indicador = null;
					if (actividad.getIndicadorId() != null)
						indicador = (Indicador) strategosIndicadoresService.load(Indicador.class,
								new Long(actividad.getIndicadorId()));
					if (respuesta == VgcReturnCode.DB_OK && indicador != null) {
						indicador.setFrecuencia(iniciativa.getFrecuencia());
						respuesta = strategosIndicadoresService.saveIndicador(indicador, usuario);
						if (respuesta == VgcReturnCode.DB_PK_AK_VIOLATED) {
							filtros = new HashMap<String, Object>();

							filtros.put("claseId", indicador.getClaseId());
							filtros.put("nombre", indicador.getNombre());
							List<Indicador> inds = strategosIndicadoresService
									.getIndicadores(0, 0, "nombre", "ASC", true, filtros, null, null, false).getLista();
							if (inds.size() > 0) {
								indicador = inds.get(0);
								respuesta = VgcReturnCode.DB_OK;
								;
							}
						}
					}
					if (respuesta != VgcReturnCode.DB_OK)
						break;
				}
				strategosIndicadoresService.close();
			}
		}

		if (respuesta == VgcReturnCode.DB_OK) {
			filtros = new HashMap<String, Object>();
			filtros.put("objetoAsociadoId", iniciativa.getIniciativaId().toString());

			String atributoOrden = "fila";
			String tipoOrden = "ASC";
			int pagina = 0;
			actividades = strategosPryActividadesService
					.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();

			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = (PryActividad) iter.next();

				if (iniciativa.getNombre() != null)
					actividad.setNombre(iniciativa.getNombre());
				else
					actividad.setNombre(null);

				if ((iniciativa.getMemoIniciativa() != null)
						&& (iniciativa.getMemoIniciativa().getDescripcion() != null)
						&& (!iniciativa.getMemoIniciativa().getDescripcion().equals("")))
					actividad.setDescripcion(iniciativa.getMemoIniciativa().getDescripcion());
				else
					actividad.setDescripcion(null);

				if (comienzoPlan != null)
					actividad.setComienzoPlan(comienzoPlan);
				else
					actividad.setComienzoPlan(null);

				if (finPlan != null)
					actividad.setFinPlan(finPlan);
				else
					actividad.setFinPlan(null);

				if (comienzoReal != null)
					actividad.setComienzoReal(comienzoReal);
				else
					actividad.setComienzoReal(null);

				if (finReal != null)
					actividad.setFinReal(finReal);
				else
					actividad.setFinReal(null);

				actividad.setDuracionPlan(null);

				if (iniciativa.getResponsableFijarMetaId() != null
						&& iniciativa.getResponsableFijarMetaId().equals(new Long(0L)))
					actividad.setResponsableFijarMetaId(null);
				else
					actividad.setResponsableFijarMetaId(iniciativa.getResponsableFijarMetaId());

				if (iniciativa.getResponsableLograrMetaId() != null
						&& iniciativa.getResponsableLograrMetaId().equals(new Long(0L)))
					actividad.setResponsableLograrMetaId(null);
				else
					actividad.setResponsableLograrMetaId(iniciativa.getResponsableLograrMetaId());

				if (iniciativa.getResponsableSeguimientoId() != null
						&& iniciativa.getResponsableSeguimientoId().equals(new Long(0L)))
					actividad.setResponsableSeguimientoId(null);
				else
					actividad.setResponsableSeguimientoId(iniciativa.getResponsableSeguimientoId());

				if (iniciativa.getResponsableCargarMetaId() != null
						&& iniciativa.getResponsableCargarMetaId().equals(new Long(0L)))
					actividad.setResponsableCargarMetaId(null);
				else
					actividad.setResponsableCargarMetaId(iniciativa.getResponsableCargarMetaId());

				if (iniciativa.getResponsableCargarEjecutadoId() != null
						&& iniciativa.getResponsableCargarEjecutadoId().equals(new Long(0L)))
					actividad.setResponsableCargarEjecutadoId(null);
				else
					actividad.setResponsableCargarEjecutadoId(iniciativa.getResponsableCargarEjecutadoId());

				if (iniciativa.getAlertaZonaVerde() != null)
					actividad.getPryInformacionActividad().setPorcentajeVerde(iniciativa.getAlertaZonaVerde());
				else
					actividad.getPryInformacionActividad().setPorcentajeVerde(null);

				if (iniciativa.getAlertaZonaAmarilla() != null)
					actividad.getPryInformacionActividad().setPorcentajeAmarillo(iniciativa.getAlertaZonaAmarilla());
				else
					actividad.getPryInformacionActividad().setPorcentajeAmarillo(null);

				if (iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()) != null
						&& iniciativa.getClaseId() != null) {
					actividad.setIndicadorId(
							new Long(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento())));
					actividad.setClaseId(new Long(iniciativa.getClaseId()));
				}

				respuesta = strategosPryActividadesService.saveActividad(actividad, usuario, false);
				if (respuesta != VgcReturnCode.DB_OK)
					break;
			}
		}

		strategosPryActividadesService.close();

		return respuesta;
	}

	public int eliminarMediciones(Iniciativa iniciativa, Usuario usuario) {
		int respuesta = VgcReturnCode.DB_OK;

		iniciativa.setFechaUltimaMedicion(null);
		iniciativa.setPorcentajeCompletado(null);
		iniciativa.setPorcentajeEsperado(null);
		iniciativa.setAlerta(null);

		List<Long> indicadores = new ArrayList<Long>();
		indicadores.add(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionSeguimiento()));
		indicadores.add(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficiencia()));
		indicadores.add(iniciativa.getIndicadorId(TipoFuncionIndicador.getTipoFuncionEficiencia()));

		StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance()
				.openStrategosPryActividadesService();

		Map<String, Object> filtros = new HashMap<String, Object>();
		List<PryActividad> actividades = new ArrayList<PryActividad>();

		if (iniciativa.getProyectoId() != null) {
			filtros = new HashMap<String, Object>();
			filtros.put("proyectoId", iniciativa.getProyectoId().toString());
			filtros.put("padreId", null);

			String atributoOrden = "fila";
			String tipoOrden = "ASC";
			int pagina = 0;
			actividades = strategosPryActividadesService
					.getActividades(pagina, 0, atributoOrden, tipoOrden, false, filtros).getLista();
			for (Iterator<PryActividad> iter = actividades.iterator(); iter.hasNext();) {
				PryActividad actividad = (PryActividad) iter.next();
				indicadores.add(actividad.getIndicadorId());
				actividad.setFechaUltimaMedicion(null);
				actividad.setPorcentajeCompletado(null);
				actividad.setPorcentajeEjecutado(null);
				actividad.setPorcentajeEsperado(null);
				actividad.setAlerta(null);
				actividad.setTipoMedicion(iniciativa.getTipoMedicion());
				if (respuesta == VgcReturnCode.DB_OK)
					respuesta = strategosPryActividadesService.saveActividad(actividad, usuario, false);
				if (respuesta != VgcReturnCode.DB_OK)
					break;
			}
		}
		strategosPryActividadesService.close();

		if (respuesta == VgcReturnCode.DB_OK) {
			StrategosMedicionesService strategosMedicionesService = StrategosServiceFactory.getInstance()
					.openStrategosMedicionesService();
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance()
					.openStrategosIndicadoresService();

			for (Iterator<Long> iter = indicadores.iterator(); iter.hasNext();) {
				Long indicadorId = (Long) iter.next();
				if (indicadorId != null) {
					if (respuesta == VgcReturnCode.DB_OK)
						respuesta = strategosMedicionesService.deleteMediciones(indicadorId);
					try {
						if (respuesta == VgcReturnCode.DB_OK)
							respuesta = strategosIndicadoresService.actualizarDatosIndicador(indicadorId, null, null,
									null);
					} catch (Throwable e) {
						respuesta = VgcReturnCode.DB_PK_AK_VIOLATED;
					}
				}
				if (respuesta != VgcReturnCode.DB_OK)
					break;
			}

			strategosMedicionesService.close();
			strategosIndicadoresService.close();
		}

		return respuesta;
	}
}