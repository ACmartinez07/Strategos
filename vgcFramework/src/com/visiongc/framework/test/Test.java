

package com.visiongc.framework.test;

import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.ObjetoSistema;
import com.visiongc.framework.model.Permiso;
import com.visiongc.framework.util.ObjetosSistema;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Set;

public class Test
{

    public Test()
    {
    }

    public static void main(String args[])
    {
        FrameworkService fs = FrameworkServiceFactory.getInstance().openFrameworkService();
        Set permisos = fs.getPermisosRoot(true);
        Permiso per;
        for(Iterator iter = permisos.iterator(); iter.hasNext(); System.out.println((new StringBuilder("Grupo: ")).append(per.getPermisoId()).toString()))
            per = (Permiso)iter.next();

        fs.close();
        ObjetoSistema os = ObjetosSistema.getInstance().getObjetoSistema("INDICADOR");
        System.out.println((new StringBuilder(String.valueOf(os.getArticuloPlural()))).append(" ").append(os.getNombreSingular()).toString());
        System.out.println("Fin de Prueba");
    }
}