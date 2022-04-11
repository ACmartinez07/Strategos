package com.visiongc.framework.auditoria.model.util;


public class AuditoriaTipoAtributo
{

    public AuditoriaTipoAtributo()
    {
    }

    public static byte getAuditoriaTipoAtributoString()
    {
        return AUDITORIA_TIPO_ATRIBUTO_STRING;
    }

    public static byte getAuditoriaTipoAtributoMemo()
    {
        return AUDITORIA_TIPO_ATRIBUTO_MEMO;
    }

    public static byte getAuditoriaTipoAtributoFecha()
    {
        return AUDITORIA_TIPO_ATRIBUTO_FECHA;
    }

    public static byte getAuditoriaTipoAtributoEntero()
    {
        return AUDITORIA_TIPO_ATRIBUTO_ENTERO;
    }

    public static byte getAuditoriaTipoAtributoFlotante()
    {
        return AUDITORIA_TIPO_ATRIBUTO_FLOTANTE;
    }

    private static byte AUDITORIA_TIPO_ATRIBUTO_STRING = 1;
    private static byte AUDITORIA_TIPO_ATRIBUTO_MEMO = 2;
    private static byte AUDITORIA_TIPO_ATRIBUTO_FECHA = 3;
    private static byte AUDITORIA_TIPO_ATRIBUTO_ENTERO = 4;
    private static byte AUDITORIA_TIPO_ATRIBUTO_FLOTANTE = 5;

}