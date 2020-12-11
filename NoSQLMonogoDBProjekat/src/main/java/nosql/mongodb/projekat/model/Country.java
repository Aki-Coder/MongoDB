package nosql.mongodb.projekat.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
	
	@JsonProperty("iso_3166_1")
	private int iso_3166_1;
	@JsonProperty("name")
	private String name;

	
	

}
