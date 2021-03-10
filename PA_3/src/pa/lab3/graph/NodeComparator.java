package pa.lab3.graph;

import java.util.Comparator;

public interface NodeComparator {
    int getCost(NodeComparator object);
    String nodeToString();
    String getName();

    int compareToNode(NodeComparator subject);
}
