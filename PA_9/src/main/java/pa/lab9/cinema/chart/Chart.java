package pa.lab9.cinema.chart;


import freemarker.template.*;
import pa.lab9.cinema.factory.AbstractFactory;
import pa.lab9.cinema.factory.FactoryProvider;
import pa.lab9.cinema.jpa.entities.MovieEntity;


import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import pa.lab9.html.TemplateMaker;

public class Chart {
    private ChartType chartType;
    private Timestamp creationDate;
    private List<MovieEntity> movieEntityList;

    public Chart(ChartType chartType) throws Exception {
        this.chartType = chartType;
        this.creationDate = Timestamp.from(ZonedDateTime.now().toInstant());
        this.fetchList();
    }

    public void fetchList() throws Exception {
            AbstractFactory factory = FactoryProvider.getConfiguredFactory();
            movieEntityList = factory.createMovieRepository().fetchOrderedBy(this.chartType);

            System.out.println(movieEntityList.size());
    }

    public void chartToHtml() throws IOException, SQLException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        input.put("movieList", this.movieEntityList);
        input.put("chartTitle", this.chartType.label);

        TemplateMaker.outputToHtml(input, "chart.ftl", String.format("chart-%s.html", chartType.signature));
    }
}
