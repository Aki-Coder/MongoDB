package nosql.monogodb.projekat.main;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import nosql.mongodb.projekat.model.Company;
import nosql.mongodb.projekat.model.Country;
import nosql.mongodb.projekat.model.Genre;
import nosql.mongodb.projekat.model.Keywoard;
import nosql.mongodb.projekat.model.Movie;
import nosql.mongodb.projekat.model.Production;
import nosql.mongodb.projekat.model.Vote;

public class CsvToJson {

	public static List<Movie> createJson() {
		try {

			// kreiranje objekta fileReader sa CSV fajlom kao parametrom
			FileReader fileReader = new FileReader("tmdb_5000_movies.csv");

			// kreiranje csvReader objekta, fileReader kao parametar
			// skipujemo header fajla(kolone)
			CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(1).build();

			// niz linija u fajlu
			String[] nextLine;

			// za rad sa datumom
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			ObjectMapper om = new ObjectMapper();

			int i = 1;

			List<Movie> movies = new ArrayList<Movie>();

			// citanje podataka liniju po liniju
			while ((nextLine = csvReader.readNext()) != null) {
				System.out.println("----- Linija u fajlu ----- " + i + "\n");
				System.out.println("----- Duzina linije -----" + nextLine.length + "\n");

				Movie m = new Movie();
				long budget = Long.parseLong(nextLine[0]);
				m.setBudget(budget);
				System.out.println(m.getBudget());

				ArrayList<Genre> genres = new ArrayList<Genre>();
				if (nextLine[1] != null && nextLine[1] != "") {
					// uklanjamo [ i ]
					String json = nextLine[1].substring(1, nextLine[1].length() - 1);
					// napravimo stringove objekata
					String[] objects = json.split(",");
					for (String object : objects) {

						// Od string objekta pravimo objekat
						Genre g = om.readValue(object, Genre.class);
						genres.add(g);
						System.out.println(g.id + g.name);
					}
					m.setGenres(genres);
				}

				if (nextLine[2] != null && nextLine[2] != "") {
					m.setHomepage(nextLine[2]);
				}

				m.setId(Integer.parseInt(nextLine[3]));

				ArrayList<Keywoard> keywoards = new ArrayList<Keywoard>();
				if (nextLine[4] != null && nextLine[4] != "") {
					String json = nextLine[4].substring(1, nextLine[4].length() - 1);
					String[] objects = json.split(",");
					for (String o : objects) {
						Keywoard k = om.readValue(o, Keywoard.class);
						keywoards.add(k);
					}
					m.setKeywoards(keywoards);
				}

				m.setOriginal_language(nextLine[5]);
				m.setOriginal_title(nextLine[6]);
				m.setOverview(nextLine[7]);
				m.setPopularity(Double.parseDouble(nextLine[8]));

				Production p = new Production();
				ArrayList<Company> companies = new ArrayList<Company>();
				if (nextLine[9] != null && nextLine[9] != "") {
					String json = nextLine[9].substring(1, nextLine[9].length() - 1);
					String[] objects = json.split(",");
					for (String o : objects) {
						Company c = om.readValue(o, Company.class);
						companies.add(c);
					}
					p.setCompanies(companies);
				}

				ArrayList<Country> countries = new ArrayList<Country>();
				if (nextLine[10] != null && nextLine[10] != null) {
					String json = nextLine[10].substring(1, nextLine[10].length() - 1);
					String[] obStrings = json.split(",");
					for (String o : obStrings) {
						Country c = om.readValue(o, Country.class);
						countries.add(c);
					}
					p.setCountries(countries);
				}
				try {
					m.setRelease_date(sdf.parse(nextLine[11]));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				m.setRevenue(Long.parseLong(nextLine[12]));
				m.setRuntime(Integer.parseInt(nextLine[13]));
				m.setStatus(nextLine[14]);
				m.setTagline(nextLine[15]);
				m.setTitle(nextLine[16]);

				Vote v = new Vote();
				int vote_average = Integer.parseInt(nextLine[18]);
				int vote_count = Integer.parseInt(nextLine[19]);

				movies.add(m);
				System.out.println("----- -----");
				i++;

			}

			csvReader.close();

			return movies;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static boolean upisiUBazu(List<Movie> movies) {
		try {
			ConnectionString connectionString = new ConnectionString("mongodb://nastava.is.pmf.uns.ac.rs");
			CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
			CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
					pojoCodecRegistry);
			MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
					.codecRegistry(codecRegistry).build();

			MongoClient mongoClient = MongoClients.create(clientSettings);
			MongoDatabase db = mongoClient.getDatabase("nosql");
			MongoCollection<Movie> col = db.getCollection("Movies", Movie.class);
			int i = 1;
			for (Movie m : movies) {
				System.out.println("Inserting movies " + i);
				col.insertOne(m);
				i++;
			}
			mongoClient.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		List<Movie> movies = createJson();
		upisiUBazu(movies);

	}

}
