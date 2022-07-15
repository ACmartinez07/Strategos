package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.categoriasmedicion.forms.EditarCategoriaMedicionForm;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.EditarInstrumentosForm;
import com.visiongc.app.strategos.web.struts.tipoproyecto.forms.EditarTiposProyectoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class GuardarInstrumentosAction extends VgcAction
{
  private static final String ACTION_KEY = "GuardarTiposProyectoAction";

  public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
  {
  }

  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);

    String forward = mapping.getParameter();

    EditarInstrumentosForm editarInstrumentosForm = (EditarInstrumentosForm)form;
    
    ActionMessages messages = getMessages(request);

    boolean cancelar = mapping.getPath().toLowerCase().indexOf("cancelar") > -1;
    String ts = request.getParameter("ts");
    String ultimoTs = (String)request.getSession().getAttribute("GuardarInstrumentosAction.ultimoTs");

    if ((ts == null) || (ts.equals("")))
      cancelar = true;
    else if ((ultimoTs != null) && 
      (ultimoTs.equals(ts))) {
      cancelar = true;
    }

    StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();

    if (cancelar)
    {
      strategosInstrumentosService.unlockObject(request.getSession().getId(), editarInstrumentosForm);

      strategosInstrumentosService.close();

      return getForwardBack(request, 2, true);
    }

    Instrumentos instrumento = new Instrumentos();
    boolean nuevo = false;
    int respuesta = 10000;

    instrumento.setInstrumentoId(editarInstrumentosForm.getInstrumentoId());
    instrumento.setNombreCorto(editarInstrumentosForm.getNombreCorto());

    if ((editarInstrumentosForm.getInstrumentoId() != null) && (!editarInstrumentosForm.getInstrumentoId().equals(Long.valueOf("0"))))
    {
      instrumento = (Instrumentos)strategosInstrumentosService.load(Instrumentos.class, editarInstrumentosForm.getInstrumentoId());
    }
    else
    {
      nuevo = true;
      instrumento = new Instrumentos();
      instrumento.setInstrumentoId(new Long(0L));
    }
    	
    instrumento.setAnio(editarInstrumentosForm.getAnio());
    instrumento.setAreasCargo(editarInstrumentosForm.getAreasCargo());
    
    instrumento.setEstatus(editarInstrumentosForm.getEstatus());
    
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	
	
	if (editarInstrumentosForm.getFechaInicio() != null && (!editarInstrumentosForm.getFechaInicio().equals("")))
    {
		Calendar calFechaInicio = Calendar.getInstance();
		calFechaInicio.setTime(simpleDateFormat.parse(editarInstrumentosForm.getFechaInicio()));
		calFechaInicio = PeriodoUtil.inicioDelDia(calFechaInicio);
		instrumento.setFechaInicio(calFechaInicio.getTime());
    }
    else 
    	instrumento.setFechaInicio(null);


    if (editarInstrumentosForm.getFechaTerminacion() != null && (!editarInstrumentosForm.getFechaTerminacion().equals("")))
    {
    	Calendar calFechaTerminacion = Calendar.getInstance();
    	calFechaTerminacion.setTime(simpleDateFormat.parse(editarInstrumentosForm.getFechaTerminacion()));
    	calFechaTerminacion = PeriodoUtil.finDelDia(calFechaTerminacion);
    	instrumento.setFechaTerminacion(calFechaTerminacion.getTime());
    }
    else 
    	instrumento.setFechaTerminacion(null);
 
    
    if (editarInstrumentosForm.getFechaProrroga() != null && (!editarInstrumentosForm.getFechaProrroga().equals("")))
    {
    	Calendar calFechaProrroga = Calendar.getInstance();
    	calFechaProrroga.setTime(simpleDateFormat.parse(editarInstrumentosForm.getFechaProrroga()));
    	calFechaProrroga = PeriodoUtil.finDelDia(calFechaProrroga);
    	instrumento.setFechaProrroga(calFechaProrroga.getTime());
    }
    else 
    	instrumento.setFechaProrroga(null);
	
	
	String selectTipoConvenio = request.getParameter("selectTipoConvenio");
	
	if(selectTipoConvenio != null && !selectTipoConvenio.equals("") && !selectTipoConvenio.equals("0")){
		instrumento.setTiposConvenioId(Long.parseLong(selectTipoConvenio));
	}else{
		instrumento.setTiposConvenioId(null);
	}
	
	String selectCooperante = request.getParameter("selectCooperante");
	
	if(selectCooperante != null && !selectCooperante.equals("") && !selectCooperante.equals("0")){
		instrumento.setCooperanteId(Long.parseLong(selectCooperante));
		
	}else{
		
		instrumento.setCooperanteId(null);
	}

    instrumento.setInstrumentoMarco(editarInstrumentosForm.getInstrumentoMarco());
    instrumento.setNombreCorto(editarInstrumentosForm.getNombreCorto());
    instrumento.setNombreEjecutante(editarInstrumentosForm.getNombreEjecutante());
    instrumento.setNombreInstrumento(editarInstrumentosForm.getNombreInstrumento());
    instrumento.setNombreReposnsableAreas(editarInstrumentosForm.getNombreReposnsableAreas());
    instrumento.setObjetivoInstrumento(editarInstrumentosForm.getObjetivoInstrumento());
    instrumento.setObservaciones(editarInstrumentosForm.getObservaciones());
    instrumento.setProductos(editarInstrumentosForm.getProductos());
    instrumento.setRecursosDolares(editarInstrumentosForm.getRecursosDolares());
    instrumento.setRecursosPesos(editarInstrumentosForm.getRecursosPesos());
    instrumento.setResponsableCgi(editarInstrumentosForm.getResponsableCgi());
    
 
    respuesta = strategosInstrumentosService.saveInstrumentos(instrumento, getUsuarioConectado(request), true);

    if (respuesta == 10000)
    {
      strategosInstrumentosService.unlockObject(request.getSession().getId(), editarInstrumentosForm.getInstrumentoId());
      forward = "exito";

      if (nuevo)
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.nuevo.ok"));
        forward = "crearInstrumento";
      }
      else
      {
        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.modificar.ok"));
      }

    }
    else if (respuesta == 10003)
    {
      messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.guardarregistro.duplicado"));
    }

    strategosInstrumentosService.close();

    saveMessages(request, messages);

    request.getSession().setAttribute("GuardarInstrumentosAction.ultimoTs", ts);

    if (forward.equals("exito")) {
      return getForwardBack(request, 2, true);
    }
    return mapping.findForward(forward);
  }
}