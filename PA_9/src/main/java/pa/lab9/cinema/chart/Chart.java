package pa.lab9.cinema.chart;

public class Chart {

//    public DatabaseHtmlMaker() throws IOException, SQLException, TemplateException {
//        Configuration cfg = new Configuration();
//
//        FileTemplateLoader ftl1 = new FileTemplateLoader(new File("src\\main\\java\\pa\\lab8\\optional\\cinema\\html\\templates"));
//
//        MultiTemplateLoader mtl = new MultiTemplateLoader(new TemplateLoader[] { ftl1 });
//
//        cfg.setTemplateLoader(mtl);
//
//        // Some other recommended settings:
//        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
//        cfg.setDefaultEncoding("UTF-8");
//        cfg.setLocale(Locale.US);
//        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//
//
//        Map<String, Object> input = new HashMap<>();
//
//        List<MovieModel> movieList = MovieDao.getMoviesForDisplay();
//        System.out.println(movieList.size());
//        input.put("movieList", movieList);
//
//        // 2.2. Get the template
//
//        Template template = cfg.getTemplate("database.ftl");
//
//        // 2.3. Generate the output
//
//        // Write output to the console
//        Writer consoleWriter = new OutputStreamWriter(System.out);
//        template.process(input, consoleWriter);
//
//        // For the sake of example, also write output into a file:
//        Writer fileWriter = new FileWriter(new File("output.html"));
//        try {
//            template.process(input, fileWriter);
//        } finally {
//            fileWriter.close();
//        }
//
//    }
}
