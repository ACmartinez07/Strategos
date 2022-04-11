package com.visiongc.framework.model;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ObjetoSistema
    implements Serializable, Comparable
{

    public ObjetoSistema(String objetoId, String articuloSingular, String nombreSingular, String articuloPlural, String nombrePlural)
    {
        this.objetoId = objetoId;
        this.articuloSingular = articuloSingular;
        this.nombreSingular = nombreSingular;
        this.articuloPlural = articuloPlural;
        this.nombrePlural = nombrePlural;
    }

    public ObjetoSistema()
    {
    }

    public String getObjetoId()
    {
        return objetoId;
    }

    public void setObjetoId(String objetoId)
    {
        this.objetoId = objetoId;
    }

    public String getArticuloSingular()
    {
        return articuloSingular;
    }

    public void setArticuloSingular(String articuloSingular)
    {
        this.articuloSingular = articuloSingular;
    }

    public String getNombreSingular()
    {
        return nombreSingular;
    }

    public void setNombreSingular(String nombreSingular)
    {
        this.nombreSingular = nombreSingular;
    }

    public String getArticuloPlural()
    {
        return articuloPlural;
    }

    public void setArticuloPlural(String articuloPlural)
    {
        this.articuloPlural = articuloPlural;
    }

    public String getNombrePlural()
    {
        return nombrePlural;
    }

    public void setNombrePlural(String nombrePlural)
    {
        this.nombrePlural = nombrePlural;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("objetoId", getObjetoId()).toString();
    }

    public int compareTo(Object o)
    {
        ObjetoSistema os = (ObjetoSistema)o;
        return getObjetoId().compareTo(os.getObjetoId());
    }

    static final long serialVersionUID = 0L;
    private String objetoId;
    private String articuloSingular;
    private String nombreSingular;
    private String articuloPlural;
    private String nombrePlural;
}