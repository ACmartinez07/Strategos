/**
 * 
 */
package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.instrumentos.StrategosInstrumentosService;
import com.visiongc.app.strategos.instrumentos.model.Instrumentos;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.EditarInstrumentosForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.web.NavigationBar;

import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativa;
import com.visiongc.app.strategos.instrumentos.model.InstrumentoIniciativaPK;
/**
 * @author andres_martinez
 *
 */
public class AsignarPesosIniciativasInstrumentoAction extends VgcAction{

	@Override
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {	
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

	    String forward = mapping.getParameter();

	    EditarInstrumentosForm editarInstrumentosForm = (EditarInstrumentosForm)form;
	    StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();	    
	    
	    Long id = (request.getParameter("id") != null && request.getParameter("id") != "" ? Long.parseLong(request.getParameter("id")) : 0L);
	    Instrumentos instrumento =(Instrumentos)strategosInstrumentosService.load(Instrumentos.class,new Long(id));
	    PaginaLista paginaIniciativas = new PaginaLista();
	    
	    if(instrumento != null) {
	    	editarInstrumentosForm.clear();
	    	editarInstrumentosForm.setInstrumentoId(id);
	    	
	    	if (request.getParameter("funcion") != null) 
		    {	    		
	    		String funcion = request.getParameter("funcion");
	    		if (funcion.equals("guardar")) {	    			
	    			int respuesta = VgcReturnCode.DB_OK;
	    			respuesta = guardarPesos(strategosInstrumentosService, editarInstrumentosForm, request);	
	    			if (respuesta == VgcReturnCode.DB_OK)
	    				editarInstrumentosForm.setStatus(StatusUtil.getStatusSuccess());
		    	    else
		    	    	editarInstrumentosForm.setStatus(StatusUtil.getStatusInvalido());
	    		}
		    }
	    	
	    	List<InstrumentoIniciativa> instrumentoIniciativas = strategosInstrumentosService.getIniciativasInstrumento(instrumento.getInstrumentoId());
	    	
	    	for (Iterator<InstrumentoIniciativa> iter = instrumentoIniciativas.iterator(); iter.hasNext();) {
	    		InstrumentoIniciativa instrumentoIniciativa = (InstrumentoIniciativa)iter.next();
	    		Iniciativa iniciativa = (Iniciativa) strategosInstrumentosService.load(Iniciativa.class, instrumentoIniciativa.getIniciativa().getIniciativaId());
	    		instrumentoIniciativa.setIniciativa(iniciativa);
	    	}
	    		    		    	
	    	editarInstrumentosForm.setNombreCorto(instrumento.getNombreCorto());
	    		    	
	    	paginaIniciativas.setLista(instrumentoIniciativas);
	    	paginaIniciativas.setTamanoSetPaginas(5);
	    	
	    }else {
	    	if(editarInstrumentosForm != null) {
	    		editarInstrumentosForm.clear();	   	
	    		editarInstrumentosForm.setStatus(StatusUtil.getStatusInvalido());
	    	}
	    	ActionMessages messages = getMessages(request);
	    	messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.calcularregistro.noencontrado"));
	    	saveMessages(request, messages);
	    	
	    	paginaIniciativas.setLista(new ArrayList<InstrumentoIniciativa>());
	    	paginaIniciativas.setTamanoSetPaginas(5);
	    }
	    
	    request.setAttribute("asignarPesosIniciativasInstrumento.paginaIniciativas", paginaIniciativas);
		
	    strategosInstrumentosService.close();
		
	    return mapping.findForward(forward);
	}
	
	private int guardarPesos(StrategosInstrumentosService strategosInstrumentosService, EditarInstrumentosForm editarInstrumentosForm, HttpServletRequest request ) throws Exception {			
		List<InstrumentoIniciativa> instrumentoIniciativas = new ArrayList<InstrumentoIniciativa>();
		Map<?, ?> nombresParametros = request.getParameterMap();
		
		System.out.print("\n\n"+ nombresParametros + " \n\n");
		
		for (Iterator<?> iter = nombresParametros.keySet().iterator(); iter.hasNext();) {
			
			String nombre = (String)iter.next();
			int index = nombre.indexOf("pesoIniciativa");
			
			if(index > -1) {
				InstrumentoIniciativa instrumentoIniciativa = new InstrumentoIniciativa(); 
				InstrumentoIniciativaPK pk = new InstrumentoIniciativaPK();			
				pk.setIniciativaId(new Long(nombre.substring("pesoIniciativa".length())));
				pk.setInstrumentoId(editarInstrumentosForm.getInstrumentoId());
				instrumentoIniciativa.setPk(pk);
				if ((request.getParameter(nombre) != null) && (!request.getParameter(nombre).equals("")))
					instrumentoIniciativa.setPeso(new Double(VgcFormatter.parsearNumeroFormateado(request.getParameter(nombre))));					
				instrumentoIniciativas.add(instrumentoIniciativa);
			}
		}		
		return strategosInstrumentosService.saveIniciativaInstrumento(instrumentoIniciativas, getUsuarioConectado(request));
		
	}
}
