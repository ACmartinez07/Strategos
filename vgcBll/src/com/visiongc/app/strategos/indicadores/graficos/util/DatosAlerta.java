package com.visiongc.app.strategos.indicadores.graficos.util;

import com.visiongc.app.strategos.indicadores.model.util.AlertaIndicador;

public class DatosAlerta
{
  private final String ALERTA_NO_DEFINIBLE = "alertaBlanca.gif";
  
  private final String ALERTA_ROJA = "alertaRoja.gif";
  
  private final String ALERTA_INALTERADA = "alertaInalterada.gif";
  
  private final String ALERTA_VERDE = "alertaVerde.gif";
  
  private final String ALERTA_AMARILLA = "alertaAmarilla.gif";
  private String nombreArchivo;
  private Byte alerta;
  
  public DatosAlerta() {}
  
  public String getNombreArchivo() { return nombreArchivo; }
  
  public Byte getAlerta()
  {
    return alerta;
  }
  
  public void setAlerta(Byte alerta) {
    this.alerta = alerta;
    
    if (alerta == AlertaIndicador.getAlertaNoDefinible()) {
      nombreArchivo = "alertaBlanca.gif";
    } else if (alerta.byteValue() == AlertaIndicador.getAlertaRoja().byteValue()) {
      nombreArchivo = "alertaRoja.gif";
    } else if (alerta.byteValue() == AlertaIndicador.getAlertaInalterada().byteValue()) {
      nombreArchivo = "alertaInalterada.gif";
    } else if (alerta.byteValue() == AlertaIndicador.getAlertaVerde().byteValue()) {
      nombreArchivo = "alertaVerde.gif";
    } else if (alerta.byteValue() == AlertaIndicador.getAlertaAmarilla().byteValue()) {
      nombreArchivo = "alertaAmarilla.gif";
    }
  }
}
