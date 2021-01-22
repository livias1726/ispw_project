package logic.application.control;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.application.adapter.EuroAdapter;
import logic.application.adapter.PoundAdapter;
import logic.application.adapter.USDAdapter;
import logic.domain.BusinessInCountry;
import logic.domain.Country;
import logic.exceptions.DatabaseFailureException;
import logic.exceptions.InvalidFieldException;
import logic.presentation.bean.BusinessInCountryBean;
import logic.service.AbstractFactory;
import logic.service.Factory;
import logic.service.Types;

public class CalculateFeasibilityControl {
	
	private static CalculateFeasibilityControl instance = null;

    private CalculateFeasibilityControl() {
    	/*Singleton*/
    }

    public static CalculateFeasibilityControl getInstance() {
        if(instance == null) {
        	instance = new CalculateFeasibilityControl();
        }

        return instance;
    }

	public Float retrieveBusinessFeasibility(BusinessInCountryBean business, String budget) throws DatabaseFailureException{
		AbstractFactory factoryBus = Factory.getInstance().getObject(Types.BUSINESSINCOUNTRY);
		BusinessInCountry bus = (BusinessInCountry)factoryBus.createObject();
		
		bus.setId(business.getId());
		
		AbstractFactory factoryCou = Factory.getInstance().getObject(Types.COUNTRY);
		Country country = (Country)factoryCou.createObject();
		
		country.setName(business.getCountry().getName());
		bus.setCountry(country);

		try {
			bus.getBusinessFeasibility();

			business.getCountry().setLivingExpense(bus.getCountry().getLivingExpense());
			business.getCountry().setExampleCity(bus.getCountry().getExampleCity());
			business.setStartExpense(bus.getStartExpense());
			business.setTaxes(bus.getTaxes());
			
			return calculateResult(Float.valueOf(budget), business);

		} catch (SQLException e) {
			Logger.getLogger(CalculateFeasibilityControl.class.getName()).log(Level.SEVERE, "SQLException thrown", e);
			throw new DatabaseFailureException();
		}
	}
	
	public Float convertValue(Float val, String newCurr, String oldCurr) throws InvalidFieldException{
		
		switch(newCurr) {
			case "�":
			case "EUR":
				EuroAdapter euroAdapter = new EuroAdapter(oldCurr);
				return euroAdapter.value(val);
			case "�":
			case "GBP":
				PoundAdapter poundAdapter = new PoundAdapter(oldCurr);
				return poundAdapter.value(val);
			case "$":
			case "USD":
				USDAdapter usdAdapter = new USDAdapter(oldCurr);
				return usdAdapter.value(val);
			default:
				throw new InvalidFieldException("Currency not recognized.");
		}
	}
	
	public Float calculateResult(Float budget, BusinessInCountryBean business) {
		
		Float result = budget - business.getStartExpense() - business.getCountry().getLivingExpense();
		Float earn = business.getAverageEarnings();
		
		for(Float i: business.getTaxes()) {
			business.setAverageEarnings(earn*(1-(i/100)));
			earn = business.getAverageEarnings();
		}
		
		result = result + business.getAverageEarnings() - business.getAverageCost();
		
		return result;
	}
}