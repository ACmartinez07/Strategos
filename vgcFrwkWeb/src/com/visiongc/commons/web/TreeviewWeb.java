package com.visiongc.commons.web;

import com.visiongc.commons.util.ArbolBean;
import com.visiongc.commons.util.ListaMap;
import com.visiongc.commons.util.ObjetoAbstracto;
import com.visiongc.commons.util.TextEncoder;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.arboles.MultiNodoArbol;
import com.visiongc.framework.arboles.NodoArbol;
import com.visiongc.framework.arboles.ObjetoNodoHoja;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;








public class TreeviewWeb
{
  private static final String SEPARATOR_TOKENS = "Ç";
  
  public TreeviewWeb() {}
  
  public static String getSeparadorTokens()
  {
    return "Ç";
  }
  







  public static String getUrlTreeview(String url)
  {
    String str = url;
    
    int indexAncla = url.indexOf("&sanclasel=");
    if (indexAncla < 0) {
      indexAncla = url.indexOf("?sanclasel=");
    }
    if (indexAncla > -1) {
      indexAncla++;
      String parametroAncla = url.substring(indexAncla);
      int index = url.indexOf("?");
      str = url.substring(0, index + 1) + parametroAncla;
    }
    return str;
  }
  
  public static void publishTree(String nameTreeview, String objectId, String scope, HttpServletRequest req, boolean reset) throws Exception {
    publishTree(nameTreeview, "", "", objectId, scope, req, reset);
  }
  
  public static void publishTree(String nameTreeview, String objectId, String scope, HttpServletRequest req) throws Exception {
    publishTree(nameTreeview, "", "", objectId, scope, req, false);
  }
  
  public static void publishTreeOpen(String nameTreeview, String objectId, String scope, HttpServletRequest req) throws Exception {
    publishTree(nameTreeview, "", "", "", objectId, "", scope, req, false);
  }
  
  public static void publishTreeClose(String nameTreeview, String objectId, String scope, HttpServletRequest req) throws Exception {
    publishTree(nameTreeview, "", "", "", "", objectId, scope, req, false);
  }
  
  public static void publishTree(String nameTreeview, String nameExcludedList, String excludedElemId, String objectId, String scope, HttpServletRequest req) throws Exception {
    publishTree(nameTreeview, nameExcludedList, excludedElemId, objectId, scope, req, false);
  }
  
  public static void publishTree(String nameTreeview, String nameExcludedList, String excludedElemId, String objectId, String scope, HttpServletRequest req, boolean reset) throws Exception {
    publishTree(nameTreeview, nameExcludedList, excludedElemId, objectId, "", "", scope, req, reset);
  }
  
  public static void publishTree(String nameTreeview, String nameExcludedList, String excludedElemId, String objectId, String openObjectId, String closeObjectId, String scope, HttpServletRequest req, boolean reset) throws Exception
  {
    try {
      String openedNodesSet = "Ç";
      String excludedNodesSet = "Ç";
      
      if (!reset) {
        if ((scope == null) || (scope.equalsIgnoreCase("session"))) {
          if (req.getSession().getAttribute(nameTreeview) != null) {
            openedNodesSet = (String)req.getSession().getAttribute(nameTreeview);
          }
          if (req.getSession().getAttribute(nameExcludedList) != null) {
            excludedNodesSet = (String)req.getSession().getAttribute(nameExcludedList);
          }
        }
        else {
          if (req.getAttribute(nameTreeview) != null) {
            openedNodesSet = (String)req.getAttribute(nameTreeview);
          }
          if (req.getAttribute(nameExcludedList) != null) {
            excludedNodesSet = (String)req.getAttribute(nameExcludedList);
          }
        }
      }
      
      if ((openObjectId != null) && (!openObjectId.equals(""))) {
        if (!existElementStr(openedNodesSet, openObjectId)) {
          openedNodesSet = addElementStr(openedNodesSet, openObjectId);
        }
      } else if ((closeObjectId != null) && (!closeObjectId.equals(""))) {
        openedNodesSet = removeElementStr(openedNodesSet, closeObjectId);
      }
      else if (!existElementStr(openedNodesSet, objectId)) {
        openedNodesSet = addElementStr(openedNodesSet, objectId);
      } else {
        openedNodesSet = removeElementStr(openedNodesSet, objectId);
      }
      

      if ((!nameExcludedList.equals("")) && (!existElementStr(excludedNodesSet, excludedElemId))) {
        excludedNodesSet = addElementStr(excludedNodesSet, excludedElemId);
      }
      
      if ((scope == null) || (scope.equalsIgnoreCase("session"))) {
        req.getSession().setAttribute(nameTreeview, openedNodesSet);
        if (!nameExcludedList.equals("")) {
          req.getSession().setAttribute(nameExcludedList, excludedNodesSet);
        }
      } else {
        req.setAttribute(nameTreeview, openedNodesSet);
        if (!nameExcludedList.equals("")) {
          req.setAttribute(nameExcludedList, excludedNodesSet);
        }
      }
    }
    catch (Exception e) {
      throw new ChainedRuntimeException(e.getMessage(), e);
    }
  }
  
