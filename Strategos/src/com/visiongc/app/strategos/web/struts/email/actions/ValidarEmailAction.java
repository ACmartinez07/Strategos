/**
 * 
 */
package com.visiongc.app.strategos.web.struts.email.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.planes.StrategosPerspectivasService;
import com.visiongc.app.strategos.planes.model.Perspectiva;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPryActividadesService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PryActividad;
import com.visiongc.app.strategos.responsables.StrategosResponsablesService;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.responsables.model.util.ConfiguracionResponsable;

import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.web.NavigationBar;

/**
 * @author Kerwin
 *
 */
public class ValidarEmailAction extends VgcAction
{
	public void updateNavigationBar(NavigationBar navBar, String url, String nombre)
	{
	}
	
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		super.execute(mapping, form, request, response);
		
		String tipoObjeto = (request.getParameter("tipoObjeto") != null ? request.getParameter("tipoObjeto") : null);
		Long objetoId = (request.getParameter("objetoId") != null ? Long.parseLong(request.getParameter("objetoId")) : null);
		  
		boolean hayResponsabilidad = false;
		boolean hayEmail = false;
		String to = "";
		String cc = "";
		String subject = "";
		if (tipoObjeto != null)
		{
			StrategosResponsablesService strategosResponsablesService = StrategosServiceFactory.getInstance().openStrategosResponsablesService();
			ConfiguracionResponsable configuracionResponsable = strategosResponsablesService.getConfiguracionResponsable(); 
			
			if (tipoObjeto.equals("Indicador"))
			{
				StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();

				Indicador indicador = (Indicador)strategosIndicadoresService.load(Indicador.class, new Long(objetoId));
				if (indicador != null)
				{
					subject = "Indicador: " + indicador.getNombre();
					if (!hayResponsabilidad)
						hayResponsabilidad = indicador.getResponsableFijarMetaId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = indicador.getResponsableSeguimientoId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = indicador.getResponsableLograrMetaId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = indicador.getResponsableCargarMetaId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = indicador.getResponsableCargarEjecutadoId() != null;
					
					Responsable responsable = null;
					if (configuracionResponsable.getEnviarResponsableLograrMeta())
					{
						if (indicador.getResponsableLograrMetaId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(indicador.getResponsableLograrMetaId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									to = responsable.getEmail(); 
								}
							}
						}
					}
	
					if (configuracionResponsable.getEnviarResponsableSeguimiento())
					{
						if (indicador.getResponsableSeguimientoId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(indicador.getResponsableSeguimientoId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else
										cc = responsable.getEmail();
								}
							}
						}
					}
					
					if (configuracionResponsable.getEnviarResponsableFijarMeta())
					{
						if (indicador.getResponsableFijarMetaId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(indicador.getResponsableFijarMetaId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else if (cc.equals(""))
										cc = responsable.getEmail();
									else
										cc = cc + ";" + responsable.getEmail();
								}
							}
						}
					}
					
