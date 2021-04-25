package pa.lab8.optional.cinema.assets.csv.columnMap;

public enum PersonColumn {
    PERSON_ID(0),
    PERSON_NAME(2),
    PERSON_BIRTH_DETAILS(4),
    PERSON_DEATH_DETAILS(4),
    PERSON_BIRTH_DATE(4);

    public int indexColumn;

    PersonColumn(int indexColumn) {
        this.indexColumn = indexColumn;
    }
}