  public static void publishArbol(ArbolBean arbolBean, String seleccionadoNodoId, boolean reset) throws Exception {
    publishArbol(arbolBean, "", seleccionadoNodoId, "", "", reset);
  }
  
  public static void publishArbolAbrirNodo(ArbolBean arbolBean, String abrirNodoId) throws Exception {
    publishArbol(arbolBean, "", "", abrirNodoId, "", false);
  }
  
  public static void publishArbolCerrarNodo(ArbolBean arbolBean, String cerrarNodoId) throws Exception {
    publishArbol(arbolBean, "", "", "", cerrarNodoId, false);
  }
  








  public static void publishArbol(ArbolBean arbolBean, String excluirNodoId, String seleccionadoNodoId, String abrirNodoId, String cerrarNodoId, boolean reset)
    throws Exception
  {
    try
    {
      String listaNodosAbiertos = "Ç";
      String listaNodosExcluidos = "Ç";
      
      if (!reset) {
        if (arbolBean.getListaNodosAbiertos() != null) {
          listaNodosAbiertos = arbolBean.getListaNodosAbiertos();
        }
        if (arbolBean.getListaNodosExcluidos() != null) {
          listaNodosExcluidos = arbolBean.getListaNodosExcluidos();
        }
      }
      
      if ((abrirNodoId != null) && (!abrirNodoId.equals(""))) {
        if (!existElementStr(listaNodosAbiertos, abrirNodoId)) {
          listaNodosAbiertos = addElementStr(listaNodosAbiertos, abrirNodoId);
        }
      } else if ((cerrarNodoId != null) && (!cerrarNodoId.equals(""))) {
        listaNodosAbiertos = removeElementStr(listaNodosAbiertos, cerrarNodoId);
      }
      else if (!existElementStr(listaNodosAbiertos, seleccionadoNodoId)) {
        listaNodosAbiertos = addElementStr(listaNodosAbiertos, seleccionadoNodoId);
      } else {
        listaNodosAbiertos = removeElementStr(listaNodosAbiertos, seleccionadoNodoId);
      }
      

      if ((excluirNodoId != null) && (!excluirNodoId.equals("")) && (!existElementStr(listaNodosAbiertos, excluirNodoId))) {
        listaNodosExcluidos = addElementStr(listaNodosExcluidos, excluirNodoId);
      }
      
      arbolBean.setListaNodosAbiertos(listaNodosAbiertos);
      arbolBean.setListaNodosExcluidos(listaNodosExcluidos);
    }
    catch (Exception e) {
      throw new ChainedRuntimeException(e.getMessage(), e);
    }
  }
  





























