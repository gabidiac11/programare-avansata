package pa.lab5.multimedia;


import pa.lab5.files.json.JsonFileProperty;
import pa.lab5.multimedia.library.exception.MediaException;

import java.util.*;

/**
 * this class implements methods design - to help safely parse a json object to an instance of this class
 *                                      - to help being parsed as json object (by mapping the properties)
 * validates fields (in terms of number, null, and other)
 */
public class Book extends Media {

    /**
     * accepted fields are a group of json properties that can be used from a json object in order to create a class of this instance
     * accepted fields = default fields + custom fields of the class
     */
    public static final List<JsonFileProperty> ACCEPTED_PROPERTIES;
    static
    {
        ACCEPTED_PROPERTIES = new ArrayList<>();

        Arrays.stream(Media.ACCEPTED_PROPERTIES).sequential().forEach(ACCEPTED_PROPERTIES::add);
        ACCEPTED_PROPERTIES.add(JsonFileProperty.PUBLICATION);
    }

    String publication;

    public Book(String author, String name, String path, int releaseYear, int rating, String publication) {
        super(author, name, path, releaseYear, rating);
        this.publication = publication;
    }

    /**
     * constructor -> from a string mapping to a book object
     * each key should point to a property
     * each value gets parsed, validated and passed to the instance
     * @param propertyMap
     * @throws MediaException
     */
    public Book(Map<String, String> propertyMap) throws MediaException {
        super(propertyMap);

        validateFields(propertyMap);

        this.publication = propertyMap.get(JsonFileProperty.PUBLICATION.label);
    }

    /**
     * validates additional field or conditions that deriving classes use - by throwing a MediaException
     * @param propertyMap - property-value pairs
     * @throws MediaException
     */
    @Override
    protected void validateFields(Map<String, String> propertyMap) throws MediaException {
        validateNullException(JsonFileProperty.PUBLICATION, propertyMap.get(JsonFileProperty.PUBLICATION.label));
    }

    /**
     * creates a list of Books using a list of mapping with: key-value representing the properties of this instance
     * @param mediaMapList -
     * @return
     * @throws MediaException - validates each value (int terms of numeric, null, other)
     */
    public static List<Media> computeStringMaps(List<Map<String, String>> mediaMapList) throws MediaException {
        List<Media> mediaList = new ArrayList<>();
        for(Map<String, String > item : mediaMapList) {
            mediaList.add(new Book(item));
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

        map.put(JsonFileProperty.PUBLICATION.label, this.publication);

        return map;
    }
}
