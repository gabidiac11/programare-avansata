package pa.lab5.multimedia.library.exception;

import pa.lab5.files.json.JsonFileProperty;

public class MediaFieldNullException extends MediaException {
    public MediaFieldNullException(JsonFileProperty property) {
        super(MediaFieldNullException.defaultFormat(property));
    }

    public MediaFieldNullException(JsonFileProperty property, String mediaName) {
        super(String.format("%s%s", MediaFieldNullException.defaultFormat(property), mediaName != null ? " " + mediaName : ""));
    }

    private static String defaultFormat(JsonFileProperty property) {
        return String.format("MediaFieldException: the field %s can not be null", property.label);
    }
}
