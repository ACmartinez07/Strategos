/**
 * 
 */
package com.visiongc.app.strategos.web.struts.modulo.codigoEnlace.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.modulo.codigoenlace.StrategosCodigoEnlaceService;
import com.visiongc.app.strategos.modulo.codigoenlace.model.CodigoEnlace;
import com.visiongc.app.strategos.util.StatusUtil;
import com.visiongc.app.strategos.web.struts.modulo.codigoEnlace.forms.EditarCodigoEnlaceForm;
import com.visiongc.commons.VgcReturnCode;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class GuardarCodigoEnlaceAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
	    super.execute(mapping, form, request, response);

	    String forward = mapping.getParameter();

	    EditarCodigoEnlaceForm editarCodigoEnlaceForm = (EditarCodigoEnlaceForm)form;

	    ActionMessages messages = getMessages(request);
	    
		Long id = (request.getParameter("id") != null && request.getParameter("id") != "" ? Long.parseLong(request.getParameter("id")) : 0L);
		Long bi = (request.getParameter("bi") != null && request.getParameter("bi") != "" ? Long.parseLong(request.getParameter("bi")) : null);
		Long categoria = (request.getParameter("categoria") != null && request.getParameter("categoria") != "" ? Long.parseLong(request.getParameter("categoria")) : null);

		editarCodigoEnlaceForm.setId(id);
		editarCodigoEnlaceForm.setBi(bi);
		editarCodigoEnlaceForm.setCategoria(categoria);
		
	    StrategosCodigoEnlaceService strategosCodigoEnlaceService = StrategosServiceFactory.getInstance().openStrategosCodigoEnlaceService();

	    CodigoEnlace codigoEnlace = new CodigoEnlace();
	    boolean nuevo = false;
	    int respuesta = VgcReturnCode.DB_OK;

	    if ((editarCodigoEnlaceForm.getId() != null) && (!editarCodigoEnlaceForm.getId().equals(Long.valueOf("0"))))
	    	codigoEnlace = (CodigoEnlace)strategosCodigoEnlaceService.load(CodigoEnlace.class, new Long(editarCodigoEnlaceForm.getId()));
	    else
	    {
	    	nuevo = true;
	    	codigoEnlace = new CodigoEnlace();
	    	codigoEnlace.setId(new Long(0L));
	    }

	    codigoEnlace.setCodigo(editarCodigoEnlaceForm.getCodigo());
	    codigoEnlace.setNombre(editarCodigoEnlaceForm.getNombre());
	    codigoEnlace.setBi(editarCodigoEnlaceForm.getBi());
	    codigoEnlace.setCategoria(editarCodigoEnlaceForm.getCategoria());

	    respuesta = strategosCodigoEnlaceService.saveCodigoEnlace(codigoEnlace, getUsuarioConectado(request));

	    if (respuesta == VgcReturnCode.DB_OK)
	    {
	    	forward = "crearCodigoenlace";

	    	if (nuevo)
	    		editarCodigoEnlaceForm.setStatus(StatusUtil.getStatusSuccess());
	    	else
	    		editarCodigoEnlaceForm.setStatus(StatusUtil.getStatusSuccessModify());
	    }
	    else if (respuesta == VgcReturnCode.DB_PK_AK_VIOLATED)
			editarCodigoEnlaceForm.setStatus(StatusUtil.getStatusRegistroDuplicado());
	    else
	    	editarCodigoEnlaceForm.setStatus(StatusUtil.getStatusInvalido());

	    strategosCodigoEnlaceService.close();
	    return mapping.findForward(forward);
	}
}