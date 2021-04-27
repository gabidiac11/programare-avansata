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
        Configuration cfg = new Configuration();
        FileTemplateLoader ftl1 = new FileTemplateLoader(new File("src\\main\\java\\pa\\lab9\\cinema\\chart\\templates"));
        MultiTemplateLoader mtl = new MultiTemplateLoader(new TemplateLoader[] { ftl1 });
        cfg.setTemplateLoader(mtl);

        // Some other recommended settings:
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);


        Map<String, Object> input = new HashMap<>();
        input.put("movieList", this.movieEntityList);
        input.put("chartTitle", this.chartType.label);

        // 2.2. Get the template
        Template template = cfg.getTemplate("template.ftl");

        // 2.3. Generate the output
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);
        String outputPath = String.format("chart-%s.html", chartType.signature);
        Writer fileWriter = new FileWriter(outputPath);

        try {
            template.process(input, fileWriter);
            File file = new File(outputPath);
            Desktop.getDesktop().open(file);
        } finally {
            fileWriter.close();
        }
    }
}
