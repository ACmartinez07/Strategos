package com.visiongc.framework.web.struts.forms;

import org.apache.struts.action.ActionForm;






public class VisorListaForm
  extends ActionForm
{
  private int pagina = 0;
  private String atributoOrden = null;
  private String tipoOrden = null;
  private String registrosPorPagina = null;
  private String paginasPorConjunto = null;
  private String seleccionados = null;
  private String valoresSeleccionados = null;
  private Boolean editarForma = null;
  private Boolean verForma = null;
  private Boolean addForma = null;
  private Boolean deleteForma = null;
  private FiltroForm filtro = new FiltroForm();
  static final long serialVersionUID = 0L;
  
  public VisorListaForm() {}
  
  public int getPagina() {
    return pagina;
  }
  
  public void setPagina(int inPagina)
  {
    pagina = inPagina;
  }
  
  public String getAtributoOrden()
  {
    return atributoOrden;
  }
  
  public void setAtributoOrden(String atributoOrden)
  {
    this.atributoOrden = atributoOrden;
  }
  
  public String getTipoOrden()
  {
    return tipoOrden;
  }
  
  public void setTipoOrden(String inTipoOrden)
  {
    tipoOrden = inTipoOrden;
  }
  
  public String getPaginasPorConjunto()
  {
    return paginasPorConjunto;
  }
  
  public void setPaginasPorConjunto(String paginasPorConjunto)
  {
    this.paginasPorConjunto = paginasPorConjunto;
  }
  
  public String getRegistrosPorPagina()
  {
    return registrosPorPagina;
  }
  
  public void setRegistrosPorPagina(String registrosPorPagina)
  {
    this.registrosPorPagina = registrosPorPagina;
  }
  
  public String getSeleccionados()
  {
    return seleccionados;
  }
  
  public void setSeleccionados(String seleccionados)
  {
    this.seleccionados = seleccionados;
  }
  
  public String getValoresSeleccionados()
  {
    return valoresSeleccionados;
  }
  
  public void setValoresSeleccionados(String valoresSeleccionados)
  {
    this.valoresSeleccionados = valoresSeleccionados;
  }
  
  public Boolean getEditarForma()
  {
    return editarForma;
  }
  
  public void setEditarForma(Boolean editarForma)
  {
    this.editarForma = editarForma;
  }
  
  public Boolean getVerForma()
  {
    return verForma;
  }
  
  public void setVerForma(Boolean verForma)
  {
    this.verForma = verForma;
  }
  
  public FiltroForm getFiltro()
  {
    return filtro;
  }
  
  public void setFiltro(FiltroForm filtro)
  {
    this.filtro = filtro;
  }
  
  public void setAddForma(Boolean addForma)
  {
    this.addForma = addForma;
  }
  
  public Boolean getAddForma()
  {
    return addForma;
  }
  
  public void setDeleteForma(Boolean deleteForma)
  {
    this.deleteForma = deleteForma;
  }
  
  public Boolean getDeleteForma()
  {
    return deleteForma;
  }
}
