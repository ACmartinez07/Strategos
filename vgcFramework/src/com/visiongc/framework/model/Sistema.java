package com.visiongc.framework.model;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Sistema
    implements Serializable
{
    public static class SistemaTipo
    {

        private static Byte getSistemaTipo(Byte tipo)
        {
            if(tipo.byteValue() == 1)
                return new Byte((byte)1);
            if(tipo.byteValue() == 2)
                return new Byte((byte)2);
            if(tipo.byteValue() == 3)
                return new Byte((byte)3);
            if(tipo.byteValue() == 4)
                return new Byte((byte)4);
            else
                return null;
        }

        public static String getSistemaTipoName(Byte tipo)
        {
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            if(tipo.byteValue() == 1)
                return messageResources.getResource("sistema.producto.prototipo");
            if(tipo.byteValue() == 2)
                return messageResources.getResource("sistema.producto.demo");
            if(tipo.byteValue() == 3)
                return messageResources.getResource("sistema.producto.full");
            if(tipo.byteValue() == 4)
                return messageResources.getResource("sistema.producto.limitado");
            else
                return null;
        }

        public static Byte getSistemaTipoPrototipo()
        {
            return new Byte((byte)1);
        }

        public static Byte getSistemaTipoDemo()
        {
            return new Byte((byte)2);
        }

        public static Byte getSistemaTipoFull()
        {
            return new Byte((byte)3);
        }

        public static Byte getSistemaTipoLimitado()
        {
            return new Byte((byte)4);
        }

        private static final byte SISTEMATIPO_PROTOTIPO = 1;
        private static final byte SISTEMATIPO_DEMO = 2;
        private static final byte SISTEMATIPO_FULL = 3;
        private static final byte SISTEMATIPO_LIMITADO = 4;


        public SistemaTipo()
        {
        }
    }


    public Sistema()
    {
    }

    public Byte getTipoProducto()
    {
        return tipoProducto;
    }

    public void setTipoProducto(Byte tipoProducto)
    {
        this.tipoProducto = SistemaTipo.getSistemaTipo(tipoProducto);
    }

    public String getProducto()
    {
        return producto;
    }

    public void setProducto(String producto)
    {
        this.producto = producto;
    }

    public String getRdbmsId()
    {
        return rdbmsId;
    }

    public void setRdbmsId(String rdbmsId)
    {
        this.rdbmsId = rdbmsId;
    }

    public Long getSistemaId()
    {
        return sistemaId;
    }

    public void setSistemaId(Long sistemaId)
    {
        this.sistemaId = sistemaId;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public Long getBuild()
    {
        return build;
    }

    public void setBuild(Long build)
    {
        this.build = build;
    }

    public Date getFecha()
    {
        return fecha;
    }

    public void setFecha(Date fecha)
    {
        this.fecha = fecha;
    }

    public String getCmaxc()
    {
        return cmaxc;
    }

    public void setCmaxc(String cmaxc)
    {
        this.cmaxc = cmaxc;
    }

    public String getActual()
    {
        return actual;
    }

    public void setActual(String actual)
    {
        this.actual = actual;
    }

    public int compareTo(Object o)
    {
        Sistema or = (Sistema)o;
        return getSistemaId().compareTo(or.getSistemaId());
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("sistemaId", getSistemaId()).toString();
    }

    static final long serialVersionUID = 0L;
    private Long sistemaId;
    private String version;
    private Long build;
    private Date fecha;
    private String rdbmsId;
    private String producto;
    private String cmaxc;
    private Byte tipoProducto;
    private String actual;
}