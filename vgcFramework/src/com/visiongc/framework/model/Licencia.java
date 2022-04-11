package com.visiongc.framework.model;

import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

// Referenced classes of package com.visiongc.framework.model:
//            Sistema

public class Licencia
    implements Serializable
{

    public Licencia()
    {
        tipo = "";
        expiracion = "";
        serial = "";
        cmaxc = "";
        numeroUsuarios = Integer.valueOf(0);
        numeroIndicadores = Integer.valueOf(0);
        codigoMaquina = "";
        fechaInstalacion = "";
        diasVencimiento = "";
        algoritmo = "";
        fechaLicencia = "";
        expiracionDias = "";
        instalacionMaquinaDiferentes = Boolean.valueOf(false);
        chequearLicencia = Boolean.valueOf(true);
        respuesta = 10000;
        chequearLicenciaBD = Boolean.valueOf(true);
        chequearLicenciaAPP = Boolean.valueOf(true);
        servidores = new ArrayList();
    }

    public String getTipo()
    {
        return tipo;
    }

    public void setTipo(String tipo)
    {
        VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Framework");
        if(tipo.equals(Sistema.SistemaTipo.getSistemaTipoDemo().toString()))
            this.tipo = messageResources.getResource("sistema.producto.demo");
        else
        if(tipo.equals(Sistema.SistemaTipo.getSistemaTipoLimitado().toString()))
            this.tipo = messageResources.getResource("sistema.producto.limitado");
        else
        if(tipo.equals(Sistema.SistemaTipo.getSistemaTipoFull().toString()))
            this.tipo = messageResources.getResource("sistema.producto.full");
        else
            this.tipo = tipo;
    }

    public String getExpiracion()
    {
        return expiracion;
    }

    public void setExpiracion(String expiracion)
    {
        this.expiracion = expiracion;
    }

    public String getSerial()
    {
        return serial;
    }

    public void setSerial(String serial)
    {
        this.serial = serial;
    }

    public String getCmaxc()
    {
        return cmaxc;
    }

    public void setCmaxc(String cmaxc)
    {
        this.cmaxc = cmaxc;
    }

    public Integer getNumeroIndicadores()
    {
        return numeroIndicadores;
    }

    public void setNumeroIndicadores(Integer numeroIndicadores)
    {
        this.numeroIndicadores = numeroIndicadores;
    }

    public Integer getNumeroUsuarios()
    {
        return numeroUsuarios;
    }

    public void setNumeroUsuarios(Integer numeroUsuarios)
    {
        this.numeroUsuarios = numeroUsuarios;
    }

    public String getCodigoMaquina()
    {
        return codigoMaquina;
    }

    public void setCodigoMaquina(String codigoMaquina)
    {
        this.codigoMaquina = codigoMaquina;
    }

    public String getFechaInstalacion()
    {
        return fechaInstalacion;
    }

    public void setFechaInstalacion(String fechaInstalacion)
    {
        this.fechaInstalacion = fechaInstalacion;
    }

    public String getDiasVencimiento()
    {
        return diasVencimiento;
    }

    public void setDiasVencimiento(String diasVencimiento)
    {
        this.diasVencimiento = diasVencimiento;
    }

    public String getAlgoritmo()
    {
        return algoritmo;
    }

    public void setAlgoritmo(String algoritmo)
    {
        this.algoritmo = algoritmo;
    }

    public String getFechaLicencia()
    {
        return fechaLicencia;
    }

    public void setFechaLicencia(String fechaLicencia)
    {
        this.fechaLicencia = fechaLicencia;
    }

    public String getExpiracionDias()
    {
        return expiracionDias;
    }

    public void setExpiracionDias(String expiracionDias)
    {
        this.expiracionDias = expiracionDias;
    }

    public Boolean getInstalacionMaquinaDiferentes()
    {
        return instalacionMaquinaDiferentes;
    }

    public void setInstalacionMaquinaDiferentes(Boolean instalacionMaquinaDiferentes)
    {
        this.instalacionMaquinaDiferentes = instalacionMaquinaDiferentes;
    }

    public Boolean getChequearLicencia()
    {
        return chequearLicencia;
    }

    public void setChequearLicencia(Boolean chequearLicencia)
    {
        this.chequearLicencia = chequearLicencia;
    }

    public int getRespuesta()
    {
        return respuesta;
    }

    public void setRespuesta(int respuesta)
    {
        this.respuesta = respuesta;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getProductoId()
    {
        return productoId;
    }

    public void setProductoId(String productoId)
    {
        this.productoId = productoId;
    }

    public Boolean getFileCheck()
    {
        return fileCheck;
    }

    public void setFileCheck(Boolean fileCheck)
    {
        this.fileCheck = fileCheck;
    }

    public Boolean getChequearLicenciaBD()
    {
        return chequearLicenciaBD;
    }

    public void setChequearLicenciaBD(Boolean chequearLicenciaBD)
    {
        this.chequearLicenciaBD = chequearLicenciaBD;
    }

    public Boolean getChequearLicenciaAPP()
    {
        return chequearLicenciaAPP;
    }

    public void setChequearLicenciaAPP(Boolean chequearLicenciaAPP)
    {
        this.chequearLicenciaAPP = chequearLicenciaAPP;
    }

    public List getServidores()
    {
        return servidores;
    }

    public void setServidores(List servidores)
    {
        this.servidores = servidores;
    }

    public Licencia getLicencia(HttpServletRequest request)
        throws Exception
    {
        Licencia licencia = (Licencia)request.getSession().getAttribute("licencia");
        if(licencia == null)
        {
            FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
            Sistema sistema = frameworkService.getSistema(null);
            request.getSession().setAttribute("licencia", frameworkService.getCheckProducto(sistema, request));
            licencia = (Licencia)request.getSession().getAttribute("licencia");
            frameworkService.close();
        }
        return licencia;
    }

    static final long serialVersionUID = 0L;
    private String tipo;
    private String expiracion;
    private String serial;
    private String cmaxc;
    private Integer numeroUsuarios;
    private Integer numeroIndicadores;
    private String codigoMaquina;
    private String fechaInstalacion;
    private String diasVencimiento;
    private String algoritmo;
    private String fechaLicencia;
    private String expiracionDias;
    private Boolean instalacionMaquinaDiferentes;
    private Boolean chequearLicencia;
    private int respuesta;
    private String companyName;
    private String productoId;
    private Boolean fileCheck;
    private Boolean chequearLicenciaBD;
    private Boolean chequearLicenciaAPP;
    private List servidores;
}