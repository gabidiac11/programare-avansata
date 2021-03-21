package pa.lab5.multimedia.library;

import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.FormattedMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.Level;

import pa.lab5.multimedia.Book;
import pa.lab5.multimedia.Song;
import pa.lab5.multimedia.Media;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import pa.lab5.files.json.JsonFileProperty;
import pa.lab5.multimedia.MediaJson;
import pa.lab5.multimedia.library.exception.MediaException;
import pa.lab5.multimedia.library.exception.MediaPlayableNotFound;

/**
 * - receives a path to json file (see Catalog.load() for info about this)
 * - creates a list of media (songs and books) by parsing this json
 * - opens files
 * - can add new files
 * - can save the new catalog configuration at the path provided in constructor
 */
public class Catalog {
    static Logger log = LogManager.getLogger(Catalog.class);
    private static final Marker CATALOG_MARKER = MarkerManager.getMarker("CATALOG");

    List<Media> mediaList = new ArrayList<>();
    private final String path;

    public Catalog(String path) throws ParseException, IOException, MediaException {
        this.path = path;
        this.load(path);
    }

    /**
     * creates a list of media by reading and parsing a json file of this form:
     * {
     *   "books": [
     *     {
     *       "author": "Feodor Dostoevsky",
     *       "name": "Insermari din subterana",
     *       "path": "src\\main\\java\\pa\\lab5\\files\\books\\Feodor_Mihailovici_Dostoievski_Insemnari_Din_Subterana.pdf",
     *       "year": "1846",
     *       "rating": "1000000",
     *       "publication": "POLIROM"
     *     }
     *   ],
     *   "songs": [
     *     {
     *       "author": "Alexandrov Ensemble",
     *       "name": "To serve Russia",
     *       "path": "src\\main\\java\\pa\\lab5\\files\\songs\\Russian_Federation_1991_Military_March_To_Serve_Russia.mp3",
     *       "year": "1945",
     *       "rating": "1000000",
     *       "genre": "Classical, folk tunes, hymns, operatic arias, popular music"
     *     }
     *   ]
     * }
     * @param  - the path to the json file
     * @throws IOException
     * @throws ParseException
     * @throws MediaException - case: a fields is null, a field is not numerical, a path from a media json object does not exist
     */
    void load(String path) throws IOException, ParseException, MediaException {
        FileReader file = null;
        try {
            file = new FileReader(path);

            Object obj = new JSONParser().parse(file);
            JSONObject jsonObject = (JSONObject) obj;


            List<Media> mediaList = Book.computeStringMaps(
                    createMediaListFromArrayJson((JSONArray) jsonObject.get("books"), Book.ACCEPTED_PROPERTIES)
            );

            Song.computeStringMaps(
                    createMediaListFromArrayJson((JSONArray) jsonObject.get("songs"), Song.ACCEPTED_PROPERTIES)
            ).stream().forEach(item -> {
                mediaList.add(item);
            });

            this.mediaList = mediaList;
        } catch (IOException|ParseException|MediaException e) {
            throw e;
        } finally {
            if(file != null) {
                file.close();
            }
        }
    }

    /**
     * creates a list of Map from accepted properties to values
     * these are used for passing forward to a Media class that uses them to create new instances
     * @param jsonArray -
     * @param ACCEPTED_PROPERTIES - a list of accepted properties in a enum form
     * @return -
     */
    private static List<Map<String, String>> createMediaListFromArrayJson(JSONArray jsonArray, List<JsonFileProperty> ACCEPTED_PROPERTIES) {
        List<Map<String, String>> mediaMapList = new ArrayList<>();

        Iterator<Map.Entry> itr1;
        Iterator itr2 = jsonArray.iterator();

        while (itr2.hasNext()) {
            itr1 = ((Map) itr2.next()).entrySet().iterator();

            Map<String, String> data = new HashMap<>();

            while (itr1.hasNext()) {
                Map.Entry pair = itr1.next();
                String property = (String) pair.getKey();

                if (ACCEPTED_PROPERTIES.stream()
                        .anyMatch(item -> property.equals(item.label))) {
                    data.put(property, String.valueOf(pair.getValue()));
                }
            }

            mediaMapList.add(data);
        }

        return mediaMapList;
    }

