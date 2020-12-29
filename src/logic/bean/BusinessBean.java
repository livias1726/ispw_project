package logic.bean;

import java.util.List;

import logic.application.control.ViewBusinessControl;
import logic.exceptions.DatabaseFailureException;

public class BusinessBean {
	private String category;
	private String name;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public List<String> getBusinesses() throws DatabaseFailureException{
		return ViewBusinessControl.getInstance().retrieveBusinesses();
	}
}
