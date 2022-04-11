/**
 * 
 */
package com.visiongc.app.strategos.web.struts.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.visiongc.app.strategos.planificacionseguimiento.model.util.RelacionIniciativaActividad;
import com.visiongc.commons.struts.tag.VgcBaseTag;

/**
 * @author Kerwin
 *
 */
public class ImagenRelacionTag extends VgcBaseTag
{
	static final long serialVersionUID = 0L;
	
	protected String name = null;
	protected String property = null;
	protected String scope = null;

	public String getName() 
	{
		return this.name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getScope() 
	{
		return this.scope;
	}

	public void setScope(String scope) 
	{
		this.scope = scope;
	}

	public String getProperty() 
	{
		return this.property;
	}

	public void setProperty(String property) 
	{
		this.property = property;
	}

	public int doStartTag() throws JspException
	{
		String nombreImagen = "relacion_emty.gif";
		
		Object value = TagUtils.getInstance().lookup(this.pageContext, this.name, this.property, this.scope);

		if (value != null) 
		{
			Byte valor = Byte.valueOf(value.toString());

			if (valor.equals(RelacionIniciativaActividad.getRelacionNoDefinible()))
				nombreImagen = "relacion_emty.gif";
			else if (valor.equals(RelacionIniciativaActividad.getRelacionDefinible()))
				nombreImagen = "relacion.gif";
		}
    
		String resultado = "<img style=\"cursor: pointer\" src=\"" + getUrlAplicacion() + "/paginas/strategos/indicadores/imagenes/" + nombreImagen + "\" border=\"0\" width=\"10\" height=\"10\" title=\"" + getMessageResource(null, null, "boton.relacion.alt") + "\">";

		TagUtils.getInstance().write(this.pageContext, resultado);

		return 0;
	}

	public void release() 
	{
		super.release();
		this.name = null;
		this.property = null;
		this.scope = null;
	}
}