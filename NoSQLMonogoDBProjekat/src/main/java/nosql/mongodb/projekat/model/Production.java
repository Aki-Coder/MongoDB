package nosql.mongodb.projekat.model;

import java.util.List;

public class Production {
	
	private List<Country> countries;
	private List<Company> companies;
	
	
	public List<Country> getCountries() {
		return countries;
	}
	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}
	public List<Company> getCompanies() {
		return companies;
	}
	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

}
