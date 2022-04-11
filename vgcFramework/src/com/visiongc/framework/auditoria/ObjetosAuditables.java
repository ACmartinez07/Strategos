package com.visiongc.framework.auditoria;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.persistence.VgcPersistenceSession;
import com.visiongc.commons.util.ObjetoAbstracto;
import com.visiongc.commons.util.lang.ChainedRuntimeException;
import com.visiongc.framework.FrameworkService;
import com.visiongc.framework.auditoria.model.AuditoriaAtributoPK;
import com.visiongc.framework.auditoria.model.AuditoriaEntero;
import com.visiongc.framework.auditoria.model.AuditoriaEvento;
import com.visiongc.framework.auditoria.model.AuditoriaEventoPK;
import com.visiongc.framework.auditoria.model.AuditoriaFecha;
import com.visiongc.framework.auditoria.model.AuditoriaFlotante;
import com.visiongc.framework.auditoria.model.AuditoriaMemo;
import com.visiongc.framework.auditoria.model.AuditoriaString;
import com.visiongc.framework.auditoria.model.ObjetoAuditable;
import com.visiongc.framework.auditoria.model.ObjetoAuditableAtributo;
import com.visiongc.framework.auditoria.model.ObjetoAuditableAtributoPK;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoAtributo;
import com.visiongc.framework.auditoria.model.util.AuditoriaTipoEvento;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import com.visiongc.framework.model.Configuracion;
import com.visiongc.framework.model.Usuario;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

// Referenced classes of package com.visiongc.framework.auditoria:
//            AuditoriaService

public class ObjetosAuditables
{

    public ObjetosAuditables()
    {
    }

    public static ObjetosAuditables getInstance(VgcPersistenceSession vgcPersistenceSession)
    {
        if(instance == null)
        {
            Boolean auditoriaHabilitada = Boolean.valueOf(false);
            FrameworkService frameworkService = FrameworkServiceFactory.getInstance().openFrameworkService();
            Configuracion configuracion = frameworkService.getConfiguracion("Strategos.Configuracion.Login");
            if(configuracion != null)
                try
                {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(new ByteArrayInputStream(configuracion.getValor().getBytes("UTF-8")));
                    doc.getDocumentElement().normalize();
                    NodeList nList = doc.getElementsByTagName("Configuracion");
                    Element eElement = (Element)nList.item(0);
                    String value = VgcAbstractService.getTagValue("habilitarAuditoria", eElement);
                    auditoriaHabilitada = Boolean.valueOf(value != null && !value.equals("") && value.equals("1"));
                }
                catch(UnsupportedEncodingException e)
                {
                    auditoriaHabilitada = Boolean.valueOf(false);
                }
                catch(SAXException e)
                {
                    auditoriaHabilitada = Boolean.valueOf(false);
                }
                catch(IOException e)
                {
                    auditoriaHabilitada = Boolean.valueOf(false);
                }
                catch(ParserConfigurationException e)
                {
                    auditoriaHabilitada = Boolean.valueOf(false);
                }
            frameworkService.close();
            instance = new ObjetosAuditables();
            instance.objetosAuditables = new HashMap();
            AuditoriaService auditoriaService = FrameworkServiceFactory.getInstance().openAuditoriaService(vgcPersistenceSession);
            List lista = auditoriaService.getObjetosAuditables();
            ObjetoAuditable objetoAuditable;
            for(Iterator iter = lista.iterator(); iter.hasNext(); instance.objetosAuditables.put(objetoAuditable.getNombreClase(), objetoAuditable))
            {
                objetoAuditable = (ObjetoAuditable)iter.next();
                if(!auditoriaHabilitada.booleanValue())
                    objetoAuditable.setAuditoriaActiva(auditoriaHabilitada);
            }

        }
        return instance;
    }

