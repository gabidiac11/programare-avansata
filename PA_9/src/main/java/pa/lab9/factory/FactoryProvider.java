package pa.lab9.factory;

import com.google.protobuf.TextFormat;
import javafx.scene.media.MediaException;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import pa.lab9.jdbc.FactoryJdbc;
import pa.lab9.jpa.factory.FactoryJpa;

/**
 * chooses the selected implementation factory (jpa or jdbc)
 */
public class FactoryProvider {
    public static AbstractFactory getConfiguredFactory() throws Exception {
        FileReader file = new FileReader("src\\main\\resources\\config.json");
        Object obj = new JSONParser().parse(file);
        JSONObject jsonObject = (JSONObject) obj;

        if(jsonObject.get("database_connectivity").equals(ConnectionType.JPA.value)) {
            return new FactoryJpa();
        }

        if(jsonObject.get("database_connectivity").equals(ConnectionType.JDBC.value)) {
            return new FactoryJdbc();
        }

        throw new Exception("WRONG CONFIGURATION");
    }
}
