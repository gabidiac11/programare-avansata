package pa.lab8.cinema.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class Movie {
    private int id;
    private String title;
    private String releaseDate;
    private int duration;
    private int score;
    private List<Genre> genreList;

    /**
     * 🌿 🌿 🌿 🌿 🌿 🌿 🌿  🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿
     * 🤘🤘🤘
     * 🌿 🌿 🌿 🌿 🌿 🌿 🌿  🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿 🌿
     * @return
     */
    public List<Genre> getGenreList() {
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
                ", genreList=[\n" + genreList.stream().map(Genre::toString).collect(Collectors.joining("\n")) +
                "\n]}";
    }
}