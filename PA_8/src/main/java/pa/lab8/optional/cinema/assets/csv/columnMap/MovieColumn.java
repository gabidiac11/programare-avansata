package pa.lab8.optional.cinema.assets.csv.columnMap;

public enum MovieColumn {
    MOVIE_ID(0),
    MOVIE_TITLE(1),
    RELEASE_DATE(4),
    GENRE_NAME(5),
    MOVIE_DURATION(6);

    public int indexColumn;

    MovieColumn(int indexColumn) {
        this.indexColumn = indexColumn;
    }
}
