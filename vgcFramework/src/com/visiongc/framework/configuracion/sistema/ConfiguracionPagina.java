package com.visiongc.framework.configuracion.sistema;

import com.lowagie.text.Font;
import com.visiongc.commons.util.xmldata.*;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ConfiguracionUsuario;
import com.visiongc.framework.model.ObjetoBinario;

public class ConfiguracionPagina
{

    public ConfiguracionPagina()
    {
        tamanoMargenSuperior = new Double(1.27D);
        tamanoMargenInferior = new Double(1.27D);
        tamanoMargenIzquierdo = new Double(1.27D);
        tamanoMargenDerecho = new Double(1.27D);
        encabezadoIzquierdo = null;
        encabezadoCentro = null;
        encabezadoDerecho = null;
        piePaginaIzquierdo = null;
        piePaginaCentro = null;
        piePaginaDerecho = null;
        nombreFuente = "Times-Roman";
        estiloFuente = "Normal";
        tamanoFuente = new Integer(11);
    }

    public String getEncabezadoCentro()
    {
        return encabezadoCentro;
    }

    public void setEncabezadoCentro(String encabezadoCentro)
    {
        this.encabezadoCentro = encabezadoCentro;
    }

    public String getEncabezadoDerecho()
    {
        return encabezadoDerecho;
    }

    public void setEncabezadoDerecho(String encabezadoDerecho)
    {
        this.encabezadoDerecho = encabezadoDerecho;
    }

    public String getEncabezadoIzquierdo()
    {
        return encabezadoIzquierdo;
    }

    public void setEncabezadoIzquierdo(String encabezadoIzquierdo)
    {
        this.encabezadoIzquierdo = encabezadoIzquierdo;
    }

    public String getEstiloFuente()
    {
        return estiloFuente;
    }

    public void setEstiloFuente(String estiloFuente)
    {
        this.estiloFuente = estiloFuente;
    }

    public Font getFuente()
    {
        Font fuente = new Font(getCodigoFuente());
        fuente.setSize(getTamanoFuente().floatValue());
        if(getEstiloFuente().equals("Normal"))
            fuente.setStyle(0);
        else
        if(getEstiloFuente().equals("Cursiva"))
            fuente.setStyle(2);
        else
        if(getEstiloFuente().equals("Negrita"))
            fuente.setStyle(1);
        else
        if(getEstiloFuente().equals("Negrita Cursiva"))
            fuente.setStyle(3);
        return fuente;
    }

    public int getCodigoFuente()
    {
        int codigo = 0;
        if(nombreFuente.equalsIgnoreCase("Times-Roman"))
            codigo = 2;
        else
        if(nombreFuente.equalsIgnoreCase("Courier"))
            codigo = 0;
        else
        if(nombreFuente.equalsIgnoreCase("Helvetica"))
            codigo = 1;
        return codigo;
    }

    public String getNombreFuente()
    {
        return nombreFuente;
    }

    public void setNombreFuente(String nombreFuente)
    {
        this.nombreFuente = nombreFuente;
    }

    public String getPiePaginaCentro()
    {
        return piePaginaCentro;
    }

    public void setPiePaginaCentro(String piePaginaCentro)
    {
        this.piePaginaCentro = piePaginaCentro;
    }

    public String getPiePaginaDerecho()
    {
        return piePaginaDerecho;
    }

    public void setPiePaginaDerecho(String piePaginaDerecho)
    {
        this.piePaginaDerecho = piePaginaDerecho;
    }

    public String getPiePaginaIzquierdo()
    {
        return piePaginaIzquierdo;
    }

    public void setPiePaginaIzquierdo(String piePaginaIzquierdo)
    {
        this.piePaginaIzquierdo = piePaginaIzquierdo;
    }

    public Integer getTamanoFuente()
    {
        return tamanoFuente;
    }

    public void setTamanoFuente(Integer tamanoFuente)
    {
        this.tamanoFuente = tamanoFuente;
    }

