package pa.lab8.optional.cinema;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import pa.lab8.optional.cinema.connection.Connection;
import pa.lab8.optional.cinema.dao.GenreDao;
import pa.lab8.optional.cinema.dao.MovieDao;
import pa.lab8.optional.cinema.dao.PersonDao;
import pa.lab8.optional.cinema.models.GenreModel;

import static pa.lab8.optional.cinema.assets.csv.columnMap.MovieColumn.*;
import static pa.lab8.optional.cinema.assets.csv.columnMap.JobColumn.*;

public class DatabaseManager {

    private static List<GenreModel> createOrSelectGenre(String name) throws SQLException {
        List<GenreModel> genreModelList = GenreDao.findByName(name);
        if(genreModelList.size() == 0) {
            genreModelList.add(GenreDao.createGenre(name));
        }

        return genreModelList;
    }

    private static void emptyAllTables() throws SQLException {
        for(String sqlString : new String[]{
                "DELETE FROM movie",
                "DELETE FROM genre",
                "DELETE FROM job",
                "DELETE FROM movie_genre",
                "DELETE FROM persons"
            }) {
                PreparedStatement preparedStmt = Connection.getInstance().getCon().prepareStatement(sqlString);
                preparedStmt.executeUpdate();
            }
    }

    public static String[] escapeRow(String[] row) {
        for(int i = 0; i < row.length; i++) {
            if(row[i] != null) {
                row[i] = row[i].replaceAll("'", "-");
            }
        }

        return row;
    }

    public static void populateDatabaseFromCsv() throws SQLException, IOException, CsvValidationException {
        emptyAllTables();

//        populateMovies();
        populateJobs();
        populatePersons();
    }

    private static void populateMovies() throws SQLException, IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("src\\main\\java\\pa\\lab8\\optional\\cinema\\assets\\csv\\movies.csv"));

        String[] row;
        reader.skip(1);
        while ((row = reader.readNext()) != null) {
            row = escapeRow(row);

            /* create a movie instance that records itself to the database */
            System.out.println(MovieDao.createMovie(
                    row[MOVIE_ID.indexColumn],
                    row[MOVIE_TITLE.indexColumn],
                    row[RELEASE_DATE.indexColumn],
                    Integer.parseInt(row[MOVIE_DURATION.indexColumn]),
                    Integer.parseInt(row[MOVIE_DURATION.indexColumn]),
                    createOrSelectGenre(row[GENRE_NAME.indexColumn])
            ).toString());
        }
        System.out.println("All movies were inserted!");
    }

    private static void populatePersons() throws SQLException, IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("src\\main\\java\\pa\\lab8\\optional\\cinema\\assets\\csv\\persons.csv"));

        String[] row;
        reader.skip(1);
        while ((row = reader.readNext()) != null) {
            row = escapeRow(row);

            /* create a movie instance that records itself to the database */
            System.out.println(MovieDao.createMovie(
                    row[MOVIE_ID.indexColumn],
                    row[MOVIE_TITLE.indexColumn],
                    row[RELEASE_DATE.indexColumn],
                    Integer.parseInt(row[MOVIE_DURATION.indexColumn]),
                    Integer.parseInt(row[MOVIE_DURATION.indexColumn]),
                    createOrSelectGenre(row[GENRE_NAME.indexColumn])
            ).toString());
        }
        System.out.println("All movies were inserted!");
    }

    private static void populateJobs() throws SQLException, IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("src\\main\\java\\pa\\lab8\\optional\\cinema\\assets\\csv\\jobs.csv"));

        String[] row;
        reader.skip(1);
        while ((row = reader.readNext()) != null) {
            row = escapeRow(row);
            System.out.println(PersonDao.createJob(
                    row[JOB_MOVIE_ID.indexColumn],
                    row[JOB_PERSON_ID.indexColumn],
                    row[JOB_NAME.indexColumn]
            ));
        }
        System.out.println("All movies were inserted!");
    }
}
