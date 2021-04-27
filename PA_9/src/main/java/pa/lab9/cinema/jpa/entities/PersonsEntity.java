package pa.lab9.cinema.jpa.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "persons", schema = "pa_database")
public class PersonsEntity {
    private String imdbNameId;
    private String name;
    private String birthDetails;
    private String dateOfBirth;
    private String deathDetails;

    @Id
    @Column(name = "imdb_name_id", nullable = false, length = 100)
    public String getImdbNameId() {
        return imdbNameId;
    }

    @ManyToMany(mappedBy = "persons")
    private Set<MovieEntity> movies = new HashSet<>();

    public void setImdbNameId(String imdbNameId) {
        this.imdbNameId = imdbNameId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "birth_details", nullable = true, length = -1)
    public String getBirthDetails() {
        return birthDetails;
    }

    public void setBirthDetails(String birthDetails) {
        this.birthDetails = birthDetails;
    }

    @Basic
    @Column(name = "date_of_birth", nullable = true, length = -1)
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    @Column(name = "death_details", nullable = true, length = -1)
    public String getDeathDetails() {
        return deathDetails;
    }

    public void setDeathDetails(String deathDetails) {
        this.deathDetails = deathDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonsEntity that = (PersonsEntity) o;
        return Objects.equals(imdbNameId, that.imdbNameId) && Objects.equals(name, that.name) && Objects.equals(birthDetails, that.birthDetails) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(deathDetails, that.deathDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbNameId, name, birthDetails, dateOfBirth, deathDetails);
    }
}
