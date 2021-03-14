package pa.lab4.stablematching;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class resolves the following problem:
 *
 * In mathematics, economics, and computer science, the stable marriage problem (also stable matching problem or SMP) is the problem of finding a stable matching
 * between two equally sized sets of elements given an ordering of preferences for each element. A matching is a bijection from the elements of one set to the elements
 * of the other set.
 *
 * A matching is not stable if:
 *
 * There is an element M of the first matched set which prefers some given element W of the second matched set over the element to which A is already matched, and
 * B also prefers M over the element to which B is already matched.
 *
 * In other words, a matching is stable when there does not exist any match (M, W) which both prefer each other to their current partner under the matching.
 *
 * ---------------------------------------------------
 * This is solved using David Gale's algorithm
 * @param <M>
 * @param <W>
 */
@AllArgsConstructor
public class StableMarriage <M, W> {
    private List<M> men;
    private Set<W> women;
    private Map<M, List<W>> menPreferences;
    private Map<W, List<M>> womenPreferences;


    /**
     *
     * @return - a map from woman of type W to a generic type class Woman object
     */
    private Map<W, Woman<W, M>> generateWomen() {
        Map<W, Woman<W, M>> womenList = new HashMap<>();

        this.women.stream().forEach(woman -> {
            womenList.put(woman, new Woman<>(woman, this.womenPreferences.get(woman), new HashSet<>()));
        });

        return womenList;
    }

    /**
     * @return - a stable match
     */
    public Map<M, W> generateStableMatching() {
        Map<M, W> matches = new HashMap<>();

        Set<M> notFreeMen = new HashSet<>();
        Map<W, Woman<W, M>> allWoman = this.generateWomen();


        while(this.men.size() != notFreeMen.size()) {
            M man = this.men.stream()
                    .filter((manSearch) -> !notFreeMen.contains(manSearch))
                    .findFirst().get();

            if(man != null) {

                /*
                 * go trough every preferred women
                 */
                List<W> preferredWomen = this.menPreferences.get(man);
                for(int i = 0; i < preferredWomen.size() && !notFreeMen.contains(man); i++) {
                    Woman woman =  allWoman.get(preferredWomen.get(i));

                    /*
                     * this handles if another the women wants a new engagement and does the dumping
                     */
                    Pair<M, Boolean> resultProposal = woman.manProposes(man);

                    /* the engagement worked */
                    if(resultProposal.getValue() == true) {
                        matches.put(man, (W) woman.getWoman());
                        notFreeMen.add(man);

                        /* a fiance has been dumped */
                        if(resultProposal.getKey() != null) {
                            notFreeMen.remove(resultProposal.getKey());
                        }
                    }
                }

//                notFreeMen.stream().forEach(item -> {
//                    System.out.printf(item.toString());
//                    System.out.println(1);
//                });
//                System.out.println();
//                notFreeWomen.stream().forEach(item -> {
//                    System.out.printf(item.toString());
//                    System.out.println(1);
//                });
//
//                System.out.println(2);
//
//                System.out.printf("\n\n-%d-%d----%d-%d--\n\n",
//                        this.men.size(),
//                        notFreeMen.size(),
//                        this.women.size(),
//                        notFreeWomen.size()
//                );
            } else {
                break;
            }
        }

        return matches;
    }
}
