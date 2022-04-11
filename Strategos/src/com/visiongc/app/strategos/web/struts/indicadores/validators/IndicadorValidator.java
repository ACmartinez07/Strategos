package com.visiongc.app.strategos.web.struts.indicadores.validators;

import java.util.Iterator;
import java.util.List;

import com.visiongc.app.strategos.calculos.model.util.VgcFormulaEvaluator;
import com.visiongc.app.strategos.impl.StrategosServiceFactory;
import com.visiongc.app.strategos.indicadores.StrategosIndicadoresService;
import com.visiongc.app.strategos.indicadores.model.Indicador;
import com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.Resources;

public class IndicadorValidator
{
	public static boolean validateNombreReservado(Object bean, ValidatorAction validatorAction, Field field, ActionMessages errors, Validator validator, HttpServletRequest request)
	{
		String value = null;
		boolean hayReservados = false;
		String[] reservados = new String[4];
		reservados[0] = "[";
		reservados[1] = "]";
		reservados[2] = "{";
		reservados[3] = "}";
		
		if (isString(bean))
			value = (String)bean;
		else 
			value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		for (int i = 0; i < reservados.length; i++) 
		{
			if (value.indexOf(reservados[i]) > -1) 
			{
				errors.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
				hayReservados = true;
				break;
			}
		}

		return !hayReservados;
	}

	public static boolean validateRevision(Object bean, ValidatorAction validatorAction, Field field, ActionMessages errors, Validator validator, HttpServletRequest request)
	{
		boolean revisionValida = true;
		
		EditarIndicadorForm editarIndicadorForm = (EditarIndicadorForm)bean;

		Long indicadorId = editarIndicadorForm.getIndicadorAsociadoId();

		Byte revision = editarIndicadorForm.getIndicadorAsociadoRevision();

		if ((indicadorId != null) && (indicadorId.longValue() > 0L) && (revision != null) && (!request.getParameter("indicadorAsociadoRevision").equals(""))) 
		{
			StrategosIndicadoresService strategosIndicadoresService = StrategosServiceFactory.getInstance().openStrategosIndicadoresService();
			
			Indicador indicadorProgramado = strategosIndicadoresService.getIndicadorProgramado(indicadorId, revision);

			if ((indicadorProgramado != null) && ((editarIndicadorForm.getIndicadorId() == null) || (editarIndicadorForm.getIndicadorId().longValue() != indicadorProgramado.getIndicadorId().longValue()))) 
			{
				errors.add(field.getKey(), new ActionMessage("validation.editarindicador.revisionrepetida", revision.toString()));
				revisionValida = false;
			}

			strategosIndicadoresService.close();
		}

		return revisionValida;
	}

	public static boolean validateFormulaMacrosPeriodo(Object bean, ValidatorAction validatorAction, Field field, ActionMessages errors, Validator validator, HttpServletRequest request)
	{
		String value = null;

		if (isString(bean))
			value = (String)bean;
		else 
			value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (bean.getClass().getName().equals("com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm"))
		{
			EditarIndicadorForm forma = (EditarIndicadorForm)bean;

			if (forma.getNaturaleza().byteValue() != forma.getNaturalezaFormula().byteValue()) 
				return true;
			if ((value != null) && (forma.getInsumosFormula() != null)) 
				value = parseFormula(value, forma.getInsumosFormula());

			String[] strMacros = new String[1];
			strMacros[0] = value;
			if (!stripMacros(strMacros)) 
			{
				errors.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
				forma.setPuntoEdicion("definicionFormula");
				return false;
			}
			
			return true;
		}

		return true;
	}

	public static boolean validateFormulaInsumosPegados(Object bean, ValidatorAction va, Field field, ActionMessages errors, Validator validator, HttpServletRequest request)
	{
		String value = null;
		if (isString(bean))
			value = (String)bean;
		else 
			value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (value == null) 
			return true;
		if (bean.getClass().getName().equals("com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm")) 
		{
			EditarIndicadorForm forma = (EditarIndicadorForm)bean;
			
			if (forma.getNaturaleza().byteValue() != forma.getNaturalezaFormula().byteValue()) 
				return true;
			if (value.indexOf("][") > -1) 
			{
				errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
				forma.setPuntoEdicion("definicionFormula");
				return false;
			}
			
			return true;
		}

		return true;
	}

