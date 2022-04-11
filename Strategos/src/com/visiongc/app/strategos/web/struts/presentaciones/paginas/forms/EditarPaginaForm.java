package com.visiongc.app.strategos.web.struts.presentaciones.paginas.forms;

import com.visiongc.framework.web.struts.forms.EditarObjetoForm;
import java.util.Set;

public class EditarPaginaForm extends EditarObjetoForm
{
  static final long serialVersionUID = 0L;
  private Long paginaId;
  private Long vistaId;
  private String Descripcion;
  private Byte filas;
  private Byte columnas;
  private Integer alto;
  private Integer ancho;
  private Set paginas;
  private String nombreOrganizacion;
  private String nombreVista;
  private Integer numero;

  public Long getPaginaId()
  {
    return this.paginaId;
  }

  public void setPaginaId(Long paginaId) {
    this.paginaId = paginaId;
  }

  public Long getVistaId() {
    return this.vistaId;
  }

  public void setVistaId(Long vistaId) {
    this.vistaId = vistaId;
  }

  public String getDescripcion() {
    return this.Descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.Descripcion = descripcion;
  }

  public Byte getFilas() {
    return this.filas;
  }

  public void setFilas(Byte filas) {
    this.filas = filas;
  }

  public Byte getColumnas() {
    return this.columnas;
  }

  public void setColumnas(Byte columnas) {
    this.columnas = columnas;
  }

  public Integer getAlto() {
    return this.alto;
  }

  public void setAlto(Integer alto) {
    this.alto = alto;
  }

  public Integer getAncho() {
    return this.ancho;
  }

  public void setAncho(Integer ancho) {
    this.ancho = ancho;
  }

  public Set getPaginas() {
    return this.paginas;
  }

  public void setPaginas(Set paginas) {
    this.paginas = paginas;
  }

  public String getNombreOrganizacion() {
    return this.nombreOrganizacion;
  }

  public void setNombreOrganizacion(String nombreOrganizacion) {
    this.nombreOrganizacion = nombreOrganizacion;
  }

  public String getNombreVista() {
    return this.nombreVista;
  }

  public void setNombreVista(String nombreVista) {
    this.nombreVista = nombreVista;
  }

  public Integer getNumero() {
    return this.numero;
  }

  public void setNumero(Integer numero) {
    this.numero = numero;
  }

  public void clear() {
    this.paginaId = new Long(0L);
    this.vistaId = new Long(0L);
    this.Descripcion = null;
    this.filas = new Byte("1");
    this.columnas = new Byte("1");
    this.alto = new Integer(250);
    this.ancho = new Integer(300);
    this.paginas = null;
    this.nombreOrganizacion = null;
    this.nombreVista = null;
    setBloqueado(new Boolean(false));
    this.numero = new Integer(0);
  }
}