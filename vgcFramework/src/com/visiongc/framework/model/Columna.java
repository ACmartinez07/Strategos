package com.visiongc.framework.model;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.*;

public class Columna
    implements Serializable
{
    public static class ColumnaTipo
    {

        private static Byte getTipo(Byte tipo)
        {
            if(tipo.byteValue() == 1)
                return new Byte((byte)1);
            if(tipo.byteValue() == 2)
                return new Byte((byte)2);
            if(tipo.byteValue() == 3)
                return new Byte((byte)3);
            else
                return null;
        }

        public static Byte getTipoString()
        {
            return new Byte((byte)1);
        }

        public static Byte getTipoFloat()
        {
            return new Byte((byte)2);
        }

        public static Byte getTipoDate()
        {
            return new Byte((byte)3);
        }

        public Byte getId()
        {
            return id;
        }

        public void setId(Byte id)
        {
            this.id = id;
        }

        public String getNombre()
        {
            return nombre;
        }

        public void setNombre(String nombre)
        {
            this.nombre = nombre;
        }

        public static List getTipos()
        {
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            List tipos = new ArrayList();
            ColumnaTipo tipo = new ColumnaTipo();
            tipo.setId(Byte.valueOf((byte)1));
            tipo.nombre = messageResources.getResource("columna.tipo.string");
            tipos.add(tipo);
            tipo = new ColumnaTipo();
            tipo.setId(Byte.valueOf((byte)2));
            tipo.nombre = messageResources.getResource("columna.tipo.float");
            tipos.add(tipo);
            tipo = new ColumnaTipo();
            tipo.setId(Byte.valueOf((byte)3));
            tipo.nombre = messageResources.getResource("columna.tipo.date");
            tipos.add(tipo);
            return tipos;
        }

        public static String getNombre(Byte tipo)
        {
            String nombre = "";
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            if(tipo.byteValue() == 1)
                nombre = messageResources.getResource("columna.tipo.string");
            else
            if(tipo.byteValue() == 2)
                nombre = messageResources.getResource("columna.tipo.float");
            else
            if(tipo.byteValue() == 3)
                nombre = messageResources.getResource("columna.tipo.date");
            return nombre;
        }

        private Byte id;
        private String nombre;
        private static final byte TIPO_STRING = 1;
        private static final byte TIPO_FLOAT = 2;
        private static final byte TIPO_DATE = 3;


        public ColumnaTipo()
        {
        }
    }


    public Columna(String nombre, String alias, Byte tipo, Integer tamano, String formato, Boolean clavePrimaria, Long indicadorId)
    {
        this.clavePrimaria = Boolean.valueOf(false);
        visible = Boolean.valueOf(true);
        ancho = "80";
        agrupar = Boolean.valueOf(false);
        orden = null;
        this.nombre = nombre;
        this.alias = alias;
        this.tipo = tipo;
        this.tamano = tamano;
        this.formato = formato;
        this.clavePrimaria = clavePrimaria;
        this.indicadorId = indicadorId;
    }

    public Columna()
    {
        clavePrimaria = Boolean.valueOf(false);
        visible = Boolean.valueOf(true);
        ancho = "80";
        agrupar = Boolean.valueOf(false);
        orden = null;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public Byte getTipo()
    {
        return tipo;
    }

    public void setTipo(Byte tipo)
    {
        this.tipo = ColumnaTipo.getTipo(tipo);
    }

    public Integer getTamano()
    {
        return tamano;
    }

    public void setTamano(Integer tamano)
    {
        this.tamano = tamano;
    }

    public String getFormato()
    {
        return formato;
    }

    public void setFormato(String formato)
    {
        this.formato = formato;
    }

    public Boolean getClavePrimaria()
    {
        return clavePrimaria;
    }

    public void setClavePrimaria(Boolean clavePrimaria)
    {
        this.clavePrimaria = clavePrimaria;
    }

    public Long getIndicadorId()
    {
        return indicadorId;
    }

    public void setIndicadorId(Long indicadorId)
    {
        this.indicadorId = indicadorId;
    }

    public Integer getPosicionArchivo()
    {
        return posicionArchivo;
    }

    public void setPosicionArchivo(Integer posicionArchivo)
    {
        this.posicionArchivo = posicionArchivo;
    }

    public String getValorArchivo()
    {
        return valorArchivo;
    }

    public void setValorArchivo(String valorArchivo)
    {
        this.valorArchivo = valorArchivo;
    }

    public String getValorReal()
    {
        return valorReal;
    }

    public void setValorReal(String valorReal)
    {
        this.valorReal = valorReal;
    }

    public Boolean getVisible()
    {
        return visible;
    }

    public void setVisible(Boolean visible)
    {
        this.visible = visible;
    }

    public String getAncho()
    {
        return ancho;
    }

    public void setAncho(String ancho)
    {
        this.ancho = ancho;
    }

    public Boolean getAgrupar()
    {
        return agrupar;
    }

    public void setAgrupar(Boolean agrupar)
    {
        this.agrupar = agrupar;
    }

    public String getOrden()
    {
        return orden;
    }

    public void setOrden(String orden)
    {
        this.orden = orden;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("nombre", getNombre()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof Columna))
        {
            return false;
        } else
        {
            Columna castOther = (Columna)other;
            return (new EqualsBuilder()).append(getNombre(), castOther.getNombre()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getNombre()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private String nombre;
    private String alias;
    private Byte tipo;
    private Integer tamano;
    private String formato;
    private Boolean clavePrimaria;
    private Long indicadorId;
    private Integer posicionArchivo;
    private String valorArchivo;
    private String valorReal;
    private Boolean visible;
    private String ancho;
    private Boolean agrupar;
    private String orden;
}