package pa.lab5.multimedia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pa.lab5.files.json.JsonFileProperty;
import pa.lab5.multimedia.library.exception.*;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * preserve the basic functionality of a media file
 * provides functions for validation (throwable's)
 *
 */
@AllArgsConstructor
@Getter
public abstract class Media implements MediaJson {
    protected static final JsonFileProperty[] ACCEPTED_PROPERTIES = {
            JsonFileProperty.NAME,
            JsonFileProperty.AUTHOR,
            JsonFileProperty.RELEASE_YEAR,
            JsonFileProperty.RATING,
            JsonFileProperty.PATH
    };

    private String author;
    private String name;
    private String path;
    private int releaseYear;
    private int rating;

    /**
     * @param propertyMap - a map from properties to a value that should populate this instance
     *                    - these are from a parsed object so needs validation
     */
    protected Media(Map<String, String> propertyMap) throws MediaException {
        validateDefaultFields(propertyMap);

        this.author = propertyMap.get(JsonFileProperty.AUTHOR.label);
        this.name = propertyMap.get(JsonFileProperty.NAME.label);
        this.path = propertyMap.get(JsonFileProperty.PATH.label);
        this.releaseYear = Integer.parseInt(propertyMap.get(JsonFileProperty.RELEASE_YEAR.label));
        this.rating = Integer.parseInt(propertyMap.get(JsonFileProperty.RATING.label));
    }

    protected void validateDefaultFields(Map<String, String> propertyMap) throws MediaException {
        for(JsonFileProperty field : Media.ACCEPTED_PROPERTIES) {
            validateNullException(field, propertyMap.get(field.label));

            if(field == JsonFileProperty.RATING || field == JsonFileProperty.RELEASE_YEAR) {
                validateNumbericFields(field, propertyMap.get(field.label));
            }


        }

        this.validateMediaPath(propertyMap);
    }

    /**
     * takes care of null exception -> is better than NullException because it's specifying what field has problems
     * @param field
     * @param value
     * @throws MediaFieldNullException
     */
    protected void validateNullException(JsonFileProperty field, String value) throws MediaFieldNullException {
        if(value == null) {
           throw new MediaFieldNullException(field);
        }
    }

    /**
     * validates the path of a file
     * @param propertyMap
     * @throws MediaException
     */
    protected void validateMediaPath(Map<String, String> propertyMap) throws MediaException {
        FileReader file = null;
        String path = propertyMap.get(JsonFileProperty.PATH.label);

        try {
            file = new FileReader(path);
        } catch (Exception e) {
            throw new MediaFileMissingException(e, path, propertyMap.get(JsonFileProperty.NAME.label));
        } finally {
            try {
                if(file != null) {
                    file.close();
                }
            } catch (Exception e) {
                throw new MediaGeneralException(e.getMessage());
            }

        }
    }

    /**
     * validates field as a number
     * -> takes care if a parse int fails -> specifies better which field is at fault
     * @param field
     * @param value
     * @throws MediaNumericFieldException
     */
    protected void validateNumbericFields(JsonFileProperty field, String value) throws MediaNumericFieldException {
        try {
            int integerValue =  Integer.parseInt(value);
        } catch (Exception e) {
            throw new MediaNumericFieldException(field, value, e);
        }
    }

    /**
     * validates additional field or conditions that deriving classes use - by throwing a MediaException
     * @param propertyMap - property-value pairs
     * @throws MediaException
     */
    protected abstract void validateFields(Map<String, String> propertyMap) throws MediaException;

    /**
     * helps parse this object to a regulated json string
     * @return - a map from established JsonFileProperty fields to the values of the instance
     */
    @Override
    public Map<String, String> toMap() {
        Map<String, String> mapProperties = new LinkedHashMap<>();

        mapProperties.put(JsonFileProperty.AUTHOR.label, author);
        mapProperties.put(JsonFileProperty.NAME.label, name);
        mapProperties.put(JsonFileProperty.PATH.label, path);
        mapProperties.put(JsonFileProperty.RELEASE_YEAR.label, Integer.toString(releaseYear));
        mapProperties.put(JsonFileProperty.RATING.label, Integer.toString(rating));

        return mapProperties;
    }



    @Override
    public String toString() {
        return "Media{" +
                "author='" + author + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", rating=" + rating +
                '}';
    }
}