					if (configuracionResponsable.getEnviarResponsableCargarMeta())
					{
						if (indicador.getResponsableCargarMetaId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(indicador.getResponsableCargarMetaId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else if (cc.equals(""))
										cc = responsable.getEmail();
									else
										cc = cc + ";" + responsable.getEmail();
								}
							}
						}
					}

					if (configuracionResponsable.getEnviarResponsableCargarEjecutado())
					{
						if (indicador.getResponsableCargarEjecutadoId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(indicador.getResponsableCargarEjecutadoId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else if (cc.equals(""))
										cc = responsable.getEmail();
									else
										cc = cc + ";" + responsable.getEmail();
								}
							}
						}
					}
				}
			}
			else if (tipoObjeto.equals("Iniciativa"))
			{
				StrategosIniciativasService strategosIniciativasService = StrategosServiceFactory.getInstance().openStrategosIniciativasService();

				Iniciativa iniciativa = (Iniciativa)strategosIniciativasService.load(Iniciativa.class, new Long(objetoId));
				if (iniciativa != null)
				{
					subject = "Iniciativa: " + iniciativa.getNombre();
					if (!hayResponsabilidad)
						hayResponsabilidad = iniciativa.getResponsableFijarMetaId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = iniciativa.getResponsableSeguimientoId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = iniciativa.getResponsableLograrMetaId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = iniciativa.getResponsableCargarMetaId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = iniciativa.getResponsableCargarEjecutadoId() != null;
					
					Responsable responsable = null;
					if (configuracionResponsable.getEnviarResponsableLograrMeta())
					{
						if (iniciativa.getResponsableLograrMetaId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(iniciativa.getResponsableLograrMetaId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									to = responsable.getEmail(); 
								}
							}
						}
					}
	
					if (configuracionResponsable.getEnviarResponsableSeguimiento())
					{
						if (iniciativa.getResponsableSeguimientoId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(iniciativa.getResponsableSeguimientoId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else
										cc = responsable.getEmail();
								}
							}
						}
					}

					if (configuracionResponsable.getEnviarResponsableFijarMeta())
					{
						if (iniciativa.getResponsableFijarMetaId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(iniciativa.getResponsableFijarMetaId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else if (cc.equals(""))
										cc = responsable.getEmail();
									else
										cc = cc + ";" + responsable.getEmail();
								}
							}
						}
					}

					if (configuracionResponsable.getEnviarResponsableCargarMeta())
					{
						if (iniciativa.getResponsableCargarMetaId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(iniciativa.getResponsableCargarMetaId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else if (cc.equals(""))
										cc = responsable.getEmail();
									else
										cc = cc + ";" + responsable.getEmail();
								}
							}
						}
					}

					if (configuracionResponsable.getEnviarResponsableCargarEjecutado())
					{
						if (iniciativa.getResponsableCargarEjecutadoId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(iniciativa.getResponsableCargarEjecutadoId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else if (cc.equals(""))
										cc = responsable.getEmail();
									else
										cc = cc + ";" + responsable.getEmail();
								}
							}
						}
					}
				}
				
				strategosIniciativasService.close();
			}
			else if (tipoObjeto.equals("Actividad"))
			{
				StrategosPryActividadesService strategosPryActividadesService = StrategosServiceFactory.getInstance().openStrategosPryActividadesService();

				PryActividad actividad = (PryActividad)strategosPryActividadesService.load(PryActividad.class, new Long(objetoId));
				if (actividad != null)
				{
					subject = "Actividad: " + actividad.getNombre();
					if (!hayResponsabilidad)
						hayResponsabilidad = actividad.getResponsableFijarMetaId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = actividad.getResponsableSeguimientoId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = actividad.getResponsableLograrMetaId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = actividad.getResponsableCargarMetaId() != null;
					if (!hayResponsabilidad)
						hayResponsabilidad = actividad.getResponsableCargarEjecutadoId() != null;
					
					Responsable responsable = null;
					if (configuracionResponsable.getEnviarResponsableLograrMeta())
					{
						if (actividad.getResponsableLograrMetaId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(actividad.getResponsableLograrMetaId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									to = responsable.getEmail(); 
								}
							}
						}
					}
	
					if (configuracionResponsable.getEnviarResponsableSeguimiento())
					{
						if (actividad.getResponsableSeguimientoId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(actividad.getResponsableSeguimientoId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else
										cc = responsable.getEmail();
								}
							}
						}
					}

					if (configuracionResponsable.getEnviarResponsableFijarMeta())
					{
						if (actividad.getResponsableFijarMetaId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(actividad.getResponsableFijarMetaId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else if (cc.equals(""))
										cc = responsable.getEmail();
									else
										cc = cc + ";" + responsable.getEmail();
								}
							}
						}
					}
					
					if (configuracionResponsable.getEnviarResponsableCargarMeta())
					{
						if (actividad.getResponsableCargarMetaId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(actividad.getResponsableCargarMetaId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else if (cc.equals(""))
										cc = responsable.getEmail();
									else
										cc = cc + ";" + responsable.getEmail();
								}
							}
						}
					}

					if (configuracionResponsable.getEnviarResponsableCargarEjecutado())
					{
						if (actividad.getResponsableCargarEjecutadoId() != null)
						{
							responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(actividad.getResponsableCargarEjecutadoId()));
							if (responsable != null)
							{
								if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
								{
									hayEmail = true;
									if (to.equals(""))
										to = responsable.getEmail();
									else if (cc.equals(""))
										cc = responsable.getEmail();
									else
										cc = cc + ";" + responsable.getEmail();
								}
							}
						}
					}
				}
				strategosPryActividadesService.close();
			}
			else if (tipoObjeto.equals("Perspectiva"))
			{
				StrategosPerspectivasService strategosPerspectivasService = StrategosServiceFactory.getInstance().openStrategosPerspectivasService();

				Perspectiva perspectiva = (Perspectiva)strategosPerspectivasService.load(Perspectiva.class, new Long(objetoId));
				if (perspectiva != null)
				{
					subject = "Perspectiva: " + perspectiva.getNombre();
					if (!hayResponsabilidad)
						hayResponsabilidad = perspectiva.getResponsableId() != null;
					
					Responsable responsable = null;
					if (perspectiva.getResponsableId() != null)
					{
						responsable = (Responsable)strategosResponsablesService.load(Responsable.class, new Long(perspectiva.getResponsableId()));
						if (responsable != null)
						{
							if (responsable.getEmail() != null && !responsable.getEmail().equals(""))
							{
								hayEmail = true;
								to = responsable.getEmail(); 
							}
						}
					}
				}
				strategosPerspectivasService.close();
			}
			
			strategosResponsablesService.close();
		}
		
		request.setAttribute("ajaxResponse", (hayResponsabilidad ? "true" : "false") + "|" + (hayEmail ? "true" : "false") + "|" + (!to.equals("") ? to : "") + "|" + (!cc.equals("") ? cc : "") + "|" + (!subject.equals("") ? subject : ""));
	    return mapping.findForward("ajaxResponse");
	}
}