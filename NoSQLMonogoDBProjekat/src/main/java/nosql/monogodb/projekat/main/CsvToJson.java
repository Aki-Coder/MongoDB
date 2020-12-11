package nosql.monogodb.projekat.main;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import nosql.mongodb.projekat.model.Movie;

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

			int i = 1;

			List<Movie> movies = new ArrayList<Movie>();

			// citanje podataka liniju po liniju
			while ((nextLine = csvReader.readNext()) != null) {
				System.out.println("----- Linija u fajlu ----- " + i + "\n");
				System.out.println("----- Duzina linije -----" + nextLine.length + "\n");
				
				Movie m = new Movie();
				int id = Integer.parseInt(nextLine[0]);
				m.setId(id);
				System.out.println(m.getId());
			}

			return movies;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {

	}

}