package nosql.mongodb.projekat.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;

public class Company extends BasicDBObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	

	
	

}
