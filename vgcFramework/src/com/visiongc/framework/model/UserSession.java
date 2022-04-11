package com.visiongc.framework.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UserSession implements Serializable 
{
	static final long serialVersionUID = 0;

	/** identifier field */
	private String sessionId;

	/** persistent field */
	private Long usuarioId;

	/** persistent field */
	private Long personaId;

	/** nullable persistent field */
	private Date loginTs;

	/** nullable persistent field */
	private String remoteAddress;

	/** nullable persistent field */
	private String remoteHost;

	/** nullable persistent field */
	private String remoteUser;

	/** nullable persistent field */
	private String url;

	/** objetos asociados */

	/**
	 * Lista de bloqueos realizados bajo la sesión de usuario
	 */
	private List bloqueos;

	/** nullable persistent field */
	private Usuario usuario;

	/** full constructor */
	public UserSession(String sessionId, Long usuarioId, Long personaId, Date loginTs, String remoteAddress, String remoteHost, String remoteUser, String url, List bloqueos, Usuario usuario) 
	{
		this.sessionId = sessionId;
		this.usuarioId = usuarioId;
		this.personaId = personaId;
		this.loginTs = loginTs;
		this.remoteAddress = remoteAddress;
		this.remoteHost = remoteHost;
		this.remoteUser = remoteUser;
		this.url = url;
		this.bloqueos = bloqueos;
		this.usuario = usuario;
	}

	/** default constructor */
	public UserSession() 
	{
	}

	public String getSessionId() 
	{
		return this.sessionId;
	}

	public void setSessionId(String sessionId) 
	{
		this.sessionId = sessionId;
	}

	public Long getUsuarioId() 
	{
		return this.usuarioId;
	}

	public void setUsuarioId(Long usuarioId) 
	{
		this.usuarioId = usuarioId;
	}

	public Long getPersonaId() 
	{
		return personaId;
	}

	public void setPersonaId(Long personaId) 
	{
		this.personaId = personaId;
	}

	public Date getLoginTs() 
	{
		return this.loginTs;
	}

	public void setLoginTs(Date loginTs) 
	{
		this.loginTs = loginTs;
	}

	public String getRemoteAddress() 
	{
		return this.remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) 
	{
		this.remoteAddress = remoteAddress;
	}

	public String getRemoteHost() 
	{
		return this.remoteHost;
	}

	public void setRemoteHost(String remoteHost) 
	{
		this.remoteHost = remoteHost;
	}

	public String getRemoteUser() 
	{
		return this.remoteUser;
	}

	public void setRemoteUser(String remoteUser) 
	{
		this.remoteUser = remoteUser;
	}

	public String getUrl() 
	{
		return this.url;
	}

	public List getBloqueos() 
	{
		return bloqueos;
	}

	public void setBloqueos(List bloqueos) 
	{
		this.bloqueos = bloqueos;
	}

	public Integer getNroBloqueos() 
	{
		Integer nroBloqueos = null;

		if (this.bloqueos != null) 
			nroBloqueos = new Integer(this.bloqueos.size());

		return nroBloqueos;

	}

	public void setUrl(String url) 
	{
		this.url = url;
	}

	public Usuario getUsuario() 
	{
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) 
	{
		this.usuario = usuario;
	}
	
	public String getUsuarioLogin() 
	{
		return this.usuario.getUId();
	}

	public String toString() 
	{
		return new ToStringBuilder(this).append("sessionId", getSessionId()).toString();
	}
}