    public AuditoriaEvento getAuditoriaEvento(Object objeto, Usuario usuario, byte tipoEvento, VgcPersistenceSession persistenceSession)
    {
        AuditoriaEvento auditoriaEvento = null;
        Object object = null;
        ObjetoAuditable objetoAuditable = (ObjetoAuditable)objetosAuditables.get(objeto.getClass().getName());
        if(objetoAuditable != null)
        {
            if(usuario == null)
                throw new ChainedRuntimeException("No se puede registrar auditor\355as sin un usuario");
            if(objetoAuditable.getAuditoriaActiva().booleanValue())
            {
                auditoriaEvento = new AuditoriaEvento();
                auditoriaEvento.setPk(new AuditoriaEventoPK());
                auditoriaEvento.getPk().setFecha(new Date());
                String nombreMetodoGet = (new StringBuilder("get")).append(objetoAuditable.getNombreCampoId()).toString();
                object = ObjetoAbstracto.ejecutarReturnObject(objeto, nombreMetodoGet);
                String instanciaId = object == null ? null : object.toString();
                int posicion = instanciaId.indexOf("[");
                if(posicion > -1)
                    instanciaId = instanciaId.substring(posicion);
                auditoriaEvento.getPk().setInstanciaId(instanciaId);
                auditoriaEvento.getPk().setTipoEvento(new Byte(tipoEvento));
                nombreMetodoGet = (new StringBuilder("get")).append(objetoAuditable.getNombreCampoNombre()).toString();
                object = ObjetoAbstracto.ejecutarReturnObject(objeto, nombreMetodoGet);
                String nombre = object == null ? null : object.toString();
                auditoriaEvento.setInstanciaNombre(nombre);
                auditoriaEvento.setObjetoId(objetoAuditable.getObjetoId());
                auditoriaEvento.setUsuarioId(usuario.getUsuarioId());
                auditoriaEvento.setObjetoAuditable(objetoAuditable);
                agregarAuditoriaAtributos(objeto, objetoAuditable, auditoriaEvento, instanciaId, usuario, persistenceSession, tipoEvento);
                if(objetoAuditable.getAtributosAuditables().size() == 0 || objetoAuditable.getHasValue() == null)
                    auditoriaEvento = null;
            }
        }
        return auditoriaEvento;
    }

    private void agregarAuditoriaAtributos(Object objeto, ObjetoAuditable objetoAuditable, AuditoriaEvento auditoriaEvento, String instanciaId, Usuario usuario, VgcPersistenceSession persistenceSession, byte tipoEvento)
    {
        if(auditoriaEvento.getPk().getTipoEvento().byteValue() != AuditoriaTipoEvento.getAuditoriaTipoEventoDelete())
        {
            auditoriaEvento.setAtributosString(new HashSet());
            auditoriaEvento.setAtributosMemo(new HashSet());
            auditoriaEvento.setAtributosFecha(new HashSet());
            auditoriaEvento.setAtributosEntero(new HashSet());
            auditoriaEvento.setAtributosFlotante(new HashSet());
            for(Iterator iter = objetoAuditable.getAtributosAuditables().iterator(); iter.hasNext();)
            {
                ObjetoAuditableAtributo objetoAuditableAtributo = (ObjetoAuditableAtributo)iter.next();
                if(objetoAuditableAtributo.getTipo().byteValue() == AuditoriaTipoAtributo.getAuditoriaTipoAtributoString())
                    agregarAuditoriaAtributoString(objeto, auditoriaEvento, objetoAuditableAtributo, instanciaId, usuario, persistenceSession, tipoEvento);
                else
                if(objetoAuditableAtributo.getTipo().byteValue() == AuditoriaTipoAtributo.getAuditoriaTipoAtributoMemo())
                    agregarAuditoriaAtributoMemo(objeto, auditoriaEvento, objetoAuditableAtributo, instanciaId, usuario, persistenceSession, tipoEvento);
                else
                if(objetoAuditableAtributo.getTipo().byteValue() == AuditoriaTipoAtributo.getAuditoriaTipoAtributoFecha())
                    agregarAuditoriaAtributoFecha(objeto, auditoriaEvento, objetoAuditableAtributo, instanciaId, usuario, persistenceSession, tipoEvento);
                else
                if(objetoAuditableAtributo.getTipo().byteValue() == AuditoriaTipoAtributo.getAuditoriaTipoAtributoEntero())
                    agregarAuditoriaAtributoEntero(objeto, auditoriaEvento, objetoAuditableAtributo, instanciaId, usuario, persistenceSession, tipoEvento);
                else
                if(objetoAuditableAtributo.getTipo().byteValue() == AuditoriaTipoAtributo.getAuditoriaTipoAtributoFlotante())
                    agregarAuditoriaAtributoFlotante(objeto, auditoriaEvento, objetoAuditableAtributo, instanciaId, usuario, persistenceSession, tipoEvento);
                if(objetoAuditable.getHasValue() == null)
                    objetoAuditable.setHasValue(objetoAuditableAtributo.getHasValue());
            }

        }
    }

