package pa.lab8.compulsory.cinema.connection;

import lombok.Getter;

import java.sql.DriverManager;

public class Connection {
    @Getter
    private java.sql.Connection con;

    private Connection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pa_database",
                    "root",
                    ""
            );

        } catch (Exception e){
            System.out.println(e);
        }
    }

    private static Connection instance = null;

    public static synchronized Connection getInstance() {
        if(instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    @Override
    protected void finalize() throws Throwable {
        con.close();
    }
}
