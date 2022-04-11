package com.visiongc.commons.util;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.visiongc.commons.util:
//            VgcResourceManager, VgcMessageResources

public class CondicionType
{

    public CondicionType()
    {
    }

    public static Byte getFiltroCondicionType(Byte condicionType)
    {
        if(condicionType.byteValue() == 0)
            return new Byte((byte)0);
        if(condicionType.byteValue() == 1)
            return new Byte((byte)1);
        if(condicionType.byteValue() == 2)
            return new Byte((byte)2);
        else
            return null;
    }

    public static byte getFiltroCondicionTodos()
    {
        return 0;
    }

    public static byte getFiltroCondicionActivo()
    {
        return 1;
    }

    public static byte getFiltroCondicionInactivo()
    {
        return 2;
    }

    public byte getFiltroCondicionTypeId()
    {
        return filtroCondicionTypeId;
    }

    public void setFiltroCondicionTypeId(byte filtroCondicionTypeId)
    {
        this.filtroCondicionTypeId = filtroCondicionTypeId;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public static List getCondicionesTypes()
    {
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
        List tiposCondiciones = new ArrayList();
        CondicionType tipoCondicion = new CondicionType();
        tipoCondicion.setFiltroCondicionTypeId((byte)0);
        tipoCondicion.nombre = messageResources.getResource("filtro.condicion.todos");
        tiposCondiciones.add(tipoCondicion);
        tipoCondicion = new CondicionType();
        tipoCondicion.setFiltroCondicionTypeId((byte)1);
        tipoCondicion.nombre = messageResources.getResource("filtro.condicion.activo");
        tiposCondiciones.add(tipoCondicion);
        tipoCondicion = new CondicionType();
        tipoCondicion.setFiltroCondicionTypeId((byte)2);
        tipoCondicion.nombre = messageResources.getResource("filtro.condicion.inactivo");
        tiposCondiciones.add(tipoCondicion);
        return tiposCondiciones;
    }

    public static String getNombre(byte tipoCondicion)
    {
        String nombre = "";
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
        if(tipoCondicion == 0)
            nombre = messageResources.getResource("filtro.condicion.todos");
        else
        if(tipoCondicion == 1)
            nombre = messageResources.getResource("filtro.condicion.activo");
        else
        if(tipoCondicion == 2)
            nombre = messageResources.getResource("filtro.condicion.inactivo");
        return nombre;
    }

    private byte filtroCondicionTypeId;
    private String nombre;
    private static final byte FILTRO_CONDICION_TODOS = 0;
    private static final byte FILTRO_CONDICION_ACTIVO = 1;
    private static final byte FILTRO_CONDICION_INACTIVO = 2;
}