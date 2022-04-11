<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<!-- Modificado por: Kerwin Arias (11/05/2012) -->

<vgcinterfaz:menuBotones id="menuArchivo" key="menu.archivo">
	<vgcinterfaz:botonMenu key="menu.archivo.prepararpagina" onclick="prepararPagina();" />
	<vgcinterfaz:botonMenu key="menu.archivo.presentacionpreliminar" onclick="reporte();" agregarSeparador="true" />
	<vgcinterfaz:botonMenu key="menu.archivo.salir" onclick="salir();" />
</vgcinterfaz:menuBotones>
