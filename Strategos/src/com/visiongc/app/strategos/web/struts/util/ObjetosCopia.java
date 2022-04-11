/**
 * 
 */
package com.visiongc.app.strategos.web.struts.util;

/**
 * @author Kerwin
 *
 */
public class ObjetosCopia 
{
	private Long objetoOriginalId;
	private Long objetoCopiaId;
	private Long organizacionDestinoId;

	public ObjetosCopia(Long objetoOriginalId, Long objetoCopiaId, Long organizacionDestinoId)
	{
		this.objetoOriginalId = objetoOriginalId;
		this.objetoCopiaId = objetoCopiaId;
		this.organizacionDestinoId = organizacionDestinoId;
	}

	public ObjetosCopia()
	{
		this.objetoOriginalId = null;
		this.objetoCopiaId = null;
		this.organizacionDestinoId = null;
	}

	public Long getObjetoOriginalId()
	{
		return this.objetoOriginalId;
	}

	public void setObjetoOriginalId(Long objetoOriginalId) 
	{
		this.objetoOriginalId = objetoOriginalId;
	}
	
	public Long getObjetoCopiaId()
	{
		return this.objetoCopiaId;
	}

	public void setObjetoCopiaId(Long objetoCopiaId) 
	{
		this.objetoCopiaId = objetoCopiaId;
	}
	
	public Long getOrganizacionDestinoId()
	{
		return this.organizacionDestinoId;
	}

	public void setOrganizacionDestinoId(Long organizacionDestinoId) 
	{
		this.organizacionDestinoId = organizacionDestinoId;
	}
}
