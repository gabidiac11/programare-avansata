package pa.lab10.server.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import pa.lab10.server.User;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Chart {
    private static void createMessagesChart(List<User> userList) throws IOException {
        var dataset = new DefaultPieDataset();
        userList.forEach(item -> {
            dataset.setValue(item.getUserName(), item.getMessages().size());
        });

        JFreeChart chart = ChartFactory.createPieChart("Messages received",
                dataset, true, true, false);

        chart.setBorderVisible(false);
        int width = 1000;
        int height = 1000;

        ChartUtils.writeChartAsPNG(new FileOutputStream("src\\main\\java\\pa\\lab10\\server\\chart\\session\\chart-messages.png"), chart, width, height);
    }

    private static void createFriendsChart(List<User> userList) throws IOException {
        var dataset = new DefaultPieDataset();
        userList.forEach(item -> {
            dataset.setValue(item.getUserName(), item.getFriends().size());
        });

        JFreeChart chart = ChartFactory.createPieChart("Friends that each user has",
                dataset, true, true, false);

        chart.setBorderVisible(false);
        int width = 1000;
        int height = 1000;

        ChartUtils.writeChartAsPNG(new FileOutputStream("src\\main\\java\\pa\\lab10\\server\\chart\\session\\chart-friends.png"), chart, width, height);
    }

    public static void createChart(List<User> userList) throws IOException {
        createMessagesChart(userList);
        createFriendsChart(userList);
    }
}
