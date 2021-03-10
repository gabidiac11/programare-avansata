package pa.lab3.graph;

import lombok.Getter;

import java.util.Comparator;

public class Node <T extends NodeComparator> {
    @Getter
    private T subject;

    public Node(T subject) {
        this.subject = subject;
    }

    public boolean hasEdgeWith(Node<T> node) {
        return  node.subject.getCost(node.getSubject()) > -1;
    }

    public int getCostBetween(Node<T> node) {
        return node.subject.getCost(node.getSubject());
    }

}
