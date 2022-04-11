package com.visiongc.framework.auditoria.model;


// Referenced classes of package com.visiongc.framework.auditoria.model:
//            AuditoriaAtributo

public class AuditoriaString extends AuditoriaAtributo
{

    public AuditoriaString()
    {
    }

    public String getValor()
    {
        return valor;
    }

    public void setValor(String valor)
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

    public String getValorAnterior()
    {
        return valorAnterior;
    }

    public void setValorAnterior(String valorAnterior)
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
    private String valor;
    private String valorAnterior;
}