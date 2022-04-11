package com.visiongc.commons.util;

import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.visiongc.commons.util:
//            VgcResourceManager, VgcMessageResources

public class HistoricoType
{

    public HistoricoType()
    {
    }

    public static Byte getFiltroHistoricoType(Byte historicoType)
    {
        if(historicoType.byteValue() == 0)
            return new Byte((byte)0);
        if(historicoType.byteValue() == 1)
            return new Byte((byte)1);
        if(historicoType.byteValue() == 2)
            return new Byte((byte)2);
        else
            return null;
    }

    public static byte getFiltroHistoricoTodos()
    {
        return 0;
    }

    public static byte getFiltroHistoricoNoMarcado()
    {
        return 1;
    }

    public static byte getFiltroHistoricoMarcado()
    {
        return 2;
    }

    public byte getFiltroHistoricoTypeId()
    {
        return filtroHistoricoTypeId;
    }

    public void setFiltroHistoricoTypeId(byte filtroHistoricoTypeId)
    {
        this.filtroHistoricoTypeId = filtroHistoricoTypeId;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public static List getHistoricosTypes()
    {
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
        List tiposHistoricos = new ArrayList();
        HistoricoType tipoHistorico = new HistoricoType();
        tipoHistorico.setFiltroHistoricoTypeId((byte)1);
        tipoHistorico.nombre = messageResources.getResource("filtro.historico.vigente");
        tiposHistoricos.add(tipoHistorico);
        tipoHistorico = new HistoricoType();
        tipoHistorico.setFiltroHistoricoTypeId((byte)2);
        tipoHistorico.nombre = messageResources.getResource("filtro.historico.en.historico");
        tiposHistoricos.add(tipoHistorico);
        return tiposHistoricos;
    }

    public static String getNombre(byte tipoHistorico)
    {
        String nombre = "";
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
        if(tipoHistorico == 1)
            nombre = messageResources.getResource("filtro.historico.vigente");
        else
        if(tipoHistorico == 2)
            nombre = messageResources.getResource("filtro.historico.en.historico");
        return nombre;
    }

    private byte filtroHistoricoTypeId;
    private String nombre;
    private static final byte FILTRO_HISTORICO_TODOS = 0;
    private static final byte FILTRO_HISTORICO_NO_MARCADO = 1;
    private static final byte FILTRO_HISTORICO_MARCADO = 2;
}