  public static String createTreeviewBean(String nombreArbolBean, boolean useFrame, String urlJs, String pathImages, String nameSelectedId, String scope, boolean isRoot, String urlConnectorOpen, String urlConnectorClose, String urlName, boolean useAnchor, String nameFieldName, String nameFieldId, String nameFieldChildren, String lblUrlObjectId, String lblUrlAnchor, String styleClass, boolean checkbox, String checkboxName, String checkboxOnClick, String width, String height, boolean usarImagenNodo, String labelNodeType, String idType, HttpServletRequest req)
    throws Exception
  {
    return createTreeview(nombreArbolBean, null, null, useFrame, urlJs, pathImages, nameSelectedId, scope, null, isRoot, urlConnectorOpen, urlConnectorClose, urlName, useAnchor, nameFieldName, nameFieldId, nameFieldChildren, lblUrlObjectId, lblUrlAnchor, styleClass, checkbox, checkboxName, checkboxOnClick, width, height, usarImagenNodo, labelNodeType, idType, req);
  }
  
  public static String createTreeview(String nameTreeview, String nameExcludedList, boolean useFrame, String urlJs, String pathImages, String nameSelectedId, String scope, String nameBaseObject, boolean isRoot, String urlConnectorOpen, String urlConnectorClose, String urlName, boolean useAnchor, String nameFieldName, String nameFieldId, String nameFieldChildren, String lblUrlObjectId, String lblUrlAnchor, String styleClass, boolean checkbox, String checkboxName, String checkboxOnClick, String width, String height, boolean usarImagenNodo, String labelNodeType, String idType, HttpServletRequest req) throws Exception
  {
    return createTreeview(null, nameTreeview, nameExcludedList, useFrame, urlJs, pathImages, nameSelectedId, scope, nameBaseObject, isRoot, urlConnectorOpen, urlConnectorClose, urlName, useAnchor, nameFieldName, nameFieldId, nameFieldChildren, lblUrlObjectId, lblUrlAnchor, styleClass, checkbox, checkboxName, checkboxOnClick, width, height, usarImagenNodo, labelNodeType, idType, req);
  }
  































  public static String createTreeview(String nombreArbolBean, String nameTreeview, String nameExcludedList, boolean useFrame, String urlJs, String pathImages, String nameSelectedId, String scope, String nameBaseObject, boolean isRoot, String urlConnectorOpen, String urlConnectorClose, String urlName, boolean useAnchor, String nameFieldName, String nameFieldId, String nameFieldChildren, String lblUrlObjectId, String lblUrlAnchor, String styleClass, boolean checkbox, String checkboxName, String checkboxOnClick, String width, String height, boolean usarImagenNodo, String labelNodeType, String idType, HttpServletRequest req)
    throws Exception
  {
    StringBuffer html = new StringBuffer();
    String stClass = "";
    ArbolBean arbolBean = null;
    
    if ((nombreArbolBean != null) && (!nombreArbolBean.equals(""))) {
      if (scope.equals("session")) {
        arbolBean = (ArbolBean)req.getSession().getAttribute(nombreArbolBean);
      } else if (scope.equals("request")) {
        arbolBean = (ArbolBean)req.getAttribute(nombreArbolBean);
      }
    }
    
    Object baseObject = null;
    
    if (arbolBean != null) {
      baseObject = arbolBean.getNodoRaiz();
    }
    else if (scope.equals("session")) {
      baseObject = req.getSession().getAttribute(nameBaseObject);
    } else if (scope.equals("request")) {
      baseObject = req.getAttribute(nameBaseObject);
    }
    

    if (nameExcludedList == null) {
      nameExcludedList = "";
    }
    
    if (nameSelectedId == null) {
      nameSelectedId = "";
    }
    
    if (useFrame) {
      if ((width == null) || (width.equals(""))) {
        width = "100";
      }
      if ((height == null) || (height.equals(""))) {
        height = "100";
      }
      if ((styleClass != null) && (!styleClass.equals(""))) {
        stClass = "class=\"" + styleClass + "Contenedor\"";
      }
      html.append("<div " + stClass + " style=\"position:relative; overflow:auto; width:" + width + "%; height:" + height + "%;\">" + "\n");
      



      if ((styleClass != null) && (!styleClass.equals(""))) {
        stClass = "class=\"" + styleClass + "\"";
      }
      html.append("<div " + stClass + " style=\"position:absolute; width:");
      if ((width.indexOf("%") < 0) && (width.indexOf("px") < 0)) {
        html.append(Integer.parseInt(width) - 30);
      } else {
        html.append(width);
      }
      html.append("; height:");
      html.append(height);
      html.append("; z-index:1; left:0px; top:0px\">\n");
    }
    
    html.append("<script language=\"Javascript\" src=\"" + req.getContextPath() + urlJs + "\">" + "\n" + "</script>");
    
    html.append("<script language=\"Javascript\">\n");
    
    html.append("setInicialTop(5);setInicialLeft(5);\n");
    
    html.append("setPathImages('" + req.getContextPath() + pathImages + "');" + "\n");
    
    if (baseObject != null) {
      createTreeview(arbolBean, nameTreeview, nameExcludedList, scope, nameSelectedId, baseObject, html, 0, isRoot, false, urlConnectorOpen, urlConnectorClose, urlName, useAnchor, nameFieldName, nameFieldId, nameFieldChildren, lblUrlObjectId, lblUrlAnchor, styleClass, checkbox, checkboxName, checkboxOnClick, usarImagenNodo, labelNodeType, idType, req);
    }
    html.append("</script>\n");
    
    if (useFrame) {
      html.append("</div></div>\n");
    }
    
    return html.toString();
  }
  
