package com.visiongc.framework.model;

import com.visiongc.framework.permisologia.OrganizacionPermisologia;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OrganizacionBasica
    implements Serializable, OrganizacionPermisologia
{

    public OrganizacionBasica(Long organizacionId, Long padreId, String nombre, Date creado, Date modificado, Long creadoId, Long modificadoId)
    {
        this.organizacionId = organizacionId;
        this.padreId = padreId;
        this.nombre = nombre;
        this.creado = creado;
        this.modificado = modificado;
        this.creadoId = creadoId;
        this.modificadoId = modificadoId;
    }

    public OrganizacionBasica()
    {
    }

    public OrganizacionBasica(Long organizacionId, Long padreId, String nombre)
    {
        this.organizacionId = organizacionId;
        this.padreId = padreId;
        this.nombre = nombre;
    }

    public Long getOrganizacionId()
    {
        return organizacionId;
    }

    public void setOrganizacionId(Long organizacionId)
    {
        this.organizacionId = organizacionId;
    }

    public Long getPadreId()
    {
        return padreId;
    }

    public void setPadreId(Long padreId)
    {
        this.padreId = padreId;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    public Date getCreado()
    {
        return creado;
    }

    public void setCreado(Date creado)
    {
        this.creado = creado;
    }

    public Date getModificado()
    {
        return modificado;
    }

    public void setModificado(Date modificado)
    {
        this.modificado = modificado;
    }

    public Long getCreadoId()
    {
        return creadoId;
    }

    public void setCreadoId(Long creadoId)
    {
        this.creadoId = creadoId;
    }

    public Long getModificadoId()
    {
        return modificadoId;
    }

    public void setModificadoId(Long modificadoId)
    {
        this.modificadoId = modificadoId;
    }

    public int compareTo(Object o)
    {
        OrganizacionBasica or = (OrganizacionBasica)o;
        return getOrganizacionId().compareTo(or.getOrganizacionId());
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("organizacionId", getOrganizacionId()).toString();
    }

    static final long serialVersionUID = 0L;
    private Long organizacionId;
    private Long padreId;
    private String nombre;
    private String direccion;
    private Date creado;
    private Date modificado;
    private Long creadoId;
    private Long modificadoId;
}