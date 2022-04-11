package com.visiongc.framework.arboles;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TipoNodoHoja
    implements Serializable
{

    public TipoNodoHoja()
    {
    }

    public byte getTipoNodoId()
    {
        return tipoNodoId;
    }

    public void setTipoNodoId(byte tipoNodoId)
    {
        this.tipoNodoId = tipoNodoId;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public static Byte getTipoNodoHojaSimple()
    {
        return new Byte((byte)1);
    }

    public static Byte getTipoNodoHojaConplejo()
    {
        return new Byte((byte)2);
    }

    public static List getTipoNodos()
    {
        return getTipoNodosHoja(null);
    }

    public static List getTipoNodosHoja(VgcMessageResources messageResources)
    {
        if(messageResources == null)
            messageResources = VgcResourceManager.getMessageResources("Framework");
        List tipoNodosHoja = new ArrayList();
        TipoNodoHoja tipoNodoHoja = new TipoNodoHoja();
        tipoNodoHoja.tipoNodoId = 1;
        tipoNodoHoja.nombre = messageResources.getResource("tiponodohoja.simple");
        tipoNodosHoja.add(tipoNodoHoja);
        tipoNodoHoja.tipoNodoId = 2;
        tipoNodoHoja.nombre = messageResources.getResource("tiponodohoja.complejo");
        tipoNodosHoja.add(tipoNodoHoja);
        return tipoNodosHoja;
    }

    public static String getNombre(byte tipoNodoHoja)
    {
        String nombre = "";
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
        if(tipoNodoHoja == 1)
            nombre = messageResources.getResource("tiponodohoja.simple");
        if(tipoNodoHoja == 2)
            nombre = messageResources.getResource("tiponodohoja.complejo");
        return nombre;
    }

    static final long serialVersionUID = 0L;
    private static final byte TIPO_NODO_HOJA_SIMPLE = 1;
    private static final byte TIPO_NODO_HOJA_COMPLEJO = 2;
    private byte tipoNodoId;
    private String nombre;
}