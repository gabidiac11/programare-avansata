package pa.lab9.cinema.jpa.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Genre_findById",
                query = "SELECT genre from GenreEntity genre where genre.genreId = :genreId"
        ),
        @NamedQuery(
                name = "Genre_findByName",
                query = "SELECT genre from GenreEntity genre where genre.name = :name"
        )
})
@Table(name = "genre", schema = "pa_database")
public class GenreEntity {
    private String name;
    private int genreId;

    @ManyToMany(mappedBy = "genreEntities")
    private Set<MovieEntity> movieEntities = new HashSet<>();

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Id
    @Column(name = "genre_id", nullable = false)
    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity that = (GenreEntity) o;
        return genreId == that.genreId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, genreId);
    }

    @Override
    public String toString() {
        return "GenreEntity{" +
                "name='" + name + '\'' +
                ", genreId=" + genreId +
                '}';
    }
}