    private void agregarAuditoriaAtributoString(Object objeto, AuditoriaEvento auditoriaEvento, ObjetoAuditableAtributo objetoAuditableAtributo, String instanciaId, Usuario usuario, VgcPersistenceSession persistenceSession, byte tipoEvento)
    {
        String nombreAtributo = objetoAuditableAtributo.getPk().getNombre();
        String nombreMetodoGet = (new StringBuilder(String.valueOf(nombreAtributo.substring(0, 1).toUpperCase()))).append(nombreAtributo.substring(1)).toString();
        nombreMetodoGet = (new StringBuilder("get")).append(nombreMetodoGet).toString();
        String valorNuevo = null;
        try
        {
            if(objetoAuditableAtributo.getClaseRelacion() != null && !objetoAuditableAtributo.getClaseRelacion().equals(""))
                valorNuevo = (String)RecorrerObjeto(objeto, nombreAtributo, objetoAuditableAtributo.getClaseRelacion(), objetoAuditableAtributo);
            else
                valorNuevo = (String)ObjetoAbstracto.ejecutarReturnObject(objeto, nombreMetodoGet);
        }
        catch(Exception e1)
        {
            valorNuevo = (String)RecorrerObjeto(objeto, nombreAtributo, null, null);
        }
        String valorAnterior = null;
        AuditoriaString auditoriaString = null;
        AuditoriaString auditoriaAtributo = (AuditoriaString)persistenceSession.getUltimaAuditoriaAtributo(instanciaId, objetoAuditableAtributo.getPk().getNombre(), objetoAuditableAtributo.getTipo().byteValue());
        if(auditoriaAtributo != null)
        {
            if(valorNuevo != null)
            {
                if(auditoriaAtributo.getValor() != null && !auditoriaAtributo.getValor().equals(valorNuevo))
                {
                    valorAnterior = auditoriaAtributo.getValor();
                    auditoriaString = new AuditoriaString();
                } else
                if(auditoriaAtributo.getValor() == null && !valorNuevo.equals(""))
                {
                    valorAnterior = auditoriaAtributo.getValor();
                    auditoriaString = new AuditoriaString();
                }
            } else
            if(valorNuevo == null && auditoriaAtributo.getValor() != null)
            {
                valorAnterior = auditoriaAtributo.getValor();
                auditoriaString = new AuditoriaString();
            }
        } else
        if(valorNuevo != null)
            auditoriaString = new AuditoriaString();
        if(auditoriaString != null)
        {
            auditoriaString.setPk(new AuditoriaAtributoPK());
            auditoriaString.getPk().setFecha(auditoriaEvento.getPk().getFecha());
            auditoriaString.getPk().setInstanciaId(instanciaId);
            auditoriaString.getPk().setNombreAtributo(nombreAtributo);
            auditoriaString.setObjetoId(auditoriaEvento.getObjetoId());
            auditoriaString.setTipoEvento(auditoriaEvento.getPk().getTipoEvento());
            auditoriaString.setUsuarioId(usuario.getUsuarioId());
            auditoriaString.setValor(valorNuevo);
            auditoriaString.setValorAnterior(valorAnterior);
            auditoriaEvento.getAtributosString().add(auditoriaString);
            objetoAuditableAtributo.setHasValue(Boolean.valueOf(true));
        }
    }