    public Double getTamanoMargenDerecho()
    {
        return tamanoMargenDerecho;
    }

    public void setTamanoMargenDerecho(Double tamanoMargenDerecho)
    {
        this.tamanoMargenDerecho = tamanoMargenDerecho;
    }

    public Double getTamanoMargenInferior()
    {
        return tamanoMargenInferior;
    }

    public void setTamanoMargenInferior(Double tamanoMargenInferior)
    {
        this.tamanoMargenInferior = tamanoMargenInferior;
    }

    public Double getTamanoMargenIzquierdo()
    {
        return tamanoMargenIzquierdo;
    }

    public void setTamanoMargenIzquierdo(Double tamanoMargenIzquierdo)
    {
        this.tamanoMargenIzquierdo = tamanoMargenIzquierdo;
    }

    public Double getTamanoMargenSuperior()
    {
        return tamanoMargenSuperior;
    }

    public void setTamanoMargenSuperior(Double tamanoMargenSuperior)
    {
        this.tamanoMargenSuperior = tamanoMargenSuperior;
    }

    public ObjetoBinario getImagenInfCen()
    {
        return imagenInfCen;
    }

    public void setImagenInfCen(ObjetoBinario imagenInfCen)
    {
        this.imagenInfCen = imagenInfCen;
    }

    public Long getImagenInfCenId()
    {
        return imagenInfCenId;
    }

    public void setImagenInfCenId(Long imagenInfCenId)
    {
        this.imagenInfCenId = imagenInfCenId;
    }

    public ObjetoBinario getImagenInfDer()
    {
        return imagenInfDer;
    }

    public void setImagenInfDer(ObjetoBinario imagenInfDer)
    {
        this.imagenInfDer = imagenInfDer;
    }

    public Long getImagenInfDerId()
    {
        return imagenInfDerId;
    }

    public void setImagenInfDerId(Long imagenInfDerId)
    {
        this.imagenInfDerId = imagenInfDerId;
    }

    public ObjetoBinario getImagenInfIzq()
    {
        return imagenInfIzq;
    }

    public void setImagenInfIzq(ObjetoBinario imagenInfIzq)
    {
        this.imagenInfIzq = imagenInfIzq;
    }

    public Long getImagenInfIzqId()
    {
        return imagenInfIzqId;
    }

    public void setImagenInfIzqId(Long imagenInfIzqId)
    {
        this.imagenInfIzqId = imagenInfIzqId;
    }

    public ObjetoBinario getImagenSupCen()
    {
        return imagenSupCen;
    }

    public void setImagenSupCen(ObjetoBinario imagenSupCen)
    {
        this.imagenSupCen = imagenSupCen;
    }

    public Long getImagenSupCenId()
    {
        return imagenSupCenId;
    }

    public void setImagenSupCenId(Long imagenSupCenId)
    {
        this.imagenSupCenId = imagenSupCenId;
    }

    public ObjetoBinario getImagenSupDer()
    {
        return imagenSupDer;
    }

    public void setImagenSupDer(ObjetoBinario imagenSupDer)
    {
        this.imagenSupDer = imagenSupDer;
    }

    public Long getImagenSupDerId()
    {
        return imagenSupDerId;
    }

    public void setImagenSupDerId(Long imagenSupDerId)
    {
        this.imagenSupDerId = imagenSupDerId;
    }

    public ObjetoBinario getImagenSupIzq()
    {
        return imagenSupIzq;
    }

    public void setImagenSupIzq(ObjetoBinario imagenSupIzq)
    {
        this.imagenSupIzq = imagenSupIzq;
    }

    public Long getImagenSupIzqId()
    {
        return imagenSupIzqId;
    }

    public void setImagenSupIzqId(Long imagenSupIzqId)
    {
        this.imagenSupIzqId = imagenSupIzqId;
    }

    public ConfiguracionUsuario getConfiguracionUsuario()
    {
        return configuracionUsuario;
    }

