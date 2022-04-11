<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/paginationtag" prefix="pagination-tag"%>
<%@ taglib uri="/tags/vgc-util" prefix="vgcutil"%>
<%@ taglib uri="/tags/vgc-interfaz" prefix="vgcinterfaz"%>
<%@ taglib uri="/tags/vgc-strategos" prefix="vgcst"%>

<%@ page errorPage="/paginas/comunes/errorJsp.jsp"%>

<%-- Modificado por: Kerwin Arias (23/09/2012) --%>
<script language="JavaScript" type="text/javascript">

function mostrarDetallePerspectiva(id)
{
	window.location.href = '<html:rewrite action="/planes/perspectivas/visualizarPerspectiva"/>?perspectivaId=' + id + '&mostrarObjetosAsociados=true&vinculoCausaEfecto=0';
}

</script>

<table class="bordeFichaDatos" cellpadding="3" cellspacing="0" align="center" height="100%" width="100%">
	<tr>
		<td><b><vgcutil:message key="jsp.visualizarperspectiva.perspectiva.hijos.title" /></b></td>
	</tr>
	<tr>
		<td>
			<%-- Visor Lista --%>
			<vgcinterfaz:visorLista namePaginaLista="paginaPerspectivaHijas" scopePaginaLista="request" messageKeyNoElementos="jsp.visualizarperspectivashijas.noregistros" nombre="visorPerspectivasHijas" nombreConfiguracionBase="com.visiongc.app.strategos.web.configuracion.StrategosWebConfiguracionesBase">

				<%-- Encabezados del Visor Lista --%>
				<logic:notEmpty scope="session" name="configuracionPlan">
					<logic:equal scope="session" name="configuracionPlan" property="planObjetivoAlertaParcialMostrar" value="true">				
						<vgcinterfaz:columnaVisorLista nombre="alertaParcial" width="40px">
							<vgcutil:message key="jsp.visualizarperspectivashijas.columna.alertaParcial" />
						</vgcinterfaz:columnaVisorLista>
					</logic:equal>
					<logic:equal scope="session" name="configuracionPlan" property="planObjetivoAlertaAnualMostrar" value="true">
						<vgcinterfaz:columnaVisorLista nombre="alertaAnual" width="40px">
							<vgcutil:message key="jsp.visualizarperspectivashijas.columna.alertaAnual" />
						</vgcinterfaz:columnaVisorLista>
					</logic:equal>
				</logic:notEmpty>
				<logic:empty scope="session" name="configuracionPlan">				
					<vgcinterfaz:columnaVisorLista nombre="alertaParcial" width="40px">
						<vgcutil:message key="jsp.visualizarperspectivashijas.columna.alertaParcial" />
					</vgcinterfaz:columnaVisorLista>
					<vgcinterfaz:columnaVisorLista nombre="alertaAnual" width="40px">
						<vgcutil:message key="jsp.visualizarperspectivashijas.columna.alertaAnual" />
					</vgcinterfaz:columnaVisorLista>
				</logic:empty>
				<vgcinterfaz:columnaVisorLista nombre="nombre" width="600px">
					<vgcutil:message key="jsp.visualizarperspectivashijas.columna.nombre" />
				</vgcinterfaz:columnaVisorLista>
	
				<%-- Filas del Visor Lista --%>
				<vgcinterfaz:filasVisorLista nombreObjeto="perspectiva">
	
					<vgcinterfaz:visorListaFilaId>
						<bean:write name="perspectiva" property="perspectivaId" />
					</vgcinterfaz:visorListaFilaId>

					<logic:notEmpty name="perspectiva" property="configuracionPlan">
						<logic:equal name="perspectiva" property="configuracionPlan.planObjetivoAlertaParcialMostrar" value="true">
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="alertaParcial" align="center">
								<vgcst:imagenAlertaIniciativa name="perspectiva" property="alertaParcial" />
							</vgcinterfaz:valorFilaColumnaVisorLista>
						</logic:equal>
						<logic:equal name="perspectiva" property="configuracionPlan.planObjetivoAlertaAnualMostrar" value="true">
							<vgcinterfaz:valorFilaColumnaVisorLista nombre="alertaAnual" align="center">
								<vgcst:imagenAlertaIniciativa name="perspectiva" property="alertaAnual" />
							</vgcinterfaz:valorFilaColumnaVisorLista>
						</logic:equal>
					</logic:notEmpty>
					<logic:empty name="perspectiva" property="configuracionPlan">
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="alertaParcial" align="center">
							<vgcst:imagenAlertaIniciativa name="perspectiva" property="alertaParcial" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
						<vgcinterfaz:valorFilaColumnaVisorLista nombre="alertaAnual" align="center">
							<vgcst:imagenAlertaIniciativa name="perspectiva" property="alertaAnual" />
						</vgcinterfaz:valorFilaColumnaVisorLista>
					</logic:empty>
					<vgcinterfaz:valorFilaColumnaVisorLista nombre="nombre">
						<a href="javascript:mostrarDetallePerspectiva('<bean:write name="perspectiva" property="perspectivaId" />')"> <bean:write name="perspectiva" property="nombreCompleto" /></a>
					</vgcinterfaz:valorFilaColumnaVisorLista>
	
				</vgcinterfaz:filasVisorLista>
			</vgcinterfaz:visorLista>
		</td>
	</tr>
</table>


