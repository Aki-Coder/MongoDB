package nosql.mongodb.projekat.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Genre {

	@JsonProperty("id")
	public int id;

	@JsonProperty("name")
	public String name;

	public Genre() {

	}

	public Genre(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
