package pa.lab4.stablematching;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Set;

/**
 * int this algorithm a woman have a multiple husbands
 * the preference is given by a subset of all men that are ordered in a list based on preference
 * @param <W>
 * @param <M>
 */
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class Woman<W, M> {
    private W woman;
    /**
     * order in which a woman prefers a subset of all men
     */
    private final List<M> preferences;
    /**
     * fiances that can be dumped if someone betters comes along
     */
    private Set<M> fiances;

    /**
     * measure level of chad-ness
     * @param man - the man to search for in woman's list
     * @return - level of preference (from lower to the biggest)
     */
    private int getManRank(M man) {
        List <M> men = this.preferences;
        for(int i = 0; i < men.size(); i++) {
            if(men.get(i).equals(man)) {
                return i;
            }
        }

        return Integer.MAX_VALUE;
    }

    /**
     * @return - the man that is the least desirable and its score
     */
    private Pair<M, Integer> getTheMostDisposableFiance() {
        int maxValue = Integer.MIN_VALUE;
        M man = null;

        for(M fiance : this.fiances) {
            int rank = this.getManRank(fiance);
            if(rank > maxValue) {
                maxValue = rank;
                man = fiance;
            }
        }
        return new Pair<>(man, maxValue);
    }

    /**
     * in this method this woman dumps the most undesirable fiance for the new man if the man is better
     * @param newMan - the man that proposes
     * @return Pair<M, Boolean>:
     *         - M - the fiance that got be dumped
     *         - Boolean - meaning the new man is engaged (-> one fiance dumped)
     */
    private Pair<M, Boolean> manProposesFullyEngagedWoman(M newMan) {


        Pair<M, Integer> disposableFiance = this.getTheMostDisposableFiance();

        if(
          disposableFiance.getKey() == null ||
          this.getManRank(newMan) < disposableFiance.getValue()
        ) {

            /* dump the fiance */
            if(disposableFiance.getKey() != null) {
                this.fiances.remove(disposableFiance.getValue());
            }

            /* add the new fiance */
            this.fiances.add(newMan);

            /* indicate what fiance has been dumped and if a new engagement has occurred */
            return new Pair<>(disposableFiance.getKey(), true);
        }

        return new Pair<>(null, false);
    }

    /**
     * in this method this woman dumps the most undesirable fiance for the new man if the man is better
     * @param newMan - the man that proposes
     * @return Pair<M, Boolean>:
     *         - M - the fiance that got be dumped
     *         - Boolean - a new man is engaged
     */
    public Pair<M, Boolean> manProposes(M newMan) {
        if(this.fiances.size() == this.preferences.size()) {
            return this.manProposesFullyEngagedWoman(newMan);
        } else {
            this.fiances.add(newMan);

            return new Pair<>(null, true);
        }
    }
}
