/**
 * 
 */
package com.visiongc.app.strategos.web.struts.portafolios.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.model.util.IniciativaEstatus.EstatusType;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.portafolios.forms.EditarPortafolioForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class GuardarPortafolioAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();

		EditarPortafolioForm editarPortafolioForm = (EditarPortafolioForm)form;

		Long id = (request.getParameter("id") != null && request.getParameter("id") != "" ? Long.parseLong(request.getParameter("id")) : 0L);
		
		editarPortafolioForm.setId(id);
		
		StrategosPortafoliosService strategosPortafoliosService = StrategosServiceFactory.getInstance().openStrategosPortafoliosService();
		
		int respuesta = VgcReturnCode.DB_OK;
		boolean nuevo = false;

		Portafolio portafolio = null;
		if ((editarPortafolioForm.getId() != null) && (!editarPortafolioForm.getId().equals(Long.valueOf("0"))))
		{
			portafolio = (Portafolio)strategosPortafoliosService.load(Portafolio.class, editarPortafolioForm.getId());
			portafolio.setId(editarPortafolioForm.getId());
		}
		else
		{
			nuevo = true;
			portafolio = new Portafolio();
			portafolio.setId(new Long(0L));
			portafolio.setOrganizacionId(new Long((String)request.getSession().getAttribute("organizacionId")));
			portafolio.setEstatusId(EstatusType.getEstatusSinIniciar());
		}

		portafolio.setNombre(editarPortafolioForm.getNombre());
		portafolio.setActivo(editarPortafolioForm.getActivo());
		
		Boolean cambioFrecuencia = (portafolio != null && portafolio.getFrecuencia() != null && portafolio.getFrecuencia().byteValue() != editarPortafolioForm.getFrecuencia().byteValue());
		if (cambioFrecuencia)
			portafolio.setPorcentajeCompletado(null);
		portafolio.setFrecuencia(editarPortafolioForm.getFrecuencia());
		portafolio.setAlto(editarPortafolioForm.getAlto());
		portafolio.setAncho(editarPortafolioForm.getAncho());
		portafolio.setFilas((byte) 2);
		portafolio.setColumnas((byte) 2);

		respuesta = strategosPortafoliosService.save(portafolio, cambioFrecuencia, getUsuarioConectado(request));
		strategosPortafoliosService.close();

	    if (respuesta == VgcReturnCode.DB_OK)
	    {
			forward = "crearPortafolio";
			editarPortafolioForm.setStatus(StatusUtil.getStatusSuccess());
	    	if (!nuevo)
	    		editarPortafolioForm.setStatus(StatusUtil.getStatusSuccessModify());
	    }
	    else
	    	editarPortafolioForm.setStatus(StatusUtil.getStatusInvalido());

		return mapping.findForward(forward);
	}
}
