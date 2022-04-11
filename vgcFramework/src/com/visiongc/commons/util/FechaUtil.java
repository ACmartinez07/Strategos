package com.visiongc.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Referenced classes of package com.visiongc.commons.util:
//            VgcResourceManager, VgcFormatter

public class FechaUtil
{

    public FechaUtil()
    {
    }

    public static int getAno(Date fecha)
    {
        Calendar fechaCal = Calendar.getInstance();
        fechaCal.setTime(fecha);
        return fechaCal.get(1);
    }

    public static int getAnoActual()
    {
        Calendar fechaCal = Calendar.getInstance();
        fechaCal.setTime(new Date());
        return fechaCal.get(1);
    }

    public static Date convertirStringToDate(String fecha, String patron)
        throws Exception
    {
        if(patron.indexOf("formato.") > -1)
            patron = VgcResourceManager.getResourceApp(patron);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(patron);
        return simpleDateFormat.parse(fecha);
    }

    public static int compareFechasAnoMesDia(Date fecha1, Date fecha2)
    {
        int resultado = 0;
        Calendar calFecha1 = Calendar.getInstance();
        calFecha1.setTime(fecha1);
        Calendar calFecha2 = Calendar.getInstance();
        calFecha2.setTime(fecha2);
        if(calFecha1.get(1) > calFecha2.get(1))
            resultado = 1;
        else
        if(calFecha1.get(1) < calFecha2.get(1))
            resultado = -1;
        else
        if(calFecha1.get(2) > calFecha2.get(2))
            resultado = 1;
        else
        if(calFecha1.get(2) < calFecha2.get(2))
            resultado = -1;
        else
        if(calFecha1.get(5) > calFecha2.get(5))
            resultado = 1;
        else
        if(calFecha1.get(5) < calFecha2.get(5))
            resultado = -1;
        return resultado;
    }

    public static void setHoraInicioDia(Date fecha)
    {
        Calendar resultado = Calendar.getInstance();
        resultado.setTime(fecha);
        resultado.set(10, 0);
        resultado.set(12, 0);
        resultado.set(13, 0);
        resultado.set(14, 0);
        resultado.set(9, 0);
        fecha.setTime(resultado.getTimeInMillis());
    }

    public static void setHoraFinalDia(Date fecha)
    {
        Calendar resultado = Calendar.getInstance();
        resultado.setTime(fecha);
        resultado.set(10, 11);
        resultado.set(12, 59);
        resultado.set(13, 59);
        resultado.set(14, 999);
        resultado.set(9, 1);
        fecha.setTime(resultado.getTimeInMillis());
    }

    public static String dateToStringformatoTS(Date fecha)
    {
        setHoraInicioDia(fecha);
        return VgcFormatter.formatDate(fecha, "yyyy-MM-dd HH:mm:ss a").replace(" AM", "").replace(" PM", "");
    }
}