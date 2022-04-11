package com.visiongc.app.strategos.web.struts.taglib;

import com.visiongc.app.strategos.indicadores.model.Medicion;
import com.visiongc.app.strategos.indicadores.model.MedicionPK;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.commons.struts.tag.VgcBaseTag;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.commons.util.VgcResourceManager;
import java.util.Calendar;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import org.apache.struts.taglib.TagUtils;

public class NombrePeriodoMedicionTag extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  protected String name = null;

  protected String scope = null;

  protected String frecuencia = null;

  private Medicion medicion = null;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getScope() {
    return this.scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getFrecuencia() {
    return this.frecuencia;
  }

  public void setFrecuencia(String frecuencia) {
    this.frecuencia = frecuencia;
  }

  public int doStartTag() throws JspException
  {
    if ((this.scope == null) || (this.scope.equals(""))) {
      this.medicion = ((Medicion)this.pageContext.getAttribute(this.name));

      if (this.medicion == null) {
        this.medicion = ((Medicion)this.pageContext.getRequest().getAttribute(this.name));

        if (this.medicion == null) {
          this.medicion = ((Medicion)this.pageContext.getSession().getAttribute(this.name));
        }
      }

    }
    else if (this.scope.equalsIgnoreCase("page")) {
      this.medicion = ((Medicion)this.pageContext.getAttribute(this.name));
    } else if (this.scope.equalsIgnoreCase("request")) {
      this.medicion = ((Medicion)this.pageContext.getRequest().getAttribute(this.name));
    } else {
      this.medicion = ((Medicion)this.pageContext.getSession().getAttribute(this.name));
    }

    if (this.medicion == null) {
      throw new JspException("El objeto medición no se encuentra en ningún scope");
    }

    return 0;
  }

  public int doEndTag() throws JspException
  {
    String resultado = "";

    byte frecuencia = Byte.parseByte(this.frecuencia);

    Integer ano = this.medicion.getMedicionId().getAno();
    Integer periodo = this.medicion.getMedicionId().getPeriodo();

    VgcMessageResources messageResources = VgcResourceManager.getMessageResources("Strategos");

    if (frecuencia == Frecuencia.getFrecuenciaDiaria().byteValue()) {
      Calendar fecha = Calendar.getInstance();

      fecha.set(1, ano.intValue());
      fecha.set(6, periodo.intValue());

      resultado = VgcFormatter.formatearFecha(fecha.getTime(), getMessageResource(null, null, "formato.fecha.corta"));
    } else if (frecuencia == Frecuencia.getFrecuenciaSemanal().byteValue()) {
      resultado = messageResources.getResource("frecuencia.semanal.periodo.nombre") + " " + periodo.toString() + "/" + ano.toString();
    } else if (frecuencia == Frecuencia.getFrecuenciaQuincenal().byteValue()) {
      resultado = messageResources.getResource("frecuencia.quincenal.periodo.nombre") + " " + periodo.toString() + "/" + ano.toString();
    } else if (frecuencia == Frecuencia.getFrecuenciaMensual().byteValue()) {
      if (periodo.byteValue() == 1)
        resultado = messageResources.getResource("mes.enero");
      else if (periodo.byteValue() == 2)
        resultado = messageResources.getResource("mes.febrero");
      else if (periodo.byteValue() == 3)
        resultado = messageResources.getResource("mes.marzo");
      else if (periodo.byteValue() == 4)
        resultado = messageResources.getResource("mes.abril");
      else if (periodo.byteValue() == 5)
        resultado = messageResources.getResource("mes.mayo");
      else if (periodo.byteValue() == 6)
        resultado = messageResources.getResource("mes.junio");
      else if (periodo.byteValue() == 7)
        resultado = messageResources.getResource("mes.julio");
      else if (periodo.byteValue() == 8)
        resultado = messageResources.getResource("mes.agosto");
      else if (periodo.byteValue() == 9)
        resultado = messageResources.getResource("mes.septiembre");
      else if (periodo.byteValue() == 10)
        resultado = messageResources.getResource("mes.octubre");
      else if (periodo.byteValue() == 11)
        resultado = messageResources.getResource("mes.noviembre");
      else if (periodo.byteValue() == 12) {
        resultado = messageResources.getResource("mes.diciembre");
      }
      resultado = resultado + "/" + ano.toString();
    } else if (frecuencia == Frecuencia.getFrecuenciaBimensual().byteValue()) {
      resultado = messageResources.getResource("frecuencia.bimensual.periodo.nombre") + " " + periodo.toString() + "/" + ano.toString();
    } else if (frecuencia == Frecuencia.getFrecuenciaTrimestral().byteValue()) {
      resultado = messageResources.getResource("frecuencia.trimestral.periodo.nombre") + " " + periodo.toString() + "/" + ano.toString();
    } else if (frecuencia == Frecuencia.getFrecuenciaCuatrimestral().byteValue()) {
      resultado = messageResources.getResource("frecuencia.cuatrimestral.periodo.nombre") + " " + periodo.toString() + "/" + ano.toString();
    } else if (frecuencia == Frecuencia.getFrecuenciaSemestral().byteValue()) {
      resultado = messageResources.getResource("frecuencia.semestral.periodo.nombre") + " " + periodo.toString() + "/" + ano.toString();
    } else if (frecuencia == Frecuencia.getFrecuenciaAnual().byteValue()) {
      resultado = messageResources.getResource("frecuencia.anual.periodo.nombre") + " " + ano.toString();
    }

    TagUtils.getInstance().write(this.pageContext, resultado);

    this.medicion = null;
    this.scope = null;
    this.name = null;

    return 6;
  }

  public void release() {
    super.release();
    this.name = null;
    this.scope = null;
  }
}