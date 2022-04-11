package com.visiongc.framework.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.*;

public class Error
    implements Serializable
{

    public Error(Long errNumber, String errSource, String errDescription, String errStackTrace, String errCause, Date errTimestamp, String errUserId, 
            String errVersion, String errStep)
    {
        this.errNumber = errNumber;
        if(errSource != null && errSource.length() > 2000)
            this.errSource = errSource.substring(0, 1999);
        else
            this.errSource = errSource;
        if(errDescription != null && errDescription.length() > 2000)
            this.errDescription = errDescription.substring(0, 1999);
        else
            this.errDescription = errDescription;
        this.errStackTrace = errStackTrace;
        if(errCause != null && errCause.length() > 2000)
            this.errCause = errCause.substring(0, 1999);
        else
            this.errCause = errCause;
        this.errTimestamp = errTimestamp;
        this.errUserId = errUserId;
        this.errVersion = errVersion;
        this.errStep = errStep;
    }

    public Error(Date errTimestamp, String errDescripcion)
    {
        this.errTimestamp = errTimestamp;
        errDescription = errDescripcion;
    }

    public Error()
    {
    }

    public Long getErrNumber()
    {
        return errNumber;
    }

    public void setErrNumber(Long errNumber)
    {
        this.errNumber = errNumber;
    }

    public String getErrSource()
    {
        return errSource;
    }

    public void setErrSource(String errSource)
    {
        if(errSource.length() > 2000)
            this.errSource = errSource.substring(0, 1999);
        else
            this.errSource = errSource;
    }

    public String getErrDescription()
    {
        return errDescription;
    }

    public void setErrDescription(String errDescription)
    {
        if(errDescription.length() > 2000)
            this.errDescription = errDescription.substring(0, 1999);
        else
            this.errDescription = errDescription;
    }

    public String getDescripcionCorta()
    {
        String descripcion = errDescription;
        if(descripcion != null && descripcion.length() > 200)
            descripcion = descripcion.substring(0, 200);
        return descripcion;
    }

    public String getErrStackTrace()
    {
        return errStackTrace;
    }

    public void setErrStackTrace(String errStackTrace)
    {
        this.errStackTrace = errStackTrace;
    }

    public String getErrCause()
    {
        return errCause;
    }

    public void setErrCause(String errCause)
    {
        if(errCause.length() > 2000)
            this.errCause = errCause.substring(0, 1999);
        else
            this.errCause = errCause;
    }

    public Date getErrTimestamp()
    {
        return errTimestamp;
    }

    public void setErrTimestamp(Date errTimestamp)
    {
        this.errTimestamp = errTimestamp;
    }

    public Long getFechaMiliSeg()
    {
        Long fechaMs = null;
        if(errTimestamp != null)
            fechaMs = new Long(errTimestamp.getTime());
        return fechaMs;
    }

    public String getErrUserId()
    {
        return errUserId;
    }

    public void setErrUserId(String errUserId)
    {
        this.errUserId = errUserId;
    }

    public String getErrVersion()
    {
        return errVersion;
    }

    public void setErrVersion(String errVersion)
    {
        this.errVersion = errVersion;
    }

    public String getErrStep()
    {
        return errStep;
    }

    public void setErrStep(String errStep)
    {
        this.errStep = errStep;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("errNumber", getErrNumber()).append("errSource", getErrSource()).append("errDescription", getErrDescription()).append("errTimestamp", getErrTimestamp()).append("errUserId", getErrUserId()).append("errVersion", getErrVersion()).append("errStep", getErrStep()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof Error))
        {
            return false;
        } else
        {
            Error castOther = (Error)other;
            return (new EqualsBuilder()).append(getErrNumber(), castOther.getErrNumber()).append(getErrSource(), castOther.getErrSource()).append(getErrDescription(), castOther.getErrDescription()).append(getErrTimestamp(), castOther.getErrTimestamp()).append(getErrUserId(), castOther.getErrUserId()).append(getErrVersion(), castOther.getErrVersion()).append(getErrStep(), castOther.getErrStep()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getErrNumber()).append(getErrSource()).append(getErrDescription()).append(getErrTimestamp()).append(getErrUserId()).append(getErrVersion()).append(getErrStep()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long errNumber;
    private String errSource;
    private String errDescription;
    private String errStackTrace;
    private String errCause;
    private Date errTimestamp;
    private String errUserId;
    private String errVersion;
    private String errStep;
}