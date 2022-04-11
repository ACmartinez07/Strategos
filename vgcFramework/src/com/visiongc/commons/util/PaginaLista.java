package com.visiongc.commons.util;

import java.util.*;

// Referenced classes of package com.visiongc.commons.util:
//            StringUtil

public class PaginaLista
{

    public PaginaLista()
    {
        total = 0;
        nroPagina = 0;
        tamanoPagina = 0;
        lista = null;
        tamanoSetPaginas = 0;
        total = 0;
        nroPagina = 0;
        lista = null;
        orden = null;
        tipoOrden = null;
        filtros = new HashMap();
    }

    public void setTotal(int inTotal)
    {
        total = inTotal;
    }

    public int getTotal()
    {
        return total;
    }

    public void setNroPagina(int inNroPagina)
    {
        nroPagina = inNroPagina;
    }

    public int getNroPagina()
    {
        return nroPagina;
    }

    public void setTamanoPagina(int inTamanoPagina)
    {
        tamanoPagina = inTamanoPagina;
    }

    public int getTamanoPagina()
    {
        return tamanoPagina;
    }

    public void setTamanoSetPaginas(int inTamanoSetPaginas)
    {
        tamanoSetPaginas = inTamanoSetPaginas;
    }

    public int getTamanoSetPaginas()
    {
        return tamanoSetPaginas;
    }

    public void setLista(List inLista)
    {
        lista = inLista;
    }

    public List getLista()
    {
        return lista;
    }

    public String getOrden()
    {
        return orden;
    }

    public void setOrden(String orden)
    {
        this.orden = orden;
    }

    public String getTipoOrden()
    {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden)
    {
        this.tipoOrden = tipoOrden;
    }

    public String getInfoOrden()
    {
        return infoOrden;
    }

    public void setInfoOrden(String infoOrden)
    {
        this.infoOrden = infoOrden;
    }

    public Map getFiltros()
    {
        return filtros;
    }

    public void setFiltros(Map filtros)
    {
        this.filtros = filtros;
    }

    public Boolean samePage(Integer pagina, Integer tamanoPagina, String orden, String tipoOrden, Map filtrosActual)
    {
        Boolean samePagina = Boolean.valueOf(true);
        Iterator iter = filtrosActual.keySet().iterator();
        String fieldName = null;
        String fieldValue = null;
        while(iter.hasNext()) 
        {
            fieldName = (String)iter.next();
            if(filtrosActual.get(fieldName) == null)
                fieldValue = null;
            else
            if(filtrosActual.get(fieldName) instanceof String)
                fieldValue = (String)filtrosActual.get(fieldName);
            else
                fieldValue = (new StringUtil()).getValorCondicionConsulta(filtrosActual.get(fieldName));
            boolean registroEncontrado = false;
            Iterator iterFiltro = filtros.keySet().iterator();
            String fieldNameFiltro = null;
            String fieldValueFiltro = null;
            while(iterFiltro.hasNext()) 
            {
                fieldNameFiltro = (String)iterFiltro.next();
                if(filtros.get(fieldNameFiltro) == null)
                    fieldValueFiltro = null;
                else
                if(filtros.get(fieldNameFiltro) instanceof String)
                    fieldValueFiltro = (String)filtros.get(fieldNameFiltro);
                else
                    fieldValueFiltro = (new StringUtil()).getValorCondicionConsulta(filtros.get(fieldNameFiltro));
                if(fieldNameFiltro.equals(fieldName) && fieldValueFiltro.equals(fieldValue))
                {
                    registroEncontrado = true;
                    break;
                }
            }
            if(!registroEncontrado)
            {
                samePagina = Boolean.valueOf(false);
                break;
            }
        }
        if(nroPagina != pagina.intValue() || this.tamanoPagina != tamanoPagina.intValue() || !this.orden.equals(orden) || !this.tipoOrden.equals(tipoOrden))
            samePagina = Boolean.valueOf(false);
        return samePagina;
    }

    private int total;
    private int nroPagina;
    private int tamanoPagina;
    private List lista;
    private int tamanoSetPaginas;
    private String orden;
    private String tipoOrden;
    private Map filtros;
    private String infoOrden;
}