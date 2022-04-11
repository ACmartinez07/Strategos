package com.visiongc.framework.auditoria.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaTipoEvento
{

    public AuditoriaTipoEvento()
    {
    }

    public byte getTipoEventoId()
    {
        return tipoEventoId;
    }

    public void setTipoEventoId(byte tipoEventoId)
    {
        this.tipoEventoId = tipoEventoId;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public static byte getAuditoriaTipoEventoInsert()
    {
        return AUDITORIA_TIPO_EVENTO_INSERT;
    }

    public static byte getAuditoriaTipoEventoUpdate()
    {
        return AUDITORIA_TIPO_EVENTO_UPDATE;
    }

    public static byte getAuditoriaTipoEventoDelete()
    {
        return AUDITORIA_TIPO_EVENTO_DELETE;
    }

    public static byte getAuditoriaTipoEventoLogin()
    {
        return AUDITORIA_TIPO_EVENTO_LOGIN;
    }

    public static byte getAuditoriaTipoEventoLogout()
    {
        return AUDITORIA_TIPO_EVENTO_LOGOUT;
    }

    public static byte getAuditoriaTipoEventoCalculo()
    {
        return AUDITORIA_TIPO_EVENTO_CALCULO;
    }

    public static byte getAuditoriaTipoEventoImportar()
    {
        return AUDITORIA_TIPO_EVENTO_IMPORTAR;
    }
    
    public static byte getAuditoriaTipoEventoProtegerLiberar()
    {
        return AUDITORIA_TIPO_EVENTO_PROTEGERLIBERAR;
    }

    public static List getTiposEventos()
    {
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
        List tiposEventos = new ArrayList();
        AuditoriaTipoEvento tipoEvento = new AuditoriaTipoEvento();
        tipoEvento.setTipoEventoId(AUDITORIA_TIPO_EVENTO_INSERT);
        tipoEvento.nombre = messageResources.getResource("auditoria.tipoevento.insert");
        tiposEventos.add(tipoEvento);
        tipoEvento = new AuditoriaTipoEvento();
        tipoEvento.setTipoEventoId(AUDITORIA_TIPO_EVENTO_UPDATE);
        tipoEvento.nombre = messageResources.getResource("auditoria.tipoevento.update");
        tiposEventos.add(tipoEvento);
        tipoEvento = new AuditoriaTipoEvento();
        tipoEvento.setTipoEventoId(AUDITORIA_TIPO_EVENTO_DELETE);
        tipoEvento.nombre = messageResources.getResource("auditoria.tipoevento.delete");
        tiposEventos.add(tipoEvento);
        tipoEvento = new AuditoriaTipoEvento();
        tipoEvento.setTipoEventoId(AUDITORIA_TIPO_EVENTO_LOGIN);
        tipoEvento.nombre = messageResources.getResource("auditoria.tipoevento.login");
        tiposEventos.add(tipoEvento);
        tipoEvento = new AuditoriaTipoEvento();
        tipoEvento.setTipoEventoId(AUDITORIA_TIPO_EVENTO_LOGOUT);
        tipoEvento.nombre = messageResources.getResource("auditoria.tipoevento.logout");
        tiposEventos.add(tipoEvento);
        tipoEvento = new AuditoriaTipoEvento();
        tipoEvento.setTipoEventoId(AUDITORIA_TIPO_EVENTO_CALCULO);
        tipoEvento.nombre = messageResources.getResource("auditoria.tipoevento.calculo");
        tiposEventos.add(tipoEvento);
        tipoEvento = new AuditoriaTipoEvento();
        tipoEvento.setTipoEventoId(AUDITORIA_TIPO_EVENTO_IMPORTAR);
        tipoEvento.nombre = messageResources.getResource("auditoria.tipoevento.importar");
        tiposEventos.add(tipoEvento);
        tipoEvento.setTipoEventoId(AUDITORIA_TIPO_EVENTO_PROTEGERLIBERAR);
        tipoEvento.nombre = messageResources.getResource("auditoria.tipoevento.protegerliberar");
        tiposEventos.add(tipoEvento);
        return tiposEventos;
    }

    public static String getNombre(byte tipoEvento)
    {
        String nombre = "";
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
        if(tipoEvento == AUDITORIA_TIPO_EVENTO_INSERT)
            nombre = messageResources.getResource("auditoria.tipoevento.insert");
        else
        if(tipoEvento == AUDITORIA_TIPO_EVENTO_UPDATE)
            nombre = messageResources.getResource("auditoria.tipoevento.update");
        else
        if(tipoEvento == AUDITORIA_TIPO_EVENTO_DELETE)
            nombre = messageResources.getResource("auditoria.tipoevento.delete");
        else
        if(tipoEvento == AUDITORIA_TIPO_EVENTO_LOGIN)
            nombre = messageResources.getResource("auditoria.tipoevento.login");
        else
        if(tipoEvento == AUDITORIA_TIPO_EVENTO_LOGOUT)
            nombre = messageResources.getResource("auditoria.tipoevento.logout");
        else
        if(tipoEvento == AUDITORIA_TIPO_EVENTO_CALCULO)
            nombre = messageResources.getResource("auditoria.tipoevento.calculo");
        else
        if(tipoEvento == AUDITORIA_TIPO_EVENTO_IMPORTAR)
            nombre = messageResources.getResource("auditoria.tipoevento.importar");
        if(tipoEvento == AUDITORIA_TIPO_EVENTO_PROTEGERLIBERAR)
            nombre = messageResources.getResource("auditoria.tipoevento.protegerliberar");
        return nombre;
    }

    private static byte AUDITORIA_TIPO_EVENTO_INSERT = 1;
    private static byte AUDITORIA_TIPO_EVENTO_UPDATE = 2;
    private static byte AUDITORIA_TIPO_EVENTO_DELETE = 3;
    private static byte AUDITORIA_TIPO_EVENTO_LOGIN = 4;
    private static byte AUDITORIA_TIPO_EVENTO_LOGOUT = 5;
    private static byte AUDITORIA_TIPO_EVENTO_CALCULO = 6;
    private static byte AUDITORIA_TIPO_EVENTO_IMPORTAR = 7;
    private static byte AUDITORIA_TIPO_EVENTO_PROTEGERLIBERAR = 8;
    private byte tipoEventoId;
    private String nombre;

}