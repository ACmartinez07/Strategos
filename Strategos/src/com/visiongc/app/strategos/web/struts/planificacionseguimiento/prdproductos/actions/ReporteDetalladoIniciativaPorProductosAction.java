package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.actions;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.iniciativas.model.Iniciativa;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.planificacionseguimiento.StrategosPrdProductosService;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoPK;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProductoPK;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaIniciativaProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaProducto;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.util.PeriodoUtil;
import com.visiongc.commons.report.TablaBasicaPDF;
import com.visiongc.commons.struts.action.VgcReporteBasicoAction;
import com.visiongc.commons.util.FechaUtil;
import com.visiongc.commons.web.util.WebUtil;
import com.visiongc.framework.configuracion.sistema.ConfiguracionPagina;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.MessageResources;

public class ReporteDetalladoIniciativaPorProductosAction extends VgcReporteBasicoAction
{
  protected String agregarTitulo(HttpServletRequest request, MessageResources mensajes)
    throws Exception
  {
    return mensajes.getMessage("reporte.iniciativas.detallado.titulo");
  }

  protected void construirReporte(ActionForm form, HttpServletRequest request, HttpServletResponse response, Document documento)
    throws Exception
  {
    String iniciativaId = request.getParameter("iniciativaId");
    String ano = request.getParameter("ano");
    String periodo = request.getParameter("periodo");

    Font font = new Font(getConfiguracionPagina(request).getCodigoFuente());

    MessageResources mensajes = getResources(request);

    StrategosPrdProductosService strategosPrdProductosService = StrategosServiceFactory.getInstance().openStrategosPrdProductosService();

    TablaBasicaPDF tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
    int[] columnas = new int[2];
    columnas[0] = 30;
    columnas[1] = 70;
    tabla.setAmplitudTabla(100);
    tabla.crearTabla(columnas);

    Iniciativa iniciativa = (Iniciativa)strategosPrdProductosService.load(Iniciativa.class, new Long(iniciativaId));

    tabla.setFormatoFont(font.style());
    tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);

