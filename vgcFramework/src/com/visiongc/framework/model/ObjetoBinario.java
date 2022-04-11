package com.visiongc.framework.model;

import java.io.Serializable;
import java.sql.Blob;
import org.apache.commons.lang.builder.*;

public class ObjetoBinario
    implements Serializable
{

    public ObjetoBinario()
    {
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public Long getObjetoBinarioId()
    {
        return objetoBinarioId;
    }

    public void setObjetoBinarioId(Long objetoBinarioId)
    {
        this.objetoBinarioId = objetoBinarioId;
    }

    public Blob getDataBlob()
    {
        return dataBlob;
    }

    public void setDataBlob(Blob dataBlob)
        throws Exception
    {
        this.dataBlob = dataBlob;
        if(dataBlob != null)
            try
            {
                int blobLength = (int)this.dataBlob.length();
                byte blobAsBytes[] = this.dataBlob.getBytes(1L, blobLength);
                this.dataBlob.free();
                data = blobAsBytes;
            }
            catch(Exception e)
            {
                data = dataBlob.getBytes(0L, (int)dataBlob.length());
            }
    }

    public byte[] getData()
    {
        return data;
    }

    public void setData(byte data[])
    {
        this.data = data;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("objetoBinarioId", getObjetoBinarioId()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof ObjetoBinario))
        {
            return false;
        } else
        {
            ObjetoBinario castOther = (ObjetoBinario)other;
            return (new EqualsBuilder()).append(getObjetoBinarioId(), castOther.getObjetoBinarioId()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getObjetoBinarioId()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long objetoBinarioId;
    private String nombre;
    private String mimeType;
    private Blob dataBlob;
    private byte data[];
}