package pa.lab3.graph;

// Java implementation of Dijkstra's Algorithm
// using Priority Queue
import java.util.*;


public class Graph <T extends NodeComparator> {
    private int count;
    private PriorityQueue<Node<T>> priorityQueueNodes;

    public Graph(PriorityQueue<T> subjects) {
        this.count = subjects.size();

        /*
         * assign list of nodes
         */
        this.priorityQueueNodes = new PriorityQueue<Node<T>>();
        for(T subject : subjects) {
            this.priorityQueueNodes.add(new Node<T>(subject));
        }
    }

    private Node<T> minDistance(Map<Node<T>, Integer> distances, Set<Node<T>> settled) {
        int minim = Integer.MAX_VALUE;
        Node<T> resultNode = null;

        for(Node<T> node : this.priorityQueueNodes) {
            if(
               !settled.contains(node) &&
               distances.get(node) <= minim
            ) {
                resultNode = node;
                minim = distances.get(node);
            }
        }

        return resultNode;
    }

    public void dijkstra(T startPointSubject) {
         Set<Node<T>> settled = new HashSet<>();
         Map<Node<T>, Integer> distances = new TreeMap<>();

        Node<T> startPoint = null;
         for(Node<T> node : this.priorityQueueNodes) {
             distances.put(node, Integer.MAX_VALUE);
             if(startPointSubject == node.getSubject()) {
                 startPoint = node;
             }
         }

        distances.put(startPoint, 0);

         while(this.priorityQueueNodes.size() != settled.size()) {
             // Pick the minimum distance vertex from the set of vertices not
             // yet processed. u is always equal to src in the first iteration.
             Node<T> minNode = this.minDistance(distances, settled);

             settled.add(minNode);

             for(Node<T> node : this.priorityQueueNodes) {
//                 System.out.printf("%s, %s, %s, %s __ %s __ %s\n",
//                         new Boolean(!settled.contains(node)).toString(),
//                         new Boolean(minNode.hasEdgeWith(node) ).toString(),
//                         new Boolean(distances.get(minNode) != Integer.MAX_VALUE ).toString(),
//                         new Boolean(distances.get(minNode) + minNode.getCostBetween(node) < distances.get(node)).toString(),
//                         minNode.getSubject().nodeToString(),
//                         node.getSubject().nodeToString()
//                 );

                 if(
                    !settled.contains(node) &&
                    minNode.hasEdgeWith(node) &&
                    distances.get(minNode) != Integer.MAX_VALUE &&
                    distances.get(minNode) + minNode.getCostBetween(node) < distances.get(node)
                 ) {
                     distances.put(node, distances.get(minNode) + minNode.getCostBetween(node));
                 }
             }
         }

         for(Node<T> node : distances.keySet()) {
             System.out.printf("Distance: %d, %s\n", distances.get(node), node.getSubject().nodeToString());
         }
    }
}

