package pa.lab5.compulsory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pa.lab5.multimedia.Book;
import pa.lab5.multimedia.Song;
import pa.lab5.multimedia.Media;
import pa.lab5.multimedia.library.Catalog;
import pa.lab5.multimedia.library.exception.MediaException;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    static Logger log = LogManager.getLogger(Main.class);

    private final static Map<String, Media> myTestMediaItems;
    static {
        myTestMediaItems = new LinkedHashMap<>();

        myTestMediaItems.put("ION",
                new Book("Liviu Rebreanu",
                        "Ion (John)",
                        "src\\main\\java\\pa\\lab5\\files\\books\\liviu.pdf",
                        1920,
                        9,
                        "Curierul literar"));

        myTestMediaItems.put( "DU_MA_ACASA_MAI_TRAMVAI",
                new Song("Gica Petrescu",
                        "Du-ma acasa, mai stramvai!",
                        "src\\main\\java\\pa\\lab5\\files\\songs\\gica_petrescu.mp4",
                        1970,
                        9*10,
                        "Pre-89"));
    }

    public static void main(String[] args)  {
        try {
            Catalog catalog = new Catalog("src\\main\\java\\pa\\lab5\\files\\json\\Catalog.json");

            catalog.add(myTestMediaItems.get("ION"));
            catalog.add(myTestMediaItems.get("DU_MA_ACASA_MAI_TRAMVAI"));

            catalog.list();

            catalog.playSongByName("To serve Russia");

            catalog.save();

            //catalog.openBookByName("Invierea");
        } catch (MediaException mediaException) {
            log.error(String.format("MEDIA ERROR: %s", mediaException.getMessage()));
        } catch (Exception exception) {
            log.error(String.format("ERROR: %s", exception.getMessage()));
        }

    }
}
