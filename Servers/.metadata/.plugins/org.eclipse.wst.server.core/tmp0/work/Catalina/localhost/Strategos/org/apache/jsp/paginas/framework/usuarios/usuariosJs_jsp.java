/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.5.57
 * Generated at: 2022-03-30 15:38:14 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.paginas.framework.usuarios;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class usuariosJs_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/WEB-INF/tld/struts-html.tld", Long.valueOf(1646829839000L));
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

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005faction_005fnobody;

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
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005faction_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005faction_005fnobody.release();
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
      out.write("<!-- Modificado por: Kerwin Arias (02/06/2012) -->\n");
      out.write("\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("\t\n");
      out.write("\tfunction abrirSelectorUsuarios(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, organizacionId, mostrarAdministradores, funcionCierre) \n");
      out.write("\t{\n");
      out.write("\t\tif (organizacionId == null) \n");
      out.write("\t\t\torganizacionId = '';\n");
      out.write("\t\tif (mostrarAdministradores == null) \n");
      out.write("\t\t\tmostrarAdministradores = '';\n");
      out.write("\t\tif (funcionCierre == null) \n");
      out.write("\t\t\tfuncionCierre = '';\n");
      out.write("\t\t\n");
      out.write("\t\tabrirVentanaModal('");
      if (_jspx_meth_html_005frewrite_005f0(_jspx_page_context))
        return;
      out.write("?nombreForma=' + nombreForma \n");
      out.write("\t\t\t+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto \n");
      out.write("\t\t\t+ '&seleccionados=' + seleccionados + '&organizacionId=' + organizacionId \n");
      out.write("\t\t\t+ '&mostrarAdministradores=' + mostrarAdministradores + '&funcionCierre=' + funcionCierre, 'seleccionarUsuarios', '600', '400');\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("\t// se añade opcion de seleccion para responsables\n");
      out.write("\tfunction abrirSelectorUsuariosResponsables(nombreForma, nombreCampoValor, nombreCampoOculto, seleccionados, desdeResponsable, funcionCierre) \n");
      out.write("\t{\n");
      out.write("\t\tif (desdeResponsable == null) \n");
      out.write("\t\t\tdesdeResponsable = 'false'; \n");
      out.write("\t\tif (funcionCierre == null) \n");
      out.write("\t\t\tfuncionCierre = '';\n");
      out.write("\t\t\n");
      out.write("\t\tabrirVentanaModal('");
      if (_jspx_meth_html_005frewrite_005f1(_jspx_page_context))
        return;
      out.write("?nombreForma=' + nombreForma \n");
      out.write("\t\t\t+ '&nombreCampoValor=' + nombreCampoValor + '&nombreCampoOculto=' + nombreCampoOculto \n");
      out.write("\t\t\t+ '&seleccionados=' + seleccionados + '&desdeResponsable=' + desdeResponsable + '&funcionCierre=' + funcionCierre, 'seleccionarUsuarios', '800', '600');\n");
      out.write("\t}\n");
      out.write("\t\n");
      out.write("</script>");
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

  private boolean _jspx_meth_html_005frewrite_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f0 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005faction_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    boolean _jspx_th_html_005frewrite_005f0_reused = false;
    try {
      _jspx_th_html_005frewrite_005f0.setPageContext(_jspx_page_context);
      _jspx_th_html_005frewrite_005f0.setParent(null);
      // /paginas/framework/usuarios/usuariosJs.jsp(17,21) name = action type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005frewrite_005f0.setAction("/framework/usuarios/seleccionarUsuarios");
      int _jspx_eval_html_005frewrite_005f0 = _jspx_th_html_005frewrite_005f0.doStartTag();
      if (_jspx_th_html_005frewrite_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005faction_005fnobody.reuse(_jspx_th_html_005frewrite_005f0);
      _jspx_th_html_005frewrite_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005frewrite_005f0, _jsp_getInstanceManager(), _jspx_th_html_005frewrite_005f0_reused);
    }
    return false;
  }

  private boolean _jspx_meth_html_005frewrite_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  html:rewrite
    org.apache.struts.taglib.html.RewriteTag _jspx_th_html_005frewrite_005f1 = (org.apache.struts.taglib.html.RewriteTag) _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005faction_005fnobody.get(org.apache.struts.taglib.html.RewriteTag.class);
    boolean _jspx_th_html_005frewrite_005f1_reused = false;
    try {
      _jspx_th_html_005frewrite_005f1.setPageContext(_jspx_page_context);
      _jspx_th_html_005frewrite_005f1.setParent(null);
      // /paginas/framework/usuarios/usuariosJs.jsp(31,21) name = action type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_html_005frewrite_005f1.setAction("/framework/usuarios/seleccionarUsuarios");
      int _jspx_eval_html_005frewrite_005f1 = _jspx_th_html_005frewrite_005f1.doStartTag();
      if (_jspx_th_html_005frewrite_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
      _005fjspx_005ftagPool_005fhtml_005frewrite_0026_005faction_005fnobody.reuse(_jspx_th_html_005frewrite_005f1);
      _jspx_th_html_005frewrite_005f1_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_html_005frewrite_005f1, _jsp_getInstanceManager(), _jspx_th_html_005frewrite_005f1_reused);
    }
    return false;
  }
}