    private void agregarAuditoriaAtributoMemo(Object objeto, AuditoriaEvento auditoriaEvento, ObjetoAuditableAtributo objetoAuditableAtributo, String instanciaId, Usuario usuario, VgcPersistenceSession persistenceSession, byte tipoEvento)
    {
        String nombreAtributo = objetoAuditableAtributo.getPk().getNombre();
        String nombreMetodoGet = (new StringBuilder(String.valueOf(nombreAtributo.substring(0, 1).toUpperCase()))).append(nombreAtributo.substring(1)).toString();
        nombreMetodoGet = (new StringBuilder("get")).append(nombreMetodoGet).toString();
        String valorNuevo = null;
        try
        {
            if(objetoAuditableAtributo.getClaseRelacion() != null && !objetoAuditableAtributo.getClaseRelacion().equals(""))
                valorNuevo = (String)RecorrerObjeto(objeto, nombreAtributo, objetoAuditableAtributo.getClaseRelacion(), objetoAuditableAtributo);
            else
                valorNuevo = (String)ObjetoAbstracto.ejecutarReturnObject(objeto, nombreMetodoGet);
        }
        catch(Exception e1)
        {
            valorNuevo = (String)RecorrerObjeto(objeto, nombreAtributo, null, null);
        }
        if(valorNuevo != null && valorNuevo.length() > 2000)
            valorNuevo = valorNuevo.substring(0, 2000);
        String valorAnterior = null;
        AuditoriaMemo auditoriaMemo = null;
        AuditoriaMemo auditoriaAtributo = null;
        String valor = (String)persistenceSession.getUltimaAuditoriaAtributo(instanciaId, objetoAuditableAtributo.getPk().getNombre(), objetoAuditableAtributo.getTipo().byteValue());
        if(valor != null && !valor.equals(""))
        {
            if(valor.length() > 2000)
                valor = valor.substring(0, 2000);
            auditoriaAtributo = new AuditoriaMemo();
            auditoriaAtributo.setValor(valor);
        }
        if(auditoriaAtributo != null)
        {
            if(valorNuevo != null)
            {
                if(!auditoriaAtributo.getValor().equals(valorNuevo))
                {
                    valorAnterior = auditoriaAtributo.getValor();
                    auditoriaMemo = new AuditoriaMemo();
                }
            } else
            if(valorNuevo == null && auditoriaAtributo.getValor() != null)
            {
                valorAnterior = auditoriaAtributo.getValor();
                auditoriaMemo = new AuditoriaMemo();
            }
        } else
        if(valorNuevo != null)
            auditoriaMemo = new AuditoriaMemo();
        if(auditoriaMemo != null)
        {
            auditoriaMemo.setPk(new AuditoriaAtributoPK());
            auditoriaMemo.getPk().setFecha(auditoriaEvento.getPk().getFecha());
            auditoriaMemo.getPk().setInstanciaId(instanciaId);
            auditoriaMemo.getPk().setNombreAtributo(nombreAtributo);
            auditoriaMemo.setObjetoId(auditoriaEvento.getObjetoId());
            auditoriaMemo.setTipoEvento(auditoriaEvento.getPk().getTipoEvento());
            auditoriaMemo.setUsuarioId(usuario.getUsuarioId());
            auditoriaMemo.setValor(valorNuevo);
            auditoriaMemo.setValorAnterior(valorAnterior);
            auditoriaEvento.getAtributosMemo().add(auditoriaMemo);
            objetoAuditableAtributo.setHasValue(Boolean.valueOf(true));
        }
    }

