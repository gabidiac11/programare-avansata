package pa.lab8.optional;


import pa.lab8.optional.cinema.DatabaseManager;
import pa.lab8.optional.cinema.connection.Connection;
import pa.lab8.optional.cinema.dao.GenreDao;
import pa.lab8.optional.cinema.dao.MovieDao;
import pa.lab8.optional.cinema.models.GenreModel;
import pa.lab8.optional.cinema.models.MovieModel;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.SQLException;

public class Main {
    static Logger logger = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) {
        logger.setLevel(Level.INFO);

        try {

            DatabaseManager.populateDatabaseFromCsv();

        } catch (SQLException exception) {
//            logger.log(Level.SEVERE, exception.getMessage());
            System.out.println(exception.getMessage());
        } catch (Exception exception) {
//            logger.log(Level.SEVERE, exception.getMessage());
            System.out.println(exception.getMessage());
        }

    }
}