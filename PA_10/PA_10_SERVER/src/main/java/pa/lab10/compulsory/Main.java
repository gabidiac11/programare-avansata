package pa.lab10.compulsory;

import pa.lab10.server.Server;
import pa.lab10.server.User;
import pa.lab10.server.chart.Chart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        new Server();
//        Chart.createChart(Arrays.asList(new User("test-1"), new User("test-2")));
    }
}
