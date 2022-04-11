package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

// Referenced classes of package com.visiongc.framework.model:
//            ConfiguracionUsuarioPK

public class ConfiguracionUsuario
    implements Serializable
{

    public ConfiguracionUsuario(ConfiguracionUsuarioPK pk, String data)
    {
        this.pk = pk;
        this.data = data;
    }

    public ConfiguracionUsuario()
    {
    }

    public ConfiguracionUsuario(ConfiguracionUsuarioPK pk)
    {
        this.pk = pk;
    }

    public ConfiguracionUsuarioPK getPk()
    {
        return pk;
    }

    public void setPk(ConfiguracionUsuarioPK pk)
    {
        this.pk = pk;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public Long getDefaulUser()
    {
        return new Long(0L);
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("pk", getPk()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof ConfiguracionUsuario))
        {
            return false;
        } else
        {
            ConfiguracionUsuario castOther = (ConfiguracionUsuario)other;
            return (new EqualsBuilder()).append(getPk(), castOther.getPk()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getPk()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    public static final long DEFAULT_USER = 0L;
    private ConfiguracionUsuarioPK pk;
    private String data;
}