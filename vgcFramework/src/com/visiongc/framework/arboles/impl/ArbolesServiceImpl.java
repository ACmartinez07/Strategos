package com.visiongc.framework.arboles.impl;

import com.visiongc.commons.impl.VgcAbstractService;
import com.visiongc.commons.util.ObjetoAbstracto;
import com.visiongc.commons.util.VgcMessageResources;
import com.visiongc.framework.arboles.*;
import com.visiongc.framework.arboles.persistence.ArbolesPersistenceSession;
import com.visiongc.framework.arboles.util.ArbolesTipoNodo;
import com.visiongc.framework.impl.FrameworkServiceFactory;
import java.util.*;

public class ArbolesServiceImpl extends VgcAbstractService
    implements ArbolesService
{

    public ArbolesServiceImpl(ArbolesPersistenceSession persistenceSession, boolean persistenceOwned, FrameworkServiceFactory serviceFactory, VgcMessageResources messageResources)
    {
        super(persistenceSession, persistenceOwned, serviceFactory, messageResources);
        this.persistenceSession = null;
        this.persistenceSession = persistenceSession;
    }

    public NodoArbol getNodoArbolRaiz(NodoArbol nodoArbol)
    {
        return getNodoArbolRaiz(nodoArbol, null);
    }

    public NodoArbol getNodoArbolRaiz(NodoArbol nodoArbol, Object grupoNodosId[])
    {
        return persistenceSession.getNodoArbolRaiz(nodoArbol, grupoNodosId);
    }

    public void refreshNodosArbol(NodoArbol nodoRaiz, String abiertos, String separador, Boolean mostrarTodos)
    {
        refreshNodosArbol(nodoRaiz, abiertos, separador, mostrarTodos, null, null);
    }

    public void refreshNodosArbol(NodoArbol nodoRaiz, String abiertos, String separador, Boolean mostrarTodos, Long reloadId)
    {
        refreshNodosArbol(nodoRaiz, abiertos, separador, mostrarTodos, reloadId, null);
    }

    public void refreshNodosArbol(NodoArbol nodoRaiz, String abiertos, String separador, Boolean mostrarTodos, List listaNodosHoja)
    {
        refreshNodosArbol(nodoRaiz, abiertos, separador, mostrarTodos, null, listaNodosHoja);
    }

    public void refreshNodosArbol(NodoArbol nodoRaiz, String abiertos, String separador, Boolean mostrarTodos, Long reloadId, List listaNodosHoja)
    {
        refreshNodosArbol(nodoRaiz, abiertos, separador, mostrarTodos, reloadId, listaNodosHoja, null);
    }

    public void refreshNodosArbol(NodoArbol nodoRaiz, String abiertos, String separador, Boolean mostrarTodos, Long reloadId, List listaNodosHoja, String orden)
    {
        boolean cargarHijosRaiz = false;
        Collection hijosRaiz;
        if(!nodoRaiz.getNodoArbolHijosCargados() || reloadId != null && reloadId.toString().equals(nodoRaiz.getNodoArbolId()))
        {
            hijosRaiz = new ArrayList();
            hijosRaiz.addAll(persistenceSession.getNodosArbolHijos(nodoRaiz, orden));
            persistenceSession.clear();
            if(orden == null)
                nodoRaiz.setNodoArbolHijos(new TreeSet(new ComparatorNodoArbol(nodoRaiz.getNodoArbolNombreCampoNombre())));
            else
                nodoRaiz.setNodoArbolHijos(new TreeSet(new ComparatorNodoArbol(nodoRaiz.getNodoArbolNombreCampoNombre(), Boolean.valueOf(false))));
            cargarHijosRaiz = true;
        } else
        {
            hijosRaiz = nodoRaiz.getNodoArbolHijos();
        }
        List hijosHoja = new ArrayList();
        if(listaNodosHoja != null)
        {
            ObjetoNodoHoja hijoHoja;
            for(Iterator i = listaNodosHoja.iterator(); i.hasNext(); hijosHoja.addAll(persistenceSession.getNodosHoja(nodoRaiz, hijoHoja)))
                hijoHoja = (ObjetoNodoHoja)i.next();

            ((MultiNodoArbol)nodoRaiz).setHijosHoja(hijosHoja);
        }
        if(reloadId != null && reloadId.equals(new Long(nodoRaiz.getNodoArbolId())))
        {
            NodoArbol temp = (NodoArbol)load(nodoRaiz.getClass(), new Long(nodoRaiz.getNodoArbolId()));
            nodoRaiz.setNodoArbolNombre(temp.getNodoArbolNombre());
        }
        boolean implementaVisibilidad = false;
        if(hijosRaiz.size() > 0)
            implementaVisibilidad = ObjetoAbstracto.implementaInterface(hijosRaiz.iterator().next(), "Visibilidad");
        for(Iterator i = hijosRaiz.iterator(); i.hasNext();)
        {
            Object objetoHijo = i.next();
            if(ObjetoAbstracto.implementaInterface(objetoHijo, "NodoArbol"))
            {
                NodoArbol hijo = (NodoArbol)objetoHijo;
                boolean esVisible = mostrarTodos.booleanValue();
                if(implementaVisibilidad && !mostrarTodos.booleanValue())
                    esVisible = ((Boolean)ObjetoAbstracto.ejecutarReturnObject(hijo, "getVisible")).booleanValue();
                else
                    esVisible = true;
                if(cargarHijosRaiz && esVisible)
                    nodoRaiz.getNodoArbolHijos().add(hijo);
                if(esVisible)
                {
                    Collection hijos;
                    if(!hijo.getNodoArbolHijosCargados() || reloadId != null && reloadId.equals(new Long(hijo.getNodoArbolId())))
                    {
                        hijos = persistenceSession.getNodosArbolHijos(hijo, orden);
                        persistenceSession.clear();
                    } else
                    {
                        hijos = hijo.getNodoArbolHijos();
                    }
                    hijosHoja = new ArrayList();
                    if(listaNodosHoja != null)
                    {
                        ObjetoNodoHoja hijoHoja;
                        for(Iterator h = listaNodosHoja.iterator(); h.hasNext(); hijosHoja.addAll(persistenceSession.getNodosHoja(hijo, hijoHoja)))
                            hijoHoja = (ObjetoNodoHoja)h.next();

                        ((MultiNodoArbol)hijo).setHijosHoja(hijosHoja);
                    }
                    refreshNodosArbolAux(hijo, hijos, abiertos, separador, mostrarTodos, reloadId, listaNodosHoja, orden);
                }
            }
        }

        nodoRaiz.setNodoArbolHijosCargados(true);
    }

    private void refreshNodosArbolAux(NodoArbol nodoArbol, Collection nodosHijos, String abiertos, String separador, Boolean mostrarTodos, Long reloadId, List listaNodosHoja, 
            String orden)
    {
        if(!nodoArbol.getNodoArbolHijosCargados())
            if(orden == null)
                nodoArbol.setNodoArbolHijos(new TreeSet(new ComparatorNodoArbol(nodoArbol.getNodoArbolNombreCampoNombre())));
            else
                nodoArbol.setNodoArbolHijos(new TreeSet(new ComparatorNodoArbol(nodoArbol.getNodoArbolNombreCampoNombre(), Boolean.valueOf(false))));
        if(reloadId != null && reloadId.toString().equals(nodoArbol.getNodoArbolId()))
        {
            NodoArbol temp = (NodoArbol)load(nodoArbol.getClass(), new Long(nodoArbol.getNodoArbolId()));
            nodoArbol.setNodoArbolNombre(temp.getNodoArbolNombre());
            if(orden == null)
                nodoArbol.setNodoArbolHijos(new TreeSet(new ComparatorNodoArbol(nodoArbol.getNodoArbolNombreCampoNombre())));
            else
                nodoArbol.setNodoArbolHijos(new TreeSet(new ComparatorNodoArbol(nodoArbol.getNodoArbolNombreCampoNombre(), Boolean.valueOf(false))));
        }
        boolean abrir = false;
        String idBuscado = (new StringBuilder(String.valueOf(separador))).append(nodoArbol.getNodoArbolId()).append(separador).toString();
        if(abiertos.indexOf(idBuscado) > -1)
            abrir = true;
        boolean implementaVisibilidad = false;
        if(nodosHijos.size() > 0)
            implementaVisibilidad = ObjetoAbstracto.implementaInterface(nodosHijos.iterator().next(), "Visibilidad");
        for(Iterator i = nodosHijos.iterator(); i.hasNext();)
        {
            Object objetoHijo = i.next();
            if(ObjetoAbstracto.implementaInterface(objetoHijo, "NodoArbol"))
            {
                NodoArbol hijo = (NodoArbol)objetoHijo;
                boolean esVisible = mostrarTodos.booleanValue();
                if(implementaVisibilidad && !mostrarTodos.booleanValue())
                    esVisible = ((Boolean)ObjetoAbstracto.ejecutarReturnObject(hijo, "getVisible")).booleanValue();
                else
                    esVisible = true;
                if(!nodoArbol.getNodoArbolHijosCargados() && esVisible || reloadId != null && reloadId.toString().equals(nodoArbol.getNodoArbolId()))
                    nodoArbol.getNodoArbolHijos().add(hijo);
                if(abrir && esVisible)
                {
                    Collection hijos;
                    if(!hijo.getNodoArbolHijosCargados() || reloadId != null && reloadId.toString().equals(hijo.getNodoArbolId()))
                    {
                        hijos = persistenceSession.getNodosArbolHijos(hijo);
                        persistenceSession.clear();
                    } else
                    {
                        hijos = hijo.getNodoArbolHijos();
                    }
                    List hijosHoja = new ArrayList();
                    if(listaNodosHoja != null)
                    {
                        ObjetoNodoHoja hijoHoja;
                        for(Iterator h = listaNodosHoja.iterator(); h.hasNext(); hijosHoja.addAll(persistenceSession.getNodosHoja(hijo, hijoHoja)))
                            hijoHoja = (ObjetoNodoHoja)h.next();

                        ((MultiNodoArbol)hijo).setHijosHoja(hijosHoja);
                    }
                    refreshNodosArbolAux(hijo, hijos, abiertos, separador, mostrarTodos, reloadId, listaNodosHoja, orden);
                }
            }
        }

        if(!nodoArbol.getNodoArbolHijosCargados())
            nodoArbol.setNodoArbolHijosCargados(true);
    }

    public List getNodosArbolHijos(NodoArbol nodoArbol)
    {
        return persistenceSession.getNodosArbolHijos(nodoArbol);
    }

    public List getNodosArbolHijos(NodoArbol nodoArbol, String order)
    {
        return persistenceSession.getNodosArbolHijos(nodoArbol, order);
    }

    public boolean bloquearParaUso(String instancia, NodoArbol nodoAnterior, NodoArbol nodoActual)
    {
        if(nodoAnterior != null)
            unlockObject(instancia, nodoAnterior.getNodoArbolId());
        return lockForUse(instancia, new Long(nodoActual.getNodoArbolId()));
    }

    private void crearReporteArbol(NodoArbol nodoBase, int ident, List arbol, Boolean mostrarTodos)
    {
        boolean implementaVisibilidad = false;
        String espaciosBlancos = "";
        for(int i = 0; i < ident; i++)
            espaciosBlancos = (new StringBuilder(String.valueOf(espaciosBlancos))).append("     ").toString();

        arbol.add((new StringBuilder(String.valueOf(espaciosBlancos))).append(nodoBase.getNodoArbolNombre()).toString());
        Collection hijos = nodoBase.getNodoArbolHijos();
        implementaVisibilidad = ObjetoAbstracto.implementaInterface(nodoBase, "Visibilidad");
        if(hijos != null)
        {
            for(Iterator iter = hijos.iterator(); iter.hasNext();)
            {
                NodoArbol nodoHijo = (NodoArbol)iter.next();
                boolean esVisible;
                if(implementaVisibilidad && !mostrarTodos.booleanValue())
                    esVisible = ((Boolean)ObjetoAbstracto.ejecutarReturnObject(nodoHijo, "getVisible")).booleanValue();
                else
                    esVisible = true;
                if(esVisible)
                    crearReporteArbol(nodoHijo, ident + 1, arbol, mostrarTodos);
            }

        }
    }

    public List crearReporteArbol(NodoArbol nodoBase, Boolean mostrarTodos)
    {
        List arbol = new ArrayList();
        Boolean mostrarTodoNodo = new Boolean(mostrarTodos != null && mostrarTodos.booleanValue());
        crearReporteArbol(nodoBase, 0, arbol, mostrarTodoNodo);
        return arbol;
    }

    private void crearReporteArbol(NodoArbol nodoBase, int ident, List arbol, Boolean mostrarTodos, String separador)
    {
        boolean implementaVisibilidad = false;
        byte tipoNodo = nodoBase.getNodoArbolHijos() == null || nodoBase.getNodoArbolHijos().size() <= 0 ? ArbolesTipoNodo.getArbolesTipoNodoHijo() : ArbolesTipoNodo.getArbolesTipoNodoPadre();
        arbol.add((new StringBuilder(String.valueOf(ident))).append(separador).append(tipoNodo).append(separador).append(nodoBase.getNodoArbolNombre()).toString());
        Collection hijos = nodoBase.getNodoArbolHijos();
        implementaVisibilidad = ObjetoAbstracto.implementaInterface(nodoBase, "Visibilidad");
        if(hijos != null)
        {
            for(Iterator iter = hijos.iterator(); iter.hasNext();)
            {
                NodoArbol nodoHijo = (NodoArbol)iter.next();
                boolean esVisible;
                if(implementaVisibilidad && !mostrarTodos.booleanValue())
                    esVisible = ((Boolean)ObjetoAbstracto.ejecutarReturnObject(nodoHijo, "getVisible")).booleanValue();
                else
                    esVisible = true;
                if(esVisible)
                    crearReporteArbol(nodoHijo, ident + 1, arbol, mostrarTodos, separador);
            }

        }
    }

    public List crearReporteArbol(NodoArbol nodoBase, Boolean mostrarTodos, String separador)
    {
        List arbol = new ArrayList();
        Boolean mostrarTodoNodo = new Boolean(mostrarTodos != null && mostrarTodos.booleanValue());
        if(separador != null && !separador.equals(""))
            crearReporteArbol(nodoBase, 0, arbol, mostrarTodoNodo, separador);
        else
            crearReporteArbol(nodoBase, 0, arbol, mostrarTodoNodo);
        return arbol;
    }

    public List getRutaCompleta(NodoArbol nodoBase)
    {
        List lista = new ArrayList();
        NodoArbol nodo = nodoBase;
        lista.add(nodo);
        nodo = nodo.getNodoArbolPadre();
        boolean padreNulo = nodo == null;
        if(!padreNulo)
            lista.add(0, nodo);
        while(!padreNulo) 
        {
            nodo = nodo.getNodoArbolPadre();
            padreNulo = nodo == null;
            if(!padreNulo)
                lista.add(0, nodo);
        }
        return lista;
    }

    public List getRutaCompletaNombres(NodoArbol nodoBase)
    {
        List lista = new ArrayList();
        String nombre = "";
        NodoArbol nodo = nodoBase;
        nombre = nodo.getNodoArbolNombre();
        lista.add(0, nombre);
        for(boolean padreNulo = nodo.getNodoArbolPadre() == null; !padreNulo;)
        {
            nodo = nodo.getNodoArbolPadre();
            padreNulo = nodo == null;
            if(!padreNulo)
            {
                nombre = nodo.getNodoArbolNombre();
                lista.add(0, nombre);
            }
        }

        return lista;
    }

    public String getRutaCompletaNombres(NodoArbol nodoBase, String separador)
    {
        String nombre = "";
        String ruta = "";
        NodoArbol nodo = nodoBase;
        nombre = nodo.getNodoArbolNombre();
        ruta = nombre;
        for(boolean padreNulo = nodo.getNodoArbolPadre() == null; !padreNulo;)
        {
            nodo = nodo.getNodoArbolPadre();
            padreNulo = nodo == null;
            if(!padreNulo)
            {
                nombre = nodo.getNodoArbolNombre();
                ruta = (new StringBuilder(String.valueOf(nombre))).append(separador).append(ruta).toString();
            }
        }

        return ruta;
    }

    public String getRutaCompletaIds(NodoArbol nodoBase, String separador)
    {
        String id = "";
        String ruta = "";
        NodoArbol nodo = nodoBase;
        id = nodo.getNodoArbolId();
        ruta = id;
        for(boolean padreNulo = nodo.getNodoArbolPadre() == null; !padreNulo;)
        {
            nodo = nodo.getNodoArbolPadre();
            padreNulo = nodo == null;
            if(!padreNulo)
            {
                id = nodo.getNodoArbolId();
                ruta = (new StringBuilder(String.valueOf(id))).append(separador).append(ruta).toString();
            }
        }

        return ruta;
    }

    public NodoArbol findNodoArbol(NodoArbol nodoBase, String nodoId)
    {
        NodoArbol buscado = null;
        if(nodoBase.getNodoArbolId().equals(nodoId))
            buscado = nodoBase;
        else
        if(nodoBase.getNodoArbolHijos() != null)
        {
            for(Iterator iter = nodoBase.getNodoArbolHijos().iterator(); iter.hasNext();)
            {
                NodoArbol nodoHijo = (NodoArbol)iter.next();
                buscado = findNodoArbol(nodoHijo, nodoId);
                if(buscado != null)
                    break;
            }

        }
        return buscado;
    }

    public NodoArbol getArbolCompleto(NodoArbol nodoArbol, Object grupoNodosId[])
    {
        NodoArbol raiz = persistenceSession.getNodoArbolRaiz(nodoArbol, grupoNodosId);
        if(raiz != null)
            getArbolCompletoAux(raiz);
        return raiz;
    }

    private void getArbolCompletoAux(NodoArbol nodoArbol)
    {
        List hijos = persistenceSession.getNodosArbolHijos(nodoArbol);
        nodoArbol.setNodoArbolHijos(hijos);
        if(hijos != null)
        {
            NodoArbol hijo;
            for(Iterator iter = hijos.iterator(); iter.hasNext(); getArbolCompletoAux(hijo))
                hijo = (NodoArbol)iter.next();

        }
    }

    private ArbolesPersistenceSession persistenceSession;
}