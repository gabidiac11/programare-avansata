
# Lab 4
## _Advanced Programming 2021_
[![N|Solid](https://plati-taxe.uaic.ro/img/logo-retina1.png)](https://www.info.uaic.ro/)

Diac P. Gabriel
2A2

This piece is aiming at offering a better understanding concerning the following aspects of the source code:

- problem-solving strategies that were used
- justification for choices made regarding object modeling 
- what exactly are the exercises that I assume are completed (~✔️), partially completed (⏰) or are not started (❌)

## Addressed exercises 
### Compulsory (1p) 

Requirements and their status:

 #### - Create an object-oriented model of the problem. You should have at least the following classes: Catalog and two item classes at your choice. Consider using an interface or an abstract class in order to describe the items in a catalog.~✔️
 I've created a abstract class `pa.lab5.multimedia.Media` that is extended by other classes, *Book* and *Song*. *Book* has a publication and *Song* a genre, and other than that they share the same properties using the their parent class (path, name, author, rating, year).
 The *Catalog* class is present into `pa.lab5.multimedia.Media.library.Catalog`.
 
 #### - Implement the following methods representing commands that will manage the content of the catalog:~✔️
  ##### add: adds a new entry into the catalog;
    The *Catalog* instance has a list of *Media* objects. `Catalog.add(Media media)` adds a new element to the list.
  ##### list: prints the content of the catalog on the screen;
  `Catalog.list()` converts the list of items to json (`org.json.simple.*`) then prints the result using `org.apache.logging.log4j`. Each media has a method *toMap* which maps property value to a property name, and this list gets converted to json. Each property name is managed using a enum for a better implementation (`pa.lab5.files.json.JsonFileProperty`):
  
  ````java
   public enum JsonFileProperty {
       /**
        * general enums for media items
        */
       NAME("name"),
       AUTHOR("author"),
       RELEASE_YEAR("year"),
       RATING("rating"),
       PATH("path"),

       /**
        * particularities for a subclass of media
        */
       PUBLICATION("publication"),

       GENRE("genre");

       public final String label;

       JsonFileProperty(String label) {
           this.label = label;
       }
   }
````


  Output: 
 ````JSON
    12:28:20.548 [main] OFF   pa.lab5.multimedia.library.Catalog - 
        --------------------------------------LISTA----------------------------------------------
        {
          "list": [
            {
              "author": "Feodor Dostoevsky",
              "name": "Insermari din subterana",
              "path": "src\\main\\java\\pa\\lab5\\files\\books\\Feodor_Mihailovici_Dostoievski_Insemnari_Din_Subterana.pdf",
              "year": "1846",
              "rating": "1000000",
              "publication": "POLIROM"
            }
            {
              "author": "Alexandrov Ensemble",
              "name": "To serve Russia",
              "path": "src\\main\\java\\pa\\lab5\\files\\songs\\Russian_Federation_1991_Military_March_To_Serve_Russia.mp3",
              "year": "1945",
              "rating": "1000000",
              "genre": "Classical, folk tunes, hymns, operatic arias, popular music"
            },
            {
              "author": "Gica Petrescu",
              "name": "Du-ma acasa, mai stramvai!",
              "path": "src\\main\\java\\pa\\lab5\\files\\songs\\gica_petrescu.mp4",
              "year": "1970",
              "rating": "90",
              "genre": "Pre-89"
            }
          ]
      }
````
  #####  play: playback using the native operating system application (see the Desktop class);
  
  For this one I've created 2 methods, one for the books and one for the songs (*playBookByName*, *playSongByName*). It's searching by name and throws error if is not found.
  ````java
  
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
    
   ````
   
   #####  load: loads the catalog from an external file.
   
   `Catalog.load(String path)` opens a json file, parses the file and creates Media objects. The exemple from the `pa.lab5.compulsory.main()` is using the config file from `pa.lab5.files.json.Catalog.json` (which has a backup `pa.lab5.files.json.CatalogBackup.json`, for emergencies). Media, Book and Song have a static method that creates a map from their properties to the values (<String, String>). Book and Song class have a constructor which receives this kind of map. This map is validated and throws custom errors if something is wrong with the data. All media error classes are stored into `pa.lab5.multimedia.library.exception` packages and each of them (that is thrown) extends the abstract class `MediaException`. The data is validated (and better logged) in terms of in terms of null values (`MediaFieldNullException`), numerical values (`MediaNumericFieldException`), paths that don't exist (`MediaFileMissingException`).
   
   ````java
   
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
    
   ````
  
  ##### save: saves the catalog to an external file (either as a text or binary, using object serialization);
   Using `Catalog.toString()` a json text is generated from the current state of the catalog. The method uses this text and puts that in the path provided at instanciation.
   
   ````java
     /**
     * save the current list as config json file (see Catalog.load() for info about this file structure)
     * @throws FileNotFoundException
     */
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
   ````
  
  
