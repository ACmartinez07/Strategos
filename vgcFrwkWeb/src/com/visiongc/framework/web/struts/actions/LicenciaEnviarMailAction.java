package com.visiongc.framework.web.struts.actions;


import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.StrategosMedicionesService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.iniciativas.StrategosIniciativasService;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.StrategosOrganizacionesService;
import com.visiongc.app.strategos.seriestiempo.model.SerieTiempo;
import com.visiongc.commons.struts.action.VgcAction;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.web.NavigationBar;
import com.visiongc.commons.web.util.Correo;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Licencia;
import com.visiongc.framework.model.Organizacion;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public final class LicenciaEnviarMailAction
  extends VgcAction
{
  public LicenciaEnviarMailAction() {}
  
  public void updateNavigationBar(NavigationBar navBar, String url, String nombre) {}
  
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    super.execute(mapping, form, request, response);
    
    int resultado = 0;
    
    if (request.getParameter("funcion") != null)
    {	
      String funcion = request.getParameter("funcion");
      if (funcion.equals("test"))
      {
        /*
        FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService(getLocale(request));
        resultado = frameworkService.enviarCorreo(receptor, "Test", "Test", null, null);
        frameworkService.close();
      	*/
    	
    	String receptor = TextEncoder.uRLDecode(request.getParameter("receptor"));
    	String user=TextEncoder.uRLDecode(request.getParameter("user"));
    	String pass=TextEncoder.uRLDecode(request.getParameter("pass"));
    	String host=TextEncoder.uRLDecode(request.getParameter("host"));
    	String port=TextEncoder.uRLDecode(request.getParameter("port"));
    	  
    	Correo correo = new Correo();  
    	
    	String cuerpo="Reporte de iniciativas";
    	
    	
		
    	/*
			
			cuerpo += "\n \n";
			cuerpo += "Iniciativa: \n";
			cuerpo += "Nombre Organización: "+"02. Sistemas" +"\n";
			cuerpo += "Nombre Iniciativa: "+"P.D 2019 Gestión de TI"+"\n";
			
			cuerpo += "Descripción: "+"ADQUISICIÓN DE UNA SOLUCIÓN DE GESTIÓN DE TI Y SERVICIOS CONEXOS PARA LA PROCURADURÍA GENERAL DE LA NACIÓN- PGN." +"\n";
			
			cuerpo += "Fecha Ultima Medición: "+"2/20" +"\n";
			cuerpo += "Porcentaje Completado: "+"74.00" +"\n";
			
			
			cuerpo += "Porcentaje Esperado: "+ "74.00" +"\n";
			
			
			cuerpo += "Año Formulación: "+"2019" +"\n";
		
			cuerpo += "Ultima Medición: "+"5.0" +"\n";
			
		*/
    	
    	correo.sendEmail(host, port, user, pass, "Reporte Iniciativas", cuerpo, receptor);
    	  
      }
    }
    else
    {
      String receptor = TextEncoder.uRLDecode(request.getParameter("receptor"));
      String asunto = TextEncoder.uRLDecode(request.getParameter("asunto"));
      
      Licencia licencia = new Licencia().getLicencia(request);
      
      File archivo = new File("vision.sln");
      FileWriter fichero = new FileWriter(archivo);
      PrintWriter pw = new PrintWriter(fichero);
      pw.print(licencia.getCmaxc());
      pw.close();
      
      FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService(getLocale(request));
      resultado = frameworkService.enviarCorreo(receptor, asunto, asunto, archivo.getPath(), archivo.getName());
      frameworkService.close();
      
      archivo.delete();
    }
    
    request.setAttribute("ajaxResponse", Integer.valueOf(resultado));
    
    return mapping.findForward("ajaxResponse");
  }
}