	public static boolean validateFormulaMacroP(Object bean, ValidatorAction va, Field field, ActionMessages errors, Validator validator, HttpServletRequest request)
	{
		String value = null;
		if (isString(bean))
			value = (String)bean;
		else 
			value = ValidatorUtils.getValueAsString(bean, field.getProperty());
		if (value == null)
			return true;

		if (bean.getClass().getName().equals("com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm"))
		{
			EditarIndicadorForm forma = (EditarIndicadorForm)bean;

			if (forma.getNaturaleza().byteValue() != forma.getNaturalezaFormula().byteValue()) 
				return true;

			if (value.indexOf("[P]:") > -1) 
			{
				forma.setPuntoEdicion("definicionFormula");
				errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
				return false;
			}
			return true;
		}

		return true;
	}

	public static boolean validateFormulaSintaxisGeneral(Object bean, ValidatorAction validatorAction, Field field, ActionMessages errors, Validator validator, HttpServletRequest request)
	{
		String value = null;

		if (isString(bean))
			value = (String)bean;
		else 
			value = ValidatorUtils.getValueAsString(bean, field.getProperty());

		if (value == null) 
			return true;

		if (bean.getClass().getName().equals("com.visiongc.app.strategos.web.struts.indicadores.forms.EditarIndicadorForm"))
		{
			EditarIndicadorForm forma = (EditarIndicadorForm)bean;
			
			if (forma.getNaturaleza().byteValue() != forma.getNaturalezaFormula().byteValue()) 
				return true;
			if ((value != null) && (forma.getInsumosFormula() != null)) 
				value = parseFormula(value, forma.getInsumosFormula());
			if ((value.indexOf("[") == -1) || (value.indexOf("]") == -1)) 
			{
				errors.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
				forma.setPuntoEdicion("definicionFormula");
				return false;
			}

			String[] strMacros = new String[1];
			strMacros[0] = value;
			stripMacros(strMacros);
			value = strMacros[0];

			VgcFormulaEvaluator vgcFormulaEvaluator = new VgcFormulaEvaluator();

			vgcFormulaEvaluator.setExpresion(value);

			if (!vgcFormulaEvaluator.expresionEsValida()) 
			{
				errors.add(field.getKey(), Resources.getActionMessage(validator, request, validatorAction, field));
				forma.setPuntoEdicion("definicionFormula");
				return false;
			}
			
			return true;
		}

		return true;
	}

	private static String parseFormula(String formula, String insumos)
	{
		String[] insumosFormula = insumos.split(";");
		for (int i = 0; i < insumosFormula.length; i++) 
		{
			if (!insumosFormula[i].equals("")) {
				int index1 = insumosFormula[i].indexOf("][");
				int index2 = index1 + insumosFormula[i].substring(index1 + 1, insumosFormula[i].length()).indexOf("]");
				String buscado = "\\\\[" + insumosFormula[i].substring((insumosFormula[i].indexOf("[") + 1), index1) + "\\\\]";
				String reemplazo = insumosFormula[i].substring(index1 + 1, index2 + 2);
				formula = formula.replaceAll(buscado, reemplazo);
			}
		}

		for (int i = 0; i < insumosFormula.length; i++) 
		{
			if (!insumosFormula[i].equals("")) 
			{
				int index1 = insumosFormula[i].indexOf("][");
				int index2 = index1 + insumosFormula[i].substring(index1 + 1, insumosFormula[i].length()).indexOf("]");
				String buscado = "\\\\[" + insumosFormula[i].substring(index1 + 2, index2 + 1) + "\\\\]";
				formula = formula.replaceAll(buscado, "1");
			}
		}

		formula = formula.replaceAll("\\\\[P\\\\]", "1");
		
		return formula;
	}

