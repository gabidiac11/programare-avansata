package pa.lab5.multimedia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;

import java.util.List;
import java.util.Map;

public interface MediaJson {
    Map<String, String> toMap();

    static String jsonToPretty(String stringJson) {
        Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .create();

        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(stringJson);
        return  gson.toJson(je);
    }
}