    private void agregarAuditoriaAtributoFecha(Object objeto, AuditoriaEvento auditoriaEvento, ObjetoAuditableAtributo objetoAuditableAtributo, String instanciaId, Usuario usuario, VgcPersistenceSession persistenceSession, byte tipoEvento)
    {
        String nombreAtributo = objetoAuditableAtributo.getPk().getNombre();
        String nombreMetodoGet = (new StringBuilder(String.valueOf(nombreAtributo.substring(0, 1).toUpperCase()))).append(nombreAtributo.substring(1)).toString();
        nombreMetodoGet = (new StringBuilder("get")).append(nombreMetodoGet).toString();
        Date valorNuevo = (Date)ObjetoAbstracto.ejecutarReturnObject(objeto, nombreMetodoGet);
        Date valorAnterior = null;
        AuditoriaFecha auditoriaFecha = null;
        AuditoriaFecha auditoriaAtributo = (AuditoriaFecha)persistenceSession.getUltimaAuditoriaAtributo(instanciaId, objetoAuditableAtributo.getPk().getNombre(), objetoAuditableAtributo.getTipo().byteValue());
        if(auditoriaAtributo != null)
        {
            if(valorNuevo != null)
            {
                if(auditoriaAtributo.getValor() != null && auditoriaAtributo.getValor().getTime() != valorNuevo.getTime())
                {
                    valorAnterior = auditoriaAtributo.getValor();
                    auditoriaFecha = new AuditoriaFecha();
                }
            } else
            if(valorNuevo == null && auditoriaAtributo.getValor() != null)
            {
                valorAnterior = auditoriaAtributo.getValor();
                auditoriaFecha = new AuditoriaFecha();
            }
        } else
        if(valorNuevo != null)
            auditoriaFecha = new AuditoriaFecha();
        if(auditoriaFecha != null)
        {
            auditoriaFecha.setPk(new AuditoriaAtributoPK());
            auditoriaFecha.getPk().setFecha(auditoriaEvento.getPk().getFecha());
            auditoriaFecha.getPk().setInstanciaId(instanciaId);
            auditoriaFecha.getPk().setNombreAtributo(nombreAtributo);
            auditoriaFecha.setObjetoId(auditoriaEvento.getObjetoId());
            auditoriaFecha.setTipoEvento(auditoriaEvento.getPk().getTipoEvento());
            auditoriaFecha.setUsuarioId(usuario.getUsuarioId());
            auditoriaFecha.setValor(valorNuevo);
            auditoriaFecha.setValorAnterior(valorAnterior);
            auditoriaEvento.getAtributosFecha().add(auditoriaFecha);
            objetoAuditableAtributo.setHasValue(Boolean.valueOf(true));
        }
    }

