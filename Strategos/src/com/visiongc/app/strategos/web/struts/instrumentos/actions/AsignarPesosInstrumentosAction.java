package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativa;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoPeso;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.EditarInstrumentosForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.web.struts.forms.FiltroForm;

/**
 * @author andres_martinez
 *
 */
public class AsignarPesosInstrumentosAction extends VgcAction {

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		
		EditarInstrumentosForm editarInstrumentosForm = (EditarInstrumentosForm)form;
		StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();		
		
		String anio = request.getParameter("anio") != null ? request.getParameter("anio") : "";
		String estatusSt = request.getParameter("estatus") != null ? request.getParameter("estatus") : "";
		PaginaLista paginaInstrumentos  = new PaginaLista();
		
		Byte estatus = 0;
		
		if(estatusSt != null && estatusSt != "") {
			estatus = Byte.valueOf(estatusSt);
		}					
						
		List<InstrumentoPeso> instrumentoPesos = strategosInstrumentosService.getInstrumentoPeso(anio);
		List<InstrumentoPeso> instrumentos = new ArrayList<InstrumentoPeso>();
		
		for(Iterator<InstrumentoPeso> iter = instrumentoPesos.iterator(); iter.hasNext();) {
			InstrumentoPeso instrumentoPeso = (InstrumentoPeso)iter.next();						
			Instrumentos instrumento = (Instrumentos) strategosInstrumentosService.load(Instrumentos.class, instrumentoPeso.getInstrumentoId());
			List<InstrumentoIniciativa> instrumentoIniciativas = strategosInstrumentosService.getIniciativasInstrumento(instrumento.getInstrumentoId());
			if(instrumento.getEstatus().byteValue() == estatus.byteValue() && instrumentoIniciativas.size() > 0) {					
				instrumentoPeso.setInstrumento(instrumento);
				instrumentos.add(instrumentoPeso);
			}						
		}				
		
	    if (instrumentos.size() > 0) 
		{
	    	editarInstrumentosForm.clear();
	    	
	    	if (request.getParameter("funcion") != null) 
		    {	    			    		
	    		String funcion = request.getParameter("funcion");
	    		if (funcion.equals("guardar")) {	    			
	    			int respuesta = VgcReturnCode.DB_OK;
	    			respuesta = guardarPesos(strategosInstrumentosService, editarInstrumentosForm, request);	    			
	    			if (respuesta == VgcReturnCode.DB_OK) {	    				
	    				editarInstrumentosForm.setStatus(StatusUtil.getStatusSuccess());
	    				StatusUtil.getStatusSuccess();
	    			}
		    	    else
		    	    	editarInstrumentosForm.setStatus(StatusUtil.getStatusInvalido());
	    		}
		    }				    		    	
			
	    	editarInstrumentosForm.setAnio(anio);
			editarInstrumentosForm.setEstatus(estatus);
			
		    paginaInstrumentos.setLista(instrumentos);
		    paginaInstrumentos.setTamanoPagina(5);
		}
	    else {
	    	if(editarInstrumentosForm != null) {
	    		editarInstrumentosForm.clear();	   		    		
	    		editarInstrumentosForm.setAnio(anio);
				editarInstrumentosForm.setEstatus(estatus);
	    	}
	    	
	    	
	    	paginaInstrumentos.setLista(new ArrayList<InstrumentoPeso>());
	    	paginaInstrumentos.setTamanoSetPaginas(5);
	    }
	    	    
	    
	    paginaInstrumentos.setTamanoSetPaginas(5);

	    request.setAttribute("paginaInstrumentos", paginaInstrumentos);	    	   
	    	    
	    strategosInstrumentosService.close();

		return mapping.findForward(forward);
	}


	private int guardarPesos(StrategosInstrumentosService strategosInstrumentosService, EditarInstrumentosForm editarInstrumentosForm, HttpServletRequest request ) throws Exception {
		List<InstrumentoPeso> instrumentoPesos = new ArrayList<InstrumentoPeso>();
		Map<?, ?> nombresParametros = request.getParameterMap();		
		for (Iterator<?> iter = nombresParametros.keySet().iterator(); iter.hasNext();) {
			
			String nombre = (String)iter.next();
			int index = nombre.indexOf("pesoInstrumento");
			
			if(index > -1) {
				InstrumentoPeso instrumentoPeso = new InstrumentoPeso();
				instrumentoPeso.setInstrumentoId(new Long(nombre.substring("pesoInstrumento".length())));
				if ((request.getParameter(nombre) != null) && (!request.getParameter(nombre).equals("")))
					instrumentoPeso.setPeso(new Double(VgcFormatter.parsearNumeroFormateado(request.getParameter(nombre))));				
				instrumentoPesos.add(instrumentoPeso);
			}
		}
		return strategosInstrumentosService.saveInstrumentoPeso(instrumentoPesos, getUsuarioConectado(request));
	}
}
