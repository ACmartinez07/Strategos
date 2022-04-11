package com.visiongc.framework.model;

import com.visiongc.commons.util.Password;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PwdHistoria
    implements Serializable
{

    public PwdHistoria(Long usuarioId, String pwd, Date fecha)
    {
        this.usuarioId = usuarioId;
        this.pwd = pwd;
        this.fecha = fecha;
    }

    public PwdHistoria()
    {
    }

    public Long getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId)
    {
        this.usuarioId = usuarioId;
    }

    public String getPwd()
    {
        return pwd;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    public String getPwdPlain()
    {
        try
        {
            return Password.decriptPassWord(pwd);
        }
        catch(Throwable t)
        {
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
    }

    public void setPwdPlain(String pwd)
    {
        try
        {
            if(pwd != null && !pwd.equals(""))
                this.pwd = Password.encriptPassWord(pwd);
            else
                this.pwd = null;
        }
        catch(Throwable t)
        {
            throw new ChainedRuntimeException(t.getMessage(), t);
        }
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
        {
            return false;
        } else
        {
            PwdHistoria other = (PwdHistoria)obj;
            return (new EqualsBuilder()).append(getUsuarioId(), other.getUsuarioId()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getUsuarioId()).append(getPwd()).append(getFecha()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long usuarioId;
    private String pwd;
    private Date fecha;
}