  private static void createTreeview(ArbolBean arbolBean, String nameTreeview, String nameExcludedList, String scope, String nameSelectedId, Object baseObject, StringBuffer html, int intNivel, boolean isRoot, boolean isFinalNode, String urlConnectorOpen, String urlConnectorClose, String urlName, boolean useAnchor, String nameFieldName, String nameFieldId, String nameFieldChildren, String lblUrlObjectId, String lblUrlAnchor, String styleClass, boolean checkbox, String checkboxName, String checkboxOnClick, boolean usarImagenNodo, String labelNodeType, String idType, HttpServletRequest req) throws Exception
  {
    String nivel = "";
    String tieneHijos = "";
    String esNodoFinal = "";
    String esRaiz = "";
    String seleccionado = "";
    
    Object hijo = new Object();
    



    NodoArbol nodoArbol = null;
    

    try
    {
      if (ObjetoAbstracto.implementaInterface(baseObject, "NodoArbol")) {
        nodoArbol = (NodoArbol)baseObject;
      } else if (ObjetoAbstracto.implementaInterface(baseObject, "ObjetoNodoHoja"))
      {
        nameFieldName = ((ObjetoNodoHoja)baseObject).getNombreCampoNombre();
        nameFieldId = ((ObjetoNodoHoja)baseObject).getNombreCampoId();
        nameFieldChildren = "";
      }
      
      if ((nameFieldChildren != null) && (!nameFieldChildren.equals("")))
        nameFieldChildren = nameFieldChildren.substring(0, 1).toUpperCase() + nameFieldChildren.substring(1);
      nameFieldName = nameFieldName.substring(0, 1).toUpperCase() + nameFieldName.substring(1);
      nameFieldId = nameFieldId.substring(0, 1).toUpperCase() + nameFieldId.substring(1);
      

      Class baseClass = baseObject.getClass();
      String tipoNodo = ObjetoAbstracto.getClassSimpleName(baseClass);
      
      Class[] tipoParametros = new Class[0];
      String[] parametros = new String[0];
      

      Collection hijosCast = new ArrayList();
      if (nodoArbol != null)
      {
        if (ObjetoAbstracto.implementaInterface(baseObject, "MultiNodoArbol"))
        {
          Collection listaHijos = ((MultiNodoArbol)baseObject).getHijosHoja();
          if (listaHijos != null)
            hijosCast.addAll(listaHijos);
        }
        if (nodoArbol.getNodoArbolHijos() != null) {
          hijosCast.addAll(nodoArbol.getNodoArbolHijos());
        }
      }
      else {
        Collection listaHijos = (Collection)baseClass.getMethod("get" + nameFieldChildren, tipoParametros).invoke(baseObject, parametros);
        if (listaHijos != null)
          hijosCast.addAll(listaHijos);
      }
      List hijos = new ArrayList();
      hijos.addAll(hijosCast);
      

      String nombre = "";
      if (nodoArbol != null) {
        nombre = nodoArbol.getNodoArbolNombre();
      }
      else {
        Object resultadoLlamada = baseClass.getMethod("get" + nameFieldName, tipoParametros).invoke(baseObject, parametros);
        
        if (resultadoLlamada == null) {
          throw new Exception("El nodo de árbol de tipo " + baseObject.getClass().getName() + " devolvió un valor nulo para la propiedad " + nameFieldName);
        }
        nombre = ((Serializable)resultadoLlamada).toString();
      }
      

      String nodoId = "";
      if (nodoArbol != null) {
        nodoId = nodoArbol.getNodoArbolId();
      } else {
        nodoId = ((Serializable)baseClass.getMethod("get" + nameFieldId, tipoParametros).invoke(baseObject, parametros)).toString();
      }
      
      if (isRoot) {
        nivel = Integer.toString(intNivel + 1);
      } else {
        nivel = Integer.toString(intNivel);
      }
      esNodoFinal = Boolean.toString(isFinalNode);
      esRaiz = Boolean.toString(isRoot);
      tieneHijos = Boolean.toString(hijos.size() > 0);
      

      String selectedId = "";
      if (arbolBean != null) {
        selectedId = arbolBean.getSeleccionadoId();

      }
      else if (!nameFieldId.equals(""))
      {
        String attNameFieldId = nameFieldId.substring(0, 1).toLowerCase() + nameFieldId.substring(1);
        if (scope.equals("session"))
        {
          if (req.getSession().getAttribute(attNameFieldId) != null) {
            selectedId = ((Serializable)req.getSession().getAttribute(attNameFieldId)).toString();
          }
          
        }
        else if (req.getAttribute(attNameFieldId) != null) {
          selectedId = ((Serializable)req.getAttribute(attNameFieldId)).toString();
        }
      }
      


      seleccionado = Boolean.toString(selectedId.equals(nodoId));
      
      if (lblUrlObjectId == null) {
        lblUrlObjectId = "";
      }
      String idFieldValueLnk = nodoId;
      
      if (ObjetoAbstracto.implementaInterface(baseObject, "ObjetoNodoHoja")) {
        idFieldValueLnk = "\\\\'" + idFieldValueLnk + "_" + ObjetoAbstracto.getClassSimpleName(baseObject) + "\\\\'";
      }
      String linkConnectorOpen = "";
      if ((urlConnectorOpen != null) && (!urlConnectorOpen.equals("")))
      {
        linkConnectorOpen = urlConnectorOpen;
        if ((lblUrlObjectId != null) && (!lblUrlObjectId.equals("")) && (idType == "String")) {
          linkConnectorOpen = linkConnectorOpen.replaceAll(lblUrlObjectId, "\\\\'" + idFieldValueLnk + "\\\\'");
        } else if ((lblUrlObjectId != null) && (!lblUrlObjectId.equals(""))) {
          linkConnectorOpen = linkConnectorOpen.replaceAll(lblUrlObjectId, idFieldValueLnk);
        }
        if ((labelNodeType != null) && (!labelNodeType.equals(""))) {
          linkConnectorOpen = linkConnectorOpen.replaceAll(labelNodeType, "\\\\'" + tipoNodo + "\\\\'");
        }
      }
      String linkConnectorClose = "";
      if ((urlConnectorClose != null) && (!urlConnectorClose.equals("")))
      {
        linkConnectorClose = urlConnectorClose;
        if ((lblUrlObjectId != null) && (!lblUrlObjectId.equals("")) && (idType == "String")) {
          linkConnectorClose = linkConnectorClose.replaceAll(lblUrlObjectId, "\\\\'" + idFieldValueLnk + "\\\\'");
        } else if ((lblUrlObjectId != null) && (!lblUrlObjectId.equals(""))) {
          linkConnectorClose = linkConnectorClose.replaceAll(lblUrlObjectId, idFieldValueLnk);
        }
        if ((labelNodeType != null) && (!labelNodeType.equals(""))) {
          linkConnectorClose = linkConnectorClose.replaceAll(labelNodeType, "\\\\'" + tipoNodo + "\\\\'");
        }
      }
      String linkName = "";
      if ((urlName != null) && (!urlName.equals("")))
      {
        linkName = urlName;
        if ((lblUrlObjectId != null) && (!lblUrlObjectId.equals("")) && (idType == "String")) {
          linkName = linkName.replaceAll(lblUrlObjectId, "\\\\'" + idFieldValueLnk + "\\\\'");
        } else if ((lblUrlObjectId != null) && (!lblUrlObjectId.equals(""))) {
          linkName = linkName.replaceAll(lblUrlObjectId, idFieldValueLnk);
        }
        if ((labelNodeType != null) && (!labelNodeType.equals(""))) {
          linkName = linkName.replaceAll(labelNodeType, "\\\\'" + tipoNodo + "\\\\'");
        }
      }
      boolean abierto = false;
      
      if (arbolBean != null)
      {
        abierto = existElementStr(arbolBean.getListaNodosAbiertos(), nodoId);


      }
      else if (scope.equals("session")) {
        abierto = existElementStr((String)req.getSession().getAttribute(nameTreeview), nodoId);
      } else {
        abierto = existElementStr((String)req.getAttribute(nameTreeview), nodoId);
      }
      
      boolean hideNodo = false;
      
      if (arbolBean != null)
      {
        if (arbolBean.getListaNodosExcluidos() != null) {
          hideNodo = existElementStr(arbolBean.getListaNodosExcluidos(), nodoId);
        }
        
      }
      else if (!nameExcludedList.equals(""))
      {

        if (scope.equals("session"))
        {
          if ((String)req.getSession().getAttribute(nameExcludedList) != null) {
            hideNodo = existElementStr((String)req.getSession().getAttribute(nameExcludedList), nodoId);
          }
          
        }
        else if ((String)req.getAttribute(nameExcludedList) != null) {
          hideNodo = existElementStr((String)req.getAttribute(nameExcludedList), nodoId);
        }
      }
      

      if (hideNodo)
      {
        linkConnectorOpen = "";
        linkConnectorClose = "";
        linkName = "";
      }
      
      Object listaImgenes = req.getSession().getAttribute("imagenesNodosHoja");
      ListaMap imagenesNodosHojas = null;
      String imagenNodo = "";
      if (listaImgenes != null)
      {
        imagenesNodosHojas = (ListaMap)listaImgenes;
        if (imagenesNodosHojas.get(ObjetoAbstracto.getClassSimpleName(baseObject)) != null) {
          imagenNodo = (String)imagenesNodosHojas.get(ObjetoAbstracto.getClassSimpleName(baseObject));
        }
      }
      if (usarImagenNodo)
      {
        imagenNodo = "imgNodo";
        if (nodoArbol != null)
        {
          if ((nodoArbol.getNodoArbolNombreImagen(Byte.valueOf((byte)1)) != null) && (!nodoArbol.getNodoArbolNombreImagen(Byte.valueOf((byte)1)).equals(""))) {
            imagenNodo = imagenNodo + nodoArbol.getNodoArbolNombreImagen(Byte.valueOf((byte)1));
          } else {
            imagenNodo = imagenNodo + tipoNodo;
          }
        } else
          imagenNodo = imagenNodo + tipoNodo;
        if ((abierto) && (hijos.size() > 0)) {
          imagenNodo = imagenNodo + "Expandido.gif";
        } else {
          imagenNodo = imagenNodo + ".gif";
        }
      }
      String toolTipImg1 = "";
      if (!imagenNodo.equals(""))
      {
        if ((nodoArbol != null) && (nodoArbol.getNodoArbolNombreToolTipImagen(Byte.valueOf((byte)1)) != null) && (!nodoArbol.getNodoArbolNombreToolTipImagen(Byte.valueOf((byte)1)).equals(""))) {
          toolTipImg1 = nodoArbol.getNodoArbolNombreToolTipImagen(Byte.valueOf((byte)1));
        }
      }
      String imagen2Nodo = "";
      if (usarImagenNodo)
      {
        if ((nodoArbol != null) && (nodoArbol.getNodoArbolNombreImagen(Byte.valueOf((byte)2)) != null) && (!nodoArbol.getNodoArbolNombreImagen(Byte.valueOf((byte)2)).equals("")))
        {
          imagen2Nodo = "imgNodo";
          imagen2Nodo = imagen2Nodo + nodoArbol.getNodoArbolNombreImagen(Byte.valueOf((byte)2));
          if ((abierto) && (hijos.size() > 0)) {
            imagen2Nodo = imagen2Nodo + "Expandido.gif";
          } else {
            imagen2Nodo = imagen2Nodo + ".gif";
          }
        }
      }
      String toolTipImg2 = "";
      if (!imagen2Nodo.equals(""))
      {
        if ((nodoArbol != null) && (nodoArbol.getNodoArbolNombreToolTipImagen(Byte.valueOf((byte)2)) != null) && (!nodoArbol.getNodoArbolNombreToolTipImagen(Byte.valueOf((byte)2)).equals(""))) {
          toolTipImg2 = nodoArbol.getNodoArbolNombreToolTipImagen(Byte.valueOf((byte)2));
        }
      }
      if (linkConnectorClose.equals(""))
      {


        html.append("addNode('" + TextEncoder.encodeForJavascript(nombre) + "', '" + nodoId + "', '" + nivel + "', " + tieneHijos + 
          ", " + abierto + ", " + esNodoFinal + ", " + esRaiz + ",  " + seleccionado + ", '" + imagenNodo + "', '" + toolTipImg1 + "', '" + imagen2Nodo + "', '" + toolTipImg2 + "', '', " + checkbox + 
          ", false, '" + checkboxName + "', '" + checkboxOnClick + "', '" + linkConnectorOpen + "', '', '" + linkName + "', " + useAnchor + 
          ", '" + styleClass + "', '" + lblUrlAnchor + "' );" + "\n");

      }
      else
      {

        html.append("addNode('" + TextEncoder.encodeForJavascript(nombre) + "', '" + nodoId + "', '" + nivel + "', " + tieneHijos + 
          ", " + abierto + ", " + esNodoFinal + ", " + esRaiz + ",  " + seleccionado + ", '" + imagenNodo + "', '" + toolTipImg1 + "', '" + imagen2Nodo + "', '" + toolTipImg2 + "', '', " + checkbox + 
          ", false, '" + checkboxName + "', '" + checkboxOnClick + "', '" + linkConnectorOpen + "', '" + linkConnectorClose + 
          "', '" + linkName + "', " + useAnchor + ", '" + styleClass + "', '" + lblUrlAnchor + "' );" + "\n");
      }
      
      if (abierto)
      {
        for (Iterator i = hijos.iterator(); i.hasNext();)
        {
          hijo = i.next();
          boolean nodofinal = false;
          
          String hijoId = "";
          if (ObjetoAbstracto.implementaInterface(hijo, "NodoArbol")) {
            hijoId = ((NodoArbol)hijo).getNodoArbolId();
          }
          else {
            if (ObjetoAbstracto.implementaInterface(hijo, "ObjetoNodoHoja"))
            {
              nameFieldId = ((ObjetoNodoHoja)hijo).getNombreCampoId();
              nameFieldId = nameFieldId.substring(0, 1).toUpperCase() + nameFieldId.substring(1);
            }
            hijoId = ((Serializable)hijo.getClass().getMethod("get" + nameFieldId, tipoParametros).invoke(hijo, parametros)).toString();
          }
          

          Object ultimoNodo = hijos.get(hijos.size() - 1);
          String ultimoHijoId = "";
          if (ObjetoAbstracto.implementaInterface(ultimoNodo, "NodoArbol")) {
            ultimoHijoId = ((NodoArbol)ultimoNodo).getNodoArbolId();
          }
          else {
            if (ObjetoAbstracto.implementaInterface(ultimoNodo, "ObjetoNodoHoja"))
            {
              nameFieldId = ((ObjetoNodoHoja)ultimoNodo).getNombreCampoId();
              nameFieldId = nameFieldId.substring(0, 1).toUpperCase() + nameFieldId.substring(1);
            }
            ultimoHijoId = ((Serializable)ultimoNodo.getClass().getMethod("get" + nameFieldId, tipoParametros).invoke(ultimoNodo, parametros)).toString();
          }
          
          if (hijoId.equals(ultimoHijoId)) {
            nodofinal = true;
          } else {
            nodofinal = false;
          }
          createTreeview(arbolBean, nameTreeview, nameExcludedList, scope, nameSelectedId, hijo, html, intNivel + 1, false, nodofinal, urlConnectorOpen, urlConnectorClose, urlName, useAnchor, nameFieldName, nameFieldId, nameFieldChildren, lblUrlObjectId, lblUrlAnchor, styleClass, checkbox, checkboxName, checkboxOnClick, usarImagenNodo, labelNodeType, idType, req);
        }
        
      }
    }
    catch (Throwable t)
    {
      throw new ChainedRuntimeException(t.getMessage(), t);
    }
    Class[] tipoParametros;
    String[] parametros;
    Class baseClass;
  }
  
