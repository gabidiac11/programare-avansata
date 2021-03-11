package pa.lab3.graph;

/**
 * this interface needs to be implemented by any T that is used for Node<T>
 *  helps with printing, comparison (mostly)
 */
public interface NodeComparator {
    int getCost(NodeComparator object);
    String nodeToString();
    String getName();

    int compareToNode(NodeComparator subject);
}
