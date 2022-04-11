package com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.util;

import com.visiongc.app.strategos.planificacionseguimiento.model.PrdProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimiento;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.PrdSeguimientoProductoPK;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.AlertaProducto;
import com.visiongc.app.strategos.planificacionseguimiento.model.util.ComparatorSeguimientosProductos;
import com.visiongc.app.strategos.web.struts.planificacionseguimiento.prdproductos.forms.RegistrarSeguimientoForm;
import com.visiongc.commons.util.PaginaLista;
import com.visiongc.commons.util.VgcFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class RegistroSeguimientoUtil
{
  public static void setSeguimientosProductos(RegistrarSeguimientoForm registrarSeguimientoForm, PrdSeguimiento seguimiento)
  {
    String seguimientosProductos = "";
    int index = 0;

    if (seguimiento == null)
    {
      ArrayList listaSeguimientosProductos = new ArrayList();
      for (Iterator iter = registrarSeguimientoForm.getPaginaProductos().getLista().iterator(); iter.hasNext(); ) {
        PrdProducto producto = (PrdProducto)iter.next();
        index++;

        if (index == 1) {
          registrarSeguimientoForm.setProductoSeleccionadoId(producto.getProductoId());
        }

        PrdSeguimientoProducto seguimientoProducto = new PrdSeguimientoProducto();

        seguimientoProducto.setPk(new PrdSeguimientoProductoPK());
        seguimientoProducto.getPk().setProductoId(producto.getProductoId());
        seguimientoProducto.setAlerta(AlertaProducto.getAlertaEnEsperaComienzo());
        seguimientoProducto.setFechaInicio(producto.getFechaInicio());
        seguimientoProducto.setFechaFin(producto.getFechaInicio());
        PrdSeguimientoProducto seguimientoProductoUltimo = null;
        boolean haySegPrd = false;
        for (Iterator iterSeg = registrarSeguimientoForm.getPaginaSeguimientos().getLista().iterator(); iterSeg.hasNext(); ) {
          PrdSeguimiento seg = (PrdSeguimiento)iterSeg.next();
          for (Iterator iterSegPrd = seg.getSeguimientosProductos().iterator(); iterSegPrd.hasNext(); ) {
            PrdSeguimientoProducto segPrd = (PrdSeguimientoProducto)iterSegPrd.next();
            if (segPrd.getPk().getProductoId().equals(seguimientoProducto.getPk().getProductoId())) {
              seguimientoProductoUltimo = segPrd;
              haySegPrd = true;
              break;
            }
          }
          if (haySegPrd) {
            break;
          }
        }
        if (seguimientoProductoUltimo != null) {
          seguimientoProducto.setFechaFin(seguimientoProductoUltimo.getFechaFin());
          seguimientoProducto.setAlerta(seguimientoProductoUltimo.getAlerta());
        }

        seguimientoProducto.setProducto(producto);
        listaSeguimientosProductos.add(seguimientoProducto);
        seguimientosProductos = seguimientosProductos + "productoId" + producto.getProductoId() + "alerta" + seguimientoProducto.getAlerta().toString() + "programado" + VgcFormatter.formatearFecha(seguimientoProducto.getFechaInicio(), "formato.fecha.corta") + "reprogramado" + VgcFormatter.formatearFecha(seguimientoProducto.getFechaFin(), "formato.fecha.corta") + "#";
      }
      seguimientosProductos = seguimientosProductos.substring(0, seguimientosProductos.length() - "#".length());
      PaginaLista paginaSeguimientosProductos = new PaginaLista();
      paginaSeguimientosProductos.setNroPagina(1);
      paginaSeguimientosProductos.setOrden("productoId");
      paginaSeguimientosProductos.setLista(listaSeguimientosProductos);
      paginaSeguimientosProductos.setTamanoPagina(paginaSeguimientosProductos.getLista().size());
      paginaSeguimientosProductos.setTotal(paginaSeguimientosProductos.getLista().size());
      paginaSeguimientosProductos.setTipoOrden("ASC");
      paginaSeguimientosProductos.setTamanoSetPaginas(5);
      registrarSeguimientoForm.setPaginaSeguimientosProductos(paginaSeguimientosProductos);
    } else {
      TreeSet seguimientosOrdenados = new TreeSet(new ComparatorSeguimientosProductos());
      for (Iterator iter = seguimiento.getSeguimientosProductos().iterator(); iter.hasNext(); ) {
        PrdSeguimientoProducto seguimientoProducto = (PrdSeguimientoProducto)iter.next();
        seguimientosOrdenados.add(seguimientoProducto);
      }
      seguimiento.setSeguimientosProductos(seguimientosOrdenados);
      for (Iterator iter = seguimiento.getSeguimientosProductos().iterator(); iter.hasNext(); ) {
        PrdSeguimientoProducto seguimientoProducto = (PrdSeguimientoProducto)iter.next();
        index++;

        if (index == 1) {
          registrarSeguimientoForm.setProductoSeleccionadoId(seguimientoProducto.getPk().getProductoId());
        }
        seguimientosProductos = seguimientosProductos + "productoId" + seguimientoProducto.getPk().getProductoId() + "alerta" + seguimientoProducto.getAlerta().toString() + "programado" + VgcFormatter.formatearFecha(seguimientoProducto.getFechaInicio(), "formato.fecha.corta") + "reprogramado" + VgcFormatter.formatearFecha(seguimientoProducto.getFechaFin(), "formato.fecha.corta") + "#";
      }
      seguimientosProductos = seguimientosProductos.substring(0, seguimientosProductos.length() - "#".length());

      PaginaLista paginaSeguimientosProductos = new PaginaLista();
      paginaSeguimientosProductos.setNroPagina(1);
      paginaSeguimientosProductos.setOrden("productoId");
      ArrayList listaSeguimientosProductos = new ArrayList();
      listaSeguimientosProductos.addAll(seguimiento.getSeguimientosProductos());
      paginaSeguimientosProductos.setLista(listaSeguimientosProductos);
      paginaSeguimientosProductos.setTamanoPagina(paginaSeguimientosProductos.getLista().size());
      paginaSeguimientosProductos.setTotal(paginaSeguimientosProductos.getLista().size());
      paginaSeguimientosProductos.setTipoOrden("ASC");
      paginaSeguimientosProductos.setTamanoSetPaginas(5);
      registrarSeguimientoForm.setPaginaSeguimientosProductos(paginaSeguimientosProductos);
    }
    registrarSeguimientoForm.setSeguimientosProductos(seguimientosProductos);
  }
}