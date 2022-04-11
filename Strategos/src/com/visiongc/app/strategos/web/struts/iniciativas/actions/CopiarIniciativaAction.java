/**
 * 
 */
package com.visiongc.app.strategos.web.struts.iniciativas.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.HistoricoType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class CopiarIniciativaAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();
		
	    if (forward.equals("finalizarCopiarIniciativa")) 
	    	return getForwardBack(request, 1, true);
	    
	    return getForwardBack(request, 1, true);
	}

	public int Copiar(Long organizacionOrigenId, Long organizacionDestinoId, HttpServletRequest request)
	{
		int respuesta = VgcReturnCode.DB_OK;
		
		Map<String, String> filtros = new HashMap<String, String>();
		
		filtros.put("organizacionId", organizacionOrigenId.toString());
		filtros.put("historicoDate", "IS NULL");
		
		StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();
		PaginaLista paginaIniciativas = strategosIniciativasService.getIniciativas(0, 0, "nombre", "ASC", false, filtros);
		
		for (Iterator<Iniciativa> iter = paginaIniciativas.getLista().iterator(); iter.hasNext(); ) 
		{
			Iniciativa iniciativa = (Iniciativa)iter.next();
			
			Iniciativa iniciativaCopia = new Iniciativa().copyFrom(iniciativa); 
			
			iniciativaCopia.setProyectoId(null);
			iniciativaCopia.setOrganizacionId(organizacionDestinoId);
			iniciativaCopia.setOrganizacion(null);
			iniciativaCopia.setClaseId(null);
			iniciativaCopia.setHistoricoDate(null);
			iniciativaCopia.setIniciativaIndicadores(null);
			iniciativaCopia.setIniciativaPerspectivas(null);
			iniciativaCopia.setIniciativaPlanes(null);
			iniciativaCopia.setIniciativasAsociadas(null);
			iniciativaCopia.setEstatusId(EstatusType.getEstatusSinIniciar());
			iniciativaCopia.setEstatus(null);
			iniciativaCopia.setAlerta(null);
			iniciativaCopia.setPorcentajeCompletado(null);
			iniciativaCopia.setFechaUltimaMedicion(null);
			iniciativaCopia.setPorcentajeEsperado(null);
			iniciativaCopia.setUltimaMedicion(null);
			
			respuesta = strategosIniciativasService.saveIniciativa(iniciativaCopia, getUsuarioConectado(request), true);	
			if (respuesta != VgcReturnCode.DB_OK)
				break;
		}		
		
		strategosIniciativasService.close();
		
		return respuesta;
	}
}
