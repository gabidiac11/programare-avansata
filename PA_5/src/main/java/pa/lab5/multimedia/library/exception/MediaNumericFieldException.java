package pa.lab5.multimedia.library.exception;

import pa.lab5.files.json.JsonFileProperty;

public class MediaNumericFieldException extends MediaException {
    public MediaNumericFieldException(JsonFileProperty field, String mediaName, Exception e) {
        super(String.format("The media numeric field %s item with the name %s has a the following error: '%s'", field.label, mediaName, e.getMessage()));
    }
}
