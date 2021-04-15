package pa.lab8.compulsory;

import pa.lab8.cinema.controllers.GenreController;
import pa.lab8.cinema.controllers.MovieController;
import pa.lab8.cinema.models.Genre;
import pa.lab8.cinema.models.Movie;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.SQLException;

public class Main {
    static Logger logger = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) {
        logger.setLevel(Level.INFO);

        try {
            Genre genre = GenreController.createGenre("Comedy");

            Movie movie = MovieController.createMovie("2 birds 1 cup of tea", "2007-01-05", 3_720_000 /* 62 minutes */, -1, genre);

            logger.log(Level.INFO, movie.toString());

        } catch (SQLException sqlException) {
            logger.log(Level.SEVERE, sqlException.toString());

        } catch (Exception exception) {
            logger.log(Level.SEVERE, exception.getMessage());
        }

    }
}
