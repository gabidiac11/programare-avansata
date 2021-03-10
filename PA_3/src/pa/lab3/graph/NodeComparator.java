package pa.lab3.graph;

import java.util.Comparator;

public interface NodeComparator extends Comparator<NodeComparator> {
    int getCost(NodeComparator object);
    String nodeToString();
}
