package pa.lab9.bonus;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
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

/**
 * To implement this functionality I used org.jgrapht.alg.matching.DenseEdmondsMaximumCardinalityMatching to create a pair of movies
 *
 * The process follows these steps:
 * 1. generate a graph representing each connection movies have with each other (share at least one director)
 * 2. solve the maximum cardinal matching and obtain a graph with edges that are not adjacent with each other
 * 3. filter out the result pairs with edges that still share the a director
 *
 * Output, a html file (pa.lab9.hml.output) containing:
 * - a side by side images of initial graph and the resulting graph
 * - a list of pairs of movies per day
 */
public class MoviePlaylist
{
    /**
     * sample of movies used -> the same list used for chart, a select with 100 movies sorted by release date + they must have at least 1 director
     */
    private final List<MovieEntity> movieEntityList;

    /** the resulting pairs (easier in freeMaker loop in this form) */
    private List<List<MovieEntity>> moviePairs;

    public MoviePlaylist() throws Exception {
        movieEntityList = FactoryProvider.getConfiguredFactory().createMovieRepository().fetchOrderedBy(ChartType.BY_RATING);
        this.createPlayList();
    }

    /**
     * get the first directory shared by 2 movies
     * @param movieEntity
     * @param v1
     * @return
     */
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

    /**
     * maps Movie entity to an integer and makes a graph
     * an edge -> 2 movies share at least one movie
     * @return
     */
    private Graph<Integer, DefaultEdge> generateGraph() {
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

    /**
     * creates
     * @throws Exception
     */
    private void createPlayList() throws Exception {
        Graph<Integer, DefaultEdge> graph = generateGraph();

       MatchingAlgorithm<Integer, DefaultEdge> matchCardinal = new DenseEdmondsMaximumCardinalityMatching<>(graph);
       MatchingAlgorithm.Matching<Integer, DefaultEdge> match =  matchCardinal.getMatching();

       /* save initial graph image */
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

        this.generatePairsFromGraphResults(graph, vertexes);

        /* save resulted graph image */
        graphToPng(graph, "matched-after.png");
    }

    /**
     * maps the Integer vertex based graph to the actual movie entities
     * each edge will be a 2 length-ed list of movies
     *
     * an extra verification is done to not insert movies that still share a secondary director
     * @param graph
     * @param vertexes
     */
    private void generatePairsFromGraphResults(Graph<Integer, DefaultEdge> graph, List<Integer> vertexes) {
        moviePairs = new ArrayList<>();
        for(int i = 0; i < vertexes.size(); i++) {
            for(int ii = i+1; ii < vertexes.size(); ii++) {

                for(DefaultEdge edge : new DefaultEdge[]{
                        graph.getEdge(vertexes.get(i), vertexes.get(ii)),
                        graph.getEdge(vertexes.get(ii), vertexes.get(i))
                }) {
                    MovieEntity movieEntity1 = movieEntityList.get(vertexes.get(i));
                    MovieEntity movieEntity2 = movieEntityList.get(vertexes.get(ii));

                    if(graph.edgeSet().contains(edge) && this.noCommonDirectorInPairSoFar(movieEntity1, movieEntity2)) {
                        moviePairs.add(Arrays.asList(movieEntity1, movieEntity2));
                        break;
                    }
                }
            }
        }
    }

    /**
     * an extra verification is done to not insert movies that still share a secondary director
     * @param movieEntity
     * @param movieEntity2
     * @return
     */
    private boolean noCommonDirectorInPairSoFar(MovieEntity movieEntity, MovieEntity movieEntity2) {
        for(List<MovieEntity> moviePair : this.moviePairs) {
            for(MovieEntity movieEntityTest : moviePair) {
                if(getCommonDirector(movieEntityTest, movieEntity) != null || getCommonDirector(movieEntityTest, movieEntity2) != null) {
                    return false;
                }
            }
        }

        return true;
    }

    private void graphToPng(Graph graph, String fileName) throws IOException {
        JGraphXAdapter<Integer, DefaultEdge> graphAdapter = new JGraphXAdapter<Integer, DefaultEdge>(graph);

        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());
        File imgFile = new File(String.format("src\\main\\java\\pa\\lab9\\html\\output\\%s", fileName));

        BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        ImageIO.write(image, "PNG", imgFile);
    }

    /**
     * Output, a html file containing:
     * - a side by side images of initial graph and the resulting graph
     * - a list of pairs of movies per day
     *
     * @throws IOException
     * @throws SQLException
     * @throws TemplateException
     */
    public void outputToHtml() throws IOException, SQLException, TemplateException {
        Map<String, Object> input = new HashMap<>();
        input.put("moviePairList", this.moviePairs);
        TemplateMaker.outputToHtml(input, "bonus.ftl", "bonus.html");
    }
}
