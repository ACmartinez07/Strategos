package com.visiongc.framework.auditoria.model;


// Referenced classes of package com.visiongc.framework.auditoria.model:
//            AuditoriaAtributo

public class AuditoriaFlotante extends AuditoriaAtributo
{

    public AuditoriaFlotante()
    {
    }

    public Double getValor()
    {
        return valor;
    }

    public void setValor(Double valor)
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

    public Double getValorAnterior()
    {
        return valorAnterior;
    }

    public void setValorAnterior(Double valorAnterior)
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
    private Double valor;
    private Double valorAnterior;
}