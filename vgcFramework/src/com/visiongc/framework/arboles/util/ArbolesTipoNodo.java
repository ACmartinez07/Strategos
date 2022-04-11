package com.visiongc.framework.arboles.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.util.ArrayList;
import java.util.List;

public class ArbolesTipoNodo
{

    public ArbolesTipoNodo()
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

    public static byte getArbolesTipoNodoHijo()
    {
        return ARBOLES_TIPO_NODO_HIJO;
    }

    public static byte getArbolesTipoNodoPadre()
    {
        return ARBOLES_TIPO_NODO_PADRE;
    }

    public static List getTiposNodos()
    {
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
        List tiposNodos = new ArrayList();
        ArbolesTipoNodo tipoNodo = new ArbolesTipoNodo();
        tipoNodo.setTipoNodoId(ARBOLES_TIPO_NODO_PADRE);
        tipoNodo.nombre = messageResources.getResource("arboles.tiponodo.padre");
        tiposNodos.add(tipoNodo);
        tipoNodo = new ArbolesTipoNodo();
        tipoNodo.setTipoNodoId(ARBOLES_TIPO_NODO_HIJO);
        tipoNodo.nombre = messageResources.getResource("arboles.tiponodo.hijo");
        tiposNodos.add(tipoNodo);
        return tiposNodos;
    }

    public static String getNombre(byte tipoNodo)
    {
        String nombre = "";
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
        if(tipoNodo == ARBOLES_TIPO_NODO_PADRE)
            nombre = messageResources.getResource("arboles.tiponodo.padre");
        else
        if(tipoNodo == ARBOLES_TIPO_NODO_HIJO)
            nombre = messageResources.getResource("arboles.tiponodo.hijo");
        return nombre;
    }

    private static byte ARBOLES_TIPO_NODO_PADRE = 1;
    private static byte ARBOLES_TIPO_NODO_HIJO = 0;
    private byte tipoNodoId;
    private String nombre;

}