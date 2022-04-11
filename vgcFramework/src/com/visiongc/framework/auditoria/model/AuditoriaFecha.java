package com.visiongc.framework.auditoria.model;

import java.util.Date;

// Referenced classes of package com.visiongc.framework.auditoria.model:
//            AuditoriaAtributo

public class AuditoriaFecha extends AuditoriaAtributo
{

    public AuditoriaFecha()
    {
    }

    public Date getValor()
    {
        return valor;
    }

    public void setValor(Date valor)
    {
        this.valor = valor;
    }

    public String getValorString()
    {
        String valorString = null;
        if(valor != null)
            valorString = valor.toString();
        return valorString;
    }

    public String getValorStringCorto()
    {
        String valorString = getValorString();
        if(valorString != null && valorString.length() > 50)
            valorString = valorString.substring(0, 50);
        return valorString;
    }

    public Date getValorAnterior()
    {
        return valorAnterior;
    }

    public void setValorAnterior(Date valorAnterior)
    {
        this.valorAnterior = valorAnterior;
    }

    public String getValorAnteriorString()
    {
        String valorString = null;
        if(valorAnterior != null)
            valorString = valorAnterior.toString();
        return valorString;
    }

    public String getValorAnteriorStringCorto()
    {
        String valorString = getValorAnteriorString();
        if(valorString != null && valorString.length() > 50)
            valorString = valorString.substring(0, 50);
        return valorString;
    }

    static final long serialVersionUID = 0L;
    private Date valor;
    private Date valorAnterior;
}