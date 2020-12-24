package logic.bean;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

import logic.application.control.ViewOfferControl;

public class OfferBean {

	private String companyName;
	private JobBean position;
	private String taskDescription;
	private List<String> requirements;
	private AddressBean branch;
	private Time start;
	private Time finish;
	private float baseSalary;
	private LocalDate expiration;
	private LocalDate upload;
	
	public OfferBean() {
		/*Constructor*/
	}
	
	public JobBean getPosition() {
		return position;
	}

	public void setPosition(JobBean position) {
		this.position = position;
	}

	public List<String> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<String> requirements) {
		this.requirements = requirements;
	}

	public AddressBean getBranch() {
		return branch;
	}

	public void setBranch(AddressBean branch) {
		this.branch = branch;
	}

	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getFinish() {
		return finish;
	}

	public void setFinish(Time finish) {
		this.finish = finish;
	}

	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public LocalDate getExpiration() {
		return expiration;
	}

	public void setUpload(LocalDate upload) {
		this.upload = upload;
	}
	
	public LocalDate getUpload() {
		return upload;
	}

	public void setExpiration(LocalDate expiration) {
		this.expiration = expiration;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public float getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(float baseSalary) {
		this.baseSalary = baseSalary;
	}
	
	public List<OfferBean> getOffers(CountryBean country, JobBean job){
		ViewOfferControl controller = ViewOfferControl.getInstance();
		return controller.retrieveOffers(country, job);
	}
	
	public List<OfferBean> getOffers(CountryBean country){
		ViewOfferControl controller = ViewOfferControl.getInstance();
		return controller.retrieveOffersByCountry(country);
	}
	
	public List<OfferBean> getOffers(JobBean job){
		ViewOfferControl controller = ViewOfferControl.getInstance();
		return controller.retrieveOffersByJob(job);
	}
}
