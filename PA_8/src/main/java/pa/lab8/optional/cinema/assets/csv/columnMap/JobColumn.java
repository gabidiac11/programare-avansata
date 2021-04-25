package pa.lab8.optional.cinema.assets.csv.columnMap;

public enum JobColumn {
    JOB_MOVIE_ID(0),
    JOB_PERSON_ID(2),
    JOB_NAME(4); //ACTOR or DIRECTOR

    public int indexColumn;

    JobColumn(int indexColumn) {
        this.indexColumn = indexColumn;
    }
}
