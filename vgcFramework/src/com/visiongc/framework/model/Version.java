package com.visiongc.framework.model;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Version
    implements Serializable
{

    public Version()
    {
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getBuild()
    {
        return build;
    }

    public void setBuild(String build)
    {
        this.build = build;
    }

    public String getDateBuild()
    {
        return dateBuild;
    }

    public void setDateBuild(String dateBuild)
    {
        this.dateBuild = dateBuild;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
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
            Version other = (Version)obj;
            return (new EqualsBuilder()).append(getVersion(), other.getVersion()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getVersion()).append(getBuild()).append(getDateBuild()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private String version;
    private String build;
    private String dateBuild;
    private Date createdAt;
}