package com.visiongc.framework.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.*;

// Referenced classes of package com.visiongc.framework.model:
//            LockPK

public class Lock
    implements Serializable
{

    public Lock(LockPK pk, String instancia, Date timeStamp)
    {
        this.pk = pk;
        this.instancia = instancia;
        this.timeStamp = timeStamp;
    }

    public Lock()
    {
    }

    public LockPK getPk()
    {
        return pk;
    }

    public void setPk(LockPK pk)
    {
        this.pk = pk;
    }

    public String getInstancia()
    {
        return instancia;
    }

    public void setInstancia(String instancia)
    {
        this.instancia = instancia;
    }

    public Date getTimeStamp()
    {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp)
    {
        this.timeStamp = timeStamp;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("pk", getPk()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof Lock))
        {
            return false;
        } else
        {
            Lock castOther = (Lock)other;
            return (new EqualsBuilder()).append(getPk(), castOther.getPk()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getPk()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private LockPK pk;
    private String instancia;
    private Date timeStamp;
}