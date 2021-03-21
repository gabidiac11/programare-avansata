package pa.lab5.multimedia.library.exception;

public class MediaFileMissingException extends MediaException  {
    public MediaFileMissingException(Exception e, String path, String mediaName) {
        super(String.format("error: '%s'\nThe media item with the name '%s' has a file that does not exit: '%s'", e.getMessage(), mediaName, path));
    }
}
