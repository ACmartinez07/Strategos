/**
 * 
 */
package com.visiongc.app.strategos.web.struts.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.visiongc.commons.struts.tag.VgcBaseTag;
import com.visiongc.commons.util.CondicionType;

/**
 * @author Kerwin
 *
 */
public class ImagenActivoPortafolioTag extends VgcBaseTag
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
		String nombreImagen = "portafolioActivo.gif";
		String titulo = getMessageResource(null, null, "boton.activo.alt");
		Object value = TagUtils.getInstance().lookup(this.pageContext, this.name, this.property, this.scope);

		if (value != null) 
		{
			Byte valorCondicion = Byte.valueOf(value.toString());

			if (valorCondicion.byteValue() == CondicionType.getFiltroCondicionInactivo())
			{
				nombreImagen = "portafolioInactivo.gif";
				titulo = getMessageResource(null, null, "boton.inactivo.alt");
			}
		}
    
		String resultado = "<img style=\"cursor: pointer\" src=\"" + getUrlAplicacion() + "/paginas/strategos/indicadores/imagenes/" + nombreImagen + "\" border=\"0\" width=\"10\" height=\"10\" title=\"" + titulo + "\">";

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