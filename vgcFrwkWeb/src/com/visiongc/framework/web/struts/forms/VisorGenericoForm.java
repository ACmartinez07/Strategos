package com.visiongc.framework.web.struts.forms;

import org.apache.struts.action.ActionForm;





public class VisorGenericoForm
  extends ActionForm
{
  private int pagina = 0;
  private String atributoOrden = null;
  private String tipoOrden = null;
  

  private String registrosPorPagina = null;
  

  private String paginasPorConjunto = null;
  static final long serialVersionUID = 0L;
  
  public VisorGenericoForm() {}
  
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
}