  private static boolean existElementStr(String cadena, String elemento) throws Exception {
    try { return cadena.indexOf("Ç" + elemento + "Ç") > -1;
    }
    catch (Exception e)
    {
      throw new ChainedRuntimeException(e.getMessage(), e);
    }
  }
  
  private static String addElementStr(String cadena, String elemento) throws Exception
  {
    try
    {
      return cadena + elemento + "Ç";
    }
    catch (Exception e)
    {
      throw new ChainedRuntimeException(e.getMessage(), e);
    }
  }
  
  private static String removeElementStr(String cadena, String elemento) throws Exception
  {
    String tempStrIni = "";
    String tempStrFin = "";
    int posToken = 0;
    int lenToken = 0;
    String cadenaResultante = "";
    
    try
    {
      posToken = cadena.indexOf("Ç" + elemento + "Ç");
      
      cadenaResultante = cadena;
      
      if (posToken > -1)
      {
        lenToken = ("Ç" + elemento).length();
        
        tempStrIni = cadena.substring(0, posToken);
        tempStrFin = cadena.substring(posToken + lenToken); }
      return tempStrIni + tempStrFin;


    }
    catch (Exception e)
    {


      throw new ChainedRuntimeException(e.getMessage(), e);
    }
  }
  


















  public static boolean isOpenNode(String nameTreeview, String objectId, String scope, HttpServletRequest req)
  {
    String openedNodesSet = "";
    
    if ((scope == null) || (scope.equalsIgnoreCase("session")))
    {
      if (req.getSession().getAttribute(nameTreeview) != null) {
        openedNodesSet = (String)req.getSession().getAttribute(nameTreeview);
      }
      
    }
    else if (req.getAttribute(nameTreeview) != null) {
      openedNodesSet = (String)req.getAttribute(nameTreeview);
    }
    
    return openedNodesSet.indexOf("Ç" + objectId + "Ç") > -1;
  }
}
