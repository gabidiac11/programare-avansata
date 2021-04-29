package pa.lab9.bonus;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.*;
import org.jgrapht.Graph;
import org.jgrapht.alg.interfaces.MatchingAlgorithm;
import org.jgrapht.alg.matching.DenseEdmondsMaximumCardinalityMatching;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import pa.lab9.cinema.chart.ChartType;
import pa.lab9.cinema.factory.FactoryProvider;
import pa.lab9.cinema.jpa.entities.MovieEntity;
import pa.lab9.cinema.jpa.entities.PersonsEntity;
import pa.lab9.html.TemplateMaker;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

public class MoviePlaylist
{
    private List<MovieEntity> movieEntityList;
    private Graph<Integer, DefaultEdge> graph;
    List<List<MovieEntity>> moviePairs;

    public MoviePlaylist() throws Exception {
        movieEntityList = FactoryProvider.getConfiguredFactory().createMovieRepository().fetchOrderedBy(ChartType.BY_RATING);
        this.createPlayList();
    }

    private static PersonsEntity getCommonDirector(MovieEntity movieEntity, MovieEntity v1) {
        for(PersonsEntity personsEntity : movieEntity.getPersons()) {
            for(PersonsEntity personsEntity1 : v1.getPersons()) {
                if(personsEntity.equals(personsEntity1)) {
                    return personsEntity;
                }
            }
        }

        return null;
    }

    private Graph<Integer, DefaultEdge> generateGraph() throws Exception {
        Graph<Integer, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);

        for(int i = 0; i < movieEntityList.size(); i++) {
            graph.addVertex(i);
        }

        for(int i = 0; i < movieEntityList.size(); i++) {
            for(int ii = i + 1; ii < movieEntityList.size(); ii++) {
                if(getCommonDirector(movieEntityList.get(i), movieEntityList.get(ii)) != null) {
                    graph.addEdge(i, ii);
                    graph.addEdge(ii, i);
                }
            }
        }

        return graph;
    }

    private void createPlayList() throws Exception {
        graph = generateGraph();

       MatchingAlgorithm<Integer, DefaultEdge> matchCardinal = new DenseEdmondsMaximumCardinalityMatching<>(graph);
       MatchingAlgorithm.Matching<Integer, DefaultEdge> match =  matchCardinal.getMatching();

       graphToPng(graph, "matched-initial.png");

        /* remove unmatched movies */
        Set<DefaultEdge> matchedEdges = match.getEdges();
        List<Integer> vertexes = new ArrayList<>(graph.vertexSet());
        for(int i = 0; i < vertexes.size(); i++) {
            for(int ii = i+1; ii < vertexes.size(); ii++) {

                for(DefaultEdge edge : new DefaultEdge[]{
                        graph.getEdge(vertexes.get(i), vertexes.get(ii)),
                        graph.getEdge(vertexes.get(ii), vertexes.get(i))
                }) {
                    if(edge != null && !matchedEdges.contains(edge)) {
                            graph.removeEdge(edge);

                    }
                }
            }
        }

        moviePairs = new ArrayList<>();
        for(int i = 0; i < vertexes.size(); i++) {
            for(int ii = i+1; ii < vertexes.size(); ii++) {

                for(DefaultEdge edge : new DefaultEdge[]{
                        graph.getEdge(vertexes.get(i), vertexes.get(ii)),
                        graph.getEdge(vertexes.get(ii), vertexes.get(i))
                }) {
                    if(edge != null) {
                        moviePairs.add(Arrays.asList(
                                movieEntityList.get(vertexes.get(i)),
                                movieEntityList.get(vertexes.get(ii))));
                    }
                }
            }
        }

        graphToPng(graph, "matched-after.png");
    }

    private void graphToPng(Graph graph, String fileName) throws IOException {
        JGraphXAdapter<Integer, DefaultEdge> graphAdapter = new JGraphXAdapter<Integer, DefaultEdge>(graph);

        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());
        File imgFile = new File(String.format("src\\main\\java\\pa\\lab9\\html\\output\\%s", fileName));

        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        ImageIO.write(image, "PNG", imgFile);
    }

    public void outputToHtml() throws IOException, SQLException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        input.put("moviePairList", this.moviePairs);
        TemplateMaker.outputToHtml(input, "bonus.ftl", "bonus.html");
    }
}
