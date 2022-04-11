package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosTipoProyectoService;
import com.visiongc.app.strategos.iniciativas.model.util.TipoProyecto;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.EditarInstrumentosForm;
import com.visiongc.app.strategos.web.struts.tipoproyecto.forms.EditarTiposProyectoForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class EditarInstrumentosAction extends VgcAction
{
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

    String instrumentoId = request.getParameter("instrumentoId");

    boolean bloqueado = false;

    StrategosInstrumentosService strategosConveniosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
    StrategosTiposConvenioService strategosTiposConvenioService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();
	StrategosCooperantesService strategosCooperantesService = StrategosServiceFactory.getInstance().openStrategosCooperantesService();
    

    if ((instrumentoId != null) && (!instrumentoId.equals("")) && (!instrumentoId.equals("0")))
    {
      bloqueado = !strategosConveniosService.lockForUpdate(request.getSession().getId(), instrumentoId, null);

      editarInstrumentosForm.setBloqueado(new Boolean(bloqueado));

      Instrumentos instrumentos = (Instrumentos)strategosConveniosService.load(Instrumentos.class, new Long(instrumentoId));

      if (instrumentos != null)
      {
        if (bloqueado)
        {
          messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.bloqueado"));
        }

        editarInstrumentosForm.setInstrumentoId(instrumentos.getInstrumentoId());
        editarInstrumentosForm.setNombreCorto(instrumentos.getNombreCorto());
        editarInstrumentosForm.setAnio(instrumentos.getAnio());
        editarInstrumentosForm.setAreasCargo(instrumentos.getAreasCargo());
        
        if (instrumentos.getFechaInicio() != null)
        	editarInstrumentosForm.setFechaInicio(VgcFormatter.formatearFecha(instrumentos.getFechaInicio(), "formato.fecha.corta"));
		else 
			editarInstrumentosForm.setFechaInicio(null);
			
        
        if (instrumentos.getFechaTerminacion() != null)
        	editarInstrumentosForm.setFechaTerminacion(VgcFormatter.formatearFecha(instrumentos.getFechaTerminacion(), "formato.fecha.corta"));
		else 
			editarInstrumentosForm.setFechaTerminacion(null);
			
        
        if (instrumentos.getFechaProrroga() != null)
        	editarInstrumentosForm.setFechaProrroga(VgcFormatter.formatearFecha(instrumentos.getFechaProrroga(), "formato.fecha.corta"));
		else 
			editarInstrumentosForm.setFechaProrroga(null);
			
        
        if(instrumentos.getCooperanteId() != null) {
        	Cooperante cooperante = (Cooperante)strategosCooperantesService.load(Cooperante.class, new Long(instrumentos.getCooperanteId()));
            editarInstrumentosForm.setCooperanteId(instrumentos.getCooperanteId());            
            editarInstrumentosForm.setCooperante(cooperante);
            
        }else {
        	
        	editarInstrumentosForm.setCooperante(null);
            editarInstrumentosForm.setCooperanteId(null);
        }
           
        editarInstrumentosForm.setEstatus(instrumentos.getEstatus());
        
        editarInstrumentosForm.setInstrumentoMarco(instrumentos.getInstrumentoMarco());
        editarInstrumentosForm.setNombreEjecutante(instrumentos.getNombreEjecutante());
        editarInstrumentosForm.setNombreInstrumento(instrumentos.getNombreInstrumento());
        editarInstrumentosForm.setNombreReposnsableAreas(instrumentos.getNombreReposnsableAreas());
        editarInstrumentosForm.setObjetivoInstrumento(instrumentos.getObjetivoInstrumento());
        editarInstrumentosForm.setObservaciones(instrumentos.getObservaciones());
        editarInstrumentosForm.setProductos(instrumentos.getProductos());
        editarInstrumentosForm.setRecursosDolares(instrumentos.getRecursosDolares());
        editarInstrumentosForm.setRecursosPesos(instrumentos.getRecursosPesos());
        editarInstrumentosForm.setResponsableCgi(instrumentos.getResponsableCgi());
        
        if(instrumentos.getTiposConvenioId() != null) {
        	 TipoConvenio tipoConvenio = (TipoConvenio)strategosConveniosService.load(TipoConvenio.class, new Long(instrumentos.getTiposConvenioId()));
        	 editarInstrumentosForm.setTipoConvenio(tipoConvenio);
             editarInstrumentosForm.setTiposConvenioId(instrumentos.getTiposConvenioId());
        }else {
        	 editarInstrumentosForm.setTipoConvenio(null);
             editarInstrumentosForm.setTiposConvenioId(null);
        }
       
      }
      else
      {
        strategosConveniosService.unlockObject(request.getSession().getId(), new Long(instrumentoId));

        messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
        forward = "noencontrado";
      }

    }
    else
    {
      editarInstrumentosForm.clear();
    }

            
    editarInstrumentosForm.setConvenios(new ArrayList<TipoConvenio>());
    if(editarInstrumentosForm.getTiposConvenioId() != null) {
    	editarInstrumentosForm.getConvenios().addAll(editarInstrumentosForm.getConvenios());
    }
    
    // tipo convenio
    
    Map<String, String> filtrosTipoConvenio = new HashMap();
    PaginaLista paginaTipos = strategosTiposConvenioService.getTiposConvenio(0, 0, "tiposConvenioId", "asc", true, filtrosTipoConvenio);
    
    for (Iterator<TipoConvenio> iter = paginaTipos.getLista().iterator(); iter.hasNext(); ) 
	{
			TipoConvenio tipoProyecto = (TipoConvenio)iter.next();
			editarInstrumentosForm.getConvenios().add(tipoProyecto);
	}
		
    editarInstrumentosForm.setCooperantes(new ArrayList<Cooperante>());
    if(editarInstrumentosForm.getCooperanteId() != null) {
    	editarInstrumentosForm.getCooperantes().addAll(editarInstrumentosForm.getCooperantes());
    }
    
    Map<String, String> filtrosCooperantes = new HashMap();
    PaginaLista paginaCooperantes = strategosCooperantesService.getCooperantes(0, 0, "cooperanteId", "asc", true, filtrosCooperantes);
    
    for (Iterator<Cooperante> iter = paginaCooperantes.getLista().iterator(); iter.hasNext(); ) 
	{
			Cooperante cooperante = (Cooperante)iter.next();
			editarInstrumentosForm.getCooperantes().add(cooperante);
	}
            
    if ((editarInstrumentosForm.getFechaInicio() != null) && (!editarInstrumentosForm.getFechaInicio().equals(""))) 
    {
    	Date dateFecha = FechaUtil.convertirStringToDate(editarInstrumentosForm.getFechaInicio(), "formato.fecha.corta");
    	FechaUtil.setHoraInicioDia(dateFecha);
    	Calendar calFecha = Calendar.getInstance();
    	calFecha.setTime(dateFecha);
    	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    	editarInstrumentosForm.setFechaInicioTexto(format.format(dateFecha));    	
    }
    if ((editarInstrumentosForm.getFechaTerminacion() != null) && (!editarInstrumentosForm.getFechaTerminacion().equals(""))) 
    {
    	Date dateFecha = FechaUtil.convertirStringToDate(editarInstrumentosForm.getFechaTerminacion(), "formato.fecha.corta");
    	FechaUtil.setHoraFinalDia(dateFecha);
    	Calendar calFecha = Calendar.getInstance();
    	calFecha.setTime(dateFecha);
    	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    	editarInstrumentosForm.setFechaTerminacionTexto(format.format(dateFecha));
    }
    if ((editarInstrumentosForm.getFechaProrroga() != null) && (!editarInstrumentosForm.getFechaProrroga().equals(""))) 
    {
    	Date dateFecha = FechaUtil.convertirStringToDate(editarInstrumentosForm.getFechaProrroga(), "formato.fecha.corta");
    	FechaUtil.setHoraFinalDia(dateFecha);
    	Calendar calFecha = Calendar.getInstance();
    	calFecha.setTime(dateFecha);
    	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    	editarInstrumentosForm.setFechaProrrogaTexto(format.format(dateFecha));    	
    }        
        
    strategosConveniosService.close();

    saveMessages(request, messages);

    if (forward.equals("noencontrado"))
    {
      return getForwardBack(request, 1, true);
    }
    return mapping.findForward(forward);
  }
}