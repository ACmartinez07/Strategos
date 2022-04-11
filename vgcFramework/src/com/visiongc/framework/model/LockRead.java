package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.*;

// Referenced classes of package com.visiongc.framework.model:
//            LockReadPK

public class LockRead
    implements Serializable
{

    public LockRead(LockReadPK pk)
    {
        this.pk = pk;
    }

    public LockRead()
    {
    }

    public LockReadPK getPk()
    {
        return pk;
    }

    public void setPk(LockReadPK pk)
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
        if(!(other instanceof LockRead))
        {
            return false;
        } else
        {
            LockRead castOther = (LockRead)other;
            return (new EqualsBuilder()).append(getPk(), castOther.getPk()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getPk()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private LockReadPK pk;
}