    public void setConfiguracionUsuario(ConfiguracionUsuario configuracionUsuario)
    {
        this.configuracionUsuario = configuracionUsuario;
    }

    public String getNombreConfiguracionBase()
    {
        return nombreConfiguracionBase;
    }

    public void setNombreConfiguracionBase(String nombreConfiguracionBase)
    {
        this.nombreConfiguracionBase = nombreConfiguracionBase;
    }

    public float getAjustePorTextoEncabezado()
    {
        return ajustePorTextoEncabezado;
    }

    public void setAjustePorTextoEncabezado(float ajustePorTextoEncabezado)
    {
        this.ajustePorTextoEncabezado = ajustePorTextoEncabezado;
    }

    public float getAjustePorTextoPiePagina()
    {
        return ajustePorTextoPiePagina;
    }

    public void setAjustePorTextoPiePagina(float ajustePorTextoPiePagina)
    {
        this.ajustePorTextoPiePagina = ajustePorTextoPiePagina;
    }

    public String getXml()
    {
        XmlNodo xmlConfiguracionPagina = new XmlNodo();
        XmlAtributo atributo = new XmlAtributo();
        atributo.setNombre("tamanoMargenSuperior");
        atributo.setValor(getTamanoMargenSuperior().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("tamanoMargenInferior");
        atributo.setValor(getTamanoMargenInferior().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("tamanoMargenIzquierdo");
        atributo.setValor(getTamanoMargenIzquierdo().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("tamanoMargenDerecho");
        atributo.setValor(getTamanoMargenDerecho().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("encabezadoIzquierdo");
        atributo.setValor(getEncabezadoIzquierdo());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("encabezadoCentro");
        atributo.setValor(getEncabezadoCentro());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("encabezadoDerecho");
        atributo.setValor(getEncabezadoDerecho());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("piePaginaIzquierdo");
        atributo.setValor(getPiePaginaIzquierdo());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("piePaginaCentro");
        atributo.setValor(getPiePaginaCentro());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("piePaginaDerecho");
        atributo.setValor(getPiePaginaDerecho());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("nombreFuente");
        atributo.setValor(getNombreFuente());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("estiloFuente");
        atributo.setValor(getEstiloFuente());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("tamanoFuente");
        atributo.setValor(getTamanoFuente().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("imagenSupIzqId");
        if(getImagenSupIzqId() != null)
            atributo.setValor(getImagenSupIzqId().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("imagenSupCenId");
        if(getImagenSupCenId() != null)
            atributo.setValor(getImagenSupCenId().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("imagenSupDerId");
        if(getImagenSupDerId() != null)
            atributo.setValor(getImagenSupDerId().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("imagenInfIzqId");
        if(getImagenInfIzqId() != null)
            atributo.setValor(getImagenInfIzqId().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("imagenInfCenId");
        if(getImagenInfCenId() != null)
            atributo.setValor(getImagenInfCenId().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        atributo = new XmlAtributo();
        atributo.setNombre("imagenInfDerId");
        if(getImagenInfDerId() != null)
            atributo.setValor(getImagenInfDerId().toString());
        xmlConfiguracionPagina.addAtributo(atributo);
        return XmlUtil.buildXml(xmlConfiguracionPagina);
    }

    public void readFromXml(String xml)
    {
        if(xml == null || xml.equals(""))
            return;
        XmlNodo xmlConfiguracionPagina = XmlUtil.readXml(xml);
        setTamanoMargenSuperior(new Double(xmlConfiguracionPagina.getValorAtributo("tamanoMargenSuperior")));
        setTamanoMargenInferior(new Double(xmlConfiguracionPagina.getValorAtributo("tamanoMargenInferior")));
        setTamanoMargenIzquierdo(new Double(xmlConfiguracionPagina.getValorAtributo("tamanoMargenIzquierdo")));
        setTamanoMargenDerecho(new Double(xmlConfiguracionPagina.getValorAtributo("tamanoMargenDerecho")));
        setEncabezadoIzquierdo(xmlConfiguracionPagina.getValorAtributo("encabezadoIzquierdo"));
        setEncabezadoCentro(xmlConfiguracionPagina.getValorAtributo("encabezadoCentro"));
        setEncabezadoDerecho(xmlConfiguracionPagina.getValorAtributo("encabezadoDerecho"));
        setPiePaginaIzquierdo(xmlConfiguracionPagina.getValorAtributo("piePaginaIzquierdo"));
        setPiePaginaCentro(xmlConfiguracionPagina.getValorAtributo("piePaginaCentro"));
        setPiePaginaDerecho(xmlConfiguracionPagina.getValorAtributo("piePaginaDerecho"));
        setNombreFuente(xmlConfiguracionPagina.getValorAtributo("nombreFuente"));
        setEstiloFuente(xmlConfiguracionPagina.getValorAtributo("estiloFuente"));
        setTamanoFuente(new Integer(xmlConfiguracionPagina.getValorAtributo("tamanoFuente")));
        if(xmlConfiguracionPagina.getValorAtributo("imagenSupIzqId") != null)
            setImagenSupIzqId(new Long(xmlConfiguracionPagina.getValorAtributo("imagenSupIzqId")));
        else
            setImagenSupIzqId(null);
        if(xmlConfiguracionPagina.getValorAtributo("imagenSupCenId") != null)
            setImagenSupCenId(new Long(xmlConfiguracionPagina.getValorAtributo("imagenSupCenId")));
        else
            setImagenSupCenId(null);
        if(xmlConfiguracionPagina.getValorAtributo("imagenSupDerId") != null)
            setImagenSupDerId(new Long(xmlConfiguracionPagina.getValorAtributo("imagenSupDerId")));
        else
            setImagenSupDerId(null);
        if(xmlConfiguracionPagina.getValorAtributo("imagenInfIzqId") != null)
            setImagenInfIzqId(new Long(xmlConfiguracionPagina.getValorAtributo("imagenInfIzqId")));
        else
            setImagenInfIzqId(null);
        if(xmlConfiguracionPagina.getValorAtributo("imagenInfCenId") != null)
            setImagenInfCenId(new Long(xmlConfiguracionPagina.getValorAtributo("imagenInfCenId")));
        else
            setImagenInfCenId(null);
        if(xmlConfiguracionPagina.getValorAtributo("imagenInfDerId") != null)
            setImagenInfDerId(new Long(xmlConfiguracionPagina.getValorAtributo("imagenInfDerId")));
        else
            setImagenInfDerId(null);
    }

    public void loadImagenes(boolean getDataBinaria)
    {
        FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
        if(imagenSupIzqId != null)
        {
            Object imagen = frameworkService.leerImagen(getImagenSupIzqId());
            if(imagen != null)
            {
                setImagenSupIzq((ObjetoBinario)imagen);
                if(getDataBinaria)
                    getImagenSupIzq().getDataBlob();
            }
        }
        if(imagenSupCenId != null)
        {
            Object imagen = frameworkService.leerImagen(getImagenSupCenId());
            if(imagen != null)
            {
                setImagenSupCen((ObjetoBinario)imagen);
                if(getDataBinaria)
                    getImagenSupCen().getDataBlob();
            }
        }
        if(imagenSupDerId != null)
        {
            Object imagen = frameworkService.leerImagen(getImagenSupDerId());
            if(imagen != null)
            {
                setImagenSupDer((ObjetoBinario)imagen);
                if(getDataBinaria)
                    getImagenSupDer().getDataBlob();
            }
        }
        if(imagenInfIzqId != null)
        {
            Object imagen = frameworkService.load(ObjetoBinario.class, getImagenInfIzqId());
            if(imagen != null)
            {
                setImagenInfIzq((ObjetoBinario)imagen);
                if(getDataBinaria)
                    getImagenInfIzq().getDataBlob();
            }
        }
        if(imagenInfCenId != null)
        {
            Object imagen = frameworkService.load(ObjetoBinario.class, getImagenInfCenId());
            if(imagen != null)
            {
                setImagenInfCen((ObjetoBinario)imagen);
                if(getDataBinaria)
                    getImagenInfCen().getDataBlob();
            }
        }
        if(imagenInfDerId != null)
        {
            Object imagen = frameworkService.load(ObjetoBinario.class, getImagenInfDerId());
            if(imagen != null)
            {
                setImagenInfDer((ObjetoBinario)imagen);
                if(getDataBinaria)
                    getImagenInfDer().getDataBlob();
            }
        }
        frameworkService.close();
    }

    public boolean tieneEncabezado()
    {
        boolean tieneEncabezado = false;
        if(getEncabezadoIzquierdo() != null && !getEncabezadoIzquierdo().equals(""))
            tieneEncabezado = true;
        if(getEncabezadoCentro() != null && !getEncabezadoCentro().equals(""))
            tieneEncabezado = true;
        if(getEncabezadoDerecho() != null && !getEncabezadoDerecho().equals(""))
            tieneEncabezado = true;
        return tieneEncabezado;
    }

    public boolean tienePiePagina()
    {
        boolean tienePiePagina = false;
        if(getPiePaginaIzquierdo() != null && !getPiePaginaIzquierdo().equals(""))
            tienePiePagina = true;
        if(getPiePaginaCentro() != null && !getPiePaginaCentro().equals(""))
            tienePiePagina = true;
        if(getPiePaginaDerecho() != null && !getPiePaginaDerecho().equals(""))
            tienePiePagina = true;
        return tienePiePagina;
    }

    private Double tamanoMargenSuperior;
    private Double tamanoMargenInferior;
    private Double tamanoMargenIzquierdo;
    private Double tamanoMargenDerecho;
    private String encabezadoIzquierdo;
    private String encabezadoCentro;
    private String encabezadoDerecho;
    private String piePaginaIzquierdo;
    private String piePaginaCentro;
    private String piePaginaDerecho;
    private String nombreFuente;
    private String estiloFuente;
    private Integer tamanoFuente;
    private Long imagenSupIzqId;
    private Long imagenSupCenId;
    private Long imagenSupDerId;
    private Long imagenInfIzqId;
    private Long imagenInfCenId;
    private Long imagenInfDerId;
    private ObjetoBinario imagenSupIzq;
    private ObjetoBinario imagenSupCen;
    private ObjetoBinario imagenSupDer;
    private ObjetoBinario imagenInfIzq;
    private ObjetoBinario imagenInfCen;
    private ObjetoBinario imagenInfDer;
    private ConfiguracionUsuario configuracionUsuario;
    private String nombreConfiguracionBase;
    private float ajustePorTextoEncabezado;
    private float ajustePorTextoPiePagina;
    public static final String MACRO_PAGINA = "&P";
    public static final String MACRO_FECHA_FORMATO_CORTO = "&D";
    public static final String MACRO_FECHA_FORMATO_LARGO = "&L";
    public static final String MACRO_HORA = "&T";
    public static final String MACRO_ANO = "&Y";
    public static final String MACRO_MES = "&M";
    public static final String MACRO_IMAGEN1 = "&I1";
    public static final String MACRO_IMAGEN2 = "&I2";
    public static final String MACRO_IMAGEN3 = "&I3";
    public static final String MACRO_IMAGEN4 = "&I4";
    public static final String MACRO_IMAGEN5 = "&I5";
    public static final String MACRO_IMAGEN6 = "&I6";
    public static final String FUENTE_COURIER = "Courier";
    public static final String FUENTE_HELVETICA = "Helvetica";
    public static final String FUENTE_TIMES_ROMAN = "Times-Roman";
    public static final String ESTILO_NORMAL = "Normal";
    public static final String ESTILO_CURSIVA = "Cursiva";
    public static final String ESTILO_NEGRITA = "Negrita";
    public static final String ESTILO_NEGRITA_CURSIVA = "Negrita Cursiva";
}