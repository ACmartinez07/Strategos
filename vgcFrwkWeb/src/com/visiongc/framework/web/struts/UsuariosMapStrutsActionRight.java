package com.visiongc.framework.web.struts;

import com.visiongc.commons.MapStrutsActionRight;
import com.visiongc.commons.struts.StrutsActionRight;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import java.util.HashMap;
import java.util.Map;



public class UsuariosMapStrutsActionRight
  implements MapStrutsActionRight
{
  private static UsuariosMapStrutsActionRight instance = null;
  private HashMap<String, StrutsActionRight> mapa = null;
  

  public UsuariosMapStrutsActionRight()
  {
    instance = this;
    

    mapa = new HashMap();
    


    StrutsActionRight actionRight = new StrutsActionRight("framework.usuarios.gestionarusuarios", false, false, "USUARIO");
    mapa.put("framework.usuarios.gestionarusuarios", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.seleccionarusuarios", true, false, "");
    mapa.put("framework.usuarios.seleccionarusuarios", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.crearusuario", false, false, "USUARIO_ADD");
    mapa.put("framework.usuarios.crearusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.modificarusuario", false, false, "USUARIO_EDIT");
    mapa.put("framework.usuarios.modificarusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.copiarusuario", false, false, "USUARIO_ADD");
    mapa.put("framework.usuarios.copiarusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.propiedadesusuario", false, false, "USUARIO_PROPERTIES");
    mapa.put("framework.usuarios.propiedadesusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.guardarusuario", false, false, "");
    mapa.put("framework.usuarios.guardarusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.cancelarguardarusuario", false, false, "");
    mapa.put("framework.usuarios.cancelarguardarusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.cambiarclaveusuario", true, false, "USUARIO_CLAVE");
    mapa.put("framework.usuarios.cambiarclaveusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.eliminarusuario", false, false, "USUARIO_DELETE");
    mapa.put("framework.usuarios.eliminarusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.guardarclaveusuario", true, false, "");
    mapa.put("framework.usuarios.guardarclaveusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.cancelarguardarclaveusuario", true, false, "");
    mapa.put("framework.usuarios.cancelarguardarclaveusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.reporteusuariodetallado", true, false, "USUARIO_PRINT");
    mapa.put("framework.usuarios.reporteusuariodetallado", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.reporteusuariodetalladoexcel", true, false, "USUARIO_PRINT");
    mapa.put("framework.usuarios.reporteusuariodetalladoexcel", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.reporteusuariosresumidoexcel", true, false, "USUARIO_PRINT");
    mapa.put("framework.usuarios.reporteusuariosresumidoexcel", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.reporteusuariosorganizacion", true, false, "USUARIO_PRINT");
    mapa.put("framework.usuarios.reporteusuariosorganizacion", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.reporteusuariosorganizacionpdf", true, false, "USUARIO_PRINT");
    mapa.put("framework.usuarios.reporteusuariosorganizacionpdf", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.reporteusuariosorganizacionexcel", true, false, "USUARIO_PRINT");
    mapa.put("framework.usuarios.reporteusuariosorganizacionexcel", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.activarusuarios", true, false, "USUARIO_ACTIVAR");
    mapa.put("framework.usuarios.activarusuarios", actionRight);

    actionRight = new StrutsActionRight("framework.usuarios.reporteusuariosresumido", true, false, "USUARIO_PRINT");
    mapa.put("framework.usuarios.reporteusuariosresumido", actionRight);
    
    actionRight = new StrutsActionRight("framework.grupos.gestionargrupos", false, false, "GRUPO");
    mapa.put("framework.grupos.gestionargrupos", actionRight);
    
    actionRight = new StrutsActionRight("framework.grupos.creargrupo", false, false, "GRUPO_ADD");
    mapa.put("framework.grupos.creargrupo", actionRight);
    
    actionRight = new StrutsActionRight("framework.grupos.modificargrupo", false, false, "GRUPO_EDIT");
    mapa.put("framework.grupos.modificargrupo", actionRight);
    
    actionRight = new StrutsActionRight("framework.grupos.copiargrupo", false, false, "GRUPO_ADD");
    mapa.put("framework.grupos.copiargrupo", actionRight);
    
    actionRight = new StrutsActionRight("framework.grupos.guardargrupo", false, false, "");
    mapa.put("framework.grupos.guardargrupo", actionRight);
    
    actionRight = new StrutsActionRight("framework.grupos.cancelarguardargrupo", false, false, "");
    mapa.put("framework.grupos.cancelarguardargrupo", actionRight);
    
    actionRight = new StrutsActionRight("framework.grupos.propiedadesgrupo", false, false, "GRUPO_PROPERTIES");
    mapa.put("framework.grupos.propiedadesgrupo", actionRight);
    
    actionRight = new StrutsActionRight("framework.grupos.eliminargrupo", false, false, "GRUPO_DELETE");
    mapa.put("framework.grupos.eliminargrupo", actionRight);
    
    actionRight = new StrutsActionRight("framework.grupos.reportegrupos", true, false, "GRUPO_PRINT");
    mapa.put("framework.grupos.reportegrupos", actionRight);
    
    actionRight = new StrutsActionRight("framework.sesionesusuario.gestionarsesionesusuario", false, false, "USUARIO");
    mapa.put("framework.sesionesusuario.gestionarsesionesusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.sesionesusuario.eliminarbloqueosporsesionusuario", false, false, "USUARIO");
    mapa.put("framework.sesionesusuario.eliminarbloqueosporsesionusuario", actionRight);
    
    actionRight = new StrutsActionRight("framework.bloqueos.gestionarbloqueos", false, false, "USUARIO");
    mapa.put("framework.bloqueos.gestionarbloqueos", actionRight);
    
    actionRight = new StrutsActionRight("framework.bloqueos.gestionarbloqueoslectura", false, false, "USUARIO");
    mapa.put("framework.bloqueos.gestionarbloqueoslectura", actionRight);
    
    actionRight = new StrutsActionRight("framework.bloqueos.eliminarbloqueo", false, false, "USUARIO");
    mapa.put("framework.bloqueos.eliminarbloqueo", actionRight);
    
    actionRight = new StrutsActionRight("framework.iniciosesion.configurariniciosesion", false, false, "CONFIGURACION_SET");
    mapa.put("framework.iniciosesion.configurariniciosesion", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.reportegrupo", true, false, null);
    mapa.put("framework.usuarios.reportegrupo", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.reportegrupopdf", true, false, null);
    mapa.put("framework.usuarios.reportegrupopdf", actionRight);
    
    actionRight = new StrutsActionRight("framework.usuarios.reportegrupoxls", true, false, null);
    mapa.put("framework.usuarios.reportegrupoxls", actionRight);
  }
  
  public static MapStrutsActionRight getInstance()
  {
    if (instance == null)
    {
      try
      {
        new UsuariosMapStrutsActionRight();
      }
      catch (Exception e) {
        throw new ChainedRuntimeException("El mapeo de acciones a permisos no est� configurado correctamente.", e);
      }
    }
    
    return instance;
  }
  
  public Map<String, StrutsActionRight> getMapa()
  {
    return mapa;
  }
}
