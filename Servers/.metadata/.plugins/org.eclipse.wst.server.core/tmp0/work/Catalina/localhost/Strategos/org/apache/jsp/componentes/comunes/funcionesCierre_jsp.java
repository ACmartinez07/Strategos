/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.57
 * Generated at: 2022-03-30 15:32:31 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.componentes.comunes;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class funcionesCierre_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/tld/struts-bean.tld", Long.valueOf(1646829839000L));
    _jspx_dependants.put("/WEB-INF/tld/struts-logic.tld", Long.valueOf(1646829839000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fname;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fmessagesPresent = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fname = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.release();
    _005fjspx_005ftagPool_005flogic_005fmessagesPresent.release();
    _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.release();
    _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fname.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"/paginas/comunes/errorJsp.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!-- Modificado por: Kerwin Arias (24/11/2012) -->\n");
      out.write("\n");
      //  bean:define
      org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f0 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
      boolean _jspx_th_bean_005fdefine_005f0_reused = false;
      try {
        _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
        _jspx_th_bean_005fdefine_005f0.setParent(null);
        // /componentes/comunes/funcionesCierre.jsp(8,0) name = toScope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_bean_005fdefine_005f0.setToScope("page");
        // /componentes/comunes/funcionesCierre.jsp(8,0) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_bean_005fdefine_005f0.setId("appGlobalHayMensajes");
        // /componentes/comunes/funcionesCierre.jsp(8,0) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_bean_005fdefine_005f0.setValue("false");
        int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
        if (_jspx_th_bean_005fdefine_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
        _jspx_th_bean_005fdefine_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fdefine_005f0, _jsp_getInstanceManager(), _jspx_th_bean_005fdefine_005f0_reused);
      }
      java.lang.String appGlobalHayMensajes = null;
      appGlobalHayMensajes = (java.lang.String) _jspx_page_context.findAttribute("appGlobalHayMensajes");
      out.write('\n');
      //  logic:messagesPresent
      org.apache.struts.taglib.logic.MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f0 = (org.apache.struts.taglib.logic.MessagesPresentTag) _005fjspx_005ftagPool_005flogic_005fmessagesPresent.get(org.apache.struts.taglib.logic.MessagesPresentTag.class);
      boolean _jspx_th_logic_005fmessagesPresent_005f0_reused = false;
      try {
        _jspx_th_logic_005fmessagesPresent_005f0.setPageContext(_jspx_page_context);
        _jspx_th_logic_005fmessagesPresent_005f0.setParent(null);
        int _jspx_eval_logic_005fmessagesPresent_005f0 = _jspx_th_logic_005fmessagesPresent_005f0.doStartTag();
        if (_jspx_eval_logic_005fmessagesPresent_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write('\n');
            out.write('	');
            //  bean:define
            org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f1 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
            boolean _jspx_th_bean_005fdefine_005f1_reused = false;
            try {
              _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
              _jspx_th_bean_005fdefine_005f1.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fmessagesPresent_005f0);
              // /componentes/comunes/funcionesCierre.jsp(10,1) name = toScope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_bean_005fdefine_005f1.setToScope("page");
              // /componentes/comunes/funcionesCierre.jsp(10,1) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_bean_005fdefine_005f1.setId("appGlobalHayMensajes");
              // /componentes/comunes/funcionesCierre.jsp(10,1) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_bean_005fdefine_005f1.setValue("true");
              int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
              if (_jspx_th_bean_005fdefine_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
              _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
              _jspx_th_bean_005fdefine_005f1_reused = true;
            } finally {
              org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fdefine_005f1, _jsp_getInstanceManager(), _jspx_th_bean_005fdefine_005f1_reused);
            }
            appGlobalHayMensajes = (java.lang.String) _jspx_page_context.findAttribute("appGlobalHayMensajes");
            out.write('\n');
            int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_logic_005fmessagesPresent_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005flogic_005fmessagesPresent.reuse(_jspx_th_logic_005fmessagesPresent_005f0);
        _jspx_th_logic_005fmessagesPresent_005f0_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fmessagesPresent_005f0, _jsp_getInstanceManager(), _jspx_th_logic_005fmessagesPresent_005f0_reused);
      }
      out.write('\n');
      //  logic:messagesPresent
      org.apache.struts.taglib.logic.MessagesPresentTag _jspx_th_logic_005fmessagesPresent_005f1 = (org.apache.struts.taglib.logic.MessagesPresentTag) _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.get(org.apache.struts.taglib.logic.MessagesPresentTag.class);
      boolean _jspx_th_logic_005fmessagesPresent_005f1_reused = false;
      try {
        _jspx_th_logic_005fmessagesPresent_005f1.setPageContext(_jspx_page_context);
        _jspx_th_logic_005fmessagesPresent_005f1.setParent(null);
        // /componentes/comunes/funcionesCierre.jsp(12,0) name = message type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
        _jspx_th_logic_005fmessagesPresent_005f1.setMessage("true");
        int _jspx_eval_logic_005fmessagesPresent_005f1 = _jspx_th_logic_005fmessagesPresent_005f1.doStartTag();
        if (_jspx_eval_logic_005fmessagesPresent_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write('\n');
            out.write('	');
            //  bean:define
            org.apache.struts.taglib.bean.DefineTag _jspx_th_bean_005fdefine_005f2 = (org.apache.struts.taglib.bean.DefineTag) _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.get(org.apache.struts.taglib.bean.DefineTag.class);
            boolean _jspx_th_bean_005fdefine_005f2_reused = false;
            try {
              _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
              _jspx_th_bean_005fdefine_005f2.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_logic_005fmessagesPresent_005f1);
              // /componentes/comunes/funcionesCierre.jsp(13,1) name = toScope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_bean_005fdefine_005f2.setToScope("page");
              // /componentes/comunes/funcionesCierre.jsp(13,1) name = id type = java.lang.String reqTime = false required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_bean_005fdefine_005f2.setId("appGlobalHayMensajes");
              // /componentes/comunes/funcionesCierre.jsp(13,1) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
              _jspx_th_bean_005fdefine_005f2.setValue("true");
              int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
              if (_jspx_th_bean_005fdefine_005f2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
                return;
              }
              _005fjspx_005ftagPool_005fbean_005fdefine_0026_005fvalue_005ftoScope_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
              _jspx_th_bean_005fdefine_005f2_reused = true;
            } finally {
              org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_bean_005fdefine_005f2, _jsp_getInstanceManager(), _jspx_th_bean_005fdefine_005f2_reused);
            }
            appGlobalHayMensajes = (java.lang.String) _jspx_page_context.findAttribute("appGlobalHayMensajes");
            out.write('\n');
            int evalDoAfterBody = _jspx_th_logic_005fmessagesPresent_005f1.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_logic_005fmessagesPresent_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return;
        }
        _005fjspx_005ftagPool_005flogic_005fmessagesPresent_0026_005fmessage.reuse(_jspx_th_logic_005fmessagesPresent_005f1);
        _jspx_th_logic_005fmessagesPresent_005f1_reused = true;
      } finally {
        org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fmessagesPresent_005f1, _jsp_getInstanceManager(), _jspx_th_logic_005fmessagesPresent_005f1_reused);
      }
      out.write('\n');
      out.write('\n');
      if (_jspx_meth_logic_005fequal_005f0(_jspx_page_context))
        return;
      out.write('\n');
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_logic_005fequal_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  logic:equal
    org.apache.struts.taglib.logic.EqualTag _jspx_th_logic_005fequal_005f0 = (org.apache.struts.taglib.logic.EqualTag) _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fname.get(org.apache.struts.taglib.logic.EqualTag.class);
    boolean _jspx_th_logic_005fequal_005f0_reused = false;
    try {
      _jspx_th_logic_005fequal_005f0.setPageContext(_jspx_page_context);
      _jspx_th_logic_005fequal_005f0.setParent(null);
      // /componentes/comunes/funcionesCierre.jsp(16,0) name = scope type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fequal_005f0.setScope("page");
      // /componentes/comunes/funcionesCierre.jsp(16,0) name = name type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fequal_005f0.setName("appGlobalHayMensajes");
      // /componentes/comunes/funcionesCierre.jsp(16,0) name = value type = null reqTime = true required = true fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_logic_005fequal_005f0.setValue("true");
      int _jspx_eval_logic_005fequal_005f0 = _jspx_th_logic_005fequal_005f0.doStartTag();
      if (_jspx_eval_logic_005fequal_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("\t<script type=\"text/javascript\">\n");
          out.write("\t\tfunction mostrarCombos(visible) \n");
          out.write("\t\t{\n");
          out.write("\t\t\tvar elementosForma = null;\n");
          out.write("\t\t\tvar forma = null;\n");
          out.write("\t\n");
          out.write("\t\t\tfor (var f = 0; f < document.forms.length; f++) \n");
          out.write("\t\t\t{\t   \n");
          out.write("\t\t\t\tforma = document.forms[f];\n");
          out.write("\t\t\t\telementosForma = forma.elements;\n");
          out.write("\t\t\t\tfor (var i = 0; i < elementosForma.length; i++) \n");
          out.write("\t\t\t\t{\n");
          out.write("\t\t\t\t\tif (elementosForma[i].type == 'select-one') \n");
          out.write("\t\t\t\t\t{\n");
          out.write("\t\t\t\t    \tvar obj = elementosForma[i];\n");
          out.write("\t\t\t\t\t\tif (visible) \n");
          out.write("\t\t\t\t\t\t\tobj.style.display=\"block\";\n");
          out.write("\t\t\t\t\t\telse \n");
          out.write("\t\t\t\t\t\t\tobj.style.display=\"none\";\n");
          out.write("\t\t\t\t   }\n");
          out.write("\t\t\t\t}\n");
          out.write("\t\t\t}\n");
          out.write("\t\t}\n");
          out.write("\t\n");
          out.write("\t\tmostrarCombos(false);\n");
          out.write("\t</script>\n");
          int evalDoAfterBody = _jspx_th_logic_005fequal_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_logic_005fequal_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005flogic_005fequal_0026_005fvalue_005fscope_005fname.reuse(_jspx_th_logic_005fequal_005f0);
      _jspx_th_logic_005fequal_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_logic_005fequal_005f0, _jsp_getInstanceManager(), _jspx_th_logic_005fequal_005f0_reused);
    }
    return false;
  }
}
