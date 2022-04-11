package com.visiongc.framework.model;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.*;
import org.apache.commons.lang.builder.*;

// Referenced classes of package com.visiongc.framework.model:
//            Usuario

public class Importacion
    implements Serializable
{
    public static class ImportacionType
    {

        public static Byte getType(Byte type)
        {
            if(type.byteValue() == 1)
                return Byte.valueOf((byte)1);
            if(type.byteValue() == 2)
                return Byte.valueOf((byte)2);
            if(type.byteValue() == 3)
                return Byte.valueOf((byte)3);
            if(type.byteValue() == 4)
                return Byte.valueOf((byte)4);
            if(type.byteValue() == 5)
                return Byte.valueOf((byte)5);
            else
                return null;
        }

        public static Byte getImportacionTypePlano()
        {
            return Byte.valueOf((byte)1);
        }

        public static Byte getImportacionTypeExcel()
        {
            return Byte.valueOf((byte)2);
        }

        public static Byte getImportacionTypeOracle()
        {
            return Byte.valueOf((byte)3);
        }

        public static Byte getImportacionTypePostGreSQL()
        {
            return Byte.valueOf((byte)4);
        }

        public static Byte getImportacionTypeSqlServer()
        {
            return Byte.valueOf((byte)5);
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

        public static List getTipos(List tipos)
        {
            List tips = new ArrayList();
            for(Iterator i = getTipos().iterator(); i.hasNext();)
            {
                ImportacionType tipo = (ImportacionType)i.next();
                for(Iterator j = tipos.iterator(); j.hasNext();)
                {
                    Byte type = (Byte)j.next();
                    if(type.byteValue() == tipo.getId().byteValue())
                    {
                        tips.add(tipo);
                        break;
                    }
                }

            }

            return tips;
        }

        public static List getTipos()
        {
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            List tipos = new ArrayList();
            ImportacionType tipo = new ImportacionType();
            tipo.setId(Byte.valueOf((byte)1));
            tipo.nombre = messageResources.getResource("importacion.tipo.plano");
            tipos.add(tipo);
            tipo = new ImportacionType();
            tipo.setId(Byte.valueOf((byte)2));
            tipo.nombre = messageResources.getResource("importacion.tipo.excel");
            tipos.add(tipo);
            tipo = new ImportacionType();
            tipo.setId(Byte.valueOf((byte)3));
            tipo.nombre = messageResources.getResource("importacion.tipo.oracle");
            tipos.add(tipo);
            tipo = new ImportacionType();
            tipo.setId(Byte.valueOf((byte)4));
            tipo.nombre = messageResources.getResource("importacion.tipo.postgresql");
            tipos.add(tipo);
            tipo = new ImportacionType();
            tipo.setId(Byte.valueOf((byte)5));
            tipo.nombre = messageResources.getResource("importacion.tipo.sqlserver");
            tipos.add(tipo);
            return tipos;
        }

        public static String getNombre(Byte tipo)
        {
            String nombre = "";
            VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
            if(tipo.byteValue() == 1)
                nombre = messageResources.getResource("importacion.tipo.plano");
            else
            if(tipo.byteValue() == 2)
                nombre = messageResources.getResource("importacion.tipo.excel");
            else
            if(tipo.byteValue() == 3)
                nombre = messageResources.getResource("importacion.tipo.oracle");
            else
            if(tipo.byteValue() == 4)
                nombre = messageResources.getResource("importacion.tipo.postgresql");
            else
            if(tipo.byteValue() == 5)
                nombre = messageResources.getResource("importacion.tipo.sqlserver");
            return nombre;
        }

        private Byte id;
        private String nombre;
        private static final byte IMPORTACION_TYPE_PLANO = 1;
        private static final byte IMPORTACION_TYPE_EXCEL = 2;
        private static final byte IMPORTACION_TYPE_ORACLE = 3;
        private static final byte IMPORTACION_TYPE_POSTGRESQL = 4;
        private static final byte IMPORTACION_TYPE_SQLSERVER = 5;

        public ImportacionType()
        {
        }
    }


    public Importacion(Long id, Long usuarioId, String nombre, Byte tipo, String configuracion, Usuario usuario)
    {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.tipo = tipo;
        this.configuracion = configuracion;
        this.usuario = usuario;
    }

    public Importacion()
    {
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public Byte getTipo()
    {
        return tipo;
    }

    public void setTipo(Byte tipo)
    {
        this.tipo = ImportacionType.getType(tipo);
    }

    public String getConfiguracion()
    {
        return configuracion;
    }

    public void setConfiguracion(String configuracion)
    {
        this.configuracion = configuracion;
    }

    public Usuario getUsuario()
    {
        return usuario;
    }

    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }

    public String toString()
    {
        return (new ToStringBuilder(this)).append("id", getId()).toString();
    }

    public boolean equals(Object other)
    {
        if(this == other)
            return true;
        if(!(other instanceof Importacion))
        {
            return false;
        } else
        {
            Importacion castOther = (Importacion)other;
            return (new EqualsBuilder()).append(getId(), castOther.getId()).isEquals();
        }
    }

    public int hashCode()
    {
        return (new HashCodeBuilder()).append(getId()).toHashCode();
    }

    static final long serialVersionUID = 0L;
    private Long id;
    private Long usuarioId;
    private String nombre;
    private Usuario usuario;
    private Byte tipo;
    private String configuracion;
}