package com.visiongc.app.strategos.indicadores.model;

import com.visiongc.app.strategos.categoriasmedicion.StrategosCategoriasService;
import com.visiongc.app.strategos.categoriasmedicion.model.CategoriaMedicion;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;
import com.visiongc.app.strategos.indicadores.model.util.Caracteristica;
import com.visiongc.app.strategos.indicadores.model.util.Naturaleza;
import com.visiongc.app.strategos.indicadores.model.util.PrioridadIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoCorte;
import com.visiongc.app.strategos.indicadores.model.util.TipoFuncionIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoIndicador;
import com.visiongc.app.strategos.indicadores.model.util.TipoMedicion;
import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.organizaciones.model.OrganizacionStrategos;
import com.visiongc.app.strategos.responsables.model.Responsable;
import com.visiongc.app.strategos.unidadesmedida.model.UnidadMedida;
import com.visiongc.commons.util.VgcFormatter;
import com.visiongc.servicio.strategos.calculos.util.CalculoUtil;
import com.visiongc.servicio.strategos.planes.model.util.TipoIndicadorEstado;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Entity;


public class IndicadorAsignarInventario
  implements Serializable
{
  static final long serialVersionUID = 0L;
    
  private Long asignarId;
  private Long indicadorId;
  private Long indicadorInsumoId;

  
  public Long getAsignarId() {
	return asignarId;
  }

  public void setAsignarId(Long asignarId) {
	this.asignarId = asignarId;
  }

  public Long getIndicadorId() {
	return indicadorId;
  }

  public void setIndicadorId(Long indicadorId) {
	this.indicadorId = indicadorId;
  }

  public Long getIndicadorInsumoId() {
	return indicadorInsumoId;
  }

  public void setIndicadorInsumoId(Long indicadorInsumoId) {
	this.indicadorInsumoId = indicadorInsumoId;
  }

  public IndicadorAsignarInventario() {}

  
  public IndicadorAsignarInventario(Long asignarId, Long indicadorId,
		Long indicadorInsumoId) {
	super();
	this.asignarId = asignarId;
	this.indicadorId = indicadorId;
	this.indicadorInsumoId = indicadorInsumoId;
  }
  
}
