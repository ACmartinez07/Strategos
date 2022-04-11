/**
 * 
 */
package com.visiongc.app.strategos.web.struts.instrumentos.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.instrumentos.StrategosCooperantesService;
import com.visiongc.app.strategos.instrumentos.StrategosTiposConvenioService;
import com.visiongc.app.strategos.instrumentos.model.Cooperante;
import com.visiongc.app.strategos.instrumentos.model.TipoConvenio;
import com.visiongc.app.strategos.portafolios.StrategosPortafoliosService;
import com.visiongc.app.strategos.portafolios.model.Portafolio;
import com.visiongc.app.strategos.web.struts.iniciativas.forms.GestionarIniciativasForm;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.GestionarConveniosForm;
import com.visiongc.app.strategos.web.struts.instrumentos.forms.GestionarConveniosForm;
import com.visiongc.app.strategos.web.struts.portafolios.forms.GestionarPortafoliosForm;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.CondicionType;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ConfiguracionUsuarioPK;
import com.visiongc.framework.web.controles.SplitControl;
import com.visiongc.framework.web.struts.forms.FiltroForm;

/**
 * @author Kerwin
 *
 */
public class GestionarConveniosAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrlSinParametros(url, nombre, new Integer(2));
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);

		String forward = mapping.getParameter();
		
		GestionarConveniosForm gestionarConveniosForm = (GestionarConveniosForm)form;
		
		String atributoOrden = gestionarConveniosForm.getAtributoOrden();
	    String tipoOrden = gestionarConveniosForm.getTipoOrden();
	    int pagina = gestionarConveniosForm.getPagina();

	    if (atributoOrden == null) {
	      atributoOrden = "descripcion";
	      gestionarConveniosForm.setAtributoOrden(atributoOrden);
	    }
	    if (tipoOrden == null) {
	      tipoOrden = "ASC";
	      gestionarConveniosForm.setTipoOrden(tipoOrden);
	    }

	    if (pagina < 1) {
	      pagina = 1;
	    }
		
	    StrategosTiposConvenioService strategosConveniosService = StrategosServiceFactory.getInstance().openStrategosTiposConvenioService();

	    Map filtros = new HashMap();
	    
	    /*
	    if ((gestionarTipoProyectoForm.getFiltroNombre() != null) && (!gestionarTipoProyectoForm.getFiltroNombre().equals(""))) {
	        filtros.put("nombre", gestionarTipoProyectoForm.getFiltroNombre());
	      }
		*/

	    PaginaLista paginaConvenios = strategosConveniosService.getTiposConvenio(pagina, 30, atributoOrden, tipoOrden, true, filtros);

	    paginaConvenios.setTamanoSetPaginas(5);

	    request.setAttribute("paginaConvenios", paginaConvenios);

	    strategosConveniosService.close();

	    if (paginaConvenios.getLista().size() > 0) {
	        TipoConvenio convenios = (TipoConvenio)paginaConvenios.getLista().get(0);
	        gestionarConveniosForm.setSeleccionados(convenios.getTiposConvenioId().toString());
	        gestionarConveniosForm.setValoresSeleccionados(convenios.getDescripcion());
	    } else {
	    	gestionarConveniosForm.setSeleccionados(null);
	    }
	    
		return mapping.findForward(forward);
	}
}
