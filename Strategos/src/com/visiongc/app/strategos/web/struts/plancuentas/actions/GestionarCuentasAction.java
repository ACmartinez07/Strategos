package com.visiongc.app.strategos.web.struts.plancuentas.actions;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.plancuentas.StrategosCuentasService;
import com.visiongc.app.strategos.plancuentas.model.Cuenta;
import com.visiongc.app.strategos.plancuentas.model.GrupoMascaraCodigoPlanCuentas;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.web.struts.plancuentas.forms.GestionarCuentasForm;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.TreeviewWeb;
import com.visiongc.framework.arboles.ArbolesService;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.util.PermisologiaUsuario;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class GestionarCuentasAction extends VgcAction
{
	public static final String ACTION_KEY = "GestionarCuentasAction";

	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
		navBar.agregarUrl(TreeviewWeb.getUrlTreeview(url), nombre);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String forward = mapping.getParameter();

		ActionMessages messages = getMessages(request);

		GestionarCuentasForm gestionarCuentasForm = (GestionarCuentasForm)form;

		String selectedCuentaId = request.getParameter("selectedCuentaId");
		String openCuentaId = request.getParameter("openCuentaId");
		String closeCuentaId = request.getParameter("closeCuentaId");
		boolean mostrarTodas = getPermisologiaUsuario(request).tienePermiso("ORGANIZACION_VIEWALL");

		ArbolesService arbolesService = FrameworkServiceFactory.getInstance().openArbolesService();

		boolean bloqueada = false;

		if (request.getSession().getAttribute("cuenta") == null)
		{
			Cuenta cuentaRoot = new Cuenta();
			
			cuentaRoot = (Cuenta)arbolesService.getNodoArbolRaiz(cuentaRoot);

			if (cuentaRoot == null)
			{
				StrategosCuentasService strategosCuentasService = StrategosServiceFactory.getInstance().openStrategosCuentasService();
				cuentaRoot = strategosCuentasService.crearCuentaRaiz(getUsuarioConectado(request));
				strategosCuentasService.close();
			}

			TreeviewWeb.publishTree("arbolCuentas", cuentaRoot.getCuentaId().toString(), "session", request, true);

			arbolesService.refreshNodosArbol(cuentaRoot, (String)request.getSession().getAttribute("arbolCuentas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas));
			
			request.getSession().setAttribute("cuentaRoot", cuentaRoot);

			bloqueada = !arbolesService.bloquearParaUso(request.getSession().getId(), null, cuentaRoot);

			request.getSession().setAttribute("cuenta", cuentaRoot);
			request.getSession().setAttribute("cuentaId", cuentaRoot.getCuentaId().toString());

			gestionarCuentasForm.setNivelSeleccionadoArbol(new Integer(arbolesService.getRutaCompleta(cuentaRoot).size()));
		}
		else
		{
			Cuenta cuentaSeleccionada = null;

			if (request.getAttribute("GestionarCuentasAction.reloadId") != null) 
				cuentaSeleccionada = (Cuenta)arbolesService.load(Cuenta.class, (Long)request.getAttribute("GestionarCuentasAction.reloadId"));
			else if ((selectedCuentaId != null) && (!selectedCuentaId.equals(""))) 
				cuentaSeleccionada = (Cuenta)arbolesService.load(Cuenta.class, new Long(selectedCuentaId));
			else if ((openCuentaId != null) && (!openCuentaId.equals(""))) 
			{
				TreeviewWeb.publishTreeOpen("arbolCuentas", openCuentaId, "session", request);
				cuentaSeleccionada = (Cuenta)arbolesService.load(Cuenta.class, new Long(openCuentaId));
			} 
			else if ((closeCuentaId != null) && (!closeCuentaId.equals(""))) 
			{
				TreeviewWeb.publishTreeClose("arbolCuentas", closeCuentaId, "session", request);
				cuentaSeleccionada = (Cuenta)arbolesService.load(Cuenta.class, new Long(closeCuentaId));
			} 
			else 
			{
				cuentaSeleccionada = (Cuenta)arbolesService.load(Cuenta.class, new Long((String)request.getSession().getAttribute("cuentaId")));
				gestionarCuentasForm.setNivelSeleccionadoArbol(new Integer(1));
			}
      
			Long reloadId;
			if (cuentaSeleccionada == null) 
			{
				cuentaSeleccionada = (Cuenta)request.getSession().getAttribute("cuentaRoot");
				reloadId = cuentaSeleccionada.getCuentaId();
				TreeviewWeb.publishTree("arbolCuentas", cuentaSeleccionada.getCuentaId().toString(), "session", request, true);

				messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.editarregistro.noencontrado"));
			} 
			else 
			{
				reloadId = cuentaSeleccionada.getCuentaId();
				if (closeCuentaId == null) 
					TreeviewWeb.publishTreeOpen("arbolCuentas", reloadId.toString(), "session", request);
			}

			arbolesService.refreshNodosArbol((Cuenta)request.getSession().getAttribute("cuentaRoot"), (String)request.getSession().getAttribute("arbolCuentas"), TreeviewWeb.getSeparadorTokens(), new Boolean(mostrarTodas), reloadId);

			bloqueada = !arbolesService.bloquearParaUso(request.getSession().getId(), (NodoArbol)request.getSession().getAttribute("cuenta"), cuentaSeleccionada);

			request.getSession().setAttribute("cuenta", cuentaSeleccionada);
			request.getSession().setAttribute("cuentaId", cuentaSeleccionada.getCuentaId().toString());

			getPadres(cuentaSeleccionada, arbolesService);
			gestionarCuentasForm.setNivelSeleccionadoArbol(new Integer(arbolesService.getRutaCompleta(cuentaSeleccionada).size()));
		}

		if (bloqueada) 
			messages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("action.cuenta.bloqueada"));

		StrategosCuentasService strategosCuentasService = StrategosServiceFactory.getInstance().openStrategosCuentasService();
		List<?> maxinoNivelGrupoCuenta = strategosCuentasService.getMaximoNivelGrupo();
		gestionarCuentasForm.setMaximoNivelGrupoCuenta(maxinoNivelGrupoCuenta);

		if (gestionarCuentasForm.getMaximoNivelGrupoCuenta().size() >= gestionarCuentasForm.getNivelSeleccionadoArbol().intValue()) 
		{
			GrupoMascaraCodigoPlanCuentas grupoMascaraCodigoPlanCuentas = (GrupoMascaraCodigoPlanCuentas)gestionarCuentasForm.getMaximoNivelGrupoCuenta().get(gestionarCuentasForm.getNivelSeleccionadoArbol().intValue() - 1);
			gestionarCuentasForm.setGrupoMascaraCodigoPlanCuentasNuevo(grupoMascaraCodigoPlanCuentas);
		} 
		else 
			gestionarCuentasForm.setGrupoMascaraCodigoPlanCuentasNuevo(null);

		if (gestionarCuentasForm.getNivelSeleccionadoArbol().byteValue() != 1) 
		{
			GrupoMascaraCodigoPlanCuentas grupoMascaraCodigoPlanCuentas = (GrupoMascaraCodigoPlanCuentas)gestionarCuentasForm.getMaximoNivelGrupoCuenta().get(gestionarCuentasForm.getNivelSeleccionadoArbol().intValue() - 2);
			gestionarCuentasForm.setGrupoMascaraCodigoPlanCuentasModificar(grupoMascaraCodigoPlanCuentas);
		} 
		else 
			gestionarCuentasForm.setGrupoMascaraCodigoPlanCuentasModificar(null);

    	arbolesService.close();
    	saveMessages(request, messages);

    	return mapping.findForward(forward);
  	}
  
	private void getPadres(Cuenta cuenta, ArbolesService arbolesService)
	{
		Cuenta cuentaPadre = null;
		if (cuenta.getPadreId() != null)
		{
			cuentaPadre = (Cuenta)arbolesService.load(Cuenta.class, new Long(cuenta.getPadreId()));
			cuenta.setPadre(cuentaPadre);
		}
		
		if (cuentaPadre != null && cuentaPadre.getPadreId() != null)
			getPadres(cuentaPadre, arbolesService);
	}
}