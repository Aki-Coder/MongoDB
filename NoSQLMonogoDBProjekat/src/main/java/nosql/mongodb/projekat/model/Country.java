package nosql.mongodb.projekat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;

@JsonFormat
public class Country extends BasicDBObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("iso_3166_1")
	private String iso_3166_1;
	
	@JsonProperty("name")
	private String name;

	@Override
	public String toString() {
		return "Country [iso_3166_1=" + iso_3166_1 + ", name=" + name + "]";
	}
	
	

	
	

}
