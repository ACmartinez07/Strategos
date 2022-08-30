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
		FiltroForm filtro = new FiltroForm();
		
		String anio = request.getParameter("anio") != null ? request.getParameter("anio") : "";
		String estatusSt = request.getParameter("estatus") != null ? request.getParameter("estatus") : "";
		String nombreCorto = request.getParameter("nombreCorto") != null ? request.getParameter("nombreCorto") : "";
		Byte estatus = 0;
		
		if(estatusSt != null && estatusSt != "") {
			estatus = Byte.valueOf(estatusSt);
		}
			
		//editarInstrumentosForm.setFiltro(filtro);
		editarInstrumentosForm.setNombreCorto(nombreCorto);	
		editarInstrumentosForm.setAnio(anio);
		
		StrategosInstrumentosService strategosInstrumentosService = StrategosServiceFactory.getInstance().openStrategosInstrumentosService();
		
		Map<String, String> filtros = new HashMap<String, String>();
	    int pagina = 0;
	    String atributoOrden = null;
	    String tipoOrden = null;

	    if (atributoOrden == null) 
	    	atributoOrden = "nombreCorto";
	    if (tipoOrden == null) 
	    	tipoOrden = "ASC";
	    if (pagina < 1) 
	    	pagina = 1;
	    
	    if ((editarInstrumentosForm.getNombreCorto() != null) && editarInstrumentosForm.getNombreCorto() != "") {
	        filtros.put("nombreCorto", editarInstrumentosForm.getNombreCorto());
	    }
	    if ((editarInstrumentosForm.getAnio() != null) && editarInstrumentosForm.getAnio() != "") {
	        filtros.put("anio", editarInstrumentosForm.getAnio());
	    }
	    
	    PaginaLista paginaInstrumentos = strategosInstrumentosService.getInstrumentos(pagina, 30, atributoOrden, tipoOrden, true, filtros);
	    	    
	    List<InstrumentoPeso> instrumentoPeso = strategosInstrumentosService.getInstrumentoPeso(anio);	    
	    
	    if (paginaInstrumentos.getLista().size() > 0) 
		{
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
						
	    	for (Iterator<Instrumentos> iter = paginaInstrumentos.getLista().iterator(); iter.hasNext(); ) 
			{
	    		Instrumentos instrumento = (Instrumentos)iter.next();	    		
	    		editarInstrumentosForm.setNombreCorto(instrumento.getNombreCorto());
			}		    	
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
