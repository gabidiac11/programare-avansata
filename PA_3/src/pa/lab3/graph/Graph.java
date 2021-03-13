package pa.lab3.graph;

// Java implementation of Dijkstra's Algorithm
// using Priority Queue
import lombok.NonNull;

import java.util.*;

//pe clasa javadoc mereu

public class Graph <T extends NodeComparator> {
    private PriorityQueue<Node<T>> priorityQueueNodes;


    public Graph(@NonNull PriorityQueue<T> subjects) {

        /*
         * assign list of nodes
         */
        this.priorityQueueNodes = new PriorityQueue<Node<T>>();
        for(T subject : subjects) {
            this.priorityQueueNodes.add(new Node<T>(subject));
        }
    }

    /**
     * @param distances - distance from source to each node computed so far (and not computed so far)
     * @param settled - nodes that are computed in the path already
     * @return - the node that has the lowest distance so far
     */
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

    /**
     * use dijkstra algorithm to calculate the shortest path
     * @param startPointSubject - the type that is used for identifying a node
     * @return - a map from T to a distance from the source of type T
     *
     */
    public Map<T, Integer> dijkstra(T startPointSubject) {
         Set<Node<T>> settled = new HashSet<>();
         Map<Node<T>, Integer> distances = new HashMap<>();

        Node<T> startPoint = null;
         for(Node<T> node : this.priorityQueueNodes) {
             distances.put(node, Integer.MAX_VALUE);
             if(startPointSubject == node.getSubject()) {
                 startPoint = node;
             }
         }

        distances.put(startPoint, 0);

         while(this.priorityQueueNodes.size() != settled.size()) {
             /*
              * this source will be the first one here
              */
             Node<T> minNode = this.minDistance(distances, settled);

             settled.add(minNode);

             for(Node<T> node : this.priorityQueueNodes) {
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

         Map<T, Integer> result = new HashMap<>();
         for(Node<T> node : distances.keySet()) {
             result.put(node.getSubject(), distances.get(node));
             System.out.printf("Distance: %d, %s\n", distances.get(node), node.getSubject().nodeToString());
         }

         return result;
    }
}

