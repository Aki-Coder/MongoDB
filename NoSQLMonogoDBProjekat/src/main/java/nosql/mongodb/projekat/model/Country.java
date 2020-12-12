package nosql.mongodb.projekat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
	
	@JsonProperty("iso_3166_1")
	private String iso_3166_1;
	
	@JsonProperty("name")
	private String name;

	
	

}
