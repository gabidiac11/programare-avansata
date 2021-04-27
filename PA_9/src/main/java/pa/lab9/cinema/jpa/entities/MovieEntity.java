package pa.lab9.cinema.jpa.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 *
 * movie entity in relation with GenreEntity uses "Many-to-Many Associations"
 * https://thorben-janssen.com/ultimate-guide-association-mappings-jpa-hibernate/
 */
//select distinct distributor
//        from Distributor distributor
//        join distributor.towns town
//        join town.district district
//        where district.name = :name

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Movie_findById",
                query = "SELECT movie from MovieEntity movie LEFT JOIN movie.genreEntities where movie.movieId = :movieId"
        ),
        @NamedQuery(
                name = "Movie_findByName",
                query = "SELECT movie from MovieEntity movie JOIN movie.genreEntities where movie.title = :title"
        ),
        @NamedQuery(
                name = "Movie_fetchByReleaseDate",
                query = "SELECT movie from MovieEntity movie JOIN movie.genreEntities  ORDER BY movie.releaseDate"
        ),
        @NamedQuery(
                name = "Movie_fetchByRating",
                query = "SELECT movie from MovieEntity movie JOIN movie.genreEntities  ORDER BY movie.score"
        )
})
@Table(name = "movie", schema = "pa_database")
public class MovieEntity {
    private String title;
    private String releaseDate;
    private int duration;
    private int score;
    @Id
    private String movieId;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.PERSIST)
    @JoinTable(name = "movie_genre",
            joinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "fk_movie_id"), name = "movie_id", referencedColumnName = "movie_id" ),
            inverseJoinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "fk_genre_id"), name = "genre_id", referencedColumnName = "genre_id"))
    private Set<GenreEntity> genreEntities;


    @Basic
    @Column(name = "title", nullable = false, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "release_date", nullable = false, length = 255)
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Basic
    @Column(name = "duration", nullable = false)
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Basic
    @Column(name = "score", nullable = false)
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Id
    @Column(name = "movie_id", nullable = false, length = 255)
    public String getMovieId() {
        return movieId;
    }

    public void setGenreEntities(Set<GenreEntity> genreEntities) {
        this.genreEntities = genreEntities;
    }

    @ElementCollection
    public Set<GenreEntity> getGenreEntities() {
        return genreEntities;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieEntity that = (MovieEntity) o;
        return duration == that.duration && score == that.score && Objects.equals(title, that.title) && Objects.equals(releaseDate, that.releaseDate) && Objects.equals(movieId, that.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, releaseDate, duration, score, movieId);
    }

    @Override
    public String toString() {

        return "MovieEntity{" +
                "title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", duration=" + duration +
                ", score=" + score +
                ", movieId='" + movieId + '\'' +
                ", genreEntities=" + (genreEntities != null ? genreEntities.stream().map(GenreEntity::getName).collect(Collectors.toList()) : "null")  +
                '}';
    }
}