    /**
     * extracts Book instances from the list
     * @return
     */
    private List<Media> getBooks() {
        List<Media> list = new ArrayList<>();

        this.mediaList.
                stream().
                filter(item -> item instanceof Book)
                .forEach(item -> {
                    list.add(item);
                });

        return list;
    }

    /**
     * extracts Song instances from the list
     * @return
     */
    private List<Media> getSongs() {
        List<Media> list = new ArrayList<>();

        this.mediaList.
                stream().
                filter(item -> item instanceof Song)
                .forEach(item -> {
                    list.add(item);
                });

        return list;
    }

    /**
     * creates a JSONArray of media items to be easily printed as json
     * @param mediaList
     * @return
     */
    public static JSONArray toJsonArray(List<Media> mediaList) {
        JSONArray books = new JSONArray();

        mediaList
                .stream()
                .forEach(item -> {
                    books.add(item.toMap());
                });

        return books;
    }

    public void list() {
        JSONObject jo = new JSONObject();
        jo.put("list", toJsonArray(this.mediaList));

        log.always().log(new FormattedMessage(String.format("\n--------------------------------------LISTA----------------------------------------------\n%s\n\n",
                MediaJson.jsonToPretty(jo.toJSONString())
        )));
    }

    /**
     * adds a new media object by code
     * @param media
     */
    public void add(Media media) {
        /* replace element with the same pathname if there is one present */
        for(int i = 0; i < this.mediaList.size(); i++) {
            if(this.mediaList.get(i).getPath().equals(media.getPath())) {
                this.mediaList.set(i, media);
                return;
            }
        }
        /* otherwise push new one */
        this.mediaList.add(media);
    }

    /**
     * searches and opens a media file by name that is a song
     * @param name
     * @throws IOException
     * @throws MediaException
     */
    public void playSongByName(@NonNull String name) throws IOException, MediaException {
        for(Media item : this.mediaList) {
            if(item instanceof Song && item.getName().contains(name)) {
                File file = new File(item.getPath());
                Desktop.getDesktop().open(file);

                return;
            }
        }
        throw new MediaPlayableNotFound(name);
    }

    /**
     ** searches and opens a media file by name that is a book
     * @param name
     * @throws IOException
     * @throws MediaException
     */
    public void openBookByName(@NonNull String name) throws IOException, MediaException {
        for(Media item : this.mediaList) {
            if(item instanceof Book && item.getName().contains(name)) {
                File file = new File(item.getPath());
                Desktop.getDesktop().open(file);
                return;
            }
        }
        throw new MediaPlayableNotFound(name);
    }

    public void save() throws FileNotFoundException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(this.path);
            pw.write(this.toString());
        } catch (FileNotFoundException e) {
            throw e;
        } finally {
            if(pw != null) {
                pw.flush();
                pw.close();
            }
        }
    }

    /**
     * creates a json object that can be parsed to string
     * this json object has the CURRENT STATE (structurally equivalent to the one from pa.lab5.files.Catalog.json)
     * @return
     */
    private JSONObject toJSONObject() {
        if(this.mediaList == null) {
            return new JSONObject();
        }

        JSONObject jo = new JSONObject();
        jo.put("books", toJsonArray(this.getBooks()));
        jo.put("songs", toJsonArray(this.getSongs()));

        return jo;
    }

    /**
     * @return - a json string with CURRENT STATE (structurally equivalent to the one from pa.lab5.files.Catalog.json)
     */
    @Override
    public String toString() {
        return MediaJson.jsonToPretty(toJSONObject().toString());
    }
}
