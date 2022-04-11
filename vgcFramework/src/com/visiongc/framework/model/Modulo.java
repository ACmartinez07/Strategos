package com.visiongc.framework.model;

import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.modulo.ModuloService;
import java.io.Serializable;

public class Modulo
    implements Serializable
{
    public static class ModuloType
    {
        public static class Actividades
        {

            public static final String Actividades = "EA29991D-388C-44B4-83B4-37E714709618";

            public Actividades()
            {
            }
        }

        public static class CodigoEnlace
        {

            public static final String CodigoEnlace = "B069CF20-1E8F-4E5F-BDE1-9C28C776511A";

            public CodigoEnlace()
            {
            }
        }

        public static class Indicador
        {
            public static class Reporte
            {

                public static final String ComiteEjecutivo = "2277419F-4FF0-4CC8-A602-D2619A6AFC47";

                public Reporte()
                {
                }
            }


            public Indicador()
            {
            }
        }

        public static class Informe
        {
            public static class Tipos
            {

                public static final String Cualitativos = "476267AA-75D6-4B48-B283-7712A083CA87";
                public static final String Ejecutivos = "3B61E06C-5186-47F2-A7E3-7A1BB2C1079F";
                public static final String Eventos = "56B94A4F-7B53-446A-A84B-5CB78C19905A";
                public static final String Noticias = "6366B78D-5948-4FA3-BE66-289E5D9ACA27";

                public Tipos()
                {
                }
            }


            public Informe()
            {
            }
        }

        public static class Iniciativas
        {

            public static final String Iniciativas = "C5DD8F17-963B-4175-A9A0-7D0D3754DFC0";

            public Iniciativas()
            {
            }
        }

        public static class Plan
        {

            public static final String Plan = "0AE884CC-DBE5-42BD-8168-2240A0780E69";

            public Plan()
            {
            }
        }

        public static class Portafolio
        {

            public static final String Portafolio = "128AD7FB-C2BD-478B-BA38-E3E5BC0CD2F2";

            public Portafolio()
            {
            }
        }

        public static class PresentacionEjecutiva
        {

            public static final String PresentacionEjecutiva = "53B93263-A951-492F-8E6E-633490DB5C78";

            public PresentacionEjecutiva()
            {
            }
        }

        public static class Problema
        {

            public static final String Problema = "5D776FA5-9445-427D-8781-F3D8D733AAB2";

            public Problema()
            {
            }
        }

        public static class Riesgo
        {

            public static final String Riesgo = "10C20731-7627-41E1-8535-D5C73E311A1A";

            public Riesgo()
            {
            }
        }

        public static class Transaccion
        {

            public static final String Transaccion = "0DC25625-AF06-4F7C-B981-258AEAF18C01";

            public Transaccion()
            {
            }
        }

        public static class VistaDatos
        {

            public static final String VistaDatos = "3A7CD10C-0DAB-4D41-8C1F-3299F51F1235";

            public VistaDatos()
            {
            }
        }


        public ModuloType()
        {
        }
    }


    public Modulo()
    {
        activo = Boolean.valueOf(false);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getModulo()
    {
        return modulo;
    }

    public void setModulo(String modulo)
    {
        this.modulo = modulo;
    }

    public Boolean getActivo()
    {
        return activo;
    }

    public void setActivo(Boolean activo)
    {
        this.activo = activo;
    }

    public Modulo getModuloActivo(String key)
    {
        ModuloService frameworkModuloService = FrameworkServiceFactory.getInstance().openModuloService();
        Modulo modulo = frameworkModuloService.getModulo(key);
        frameworkModuloService.close();
        return modulo;
    }

    static final long serialVersionUID = 0L;
    private String id;
    private String modulo;
    private Boolean activo;
}