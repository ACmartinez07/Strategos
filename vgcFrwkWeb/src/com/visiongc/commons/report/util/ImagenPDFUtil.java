package com.visiongc.commons.report.util;

import com.lowagie.text.Image;

public class ImagenPDFUtil { public ImagenPDFUtil() {}
  
  private static float RELACION_PUNTOS_CM = 60.0F;
  
  private static float RELACION_PUNTOS_PULGADAS = RELACION_PUNTOS_CM * 2.54F;
  
  private static float TAMANO_ESCALA = 72.0F / RELACION_PUNTOS_PULGADAS;
  
  public static void setEscalaImagen(Image imagen) {
    imagen.scaleAbsolute(imagen.width() * TAMANO_ESCALA, imagen.height() * TAMANO_ESCALA);
  }
  
  public static float getAltoAjustadoPorEscala(Image imagen) {
    return imagen.height() * TAMANO_ESCALA;
  }
  
  public static float getAnchoAjustadoPorEscala(Image imagen) {
    return imagen.width() * TAMANO_ESCALA;
  }
}
