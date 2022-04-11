package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

// Referenced classes of package com.visiongc.framework.model:
//            PermisoGrupoPK

public class PermisoGrupo
    implements Serializable
{

    public PermisoGrupo(PermisoGrupoPK pk)
    {
        this.pk = pk;
    }

    public PermisoGrupo()
    {
    }

    public PermisoGrupoPK getPk()
    {
        return pk;
    }

    public void setPk(PermisoGrupoPK pk)
    {
        this.pk = pk;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("pk", getPk()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof PermisoGrupo))
        {
            return false;
        } else
        {
            PermisoGrupo castOther = (PermisoGrupo)other;
            return (new EqualsBuilder()).append(getPk(), castOther.getPk()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getPk()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private PermisoGrupoPK pk;
}