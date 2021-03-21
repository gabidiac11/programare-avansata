package pa.lab5.multimedia.library.exception;

public class MediaPlayableNotFound extends MediaException {
    public MediaPlayableNotFound(String name) {
        super(String.format("Media file with naem '%s' was not found in catalog", name));
    }
}
