package pa.lab9.jdbc.dao;

import pa.lab9.jdbc.connection.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PersonDao {
    private static java.sql.Connection con = Connection.getInstance().getCon();

    public static String createJob(String movieId, String personName, String jobName) throws SQLException {
        final String sqlString = String.format("INSERT INTO job(imdb_title_id, imdb_name_id, job) VALUES ('%s', '%s', '%s')",
                movieId,
                personName,
                jobName
        );

        PreparedStatement preparedStmt = con.prepareStatement(
                sqlString, Statement.RETURN_GENERATED_KEYS);

        preparedStmt.executeUpdate();

        return sqlString;
    }
}
