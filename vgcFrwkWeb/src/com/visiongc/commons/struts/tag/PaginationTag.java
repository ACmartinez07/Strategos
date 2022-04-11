package com.visiongc.commons.struts.tag;

import com.visiongc.commons.util.PaginaLista;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import org.apache.commons.lang.StringUtils;

















public class PaginationTag
  extends VgcBaseTag
{
  static final long serialVersionUID = 0L;
  private String page;
  private String pageSize;
  private String pagesSetSize;
  private String resultSize;
  private String action;
  private String labelPage;
  private String styleClass;
  private String imageFirst;
  private String imagePrev;
  private String imageNext;
  private String imageLast;
  protected String nombrePaginaLista = null;
  
  protected String messageResources = null;
  
  public static final String NEWLINE = "\n";
  
  public static final String SPACE = "&nbsp;";
  

  public PaginationTag() {}
  

  public String getPage()
  {
    return page;
  }
  
  public void setPage(Object page) {
    this.page = String.valueOf(page);
  }
  
  public void setPage(String page) {
    this.page = page;
  }
  
  public String getPageSize() {
    return pageSize;
  }
  
  public void setPageSize(Object pageSize) {
    this.pageSize = String.valueOf(pageSize);
  }
  
  public void setPageSize(String pageSize) {
    this.pageSize = pageSize;
  }
  
  public String getPagesSetSize() {
    return pagesSetSize;
  }
  
  public void setPagesSetSize(String pagesSetSize) {
    this.pagesSetSize = pagesSetSize;
  }
  
  public void setPagesSetSize(Object pagesSetSize) {
    this.pagesSetSize = String.valueOf(pagesSetSize);
  }
  
  public String getResultSize() {
    return resultSize;
  }
  
  public void setResultSize(String resultSize) {
    this.resultSize = String.valueOf(resultSize);
  }
  
  public void setResultSize(Object resultSize) {
    this.resultSize = String.valueOf(resultSize);
  }
  
  public String getAction() {
    return action;
  }
  
  public void setAction(String action) {
    this.action = action;
  }
  
  public String getImageFirst() {
    return imageFirst;
  }
  
  public void setImageFirst(String imageFirst) {
    this.imageFirst = imageFirst;
  }
  
  public String getImagePrev() {
    return imagePrev;
  }
  
  public void setImagePrev(String imagePrev) {
    this.imagePrev = imagePrev;
  }
  
  public String getImageNext() {
    return imageNext;
  }
  
  public void setImageNext(String imageNext) {
    this.imageNext = imageNext;
  }
  
  public String getImageLast() {
    return imageLast;
  }
  
  public void setImageLast(String imageLast) {
    this.imageLast = imageLast;
  }
  
  public String getLabelPage() {
    return labelPage;
  }
  
  public void setLabelPage(String labelPage) {
    this.labelPage = labelPage;
  }
  
  public String getStyleClass() {
    return styleClass;
  }
  
  public void setStyleClass(String styleClass) {
    this.styleClass = styleClass;
  }
  
  public String getNombrePaginaLista() {
    return nombrePaginaLista;
  }
  
  public void setNombrePaginaLista(String nombrePaginaLista) {
    this.nombrePaginaLista = nombrePaginaLista;
  }
  

  public int doStartTag()
    throws JspException
  {
    int page = 0;
    
    int pageSize = 15;
    
    int resultSize = 100;
    int pages = 0;
    
    int setSize = 5;
    
    PaginaLista paginaLista = null;
    if ((nombrePaginaLista != null) && (!nombrePaginaLista.equals(""))) {
      paginaLista = (PaginaLista)pageContext.getRequest().getAttribute(nombrePaginaLista);
      if (paginaLista == null) {
        paginaLista = (PaginaLista)pageContext.getSession().getAttribute(nombrePaginaLista);
      }
      page = paginaLista.getNroPagina();
      pageSize = paginaLista.getTamanoPagina();
      resultSize = paginaLista.getTotal();
      setSize = paginaLista.getTamanoSetPaginas();
      if (setSize == 0) {
        setSize = 5;
      }
    }
    
    if (paginaLista == null)
    {
      page = 0;
      if ((this.page != null) && (!this.page.equals(""))) {
        page = Integer.parseInt(this.page);
      }
      if ((this.pageSize != null) && (!this.pageSize.equals(""))) {
        pageSize = Integer.parseInt(this.pageSize);
      }
      if ((this.resultSize != null) && (!this.resultSize.equals(""))) {
        resultSize = Integer.parseInt(this.resultSize);
      }
      if ((pagesSetSize != null) && (!pagesSetSize.equals(""))) {
        setSize = Integer.parseInt(pagesSetSize);
      }
    }
    
    if (pageSize == 0) {
      pageSize = resultSize;
    }
    

    pages = resultSize / pageSize;
    pages = resultSize % pageSize == 0 ? pages : pages + 1;
    
    String mostrandoDesde = getMessageResource(null, null, "tag.pagination.showingfrom");
    String hasta = getMessageResource(null, null, "tag.pagination.to");
    String de = getMessageResource(null, null, "tag.pagination.from");
    String imagenPrevio = getMessageResource(null, null, "tag.pagination.previo");
    String imagenSiguiente = getMessageResource(null, null, "tag.pagination.siguiente");
    String imagenPrincipio = getMessageResource(null, null, "tag.pagination.principio");
    String imagenFinal = getMessageResource(null, null, "tag.pagination.final");
    
    if (pages > 1)
    {


      int pagesSet = pages / setSize;
      pagesSet = pages % setSize == 0 ? pagesSet : pagesSet + 1;
      

      int setNumber = page / setSize;
      setNumber = page % setSize == 0 ? setNumber : setNumber + 1;
      




      int limSup = setNumber * setSize;
      int limInf = limSup - setSize + 1;
      
      StringBuffer sb = null;
      try
      {
        JspWriter out = pageContext.getOut();
        sb = new StringBuffer();
        sb.append(getTableWithStyle()).append("<tr>\n");
        if (limInf > 1) {
          sb.append("<td width=\"10\" align=\"center\" valign=\"middle\">");
          sb.append("<a onMouseOver=\"this.className='mouseEncimaBarraInferiorForma'\" onMouseOut=\"this.className='mouseFueraBarraInferiorForma'\" class=\"mouseFueraBarraInferiorForma\" href=\"" + getUrlPaginacion(1) + "\">");
          if ((imageFirst != null) && (!imageFirst.equals(""))) {
            sb.append("<img src=\"" + imageFirst + "\" border=\"0\" width=\"10\" height=\"10\" alt=" + imagenPrincipio + " >");

          }
          else
          {

            sb.append("<img src=\"" + getUrlAplicacion() + "/componentes/paginador/principio.gif" + "\" border=\"0\" width=\"10\" height=\"10\" alt=" + imagenPrincipio + " >");
          }
          sb.append("</a>");
          sb.append("</td>\n");
          sb.append("<td width=\"10\" align=\"center\" valign=\"middle\">");
          sb.append("<a onMouseOver=\"this.className='mouseEncimaBarraInferiorForma'\" onMouseOut=\"this.className='mouseFueraBarraInferiorForma'\" class=\"mouseFueraBarraInferiorForma\" href=\"" + getUrlPaginacion(limInf - 1) + "\">");
          if ((imagePrev != null) && (!imagePrev.equals(""))) {
            sb.append("<img src=\"" + imagePrev + "\" border=\"0\" width=\"10\" height=\"10\" alt=" + imagenPrevio + " >");

          }
          else
          {

            sb.append("<img src=\"" + getUrlAplicacion() + "/componentes/paginador/previo.gif" + "\" border=\"0\" width=\"10\" height=\"10\" alt=" + imagenPrevio + " >");
          }
          sb.append("</a>");
          sb.append("</td>\n");
        }
        for (int index = limInf; index <= limSup; index++) {
          sb.append("<td width=\"10\" align=\"center\" valign=\"middle\">");
          if (index > pages) {
            sb.append("&nbsp;");
          }
          else if (index != page) {
            sb.append("<a onMouseOver=\"this.className='mouseEncimaBarraInferiorForma'\" onMouseOut=\"this.className='mouseFueraBarraInferiorForma'\" class=\"mouseFueraBarraInferiorForma\" href=\"" + getUrlPaginacion(index) + "\">" + index + "</a>");
          } else {
            sb.append("<b>" + index + "</b>");
          }
          
          sb.append("</td>\n");
        }
        if (pages > limSup) {
          sb.append("<td width=\"10\" align=\"center\" valign=\"middle\">");
          sb.append("<a onMouseOver=\"this.className='mouseEncimaBarraInferiorForma'\" onMouseOut=\"this.className='mouseFueraBarraInferiorForma'\" class=\"mouseFueraBarraInferiorForma\" href=\"" + getUrlPaginacion(limSup + 1) + "\">");
          if ((imageNext != null) && (!imageNext.equals(""))) {
            sb.append("<img src=\"" + imageNext + "\" border=\"0\" width=\"10\" height=\"10\" alt=" + imagenSiguiente + " >");

          }
          else
          {

            sb.append("<img src=\"" + getUrlAplicacion() + "/componentes/paginador/siguiente.gif" + "\" border=\"0\" width=\"10\" height=\"10\" alt=" + imagenSiguiente + " >");
          }
          sb.append("</a>");
          sb.append("</td>\n");
          sb.append("<td width=\"10\" align=\"center\" valign=\"middle\">");
          sb.append("<a onMouseOver=\"this.className='mouseEncimaBarraInferiorForma'\" onMouseOut=\"this.className='mouseFueraBarraInferiorForma'\" class=\"mouseFueraBarraInferiorForma\" href=\"" + getUrlPaginacion(pages) + "\">");
          if ((imageLast != null) && (!imageLast.equals(""))) {
            sb.append("<img src=\"" + imageLast + "\" border=\"0\" width=\"10\" height=\"10\" alt=" + imagenFinal + " >");

          }
          else
          {

            sb.append("<img src=\"" + getUrlAplicacion() + "/componentes/paginador/final.gif" + "\" border=\"0\" width=\"10\" height=\"10\" alt=" + imagenFinal + " >");
          }
          sb.append("</a>");
          sb.append("</td>\n");
        }
        sb.append("<td align=\"center\" valign=\"middle\">");
        int iHasta = page * pageSize;
        int iDesde = iHasta - pageSize + 1;
        if (iHasta > resultSize) {
          iHasta = resultSize;
        }
        sb.append(" " + mostrandoDesde + " <b>" + iDesde + "</b> " + hasta + " <b>" + iHasta + "</b> " + de + " <b>" + resultSize + "<b>");
        sb.append("</td>\n");
        sb.append("</tr></table>\n");
        out.println(sb);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return 0;
  }
  
  public int doEndTag() {
    return 6;
  }
  
  private String getUrlPaginacion(int nroPagina)
  {
    return action.replaceAll(labelPage, String.valueOf(nroPagina));
  }
  
  public String getTableWithStyle() {
    if (StringUtils.isNotEmpty(styleClass)) {
      return "<table" + " class=\"" + styleClass + "\"" + ">\n";
    }
    return "<table bgcolor=eeeeff border=1 style=\"border-collapse:collapse\" bordercolor=#666699 >\n";
  }
  
  public String getMessageResources()
  {
    return messageResources;
  }
  
  public void setMessageResources(String messageResources) {
    this.messageResources = messageResources;
  }
}
