package pa.lab5.multimedia.library.exception;

public class MediaYearInvalidException extends MediaException {
    public MediaYearInvalidException(int year, String mediaName) {
            super(String.format("The media item with the name %s has a invalid year %d: '%s'", mediaName, year));
    }
}
