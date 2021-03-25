package pa.lab5.multimedia;

        import java.io.File;
        import java.io.FileWriter;
        import java.io.OutputStreamWriter;
        import java.io.Writer;
import java.util.HashMap;
        import java.util.List;
        import java.util.Locale;
        import java.util.Map;

        import freemarker.cache.FileTemplateLoader;
        import freemarker.cache.MultiTemplateLoader;
        import freemarker.cache.TemplateLoader;
        import freemarker.template.Configuration;
        import freemarker.template.Template;
        import freemarker.template.TemplateExceptionHandler;
        import freemarker.template.Version;
        import pa.lab5.multimedia.library.Catalog;

public class CatalogHtmlMaker {

    public CatalogHtmlMaker(Catalog catalog) throws Exception {

        Configuration cfg = new Configuration();

        FileTemplateLoader ftl1 = new FileTemplateLoader(new File("src\\main\\java\\pa\\lab5\\multimedia\\templates"));

       MultiTemplateLoader mtl = new MultiTemplateLoader(new TemplateLoader[] { ftl1});

        cfg.setTemplateLoader(mtl);

        // Some other recommended settings:
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 2. Proccess template(s)
        //
        // You will do this for several times in typical applications.

        // 2.1. Prepare the template input:

        Map<String, Object> input = new HashMap<String, Object>();

        List<Media> mediaList = catalog.getMediaList();
        input.put("mediaList", mediaList);

        // 2.2. Get the template

        Template template = cfg.getTemplate("catalog.ftl");

        // 2.3. Generate the output

        // Write output to the console
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        // For the sake of example, also write output into a file:
        Writer fileWriter = new FileWriter(new File("output.html"));
        try {
            template.process(input, fileWriter);
        } finally {
            fileWriter.close();
        }

    }
}