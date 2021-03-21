package pa.lab5.multimedia.library.exception;

import java.io.Serializable;

public abstract class MediaException extends Exception {
    public MediaException(String message) {
        super(message);
    }
}
