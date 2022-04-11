<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Creado por: Kerwin Arias (08/06/2012) --%>

<vgcinterfaz:contenedorForma mostrarBarraSuperior="false">

	<%-- Título --%>
	<vgcinterfaz:contenedorFormaTitulo>
		..:: <vgcutil:message key="jsp.lista.panel.graficos.titulo" />
	</vgcinterfaz:contenedorFormaTitulo>

	<%-- Visor Lista --%>
	<vgcinterfaz:visorLista namePaginaLista="paginaGraficos" messageKeyNoElementos="jsp.lista.noregistrosgraficos" nombre="tablaGraficos" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

		<%-- Encabezados del Visor Lista --%>
		<vgcinterfaz:columnaVisorLista width="10" nombre="seleccionar">
			<img src="<html:rewrite page='/componentes/visorLista/seleccionado.gif'/>" border="0" width="10" height="10">
		</vgcinterfaz:columnaVisorLista>
		<vgcinterfaz:columnaVisorLista align="center" width="418" onclick="javascript:consultar(seleccionarGraficoForm, 'nombre', null);" nombre="nombre">
			<vgcutil:message key="jsp.lista.graficos.columna.nombre" />
		</vgcinterfaz:columnaVisorLista>

		<%-- Filas del Visor Lista --%>
		<vgcinterfaz:filasVisorLista nombreObjeto="grafico">
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="seleccionar">
				<img name="img<bean:write name="grafico" property="graficoId" />" src="<html:rewrite page='/componentes/visorLista/transparente.gif'/>" border="0" width="10" height="10">
			</vgcinterfaz:valorFilaColumnaVisorLista>			
			<vgcinterfaz:visorListaFilaEventoOnclick>				
				eventoClickFila(document.seleccionarGraficoForm, 'tablaGraficos', this);								
			</vgcinterfaz:visorListaFilaEventoOnclick>			
			<vgcinterfaz:visorListaFilaEventoOnmouseout>
				eventoMouseFueraFila(document.seleccionarGraficoForm, this)
			</vgcinterfaz:visorListaFilaEventoOnmouseout>			
			<vgcinterfaz:visorListaFilaEventoOnmouseover>
				eventoMouseEncimaFila(document.seleccionarGraficoForm, this)
			</vgcinterfaz:visorListaFilaEventoOnmouseover>			
			<vgcinterfaz:visorListaFilaId>
				<bean:write name="grafico" property="graficoId" />
			</vgcinterfaz:visorListaFilaId>
			<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
				<vgcinterfaz:visorListaValorFilaColumnaId>valor<bean:write name="grafico" property="graficoId" />
				</vgcinterfaz:visorListaValorFilaColumnaId>
				<bean:write name="grafico" property="nombre" />
			</vgcinterfaz:valorFilaColumnaVisorLista>			
		</vgcinterfaz:filasVisorLista>

	</vgcinterfaz:visorLista>
</vgcinterfaz:contenedorForma>
