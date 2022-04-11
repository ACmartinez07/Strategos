package com.visiongc.framework.auditoria.persistence.hibernate.util;

import com.visiongc.framework.auditoria.model.AuditoriaAtributo;
import com.visiongc.framework.auditoria.model.AuditoriaAtributoPK;
import java.util.Comparator;
import java.util.Date;

public class ComparatorAuditoriaAtributo
    implements Comparator
{

    public ComparatorAuditoriaAtributo(String atributoComparacion, String tipoOrden)
    {
        this.atributoComparacion = atributoComparacion;
        if(tipoOrden != null && tipoOrden.equalsIgnoreCase("desc"))
            ordenAscendente = false;
        else
            ordenAscendente = true;
    }

    public int compare(Object objeto1, Object objeto2)
    {
        int resultado = 0;
        AuditoriaAtributo at1 = (AuditoriaAtributo)objeto1;
        AuditoriaAtributo at2 = (AuditoriaAtributo)objeto2;
        if(atributoComparacion == null || atributoComparacion.equals("pk.fecha"))
            resultado = at1.getPk().getFecha().compareTo(at2.getPk().getFecha());
        else
        if(atributoComparacion.equals("pk.instanciaId"))
            resultado = at1.getPk().getInstanciaId().compareTo(at2.getPk().getInstanciaId());
        else
        if(atributoComparacion.equals("pk.nombreAtributo"))
            resultado = at1.getPk().getNombreAtributo().compareTo(at2.getPk().getNombreAtributo());
        else
        if(atributoComparacion.equals("objetoId"))
            resultado = at1.getObjetoId().compareTo(at2.getObjetoId());
        else
        if(atributoComparacion.equals("tipoEvento"))
            resultado = at1.getTipoEvento().compareTo(at2.getTipoEvento());
        else
        if(atributoComparacion.equals("usuarioId"))
            resultado = at1.getUsuarioId().compareTo(at2.getUsuarioId());
        if(resultado == 0 && !at1.equals(at2))
            resultado = 1;
        if(!ordenAscendente)
            resultado *= -1;
        return resultado;
    }

    private String atributoComparacion;
    private boolean ordenAscendente;
}