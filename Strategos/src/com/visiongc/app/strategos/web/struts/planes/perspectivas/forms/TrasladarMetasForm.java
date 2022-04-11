package com.visiongc.app.strategos.web.struts.planes.perspectivas.forms;

import com.visiongc.app.strategos.model.util.Frecuencia;
import com.visiongc.app.strategos.planes.model.util.TipoCalculoPerspectiva;
import com.visiongc.framework.web.struts.forms.EditarObjetoForm;

public class TrasladarMetasForm extends EditarObjetoForm
{
	  static final long serialVersionUID = 0L;
	  private Long perspectivaId;
	  private Long planId;
	  private Integer ano;
	  private Integer anoFinal;

	    
	  public Long getPerspectivaId() {
		return perspectivaId;
	  }

      public void setPerspectivaId(Long perspectivaId) {
		this.perspectivaId = perspectivaId;
	  }

      public Long getPlanId() {
		return planId;
      }

      public void setPlanId(Long planId) {
		this.planId = planId;
      }

      public Integer getAno() {
		return ano;
      }

      public void setAno(Integer ano) {
		this.ano = ano;
      }

      public Integer getAnoFinal() {
		return anoFinal;
      }

      public void setAnoFinal(Integer anoFinal) {
		this.anoFinal = anoFinal;
      }
      
      public void clear() 
	  {
		    this.perspectivaId = new Long(0L);
		    this.planId = null;
		    this.anoFinal = null;
		    this.ano = null;
		   
	  }
}