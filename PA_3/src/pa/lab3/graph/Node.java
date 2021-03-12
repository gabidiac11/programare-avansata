package pa.lab3.graph;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Node <T extends NodeComparator> implements Comparable<Node> {
    @Getter
    private T subject;

    public Node(T subject) {
        this.subject = subject;
    }

    public boolean hasEdgeWith(Node<T> node) {
        return  this.subject.getCost(node.getSubject()) > -1;
    }

    public int getCostBetween(Node<T> node) {
        return this.subject.getCost(node.getSubject());
    }

    @Override
    public int compareTo(Node o) {
        return this.getSubject().compareToNode(o.getSubject());
    }
}
