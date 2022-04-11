package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

// Referenced classes of package com.visiongc.framework.model:
//            Usuario

public class Plantilla
    implements Serializable
{

    public Plantilla(Long plantillaId, Long objetoId, Long usuarioId, String nombre, String descripcion, Boolean publico, String tipo, 
            String xml, Usuario usuario)
    {
        this.plantillaId = plantillaId;
        this.objetoId = objetoId;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.publico = publico;
        this.tipo = tipo;
        this.xml = xml;
        this.usuario = usuario;
    }

    public Plantilla()
    {
    }

    public Plantilla(Long plantillaId, Long usuarioId, String nombre, Boolean publico, String tipo, String xml)
    {
        this.plantillaId = plantillaId;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.publico = publico;
        this.tipo = tipo;
        this.xml = xml;
    }

    public Long getPlantillaId()
    {
        return plantillaId;
    }

    public void setPlantillaId(Long plantillaId)
    {
        this.plantillaId = plantillaId;
    }

    public Long getObjetoId()
    {
        return objetoId;
    }

    public void setObjetoId(Long objetoId)
    {
        this.objetoId = objetoId;
    }

    public Long getUsuarioId()
    {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId)
    {
        this.usuarioId = usuarioId;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public Boolean getPublico()
    {
        return publico;
    }

    public void setPublico(Boolean publico)
    {
        this.publico = publico;
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }

    public String getXml()
    {
        return xml;
    }

    public void setXml(String xml)
    {
        this.xml = xml;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public int compareTo(Object o)
    {
        Plantilla or = (Plantilla)o;
        return getPlantillaId().compareTo(or.getPlantillaId());
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("plantillaId", getPlantillaId()).toString();
    }

    static final long serialVersionUID = 0L;
    private Long plantillaId;
    private Long objetoId;
    private Long usuarioId;
    private String nombre;
    private String descripcion;
    private Boolean publico;
    private String tipo;
    private String xml;
    private Usuario usuario;
}