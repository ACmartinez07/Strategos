package com.visiongc.app.strategos.model.util;

import com.visiongc.commons.util.VgcMessageResources;
import java.util.List;

public class Revision
{
  private Byte revision;
  private String nombre;
  
  public Revision() {}
  
  public Byte getRevision()
  {
    return revision;
  }
  
  public void setRevision(Byte revision) {
    this.revision = revision;
  }
  
  public String getNombre() {
    return nombre;
  }
  
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  
  public static List getRevisiones() {
    return getRevisiones(null);
  }
  
  public static List getRevisiones(VgcMessageResources messageResources)
  {
	  if (messageResources == null) {
	      messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
	    }
	    
	  List revisiones = new java.util.ArrayList();
      Revision revision = new Revision();
      revision.revision = new Byte((byte)0);
      revision.nombre = messageResources.getResource("revision.original");
      revisiones.add(revision);
      revision = new Revision();
      revision.revision = new Byte((byte)1);
      revision.nombre = messageResources.getResource("revision.primera");
      revisiones.add(revision);
      revision = new Revision();
      revision.revision = new Byte((byte)2);
      revision.nombre = messageResources.getResource("revision.segunda");
      revisiones.add(revision);
      revision = new Revision();
      revision.revision = new Byte((byte)3);
      revision.nombre = messageResources.getResource("revision.tercera");
      revisiones.add(revision);
      revision = new Revision();
      revision.revision = new Byte((byte)4);
      revision.nombre = messageResources.getResource("revision.cuarta");
      revisiones.add(revision);
      revision = new Revision();
      revision.revision = new Byte((byte)5);
      revision.nombre = messageResources.getResource("revision.quinta");
      revisiones.add(revision);
      revision = new Revision();
      revision.revision = new Byte((byte)6);
      revision.nombre = messageResources.getResource("revision.sexta");
      revisiones.add(revision);
      revision = new Revision();
      revision.revision = new Byte((byte)7);
      revision.nombre = messageResources.getResource("revision.septima");
      revisiones.add(revision);
      revision = new Revision();
      revision.revision = new Byte((byte)8);
      revision.nombre = messageResources.getResource("revision.octava");
      revisiones.add(revision);
      revision = new Revision();
      revision.revision = new Byte((byte)9);
      revision.nombre = messageResources.getResource("revision.novena");
      revisiones.add(revision);
      revision = new Revision();
      revision.revision = new Byte((byte)10);
      revision.nombre = messageResources.getResource("revision.decima");
      revisiones.add(revision);
      return revisiones;
  }
  
  public static String getNombre(byte revision) {
    String nombre = "";
    
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    
    if (revision == 0) {
      nombre = messageResources.getResource("revision.original");
    } else if (revision == 1) {
      nombre = messageResources.getResource("revision.primera");
    } else if (revision == 2) {
      nombre = messageResources.getResource("revision.segunda");
    } else if (revision == 3) {
      nombre = messageResources.getResource("revision.tercera");
    } else if (revision == 4) {
      nombre = messageResources.getResource("revision.cuarta");
    } else if (revision == 5) {
      nombre = messageResources.getResource("revision.quinta");
    } else if (revision == 6) {
      nombre = messageResources.getResource("revision.sexta");
    } else if (revision == 7) {
      nombre = messageResources.getResource("revision.septima");
    } else if (revision == 8) {
      nombre = messageResources.getResource("revision.octava");
    } else if (revision == 9) {
      nombre = messageResources.getResource("revision.novena");
    } else if (revision == 10) {
      nombre = messageResources.getResource("revision.decima");
    }
    
    return nombre;
  }
  
  public static Revision getRevision(byte inRevision)
  {
    Revision revision = null;
    
    VgcMessageResources messageResources = com.visiongc.commons.util.VgcResourceManager.getMessageResources("Strategos");
    
    switch (inRevision) {
    case 0: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)0));
      revision.setNombre(messageResources.getResource("revision.original"));
      break;
    case 1: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)1));
      revision.setNombre(messageResources.getResource("revision.primera"));
      break;
    case 2: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)2));
      revision.setNombre(messageResources.getResource("revision.segunda"));
      break;
    case 3: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)3));
      revision.setNombre(messageResources.getResource("revision.tercera"));
      break;
    case 4: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)4));
      revision.setNombre(messageResources.getResource("revision.cuarta"));
      break;
    case 5: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)5));
      revision.setNombre(messageResources.getResource("revision.quinta"));
      break;
    case 6: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)6));
      revision.setNombre(messageResources.getResource("revision.sexta"));
      break;
    case 7: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)7));
      revision.setNombre(messageResources.getResource("revision.septima"));
      break;
    case 8: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)8));
      revision.setNombre(messageResources.getResource("revision.octava"));
      break;
    case 9: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)9));
      revision.setNombre(messageResources.getResource("revision.novena"));
      break;
    case 10: 
      revision = new Revision();
      revision.setRevision(new Byte((byte)10));
      revision.setNombre(messageResources.getResource("revision.decima"));
    }
    
    return revision;
  }
}
