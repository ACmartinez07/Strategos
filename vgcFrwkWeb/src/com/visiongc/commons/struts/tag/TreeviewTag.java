package com.visiongc.commons.struts.tag;

import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.commons.web.TreeviewWeb;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

public class TreeviewTag
  extends TagSupport
{
  static final long serialVersionUID = 0L;
  public static final String KEY = "com.visiongc.commons.struts.tag.TreeviewTag";
  private String name;
  private String scope;
  private String nameSelectedId;
  private String baseObject;
  private String isRoot;
  private String urlConnector;
  private String urlConnectorOpen;
  private String urlConnectorClose;
  private String urlName;
  private String useUrlAnchor;
  private String fieldName;
  private String fieldId;
  private String fieldChildren;
  private String lblUrlObjectId;
  private String lblUrlAnchor;
  private String styleClass;
  private String checkbox;
  private String checkboxName;
  private String checkboxOnClick;
  private String urlJs;
  private String pathImages;
  private String useFrame;
  private String nameExcludedList;
  private String width;
  private String height;
  private String arbolBean;
  private String useNodeImage;
  private String labelNodeType;
  private String idType;
  
  public TreeviewTag() {}
  
  public void setNameExcludedList(String nameExcludedList)
  {
    this.nameExcludedList = nameExcludedList;
  }
  
  public String getNameExcludedList()
  {
    return nameExcludedList;
  }
  
  public String getName()
  {
    return name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getScope()
  {
    return scope;
  }
  
  public void setScope(String scope)
  {
    this.scope = scope;
  }
  
  public String getNameSelectedId()
  {
    return nameSelectedId;
  }
  
  public void setNameSelectedId(String nameSelectedId)
  {
    this.nameSelectedId = nameSelectedId;
  }
  
  public String getBaseObject()
  {
    return baseObject;
  }
  
  public void setBaseObject(String baseObject)
  {
    this.baseObject = baseObject;
  }
  
  public String getIsRoot()
  {
    return isRoot;
  }
  
  public void setIsRoot(String isRoot)
  {
    this.isRoot = isRoot;
  }
  
  public String getUrlConnector()
  {
    return urlConnector;
  }
  
  public void setUrlConnector(String urlConnector)
  {
    this.urlConnector = urlConnector;
    
    urlConnectorOpen = urlConnector;
  }
  
  public String getUrlConnectorOpen()
  {
    return urlConnectorOpen;
  }
  
  public void setUrlConnectorOpen(String urlConnectorOpen)
  {
    this.urlConnectorOpen = urlConnectorOpen;
  }
  
  public String getUrlConnectorClose()
  {
    return urlConnectorClose;
  }
  
  public void setUrlConnectorClose(String urlConnectorClose)
  {
    this.urlConnectorClose = urlConnectorClose;
  }
  
  public String getUrlName()
  {
    return urlName;
  }
  
  public void setUrlName(String urlName)
  {
    this.urlName = urlName;
  }
  
  public String getUseUrlAnchor()
  {
    return useUrlAnchor;
  }
  
  public void setUseUrlAnchor(String useUrlAnchor)
  {
    this.useUrlAnchor = useUrlAnchor;
  }
  
  public String getFieldName()
  {
    return fieldName;
  }
  
  public void setFieldName(String fieldName)
  {
    this.fieldName = fieldName;
  }
  
  public String getFieldId()
  {
    return fieldId;
  }
  
  public void setFieldId(String fieldId)
  {
    this.fieldId = fieldId;
  }
  
  public String getFieldChildren()
  {
    return fieldChildren;
  }
  
  public void setFieldChildren(String fieldChildren)
  {
    this.fieldChildren = fieldChildren;
  }
  
  public String getLblUrlObjectId()
  {
    return lblUrlObjectId;
  }
  
  public void setLblUrlObjectId(String lblUrlObjectId)
  {
    this.lblUrlObjectId = lblUrlObjectId;
  }
  
  public String getLblUrlAnchor()
  {
    return lblUrlAnchor;
  }
  
  public void setLblUrlAnchor(String lblUrlAnchor)
  {
    this.lblUrlAnchor = lblUrlAnchor;
  }
  
  public String getStyleClass()
  {
    return styleClass;
  }
  
  public void setStyleClass(String styleClass)
  {
    this.styleClass = styleClass;
  }
  
  public String getCheckbox()
  {
    return checkbox;
  }
  
  public void setCheckbox(String checkbox)
  {
    this.checkbox = checkbox;
  }
  
  public String getCheckboxName()
  {
    return checkboxName;
  }
  
  public void setCheckboxName(String checkboxName)
  {
    this.checkboxName = checkboxName;
  }
  
  public String getCheckboxOnClick()
  {
    return checkboxOnClick;
  }
  
  public void setCheckboxOnClick(String checkboxOnClick)
  {
    this.checkboxOnClick = checkboxOnClick;
  }
  
  public String getUrlJs()
  {
    return urlJs;
  }
  
  public void setUrlJs(String urlJs)
  {
    this.urlJs = urlJs;
  }
  
  public String getPathImages()
  {
    return pathImages;
  }
  
  public void setPathImages(String pathImages)
  {
    this.pathImages = pathImages;
  }
  
  public String getUseFrame()
  {
    return useFrame;
  }
  
  public void setUseFrame(String useFrame)
  {
    this.useFrame = useFrame;
  }
  
  public String getWidth()
  {
    return width;
  }
  
  public void setWidth(String width)
  {
    this.width = width;
  }
  
  public String getHeight()
  {
    return height;
  }
  
  public void setHeight(String height)
  {
    this.height = height;
  }
  
  public String getArbolBean()
  {
    return arbolBean;
  }
  
  public void setArbolBean(String arbolBean)
  {
    this.arbolBean = arbolBean;
  }
  
  public String getUseNodeImage()
  {
    return useNodeImage;
  }
  
  public void setUseNodeImage(String useNodeImage)
  {
    this.useNodeImage = useNodeImage;
  }
  
  public String getLabelNodeType()
  {
    return labelNodeType;
  }
  
  public void setLabelNodeType(String labelNodeType)
  {
    this.labelNodeType = labelNodeType;
  }
  
  public String getIdType()
  {
    return idType;
  }
  
  public void setIdType(String idType)
  {
    this.idType = idType;
  }
  



  public int doStartTag()
  {
    JspWriter out = pageContext.getOut();
    StringBuffer sb = null;
    
    sb = new StringBuffer();
    
    boolean isRoot = false;
    boolean checkbox = false;
    boolean useAnchor = false;
    boolean useFrame = true;
    boolean usarImagenNodo = false;
    
    if ((getIsRoot() != null) && (getIsRoot().equals("true")))
      isRoot = true;
    if ((getCheckbox() != null) && (getCheckbox().equals("true")))
      checkbox = true;
    if ((getUseUrlAnchor() != null) && (getUseUrlAnchor().equals("true")))
      useAnchor = true;
    if ((getUseFrame() != null) && (getUseFrame().equals("false")))
      useFrame = false;
    if (getNameExcludedList() != null) {
      nameExcludedList = getNameExcludedList();
    } else
      nameExcludedList = "";
    if ((getUseNodeImage() != null) && (getUseNodeImage().equals("true"))) {
      usarImagenNodo = true;
    }
    try
    {
      if ((arbolBean != null) && (!arbolBean.equals(""))) {
        sb.append(TreeviewWeb.createTreeviewBean(arbolBean, useFrame, getUrlJs(), getPathImages(), getNameSelectedId(), getScope(), isRoot, getUrlConnectorOpen(), getUrlConnectorClose(), getUrlName(), useAnchor, getFieldName(), getFieldId(), getFieldChildren(), getLblUrlObjectId(), getLblUrlAnchor(), getStyleClass(), checkbox, getCheckboxName(), getCheckboxOnClick(), getWidth(), getHeight(), usarImagenNodo, labelNodeType, idType, (HttpServletRequest)pageContext.getRequest()));
      } else {
        sb.append(TreeviewWeb.createTreeview(getName(), nameExcludedList, useFrame, getUrlJs(), getPathImages(), getNameSelectedId(), getScope(), getBaseObject(), isRoot, getUrlConnectorOpen(), getUrlConnectorClose(), getUrlName(), useAnchor, getFieldName(), getFieldId(), getFieldChildren(), getLblUrlObjectId(), getLblUrlAnchor(), getStyleClass(), checkbox, getCheckboxName(), getCheckboxOnClick(), getWidth(), getHeight(), usarImagenNodo, labelNodeType, idType, (HttpServletRequest)pageContext.getRequest()));
      }
      out.println(sb);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw new ChainedRuntimeException("Error de tag JSP : " + e.getMessage(), e);
    }
    
    return 0;
  }
  
  public int doEndTag()
  {
    return 6;
  }
}
