package pa.lab9.html;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.*;

import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

public class TemplateMaker {
    public static void outputToHtml(Map<String, Object> input, String templateName, String outputName) throws IOException, SQLException, TemplateException {
        Configuration cfg = new Configuration();
        FileTemplateLoader ftl1 = new FileTemplateLoader(new File("src\\main\\java\\pa\\lab9\\html\\templates"));
        MultiTemplateLoader mtl = new MultiTemplateLoader(new TemplateLoader[] { ftl1 });
        cfg.setTemplateLoader(mtl);

        // Some other recommended settings:
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 2.2. Get the template
        Template template = cfg.getTemplate(templateName);

        // 2.3. Generate the output
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);

        String outputPath = String.format("src\\main\\java\\pa\\lab9\\html\\output\\%s", outputName);
        try (Writer fileWriter = new FileWriter(outputPath)) {
            template.process(input, fileWriter);
            File file = new File(outputPath);
            Desktop.getDesktop().open(file);
        }
    }
}