    private void agregarAuditoriaAtributoEntero(Object objeto, AuditoriaEvento auditoriaEvento, ObjetoAuditableAtributo objetoAuditableAtributo, String instanciaId, Usuario usuario, VgcPersistenceSession persistenceSession, byte tipoEvento)
    {
        String nombreAtributo = objetoAuditableAtributo.getPk().getNombre();
        String nombreMetodoGet = (new StringBuilder(String.valueOf(nombreAtributo.substring(0, 1).toUpperCase()))).append(nombreAtributo.substring(1)).toString();
        nombreMetodoGet = (new StringBuilder("get")).append(nombreMetodoGet).toString();
        Long valorNuevo = Long.valueOf(0L);
        Long valorAnterior = null;
        try
        {
            valorNuevo = (Long)ObjetoAbstracto.ejecutarReturnObject(objeto, nombreMetodoGet);
        }
        catch(Exception e1)
        {
            try
            {
                valorNuevo = Long.valueOf(((Integer)ObjetoAbstracto.ejecutarReturnObject(objeto, nombreMetodoGet)).longValue());
            }
            catch(Exception e2)
            {
                try
                {
                    valorNuevo = Long.valueOf(Byte.toString(((Byte)ObjetoAbstracto.ejecutarReturnObject(objeto, nombreMetodoGet)).byteValue()));
                }
                catch(Exception e3)
                {
                    boolean vIn = ((Boolean)ObjetoAbstracto.ejecutarReturnObject(objeto, nombreMetodoGet)).booleanValue();
                    valorNuevo = Long.valueOf(vIn ? 1L : 0L);
                }
            }
        }
        AuditoriaEntero auditoriaEntero = null;
        AuditoriaEntero auditoriaAtributo = (AuditoriaEntero)persistenceSession.getUltimaAuditoriaAtributo(instanciaId, objetoAuditableAtributo.getPk().getNombre(), objetoAuditableAtributo.getTipo().byteValue());
        if(auditoriaAtributo != null)
        {
            if(valorNuevo != null)
            {
                if(auditoriaAtributo.getValor() != null && auditoriaAtributo.getValor().intValue() != valorNuevo.intValue())
                {
                    valorAnterior = auditoriaAtributo.getValor();
                    auditoriaEntero = new AuditoriaEntero();
                }
            } else
            if(valorNuevo == null && auditoriaAtributo.getValor() != null)
            {
                valorAnterior = auditoriaAtributo.getValor();
                auditoriaEntero = new AuditoriaEntero();
            }
        } else
        if(valorNuevo != null)
            auditoriaEntero = new AuditoriaEntero();
        if(auditoriaEntero != null)
        {
            auditoriaEntero.setPk(new AuditoriaAtributoPK());
            auditoriaEntero.getPk().setFecha(auditoriaEvento.getPk().getFecha());
            auditoriaEntero.getPk().setInstanciaId(instanciaId);
            auditoriaEntero.getPk().setNombreAtributo(nombreAtributo);
            auditoriaEntero.setObjetoId(auditoriaEvento.getObjetoId());
            auditoriaEntero.setTipoEvento(auditoriaEvento.getPk().getTipoEvento());
            auditoriaEntero.setUsuarioId(usuario.getUsuarioId());
            auditoriaEntero.setValor(valorNuevo);
            auditoriaEntero.setValorAnterior(valorAnterior);
            auditoriaEvento.getAtributosEntero().add(auditoriaEntero);
            objetoAuditableAtributo.setHasValue(Boolean.valueOf(true));
        }
    }

    private void agregarAuditoriaAtributoFlotante(Object objeto, AuditoriaEvento auditoriaEvento, ObjetoAuditableAtributo objetoAuditableAtributo, String instanciaId, Usuario usuario, VgcPersistenceSession persistenceSession, byte tipoEvento)
    {
        String nombreAtributo = objetoAuditableAtributo.getPk().getNombre();
        String nombreMetodoGet = (new StringBuilder(String.valueOf(nombreAtributo.substring(0, 1).toUpperCase()))).append(nombreAtributo.substring(1)).toString();
        nombreMetodoGet = (new StringBuilder("get")).append(nombreMetodoGet).toString();
        Double valorNuevo = (Double)ObjetoAbstracto.ejecutarReturnObject(objeto, nombreMetodoGet);
        Double valorAnterior = null;
        AuditoriaFlotante auditoriaFlotante = null;
        AuditoriaFlotante auditoriaAtributo = (AuditoriaFlotante)persistenceSession.getUltimaAuditoriaAtributo(instanciaId, objetoAuditableAtributo.getPk().getNombre(), objetoAuditableAtributo.getTipo().byteValue());
        if(auditoriaAtributo != null)
        {
            if(valorNuevo != null)
            {
                if(auditoriaAtributo.getValor() != null && auditoriaAtributo.getValor().doubleValue() != valorNuevo.doubleValue())
                {
                    valorAnterior = auditoriaAtributo.getValor();
                    auditoriaFlotante = new AuditoriaFlotante();
                }
            } else
            if(valorNuevo == null && auditoriaAtributo.getValor() != null)
            {
                valorAnterior = auditoriaAtributo.getValor();
                auditoriaFlotante = new AuditoriaFlotante();
            }
        } else
        if(valorNuevo != null)
            auditoriaFlotante = new AuditoriaFlotante();
        if(auditoriaFlotante != null)
        {
            auditoriaFlotante.setPk(new AuditoriaAtributoPK());
            auditoriaFlotante.getPk().setFecha(auditoriaEvento.getPk().getFecha());
            auditoriaFlotante.getPk().setInstanciaId(instanciaId);
            auditoriaFlotante.getPk().setNombreAtributo(nombreAtributo);
            auditoriaFlotante.setObjetoId(auditoriaEvento.getObjetoId());
            auditoriaFlotante.setTipoEvento(auditoriaEvento.getPk().getTipoEvento());
            auditoriaFlotante.setUsuarioId(usuario.getUsuarioId());
            auditoriaFlotante.setValor(valorNuevo);
            auditoriaFlotante.setValorAnterior(valorAnterior);
            auditoriaEvento.getAtributosFlotante().add(auditoriaFlotante);
            objetoAuditableAtributo.setHasValue(Boolean.valueOf(true));
        }
    }

