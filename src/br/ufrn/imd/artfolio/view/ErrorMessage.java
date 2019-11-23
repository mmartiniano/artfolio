package br.ufrn.imd.artfolio.view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ErrorMessage {
	
	private FacesMessage msg;
	
	public ErrorMessage(String msg) {
		this.msg = new FacesMessage(msg);
		this.msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage("", this.msg);
	}

}