    tabla.setDefaultAlineacionHorizontal();
    Image imagenAlerta = null;
    if (iniciativa != null) {
      OrganizacionStrategos organizacion = iniciativa.getOrganizacion();
      tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.organizacion"));
      tabla.agregarCelda(organizacion.getNombre());

      tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.iniciativa"));
      tabla.agregarCelda(iniciativa.getNombre());

      if (iniciativa.getResponsableCargarEjecutado() != null) {
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.responsablecargarejecutado"));
        tabla.agregarCelda(iniciativa.getResponsableCargarEjecutado().getNombre());
      }
      if (iniciativa.getResponsableFijarMeta() != null) {
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.responsablefijarmeta"));
        tabla.agregarCelda(iniciativa.getResponsableFijarMeta().getNombre());
      }
      if (iniciativa.getResponsableCargarMeta() != null) {
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.responsablecargarmeta"));
        tabla.agregarCelda(iniciativa.getResponsableCargarMeta().getNombre());
      }
      if (iniciativa.getResponsableLograrMeta() != null) {
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.responsablelograrmeta"));
        tabla.agregarCelda(iniciativa.getResponsableLograrMeta().getNombre());
      }
      if (iniciativa.getResponsableSeguimiento() != null) {
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.responsableseguimiento"));
        tabla.agregarCelda(iniciativa.getResponsableSeguimiento().getNombre());
      }

      tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.enteejecutor"));
      tabla.agregarCelda(iniciativa.getEnteEjecutor());

      String[] orden = new String[2];
      String[] tipoOrden = new String[2];
      orden[0] = "pk.ano";
      tipoOrden[0] = "desc";
      orden[1] = "pk.periodo";
      tipoOrden[1] = "desc";

      List seguimientos = strategosPrdProductosService.getSeguimientosPorIniciativa(iniciativa.getIniciativaId(), orden, tipoOrden);
      if (seguimientos.size() > 0) {
        PrdSeguimiento seguimiento = (PrdSeguimiento)seguimientos.get(0);
        if ((ano != null) && (periodo != null) && (!ano.equals("")) && (!periodo.equals(""))) {
          for (Iterator iter = seguimientos.iterator(); iter.hasNext(); ) {
            PrdSeguimiento seg = (PrdSeguimiento)iter.next();
            if ((seg.getPk().getAno().toString().equals(ano)) && (seg.getPk().getPeriodo().toString().equals(periodo))) {
              seguimiento = seg;
              tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.periodoano"));
              tabla.agregarCelda(PeriodoUtil.convertirPeriodoToTexto(Integer.parseInt(periodo), iniciativa.getFrecuencia().byteValue(), Integer.parseInt(ano)));
              break;
            }
          }
        }
        if (seguimiento.getAlerta() != null) {
          tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.estadocondicion"));
          imagenAlerta = null;
          if (seguimiento.getAlerta().equals(AlertaIniciativaProducto.getAlertaEnEsperaComienzo()))
            imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif")));
          else if (seguimiento.getAlerta().equals(AlertaIniciativaProducto.getAlertaVerde()))
            imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
          else if (seguimiento.getAlerta().equals(AlertaIniciativaProducto.getAlertaAmarilla()))
            imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaAmarilla.gif")));
          else if (seguimiento.getAlerta().equals(AlertaIniciativaProducto.getAlertaRoja())) {
            imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
          }
          tabla.agregarCelda(imagenAlerta);
        }

        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.fechaseguimiento"));
        tabla.agregarCelda(seguimiento.getFechaFormateada());

        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.seguimiento"));
        tabla.agregarCelda(seguimiento.getSeguimiento());
      }

      documento.add(tabla.getTabla());

      List productos = strategosPrdProductosService.getProductosPorIniciativa(new Long(iniciativaId));

      if (productos.size() > 0)
      {
        documento.newPage();

        agregarSubTitulo(documento, getConfiguracionPagina(request), mensajes.getMessage("reporte.iniciativas.detallado.planejecucionprevisto"));

        tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
        columnas = new int[4];
        columnas[0] = 60;
        columnas[1] = 15;
        columnas[2] = 15;
        columnas[3] = 10;
        tabla.setAmplitudTabla(100);
        tabla.crearTabla(columnas);

        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.actividad"));
        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.fechaentrega"));
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.fechareprogramada"));
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.estadoentrega"));

        for (Iterator iter = productos.iterator(); iter.hasNext(); ) {
          PrdProducto producto = (PrdProducto)iter.next();

          tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
          tabla.agregarCelda(producto.getNombre());
          tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
          Set seguimientosProductos = producto.getSeguimientosProducto();

          if (seguimientosProductos.size() > 0) {
            PrdSeguimientoProducto seguimientoProducto = null;
            Iterator iterSeg = null;
            if ((ano != null) && (periodo != null) && (!ano.equals("")) && (!periodo.equals("")))
              iterSeg = seguimientosProductos.iterator();
            if (iterSeg != null)
            {
	            while (true) 
	            {
	              PrdSeguimientoProducto segProducto = (PrdSeguimientoProducto)iterSeg.next();
	              if ((segProducto.getPk().getAno().toString().equals(ano)) && (segProducto.getPk().getPeriodo().toString().equals(periodo))) {
	                seguimientoProducto = segProducto;
	              }
	              else
	              {
	                if (iterSeg.hasNext())
	                {
	                    seguimientoProducto = (PrdSeguimientoProducto)seguimientosProductos.iterator().next();
	                	continue;
	                }
	                break;
	              }
	            }
            }
            if (seguimientoProducto != null) {
              tabla.agregarCelda(seguimientoProducto.getFechaInicioFormateada());
              if (FechaUtil.compareFechasAnoMesDia(seguimientoProducto.getFechaInicio(), seguimientoProducto.getFechaFin()) != 0)
                tabla.agregarCelda(seguimientoProducto.getFechaFinFormateada());
              else {
                tabla.agregarCelda(" ");
              }
              imagenAlerta = null;
              if (seguimientoProducto.getAlerta().equals(AlertaProducto.getAlertaEnEsperaComienzo()))
                imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif")));
              else if (seguimientoProducto.getAlerta().equals(AlertaProducto.getAlertaEntregado()))
                imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
              else if (seguimientoProducto.getAlerta().equals(AlertaProducto.getAlertaNoEntregado())) {
                imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
              }
              tabla.agregarCelda(imagenAlerta);
            } else {
              tabla.agregarCelda(producto.getFechaInicioFormateada());
              tabla.agregarCelda(" ");
              imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif")));
              tabla.agregarCelda(imagenAlerta);
            }
          } else {
            tabla.agregarCelda(producto.getFechaInicioFormateada());
            tabla.agregarCelda(" ");
            imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif")));
            tabla.agregarCelda(imagenAlerta);
          }
        }

        documento.add(tabla.getTabla());

        tabla = new TablaBasicaPDF(getConfiguracionPagina(request), request);
        columnas = new int[2];
        columnas[0] = 8;
        columnas[1] = 92;
        tabla.setAmplitudTabla(100);
        tabla.setAnchoBorde(0);
        tabla.setAnchoBordeCelda(0);
        tabla.crearTabla(columnas);

        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.alerta"));
        tabla.agregarCelda(mensajes.getMessage("reporte.iniciativas.detallado.significado"));
        imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaBlanca.gif")));
        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
        tabla.agregarCelda(imagenAlerta);
        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
        tabla.agregarCelda(AlertaProducto.getNombre(AlertaProducto.getAlertaEnEsperaComienzo().byteValue()));
        imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaVerde.gif")));
        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
        tabla.agregarCelda(imagenAlerta);
        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
        tabla.agregarCelda(AlertaProducto.getNombre(AlertaProducto.getAlertaEntregado().byteValue()));
        imagenAlerta = Image.getInstance(new URL(WebUtil.getUrl(request, "/paginas/strategos/indicadores/imagenes/alertaRoja.gif")));
        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_CENTER);
        tabla.agregarCelda(imagenAlerta);
        tabla.setAlineacionHorizontal(TablaBasicaPDF.H_ALINEACION_LEFT);
        tabla.agregarCelda(AlertaProducto.getNombre(AlertaProducto.getAlertaNoEntregado().byteValue()));

        documento.add(tabla.getTabla());
      }

    }

    strategosPrdProductosService.close();
  }
}