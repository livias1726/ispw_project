package logic.application.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.application.SessionFacade;
import logic.domain.Address;
import logic.domain.Company;
import logic.domain.Offer;
import logic.exceptions.DatabaseFailureException;
import logic.presentation.bean.AddressBean;
import logic.presentation.bean.CompanyBean;
import logic.presentation.bean.JobBean;
import logic.presentation.bean.OfferBean;

public class RecruiterAccountControl {
	
	private static RecruiterAccountControl instance = null;

    private RecruiterAccountControl() {
    	/*Default constructor*/
    }

    public static RecruiterAccountControl getInstance() {
        if(instance == null) {
        	instance = new RecruiterAccountControl();
        }

        return instance;
    }

	public CompanyBean retrieveCompanyInfo() throws DatabaseFailureException {
		Company company = null;
		
		try {
			company = new Company().getCompanyInfo(SessionFacade.getSession().getID());
		} catch (SQLException e) {
			throw new DatabaseFailureException();
		}
    	
		if(company != null) {
			CompanyBean bean = new CompanyBean();
			bean.setName(company.getName());
			bean.setDescription(company.getDescription());
			
			List<AddressBean> branches = new ArrayList<>();
			for(Address i: company.getBranches()) {
				branches.add(AddressControl.getInstance().extractAddressBean(i));
			}
			bean.setBranches(branches);
			
			return bean;
		}
		
		return null;
	}

	public ObservableList<OfferBean> retrievePublishedOffers() throws DatabaseFailureException {
		List<Offer> list = null;
		Offer offer = new Offer();
		
		try {
			list = offer.getOffersByRecruiter(SessionFacade.getSession().getID());
		} catch (SQLException e) {
			Logger.getLogger(RecruiterAccountControl.class.getName()).log(Level.SEVERE, null, e);
			throw new DatabaseFailureException();
		}
		
		ObservableList<OfferBean> dest = FXCollections.observableArrayList();
		for(Offer i: list) {
			OfferBean bean = new OfferBean();		
			bean.setId(i.getId());
			
			JobBean jobBean = new JobBean();
			jobBean.setName(i.getPosition().getName());
			bean.setPosition(jobBean);
			
			bean.setUpload(i.getUpload());
			bean.setExpiration(i.getExpiration());
			bean.setCandidates(i.getCandidates());
			
			dest.add(bean);
		}
		
		return dest;
	}

	public void changeCompanyInfo(CompanyBean bean) throws DatabaseFailureException {
		Company company = new Company(bean.getName());
    	company.setDescription(bean.getDescription());
    
    	List<Address> branches = new ArrayList<>();
    	for(AddressBean i: bean.getBranches()) {
			branches.add(AddressControl.getInstance().extractAddress(i));
    	} 	
    	company.setBranches(branches);
    	
		try {
			company.saveCompanyInfoOnDB(SessionFacade.getSession().getID());
		} catch (SQLException e) {
			throw new DatabaseFailureException();
		}
	}
}
