/**
 * 
 */
package com.visiongc.app.strategos.web.struts.taglib;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.visiongc.commons.struts.tag.VgcBaseTag;

/**
 * @author Kerwin
 *
 */
public class ImagenTrueFalseTag extends VgcBaseTag
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
		String nombreImagen = "emty.gif";
		String tituloImagen = getMessageResource(null, null, "boton.historico.alt");
		
		Object value = TagUtils.getInstance().lookup(this.pageContext, this.name, this.property, this.scope);

		if (value != null) 
		{
			Boolean valor = Boolean.parseBoolean(value.toString());

			if (valor != null && valor)
			{
				nombreImagen = "true.gif";
				tituloImagen = getMessageResource(null, null, "boton.boton.true.alt");
			}
			else if (valor != null && !valor)
			{
				nombreImagen = "false.gif";
				tituloImagen = getMessageResource(null, null, "boton.boton.false.alt");
			}
		}
    
		String resultado = "<img style=\"cursor: pointer\" src=\"" + getUrlAplicacion() + "/paginas/strategos/indicadores/imagenes/" + nombreImagen + "\" border=\"0\" width=\"10\" height=\"10\" title=\"" + tituloImagen + "\">";

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
