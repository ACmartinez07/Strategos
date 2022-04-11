/**
 * 
 */
package com.visiongc.app.strategos.web.struts.general.actions;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.visiongc.app.strategos.explicaciones.StrategosExplicacionesService;
import com.visiongc.app.strategos.explicaciones.model.Explicacion;
import com.visiongc.app.strategos.explicaciones.model.MemoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.Explicacion.TipoExplicacion;
import com.visiongc.app.strategos.explicaciones.model.util.TipoMemoExplicacion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.web.struts.general.forms.EditarGeneralInformeForms;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Modulo;
import com.visiongc.framework.model.Modulo.ModuloType;
import com.visiongc.framework.web.struts.actions.LogonAction;
import com.visiongc.framework.web.struts.forms.NavegadorForm;

/**
 * @author Kerwin
 *
 */
public class EditarGeneralActions 
{
	public NavegadorForm getModulo(String modulo, Boolean preCargado)
	{
		VgcMessageResources messageResources = VgcResourceManager.getMessageResources("StrategosWeb");

		NavegadorForm moduloActivo = new NavegadorForm();
		moduloActivo.clear();
		
		moduloActivo.setObjeto(new Modulo().getModuloActivo(modulo));
		
		if (preCargado != null && preCargado && moduloActivo.getObjeto() == null)
		{
			Modulo mod = new Modulo();
			mod.setId(modulo);
			mod.setActivo(true);
			
			if (modulo == ModuloType.Actividades.Actividades)
				mod.setModulo(messageResources.getResource("jsp.modulo.actividad.titulo.singular"));
			else if (modulo == ModuloType.Iniciativas.Iniciativas)
				mod.setModulo(messageResources.getResource("jsp.modulo.iniciativa.titulo.singular"));
			else if (modulo == ModuloType.Problema.Problema)
				mod.setModulo(messageResources.getResource("jsp.modulo.problema.titulo.singular"));
			else if (modulo == ModuloType.Plan.Plan)
				mod.setModulo(messageResources.getResource("jsp.modulo.plan.titulo.singular"));
			moduloActivo.setObjeto(mod);
		}
		
		if ((modulo == ModuloType.Actividades.Actividades || modulo == ModuloType.Iniciativas.Iniciativas))
		{
			try
			{
				FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
				Configuracion configuracion = null;
				if (modulo.equals(ModuloType.Iniciativas.Iniciativas))
					configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Iniciativas");
				frameworkService.close();
	
				if (configuracion != null)
				{
					//XML
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			        DocumentBuilder db = dbf.newDocumentBuilder(); 
			        Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8"))); 
			        doc.getDocumentElement().normalize();
					NodeList nList = doc.getElementsByTagName("properties");
					Element eElement = (Element) nList.item(0);
	
					moduloActivo.setNombreSingular(VgcAbstractService.getTagValue("nombre", eElement));
					String vocales = "aeiou";
					if (vocales.indexOf(moduloActivo.getNombreSingular().substring((moduloActivo.getNombreSingular().length() -1), moduloActivo.getNombreSingular().length()).toLowerCase()) != -1)
						moduloActivo.setNombrePlural(moduloActivo.getNombreSingular() + "s");
					else
						moduloActivo.setNombrePlural(moduloActivo.getNombreSingular() + "es");
				}
				else
				{
					String nombre = null;
					if (moduloActivo.getObjeto() != null && moduloActivo.getObjeto().getModulo() != null && !moduloActivo.getObjeto().getModulo().equals(""))
						nombre = moduloActivo.getObjeto().getModulo();
					else if (modulo == ModuloType.Actividades.Actividades)
						nombre = messageResources.getResource("jsp.modulo.actividad.titulo.singular");
					else if (modulo == ModuloType.Iniciativas.Iniciativas)
						nombre = messageResources.getResource("jsp.modulo.iniciativa.titulo.singular");
					else if (modulo == ModuloType.Problema.Problema)
						nombre = messageResources.getResource("jsp.modulo.problema.titulo.singular");
					else if (modulo == ModuloType.Plan.Plan)
						nombre = messageResources.getResource("jsp.modulo.plan.titulo.singular");
						
					moduloActivo.setNombreSingular(nombre);
					String vocales = "aeiou";
					if (vocales.indexOf(moduloActivo.getNombreSingular().substring((moduloActivo.getNombreSingular().length() -1), moduloActivo.getNombreSingular().length()).toLowerCase()) != -1)
						moduloActivo.setNombrePlural(moduloActivo.getNombreSingular() + "s");
					else
						moduloActivo.setNombrePlural(moduloActivo.getNombreSingular() + "es");
				}
			}
			catch (Exception e) 
			{
				String nombre = null;
				if (moduloActivo.getObjeto() != null && moduloActivo.getObjeto().getModulo() != null && !moduloActivo.getObjeto().getModulo().equals(""))
					nombre = moduloActivo.getObjeto().getModulo();
				else if (modulo == ModuloType.Actividades.Actividades)
					nombre = messageResources.getResource("jsp.modulo.actividad.titulo.singular");
				else if (modulo == ModuloType.Iniciativas.Iniciativas)
					nombre = messageResources.getResource("jsp.modulo.iniciativa.titulo.singular");
				else if (modulo == ModuloType.Problema.Problema)
					nombre = messageResources.getResource("jsp.modulo.problema.titulo.singular");
				else if (modulo == ModuloType.Plan.Plan)
					nombre = messageResources.getResource("jsp.modulo.plan.titulo.singular");
					
				moduloActivo.setNombreSingular(nombre);
				String vocales = "aeiou";
				if (vocales.indexOf(moduloActivo.getNombreSingular().substring((moduloActivo.getNombreSingular().length() -1), moduloActivo.getNombreSingular().length()).toLowerCase()) != -1)
					moduloActivo.setNombrePlural(moduloActivo.getNombreSingular() + "s");
				else
					moduloActivo.setNombrePlural(moduloActivo.getNombreSingular() + "es");
			}
		}
		
		return moduloActivo;
	}

