package pa.lab5.multimedia;


import pa.lab5.files.json.JsonFileProperty;
import pa.lab5.multimedia.library.exception.MediaException;

import java.util.*;

/**
 * this class implements methods design - to help safely parse a json object to an instance of this class
 *                                      - to help being parsed as json object (by mapping the properties)
 * validates fields (in terms of number, null, and other)
 */
public class Song extends Media {

    /**
     * accepted fields are a group of json properties that can be used from a json object in order to create a class of this instance
     * accepted fields = default fields + custom fields of the class
     */
    public static final List<JsonFileProperty> ACCEPTED_PROPERTIES;
    static
    {
        ACCEPTED_PROPERTIES = new ArrayList<>();

        Arrays.stream(Media.ACCEPTED_PROPERTIES).sequential().forEach(ACCEPTED_PROPERTIES::add);
        ACCEPTED_PROPERTIES.add(JsonFileProperty.GENRE);
    }

    String genre;

    public Song(String author, String name, String path, int releaseYear, int rating, String genre) {
        super(author, name, path, releaseYear, rating);
        this.genre = genre;
    }

    /**
     * constructor -> from a string mapping to a this instance's class
     * each key should point to a property
     * each value gets parsed, validated and passed to the instance
     * @param propertyMap
     * @throws MediaException
     */
    public Song(Map<String, String> propertyMap) throws MediaException {
        super(propertyMap);

        validateFields(propertyMap);

        this.genre = propertyMap.get(JsonFileProperty.GENRE.label);
    }

    /**
     * validates additional field or conditions that deriving classes use - by throwing a MediaException
     * @param propertyMap - property-value pairs
     * @throws MediaException
     */
    @Override
    protected void validateFields(Map<String, String> propertyMap) throws MediaException {
        validateNullException(JsonFileProperty.GENRE, propertyMap.get(JsonFileProperty.GENRE.label));
    }

    /**
     * creates a list of Songs using a list of mapping with: key-value representing the properties of this instance
     * @param mediaMapList -
     * @return
     * @throws MediaException - validates each value (int terms of numeric, null, other)
     */
    public static List<Media> computeStringMaps(List<Map<String, String>> mediaMapList) throws MediaException {
        List<Media> mediaList = new ArrayList<>();
        for(Map<String, String > item : mediaMapList) {
            mediaList.add(new Song(item));
        }

        return mediaList;
    }

    /**
     * helps parse this object to a regulated json string
     * @return - a map from established JsonFileProperty fields to the values of the instance
     */
    @Override
    public Map<String, String> toMap() {
        Map<String, String> map = super.toMap();

        map.put(JsonFileProperty.GENRE.label, this.genre);

        return map;
    }
}
