package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

public class ConfiguracionUsuarioPK
    implements Serializable
{

    public ConfiguracionUsuarioPK(Long usuarioId, String configuracionBase, String objeto)
    {
        this.usuarioId = usuarioId;
        this.configuracionBase = configuracionBase;
        this.objeto = objeto;
    }

    public ConfiguracionUsuarioPK()
    {
    }

    public Long getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(Long usuario)
    {
        usuarioId = usuario;
    }

    public String getConfiguracionBase()
    {
        return configuracionBase;
    }

    public void setConfiguracionBase(String configuracionBase)
    {
        this.configuracionBase = configuracionBase;
    }

    public String getObjeto()
    {
        return objeto;
    }

    public void setObjeto(String objeto)
    {
        this.objeto = objeto;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("usuarioId", getUsuarioId()).append("configuracionBase", getConfiguracionBase()).append("objeto", getObjeto()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof ConfiguracionUsuarioPK))
        {
            return false;
        } else
        {
            ConfiguracionUsuarioPK castOther = (ConfiguracionUsuarioPK)other;
            return (new EqualsBuilder()).append(getUsuarioId(), castOther.getUsuarioId()).append(getConfiguracionBase(), castOther.getConfiguracionBase()).append(getObjeto(), castOther.getObjeto()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getUsuarioId()).append(getConfiguracionBase()).append(getObjeto()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long usuarioId;
    private String configuracionBase;
    private String objeto;
}