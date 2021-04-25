package pa.lab8.optional.cinema.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class MovieModel {
    private final String id;
    private final String title;
    private final String releaseDate;
    private final int duration;
    private final int score;
    private final List<GenreModel> genreList;

    /**
     * 🌿 🌿 🌿 🌿 🌿 🌿 🌿  🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿
     * 🤘🤘🤘
     * 🌿 🌿 🌿 🌿 🌿 🌿 🌿  🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿
     * @return -
     */
    public List<GenreModel> getGenreList() {
        return Collections.unmodifiableList(genreList);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", duration=" + duration +
                ", score=" + score +
                ", genreList=[" + genreList.stream().map(GenreModel::toString).collect(Collectors.joining("\n")) +
                "]}\n";
    }
}