    private Object RecorrerObjeto(Object obj, String nombreAtributo, String claseRelacion, ObjetoAuditableAtributo objetoAuditableAtributo)
    {
        Object valor = null;
        try
        {
            Class _class = Class.forName(claseRelacion);
            Field properties[] = _class.getDeclaredFields();
            for(int i = 0; i < properties.length; i++)
            {
                Field field = properties[i];
                if(field.getName().equals("serialVersionUID"))
                    continue;
                String campo = field.getName();
                String tipoCampo = field.getType().toString();
                String campos[] = nombreAtributo.split(",");
                if(!campo.equals(campos[0]))
                    continue;
                field.setAccessible(true);
                if(objetoAuditableAtributo != null && objetoAuditableAtributo.getClaseImpl() != null)
                {
                    if(tipoCampo.equals("interface java.util.Set"))
                    {
                        Set objects = (Set)field.get(obj);
                        for(Iterator iter = objects.iterator(); iter.hasNext();)
                        {
                            Object objeto = iter.next();
                            if(objetoAuditableAtributo.getClaseSet() != null)
                                valor = RecorrerObjeto(objeto, objetoAuditableAtributo.getNombreSet(), objetoAuditableAtributo.getClaseImpl(), objetoAuditableAtributo);
                            if(valor != null)
                                break;
                        }

                        break;
                    }
                    if(objetoAuditableAtributo.getClaseSet() != null)
                    {
                        if(BuscarObjeto(field.get(obj), objetoAuditableAtributo.getNombreRelacion(), objetoAuditableAtributo.getValorRelacion(), objetoAuditableAtributo.getClaseSet(), objetoAuditableAtributo).booleanValue())
                            valor = RecorrerObjeto(obj, objetoAuditableAtributo.getNombreImpl(), claseRelacion, null);
                        break;
                    }
                    if(!campo.equals(nombreAtributo))
                        continue;
                    valor = RecorrerObjeto(field.get(obj), objetoAuditableAtributo.getNombreRelacion(), objetoAuditableAtributo.getClaseImpl(), null);
                } else
                {
                    valor = field.get(obj);
                }
                break;
            }

        }
        catch(Exception e)
        {
            valor = null;
        }
        return valor;
    }

    private Boolean BuscarObjeto(Object obj, String nombreRelacion, String valorRelacion, String claseRelacion, ObjetoAuditableAtributo objetoAuditableAtributo)
    {
        boolean valor = false;
        Object valorSet = null;
        String valorString = null;
        try
        {
            Class _class = Class.forName(claseRelacion);
            Field properties[] = _class.getDeclaredFields();
            for(int i = 0; i < properties.length; i++)
            {
                Field field = properties[i];
                if(field.getName().equals("serialVersionUID"))
                    continue;
                String campo = field.getName();
                String tipoCampo = field.getType().toString();
                if(campo == null || !campo.equals(nombreRelacion))
                    continue;
                field.setAccessible(true);
                valorSet = field.get(obj);
                if(tipoCampo.equals("class java.lang.Long"))
                    valorString = ((Long)valorSet).toString();
                else
                if(tipoCampo.equals("class java.lang.Integer"))
                    valorString = ((Integer)valorSet).toString();
                if(valorString == null || valorRelacion == null || !valorString.equals(valorRelacion))
                    continue;
                valor = true;
                break;
            }

        }
        catch(Exception exception) { }
        return Boolean.valueOf(valor);
    }

    private static ObjetosAuditables instance;
    private Map objetosAuditables;
}