	public EditarGeneralInformeForms getInforme()
	{
		EditarGeneralInformeForms informe = new EditarGeneralInformeForms();
		informe.clear();
		
		Modulo modulo = new Modulo().getModuloActivo(ModuloType.Informe.Tipos.Cualitativos);
		if (modulo != null)
		{
			informe.setHayCualitativos(modulo.getActivo());
			if (informe.getHayCualitativos() != null && informe.getHayCualitativos())
				informe.setHayInformen(true);
		}
		modulo = new Modulo().getModuloActivo(ModuloType.Informe.Tipos.Cualitativos);
		if (modulo != null)
		{
			informe.setHayEjecutivos(modulo.getActivo());
			if (informe.getHayEjecutivos() != null && informe.getHayEjecutivos())
				informe.setHayInformen(true);
		}
		modulo = new Modulo().getModuloActivo(ModuloType.Informe.Tipos.Cualitativos);
		if (modulo != null)
		{
			informe.setHayEventos(modulo.getActivo());
			if (informe.getHayEventos() != null && informe.getHayEventos())
				informe.setHayInformen(true);
		}
		modulo = new Modulo().getModuloActivo(ModuloType.Informe.Tipos.Cualitativos);
		if (modulo != null)
		{
			informe.setHayNoticias(modulo.getActivo());
			if (informe.getHayNoticias() != null && informe.getHayNoticias())
			{
				informe.setHayInformen(true);
				StrategosExplicacionesService strategosExplicacionesService = StrategosServiceFactory.getInstance().openStrategosExplicacionesService();
				
				Map<String, Integer> filtros = new HashMap<String, Integer>();
	
				filtros.put("tipo", TipoExplicacion.getTipoNoticia());
				PaginaLista paginaNoticias = strategosExplicacionesService.getExplicaciones(0, 0, "fecha", "DESC", true, filtros);
				
				if (paginaNoticias.getLista().size() > 0)
				{
					for (Iterator<?> e = paginaNoticias.getLista().iterator(); e.hasNext(); )
					{
						Explicacion explicacion = (Explicacion)e.next();
						if (explicacion.getPublico())
						{
							informe.setMostrarAlerta(true);
							informe.setAlerta(explicacion.getTitulo());
							for (Iterator<?> i = explicacion.getMemosExplicacion().iterator(); i.hasNext(); ) 
							{
								MemoExplicacion memoExplicacion = (MemoExplicacion)i.next();
								Byte tipoMemo = memoExplicacion.getPk().getTipo();
								if (tipoMemo.equals(new Byte(TipoMemoExplicacion.TIPO_MEMO_EXPLICACION_DESCRIPCION)))
								{
									informe.setDescripcion(memoExplicacion.getMemo());
									break;
								}
							}
							break;
						}
					}
				}
				strategosExplicacionesService.close();
			}
		}
		
		return informe;
	}
}