	public static String reemplazarCorrelativosFormula(String formula, String insumos)
	{
		if (insumos == null) 
			return formula;

		String[] insumosFormula = insumos.split("!;!");
		for (int i = 0; i < insumosFormula.length; i++) 
		{
			if (!insumosFormula[i].equals("")) 
			{
				int index1 = insumosFormula[i].indexOf("][");
				int index2 = index1 + insumosFormula[i].substring(index1 + 1, insumosFormula[i].length()).indexOf("][");
				int index3 = index2 + insumosFormula[i].substring(index2 + 2, insumosFormula[i].length()).indexOf("][");
				String buscado = "\\[" + insumosFormula[i].substring(1, index1) + "\\]";
				String reemplazo = "[" + insumosFormula[i].substring(index1 + 2 + "indicadorId:".length(), index2 + 1) + "." + insumosFormula[i].substring(index2 + 3 + "serieId:".length(), index3 + 2) + "]";
				formula = formula.replaceAll(buscado, reemplazo);
			}
		}

		return formula;
	}

	public static String ConstruirFormula(List<Long> insumos, int serie, String operador)
	{
		String formula = "";
		if (insumos.size() == 0) 
			return formula;
		
		Long insumo;
		for (Iterator<?> id = insumos.iterator(); id.hasNext(); ) 
		{
			insumo = (Long)id.next();
			formula = formula + "[" + insumo.toString() + "." + serie + "]" + operador;
		}
		formula = formula.substring(0, formula.length() - operador.length());

		return formula;
	}
  
	public static String reemplazarIdsPorCorrelativosFormula(String formula, String insumos)
	{
		String[] insumosFormula = insumos.split("!;!");
		for (int i = 0; i < insumosFormula.length; i++) 
		{
			if (!insumosFormula[i].equals("")) 
			{
				int index1 = insumosFormula[i].indexOf("][");
				int index2 = index1 + insumosFormula[i].substring(index1 + 1, insumosFormula[i].length()).indexOf("][");
				int index3 = index2 + insumosFormula[i].substring(index2 + 2, insumosFormula[i].length()).indexOf("][");
				String buscado = "\\[" + insumosFormula[i].substring(index1 + 2 + "indicadorId:".length(), index2 + 1) + "." + insumosFormula[i].substring(index2 + 3 + "serieId:".length(), index3 + 2) + "\\]";
				String reemplazo = insumosFormula[i].substring(0, index1 + 1);
				formula = formula.replaceAll(buscado, reemplazo);
			}
		}

		return formula;
	}
  
	private static boolean stripMacros(String[] entrada) 
	{
		String validos = "-1234567890@#%&S";
		String macrosValidos = "@#%&S";

		String buffer = entrada[0] + " ";
		int valorCentinela;
		do 
		{ 
			valorCentinela = buffer.indexOf(":");
			if (valorCentinela <= -1) 
				continue;

			boolean posicionMacro = false;
			int lastEntry = 0;

			boolean incomplete = false;

			for (int c = valorCentinela + 1; c < buffer.length(); c++) 
			{
				if (validos.indexOf(buffer.substring(c, c + 1)) == -1)
				{
					if (incomplete) 
					{
						entrada[0] = buffer;
						return false;
					}
          
					buffer = buffer.substring(0, valorCentinela) + buffer.substring(c);
					break;
				}
				if (lastEntry == 0) 
				{
					if (macrosValidos.indexOf(buffer.substring(c, c + 1)) == -1) 
						lastEntry = 1;
					else 
						lastEntry = 2;

					if ("-".indexOf(buffer.substring(c, c + 1)) > -1) 
					{
						incomplete = true;
					}
				}
				else
				{
					if ("-".indexOf(buffer.substring(c, c + 1)) > -1) 
					{
						entrada[0] = buffer;
						return false;
					}
					int newEntry;
					if (macrosValidos.indexOf(buffer.substring(c, c + 1)) == -1) 
					{
						newEntry = 1;
						
						incomplete = false;
					} 
					else 
						newEntry = 2;

					if (((lastEntry == 2) && (newEntry == 2)) || ((lastEntry == 1) && (newEntry == 2))) 
					{
						entrada[0] = buffer;
						return false;
					}
					lastEntry = newEntry;
				}

				posicionMacro = true;
			}

			if (!posicionMacro) 
			{
				entrada[0] = buffer;
				return false;
			}
		}
		while (valorCentinela > -1);

		entrada[0] = buffer;
		return true;
	}

	protected static boolean isString(Object o) 
	{
		return o == null ? true : String.class.isInstance(o);
	}
}